package ch.uzh.ifi.seal.soprafs18.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME_ENTITY")
public class GameEntity {

    @Autowired
    PlayerRepository playerRepository;

    public GameEntity(Game game, String name){
        this.game = game;
        this.name = name;
    }

    public GameEntity(){

    }

    private int gameID;

    @Id
    @GeneratedValue
    @Column(name = "GAMEID")
    public int getGameID(){
        return gameID;
    }
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    private String name;
    @Column(name = "NAME")
    public String getName(){
        return this.name;
    }
    public void setName(String name){

    }

    protected Game game;
    @Transient
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }


    @OneToOne
    public PlayerEntity getCurrentPlayer(){
        Player current = game.getCurrent();
        System.out.println(playerRepository.findAll().iterator().next().getPlayerID());
        return playerRepository.findByPlayerID(current.getPlayerID()).get(0);
    }
    public void setCurrentPlayer(PlayerEntity playerEntity){
    }


    private List<PlayerEntity> players;
    @Column(name = "PLAYERS")
    @OneToMany(mappedBy = "game")
    @JsonManagedReference
    public List<PlayerEntity> getPlayers(){
        return players;
    }
    public void setPlayers(List<PlayerEntity> players){
        this.players = players;
    }


    @Column(name = "RUNNING")
    public boolean getRunning(){
        return game.isRunning();
    }
    public void setRunning(boolean running){
        //game.setRunning(running);
    }



    @Column(name = "WINNER")
    @ElementCollection
    public List<PlayerEntity> getWinner(){
        List<PlayerEntity> players = new ArrayList<>();
        List<Player> winners = game.getWinners();
        if(winners == null){
            return null;
        }
        for(Player p:game.getWinners()){
            players.add(playerRepository.findByPlayerID(p.getPlayerID()).get(0));
        }
        return players;
    }
    public void setWinner(List<PlayerEntity> winners){

    }

    private int boardID;
    @Column(name = "BOARDID")
    public int getBoardID(){
        return boardID;
    }
    public void setBoardID(int boardID){
        this.boardID = boardID;
    }


    @Transient
    public HexSpace[][] getBoard(){
        return game.getPathMatrix();
    }
    public void setBoard(HexSpace[][] board){

    }

    @Transient
    @Column(name = "BLOCKADE")
    @ElementCollection
    public List<Blockade> getBlockade(){
        return game.getBlockades();
    }
    public void setBlockade(List<Blockade> blockade){

    }


}
