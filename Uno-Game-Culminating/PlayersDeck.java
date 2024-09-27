import java.util.*;

public class PlayersDeck extends CardSet{
  //ArrayList<Card>player1Stack = new ArrayList<Card>();
  private GameDeck game;
  private ArrayList<ArrayList<Card>>allPlayerDeck = new ArrayList<ArrayList<Card>>();//main double ArrayList holding all players decks 

  //Breaking apart the GameDeck, to get 4 distinct decks for 4 players
  public PlayersDeck(int elements, GameDeck game){
    super(elements);
    this.game = game; 
    Random rand = new Random();
    for(int t=0; t<4;t++){
      //System.out.println("Stack "+t);
      ArrayList<Card> player1Stack = new ArrayList<Card>();
      for(int i=0; i<getElements()/4; i++){
        int randomNum = rand.nextInt(game.getDeck().size());
        Card u = game.getDeck().get(randomNum);
        player1Stack.add(u);
        //System.out.println(u);
        game.getDeck().remove(randomNum);
      }//Randomly choosing cards for each player's deck set
      allPlayerDeck.add(player1Stack);//adding them to the main set double ArrayList
    }
  }

  public ArrayList<ArrayList<Card>> getTheDeck(){
    return allPlayerDeck;
  }//accessor for all players decks 
  
  //method to printout deck for a single player
  public void printSingleDeck(int stack){
    System.out.println("Stack "+stack);
    for(int j=0; j<allPlayerDeck.get(stack-1).size(); j++){
      System.out.println((j+1)+") "+allPlayerDeck.get(stack-1).get(j));
    }
  }


  //Abstract method, printing deck for PlayersDeck type
  public void printDeck(){
    for(int i=0; i<allPlayerDeck.size(); i++){
      System.out.println("Stack "+(i+1));
      for(int j=0; j<(allPlayerDeck.get(0)).size(); j++){
        System.out.println((allPlayerDeck.get(i)).get(j));
      }
    }
  }

  @Override
  public String toString(){
    printDeck();
    return super.toString();
  }
  
}