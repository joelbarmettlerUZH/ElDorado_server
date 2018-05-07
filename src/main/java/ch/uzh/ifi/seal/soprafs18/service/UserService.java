package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@Service
public class UserService  implements Serializable {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    private final Logger LOGGER = Logger.getLogger(RoomService.class.getName());
    private FileHandler filehandler;

    public UserService() {
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

    public boolean valid(String token, UserEntity user) {
        UserEntity u = userRepository.findByUserID(user.getUserID()).get(0);
        LOGGER.info("Validating token of user " + user.getUserID());
        return token.equals(u.getToken());
    }

    public static boolean valid(String token, UserEntity user, UserRepository userRepository) {
        UserEntity u = userRepository.findByUserID(user.getUserID()).get(0);
        System.out.println("***Validating user (no logger available)!***");
        return token.equals(u.getToken());
    }

    public List<String> createUser(UserEntity user) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String token = buffer.toString();
        LOGGER.severe("Setting token of user " + user.getUserID() + " to " + token);
        user.setToken(token);
        userRepository.save(user);
        List<String> l = new ArrayList<>();
        l.add(token);
        l.add(String.valueOf(user.getUserID()));
        LOGGER.info("Created new user " + user.getUserID());
        return l;
    }

    public List<UserEntity> getAll() {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        LOGGER.info("Returning all users");
        return users;
    }

    public UserEntity getByID(int id) {
        LOGGER.info("Returning user " + id);
        return userRepository.findByUserID(id).get(0);
    }

    public UserEntity updateUser(UserEntity userEntity, String token) {
        if (!valid(token, userEntity)) {
            LOGGER.warning("User " + userEntity.getUserID() + " provided wrong token " + token);
            return null;
        }
        UserEntity user = userRepository.findByUserID(userEntity.getUserID()).get(0);
        RoomEntity room = user.getRoomEntity();
        for(UserEntity u: room.getUsers()){
            if(u.getUserID() != userEntity.getUserID()){
                if(u.getCharacter() == userEntity.getCharacter()){
                    LOGGER.warning("Modification not possible since there is an interference with another Player in Room " + room.getRoomID()
                    + " that has the same character chosen.");
                    return user;
                }
                if(u.getName().toLowerCase().equals(userEntity.getName().toLowerCase())){
                    LOGGER.warning("Modification not possible since there is an interference with another Player in Room " + room.getRoomID()
                            + " that has the same Username.");
                    return user;
                }
            }
        }
        if(userEntity.getCharacter() < 0 || userEntity.getCharacter()>3){
            LOGGER.warning("Invalid character number "+userEntity.getCharacter()+" for user "+user.getUserID());
            return user;
        }
        user.setCharacter(userEntity.getCharacter());
        user.setName(userEntity.getName());
        user.setReady(userEntity.isReady());
        userRepository.save(user);
        LOGGER.info("Updating user " + user.getUserID());
        if (user.getRoomEntity() != null) {
            LOGGER.info("Updating room " + user.getRoomEntity().getRoomID() + " since user " + user.getUserID() + " was modified. ");
            roomService.updateRoom(user.getRoomEntity());
        }
        return user;
    }

    public void deleteUser(int userId, String token) {
        UserEntity user = userRepository.findByUserID(userId).get(0);
        RoomEntity room = user.getRoomEntity();
        if (!UserService.valid(token, user, userRepository)) {
            LOGGER.warning("User " + user.getUserID() + " was trying to leave room with wrong or missing token");
            return;
        }
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
}