package ch.uzh.ifi.seal.soprafs18.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "ROOM")
public class RoomEntity  implements Serializable {

    public RoomEntity(String name){
        this.name = name;
    }

    public RoomEntity(){

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

}
