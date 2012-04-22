package us.tehonlygod.rockcrabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.bot.event.listener.PaintListener;



@Manifest(name = "Flawless Rock", authors = "TehOnlyGod", version = 1.0, description = "Designed with everyone in mind!", premium = true, website = "")
public class RockCrabs extends ActiveScript implements
PaintListener, MouseListener{
	private transient final long startTime = System.currentTimeMillis();
	final DecimalFormat df = new DecimalFormat();
	final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	FlawlessRockCrabGUI gui;
	private int skill = Skills.STRENGTH;

	private int startExperience;
	private int startConExperience;
	private RegainAgro regain;
	@Override
	protected void setup() {

		gui = new FlawlessRockCrabGUI();
		gui.setVisible(true);


		EatFood eat = new EatFood(gui);
		provide(eat);



		WalkToCrabs walkBack = new WalkToCrabs(gui);
		provide(walkBack);

		KillShit kill = new KillShit(gui);
		provide(kill);
		GoGetFood gatherFood = new GoGetFood(gui);
		provide(gatherFood);
		
		regain = new RegainAgro(gui, walkBack);
		provide(regain);
		
		HotFixes fix = new HotFixes(regain);
		provide(fix);
		startExperience = Skills.getExperiences()[skill];
		startConExperience = Skills.getExperiences()[Skills.CONSTITUTION];

	}

	private final Color color1 = new Color(0, 0, 0, 138);
	private final Color color2 = new Color(0, 204, 204, 21);
	private final Color color3 = new Color(204, 204, 0, 64);
	private final Color color4 = new Color(0, 0, 153, 107);
	private final Color color5 = new Color(0, 204, 255);

	private final Font font1 = new Font("Arial", 1, 11);
	private final Font font2 = new Font("Arial", 3, 10);
	private final Font font3 = new Font("Arial", 0, 11);

	private final Color color10 = new Color(0, 0, 0, 180);
    private final Color color12 = new Color(0, 0, 0);
    private final Color color13 = new Color(0, 255, 255);

    private final BasicStroke stroke10 = new BasicStroke(2);
    
    private final Color color100 = new Color(0, 0, 0);

    private final Font font100 = new Font("Arial", 0, 11);



    private final Font font10 = new Font("Arial", 3, 14);
	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		long elapsedTime = System.currentTimeMillis() - startTime;
		final int totalXp = Skills.getExperiences()[skill]
				- startExperience;
		final int hpExp = Skills.getExperiences()[Skills.CONSTITUTION]
				- startConExperience;
		String message = "Elapsed time: " + formatTime(elapsedTime) + "";
		int nextLevelXp = (Skills.getExperienceAt(Skills.getLevel(skill)+2) - Skills.getExperience(skill));

		int xpPerHour = (int) ((3600000.0 / elapsedTime) * totalXp);
		long nextLevelTime = (long) ((double) nextLevelXp / (double) xpPerHour * 3600000);
		int nextLevelXpHp = (Skills.getExperienceAt(Skills.getLevel(Skills.CONSTITUTION)+2) - Skills.getExperience(Skills.CONSTITUTION));
		int xpPerHourHp = (int) ((3600000.0 / elapsedTime) * hpExp);
		long nextLevelTimeHp = (long) ((double) nextLevelXpHp
				/ (double) xpPerHourHp * 3600000);
		
		dfs.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(dfs);
		int i = 1;
		g.setColor(color1);
		g.fillRect(380+i, 168, 133+i, 166);
		g.setColor(color2);
		g.fillRect(384+i, 172, 125+i, 158);
		g.setColor(color3);
		g.fillRect(384+i, 200, 124+i, 131);
		g.setColor(color4);
		g.fillRect(384+i, 220, 125+i, 23);
		g.fillRect(384+i, 265, 125+i, 23);
		g.fillRect(384+i, 308, 125+i, 23);
		g.setFont(font1);
		g.setColor(color5);
		g.drawString("Flawless Rock Crabs", 390+i, 185);
		g.setFont(font2);
		
		g.drawString("     By TehOnlyGod", 390+i, 194);
		g.setFont(font3);
		g.drawString(message, 390+i, 213);
		g.drawString("Strength XP p/h: "+xpPerHour, 389+i, 235);
		g.drawString("Hp XP p/h: "+xpPerHourHp, 389+i, 257);
		g.drawString("Strength XP TNL: " + nextLevelXp, 389+i, 280);
		g.drawString("TTL Strength: " + formatTime(nextLevelTime), 389+i, 303);
		g.drawString("TTL Hp: " + formatTime(nextLevelTimeHp), 389+i, 325);
		g.setColor(color10);
        g.fillRect(443, 5, 72, 25);
        g.setColor(color12);
        g.setStroke(stroke10);
        g.drawRect(443, 5, 72, 25);
        g.setFont(font10);
        g.setColor(color13);
        g.drawString("Options", 453, 22);
        
        g.setFont(font100);
        g.setColor(color100);
       // g.drawString("Count: " + regain.getCount(), 421, 470);

		CustomMethods.PaintMouse(g);

	}

	private String formatTime(final long time) {
		final int sec = (int) (time / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
		return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":"
		+ (s < 10 ? "0" + s : s);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getX() >= 443 && e.getX() <= 443+72 && e.getY() >= 4 && e.getY()<=5+25){
			if(gui.isVisible() == false){
				gui.setVisible(true);
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
