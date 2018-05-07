package ch.uzh.ifi.seal.soprafs18.game.player;

import ch.uzh.ifi.seal.soprafs18.game.cards.*;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.main.Blockade;
import ch.uzh.ifi.seal.soprafs18.game.main.Game;
import ch.uzh.ifi.seal.soprafs18.game.main.Memento;
import ch.uzh.ifi.seal.soprafs18.game.main.Pathfinder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Entity
@Data
@Table(name = "PLAYER_ENTITY")
public class Player implements Serializable {

    //TODO: Set correct  initial cardAction budget
    public Player(int PlayerID, String name, Game game, String token) {
        this();
        //this.token = token;
        this.name = name;
        this.playerId = PlayerID;
        this.board = game;

        //Already in this()
        this.history = new ArrayList<CardAction>();
        history.add(new CardAction("Testaction"));
    }

    public Player() {
        this.name = "Unknown";
        this.playerId = -1;
        this.playerId = -1;
        this.board = new Game();
        this.coins = (float) 0;
        this.playingPieces = new ArrayList<PlayingPiece>();
        this.specialAction = new SpecialActions(0, 0, 0);
        this.history = new ArrayList<CardAction>();
        history.add(new CardAction(new ActionCard("ActionCard_in_History", -11, -11, new SpecialActions(3, 3, 3)), "Testaction"));

        this.drawPile = new ArrayList<Card>();

        drawPile.add(new MovingCard("Matrose", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.RIVER, COLOR.ENDFIELDRIVER}));
        drawPile.add(new MovingCard("Matrose", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.RIVER, COLOR.ENDFIELDRIVER}));
        drawPile.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        drawPile.add(new ActionCard("Wissenschaftlerin", (float) 0.5, 4, new SpecialActions(1,1,0)));
        drawPile.add(new MovingCard("Entdecker", (float) 0.5, 3, 3, 99, new COLOR[]{COLOR.JUNGLE,COLOR.ENDFIELDJUNGLE}));
        drawPile.add(new MovingCard("Tausendsassa", 1, 2, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.SAND, COLOR.RIVER,COLOR.ENDFIELDJUNGLE,COLOR.ENDFIELDRIVER}));
        drawPile.add(new RemoveActionCard("Fernsprechgerät", (float) 0.5, 4, new SpecialActions(0,0,1) ));

        drawPile.add(new MovingCard("Millionärin", 4, 5, 4, 99, new COLOR[]{COLOR.SAND}));


        /* ---------ORIGINAL_DECK--------------
        drawPile.add(new MovingCard("Matrose", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.RIVER, COLOR.ENDFIELDRIVER}));
        drawPile.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));
        drawPile.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));
        drawPile.add(new MovingCard("Forscher", (float) 0.5, 0, 1, 99, new COLOR[]{COLOR.JUNGLE, COLOR.ENDFIELDJUNGLE}));

        drawPile.add(new MovingCard("Reisende", 1, 0, 1, 99, new COLOR[]{COLOR.SAND}));
        drawPile.add(new MovingCard("Reisende", 1, 0, 1, 99, new COLOR[]{COLOR.SAND}));
        drawPile.add(new MovingCard("Reisende", 1, 0, 1, 99, new COLOR[]{COLOR.SAND}));
        drawPile.add(new MovingCard("Reisende", 1, 0, 1, 99, new COLOR[]{COLOR.SAND}));

        */
        Collections.shuffle(drawPile);

        this.handPile = new ArrayList<Card>();
        // Why adding a card? Had to comment it for testing purposes.
        // handPile.add(new RemoveMoveSellCard("MovingCard", -4, -5, -6, -7, new COLOR[]{COLOR.RIVER}));
        this.discardPile = new ArrayList<Card>();
        // Why is this?
        // discardPile.add(new ActionCard("ActionCard", -12, -12, new SpecialActions(-4, -2, -0)));
        this.bought = false;
        this.token = "TESTTOKEN";
        this.removableBlockades = new ArrayList<>();
        this.collectedBlockades = new ArrayList<>();
        this.draw(4);
    }

    /*
    Globally unique ID
     */
    @Id
    @Column(name = "GLOBAL_PLAYERID")
    private int playerId;

    /*
    Character number
     */
    private int characterNumber;

    /*
    Players  name, set by the User. Has to be unique in the Game
     */
    private String name;

    /*
    Unique token to identify a Payer
     */
    private String token;

    /*
    Blockades that can be removes
     */
    @ElementCollection
    private List<Integer> removableBlockades;

    /*
    Blockades the user has removed already
     */
    @ElementCollection
    private List<Integer> collectedBlockades;

    /*
    Global unique token that identifies a User to its player.
    The token is communicated via SSL and randomized. For each game changing move,
    the user has to validate itself with this token in order to perform the move.
     */

    /*
    Number of coins the Player has in his wallet. Is reset to 0 when he ends his round or bought one card.
     */
    private Float coins;

    /*
    Instance of Game on which the Player is performing his action.
     */
    @ManyToOne
    @JsonBackReference
    private Game board;

    /*
    Instance of PATHFINDER the player uses to find the possible paths.
     */

    /*
    List of playing pieces the player controls.
     */
    @Embedded
    @ElementCollection
    private List<PlayingPiece> playingPieces;

    /*
    List of blockades the Player has collected so far.

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)

    */
    //@Embedded
    //@ElementCollection
    //@ManyToMany
    //private List<Blockade> blockades;

    /*
    The budget the user has for the current round.
    Is set from the action cards and reset either at the end of the game or
    value-by-value each time the corresponding method (draw, remove, stealAction) is called.
     */
    @Embedded
    private SpecialActions specialAction;

    /*
    Each time the user plays a Card of any type, its history is appended with the corresponding CardAction.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CardAction> history;

    /*
    List of cards the user has in his drawPile.
     */
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Card> drawPile;

    /*
    List of cards the user has in his handPile.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Card> handPile;

    /*
    List of cards the user has in his discardPile.
     */
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Card> discardPile;

    /*
    Indicates whether the user has already bought a Card in the current round.
     */
    private Boolean bought;

    public boolean myTurn(){
        if(!(board.getCurrent().getPlayerId() == this.getPlayerId())){
            System.out.println("Not the players turn!");
            return false;
        }
        return true;
    }
    /*
    Checks in the memento whether cards corresponds to SelectedCards,
    placingPiece to playingPiece and if to-HexSpace is in reachables.
    If so, the players PlayingPiece is moved to the to-HexSpace location.
    If in the history of the to-HexSpaces previous an active barricadeSpace is located,
    deactive is called on the corresponding blockade and the blockade is added to the users barricades-array.
    After a move is done, call cards.move on all cards in the Cards array.
    Finally, we check whether a deactivated barricade is now next to the to-HexSpaces neighbours.
    If this is the case, check whether the barricade is of the same type as the to-HexSpace and if the difference
    between the cards value and the to-HexSpaces minimalCosts allows the removal of the barricade.
    The user can then decide whether he wants to take that barricade or not.
    When the move is done, the Player checks whether his PlayingPiece stands on a HexSpace of colour ElDoardo.
    If this is the case, he adds himself to the Games winning Player array.
     */
    public List<Blockade> move(PlayingPiece playingPiece, List<Card> cards, HexSpace moveTo) {
        // is it the players turn
        if(!myTurn()){
            return new ArrayList<>();
        }
        // are the cards in the hand
        for(Card card: cards){
            if(!this.handPile.contains(card)){
                return new ArrayList<>();
            }
        }
        // does the player own this playingpiece
        if(!this.playingPieces.contains(playingPiece)){
            return new ArrayList<>();
        }
        this.removableBlockades = new ArrayList<>(); // parentblockadesID the player can remove in this turn
        Memento memento = board.getMemento();
        // validates if cards and playingpieces are the same for wich the pathfinder was exectued, if not redo pathfinder
        if (!(playingPiece == memento.getPlayingPiece() && cards.equals(memento.getSelectedCards()))) {
           Pathfinder.getWay(board,cards,playingPiece);
        }
        Set<HexSpace> reachables = new HashSet<>(memento.getReachables());
        if (reachables.contains(moveTo)){
            HexSpace oldPosition = playingPiece.getStandsOn();
            playingPiece.setStandsOn(moveTo);
            for(Card card: cards){
                card.moveAction(this, moveTo); // for history
            }
            Set<Integer> setOfRemovableBlockades = new HashSet<>(); // used to make sure that we don't remove the same blockade twice. (thanks hibernate!!!)
            for(HexSpace hexSpace: moveTo.getPrevious()){
                if(hexSpace.getClass() == BlockadeSpace.class){
                    // if you directly move over blockade
                    autoRemoveBlockade(((BlockadeSpace) hexSpace).getParentBlockade());
                }
            }
            for(HexSpace neighbour: playingPiece.getStandsOn().getNeighbour(board)){
                // if the playingpiece ends up next to a blockade after a move
                if(neighbour.getClass() == BlockadeSpace.class && neighbour.getStrength() != 0){
                    Card card = cards.get(0);
                    if(cards.size()==1 && card.getClass() == MovingCard.class){ //single card case
                        if(((MovingCard) card).getColors().contains(neighbour.getColor())
                                && ((MovingCard) card).getStrength() - moveTo.getMinimalCost() >= neighbour.getStrength()
                                && ((MovingCard) card).getDepth() - moveTo.getMinimalDepth() > 0){
                            if(!(((MovingCard) card).getColors().size() > 1) || (neighbour.getColor() == moveTo.getColor()) || oldPosition == moveTo){
                                // Not multicolord card, or blockade same color as color used for moving or moved on itself
                                setOfRemovableBlockades.add(((BlockadeSpace) neighbour).getParentBlockade());
                            }
                        }
                    }else if(oldPosition == playingPiece.getStandsOn()){ //multicard case
                        if(neighbour.getColor() == COLOR.RUBBLE && neighbour.getStrength() == cards.size()){
                            setOfRemovableBlockades.add(((BlockadeSpace) neighbour).getParentBlockade());
                        }
                    }
                }
            }
            this.removableBlockades = new ArrayList<>(setOfRemovableBlockades); //convert set to list
        }
        if(playingPiece.getStandsOn().getColor() == COLOR.ENDFIELDJUNGLE ||
                playingPiece.getStandsOn().getColor() == COLOR.ENDFIELDRIVER ){
            playingPiece.move(board.getElDoradoSpaces().get(0));
            List<HexSpace> newEldoradoSpaces = board.getElDoradoSpaces().subList(0,board.getElDoradoSpaces().size()-2);
            board.setElDoradoSpaces(newEldoradoSpaces);
            this.board.getWinners().add(this);
        }
        return new ArrayList<>(blockadeIdsToBlockades(this.removableBlockades));
    }

    private List<Blockade> blockadeIdsToBlockades(List<Integer> removableBlockadeIds) {
        List<Blockade> removableBlockades = new ArrayList<>();
        for(int parentId : removableBlockadeIds){
            for(Blockade blockade : board.getBlockades()){
                if(blockade.getBlockadeId() == parentId){
                    removableBlockades.add(blockade);
                }
            }
        }
        return removableBlockades;
    }

    public void removeBlockade(Blockade blockade){
        if(!myTurn()){
            return;
        }
        if(this.removableBlockades.contains(blockade.getBlockadeId()) && board.getBlockades().contains(blockade)){
            blockade.deactivate();
            this.collectedBlockades.add(blockade.getCost());
            this.removableBlockades.remove(new Integer(blockade.getBlockadeId()));
        }
    }

    public void autoRemoveBlockade(int blockadeId){
        this.removableBlockades.add(blockadeId); // adds to removable so the removeBlockade can remove it
        Blockade foundBlockade = null;
        for(Blockade blockade: board.getBlockades()) {
            if (blockade.getBlockadeId() == blockadeId) {
                foundBlockade = blockade;
            }
        }
        removeBlockade(foundBlockade);
    }

    /*
    Calls action on the corresponding card and sets the returned SpecialAction to its own budget.
    Adds instance of CardAction with dedicated name to the history array. It returns the budget back to the Frontend.
     */

    public void action(ActionCard card) {
        if(!myTurn()){
            return;
        }
        if (handPile.contains(card)) {
            specialAction = card.performAction(this);
            while (specialAction.getDraw() > 0){
                this.draw(1);
                specialAction.reduceDraw();
            }
            CardAction cardAct = new CardAction(card, "Play: " + card.getName());
            history.add(cardAct);
        }
    }

    /*
    Moves the corresponding Card from the handPile to the discardPile.
    Adds instance of CardAction with dedicated name to the history array.
     */
    public void discard(Card card) {
        if(!myTurn()){
            return;
        }
        CardAction cardAct = new CardAction(card, "Discard: " + card.getName());
        history.add(cardAct);

        if (handPile.contains(card)) {
            discardPile.add(card);
            handPile.remove(card);
        } else {
            discardPile.add(card);
        }
    }

    /*
    calls Card.sell(self: Player)
     */
    public void sell(Card card) {
        if(!myTurn()){
            return;
        }
        if (handPile.contains(card)) {
            history.add(new CardAction(card, "Sell: " + card.getName()));
            card.sellAction(this);
        }
    }

    /*
    Calls buy on the market and adds the returned card to the discardPile if the user has the coins to do so and
    not yet bought anything. Adds instance of CardAction with dedicated name to the history array.
     */
    public void buy(Slot slot) {
        if (slot.getPile().size() != 0) {
            if (!myTurn() || bought) {
                return;
            }

            history.add(new CardAction(slot.getCard(), "Sell: " + slot.getCard().getName()));

            if (slot.getCard().getCoinCost() <= coins && !bought) {
                Card card = slot.buy();
                this.discard(card);
                this.coins = 0.0f;
                bought = true;
            }
        } else {
            return;
        }
    }

    /*
    Calls draw(amount) with the amount being 4 - length of HandPile.
     */
    public void draw() {
        if(!myTurn()){
            return;
        }
        draw(4 - handPile.size());
    }

    /*
    Takes amount-cards from the drawpile, regardless of how many cards there are in the Handpile.
    If the drawPile is empty, the discardPiles order is randomized and all cards are
    moved from the discardPile to the drawPile.
     */
    public void draw(Integer amount) {
        int amountTmp = amount;
        CardAction cardAct = new CardAction("Draw " + amountTmp + " cards.");
        while (drawPile.size() > 0 && amount > 0) {
            cardAct.addCard(this.drawPile.get(0));
            handPile.add(drawPile.remove(0));
            amount--;
        }

        history.add(cardAct);
        Random rand = new Random();
        if (drawPile.size() < 1 && amount != 0) {
            for (int i = discardPile.size(); i > 0; i--) {
                int rnd = rand.nextInt(discardPile.size());
                drawPile.add(discardPile.remove(rnd));
                System.out.println("balbab");
            }
            draw(amount);
        }
    }

    public void drawAction(){
        if(specialAction.getDraw() > 0 && myTurn() && drawPile.size() > 0){
            this.draw(1);
            specialAction.reduceDraw();
        }
    }

    /*
    Calls market.stealAction and adds the returned card to the discardpile. Does neither take the amount of
    coins nor the bought-boolean into consideration.
     */
    public void stealAction(Slot slot) {
        if(!myTurn()){
            return;
        }
        if (specialAction.getSteal() > 0) {
            history.add(new CardAction(slot.getCard(), "Steal: " + slot.getCard().getName()));
            discardPile.add(slot.buy());
            specialAction.reduceSteal();
        }
    }

    public void removeAction(Card card){
        if(specialAction.getRemove() > 0 && myTurn()){
            this.remove(card);
            specialAction.reduceRemove();
        }
    }

    /*
    Moves the card from the handPile to the removePile.
     */
    public void remove(Card card) {
        if(!myTurn()){
            return;
        }
        if (handPile.contains(card)) {
            history.add(new CardAction(card, "Remove: " + card.getName()));
            handPile.remove(card);
        }
    }

    /*
    Calls draw() and resets the coins to 0 and the bought-boolean to False.
     */
    public void endRound() {
        if(!myTurn()){
            return;
        }
        if(this.board.getWinners().contains(this.board.getPlayers().get(((this.board.getCurrentPlayerNumber()+1)%this.board.getPlayers().size())))){
            this.board.setRunning(false);
        }
        // currentPlayerNumber = (currentPlayerNumber + 1) % players.size();
        // current = players.get(currentPlayerNumber);
        draw();
        this.removableBlockades = new ArrayList<>();
        coins = (float) 0;
        bought = false;
        specialAction.setDraw(0);
        specialAction.setRemove(0);
        specialAction.setSteal(0);
        board.endRound();
        history = new ArrayList<>();
    }

    public void addCoins(Float amount) {
        coins = coins + amount;
    }

    public void addPlayingPiece(PlayingPiece playingPiece) {
        this.playingPieces.add(playingPiece);
    }

    public void resetSpacialActions(){
        this.specialAction = new SpecialActions(0,0,0);
    }
}
