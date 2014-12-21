package com.jcools.GoStop;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


class CardStack
{
	public static final int CARDMAX = 50;
	public Stack<Card> cardStack = new Stack<Card>();
	public int PopCardIndex()
	{
		return popCard().getIndex();
	}
	public int getEnoughSize()
	{
		return cardStack.size();
	}
	public final Stack<Card> getList()
	{
		return cardStack;
	}
	public final Card popCard()
	{
		if (!cardStack.isEmpty())
			return cardStack.pop();
		
		return null;
	}
	
	// 카드 섞기..
	public void RandCard()
	{
		cardStack.clear();
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0 ; i < CARDMAX ; i++)
		{
			al.add(i);
		}
		Random rand = new Random();
		int t=0;
		for (int i = 0 ; i < CARDMAX ; i++)
		{
			t = rand.nextInt(al.size());
			cardStack.push(CardSet.getCardIndex(al.get(t)));
			al.remove(t);
		}
	}
}