package ch.uzh.ifi.seal.soprafs18.game.main;

import ch.uzh.ifi.seal.soprafs18.game.cards.Card;
import ch.uzh.ifi.seal.soprafs18.game.cards.MovingCard;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.BlockadeSpace;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.COLOR;
import ch.uzh.ifi.seal.soprafs18.game.hexspace.HexSpace;
import ch.uzh.ifi.seal.soprafs18.game.player.Player;
import ch.uzh.ifi.seal.soprafs18.game.player.PlayingPiece;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pathfinder  implements Serializable {

    /*
    The PlayingPiece has a HexSpaceEntity. This hexSpace is the first entry in the reachables array,
    because the player can obviously reach its own location. From there on, the neighbours of all
    reachables are checked whether they are reachable with the given set of cards.The PathFinder
    differentiates between two cases: When the Cards-array consists of just one entry of type MovingCard,
    he looks what colours the selected Card supports. Then, for each of the X different colours, he looks
    what HexSpaces are reachables with just using this colour by always adding the reachable neighbours to
    an array of colourspecific reachables an, in the same time, sets the previous path of how he got to the
    exSpace (the path he came from, so the path from the neighboured hexSpace + the neighbour itself, including
    barricadeSpaces) the cost that were needed to enter the field (the cards strengthLeft value - the fields stregth)
    and the depthLeft value, which is just the depth from the neighbour - 1. The PathFinder keeps track of which
    HexSpaces he found the best way already with a local variable currentIndex. CurrentIndex marks a position in the Array
    of reachable HexSpaces. To the left of the currentIndex, there are the HexSpaces that were already processed, e.g. of
    which the neighbours were already checked to be reachable. To the right of the currentIndex, there are the HexSpaces of
    which not all neighbours were yet checked. Whenever the pathFinder checks a HexSpac at currentIndex, it appends all its
    neighbours (if not yet in the array) to the reachables. When an array is already in the reachables, it checks whether
    it would now be reached with a cheaper path. If so, the corresponding HexSpaceEntity is moved from the left of the reachables
    to the end (because now we have to see whether one of its neighbours can be reached cheaper as well). PathFinder
    finishes when the currentIndex equals the length of the Array reachables. In the end, he is left with X arrays, each
    contining the reachable HexSpaces with using the Xth colour. The union of these arrays then is the returned
    value for reachables.

    -If multiple cards were selected or just one card of type actionCard, PathFinder checks whether one of the neighbours
    -is of color “rubble” and only allows the move if a rubble with strenght less than the number of cards selected is in the set of neighbours.

    -When the PathFinder finished calculating the reachables, he sets the memento by setting the mementos cards and the
    -references to all reachables. In the next call of the PathFinder, he will loop over all the HexSpaceEntity references saved
    -inside the Memento and resets their states like previous and minialCost, minimalDepth so that he will not have false
    -leftover information from the previous call.

    -Before the PathFinder returns the reachables, all HexSpaces of type Blockade are filtered out of it so that the user
    -can not move on Hexspace. Note that the Blockades to not disapear from the previous history of the individual reachable
    -HexSpaces.
    */
    public static List<HexSpace> getWay(Game game, List<Card> inputCards, PlayingPiece playingPiece){
        /*
        When the PathFinder finished calculating the reachables, he sets the memento by setting the mementos cards and the
        references to all reachables. In the next call of the PathFinder, he will loop over all the HexSpaceEntity references saved
        inside the Memento and resets their states like previous and minialCost, minimalDepth so that he will not have false
        leftover information from the previous call.
         */
        Set<Card> cards = new HashSet<>(inputCards);
        game.getMemento().reset(game);
        Set<HexSpace> reachables;
        HexSpace hexSpace = playingPiece.getStandsOn();
        hexSpace.setMinimalDepth(0);
        hexSpace.setMinimalCost(0);

        if(cards.size()==1){
            reachables = singlecardCase(game, cards, hexSpace);
        }
        else{
            reachables = multicardCase(game, cards, hexSpace);
        }

        /*
        Before the PathFinder returns the reachables, all HexSpaces of type Blockade are filtered out of it so that the user
        can not move on Hexspace. Note that the Blockades to not disapear from the previous history of the individual reachable
        HexSpaces.
        */
        for (HexSpace reach: reachables){
            System.out.println("Reachable: " + reach.toString());
        }
        Set<HexSpace> reachable = new HashSet<>(filterBlockades(reachables,game));
        reachable.add(hexSpace);
        setMemento(game, reachable, cards, playingPiece);
        for (HexSpace reach: reachable){
            System.out.println("Reachable: " + reach.toString());
        }
        return new ArrayList<>(reachable);
    }

    private static Set<HexSpace> singlecardCase(Game game, Set<Card> cards, HexSpace hexSpace){
        Set<HexSpace> reachables = new HashSet<>();
        Card card = cards.iterator().next();
        if(card instanceof MovingCard && cards.size()==1){
            for(COLOR color: ((MovingCard) card).getColors()){
                hexSpace.setMinimalDepth(0);
                hexSpace.setMinimalCost(0);
                reachables.addAll(findReachables(game, color, ((MovingCard) card).getStrength(), ((MovingCard) card).getDepth(), hexSpace));
                // Added this to reset everything before trying with different color

                game.getMemento().reset(game);

            }
        }

        //Check rubble as well
        reachables.addAll(multicardCase(game, new HashSet<>(cards), hexSpace));
        return reachables;
    }

    private static Set<HexSpace> findReachables(Game game, COLOR color, int strength, int depth, HexSpace hexSpace){
        List<HexSpace> reachables = new ArrayList<>();
        reachables.add(hexSpace);
        int currentPosition = 0;
        do{
            HexSpace current = reachables.get(currentPosition);
            System.out.println("Current: "+current.toString());
            List<HexSpace> potentialNeighbours = reachables.get(currentPosition).getNeighbour(game);
            for(HexSpace tempneighbour: potentialNeighbours){
                HexSpace neighbour = (HexSpace) tempneighbour;
                System.out.println("Neighbour: "+neighbour.toString());
                System.out.println("Neighbour strength: "+neighbour.getStrength());
                if((neighbour.getColor() == color || neighbour.getStrength()==0)
                        &&
                        (strength - current.getMinimalCost() - neighbour.getStrength() >= 0 || neighbour.getStrength()==0)
                        &&
                        (depth - current.getMinimalDepth() - 1 >= 0 || neighbour.getStrength()==0 )){
                    System.out.println("validito");
                    System.out.println("!reachables.contains(neighbour): "+!reachables.contains(neighbour));
                    System.out.println("neighbour.getMinimalCost() > current.getMinimalCost() + neighbour.getStrength(): "+ (neighbour.getMinimalCost() > current.getMinimalCost() + neighbour.getStrength()));
                    if((!reachables.contains(neighbour) || neighbour.getMinimalCost() > current.getMinimalCost() + neighbour.getStrength())){
                        neighbour.setMinimalCost(current.getMinimalCost() + neighbour.getStrength());
                        int depthToSubstract = 1;
                        if (neighbour.getClass() == BlockadeSpace.class){
                            depthToSubstract = 0;
                        }
                        neighbour.setMinimalDepth(current.getMinimalDepth()+depthToSubstract);
                        ArrayList<HexSpace> previous = new ArrayList<>(current.getPrevious());
                        previous.add(current);
                        neighbour.setPrevious(previous);
                        if((reachables.contains(neighbour))&&
                                reachables.get(reachables.indexOf(neighbour)).getPoint()==neighbour.getPoint()){ //double check
                            if(reachables.indexOf(neighbour) < currentPosition){
                                currentPosition--;
                                System.out.println("reducing current Position to "+currentPosition);
                                System.out.println("rechables Size is "+reachables.size());
                            }
                            reachables.remove((HexSpace) neighbour);
                            System.out.println("removed from reachables");
                        }
                        reachables.add((HexSpace) neighbour);
                        System.out.println("added to reachables");
                        System.out.println("current Position to "+currentPosition);
                        System.out.println("rechables Size is "+reachables.size());
                    }
                }
            }
            reachables.forEach(x-> System.out.println("current reachables (before moving position: "+x.toString()));
            currentPosition++;
        }while(currentPosition<reachables.size());
        System.out.println("dijkstrato finitototo");
        return new HashSet<>(reachables);
    }

    private static Set<HexSpace> multicardCase(Game game, Set<Card> cards, HexSpace hexSpace){
        /*
        If multiple cards were selected or just one card of type actionCard, PathFinder checks whether one of the neighbours
        is of color “rubble” and only allows the move if a rubble with strenght less than the number of cards selected is in the set of neighbours.
         */
        Set<HexSpace> neighbours = new HashSet<>(hexSpace.getNeighbour(game));
        Set<HexSpace> reachables = new HashSet<>();
        reachables.add(hexSpace);
        for(HexSpace neighbour: neighbours){
            if(neighbour.getColor().equals(COLOR.RUBBLE) && neighbour.getStrength() == cards.size()){
                reachables.add(neighbour);
            }
            if(neighbour.getColor().equals(COLOR.BASECAMP) && neighbour.getStrength() == cards.size()){
                reachables.add(neighbour);
            }
        }
        return reachables;
    }


    private static Set<HexSpace> filterBlockades(Set<HexSpace> hexSpaces, Game game){
        /*
        Filter out all hexSpaces that are part of a blockade
         */
        List<HexSpace> toRemove = new ArrayList<>();
        for(HexSpace hexSpace: hexSpaces){
            if(hexSpace.getClass() == BlockadeSpace.class){
                toRemove.add(hexSpace);
            }
            for(Player player: game.getPlayers()){
                for(PlayingPiece playingPiece: player.getPlayingPieces()){
                    if(playingPiece.getStandsOn() == hexSpace){
                        toRemove.add(hexSpace);
                    }
                }
            }
        }
        hexSpaces.removeAll(toRemove);
        return hexSpaces;
    }

    private static void setMemento(Game game, Set<HexSpace> hexSpaces, Set<Card> cards, PlayingPiece playingPiece){
        Memento memento = new Memento(hexSpaces, cards, playingPiece);
        game.setMemento(memento);
    }

}
