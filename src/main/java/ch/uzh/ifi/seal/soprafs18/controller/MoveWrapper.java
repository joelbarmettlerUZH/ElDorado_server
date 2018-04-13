package ch.uzh.ifi.seal.soprafs18.controller;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class MoveWrapper {
    public List<Card> cards;
    public Point point;
}
