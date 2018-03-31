package ch.uzh.ifi.seal.soprafs18.game.cards;

public class RemoveMoveSellCard extends RemoveMoveCard {
    /*
    Overwrites the sellAction method that calls Player.remove(this) instead of Player.discard(this) when a Card is sold.
     */
    @Override
    public void sellAction(){
        super.sellAction();
    }


}
