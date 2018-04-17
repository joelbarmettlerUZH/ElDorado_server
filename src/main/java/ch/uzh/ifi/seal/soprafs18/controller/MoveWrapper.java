package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class MoveWrapper {
    public List<Card> cards;
    public HexSpace hexSpace;
}
