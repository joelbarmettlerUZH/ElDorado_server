package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;

import java.awt.*;
import java.util.List;

public class MoveWrapper {
    public List<Card> cards;
    public HexSpace hexSpace;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public HexSpace getHexSpace() {
        return hexSpace;
    }

    public void setHexSpace(HexSpace hexSpace) {
        this.hexSpace = hexSpace;
    }
}
