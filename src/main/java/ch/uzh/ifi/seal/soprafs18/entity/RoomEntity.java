package ch.uzh.ifi.seal.soprafs18.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROOM")
public class RoomEntity {
    @Id
    @GeneratedValue
    @Column(name = "ROOMID")
    private int roomID;

    @Column(name = "NAME")
    private String name = "Unknown";

    @Column(name = "USERS")
    @OneToMany(mappedBy = "room")
    private List<UserEntity> userEntities;

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

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public void addUser(UserEntity userEntity){
        this.userEntities.add(userEntity);
    }
}
