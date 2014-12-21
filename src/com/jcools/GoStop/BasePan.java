package com.jcools.GoStop;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

class BasePan 
{
	HashMap<Integer,ArrayList> pan = new HashMap<Integer,ArrayList>();
	
	ArrayList<Point> pointlist = new ArrayList<Point>();
	ArrayList<Integer> KeyList = new ArrayList<Integer>();
	
	int[] IndexArray= new int[12];
	int cardpointarray[][] = {{300, 200} , {400,200} ,{100,200} ,{100,300},{100,400} , {400, 300} ,{200,400}, { 300,400} , {400,400} , {200,200} , { 10,300} , {500,300}};
	public BasePan()
	{
		// IndexArray ?
		for (int i = 0 ; i< 12 ; i++)
		{
			IndexArray[i] = -1;
		}
		int size = cardpointarray.length;
		
		// 게임판 가운데 카드 슬롯 위치
		for (int i = 0 ; i < size ; i++)
		{
			pointlist.add(new Point(cardpointarray[i][0],cardpointarray[i][1]));
		}
	}
	
	// 빈자리 슬롯 찾아서 새로운 카드셋 할당
	public void addSet(int set)
	{
		for (int i = 0 ; i < 12 ; i++)
		{
			if (IndexArray[i] == -1)
			{
				IndexArray[i] = set;
				return;
			}
		}
	}
	
	// 해당 카드셋 슬롯 삭제 (카드를 유저들이 전부 회수해 갔을때
	public void removeSet(int set)
	{
		for (int i = 0 ; i < 12 ; i++)
		{
			if (IndexArray[i] == set)
			{
				IndexArray[i] = -1;
				return;
			}
		}
	}
	
	// 전부 리셋
	public void Reset() {
		// TODO Auto-generated method stub
		
		KeyList.clear();
		for (int i = 0 ; i< 12 ; i++)
		{
			IndexArray[i] = -1;
		}
		pan.clear();
	}
	
	// 유저들 또는 카드 덱에서 나온 카드 밀어 넣기
	public int push(Card card) {
		return push(card.getIndex());
	}
	
	// 밀어 넣기 오버라이딩  인덱스만 이용한 버전
	public int push(int index) {
		
		// TODO Auto-generated method stub
		Card card = CardSet.getCardIndex(index);
		int set = card.getSet();
		
		// tempal이 비어있으면 새로운 슬롯 삽입.. 
		ArrayList tempal = (ArrayList) pan.get(set);
		if (tempal == null)
		{
			ArrayList al1 = new ArrayList();
			al1.add(index);
			pan.put(set, al1);
			addSet(set);
			return 1;
			
		}
		// 존재하면 추가 후 정렬
		else
		{
			tempal.add(index);
			Collections.sort(tempal);
			return tempal.size();
		}
	}
	
	// Draw용으로 게임판에서 카드 셋  목록 반환 
	public final int[] getSet()
	{
		return IndexArray;
	}
	
	public ArrayList geSetList(Card card)
	{
		return geSetList(card.getSet());
	}
	public ArrayList geSetList(int set) {
		// TODO Auto-generated method stub
		return (ArrayList) pan.get(set);
	}
	
	
	public Card removeCard(Card card)
	{
		ArrayList al = (ArrayList)pan.get(card.getSet());
		Iterator it = al.iterator();
		Card tempcard = null;
		while (it.hasNext())
		{
			int index = (Integer) it.next();
			if (card.getIndex() == index)
			{		
				it.remove();
				tempcard = CardSet.getCardIndex(index);
				break;
			}
		}
		if (al.isEmpty())
		{
			pan.remove(card.getSet());
			removeSet(card.getSet());
		}
		return tempcard;
	}
	public final HashMap getHash()
	{
		return pan;
	}
	public Point getPoint(int index)
	{
		return pointlist.get(index);
	}
	public int getIndex(int set) {
		// TODO Auto-generated method stub
		for (int i = 0 ; i < 12 ; i++)
		{
			if (IndexArray[i] == set)
			{
				return i;
			}
		}
		for (int i = 0 ; i < 12 ; i++)
		{
			if (IndexArray[i] == -1)
			{
				return i;
			}
		}
		return -1;
	}
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		
		return pan.isEmpty();
	}
	public boolean isContainSet(int set) {
		// TODO Auto-generated method stub
		int size = IndexArray.length;
		for (int i = 0 ; i < size ; i++)
		{
			if (IndexArray[i] == set)
			{
				return true;
			}
		}
		return false;
	}
	public boolean isTong() {
		// TODO Auto-generated method stub
		Iterator it = pan.keySet().iterator();
		while(it.hasNext())
		{
			ArrayList al = (ArrayList) pan.get(it.next());
			if (al.size() == 4)
				return true;
		}
		return false;
	}
	public Point getStackPoint()
	{
		return new Point(200,300);
	}
}
