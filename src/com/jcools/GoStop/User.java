package com.jcools.GoStop;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Random;

class User extends User_O {
	public User(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}
	public int getPointtoIndex(Point point)
	{
		for (int i = 0 ; i < 10 ; i++)
		{
			Rectangle rect = (Rectangle) cardsRect.get(i); 
			if (rect.contains(point))
			{
				return i;
			}
		}
		return -1;
	}
	public int ask(int seletc)
	{
		return -1;
	}

	@SuppressWarnings("unchecked")
	public void setTakeCardPoint(int x, int y) {
		// TODO Auto-generated method stub
	
		int width = 40;
		int height = 60;
		cardsRect.add(new Rectangle(x,y,width,height));
		cardsRect.add(new Rectangle(x + width,y,width,height));
		cardsRect.add(new Rectangle(x + width*2,y,width,height));
		cardsRect.add(new Rectangle(x + width*3,y,width,height));
		cardsRect.add(new Rectangle(x + width*4,y,width,height));
		
		cardsRect.add(new Rectangle(x,y+height,width,height));
		cardsRect.add(new Rectangle(x + width,y+height,width,height));
		cardsRect.add(new Rectangle(x + width*2,y+height,width,height));
		cardsRect.add(new Rectangle(x + width*3,y+height,width,height));
		cardsRect.add(new Rectangle(x + width*4,y+height,width,height));

	}

	@SuppressWarnings("unchecked")
	@Override
	public int setHasCardPoint(int x, int y) {
		// TODO Auto-generated method stub
		Point GWANG = new Point(10,0);
		Point YEOLGGOT = new Point(100,0);
		Point TTI = new Point(250,0);
		Point PEE = new Point(400,0);
		
		GWANG.translate(x, y);
		YEOLGGOT.translate(x, y);
		TTI.translate(x, y);
		PEE.translate(x, y);
		
		hasCardMap.put(Card.GWANG,GWANG);
		hasCardMap.put(Card.YEOLGGOT,YEOLGGOT);
		hasCardMap.put(Card.TTI, TTI);
		hasCardMap.put(Card.PEE, PEE);
		
		return 0;
	}

	@Override
	public int setHasCardPoint(HashMap map) {
		// TODO Auto-generated method stub
		return 0;
	}
}
class Com_User extends User
{

	public Com_User(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}
	public int ask(int select)
	{
		Random r = new Random();
		
		return r.nextInt(select);
	}

}
