import java.util.*;

//Abstract class for cardsets, both official uno deck, and PlayersDeck
public abstract class CardSet{
  private int elements;//elements or cards
  //private ArrayList<Card>deck=new ArrayList<Card>();

  public CardSet(){
    elements=0;
  }
  public CardSet(int elements){
    this.elements = elements;
  }
  

  public int getElements(){
    return elements;
  }
  public void setElements(int elements){
    this.elements = elements;
  }

  public abstract void printDeck();//abstract method to print deck for each type of deck type

  public String toString(){
    return "Elements: "+elements;
  }
}