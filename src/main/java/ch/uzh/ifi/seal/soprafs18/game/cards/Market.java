package ch.uzh.ifi.seal.soprafs18.game.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Embeddable
@Data
public class Market implements Serializable {

    public Market(){
        active = new ArrayList<>();
        active.add(
                new Slot(
                    new MovingCard("ActiveMoving", 1, 2)
                )
        );
        passive = new ArrayList<>();
        passive.add(
                new Slot(
                        new ActionCard("PassiveAction", 3, 4)
                )
        );
    }

    /*
    Slot with the active cards. These are always purchasable by the user.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Slot> active;

    /*
    Slot of passive cards. These cards are only purchasable if the active ArrayList has less than 6 Slots.
    Otherwise, these cards are not purchasable.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Slot> passive;

    /*
    Removes one Card from the Slot and returns it to the user. A buy order is only valid if the Slot is part of the Active
    Slots or the Active Slots have less than 6 Slots inside. In this case, the Slot from the Passive Slots is moved into
    the active Slot. If The Slot was an active Slot and only one Card was left, the Slot gets removed entirely. The player
    then puts the card on the discard pile using Card.discard()
     */
    public Card buy(Slot slot) {

        Card tmp = slot.buy();

        if (passive.contains(slot) && active.size() < 6) {
            active.add(slot);
            passive.remove(slot);
            /* This statement must stay since it is possible to reduce a slot with stealing to 1 */
            if (slot.getPile().size() == 0) {
                active.remove(slot);
            }
            return tmp;

        } else if (active.contains(slot)) {
            if (slot.getPile().size() == 0) {
                active.remove(slot);
            }
            return tmp;

        } else {
            return null;
        }
    }

    /*
    Returns either the active Slots when size of active slots is 6, active and passive slots otherwise.
     */
    public List<Slot> getPurchasable() {
        if (active.size() <= 6) {
            return active;
        } else {
            List<Slot> both = new ArrayList<Slot>();
            both.addAll(active);
            both.addAll(passive);
            return both;
        }
    }

    /*
    Returns the active slots.
     */
    public List<Slot> getActive() {
        return active;
    }

    /*
    Returns the passive slots.
     */
    public List<Slot> getPassive() {
        return passive;
    }

    /*
    Lets the user take one card from active and passive slots. If the slot becomes empty, removes that slot from the active Slots.
     */
    public Card stealCard(Slot slot) {

        Card tmp = slot.buy();

        if (active.contains(slot) && slot.getPile().size() == 0) {
            active.remove(slot);
        }

        if (passive.contains(slot) && slot.getPile().size() == 0) {
            passive.remove(slot);
        }

        return tmp;
    }
}
