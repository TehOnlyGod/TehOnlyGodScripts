package us.gamingautomated.rockcrabs;



import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.bot.event.listener.MessageListener;


public class HotFixes extends Strategy implements Task{
	public final Area area = new Area(new Tile[] {
			new Tile(2723, 3740, 0), new Tile(2723, 3735, 0),
			new Tile(2724, 3730, 0), new Tile(2724, 3725, 0),
			new Tile(2724, 3720, 0), new Tile(2725, 3715, 0),
			new Tile(2725, 3710, 0), new Tile(2725, 3705, 0),
			new Tile(2725, 3700, 0), new Tile(2725, 3695, 0),
			new Tile(2725, 3690, 0), new Tile(2729, 3687, 0),
			new Tile(2734, 3687, 0), new Tile(2739, 3687, 0),
			new Tile(2744, 3687, 0), new Tile(2749, 3687, 0),
			new Tile(2754, 3689, 0), new Tile(2758, 3692, 0),
			new Tile(2760, 3697, 0), new Tile(2760, 3702, 0),
			new Tile(2760, 3707, 0), new Tile(2760, 3712, 0),
			new Tile(2760, 3717, 0), new Tile(2760, 3722, 0),
			new Tile(2759, 3727, 0), new Tile(2756, 3731, 0),
			new Tile(2752, 3736, 0), new Tile(2749, 3740, 0),
			new Tile(2744, 3741, 0), new Tile(2739, 3742, 0),
			new Tile(2734, 3741, 0) 
	});
	Area CRABAREA = new Area(new Tile(2690,3731,0), new Tile(2730,3708,0));
	public final Tile[] path = {
			new Tile(2731, 3741, 0), new Tile(2730, 3736, 0),
			new Tile(2727, 3732, 0), new Tile(2725, 3727, 0),
			new Tile(2724, 3722, 0), new Tile(2723, 3717, 0),
			new Tile(2723, 3712, 0), new Tile(2723, 3707, 0),
			new Tile(2723, 3712, 0), new Tile(2722, 3717, 0),
			new Tile(2717, 3720, 0), new Tile(2712, 3721, 0),
			new Tile(2707, 3721, 0) 
	};
	private RegainAgro regain;
	public HotFixes(RegainAgro r){
		regain = r;
	}
	@Override
	public boolean validate(){
		return ((!Players.getLocal().isInCombat() && area.contains(Players.getLocal().getLocation())) || (CRABAREA.contains(Players.getLocal().getLocation()) && Players.getLocal().isInCombat()));
	}
	@Override
	public void run() {
		if(!Players.getLocal().isInCombat() && area.contains(Players.getLocal().getLocation())){
		CustomMethods.Traverse(path);
		} else if(CRABAREA.contains(Players.getLocal().getLocation()) && Players.getLocal().isInCombat()){
			regain.setCount(0);
		}
	}

}
