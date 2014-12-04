package com.jcools.GoStop;

import java.awt.Component;
import java.awt.Point;

public class GoEngin extends GoEngin_O {
	GoStopMainPanel com;
	public GoEngin(Component com)
	{
		this.com = (GoStopMainPanel) com;
		p1 = new User("User");
		p1.setCardDeckPoint(540, 450);
		p1.setTakeCardPoint(580, 505);
		p1.setHasCardPoint(5,500);
		
		
		p2 = new Com_User("Com");
		p2.setCardDeckPoint(540, 150);
		p2.setTakeCardPoint(580, 15);
		p2.setHasCardPoint(5,5);
	}

	@Override
	public void PopUp(String string, long time) {
		// TODO Auto-generated method stub
		com.Popup(string, time);
	}

	@Override
	public void Draw() {
		// TODO Auto-generated method stub
		com.Draw();
	}

	@Override
	public void GameOver(User firstUser) {
		// TODO Auto-generated method stub
		com.GameOver(firstUser);
	}

	@Override
	public int ShowDialog(int showcard, User user, Card[] cards) {
		// TODO Auto-generated method stub
		
		return com.ShowDialog(showcard, user, cards);
	}

	@Override
	public int ShowDialog(int shake, User user) {
		// TODO Auto-generated method stub
		return com.ShowDialog(shake, user);
	}

	@Override
	public void animateCard(Card card, long time, Point fromPoint, Point toPoint,
			int flag) {
		// TODO Auto-generated method stub
		com.animateCard(card, time, fromPoint, toPoint, flag);
	}

	@Override
	public void animateCard(Card card,long time, Point fromPoint, Point toPoint) {
		// TODO Auto-generated method stub
		com.animateCard(card, time, fromPoint, toPoint);
	}

	public void Control(User user, int index) {
		// TODO Auto-generated method stub
		
	}
}
