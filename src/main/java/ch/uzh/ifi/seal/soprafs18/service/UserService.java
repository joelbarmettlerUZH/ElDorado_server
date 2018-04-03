package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean valid(String token, UserEntity user){
        UserEntity u = userRepository.findByName(user.getName()).get(0);
        return token.equals(u.getToken());
    }

    public static boolean valid(String token, UserEntity user, UserRepository userRepository){
        UserEntity u = userRepository.findByName(user.getName()).get(0);
        return token.equals(u.getToken());
    }

    public List<String> createUser(UserEntity user){
        String token = "TESTTOKEN";
        user.setToken(token);
        userRepository.save(user);
        List<String> l = new ArrayList<>();
        l.add(token);
        l.add(String.valueOf(user.getUserID()));
        return l;
    }

    public void deleteUser(UserEntity user, String token){
        if(valid(token, user)){
            userRepository.delete(userRepository.findByName(user.getName()).get(0));
        }
    }

    public List<UserEntity> getAll(){
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public UserEntity getByID(int id){
        return userRepository.findByUserID(id).get(0);

    }

    public UserEntity updateUser(UserEntity user, String token){

        UserEntity u = userRepository.findByUserID(user.getUserID()).get(0);
        u.setCharacter(user.getCharacter());
        u.setName(user.getName());
        u.setReady(user.isReady());
        u.setRoomEntity(user.getRoomEntity());
        userRepository.save(u);
        return u;
    }
}
