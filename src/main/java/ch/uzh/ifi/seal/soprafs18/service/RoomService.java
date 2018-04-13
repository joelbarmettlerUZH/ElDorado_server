package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class RoomService  implements Serializable {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameService gameService;

    private final Logger LOGGER = Logger.getLogger(RoomService.class.getName());
    private FileHandler filehandler;

    public RoomService() {
        try {
            filehandler = new FileHandler("Serverlog.log", 1024 * 8, 1, true);
            LOGGER.addHandler(filehandler);
            SimpleFormatter formatter = new SimpleFormatter();
            filehandler.setFormatter(formatter);
            LOGGER.setLevel(Level.FINE);
            filehandler.setLevel(Level.INFO);
        } catch (IOException io) {
            System.out.println("ERROR: Could not set logging handler to file");
        }
    }

    public void joinUser(int roomID, UserEntity userEntity, String token) {
        if (!UserService.valid(token, userEntity, userRepository)) {
            LOGGER.warning("User " + userEntity.getUserID() + " was trying to join with wrong or missing token");
            return;
        }
        UserEntity user = userRepository.findByUserID(userEntity.getUserID()).get(0);
        RoomEntity room = roomRepository.findByRoomID(roomID).get(0);
        if (room.getUsers().size() == 4) {
            LOGGER.info("Unable to join room " + roomID + " due to a usernumber of 4");
            return;
        }
        int character = user.getCharacter();
        for(UserEntity u: room.getUsers()){
            if(u.getCharacter() == user.getCharacter() || u.getName().toLowerCase().equals(user.getName().toLowerCase())){
                LOGGER.info("Unable to join room " + roomID + " Since chosen character "+user.getCharacter()
                        +"or chosen Name " + user.getName() + " is already existent:");
                return;
            }
        }
        user.setRoomEntity(room);
        room.addUser(user);
        roomRepository.save(room);
        userRepository.save(user);
        LOGGER.info("User " + user.getUserID() + " joined room " + roomID + " successfully. Updating room now");
        updateRoom(room);
    }

    public void leaveUser(int roomID, UserEntity userEntity, String token) {
        UserEntity user = userRepository.findByUserID(userEntity.getUserID()).get(0);
        if (!UserService.valid(token, user, userRepository)) {
            LOGGER.warning("User " + userEntity.getUserID() + " was trying to leave room with wrong or missing token");
            return;
        }
        RoomEntity room = roomRepository.findByRoomID(roomID).get(0);
        List<UserEntity> currentUsers = room.getUsers();
        currentUsers.remove(user);
        room.setUsers(currentUsers);
        roomRepository.save(room);
        userRepository.delete(user);
        LOGGER.info("User " + user.getUserID() + " left room and got deleted");
        if (room.getUsers().size() == 0) {
            roomRepository.delete(room);
            LOGGER.info("Deleted room since no users are left");
        }
    }

    public List<UserEntity> getUsers(RoomEntity roomEntity) {
        LOGGER.info("Requested users of room " + roomEntity.getRoomID());
        return roomEntity.getUsers();
    }

    public int newRoom(RoomEntity roomEntity) {
        roomRepository.save(roomEntity);
        LOGGER.info("Created new room " + roomEntity.getRoomID());
        return roomEntity.getRoomID();

    }

    public void removeRoom(RoomEntity roomEntity) {
        roomRepository.delete(roomEntity);
        LOGGER.info("Deleted room " + roomEntity.getRoomID());
    }

    public RoomEntity getRoom(int RoomID) {
        LOGGER.info("Returning requested room " + RoomID);
        return roomRepository.findByRoomID(RoomID).get(0);
    }

    public List<RoomEntity> getRooms() {
        List<RoomEntity> roomEntities = new ArrayList<>();
        roomRepository.findAll().forEach(roomEntities::add);
        LOGGER.info("Returning all rooms");
        return roomEntities;
    }

    public List<RoomEntity> getRooms(int fromIndex, int toIndex) {
        toIndex = toIndex + 1;
        List<RoomEntity> rooms = getRooms();
        if (fromIndex > toIndex) {
            LOGGER.warning("Requested fromIndex is smaller than given toIndex");
            return new ArrayList<>();
        }
        if (toIndex > rooms.size()) {
            LOGGER.warning("Setting toIndex to max index");
            toIndex = rooms.size();
        }
        LOGGER.info("Returning rooms from " + fromIndex + " to " + toIndex);
        return rooms.subList(fromIndex, toIndex);
    }

    public void startGame(RoomEntity roomEntity) {
        LOGGER.info("Starting a new Game with room " + roomEntity.getRoomID());
        gameService.newGame(roomEntity);
    }

    public void updateRoom(RoomEntity roomEntity) {
        boolean ready = true;
        for (UserEntity user : roomEntity.getUsers()) {
            ready = ready && user.isReady();
        }
        if (ready && (roomEntity.getUsers().size() >= 2)) {
            LOGGER.info("Room " + roomEntity.getRoomID() + " is starting.");
            startGame(roomEntity);
        }
    }

}
