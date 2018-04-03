package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.controller.UserController;
import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.PlayerEntity;
import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public void joinUser(int roomID, UserEntity userEntity, String token) {
        if(!UserService.valid(token, userEntity, userRepository)){
            return;
        }
        UserEntity user = userRepository.findByName(userEntity.getName()).get(0);
        RoomEntity room = roomRepository.findByRoomID(roomID).get(0);
        user.setRoomEntity(room);
        room.addUser(user);
        roomRepository.save(room);
        userRepository.save(user);
    }

    public void leaveUser(int roomID, UserEntity userEntity, String token){
        UserEntity user = userRepository.findByName(userEntity.getName()).get(0);
        if(!UserService.valid(token, user, userRepository)){
            return;
        }
        RoomEntity room = roomRepository.findByRoomID(roomID).get(0);
        List<UserEntity> currentUsers = room.getUsers();
        currentUsers.remove(user);
        room.setUsers(currentUsers);
        roomRepository.save(room);
        userRepository.delete(user);
        if(room.getUsers().size() == 0){
            roomRepository.delete(room);
        }
    }

    public List<UserEntity> getUsers(RoomEntity roomEntity){
        return roomEntity.getUsers();
    }

    public int newRoom(RoomEntity roomEntity){
        roomRepository.save(roomEntity);
        return roomEntity.getRoomID();

    }

    public void removeRoom(RoomEntity roomEntity){
        roomRepository.delete(roomEntity);
    }

    public RoomEntity getRoom(int RoomID){
        return roomRepository.findByRoomID(RoomID).get(0);
    }

    public List<RoomEntity> getRooms(){
        List<RoomEntity> roomEntities = new ArrayList<>();
        roomRepository.findAll().forEach(roomEntities::add);
        System.out.println("WTF is going oon");
        return roomEntities;
    }

    public List<RoomEntity> getRooms(int fromIndex, int toIndex){
        toIndex = toIndex + 1;
        List<RoomEntity> rooms = getRooms();
        if(fromIndex> toIndex){
            return new ArrayList<>();
        }
        if(toIndex > rooms.size()){
            toIndex = rooms.size();
        }
        return rooms.subList(fromIndex, toIndex);
    }

    public GameEntity startGame(RoomEntity roomEntity){
        //TODO: Create a new game from roomEntity data with running main/game
        return new GameEntity();
    }

}
