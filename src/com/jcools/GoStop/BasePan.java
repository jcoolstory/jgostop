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
		
		// ������ ��� ī�� ���� ��ġ
		for (int i = 0 ; i < size ; i++)
		{
			pointlist.add(new Point(cardpointarray[i][0],cardpointarray[i][1]));
		}
	}
	
	// ���ڸ� ���� ã�Ƽ� ���ο� ī��� �Ҵ�
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
	
	// �ش� ī��� ���� ���� (ī�带 �������� ���� ȸ���� ������
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
	
	// ���� ����
	public void Reset() {
		// TODO Auto-generated method stub
		
		KeyList.clear();
		for (int i = 0 ; i< 12 ; i++)
		{
			IndexArray[i] = -1;
		}
		pan.clear();
	}
	
	// ������ �Ǵ� ī�� ������ ���� ī�� �о� �ֱ�
	public int push(Card card) {
		return push(card.getIndex());
	}
	
	// �о� �ֱ� �������̵�  �ε����� �̿��� ����
	public int push(int index) {
		
		// TODO Auto-generated method stub
		Card card = CardSet.getCardIndex(index);
		int set = card.getSet();
		
		// tempal�� ��������� ���ο� ���� ����.. 
		ArrayList tempal = (ArrayList) pan.get(set);
		if (tempal == null)
		{
			ArrayList al1 = new ArrayList();
			al1.add(index);
			pan.put(set, al1);
			addSet(set);
			return 1;
			
		}
		// �����ϸ� �߰� �� ����
		else
		{
			tempal.add(index);
			Collections.sort(tempal);
			return tempal.size();
		}
	}
	
	// Draw������ �����ǿ��� ī�� ��  ��� ��ȯ 
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