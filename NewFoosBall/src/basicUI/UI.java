package basicUI;

import game.Field;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import mainclasses.Game;


public class UI extends JFrame{

	private static final long serialVersionUID = 1L;
	Field field;
	Game game;
	JPanel panel;
	JPanel panel1;
	
	public static JLabel timerLabel;
    public static JLabel scoreLabel;
	
	public UI(Game game) 
	{
		this.game = game;
		this.field = game.getField();
	}
    
	
	public void initUI()
	{
    	setSize(GraphicData.playingDimension);

        try {
			getContentPane().add(new JPanelWithBackground("res/bg.jpg"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	DrawOnGlassPane aPanel = new DrawOnGlassPane(this.game);
        this.panel = aPanel.panel;
        this.panel.setOpaque(false);
        setGlassPane(panel);
        panel.setVisible(true);

        panel.setSize(this.getSize());

        
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        timerLabel = new JLabel("              Timer: 0");
        scoreLabel = new JLabel("        Score: 0 - 0");
        timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        statusPanel.add(timerLabel);
        statusPanel.add(scoreLabel);
        setTitle("Foosball");

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
	public static void updateScore(int scoreA, int scoreB)
	{
		scoreLabel.setText("        Score: " + scoreA + " - " + scoreB);
	}
	
	public static void updateTime(int a)
	{
		timerLabel.setText("              Timer: " + a);
	}
	
	@Override
	public void paint(Graphics g)
	{
		this.panel.repaint();
	}
}