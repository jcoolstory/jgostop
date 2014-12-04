package com.jcools.GoStop;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

class UserState
{
	public int GWANG_SOCRE = 0;
	public int PEE_SCORE = 0;
	public int YEOLGGOT_SCORE = 0;
	public int TTI_SCORE =0;
	
	public int GWANG = 0;
	public int YEOLGGOT = 0;
	public int TTI = 0;
	public int PEE = 0;
	public boolean GODORI = false;
	public boolean CHODAN = false;
	public boolean CHEONGDAN = false;
	public boolean HONGDAN = false;
	public int MONTTA = 0;
	public int SHAKE = 0;
	public int GO = 0;
	public int SCORE = 0;

}
abstract class User_O
{
	public static final int EMPTY = 0;
	public static final int WILLPAIR = 1;
	public static final int SHAKEABLE = 2;
	public static final int CHONGTONG = 3;
	public static final int ONEPAIR = 5;
	public static final int BOMBABLE = 7;
	public final static long BASEMONEY = 100000;
	public Point DeckPoint = new Point();
	ArrayList cardsRect = new ArrayList();
	protected HashMap hasCardMap = new HashMap();
	long money = BASEMONEY;
	String name = "";
	ArrayList cardlist = new ArrayList();
	HashMap hasCard = new HashMap();
	int Score = 0;
	int[] Stars = new int[10];
	UserState userstate = new UserState();
	boolean Turn = false;
	int GoScore = 0;
	public int TotalScore = 0;

	public String getName()
	{
		return name;
	}
	public void setTurn()
	{
		if (Turn)
			Turn = false;
		else
			Turn = true;
	}
	public boolean getTurn()
	{
		return Turn;
	}
	public User_O(String str)
	{
		name = str;
		for (int i = 0 ; i < Stars.length ; i++)
		{
			Stars[i] = 0;
		}
	}
	public long getMoney()
	{
		return money;
	}
	public int getGo()
	{
		return userstate.GO;
	}
	public void SortCard()
	{
		Collections.sort(cardlist);
	}
	public void Reset() {
		// TODO Auto-generated method stub
		userstate = new UserState();
		cardlist.clear();
		hasCard.clear();
	//	Turn = false;
		GoScore = 7;
		Score = 0;
		for (int i = 0 ; i < Stars.length ; i++)
		{
			Stars[i] = 0;
		}
	}

	public void push(int index) {
		// TODO Auto-generated method stub
		cardlist.add(index);
		//Collections.sort(cardlist);
	}
	public void push(Card card) {
		// TODO Auto-generated method stub
		cardlist.add(card.getIndex());
		//Collections.sort(cardlist);
	}

	public void setFlagList(final BasePan basePan)
	{
		for (int i = 0 ; i < Stars.length ; i++)
		{
			Stars[i] = 0;
		}
		
		
		Iterator it = cardlist.iterator();
		for (int i = 0 ; i < cardlist.size()-1 ; i++)
		{
			Card ocard = CardSet.getCardIndex((Integer)cardlist.get(i));
			for (int j = i+1; j < cardlist.size() ; j++)
			{
				
				Card tcard = CardSet.getCardIndex((Integer)cardlist.get(j));
				if (ocard.isFieldCard() && ocard.getSet() == tcard.getSet())
				{
					Stars[i]++;
					Stars[j]++;
				}
			}
		}
		if (it == null)
			return;
		int index;
		int i= 0;
		while (it.hasNext())
		{
			index = (Integer)it.next();
			Card card = CardSet.getCardIndex(index);
			if (basePan.isContainSet(card.getSet()))
			{
				Stars[i] += 5 ;
			}
			i++;
		}
	}
	public int getFlag(int index)
	{
		return Stars[index];
	}
	public boolean isStar(int index)
	{
		if (Stars[index] > 4)
			return true;
		return false;
	}
	public int getTotalScore()
	{
		return TotalScore;
	}
	public int SumTotalScore(User user)
	{

		UserState wuser = userstate;
		UserState luser = user.getState() ;
		TotalScore = Score;
		int doublescore = 0;
		if (wuser.GO == 1)
		{
			TotalScore += 1;
		}
		else if (wuser.GO == 2)
		{
			TotalScore += 2;	
		}
		else if (wuser.GO == 3)
		{
			TotalScore *= 2;
		}
		else if (wuser.GO == 4)
		{
			TotalScore *= 3;
		}
		if (wuser.SHAKE > 0)
		{
			doublescore ++;
		}
		if (wuser.GO > 0)
		{
			if (wuser.GO > 2)
			{
				doublescore ++;
			}
		}
		if (wuser.PEE_SCORE > 0 && luser.PEE < 6)
		{
			doublescore ++;
		}
		if (wuser.GWANG_SOCRE > 0 && luser.GWANG == 0 )
		{
			doublescore ++;
		}
		if (wuser.YEOLGGOT >= 7)
		{
			doublescore ++;
		}
		if ( luser.GO > 0 )
		{
			doublescore ++;
		}
		if (doublescore > 0)
		{
			TotalScore = TotalScore *(doublescore*2);
		}
		return TotalScore;
	}
	public boolean isShakeable(int index)
	{
		
		int count = 0;
		int it = (Integer) cardlist.get(index);
		Card tempcard = CardSet.getCardIndex(it);
		int size = cardlist.size();
		for (int i = 0 ; i < size ; i++)
		{
			it = (Integer) cardlist.get(i);
			Card card = CardSet.getCardIndex(it);
			if (tempcard.getSet() == card.getSet())
			{
				count++;
			}
		}
	//	System.out.println("-[---Count " + count);
		
		if (count >= 3)
			return true;
		return false;
	}
	public int getIndexCard(int index)
	{
		try
		{
			int c = (Integer) cardlist.get(index);
			return  c;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	public int isEmpty(int index) {
		// TODO Auto-generated method stub
		try
		{
			int c = (Integer) cardlist.get(index);
			return  c;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	public void SendCard(Card card)
	{
		SendCard(card.getIndex());
	}
	public void SendCard(int index)
	{
		int type;
		ArrayList al = null;
		Card card = CardSet.getCardIndex(index);
	//	System.out.println("SendCard : " + card.toString());
		type = card.getType();
		al = (ArrayList) hasCard.get(type);
		if (al == null)
		{
			ArrayList tempal = new ArrayList();
			tempal.add(index);
			hasCard.put(type, tempal);
		}
		else
		{
			al.add(index);
			//Collections.sort(al);
		}
	}
	
//	public void SendCardList(ArrayList list) {
//		// TODO Auto-generated method stub
//	//	System.out.println("SendCardList");
//		Iterator it = list.iterator();
//		int index ;
//		int type;
//		ArrayList al = null;
//		while (it.hasNext())
//		{
//			index = (Integer) it.next();
//			Card card = CardSet.getCardIndex(index);
////			System.out.println("SendCard : " + card.toString());
//			type = card.getType();
//			al = (ArrayList) hasCard.get(type);
//			if (al == null)
//			{
//				ArrayList tempal = new ArrayList();
//				tempal.add(index);
//				hasCard.put(type, tempal);
//			}
//			else
//			{
//				al.add(index);
//				//Collections.sort(al);
//			}
//		}
//		
//	}
	public void calScore()
	{
		Score = 0;
		userstate.GWANG_SOCRE = 0;
		userstate.PEE_SCORE = 0;
		userstate.YEOLGGOT_SCORE = 0;
		userstate.TTI_SCORE =0;
		ArrayList al = null;
		al = (ArrayList)hasCard.get(Card.GWANG);
		if (al != null)
		{
			userstate.GWANG = al.size();
			if (al.size() == 3 )
			{
				Iterator it = al.iterator();
				boolean Beegwang = false;
				while (it.hasNext())
				{
					int temp = (Integer) it.next();
					Card card = CardSet.getCardIndex(temp);
					if (card.getTypeSub() == Card.BEGWANG)
						Beegwang = true;
				}
				if (Beegwang == true)
					userstate.GWANG_SOCRE = 2;
					//Score += 2;
				else
					userstate.GWANG_SOCRE = 3;
			}
			else if (al.size() == 4) 
			{
				userstate.GWANG_SOCRE = 4;
			}
			else if (al.size() == 5)
			{
				userstate.GWANG_SOCRE = 15;
			}
		}
		
		Score += userstate.GWANG_SOCRE;
		
		al = (ArrayList)hasCard.get(Card.YEOLGGOT);
		if (al != null)
		{
			
			int size = al.size();
			userstate.YEOLGGOT = size;
			if (al.size() >=  5 )
			{
				userstate.YEOLGGOT_SCORE =(size -4); 
				//Score +=  (size -4);
			}
			Iterator it = al.iterator();
			int godori = 0;
			while (it.hasNext())
			{
				int temp = (Integer) it.next();
				Card card = CardSet.getCardIndex(temp);
				if (card.getTypeSub() == Card.GODORI)
					godori ++;
			}
			if (godori == 3)
			{
				userstate.GODORI = true;
				Score +=5;
			}
		}
		Score += userstate.YEOLGGOT_SCORE;
		
		
		al = (ArrayList)hasCard.get(Card.TTI);
		if (al != null)
		{
			
			int size = al.size();
			userstate.TTI = size;
			Iterator it = al.iterator();
			if (al.size() >= 5 )
			{
				userstate.TTI_SCORE = (size -4);
				//Score +=  (size -4);
			}
			it = al.iterator();
			int chodan = 0;
			int hongdan = 0;
			int cheongdan = 0;
			while (it.hasNext())
			{
				int temp = (Integer) it.next();
				Card card = CardSet.getCardIndex(temp);
				if (card.getTypeSub() == Card.CHODAN)
					chodan ++;
				if (card.getTypeSub() == Card.HONGDAN)
					hongdan ++;
				if (card.getTypeSub() == Card.CHEONGDAN)
					cheongdan ++;
			}
			if (chodan == 3)
			{
				userstate.CHODAN = true;
				Score +=3;
			}
			if (hongdan == 3)
			{
				userstate.HONGDAN = true;
				Score +=3;
			}
			if (cheongdan == 3)
			{
				userstate.CHEONGDAN = true;
				Score +=3;
			}
			
		}
		Score += userstate.TTI_SCORE;
		al = (ArrayList)hasCard.get(Card.PEE);
		if (al != null)
		{
			userstate.PEE = al.size();
			Iterator it = al.iterator();
			while (it.hasNext())
			{
				int temp = (Integer) it.next();
				Card card = CardSet.getCardIndex(temp);
				if (card.getTypeSub() == Card.BONUS2)
					userstate.PEE +=1 ;
				if (card.getTypeSub() == Card.BONUS3)
					userstate.PEE +=2 ;
			}
			
			if (userstate.PEE > 10)
			{
				userstate.PEE_SCORE =userstate.PEE -10 ;
				//Score += (al.size() -10);
			}
		}
		Score += userstate.PEE_SCORE;
		userstate.SCORE = Score;
	}
	public int getSize()
	{
		return cardlist.size();
	}
	public int getScore()
	{
		return Score;
	}
	public int getsizePee() {
		// TODO Auto-generated method stub
		ArrayList al = (ArrayList)hasCard.get(Card.PEE);
		if (al == null)
			return 0;
		return al.size();
	}
	public ArrayList getCardListAll()
	{
		return cardlist;
	}
	public Card removePee() {
		// TODO Auto-generated method stub
		ArrayList al = (ArrayList)hasCard.get(Card.PEE);
		
		int count = al.size()-1;
		int returnindex = (Integer) al.get(count) ;
		
		int index = 0;
		
		for (int i = count ; i >= 0 ; i--)
		{
			index = (Integer)al.get(i);
			
			if (CardSet.getCardIndex(index).getTypeSub() == Card.EMTY)
			{
				returnindex = index;
				count = i;
				break;
			}
			else if (CardSet.getCardIndex(index).getTypeSub() < CardSet.getCardIndex(returnindex).getTypeSub());
			{
				returnindex = index;
				count = i;
			}
		}
		al.remove(count);
		return CardSet.getCardIndex(returnindex);
	}
	public HashMap getHasListAll()
	{
		return hasCard;
	}
	public final UserState getState()
	{
		//calScore();
		return userstate;
	}
	public HashMap getCardSet(Card card) {
		// TODO Auto-generated method stub
		HashMap cardmap = new HashMap();
		ArrayList<Integer> cards = new ArrayList<Integer>();
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		int count = 0;
		int size = cardlist.size();
		int it = (Integer) cardlist.get(0);
		Card tempcard = CardSet.getCardIndex(it);
		
		for (int i = 0 ; i < size ; i++)
		{
			it = (Integer) cardlist.get(i);
			tempcard = CardSet.getCardIndex(it);
			if (tempcard.getSet() == card.getSet())
			{
				cards.add(it);
				indexs.add(i);
			}
		}
	
		cardmap.put("INDEX", indexs);
		cardmap.put("CARD",  cards);
		return cardmap;
	}
	public int removeIndexCard(int index)
	{
		try
		{
			int c = (Integer) cardlist.remove(index);
			return  c;
		}
		catch(Exception e)
		{
			return -1;
		}
		
	}

	public void DoShake() {
		// TODO Auto-generated method stub
		userstate.SHAKE ++;
		
	}
	public void DoGo() {
		// TODO Auto-generated method stub
		userstate.GO++;
		GoScore = userstate.SCORE + 1;
	}
	public boolean isGoal()
	{
		if (GoScore <= userstate.SCORE)
			return true;
		return false;
	}
	public long StealMoney(long money) {
		// TODO Auto-generated method stub
		if (this.money > money)
		{
			this.money = this.money - money;
			return money;
		}
		else
		{
			long tmoney = this.money;
			this.money = 0;
			return tmoney;
		}
	}
	public void PutMoney(long money)
	{
		this.money = this.money + money;
	}
	public boolean isTong() {
		// TODO Auto-generated method stub
		int size = Stars.length;
		for (int i = 0 ; i < size ; i++)
		{
			if (Stars[i] == 3)
			{
				return true;
			}
		}
		return false;
	}
	public Point getTakeCardPoint(int index) {
		// TODO Auto-generated method stub
		Rectangle rect = (Rectangle)cardsRect.get(index);
		return rect.getLocation();
	}

	public Point getHasCardPoint(int flag)
	{
		return (Point) hasCardMap.get(flag);
	}
	public  void setCardDeckPoint(int x, int y)
	{
		DeckPoint = new Point(x,y);
		//hasCardMap.put("CardDeckPoint" , new Point(x,y));
	}
	public Point getCardDeckPoint()
	{
		return (Point) DeckPoint.clone();
	}
	public abstract int ask(int seletc);
	public abstract int setHasCardPoint(int x, int y);
	public abstract int setHasCardPoint(HashMap map);
//	public abstract void setTakeCardPoint(int width, int height);
}
