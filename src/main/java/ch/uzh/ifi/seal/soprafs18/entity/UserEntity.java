package ch.uzh.ifi.seal.soprafs18.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class UserEntity {

    public UserEntity(String name, int character, RoomEntity roomEntity){
        this.name = name;
        this.character = character;
        this.roomEntity = roomEntity;
        this.ready = false;
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

    @Column(name = "NAME", unique = true)
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
