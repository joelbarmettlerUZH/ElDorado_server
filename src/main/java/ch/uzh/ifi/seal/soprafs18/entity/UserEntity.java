package ch.uzh.ifi.seal.soprafs18.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER")
public class UserEntity  implements Serializable {

    public UserEntity(String name, int character, RoomEntity roomEntity){
        this.name = name;
        this.character = character;
        this.roomEntity = roomEntity;
        this.ready = false;
        this.token = "TESTTOKEN";
    }

    public UserEntity(String name, int character){
        this.name = name;
        this.character = character;
        this.roomEntity = null;
        this.ready = false;
        this.token = "TESTTOKEN";
    }

    public UserEntity(){
    }

    @Id
    @GeneratedValue
    @Column(name = "USERID")
    private int userID;

    @JsonIgnore
    @Column(name = "TOKEN")
    private String token;

    @Column(name = "NAME", unique = false)
    private String name;

    @Column(name = "CHARACTER")
    private int character;

    @Column(name = "READY")
    private boolean ready = false;

    @ManyToOne
    @JoinColumn(name="roomID")
    @JsonBackReference
    private RoomEntity roomEntity;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
