package ch.uzh.ifi.seal.soprafs18.game.cards;

import java.util.List;

public class Market {

    /*
    Slot with the active cards. These are always purchasable by the user.
     */
    private List<Slot> activeSlots;

    /*
    Slot of passive cards. These cards are only purchasable if the active ArrayList has less than 6 Slots. Otherwise,
    these cards are not purchasable.
     */
    private  List<Slot> passiveSlots;

    /*
    Removes one Card from the Slot and returns it to the user.
    A buy order is only valid if the Slot is part of the Active Slots or the Active Slots have less than 6 Slots inside.
    In this case, the Slot from the Passive Slots is moved into the active Slot.
    If The Slot was an active Slot and only one Card was left, the Slot gets removed entirely.
    The player then puts the card on the discard pile using Card.discard()
     */
    public Card buy(Slot slot){return null;}

    /*
    Returns either the active Slots when size of active slots is 6, active and passive slots otherwise.
     */
    public List<Slot> getPurchasables(){return null;}

    /*
    Lets the user take one card from active and passive slots. If the slot becomes empty,
    removes that slot from the active Slots.
     */
    public void stealCard(Slot slot){}
}
