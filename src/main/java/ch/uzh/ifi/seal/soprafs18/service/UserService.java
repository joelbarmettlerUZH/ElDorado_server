package ch.uzh.ifi.seal.soprafs18.service;

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
        String token = "TESTTOKEN";
        LOGGER.severe("Setting token of user " + user.getUserID() + " to " + token);
        user.setToken(token);
        userRepository.save(user);
        List<String> l = new ArrayList<>();
        l.add(token);
        l.add(String.valueOf(user.getUserID()));
        LOGGER.info("Created new user " + user.getUserID());
        return l;
    }

    public void deleteUser(UserEntity user, String token) {
        if (valid(token, user)) {
            LOGGER.warning("Deleting user " + user.getUserID());
            userRepository.delete(userRepository.findByUserID(user.getUserID()).get(0));
        }
        LOGGER.warning("User " + user.getUserID() + " provided wrong token " + token);
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

    public UserEntity updateUser(UserEntity user, String token) {
        if (!valid(token, user)) {
            LOGGER.warning("User " + user.getUserID() + " provided wrong token " + token);
            return null;
        }
        UserEntity u = userRepository.findByUserID(user.getUserID()).get(0);
        u.setCharacter(user.getCharacter());
        u.setName(user.getName());
        u.setReady(user.isReady());
        userRepository.save(u);
        LOGGER.info("Updating user " + user.getUserID());
        if (u.getRoomEntity() != null) {
            LOGGER.info("Updating room " + u.getRoomEntity().getRoomID() + " since user " + user.getUserID() + " was modified. ");
            roomService.updateRoom(u.getRoomEntity());
        }
        return u;
    }
}