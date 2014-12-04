package com.jcools.GoStop;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.jcools.GoStop.GoStopMainPanel.Animate;



abstract class GoEngin_O
{
	protected int turn =0;
	private int GameFlag = GameState.STOP;
	private boolean controlrunning = false;
	protected User p1 ;
	protected User p2 ;
	private BasePan basepan = new BasePan();
	private CardStack cardset = new CardStack();
	
	public GoEngin_O()
	{

	}

	public void setGameState(int flag)
	{
		GameFlag = flag;
	}
	public int getGameState()
	{
		return GameFlag;
	}
	public void takePoint(Point pt) {
		// TODO Auto-generated method stub
		if (GameState.PLAY != GameFlag)
			return;
		if (controlrunning)
			return;
		
		if (getP1().getTurn())
		{
//			//System.out.println("teak");
			int index = getP1().getPointtoIndex(pt);
			
			if ( index != -1)
			{
				Control(getP1(), index);
			}
		}
	}

	private void Control(User user, int index) {
		// TODO Auto-generated method stub
		control con = new control(user,index);
		
		con.start();
	}

	public void Start()
	{
		
		cardset.RandCard();
		
		p1.Reset();
		p2.Reset();
		basepan.Reset();
		
		control con = new control(control.START);
		con.start();
		GameFlag = GameState.START;
	}
	/**
	 * @return the p1
	 */
	public User getP1() {
		return p1;
	}
	/**
	 * @param p1 the p1 to set
	 */
	public void setP1(User p1) {
		this.p1 = p1;
	}
	/**
	 * @return the p2
	 */
	public User getP2() {
		return p2;
	}
	/**
	 * @param p2 the p2 to set
	 */
	public void setP2(User p2) {
		this.p2 = p2;
	}
	/**
	 * @return the basepan
	 */
	public BasePan getBasepan() {
		return basepan;
	}
	public CardStack getStack() {
		// TODO Auto-generated method stub
		return cardset;
	}
	public void UserSort() {
		// TODO Auto-generated method stub
		p1.SortCard();
		p1.setFlagList(basepan);
		p2.SortCard();
		p2.setFlagList(basepan);
	}
	public void SumScore() {
		// TODO Auto-generated method stub
		p1.calScore();
		p2.calScore();
	}
	

	public void TurnOff()
	{
		
		getP1().setTurn();
		getP2().setTurn();
		if (turn == 1)
		{
			turn = 2;
		}
		else
		{
			turn = 1;
		}
		
		TurnOn();
	}
	public void TurnOn()
	{

		if (getP2().getTurn())
		{
			putComputer();
		}
		Draw();
	}
	

	public void putComputer()
	{

		ComThread comthread = new ComThread();
		comthread.start();
	}
	abstract public void animateCard(Card card, long time, Point fromPoint, Point toPoint,int cards) ;
	abstract public void animateCard(Card card, long time, Point fromPoint,	Point toPoint) ;
	abstract public void PopUp(String string, long time) ;
	abstract public void Draw() ;
	abstract public void GameOver(User firstUser);
	abstract public int ShowDialog(int showcard, User user, Card[] cards);
	abstract public int ShowDialog(int shake, User user) ;
	
	
	class control extends Thread
	{
		User user; 
		int index;
		int flag = 0;
		public static final int TAKE_CARD = 1;
		public static final int START = 2;
		
		public control(User user, int index)
		{
			this.user = user;
			this.index = index;
			this.flag = TAKE_CARD;
		}
		public control(int flag)
		{
			this.flag = flag;
		}
		private void tackcards()
		{
			Point fromPoint = new Point();
			int result1 = 0;
			int stealcount = 0;
			int cardindex =user.getIndexCard(index);
			if (cardindex != -1)
			{
				Card takecard = CardSet.getCardIndex(cardindex);
				fromPoint =  user.getCardDeckPoint();
				//System.out.println("FromPoint | " + fromPoint);
				//System.out.println("fromPoint ---- " + fromPoint);
				if (takecard.isFieldCard())
				{
					int flag = user.getFlag(index);
					if (flag == User.BOMBABLE)
					{
						HashMap map = user.getCardSet(takecard);
						ArrayList al = (ArrayList) map.get("CARD");
						ArrayList Indexs =  (ArrayList) map.get("INDEX");
						int size = al.size();
						Iterator it = al.iterator();
						Card tcard = null;
						index = (Integer) Indexs.get(0);
						for (int x = 0 ; x < size ; x++)
						{
							//System.out.println("Bomb" + x);
							
							cardindex =user.removeIndexCard(index);	
	
							tcard = CardSet.getCardIndex(cardindex);
	
							throwPan(tcard, fromPoint);
							result1 = getBasepan().push(tcard);
						}
						for (int i = 0 ; i < al.size()-1 ; i++)
							user.push(50);
					}
					else
					{
						if (flag == User.SHAKEABLE)
						{
							if (ShowDialog(ASKTAG.SHAKE,user) == 0)
							{
								user.DoShake();
	
								Card tcard =null;
								
								HashMap map = user.getCardSet(takecard);
								
								ArrayList al = (ArrayList)map.get("CARD");
								int size = al.size();
								Iterator it = al.iterator();
								Card[] cards = new Card[size];
								for (int x = 0 ; x < size ; x++)
								{
									cardindex = (Integer)it.next();
									
									tcard = CardSet.getCardIndex(cardindex);
									cards[x] = tcard;
								}
								if (user.getName() == "Com")
								{
									ShowDialog(ASKTAG.SHOWCARD, user, cards);
								}
								int select = ShowDialog(ASKTAG.SELECTCARD, user, cards);
								takecard = cards[select];
								ArrayList Indexs = (ArrayList) map.get("INDEX");
								index = (Integer) Indexs.get(select);	
								
								
								PopUp("흔들기!", 1000);
							}
						}
					 
						user.removeIndexCard(index);	
	
						throwPan(takecard, fromPoint);
						result1 = getBasepan().push(takecard);
						//System.out.println("Result1 = " + result1);
					}
				}
				else
				{
					/*
					 * 	 user -> bonus
					 * 
					 */
					if (takecard.isBonus())
					{
						cardindex =user.removeIndexCard(index);	
	
						throwhas(takecard, fromPoint);
						user.SendCard(takecard.getIndex());
					
						takecard = getStack().popCard();
						Point toPoint = user.getTakeCardPoint(user.getSize());
						throwUser(takecard,toPoint);
						user.push(takecard);
						UserSort();
						stealCard();
						Draw();
						return;
					}
					user.removeIndexCard(index);
				}
	
				/*
				 *    deck -> pan
				 *   
				 */
		
				Card deckcard = getStack().popCard();
				fromPoint.setLocation(basepan.getStackPoint());
				
				if (deckcard.isBonus())
				{
					throwhas(deckcard, fromPoint);
					user.SendCard(deckcard);
					stealcount++;
					
					deckcard = getStack().popCard();	
					
					if (deckcard.isBonus())
					{
						throwhas(deckcard, fromPoint);
						user.SendCard(deckcard);
						stealcount++;
						deckcard = getStack().popCard();	
					}
				}
				throwPan(deckcard, fromPoint);
			
				int result2 = getBasepan().push(deckcard);
				//System.out.println("Result2 = " + result2);
		
				/*
				 *	  
				 */
				String str = "";
				if (takecard.getSet() == deckcard.getSet())
				{
					if (result2 == 2 )
					{
						str += "deepkiss";
	
						moveCard(takecard);
						stealcount++;
						PopUp("쪽!", 1000);
	
					}
					if (result2 == 3 )
					{
						str += "shit";
						
						PopUp("쌋다!!", 1000);
						
					}
					if (result2 == 4 )
					{
						str += "deepkiss";
						moveCard(takecard);
						stealcount++;
					}
				}
				else
				{
					if (result1 == 2 )
					{	
						str += "onepair";
						moveCard(takecard);
					}
					else if (result1 ==3)
					{
						str += "onepair";
						moveSelectCard(takecard);
					}
					
					else if (result1 ==4)
					{
						str += "bomb";
						moveCard(takecard);
						stealcount++;
					}
					if (result2 == 2)
					{
						str += "onepair";
						moveCard(deckcard);
					}
					else if (result2 == 3)
					{
						str += "onepair";
						moveSelectCard(deckcard);											
					}
					else if (result2 ==4)
					{
						str += "bomb";
						moveCard(deckcard);
						stealcount++;
					}
				}
				if (str == "")
				{
					str = "empty";
				}
				//System.out.println(str);
				//System.out.println(stealcount);
				if (getBasepan().isEmpty() && getStack().getEnoughSize() != 0)
				{
					stealcount++;
					PopUp("판쓸", 1000);
					
				}
				for (int i = 0 ; i < stealcount ; i++)
				{
					stealCard();
				}
				UserSort();
				SumScore();
				Draw();
				boolean end = false;
	
				if (user.isGoal())
				{
					if (user.getSize() != 0)
					{
						if (ShowDialog(ASKTAG.GOANDSTOP, user) == 0)
						{
							user.DoGo();
							PopUp(user.getGo() + " 고", 1000);
							
						}
						else
						{
							GameOver(user);
							return;
						}
					}
					else
					{
						GameOver(user);
						return;
					}
				}
				if (getStack().getEnoughSize() == 0)
				{
					GameOver(null);
					return;
				}
	
				if (!end)
					TurnOff();		
			}
		}
	
		private void startGame()
		{
			GameFlag = GameState.START;
			User FirstUser = getP1();
			User SecondUser = getP2();
			if (FirstUser.getTurn() != true)
			{
				User temp = FirstUser;
				FirstUser = SecondUser;
				SecondUser = temp;
			}
			
			Point fromPoint = new Point();
			Point toPoint = new Point();
			fromPoint.setLocation(basepan.getStackPoint());
			Card card = null;
			for (int r = 0 ; r < 2 ; r++)
			{
				for (int i = 0 ; i < 5 ; i++)
				{
					card = getStack().popCard();
					user = getP1();
					user.push(card.getIndex());
				}
				toPoint = p1.getTakeCardPoint(0);
	
				throwUser(card, toPoint);
				for (int i = 0 ; i < 5 ; i++)
				{
					user = getP2();
					card = getStack().popCard();
					user.push(card.getIndex());
				}
				toPoint = p2.getTakeCardPoint(0);
	
				throwUser(card, toPoint);
			}
			user = FirstUser;
			for (int i = 0 ; i < 8 ; i++)
			{
			
				card = getStack().popCard();
				
				if (card.isBonus())
				{
					//fromPoint = ((Rectangle)rects.get(index)).getLocation();
					
					throwhas(card, fromPoint);
					
					user.SendCard(card.getIndex());
					i--;
				}
				
				else
				{
					if ( (i % 4 )== 0)
						throwPanfast(card, fromPoint);
					getBasepan().push(card);
				}
			}
			throwPanfast(card, fromPoint);
			UserSort();
			if (getBasepan().isTong())
			{
				GameOver(FirstUser);
				return;
			}
			if (FirstUser.isTong())
			{
				GameOver(FirstUser);
				return;
			}
			if (SecondUser.isTong())
			{
				GameOver(SecondUser);
				return;
			}
			
	//		
			GameFlag = GameState.PLAY;
			TurnOn();
		}
	
	
		private void stealCard()
		{
			Point fromPoint = new Point();
			User user2 = null;
			if (user == getP1())
			{
				user2 = getP2();
				fromPoint = new Point(0,10);
			}
			else if (user == getP2())
			{
				user2 = getP1();
				fromPoint = new Point(0,500);
			}
			if (user2.getsizePee() == 0)
				return ;
			Point tpoint = new Point();
			
			tpoint.setLocation(user.getHasCardPoint(Card.PEE));
			fromPoint.translate(tpoint.x, tpoint.y);
			
			Card card = user2.removePee();
			throwhas(card,fromPoint);
			user.SendCard(card);
		}
		private void moveSelectCard(Card card)
		{
			getBasepan().removeCard(card);
			int i = getBasepan().getIndex(card.getSet());
			Point fromPoint = getBasepan().getPoint(i);
			throwhas(card, fromPoint);
			user.SendCard(card);
			ArrayList tal = getBasepan().geSetList(card);
			Card tcard =null;
			int size = tal.size();
			Iterator it = tal.iterator();
			Card[] cards = new Card[size];
			for (int x = 0 ; x < size ; x++)
			{
				flag = (Integer)it.next();
				
				tcard = CardSet.getCardIndex(flag);
				cards[x] = tcard;
			}
			int select = ShowDialog(ASKTAG.SELECTCARD, user, cards);
			tcard = cards[select];
			card = getBasepan().removeCard(tcard);
			throwhas(tcard, fromPoint);
			user.SendCard(tcard);
		}
		private void moveCard(Card card)
		{
			int i = getBasepan().getIndex(card.getSet());
			Point fromPoint = getBasepan().getPoint(i);
			ArrayList tal = getBasepan().geSetList(card);
			Card tcard =null;
			int size = tal.size();
			Iterator it = tal.iterator();
			int[] indexs = new int[size];
			for (int x = 0 ; x < size ; x++)
			{
				indexs[x] = (Integer)it.next();
				
			}
			for (int x = 0 ; x < size ; x++)
			{
				tcard = CardSet.getCardIndex(indexs[x]);
				getBasepan().removeCard(tcard);
				throwhas(tcard, fromPoint);
				user.SendCard(tcard);
			}
		}
		private void throwhas(Card card, Point fromPoint)
		{
			Point toPoint = new Point();
	
			toPoint = user.getHasCardPoint(card.getType());
	
			animateCard(card, 200, fromPoint, toPoint,Animate.CARDS);
	
			}
	
		private void throwPan(Card card, Point fromPoint)
		{
	
			int i = getBasepan().getIndex(card.getSet());
			Point toPoint = getBasepan().getPoint(i);
			
			animateCard(card, 200, fromPoint, toPoint);
			
		}
		private void throwPanfast(Card card, Point fromPoint)
		{
	
			int i = getBasepan().getIndex(card.getSet());
			Point toPoint = getBasepan().getPoint(i);
			
			animateCard(card, 300, fromPoint, toPoint,Animate.CARDS);
			
		}
		private void throwUser(Card card , Point toPoint)
		{
			Point fromPoint = basepan.getStackPoint();
			
			animateCard(card, 200, fromPoint, toPoint, Animate.CARDS);
		}
		public void run()
		{
			controlrunning = true;
			switch (flag)
			{
			case START:
				startGame();
				break;
			case TAKE_CARD:
				tackcards();
				break;
			}
			controlrunning = false;
		}
	}
	class ComThread extends Thread
	{
	
		public void run()
		{
			while(getGameState() == GameState.PLAY && getP2().getTurn())
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int i= 0;
				boolean select = false;
				int size = getP2().getSize();
				for ( i = 0 ; i < size ; i++)
				{
					if (getP2().isStar(i))
					{
						select = true;
						break;
					}
				}
				if (!select)
				{
					i = size-1;
				}
				control cont = new control(getP2(),i);
				cont.start();
				try {
					cont.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}