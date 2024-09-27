import java.util.*;

public class Card{
  private String cardType;//type of card
  private int cardNum;//card number
  private String colour;//card colour
  int type;//type for constructor and toString 
  private boolean hasItReversed;//reverse checker; if game must be reversed, this variable is sent to Main class game flow control
  private boolean isSkip;// skip checker; to communicate to Main class game flow 

  public Card(String cardType, int cardNum, String colour){
    this.cardType = cardType;
    this.cardNum  = cardNum;
    this.colour = colour;
    type=1;
  }
  
  public Card(String cardType, String colour){
    this.cardType = cardType;
    if(cardType=="Skip"){
      this.cardNum = -1;
    }
    else if(cardType=="Reverse"){
      this.cardNum = -2;
    }
    else if(cardType=="Draw_2"){
      this.cardNum = -3;
    }
    //this.cardNum = -1;
    this.colour = colour;
    type=2;
  }

  public Card(String cardType){
    this.cardType = cardType;
    //this.cardNum = -1;
    this.colour = "none";
    type=3;
  }

  //getters and accessors
  public int getNum(){
    return cardNum;
  }
  public String getColour(){
    return colour;
  }
  public String getCardType(){
    return cardType;
  }
  public boolean isItReversed(){
    return hasItReversed;
  }
  public boolean isItSkip(){
    return isSkip;
  }
  
  //Mutators methods
  public void setNum(int cardNum){
    this.cardNum = cardNum;
  }
  public void setColour(String colour){
    this.colour = colour;
  }
  public void setCardType(String cardType){
    this.cardType = cardType;
  }

  //Primary method for cards; this is the card action, determines the behaviour and outcomes of how cards are played
  public void action(GameDeck unoDeck, PlayersDeck  gamePlay, boolean ifReverse, int t){
    Random rand = new Random();
    hasItReversed  = ifReverse;//setting global variable value
    ArrayList<Card> opponentSet;//opponent set, the player deck the card intents to aim at
    if(ifReverse==true){
      opponentSet = gamePlay.getTheDeck().get(boundary((t-1), 0, 3));
    }//if game is reversed, then choose opponent player in opposite direction
    else{
      opponentSet = gamePlay.getTheDeck().get(boundary((t+1), 0, 3));
    }//if game is not reversed, then choose opponent player in normal direction
    if(getCardType()=="Draw_2"){
      System.out.println("*Drawing 2 Cards*\n");
      for(int j=0; j<2; j++){
        opponentSet.add(randomDrawer(unoDeck));
      }
    }//Outcomes for Draw_2 card
    else if(getCardType()=="Reverse"){
      System.out.println("*Reversing Direction*\n");
      if(ifReverse == true){
        ifReverse = false;
        hasItReversed = false;
      }//if game is already set to reverse, then unreverse it, 2 reverses cancel out
      else if(ifReverse == false){
        ifReverse = true;
        hasItReversed = true;
      }//if game is not reversed, then reverse it

    }//Outcome for reverse card
    else if(getCardType()=="Skip"){
      System.out.println("*Skipping next Player*\n");
      isSkip = true;//set skip to true
    }//Outcome for skip card
      
      
    else if(getCardType()=="WildDraw_4"){
      System.out.println("*Drawing 4 cards*\n");
      for(int j=0; j<4; j++){
        opponentSet.add(randomDrawer(unoDeck));
      }
      colourChange(t);
    }//Outcome for WildDraw_4; add 4 more cards to opponent, and change colour as per players choice
      
    else if(getCardType()=="WildCard"){
      System.out.println("*changing colour*\n");
      colourChange(t);
    }//WildCard outcome; changing colour for uno game cards
  }

  

  //setting boundary for opponent, regulating loop so that numbers repeat themselves from (0,3), for players array elements
  public int boundary(int t, int beg, int end){
    int newT;
    if(t<beg){
      newT=end;
    }
    else if(t>end){
      newT=beg;
    }
    else{
      newT=t;
    }
    return newT;
  }

  //colour changing method for both main player and computer player
  public void colourChange(int t){
    Scanner in = new Scanner(System.in);
    Random rand = new Random();
    System.out.println("What colour do you want to change it into?\n a)Blue\n b)Red\n c)Yellow\n d)Green");
    String colChange;
    if(t==0){
        colChange = in.nextLine();  while(!"a".equals(colChange)&&!"b".equals(colChange)&&!"c".equals(colChange)&&!"d".equals(colChange)){
          colChange = in.nextLine();
     }//if user input is not a, b, c,  or d then ask again
    if(colChange.equals("a")){
      setColour("Blue");
    }
    else if(colChange.equals("b")){
      setColour("Red");
    }
    else if(colChange.equals("c")){
      setColour("Yellow");
    }
    else if(colChange.equals("d")){
      setColour("Green");
    }
      System.out.println("Next colour is: "+getColour());
    }
      //computer random generated colour pick
    else{
      String[] colourSet = { "Red", "Blue", "Green", "Yellow" };
      int num = rand.nextInt(colourSet.length);
      setColour(colourSet[num]);
      System.out.println("Next colour is: "+getColour());
    }
  }

  
  //method for random picking cards
  public  Card randomDrawer(GameDeck unoDeck){
    Random rand = new Random();
    int randomPick = rand.nextInt(unoDeck.getDeck().size());
       Card computerPick = unoDeck.getDeck().get(randomPick);
    return computerPick;
  }



  //toString() for card itself, based on type of contstructor
  public String toString(){
    if(type==1){
      return "Card: "+cardType+", "+cardNum+", "+colour;
    }
    if(type==2){
      return "Card: "+cardType+", "+colour;
    }
    if(type==3){
      return "Card: "+cardType;
    }
    else{
      return "Card: ";
    }
  }


  
}