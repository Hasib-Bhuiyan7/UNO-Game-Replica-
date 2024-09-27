import java.util.*;

public class GameDeck extends CardSet {
  private ArrayList<Card> deck = new ArrayList<Card>();

  public GameDeck(){
    super();
  }
  //GameDeck creating it's auto-generated Uno Deck
  public GameDeck(int elements) {
    super(elements);
    int i = 0;
    int r = 0;
    Random rand = new Random();
    String[] colourSet = { "Red", "Blue", "Green", "Yellow" };
    String[] SpecialTypes = { "Skip", "Reverse", "Draw_2", "WildDraw_4", "WildCard" };
    for (int j = 0; j < super.getElements() - 10; j++) {
      Card card = new Card("normal", i, colourSet[r]);
      deck.add(card);
      // System.out.println(card);
      r++;
      i++;
      if (r > 3) {
        r = 0;
      }
      if (i > 9) {
        i = 0;
      }
    }//First setting and printing out the normal cards
    for (int t = 0; t < 5; t++) {
      for (int y = 0; y < 2; y++) {
        if (t < 3) {
          Card card = new Card(SpecialTypes[t], colourSet[rand.nextInt(4)]);
          deck.add(card);
        } else if (t >= 3) {
          Card card = new Card(SpecialTypes[t]);
          deck.add(card);
        }
      }
    }//saving the last 10 cards for special type cards
  }
  
  //Abstract method to print deck for this deck
  public void printDeck() {
    for (int i = 0; i < deck.size(); i++) {
      System.out.println(deck.get(i));
    }
  }

  public ArrayList<Card> getDeck() {
    return deck;
  }

  //method to make copy of one GameDeck
  public GameDeck makeCopy(){
    GameDeck copy = new GameDeck();
    for(int j=0; j<deck.size(); j++){
      copy.deck.add(deck.get(j));
    }
    copy.setElements(getElements());
    return copy;
  }

  @Override
  public String toString() {
    printDeck();
    return super.toString();
  }
}

