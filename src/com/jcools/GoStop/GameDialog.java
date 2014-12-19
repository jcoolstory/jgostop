package com.jcools.GoStop;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.*;
import java.awt.event.*;

class GameDialog {
	public static int ShowDialog(Component con ,int flag, User user)
	{
		return ShowDialog(con, flag,user,null);
	}
	public static int ShowDialog(Component con , int flag , User user , Object[] object)
	{
		String str = "";
		switch (flag)
		{
		case ASKTAG.GOANDSTOP:
			if (user.getName() == "Com")
			{
				return user.ask(2);
			}
			return JOptionPane.showConfirmDialog(
					con, "GO?", "갈까 말까", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null);
		case ASKTAG.SHOWCARD:
		{
			int size = object.length; 
			Card[] cards = new Card[size];
			ImageIcon[] imgs = new ImageIcon[size];
			for (int i = 0 ; i < size ; i++)
			{
				cards[i] = (Card)object[i];
				ImageIcon img =new ImageIcon();
				img.setImage(
					CardIamge.getImageIndex(
							cards[i].getIndex()));
				imgs[i] = img;
			}
			
			JOptionPane.showOptionDialog(con, "흔들어요", "흔들기", JOptionPane.NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null,imgs,0);
			return 0;
		}
		case ASKTAG.ENDGAME:
		
			User user1 = (User) object[0];
			User user2 = (User) object[1];
			UserState wuser  = user1.getState() ;
			UserState luser = user2.getState() ;
			final JDialog jd = new JDialog();
			jd.setModal(true);
			JPanel panel = new JPanel();
			JLabel label = new JLabel();
			JTextArea jta = new JTextArea();
			panel.setBackground(Color.white);
			jta.setFocusable(false);
			str = "\n" + user1.getName() + "이/가 승리";

			int doublescore = 0;
			
			str +="\r\n";
			str += "\n점수 " + wuser.SCORE ;
			str += "\n광 + " + wuser.GWANG_SOCRE;
			str += "\n열끗  + " + wuser.YEOLGGOT_SCORE;
			str += "\n띠  + " + wuser.TTI_SCORE;
			str += "\n피 + " + wuser.PEE_SCORE;
			str += "\n";
			if (wuser.GODORI)
				str += "고도리 + 3";
			if (wuser.CHEONGDAN)
				str += "청단  + 3";
			if (wuser.CHODAN)
				str += "초단  + 3";
			if (wuser.HONGDAN)
				str += "홍단 + 3";
			if (wuser.GO > 0)
			{
				
				str += "\n"+wuser.GO + " 고  ";
				if (wuser.GO > 2)
				{
					str += "X 2";
					doublescore ++;
				}
			}
			if (wuser.SHAKE > 0)
			{
				str +="\n흔들기 X 2   ";
				doublescore ++;
			}

			if (wuser.PEE_SCORE > 0 && luser.PEE < 5)
			{
				str += "피박 X 2";
				doublescore ++;
			}
			if (wuser.GWANG_SOCRE > 0 && luser.GWANG == 0 )
			{
				str += "광박 X 2";
				doublescore ++;
			}
			if ( luser.GO > 0 )
			{
				str += "고박 X 2";
				doublescore ++;
			}
			long total = 0;

			
			total = user1.getTotalScore() * 500;
			
			str += " \r\n \n" + user1.getTotalScore() + "점 X 500 원 = " + total + " 원";
			label.setText(str);
			jta.setText(str);
			panel.add(jta);
			jd.pack();
			
			jd.getContentPane().add(panel);
			
			jd.addMouseListener(new MouseAdapter()
			{
				public void mouseReleased(MouseEvent evt)
				{
					jd.dispose();
				}
			});
			jd.setSize(300,300);
			Point po = con.getLocation();
			Rectangle rect = con.getBounds();
			po.translate((int)(rect.getCenterX() - jd.getBounds().getWidth() / 2), (int)(rect.getCenterY() -jd.getHeight() / 2));
			jd.setLocation(po);
			jd.setTitle("클릭시 창을 닫습니다");
			jd.setVisible(true);
			return -1;
		case ASKTAG.SHAKE:
			if (user.getName() == "Com")
			{
				return user.ask(2);
			}
			return JOptionPane.showConfirmDialog(
					con, "흔들까요", "흔들까 말까", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null);
		case ASKTAG.BOMB:
		case ASKTAG.SELECTCARD:

			int size = object.length; 
			if (user.getName() == "Com")
			{
				return user.ask(size);
			}
			Card[] cards = new Card[size];
			ImageIcon[] imgs = new ImageIcon[size];
			for (int i = 0 ; i < size ; i++)
			{
				cards[i] = (Card)object[i];
				ImageIcon img =new ImageIcon();
				img.setImage(
					CardIamge.getImageIndex(
							cards[i].getIndex()));
				imgs[i] = img;
			}
			
			int tag = JOptionPane.showOptionDialog(con, "어느걸 드실까요", "먹어봅시다", JOptionPane.OK_OPTION,
					JOptionPane.QUESTION_MESSAGE, null,imgs,0);
			if (tag == -1 )
				tag = 0;
			return tag;
		case ASKTAG.STARTGAME:
			str ="";
			String[] strs = {"시작" , "종료"};
			return JOptionPane.showOptionDialog(con, "시작", "맞고", JOptionPane.OK_OPTION,
					JOptionPane.QUESTION_MESSAGE, null,strs,"시작");
		}
		return -1;
	}

	
}
