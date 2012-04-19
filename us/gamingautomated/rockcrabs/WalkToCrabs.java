package us.gamingautomated.rockcrabs;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

import us.gamingautomated.methods.CustomMethods;
import us.gamingautomated.methods.Paths;

public class WalkToCrabs  extends Strategy implements Task{
	protected final Area CRABAREA = new Area(new Tile(2692,3731,0), new Tile(2730,3705,0));
	protected boolean USEFOOD = false;
	protected int FOOD = 0;
	protected boolean Enabled = false;
	protected boolean runnable = true;
	protected FlawlessRockCrabGUI gui;
	public WalkToCrabs(FlawlessRockCrabGUI g){
		gui = g;
	}
	@Override
	public boolean validate(){
		Enabled = gui.getStart();
		USEFOOD = gui.getChckbxFood();
		FOOD = CustomMethods.getFoodID(gui.getComboBox());
		
		return Enabled && runnable && ((USEFOOD && Inventory.getCount(FOOD) > 0 && !CRABAREA.contains(Players.getLocal().getLocation()) || USEFOOD == false && !CRABAREA.contains(Players.getLocal().getLocation())));

	}

	public boolean isRunnable() {
		return runnable;
	}
	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}
	public boolean isUSEFOOD() {
		return USEFOOD;
	}
	public void setUSEFOOD(boolean uSEFOOD) {
		USEFOOD = uSEFOOD;
	}
	public int getFOOD() {
		return FOOD;
	}
	public void setFOOD(int fOOD) {
		FOOD = fOOD;
	}
	public boolean isEnabled() {
		return Enabled;
	}
	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}
	@Override
	public void run() {
		CustomMethods.Traverse(Paths.SEERS_BANK_TO_ROCK_CRABS);
	}
}
