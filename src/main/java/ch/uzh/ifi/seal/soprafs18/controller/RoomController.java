package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.entity.RoomEntity;
import ch.uzh.ifi.seal.soprafs18.entity.UserEntity;
import ch.uzh.ifi.seal.soprafs18.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class RoomController  implements Serializable {
    private final String context = CONSTANTS.APICONTEXT + "/Room";

    @Autowired
    private RoomService roomService;


    //Gets all rooms between indices From-To
    @GetMapping(value = context)
    @ResponseStatus(HttpStatus.OK)
    public List<RoomEntity> getRooms(@RequestParam(required = false, defaultValue = "0") String from,
                                     @RequestParam(required = false, defaultValue = "5") String to){
        return roomService.getRooms(Integer.parseInt(from),Integer.parseInt(to));
    }

    //Creates new empty room
    @PostMapping(value = context)
    @ResponseStatus(HttpStatus.CREATED)
    public RoomEntity createRoom(@RequestBody RoomEntity roomEntity){
        return roomService.newRoom(roomEntity);
    }

    //Gets room by its ID
    @GetMapping(value = context+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomEntity getRoom(@PathVariable int id){
        return roomService.getRoom(id);
    }

    //Joins a player to a room
    @PutMapping(value = context+"/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomEntity joinUser(@PathVariable int id, @RequestBody UserEntity userEntity, @RequestParam("token") String token){
        return roomService.joinUser(id, userEntity, token);
    }

}
