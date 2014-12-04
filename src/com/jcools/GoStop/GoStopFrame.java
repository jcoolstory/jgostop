package com.jcools.GoStop;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
class Prepare extends JPanel
{
	private Image background;

	public Prepare()
	{
		background = Toolkit.getDefaultToolkit().getImage("images/background_brown.png");
		setSize(400,500);
        //URL url = Prepare.class.getResource("images/background_brown.png");
       // background = getToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(background, 0);
            tracker.waitForID(0);
        } catch (Exception e) {}
		invalidate();
	}


	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0,0,getParent().getWidth(), getParent().getHeight());
		g.drawImage(background, 0,0, getParent().getWidth(), getParent().getHeight(), null);
		//g.drawString("getParent().getWidth()" + getParent().getWidth() + "/" + "getParent().getHeight()" + getParent().getHeight(), 200, 200);
//		long starttime = System.currentTimeMillis();
		Resource.LoadImage(this);
//		long endtime = System.currentTimeMillis();
	//	g.drawString("start : "+ starttime + " end : "+endtime + " delay :"  +( endtime - starttime), 100, 100);
	}
	
}
public class GoStopFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1001629318918318000L;

	/**
	 * 
	 */

	public GoStopFrame()
	{
		Dimension wdi = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(( int)(  wdi.getWidth() / 2 - 800 / 2 ), 
						(int)(  wdi.getHeight() / 2 - 700 / 2 ) ,
						(int)(  800	), 
						(int)(  700));

	//	getContentPane().setLayout(new BorderLayout());
		Prepare pre = new Prepare();
		getContentPane().add(pre);
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("게임");
		JMenuItem item = new JMenuItem("시작");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final GoStopMainPanel panel = new GoStopMainPanel();
				getContentPane().removeAll();
				getContentPane().add(panel);
				panel.setVisible(false);
				panel.setVisible(true);
				panel.Start();
			}
		});
		this.setJMenuBar(menubar);
		menubar.add(menu);
		menu.add(item);
		JMenuItem exitItem = new JMenuItem("종료");
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(EXIT_ON_CLOSE);
				
			}
		});
		menu.add(exitItem );
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//cp.setBounds(20, 20, 100, 100);
		
	}
}
