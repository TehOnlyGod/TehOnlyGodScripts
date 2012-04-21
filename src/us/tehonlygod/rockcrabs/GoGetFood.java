package us.tehonlygod.rockcrabs;


import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;


public class GoGetFood extends Strategy implements Task{
	Area CRABAREA = new Area(new Tile(2692,3731,0), new Tile(2730,3705,0));
	public final Tile[] ESCAPE_PATH = {
			new Tile(2694, 3722, 0), new Tile(2698, 3725, 0),
			new Tile(2703, 3727, 0), new Tile(2708, 3728, 0),
			new Tile(2713, 3727, 0), new Tile(2715, 3722, 0),
			new Tile(2715, 3717, 0), new Tile(2715, 3712, 0),
			new Tile(2718, 3708, 0), new Tile(2722, 3705, 0),
			new Tile(2724, 3700, 0), new Tile(2726, 3695, 0),
			new Tile(2729, 3691, 0) 
	};

	public final Area BELOWCRABAREA = new Area(new Tile[] {
			new Tile(2691, 3713, 0), new Tile(2689, 3708, 0),
			new Tile(2687, 3703, 0), new Tile(2687, 3698, 0),
			new Tile(2687, 3693, 0), new Tile(2688, 3688, 0),
			new Tile(2688, 3683, 0), new Tile(2688, 3678, 0),
			new Tile(2687, 3673, 0), new Tile(2692, 3673, 0),
			new Tile(2697, 3673, 0), new Tile(2702, 3674, 0),
			new Tile(2707, 3676, 0), new Tile(2712, 3678, 0),
			new Tile(2717, 3679, 0), new Tile(2722, 3681, 0),
			new Tile(2726, 3684, 0), new Tile(2726, 3689, 0),
			new Tile(2725, 3694, 0), new Tile(2724, 3699, 0),
			new Tile(2724, 3704, 0), new Tile(2724, 3709, 0),
			new Tile(2722, 3714, 0) 
	});
	

	public final Area SAFEAREA = new Area(new Tile[] {
			new Tile(2681, 3504, 0), new Tile(2686, 3504, 0),
			new Tile(2691, 3504, 0), new Tile(2696, 3504, 0),
			new Tile(2701, 3504, 0), new Tile(2706, 3504, 0),
			new Tile(2711, 3504, 0), new Tile(2716, 3504, 0),
			new Tile(2721, 3504, 0), new Tile(2726, 3504, 0),
			new Tile(2731, 3504, 0), new Tile(2734, 3500, 0),
			new Tile(2734, 3495, 0), new Tile(2734, 3490, 0),
			new Tile(2732, 3485, 0), new Tile(2727, 3483, 0),
			new Tile(2722, 3482, 0), new Tile(2717, 3482, 0),
			new Tile(2712, 3482, 0), new Tile(2707, 3482, 0),
			new Tile(2702, 3480, 0), new Tile(2697, 3478, 0),
			new Tile(2692, 3478, 0), new Tile(2687, 3477, 0),
			new Tile(2682, 3478, 0), new Tile(2680, 3483, 0),
			new Tile(2680, 3488, 0), new Tile(2680, 3493, 0),
			new Tile(2680, 3498, 0), new Tile(2680, 3503, 0)
	});

	public final Tile[] PATHTOBANK = {
			new Tile(2688, 3483, 0), new Tile(2693, 3483, 0),
			new Tile(2698, 3483, 0), new Tile(2703, 3484, 0),
			new Tile(2708, 3485, 0), new Tile(2713, 3485, 0),
			new Tile(2718, 3485, 0), new Tile(2723, 3485, 0),
			new Tile(2726, 3489, 0), new Tile(2729, 3493, 0)
	};
	protected boolean food = false;
	protected boolean enabled = false;
	protected int foodID;
	protected FlawlessRockCrabGUI gui;

	public GoGetFood(FlawlessRockCrabGUI g){
		gui = g;
	}
	@Override
	public boolean validate(){
		
		enabled = gui.getStart();
		food = gui.getChckbxFood();
		foodID = CustomMethods.getFoodID(gui.getComboBox());
		return enabled && food && Inventory.getCount(foodID) == 0;

	}

	@Override
	public void run() {
		if(BELOWCRABAREA.contains(Players.getLocal().getLocation())|| CRABAREA.contains(Players.getLocal().getLocation())){
			CustomMethods.Traverse(ESCAPE_PATH);
			Time.sleep(500);
			if(!Players.getLocal().isInCombat())
			CustomMethods.teleportHome("seers");
		} else if(SAFEAREA.contains(Players.getLocal().getLocation())){
			if(Tabs.getCurrent() != Tabs.INVENTORY)
			Widgets.get(548,93).click(true);
			if(Calculations.distance(Players.getLocal().getLocation(), new Tile(2725, 3491, 0)) < 10){
				if(Bank.isOpen()){
					Mouse.move(372,308);
					//Widgets.get(762, 32).click(true);
					Mouse.click(true);
					Time.sleep(2000);
					Bank.withdraw(foodID, 0);
					Time.sleep(2000);
				} else {
					Bank.open();
				}
			}else {
				CustomMethods.Traverse(PATHTOBANK);
			}
		}

	}
}
