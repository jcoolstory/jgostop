package com.jcools.GoStop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Resource
{
	static ArrayList<Image> images = new ArrayList<Image>();
	static BufferedImage StarImage = null;
	static public Image Tag01 = null;
	static public Image[] DeckCard = new Image[2];
	public Resource(){
		LoadImage(null);
	}
	
	public static void LoadImage(Component com)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
        try {
            MediaTracker tracker = new MediaTracker(com);
		DecimalFormat nf = new DecimalFormat("00");
		int index = 0;
		
		for (int i = 0 ; i < 12 ; i++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{
				Image img = tk.getImage("images/go_img" + nf.format(i+1) + "_" + nf.format(j+1) +".png");
				images.add(img);
				tracker.addImage(img, index);
				index++;
			}
			
		}
       
        
		Tag01 = tk.getImage("images/tag_01.png");
		tracker.addImage(Tag01, index);
		DeckCard[0] = tk.getImage("images/deckcard01.png");
		tracker.addImage(DeckCard[0], index);
		DeckCard[1] = tk.getImage("images/deckcard02.png");
		tracker.addImage(DeckCard[1], index);
		Image img1 = tk.getImage("images/go_img13_01.png");
		tracker.addImage(img1, index);
		Image img2 = tk.getImage("images/go_img13_02.png");
		tracker.addImage(img2, index);
		Image img3 = tk.getImage("images/go_img14_01.png");
		tracker.addImage(img1, index);
		images.add(img1);
		images.add(img2);
		images.add(img3);
		
		BufferedImage StarImage = new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D g2d = StarImage.createGraphics();
		g2d.setColor(Color.YELLOW);
		g2d.fillOval(0, 0,8, 8);
		tracker.addImage(img1, index);
		tracker.waitForAll();

    } catch (Exception e) {}
		//g2d.dispose();
	}
	public static Image getImageIndex(int index)
	{
		return images.get(index);
	}
	public static Image getImageBehind() {
		// TODO Auto-generated method stub
		
		return images.get(images.size()-1);
	}
}