package com.jcools.GoStop;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.swing.*;
public class GoStop {
	public static void main(String[] args)
	{
		JFrame frame = new GoStopFrame();
		frame.setVisible(true);
	}
//	public static void main(String[] args)
//	{
//		Cardtemp cardset = new Cardtemp();
//		
//		
//	}
	
}
class Cardtemp extends CardSet
{
	public Cardtemp()
	{
		cardsetting();
		int len = cardsetsAl.size();
		int width = 67;
		int height = 94;
		int x = 0;
		int y = 0;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0 ; i < len ; i++)
		{if (i % 12 == 0 && i !=0 )
		{
			x = 0;
			y += height;
		}
			Card card = cardsetsAl.get(i);
			int index = card.getIndex();
			int set = card.getLine();
			int line = card.getSet();
			int type = card.getType();
			int type2 = card.getTypeSub();
			int left = x;
			int top = y;
			
			
			
		
				
				
								
			String buff = String.format("%d/%d,%d/%d,%d,%d,%d",i+1 ,line,set,left,top,width,height);
			if (type2 == Card.EMTY)
			{
				buff += String.format("/%d", type);
			}
			else
			{
				buff += String.format("/%d,%d", type, type2);
			}
			sb.append(buff+"\n");
			x = left + width;

			
		}
		try
		{
			FileOutputStream fos = new FileOutputStream("go_card.data");
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write(sb.toString());
			osw.flush();
			osw.close();
			fos.close();
		}
		catch (Exception e) {
			
			// TODO: handle exception
		}
		//System.out.print(sb.toString());
	}
}