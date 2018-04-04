package ch.uzh.ifi.seal.soprafs18.game.cards;

import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;

public abstract class Card {
    /*
    Defines the cards name for identification in the frontend. Multiple cards can have the same name.
     */
    private String name;

    /*
    How many coins the user gets when selling the card
     */
    private float coinValue;

    /*
    How many coins the user needs to buy this card from the Market.
     */
    private int coinCost;

    /*
     The cards sellAction method handles what happens when a card is sold. In the normal cases, sell will increase call
     Player.discard(Card), but in some cases a special Card will overwrite the sell method and call Player.remove(Card),
     making the card fall out of the game when being sold (treasury).
     */
    public void sellAction(Player player){
        player.addCoins(coinValue);
        player.discard(this);
    }

    /*
    Abstract method to define action on move
     */

    public abstract void moveAction(Player player, HexSpace moveTo);

    public String getName() {
        return name;
    }

    public float getCoinValue() {
        return coinValue;
    }

    public int getCoinCost() {
        return coinCost;
    }
}
