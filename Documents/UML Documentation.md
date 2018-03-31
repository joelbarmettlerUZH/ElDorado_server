# UML Documentation

- [public class Assembler](#public-class-assembler)
- [public class Game](#public-class-game)
- [public class HexSpace](#public-class-hexspace)
- [public class PATHFINDER](#public-class-pathfinder)
- [public class Blockade](#public-class-blockade)
- [public class BlockadeSpace extends HexSpace](#public-class-blockade-space-extends-hexspace)
- [public abstract class Card](#public-abstract-class-card)
- [public class MovingCard extends Card](#public-class-movingcard-extends-card)
- [public class RemoveMoveCard extends MovingCard](#public-clas-removecard-extends-movingcard)
- [public class RemoveMoveSellCard extends MovingCard](#public-class-removemovesellcard-extends-movingcard)
- [public class ActionCard extends Card](#public-class-actioncard-extends-card)
- [public class RemoveActionCard](#public-class-removeactioncard)
- [public class Market](#public-class-market)
- [public class Slot](#public-class-slot)
- [public class Player](#public-class-player)
- [public class CardAction](#public-class-cardaction)
- [public class Actions](#public-class-actions)
- [public class Memento](#public-class-memento)
- [public class PlayerService](#public-class-playerservice)


## public class Assembler
======================

**-private DataBase DB**
Instance of Database containing all the Game

**+assembleBoard(int boardNumber): Matrix<HexSpace>**
The assembleBoard creates a Matrix consisting of all the elements from the Game with ID = boardNumber and returns the matrix with the well prepared Game. The assembler starts with a matrix consisting of only HexSpaces with infinite cost and colour EMPTY and replace them with the right HexSpaces according to the values he reads out of the Database.

**+getBockades(boardID: int): ArrayList<Array<Point>>**
Used by the GameBorad and returns an ArrayList of Arrays with the coordinates of the blockades in the pathMatrix. This is needed so that the Game can assign blockade instances to them. We consider this more efficient than parsing the pathMatrix, since the assembler has the information abouts these positions already.

**+getStartingFields(boardID: int): ArrayList<HexSpace>**
Used by the GameBorad and returns an Arrays with the HexSpaces of the starting-fields. The Game needs these information to place the playing Pieces. We rather request these informations from the assembler than parsing the matrix.

**+getEndingFields(boardID: int): ArrayList<HexSpace>**
Used by the GameBorad and returns an Arrays with the HexSpaces of the ending-fields. The Game needs these information to place the playing Pieces. We rather request these informations from the Assembler than parsing the matrix.

* * *

## public class Game
=================

**-private current: Player**
Player that can currently play the round. When one player calls endRound, the turn of the next player starts. The next player is always the one with either the next bigger ID or, there is none, the one with ID 0. With N players: current = (current + 1) % N.

**-private running: Boolean = True**
Indicates whether the game is still in a running state or whether it has finished. Only allow manipulations when the running boolean is True.

**-private Matrix<HexSpace> pathMatrix**
Matrix of HexSpaces representing the whole playable field, also containing all blockades and starting-ending Fields. The Matrix has no Null-Entries but instances of HexSpaces with infinite costs and a specific colour wherever no HexSpace is in located on the Game.

**-private ArrayList<Player> players**
List of all players participating in the Game.

**-private ArrayList<Player> winners**
List containing all players that have reached ElDorado. Is used to calculate the final winner and to determine when the game is ended.

**-private ArrayList<Blockade> blockades**
List of all blockades that are in the game so that we can set the strength of all blockades belonging together to 0 when one blockade is removed.

**-private marketPlace: Market**
Instance of the current Marketplace that contains active and passive cards.

**-private memento: Memento**
Instance of the memento which save the state of the HexSpaces while the PathFinder modifies them, so that the HexSpaces can be reset.

**+public getHexSpace(point Point): HexSpace**
Gets a point with X/Y coordinates and returns a reference to the instance of HexSpace that is located at that position in the pathMatrix.

* * *

## public class HexSpace
=====================

**\- private strength: Integer**
The strength of a field indicates how high the card-value has to be to make it accessible. The strength of non-playing fields such as Mountains, Empty-Fields is set to 1000, all the other field strength correspond to the card strength needed to enter the field.

**\- private color: COLOR**
Enum of all possible colours a HexSpace can have. Each HexSpace has exactly one colour.

**\- private coordinates: Point**
The X/Y coordinates of the HexSpace in the Game Matrix.

**\- private minimalCost: Integer**
Costfactor assigned by the Pathfinding algorithm indicating how expensive it is to reach this field. This cost factor should by default be 1000 and be overwritten by the pathfinding whenever a way to the HexSpace is found. The value is reset to 1000 once the path algorithm finished.

**-private minimalDepth: Integer**
Keeps track of how many moving-steps were needed by the player to move to this current field with the minimal costs.

**\- private ArrayList<HexSpaces> Previous**
The way our pathfinding-algorithm found to access this HexSpace. The field is usually an empty ArrayList and set by the pathfinding-algorithm. The length of the HexSpaces array corresponds to the depth / amount of steps it took the pathfinding-algorithm to reach the HexSpace. BlockadeSpaces are included in the Previous history.

**\- private Game: Game**
HexSpace need to know to which Game it belongs. Primarily used for the PathFinder.

**\+ public getNeighbour(previous: HexSpace): ArrayList<HexSpace>**
Method that takes the previous HexSpace, the userEntity came from, into consideration when calculating the neighbours. The Methods calculates the coordinates of the neighbouring fields by taking the Game’s pathMatrix into account. From all found neighbours, the method looks whether BlockadeSpaces are among them. If this is the case and the blockade Spaces belong to the same active blockade, only one of the blockadeSpaces is returned. If the blockadeSpaces are inactive, the method asks it again for its neighbours by calling blockadeSpace.getNeighbours(this) and provides itself as the previous. This way the blockade can handle the neighbours with taking the previous direction into account.

* * *

## public class PATHFINDER
=======================

**\- private Set<HexSpaces> reachables**
Array of all HexSpaces that the PathFinder algorithm has visited and are marked as reachable.

**+public getWay(ArrayList<Cards>, PlayingPiece): ArrayList<HexSpace>**
The PlayingPiece has a HexSpace. This hexSpaceEntity is the first entry in the reachables array, because the player can obviously reach its own location. From there on, the neighbours of all reachables are checked whether they are reachable with the given set of cards.The PathFinder differentiates between two cases:
When the Cards-array consists of just one entry of type MovingCard, he looks what colours the selected Card supports. Then, for each of the X different colours, he looks what HexSpaces are reachables with just using this colour by always adding the reachable neighbours to an array of colourspecific reachables an, in the same time, sets the previous path of how he got to the HexSpace (the path he came from, so the path from the neighboured hexSpaceEntity + the neighbour itself, including barricadeSpaces) the cost that were needed to enter the field (the cards strengthLeft value - the fields stregth) and the depthLeft value, which is just the depth from the neighbour - 1. The PathFinder keeps track of which HexSpaces he found the best way already with a local variable currentIndex. CurrentIndex marks a position in the Array of reachable HexSpaces. To the left of the currentIndex, there are the HexSpaces that were already processed, e.g. of which the neighbours were already checked to be reachable. To the right of the currentIndex, there are the HexSpaces of which not all neighbours were yet checked. Whenever the pathFinder checks a HexSpac at currentIndex, it appends all its neighbours (if not yet in the array) to the reachables. When an array is already in the reachables, it checks whether it would now be reached with a cheaper path. If so, the corresponding HexSpace is moved from the left of the reachables to the end (because now we have to see whether one of its neighbours can be reached cheaper as well). PathFinder finishes when the currentIndex equals the length of the Array reachables. In the end, he is left with X arrays, each contining the reachable HexSpaces with using the Xth colour. The union of these arrays then is the returned value for reachables.

If multiple cards were selected or just one card of type actionCard, PathFinder checks whether one of the neighbours is of color “rubble” and only allows the move if a rubble with strenght less than the number of cards selected is in the set of neighbours.

When the PathFinder finished calculating the reachables, he sets the memento by setting the mementos cards and the references to all reachables. In the next call of the PathFinder, he will loop over all the HexSpace references saved inside the Memento and resets their states like previous and minialCost, minimalDepth so that he will not have false leftover information from the previous call.

Before the PathFinder returns the reachables, all HexSpaces of type Blockade are filtered out of it so that the userEntity can not move on Hexspace. Note that the Baricades to not disapear from the previous history of the individual reachable HexSpaces.

* * *

public class Blockade
=====================

**-private Array<BlockadeSpace>: spaces**
List of BlockadeSpaces the Blockade consists of.

**-private cost: int**
Cost to remove this blockade. The cost is the same for all BlockadSpaces in the same Blockade. The same cost is also set as “strength” in the BlockadeSpace. When a barricade is deactivated, the BlockadeSpace strength is set to 0, but not the Blockades cost since the Blockade gets assigned to the Player that removed it and its cost factor has to be remembered for the endgame.

**-public deactivate(): void**
Deactivates a blockade by settings its BlockadeSpace stregth to 0.

**-public assign(this): void**
Assings a new BlockadeSpace to the Blockade by appending it to the spaces array.

* * *

## public class BlockadeSpace extends HexSpace
===========================================

**@Overwrites**
**public getNeighbour(previous: HexSpace): ArrayList<HexSpaces>**
This method needs to be overwritten in order to determine the neighbours of a special HexSpace of type BlockadeSpace, since other BlockadeSpaces shall not count as its neighbours. The overwritten method considers where the player was coming from and therefore computes the neighbours ignoring inactive barricades. ![](images/image1.png)

* * *

## public abstract class Card
==========================

**-private String: name**
Defines the cards name for identification in the frontend. Multiple cards can have the same name.

**-private coinValue: float**
How many coins the userEntity gets when selling the card

**-private coinCost: int**
How many coins the userEntity needs to buy this card from the Market.

**+public sellAction(player: Player)**
The cards sellAction method  handles what happens when a card is sold. In the normal cases, sell will increase call Player.discard(Card), but in some cases a special Card will overwrite the sell method and call Player.remove(Card), making the card fall out of the game when being sold (treasury).

**+public moveAction(player: Player, moveTo: HexSpace)**
Not implemented

* * *

## public class MovingCard extends Card
====================================

**-private strength: int**
Defines the cards moving-value: The moving cards strength defines the initial strengthLeft value for the path finding algorithm.

**-private depth: int**
The depth value defines how far you can walk at most with this card at most.

**-private color: ArrayList<Color>**
List of all colors on which this card can move on. Every movingCard always supports the color rubble and basecamp as well.

**+public validateMove(moveTo: HexSpace, strengtLeft: int, color: Color, depthLeft: int): Boolean**
Checks whether the HexSpaces color matches one of the cards colors and whether the strengthLeft is as least as high as the strength of the HexSpace. Also checks if the depthLeft is high enough to even move, so whether depthLeft is smaller than depth. Returns True if both properties are True, false otherwise.

**@Implements**
**+moveAction(player: Player, moveTo: HexSpace)**
Does NOT move the Player directly, that is his job. This method lets the card decide of what action is to be taken after a move is fullfilled: Player.discard(this) or Player.remove(this). Implementation here is Player.discard(this), all special cards that want to be removed after moving overwrite this method with Player.remove(this).  
Before Player.dicard(this) is called, we check for one special case: if the to-HexSpace is of type basecamp, the card is removed instead of discarded.

* * *

## public class RemoveMoveCard extends MovingCard
==============================================

**@Overwrites**
**public moveAction(player: Player, moveTo: HexSpace)**
Overwrites the move method to make Player.discard(this) the standard behaviour. Also checks for one special case: If the to-HexSpace is of type Rubble, the card is discarded via Player.discard(this).

* * *

## public class RemoveMoveSellCard extends MovingCard
==================================================

**@Overwrites**
**public sellAction(player: Player)**
Overwrites the sellAction method that calls Player.remove(this) instead of Player.discard(this) when a Card is sold.

* * *

## public class ActionCard extends Card
====================================

**+public actions: SpecialActions**
The Budget that is granted to the userEntity when the ActionCards action is performed. Bugeds stores how many cards the userEntity can draw from the draw pile, how many card she/he can remove and how many cards she/he  can steal from the market according to the cards type.

**+public performAction(): SpecialActions**
The performAction returns a Budget of how many cards the Player can draw/remove/steal for free.

**@Implements**
**+public moveAction(player: Player, to: HexSpace)**
Calls Player.discard(this) in the standard case. If the to-HexSpace happens to be of color BaseCamp, then call Player.remove(this) instead.

* * *

## public class RemoveActionCard
=============================

**@Overwrites**
**+public performAction(): SpecialAction**
Calls the parents performAction to use Player.remove(Card) instead of Player.discard(Card).

* * *

## public class Market
===================

**-private active: ArrayList<Slot>**
Slot with the active cards. These are always purchasable by the userEntity.

**-private passive: ArrayList<Slot>**
Slot of passive cards. These cards are only purchasable if the active ArrayList has less than 6 Slots. Otherwise, these cards are not purchasable.

**+public buy(slot: Slot): Card**
Removes one Card from the Slot and returns it to the userEntity. A buy order is only valid if the Slot is part of the Active Slots or the Active Slots have less than 6 Slots inside. In this case, the Slot from the Passive Slots is moved into the active Slot. If The Slot was an active Slot and only one Card was left, the Slot gets removed entirely. The player then puts the card on the discard pile using Card.discard()

**+public getPurchasables(): ArrayList<Slot>**
Returns either the active Slots when size of active slots is 6, active and passive slots otherwise.

**+public getActive(): ArrayList<Slot>**
Returns the active slots.

**+public getPassive(): ArrayList<Slot>**
Returns the passive slots.

**+public stealCard(slot: Slot)**
Lets the userEntity take one card from active and passive slots. If the slot becomes empty, removes that slot from the active Slots.

* * *

## public class Slot
=================

**+public pile: ArrayList<Cards>**
Each pile consists of 1 to 3 Cards.

**+public buy()**
Returns one contained Card instance and removes it from the Pile.

**+public getCard(): Card**
Returns one of the Card from the pile without removing it. Is used to compare card values before the userEntity buys a card.

* * *

## Public class Player
===================

**-private name: String**
Players  name, set by the User. Has to be unique in the Game

**-private id: Integer**
Globally unique ID to recognize a Player within the Game.

**-private token: String**
Global unique token that identifies a User to its player. The token is communicated via SSL and randomized. For each game changing move, the userEntity has to validate itself with this token in order to perform the move.

**-private coins: Float**
Number of coins the Player has in his wallet. Is reset to 0 when he ends his round or bought one card.

**-private board: Game**
Instance of Game on which the Player is performing his action.

**-private pathFinder: PATHFINDER**
Instance of PATHFINDER the player uses to find the possible paths.

**-private blockades: ArrayList<Blockade>**
List of blockades the Player has collected so far.

**-private playingPiece: ArrayList<PlayingPiece>**
The HexSpaces the userEntity can move with.

**-private specialAction: SpecialAction**
The budget the userEntity has for the current round. Is set from the action cards and reset either at the end of the game or value-by-value each time the corresponding method (draw, remove, steal) is called.

**-private history: ArrayList<CardAction>**
Each time the userEntity plays a Card of any type, its history is appended with the corresponding CardAction.

**-private drawPile: ArrayList<Cards>**
List of cards the userEntity has in his drawpile.

**-private handPile: ArrayList<Cards>**
List of cards the userEntity has in his drawPile.

**-private discardPile: ArrayList<Cards>**
List of cards the userEntity has in his discardPile.

**-private bought: Boolean**
Indicates whether the userEntity has already bought a Card in the current round.

**\+ public findPath(activeCards: ArrayList<Card>, playingPiece: PlayingPiece): ArrayList<HexSpace>**
Calls PathFinder with the cards and the selected playingPiece. Returns the same arrayList the PathFinder returns.

**\+ public findPath(activeCards: ArrayList<Card>): ArrayList<HexSpace>**
Call this.pathFinder with the first playingPiece and the list of Cards.Returns the same arrayList this.pathFinder returns.

**\+ public move(playingPiece: PlayingPiece, cards: ArrayList<Cards>, moveTo: HexSpace)**
Checks in the memento whether cards corresponds to SelectedCards, placingPiece to playingPiece and if to-HexSpace is in reachables. If so, the players PlayingPiece is moved to the to-HexSpace location. If in the history of the to-HexSpaces previous an active barricadeSpace is located, deactive is called on the corresponding blockade and the blockade is added to the userEntities barricades-array. After a move is done, call cards.move on all cards in the Cards array. Finally, we check whether a deactivated barricade is now next to the to-HexSpaces neighbours. If this is the case, check whether the barricade is of the same type as the to-HexSpace and if the difference between the cards value and the to-HexSpaces minimalCosts allows the removal of the barricade. The userEntity can then decide whether he wants to take that barricade or not.

When the move is done, the Player checks whether his PlayingPiece stands on a HexSpace of colour ElDoardo. If this is the case, he adds himself to the Games winning Player array.

**\+ public action(card: Card): specialAction**
Calls action on the corresponding card and sets the returned SpecialAction to its own budget. Adds instance of CardAction with dedicated name to the history array. It returns the budget back to the Frontend.

**\+ public discard(card: Card)**
Moves the corresponding Card from the handPile to the discardPile. Adds instance of CardAction with dedicated name to the history array.

**\+ public buy(slot: Slot)**
Calls buy on the market and adds the returned card to the discardPile if the userEntity has the coins to do so and not yet bought anything. Adds instance of CardAction with dedicated name to the history array.

**\+ public draw()**
Calls draw(amount) with the amount being 4 - length of HandPile.

**\+ public draw(amount: Int)**
Takes amount-cards from the drawpile, regardles of how many cards there are in the Handpile. If the drawPile is empty, the discardPiles order is randomized and all cards are moved from the discardPile to the drawPile.

**\+ public steal(slot: Slot)**
Calls market.steal and adds the returned card to the discardpile. Does neither take the amount of coins nor the bought-boolean into consideration.

**\+ public remove(card: Card)**
Moves the card from the handPile to the removePile.

**\+ public endRound()**
Calls draw() and resets the coins to 0 and the bought-boolean to False.

**+ public sell(card:Card)**
calls Card.sell(self: Player)

* * *

## public class CardAction
=======================

**-private cards: ArrayList<Cards>**
List of Cards that were used to perform a certain action.

**-private actionName: String**
Name of the corresponding Action that is then displayed in the FrontEnd

* * *

## public class Actions
====================

**-private draw: Integer**
Defines how many Card the player can draw for free.

**-private remove: Integer**
Defines how many Card the player can remove for free.

**-private steal: Integer**
Defines how many Card the player can steal for free.

* * *

## public class Memento
====================

**-private reachables: ArrayList<HexSpace>**
List of HexSpaces that the PathFinder reached.

**-private selectedCards: ArrayList<Card>**
List of Cards that were used to perform the pathfinding-algorithm.

**-private playingPiece: PlayingPiece**
PlayingPiece from which the PathFinder performed its pathfinder-algorithm.

**+public update(reachables: ArrayList<Card>, Cards: ArrayList<Card>)**
Refill Memento with new information from the PathFinder.

**+public reset()**
Deletes all entries from reachables and selectedCards, playingPiece can be set to Null.

* * *

## public class PlayerService
==========================

**\+ public validate(token: String): boolean**
Compares the userEntity token with the token from the current player and only allows the turn if they match AND the game is in running state.

**\+ public endRound(token: String)**
Calls Endround on the player after validating token. Changes the current Player and therefore makes all further action of the old player non-accepting. Checks whether the current Player was of the highest ID (last Player in the round). If so, check whether the Game has Players in its winning Players array and set the running attribute to false if this is the case.

**\+ public playerFindPath(token: String, playingPiece: PlayingPiece, cards: ArrayList<Card>)**
Calls corresponding pathfinding method on player after validating the token.

**\+ public playerMove(token: String, playingPiece: PlayingPiece, cards: ArrayList<Card>, moveTo: HexSpace)**
Calls corresponding move method on player after validating the token.

**\+ public playerMove(token: String, cards: ArrayList<Card>, moveTo: HexSpace)**
Calls corresponding move method on player after validating the token.

**\+ public playerSell(token: String, sellingCard: Card)**
Calls sell on player after validating the token.

**\+ public playerDiscard(token: String, discardingCard: Card)**
Calls discard on player after validating the token.

**\+ public playerBuy(token: String, buyingSlot: Slot)**
Calls buy on player after validating the token.

**\+ public playerSteal(token: String, stealingSlot: Slot)**
Calls steal on player after validating the token.

**\+ public playerRemove(token: String, removingCard: Card)**
Calls remove on player after validating the token.

**\+ public playerAction(token: String, actionCard: Card): SpecialAction**
Calls action on player after validating the token.

**\+ public getActions(): ArrayList<CardAction>**
Returns an array of CardActions that the userEntity performed until now in his turn.


* * *

# DECISIONS:

Assembler: setzt das spielfeld zusammen. für blockaden setzt er nur generelle blockaden ein, also ohne typ und strenght. die fertige matrix wird dann an Game übergeben.

Game fragt dann, den Assembler zusätzlich wo die Blockaden positioniert sind und auch wo die StaringSpaces und EndSpaces sind. So muss das Game die Matrix nicht parsen. Das Game macht dann das mapping zwischen der matrix und der blockaden objekten. Der Assembler übernimmt somit also die komplette kommunikation mit der Datenbank was das Spielfeld betrifft.

Kommunikation mit der DB sollte wenn immer über ein und dieselbe classe ablaufen. Es kann auch mehrere solche klassen geben. Eine dieser klassen ist der Assembler welcher für die Spielfeld Daten zuständig ist. Es könnte dann noch klassen geben die für die Player-Daten zuständig sind etc.

alle karten haben eine validate move funktion. der hauptgrund dafür ist das jede karte benützt werden kann um auf ein einfachen Rubble/BaseCamp feld zu ziehen.

Im backend brauchen Karten kein Image: im frontend kann das image abhängig vom namen gefecht werden. selbes gilt für die description

dijkstra hat drei verschiedene strategien: falls das array von karten mehr als ein karte umfasst wird nur nach Rubble oder BAsecamp felder gesucht. falls nur eine Karte übergeben wird überprüft ob es sich um eine Action Karte oder eine Move karte handelt. Wenn move Karte dann sucht der ElDijkstra “normal” nach feldern mit der gleichen farbe, oder nach rubble/basecamp felder. Falls es eine AktionKarte ist sucht er nur nach Rubble/BaseCamp.

sell und remove brauchen den player als argument das es vorkommen kann dass wenn eine karte verkauft wird, sie aus dem spiel genommen wird (treasure) in diesem fall muss die karte wissen aus welchem player sie sich entfernen muss und desshalb brauch remove den player. und damit wir den player an remove übergeben können müssen wir ihn im sell auch übergeben.

der “Native” wird als move karte interpretiert da er keine anderen spezialfähigkeiten hat. spirch er ist eine move karte mit allen Farben.

Das Flugzeug und andere multicolor karten haben mehrere Farben. Aber es kann jeweils nur eine Farbe ausgewählt werden für den zug. Darum muss der Dijkstra für jede frabe separat die reachables berechnen und gibt dann die union zurück.

Die “Depth” in movingcard brauchen wir um sicher zu stellen das der Native nur ein Feld weit zieht. da der Native eine hohe strength hat müssen wir sicherstellen dass die übriggebliebene strenght nicht weiter verwendet wird.

Rubble und BaseCamp werden auch als farben interpretiert so das wir das ziehen mit dem native rein über die farben lösen können.

Es gibt felder die entscheiden was mit der verwendeten karte passiert und es gibt karten die entscheiden was mit ihnen bei der verwendung passiert. das feld ist immer stärker. Bespiel. wenn der treasure verwendet wird um auf ein sand feld zu fahren dann wird remove() ausgeführt. wenn der treasure aber discarded wird um auf ein rubble zu fahren das wird nur discard ausgeführt. Es dürfen auf keinen Fall zwei solche funktionen durch eine karte ausgeführt werden.

wenn dijkstra über eine blockade hinweg ein HexSpace findet dann schreibt er diese information in den gefundenen HexSpace rein. Dass heisst auch das ein BlockadeSpace in den previous drin. BlockadeSpaces werden jedoch nie in den Reachables zurückgegeben. wenn der spieler auf ein feld hinter der blockade zieht dan wird die blockade entfernt/ auf inaktiv gesetzt und dem spieler angerechnet.

Damit wir erkennen ob der spieler eine blockade entfernen kann aber nicht darüber hinaus zieht überprüfen wir nach dem zug ob in den Nachbarn eine blockade ist und es leftover strength gibt die reichen um die blockade zu entfernen. Wir müssen auch wissen mit welcher farbe man auf dieses feld gezogen ist weil die barrikade die gleiche farbe haben muss.
