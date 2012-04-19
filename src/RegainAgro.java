

import java.nio.file.Path;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class RegainAgro extends Strategy implements Task{
	protected FlawlessRockCrabGUI gui;
	protected boolean enabled = false;
	protected int randomVar1=0;
	protected int randomVar2=0;
	protected int randomVar3=0;
	protected int randomVar4=0;
	protected int randomVar5=0;
	protected final Area CRABAREA = new Area(new Tile(2690,3750,0), new Tile(2730,3708,0));
	protected int count = 0;
	protected WalkToCrabs walkBack;
	final Tile firstPlaceToWalk = new Tile(2704, 3726,0); final Tile firstPlaceToWalk1 = new Tile(2702, 3726, 0); final Tile firstPlaceToWalk2 = new Tile(2705, 3725,0);
	final Tile secondPlaceToWalk = new Tile(2701, 3719, 0);final Tile secondPlaceToWalk1 = new Tile(2703, 3720, 0);final Tile secondPlaceToWalk2 = new Tile(2702, 3716, 0);final Tile secondPlaceToWalk3 = new Tile(2699, 3717, 0);
	final Tile thirdPlaceToWalk = new Tile(2709, 3718, 0);final Tile thirdPlaceToWalk1 = new Tile(2712, 3718, 0);final Tile thirdPlaceToWalk2 = new Tile(2711, 3716, 0);
	final Tile forthPlaceToWalk = new Tile(2710, 3725,0);
	public final Tile[] lastPlaceToWalk = {
			new Tile(2708, 3726, 0), new Tile(2703, 3728, 0),
			new Tile(2698, 3726, 0), new Tile(2695, 3722, 0),
			new Tile(2699, 3720, 0), new Tile(2704, 3720, 0),
			new Tile(2709, 3722, 0), new Tile(2702, 3723, 0)
	};
	public final Tile[] path = {
			new Tile(2712, 3728, 0), new Tile(2707, 3726, 0),
			new Tile(2702, 3726, 0), new Tile(2697, 3725, 0),
			new Tile(2701, 3722, 0), new Tile(2702, 3717, 0),
			new Tile(2704, 3712, 0), new Tile(2704, 3707, 0),
			new Tile(2702, 3702, 0), new Tile(2700, 3697, 0),
			new Tile(2697, 3693, 0), new Tile(2695, 3688, 0),
			new Tile(2695, 3683, 0), new Tile(2693, 3675, 0),
			new Tile(2700, 3668, 0) 
	};
	final Tile ENDOFPATH = new Tile(2693, 3668, 0);
	static int x=0;
	static int y=0;
	final Tile resetTile = new Tile(2702, 3687, 0);
	public RegainAgro(FlawlessRockCrabGUI g, WalkToCrabs w){
		gui = g;
		walkBack = w;
		randomVar1=Random.nextInt(23, 27);
		randomVar2=Random.nextInt(33, 37);
		randomVar3=Random.nextInt(43, 47);
		randomVar4=Random.nextInt(55, 65);
		randomVar5 = Random.nextInt(85, 95);
	}
	public void setCount(int i){
		count = i;
	}
	@Override
	public boolean validate(){
		enabled = gui.getStart();
		return enabled && (CRABAREA.contains(Players.getLocal().getLocation()) || count >=80) && Players.getLocal().getAnimation() == -1 && NPCs.getNearest(new Filter<NPC>(){

			@Override
			public boolean accept(NPC n) {
				try{
					return n.getName().equalsIgnoreCase("Rock Crab") && CRABAREA.contains(n.getLocation()) && (gui.getChckbxLoot() || (gui.getChckbxLoot()==false && (((n.getInteracting() != null) && n.getInteracting().getName().equalsIgnoreCase(Players.getLocal().getName())))));
				} catch (NullPointerException e){

					return true;
				}
			}	})==null;

	}

	public int getCount(){
		return count;
	}
	@Override
	public void run() {

		if(count < 120){
			NPC nearestRock = NPCs.getNearest(new Filter<NPC>(){
				@Override
				public boolean accept(NPC n){
					return( CRABAREA.contains(n.getLocation()) && (n.getId() == 1265 || n.getId() == 1266 || n.getId() == 1267 || n.getId() == 1268)&& !n.getName().equalsIgnoreCase("Rock Crab"));
				}
			});
			if(Calculations.distance(nearestRock.getLocation(), Players.getLocal().getLocation()) > 1){
				Walking.walk(nearestRock);
			} else if(isUnAgro()){
				count = 121;
			}else if(Players.getLocal().isInCombat()){
				count =0;
			}
			Time.sleep(Random.nextInt(900,1100));
			if(!Players.getLocal().isInCombat())
				count++;
		}else{
			walkBack.setRunnable(false);
			CustomMethods.Traverse(path);
			Time.sleep(150);
			count++;
			if(count > 140){
				count = 0;
				walkBack.setRunnable(true);
			}
		}

	}

	private boolean isUnAgro(){
		x =Players.getLocal().getLocation().getX();
		y =Players.getLocal().getLocation().getY();

		return (NPCs.getNearest(new Filter<NPC>(){

			@Override
			public boolean accept(NPC n) {
				return n.getName().equalsIgnoreCase("Rocks") && 
						(n.getLocation().getX() == x || n.getLocation().getX() == x-1 || n.getLocation().getX() == x+1) &&
						(n.getLocation().getY() == y || n.getLocation().getY() == y-1 || n.getLocation().getY() == y+1);
			}	}) != null);

	}

}
