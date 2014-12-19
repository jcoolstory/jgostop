package com.jcools.GoStop;
import java.util.ArrayList;
import java.util.Stack;

class Card
{
	

	
	public static final int GWANG = 1;
	public static final int YEOLGGOT = 2;
	public static final int TTI = 3;
	public static final int PEE = 4;
	
	public static int GUCKGIN = 7;
	public static int GODORI = 8;
	public static int BEGWANG = 9;
	public static int CHODAN = 10;
	public static int CHEONGDAN = 11;
	public static int HONGDAN = 12;
	
	public static int EMTY = 0;
	 
	public static int FIRST = 1;
	public static int SECOND = 2;
	public static int THIRD = 3;
	public static int FORTH = 4;
	public static int FIVETH = 5;
	public static int SIXTH = 6;
	public static int SEVENTH = 7;
	public static int EIGHT = 8;
	public static int NINE = 9;
	public static int TEN = 10;
	public static int ELEVEN = 11;
	public static int TWELVE = 12;
	public static int BONUS = 13;
	
	
	public static final int SSANPEE = 1;
	public static int BONUS2 = 2;
	public static int BONUS3 = 3;
	
	private int Set ;
	private int Line;
	private int Type;
	private int TypeSub;
	private int Index;
	
	public Card(int index, int set, int line, int  type) {
		// TODO Auto-generated constructor stub
		this.setIndex(index);
		this.setSet(set);
		this.setLine(line);
		this.setType(type);
		this.setTypeSub(EMTY);
	}
	public Card(int index, int set, int line, int  type , int type2) {
		// TODO Auto-generated constructor stub
		this.setIndex(index);
		this.setSet(set);
		this.setLine(line);
		this.setType(type);
		this.setTypeSub(type2);
	}
	public void setSet(int set) {
		Set = set;
	}
	public int getSet() {
		return Set;
	}
	public void setLine(int line) {
		Line = line;
	}
	public int getLine() {
		return Line;
	}
	public void setType(int type) {
		Type = type;
	}
	public int getType() {
		return Type;
	}
	public void setTypeSub(int typeSub) {
		TypeSub = typeSub;
	}
	public int getTypeSub() {
		return TypeSub;
	}
	public void setIndex(int index) {
		Index = index;
	}
	public int getIndex() {
		return Index;
	}

	public boolean isFieldCard()
	{
		if (isBonus() || isEmpty() )
			return false;
		return true;
	}
	public boolean isBonus()
	{
		if (Set == BONUS )
			return true;
		return false;
		
	}
	public boolean isEmpty()
	{
		if (Type == EMTY)
			return true;
		return false;
		
	}
	public final String toString()
	{
		return new String("Card : index=" + Index + ", Set=" + Set + ",Line="
				+ Line + ",Type=" + Type +",TypeSub="+TypeSub);
	}
}
class CardSet 
{
	//12 X 4 + 2
	public static ArrayList<Card> cardsetsAl = new ArrayList<Card>();
	public static final int CARDMAX = 50;

	public static boolean loaded = false;
	protected static void cardsetting()
	{
		Card[] cards = new Card[CARDMAX+1];
		
		cards[0] = new Card(0,1,1,Card.GWANG);
		cards[1] = new Card(1,1,2,Card.TTI , Card.HONGDAN);
		cards[2] = new Card(2,1,3,Card.PEE );
		cards[3] = new Card(3,1,4,Card.PEE);
		cards[4] = new Card(4,2,1,Card.YEOLGGOT , Card.GODORI);
		cards[5] = new Card(5,2,2,Card.TTI , Card.HONGDAN);
		cards[6] = new Card(6,2,3,Card.PEE);
		cards[7] = new Card(7,2,4,Card.PEE);
		cards[8] = new Card(8,3,1,Card.GWANG);
		cards[9] = new Card(9,3,2,Card.TTI , Card.HONGDAN);
		
		cards[10] = new Card(10,3,3,Card.PEE);
		cards[11] = new Card(11,3,4,Card.PEE);	
		cards[12] = new Card(12,4,1,Card.YEOLGGOT, Card.GODORI);
		cards[13] = new Card(13,4,2,Card.TTI , Card.CHODAN);
		cards[14] = new Card(14,4,3,Card.PEE);
		cards[15] = new Card(15,4,4,Card.PEE);	
		cards[16] = new Card(16,5,1,Card.YEOLGGOT);
		cards[17] = new Card(17,5,2,Card.TTI , Card.CHODAN);
		cards[18] = new Card(18,5,3,Card.PEE);
		cards[19] = new Card(19,5,4,Card.PEE);	
		
		cards[20] = new Card(20,6,1,Card.YEOLGGOT);
		cards[21] = new Card(21,6,2,Card.TTI , Card.CHEONGDAN);
		cards[22] = new Card(22,6,3,Card.PEE);
		cards[23] = new Card(23,6,4,Card.PEE);		
		cards[24] = new Card(24,7,1,Card.YEOLGGOT);
		cards[25] = new Card(25,7,2,Card.TTI , Card.CHODAN);
		cards[26] = new Card(26,7,3,Card.PEE);
		cards[27] = new Card(27,7,4,Card.PEE);		
		cards[28] = new Card(28,8,1,Card.GWANG);
		cards[29] = new Card(29,8,2,Card.YEOLGGOT,  Card.GODORI);
		
		cards[30] = new Card(30,8,3,Card.PEE);
		cards[31] = new Card(31,8,4,Card.PEE);		
		cards[32] = new Card(32,9,1,Card.YEOLGGOT, Card.GUCKGIN);
		cards[33] = new Card(33,9,2,Card.TTI  , Card.CHEONGDAN);
		cards[34] = new Card(34,9,3,Card.PEE);
		cards[35] = new Card(35,9,4,Card.PEE);		
		
		cards[36] = new Card(36,10,1,Card.YEOLGGOT);
		cards[37] = new Card(37,10,2,Card.TTI  , Card.CHEONGDAN);
		cards[38] = new Card(38,10,3,Card.PEE);
		cards[39] = new Card(39,10,4,Card.PEE);		
		
		cards[40] = new Card(40,11,1,Card.GWANG);
		cards[41] = new Card(41,11,2,Card.PEE, Card.SSANPEE);
		cards[42] = new Card(42,11,3,Card.PEE);
		cards[43] = new Card(43,11,4,Card.PEE);		
		cards[44] = new Card(44,12,1,Card.GWANG, Card.BEGWANG);
		cards[45] = new Card(45,12,2,Card.YEOLGGOT);
		cards[46] = new Card(46,12,3,Card.TTI);
		cards[47] = new Card(47,12,4,Card.PEE, Card.SSANPEE);		
		
		cards[48] = new Card(48,13,1,Card.PEE , Card.BONUS2);
		cards[49] = new Card(49,13,2,Card.PEE , Card.BONUS3);
		
		
		cards[50] = new Card(50,14,3,Card.EMTY);                     
		for (int i = 0 ; i < CARDMAX+1 ; i++)
		{
			cardsetsAl.add(cards[i]);
			
		}
		loaded = true;
	}
	public CardSet()
	{

/*
		"일월 이월 "
		"Second"
		
*/		
	}
	public static final Card getCardIndex(int index)
	{
		if (!loaded)
		{
			cardsetting();
		}
		return (Card) cardsetsAl.get(index);
		
	}

	/**
	 * @return the cardsetsAl
	 */
	public static ArrayList<Card> getCardsetsAl() {
		return cardsetsAl;
	}
}