

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;


public class KillShit extends Strategy implements Task{
	protected final Area CRABAREA = new Area(new Tile(2690,3731,0), new Tile(2730,3708,0));
	protected final int[] CRABS = {1266, 1267, 1268};
	public final Tile[] path = {
			new Tile(2712, 3725, 0), new Tile(2707, 3725, 0),
			new Tile(2702, 3725, 0), new Tile(2697, 3725, 0),
			new Tile(2698, 3720, 0), new Tile(2701, 3716, 0),
			new Tile(2702, 3711, 0), new Tile(2705, 3704, 0),
			new Tile(2702, 3701, 0), new Tile(2702, 3696, 0),
			new Tile(2702, 3691, 0), new Tile(2702, 3686, 0),
			new Tile(2702, 3681, 0), new Tile(2702, 3676, 0)
	};
	protected boolean enabled = false;
	protected int count = 0;
	protected FlawlessRockCrabGUI gui;
	public KillShit(FlawlessRockCrabGUI g){
		gui = g;
	}
	@Override
	public boolean validate(){
		enabled = gui.getStart();

		return enabled && ((gui.getChckbxFood() && Inventory.getCount(CustomMethods.getFoodID(gui.getComboBox())) != 0 && (CRABAREA.contains(Players.getLocal().getLocation()) && !Players.getLocal().isInCombat() && Players.getLocal().getHpPercent() > 30) && NPCs.getNearest(new Filter<NPC>(){

			@Override
			public boolean accept(NPC n) {
				return n.getName().equalsIgnoreCase("Rock Crab") && CRABAREA.contains(n.getLocation()) && Calculations.distance(Players.getLocal().getLocation(), n.getLocation()) < 10 && ((gui.getChckbxLoot())|| (gui.getChckbxLoot()==false && !n.isInCombat() && n.getInteracting() == null));
			}

		})!=null) || (gui.getChckbxFood() == false && CRABAREA.contains(Players.getLocal().getLocation()) && !Players.getLocal().isInCombat() && Players.getLocal().getHpPercent() > 30 && NPCs.getNearest(new Filter<NPC>(){

			@Override
			public boolean accept(NPC n) {
				return n.getName().equalsIgnoreCase("Rock Crab") && CRABAREA.contains(n.getLocation()) && Calculations.distance(Players.getLocal().getLocation(), n.getLocation()) < 10 && ((gui.getChckbxLoot()) || (gui.getChckbxLoot()==false && !n.isInCombat() && n.getInteracting() == null));
			}

		})!=null));

	}
	@Override
	public void run() {
		NPC nearestCrab = NPCs.getNearest(new Filter<NPC>(){

			@Override
			public boolean accept(NPC n) {
				return n.getName().equalsIgnoreCase("Rock Crab") &&  CRABAREA.contains(n.getLocation()) && Calculations.distance(Players.getLocal().getLocation(), n.getLocation()) < 10 && ((gui.getChckbxLoot()) || (gui.getChckbxLoot()==false && !n.isInCombat() && n.getInteracting() == null));

			}

		});

		if(nearestCrab != null){
			if(nearestCrab.getInteracting() == null || gui.getChckbxLoot() ){
				if(nearestCrab.isOnScreen()){
					if(Players.getLocal().getAnimation() == -1 || Players.getLocal().isMoving()){
						if(nearestCrab.interact("Attack")){
							Time.sleep(Random.nextInt(2500, 4000));
						}
					}
				} else {
					Camera.turnTo(nearestCrab);
					Walking.walk(nearestCrab.getLocation());
					Time.sleep(Random.nextInt(250, 400));
				}
			}
		} else {
			if(count < 20){
				Time.sleep(150);
				count++;
			} else {
				while(CRABAREA.contains(Players.getLocal().getLocation())){
					CustomMethods.Traverse(path);
				}
				count = 0;
			}
		}

	}

	
}
