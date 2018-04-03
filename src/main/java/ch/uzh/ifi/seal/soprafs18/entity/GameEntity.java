package ch.uzh.ifi.seal.soprafs18.entity;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME_ENTITY")
public class GameEntity {
    @Autowired
    PlayerRepository playerRepository;

    public GameEntity(Game game){
        this.game = game;
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
        return playerRepository.findByPlayerID(game.getCurrent().getPlayerID()).get(0);
    }
    public void setCurrentPlayer(PlayerEntity playerEntity){

    }



    @Column(name = "PLAYERS")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    public List<PlayerEntity> getPlayers(){
        List<PlayerEntity> players = new ArrayList<>();
        for(Player p:game.getPlayers()){
            players.add(playerRepository.findByPlayerID(p.getPlayerID()).get(0));
        }
        return players;
    }
    public void setPlayers(List<Player> players){

    }


    @Column(name = "RUNNING")
    public boolean getRunning(){
        return game.isRunning();
    }
    public void setRunning(boolean running){
        game.setRunning(running);
    }


    @Column(name = "WINNER")
    @ElementCollection
    public List<PlayerEntity> getWinner(){
        List<PlayerEntity> players = new ArrayList<>();
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
