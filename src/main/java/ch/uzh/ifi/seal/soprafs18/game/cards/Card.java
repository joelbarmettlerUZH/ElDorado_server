package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovingCard.class, name = "MovingCard"),
        @JsonSubTypes.Type(value = RemoveMoveCard.class, name = "RemoveMoveCard"),
        @JsonSubTypes.Type(value = RemoveMoveSellCard.class, name = "RemoveMoveSellCard"),
        @JsonSubTypes.Type(value = RemoveActionCard.class, name = "RemoveActionCard"),
        @JsonSubTypes.Type(value = ActionCard.class, name = "ActionCard") }
)
public abstract class Card  implements Serializable, Cloneable {
    public Card(){
    }

    public Card(String name, float coinValue, int coinCost){
        this.name = name;
        this.coinValue = coinValue;
        this.coinCost = coinCost;
    }

    @Id
    @GeneratedValue
    private int id;
    /*
    Defines the cards name for identification in the frontend. Multiple cards can have the same name.
     */
    @Column(name = "CARDNAME")
    private String name;

    /*
    How many coins the user gets when selling the card
     */
    @Column(name = "COINVALUE")
    private float coinValue;

    /*
    How many coins the user needs to buy this card from the Market.
     */
    @Column(name = "COINCOST")
    private int coinCost;

    /*
     The cards sellAction method handles what happens when a card is sold. In the normal cases, sell will increase call
     Player.discard(Card), but in some cases a special Card will overwrite the sell method and call Player.remove(Card),
     making the card fall out of the game when being sold (treasury).
     */

    public void sellAction(Player player){
        if (player.getHandPile().contains(this)) {
            if(!player.getBought()){
                player.addCoins(coinValue);
            }
            player.discard(this);
        }
    }

    /*
    Abstract method to define action on move
     */

    public abstract void moveAction(Player player, HexSpace moveTo);

    public Object clone(){
        try{
            return super.clone();
        } catch(CloneNotSupportedException cns){
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCoinValue() {
        return coinValue;
    }

    public int getCoinCost() {
        return coinCost;
    }

}