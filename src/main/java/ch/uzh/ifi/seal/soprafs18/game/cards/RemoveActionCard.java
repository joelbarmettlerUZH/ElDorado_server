package ch.uzh.ifi.seal.soprafs18.game.cards;

public class RemoveActionCard extends ActionCard {
    /*
   Calls the parents performAction to use Player.remove(Card) instead of Player.discard(Card).
     */
    @Override
    public SpecialActions performAction(){
        super.performAction();
        return null;
    }
}
