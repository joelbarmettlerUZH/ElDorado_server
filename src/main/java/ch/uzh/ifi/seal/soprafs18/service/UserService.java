package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
//import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserEntity createUser(UserEntity user){
        //user.setToken(RandomString.make(16));
        userRepository.save(user);
        return user;
    }

    public void deleteUser(UserEntity user){
        userRepository.delete(user);
    }

}
