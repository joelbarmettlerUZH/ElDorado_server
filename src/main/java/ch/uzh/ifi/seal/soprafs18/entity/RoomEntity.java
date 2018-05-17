package ch.uzh.ifi.seal.soprafs18.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.apache.tomcat.util.digester.ArrayStack;
import java.util.ArrayList;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ROOM")
public class RoomEntity  implements Serializable {

    public RoomEntity(String name){
        this.name = name;
    }

    public RoomEntity(){
        this.name = "testRoom";
        ArrayList<UserEntity> users = new ArrayList<UserEntity>();
        this.users = users;
    }

    @Id
    @GeneratedValue
    @Column(name = "ROOMID")
    private int roomID;

    @Column(name = "NAME", unique = true)
    private String name = "Unknown";

    @Column(name = "USERS")
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "roomEntity", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<UserEntity> users;

    @Column(name = "BOARDNUMBER")
    private int boardnumber;

    public void addUser(UserEntity userEntity){
        this.users.add(userEntity);
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public int getBoardnumber() {
        return boardnumber;
    }

    public void setBoardnumber(int boardnumber) {
        this.boardnumber = boardnumber;
    }
}
