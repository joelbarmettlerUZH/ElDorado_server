package ch.uzh.ifi.seal.soprafs18.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "USER")
public class UserEntity  implements Serializable {

    public UserEntity(String name, int character, RoomEntity roomEntity){
        this.name = name;
        this.character = character;
        this.roomEntity = roomEntity;
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

}
