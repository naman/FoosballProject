package threads;

import exceptions.ShouldBeOverridenException;
import exceptions.UnsupportedMethodCalledException;
import game.Team;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import mainclasses.Game;
import notifications.Notification;
import basicUI.GraphicData;

public class TeamLogicThread extends JComponent implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Game game;
	Team team;
	
	@Override
	public void run()
	{
		this.team.threadID = Thread.currentThread().getId();
		try
		{
			while (true)
			{
				this.execute();
				Thread.sleep(100/GraphicData.FPS);
			}
		} catch (ShouldBeOverridenException
				| UnsupportedMethodCalledException | InterruptedException e){
				e.printStackTrace();
				System.exit(1);
			}
	}
	
	@SuppressWarnings("serial")
	public TeamLogicThread(Game game, Team team)
	{
		this.game = game;
		this.team = team;
		this.setFocusable(true);
		System.out.println("Starting");
		InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = this.getActionMap();
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");
		
		am.put("UP", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("UP pressed");
			}
		});
		
		
		am.put("DOWN", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Down pressed");
			}
		});
		

		am.put("W", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("W pressed");
			}
		});

		am.put("S", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("S pressed");
			}
		});
		
	}
	
	/**
	 * Simply use handleByRenderThread, handlebyTeamLogicThread
	 * and handleByMainThread of the notification object in
	 * the corresponding threads.
	 * 
	 * Create new notifications by setting game.notification
	 * to new notification objects.
	 * @param notification
	 */
	private void handleNotification(Notification notification)
	{
		notification.handleByTeamLogicThread();
	}

	private void execute() throws ShouldBeOverridenException, UnsupportedMethodCalledException, InterruptedException
	{	
		
//		while(true)
//		{
//			if (Thread.currentThread().getId() == game.getField().getTeamA().threadID)
//			{
//				if(ConcurrencyCheck.teamAThreadDone)
//				{
//					Thread.sleep(10);
//				}
//				else
//				{
//					ConcurrencyCheck.teamAThreadDone = true;
//					break;
//				}
//			}
//			else
//			{
//				if(ConcurrencyCheck.teamAThreadDone && !ConcurrencyCheck.teamBThreadDone)
//				{
//					ConcurrencyCheck.teamBThreadDone = true;
//					break;
//				}
//				else
//				{
//					Thread.sleep(10);
//				}
//			}
//		}
//		
		while(true)
		{
			if (game.notification == null) break;
			if (!game.notification.isHandlingLeft())
			{
				System.out.println("breaking");
				break;
			}
			this.handleNotification(game.notification);
		}
	
		this.game.notification = null;
		Robot bot = null;
		try {
			bot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		if (this.game.getField().getTeamB().threadID == Thread.currentThread().getId())
		{
				this.team.mirrorBall(bot);
		}		
		Thread.sleep((int)Math.pow(10,(1.0/(0.99-this.game.getContext().getCompError()))));
		this.team.play();
	}
}