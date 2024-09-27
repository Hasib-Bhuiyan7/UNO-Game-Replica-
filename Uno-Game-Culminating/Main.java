import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
    Random rand = new Random();
    Scanner in = new Scanner(System.in);
    Scanner file = new Scanner(new File("Uno-Dialogue.txt"));//File I/O - File Instance
    for(int i=0; i<7; i++){
      System.out.println(file.nextLine());
    }//file I/O txt print
    GameDeck unoDeck = new GameDeck(); //official uno deck with all uno cards for the game
    String choice = in.nextLine();//choice for number of cards to play with
    if(choice.equals("a")){
      GameDeck specificDeck = new GameDeck(16);
      unoDeck = specificDeck;
    }
    else if(choice.equals("b")){
      GameDeck specificDeck = new GameDeck(20);
      unoDeck = specificDeck;
    }
    else if(choice.equals("c")){
      GameDeck specificDeck = new GameDeck(24);
      unoDeck = specificDeck;
    }
    GameDeck cdeck = unoDeck.makeCopy();//copy so original uno deck is untouched
    PlayersDeck gamePlay = new PlayersDeck(unoDeck.getElements(), cdeck);//PlayersDeck class used for breaking uno deck into 4 decks for 4 players
    //fixedTest(gamePlay); [This is for test and debugging]
    System.out.println("\nThis is the deck:");
    System.out.println(unoDeck);
    System.out.println("\nEach player gets "+(unoDeck.getElements()/4) + " Cards\n\nThis is your stack: ");
    gamePlay.printSingleDeck(1);//prnting out deck 1; the main players deck
    System.out.println("\n"+file.nextLine());

    System.out.println("\n\n");
    int cardPlayNum = in.nextInt();//number chosen as per mai player's card list
    cardPlayNum = limited((unoDeck.getElements()/4), cardPlayNum);
    int t=0;//variable for controlling game flow
    boolean ifReverse = false;//variable for controlling reverse directions assisssting with game flow
    Card cardPlay = gamePlay.getTheDeck().get(t).get(cardPlayNum-1);//first card of the game to play with
    System.out.println("Played -- "+cardPlay);
    gamePlay.getTheDeck().get(t).remove(cardPlayNum-1); //removing card from players deck, as it's played with
    System.out.println("This is your stack now: ");
    gamePlay.printSingleDeck(t+1);//showing players stack
    cardPlay.action(unoDeck,  gamePlay, ifReverse, t);//main method for card behaviour; this is the method for card being played
    ifReverse = cardPlay.isItReversed();//reassessing players direction of gam flow
    t=gameFlowDirector(t, ifReverse, cardPlay);
    //reassessing the variable t for gameflow behaviour



    
    Card computerPick;//variable for computer and main players pick 
    boolean didComputerPick = false;//checking if any cards were picked for any given player
    int i=0;//iteration variable initialized

    //while there is no winner keep the game moving
    while(checkWinner(gamePlay)==(-1)){
      //while t is not 0; which means, while it's not main players turn keep thi section running
    while(t!=0){
      //Computer AI behaviour and play 
    for(i=0; i<gamePlay.getTheDeck().get(t).size(); i++){
      computerPick = gamePlay.getTheDeck().get(t).get(i);//checking over all cards for any suitable game card; this is searching
    /*checking for requirements to be played */  if(computerPick.getColour()==cardPlay.getColour() || computerPick.getNum()==cardPlay.getNum() || computerPick.getColour()=="none"){
        System.out.println("\n\nNow it's Player "+(t+1)+"'s turn\n");
        cardPlay = computerPick;//setting new current card in the game; cardPlay
        System.out.println("Played -- "+cardPlay);
        didComputerPick = true;//Given player has picked cards
       
/*main game actions; card play, and setting game flow*/      gamePlay.getTheDeck().get(t).remove(cardPlay); 
        cardPlay.action(unoDeck,  gamePlay, ifReverse, t);
        ifReverse = cardPlay.isItReversed();
        t=gameFlowDirector(t, ifReverse, cardPlay);
        break;
      }
    }
      //if computerd did not find suitable cards
      if(didComputerPick==false){
        didComputerPick = true;
        int randomPick = rand.nextInt(unoDeck.getDeck().size());
        System.out.println("\nNow it's Player "+(t+1)+"'s turn");//random picker for cards
        System.out.println("*Player Picking random Card*");
        computerPick = unoDeck.getDeck().get(randomPick);
       /*if random card is suitable to play out*/ if(computerPick.getColour()==cardPlay.getColour() || computerPick.getNum()==cardPlay.getNum() || computerPick.getColour()=="none"){
        cardPlay = computerPick;
        System.out.println("Played -- "+cardPlay);  
        gamePlay.getTheDeck().get(t).remove(computerPick); 
          cardPlay.action(unoDeck,  gamePlay, ifReverse, t);
          ifReverse = cardPlay.isItReversed();
          t=gameFlowDirector(t, ifReverse, cardPlay);
          didComputerPick = false;
}
         //if random additional card is not suitable
        else{
          gamePlay.getTheDeck().get(t).add(computerPick);
        ifReverse = cardPlay.isItReversed();
          t=gameFlowDirector(t, ifReverse, cardPlay);
        }
    }
      didComputerPick = false;//resetting 'if card was picked' variable
}
      //if t=0; this is when main player is playing
    if(t==0){
    System.out.println("\n\nIt's your turn again, choose another card:\n");
    gamePlay.printSingleDeck(t+1);
    cardPlayNum = in.nextInt();//number chosen by player
    cardPlayNum = limited((gamePlay.getTheDeck().get(0).size()), cardPlayNum);//checking if number is appropriate with it's limits
    Card mainPlayer = gamePlay.getTheDeck().get(t).get(cardPlayNum-1);
      System.out.println(mainPlayer);
      
     /*if card chosen is suitable play card*/ if(mainPlayer.getColour()==cardPlay.getColour() || mainPlayer.getNum()==cardPlay.getNum() || mainPlayer.getColour()=="none"){
        cardPlay = mainPlayer;
        System.out.println("Played -- "+cardPlay);
        didComputerPick = true;
        gamePlay.getTheDeck().get(t).remove(cardPlay); 
        cardPlay.action(unoDeck,  gamePlay, ifReverse, t);
        ifReverse = cardPlay.isItReversed();
        gamePlay.printSingleDeck(t+1);
        t=gameFlowDirector(t, ifReverse, cardPlay);
      }
       
       //if chosen card is not suitable 
else if(didComputerPick==false){
        didComputerPick = true;
        int randomPick = rand.nextInt(unoDeck.getDeck().size());
        System.out.println("*Picking random Card*");//random card is picked for user
        computerPick = unoDeck.getDeck().get(randomPick);
  gamePlay.getTheDeck().get(t).add(computerPick);
  
  /*if random given card is suitable to play with*/      if(computerPick.getColour()==cardPlay.getColour() || computerPick.getNum()==cardPlay.getNum() || computerPick.getColour()=="none"){
        gamePlay.printSingleDeck(t+1);
        cardPlay = computerPick;
        System.out.println("Played -- "+cardPlay);  
        gamePlay.getTheDeck().get(t).remove(computerPick); 
          cardPlay.action(unoDeck,  gamePlay, ifReverse, t);
          ifReverse = cardPlay.isItReversed();
          gamePlay.printSingleDeck(t+1);
          t=gameFlowDirector(t, ifReverse, cardPlay);
          didComputerPick = false;
}
    //if random given card is not playable
  else{
    gamePlay.getTheDeck().get(t).add(computerPick);
  ifReverse = cardPlay.isItReversed();
          gamePlay.printSingleDeck(t+1);
          t=gameFlowDirector(t, ifReverse, cardPlay);
  }
    }
      didComputerPick = false;
}
    
}
    //if any player won the game then declare that player as winner
    if(checkWinner(gamePlay)!=(-1)){
      System.out.println("The winner of this game is: Player "+(checkWinner(gamePlay)));
    }
}

  //main method for game flow; this makes sure that game is moving in the right diretion, it takes reverses, and skips into account
  public static int gameFlowDirector(int t, boolean ifReverse, Card cardPlay){
    if(ifReverse==true){
        t--;
    }
    else{
        t++;
    }//game flow for reverse effects
    if(cardPlay.isItSkip()==true){
      if(ifReverse==true){
        t--;
      }
      else{
        t++;
      }
    }//gameflow for skip turns
    if(t<0){
      t=3;
    }
    else if(t>3){
      t=0;
    }
    return t;//regulating the loop so numbers dont surpass boundaries
  }

  //method to check for winners
  public static int checkWinner(PlayersDeck gamePlay){
    int winner=-1;
    for(int i=0; i<gamePlay.getTheDeck().size(); i++){
      if(gamePlay.getTheDeck().get(i).size()==0){
        winner = i+1;
      }//winner is the player with 0 cards on deck
    }
    return winner;
  }

  //method to regulate limits for user input numbers
  public static int limited(int lim, int choice) {
    Scanner in = new Scanner(System.in);
    while (choice > lim || choice < 1) {
      System.out.println("Please try an appropriate number from the number bounds:");
      choice = in.nextInt();
    }
    return choice;
  }
  


  //testing and debugging method; takes in a PlayersDeck class, empties it and puts in specific test values; this is made so that real game values are'nt changed
  public static void fixedTest(PlayersDeck Game){
   Game.getTheDeck().get(0).removeAll(Game.getTheDeck().get(0));
    Game.getTheDeck().get(1).removeAll(Game.getTheDeck().get(1));
    Game.getTheDeck().get(2).removeAll(Game.getTheDeck().get(2));
    Game.getTheDeck().get(3).removeAll(Game.getTheDeck().get(3));
   Card card1 = new Card("normal", 5, "Yellow");
    Card card2 = new Card("Reverse", "Blue");
    Card card3 = new Card("Skip", "Red");
    Card card4 = new Card("Draw_2", "Green");
    //Card card5 = new Card("WildDraw_4");
    //Card card6 = new Card("WildCard");
Game.getTheDeck().get(0).add(card1); Game.getTheDeck().get(0).add(card2); Game.getTheDeck().get(0).add(card3); Game.getTheDeck().get(0).add(card4);
     
Game.getTheDeck().get(1).add(card1); Game.getTheDeck().get(1).add(card1); Game.getTheDeck().get(1).add(card1); Game.getTheDeck().get(1).add(card1);

Game.getTheDeck().get(2).add(card1); Game.getTheDeck().get(2).add(card1); Game.getTheDeck().get(2).add(card1); Game.getTheDeck().get(2).add(card1);
    
Game.getTheDeck().get(3).add(card1); Game.getTheDeck().get(3).add(card1); Game.getTheDeck().get(3).add(card1); Game.getTheDeck().get(3).add(card1);
}
}

  



