package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.GameEntity;
import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public void joinUser(RoomEntity roomEntity, UserEntity userEntity){
        roomEntity.addUser(userEntity);
    }

    public void leaveUser(RoomEntity roomEntity, UserEntity userEntity){
        List<UserEntity> userEntities = roomEntity.getUserEntities();
        userEntities.remove(userEntity);
        roomEntity.setUserEntities(userEntities);
    }

    public List<UserEntity> getUsrs(RoomEntity roomEntity){
        return roomEntity.getUserEntities();
    }

    public void newRoom(RoomEntity roomEntity){
        roomRepository.save(roomEntity);
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
        return roomEntities;
    }

    public GameEntity startGame(RoomEntity roomEntity){
        //TODO: Create a new game from roomEntity data with running main/game
        return new GameEntity();
    }

}
