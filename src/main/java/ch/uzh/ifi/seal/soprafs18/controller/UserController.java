package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs18.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
public class UserController  implements Serializable {
    private final String context = CONSTANTS.APICONTEXT + "/User";

    @Autowired
    private UserService userService;

    //get all users
    @GetMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> getUsers(){
        return userService.getAll();
    }

    //create new user
    @PostMapping(value = context)
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> createUser(@RequestBody UserEntity user){
        return userService.createUser(user);
    }

    //delete user
    @DeleteMapping(value = context)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@RequestBody UserEntity user, @RequestParam("token") String token){
        userService.deleteUser(user, token);
    }

    //get a specific user
    @GetMapping(value = context+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUser(@PathVariable int id){
        return userService.getByID(id);
    }

    //change a user
    @PutMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    public UserEntity changeUser(@RequestBody UserEntity user, @RequestParam("token") String token){
        return userService.updateUser(user, token);
    }

}
