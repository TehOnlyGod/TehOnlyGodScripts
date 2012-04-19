package us.gamingautomated.methods;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class CustomMethods {

	public static SceneObject findClosestSceneObjectObject(int id){
		Player user = Players.getLocal();
		double ShortestDistance = Double.MAX_VALUE;
		SceneObject returnValue = null;
		for(SceneObject l : SceneEntities.getLoaded()){
			if(l.getId() == id && ShortestDistance > Calculations.distance(user.getLocation(), l.getLocation())){
				ShortestDistance = Calculations.distance(user.getLocation(), l.getLocation());
				returnValue = l;
			}
		}
		return returnValue;
	}
	public static void PrintoutMouseSceneObject(){
		System.out.println("X: " + Mouse.getX() + " Y: " + Mouse.getY());
	}
	
	public static int getNumberInStack(int id){
		for(Item i : Inventory.getItems()){
			if(i.getId() == id)
				return i.getWidgetChild().getChildStackSize();
		}
		return 0;
	}
	
	public static void PaintMouse(Graphics2D g){
		g.setColor(Color.CYAN);
		g.drawLine(Mouse.getX()-5, Mouse.getY(), Mouse.getX()+5, Mouse.getY());
		g.drawLine(Mouse.getX(), Mouse.getY()-5, Mouse.getX(), Mouse.getY()+5);
	}
	public static void TraverseOld(Tile[] t){
		int currentPoint = getNextTileOnPath(t);
		while(currentPoint <= t.length-1){
			Tile tmpTile = new Tile(t[currentPoint].getX()+Random.nextGaussian(-1, 0, 2), t[currentPoint].getY()+Random.nextGaussian(-1, 0, 2), 0);
			if(Calculations.distance(Players.getLocal().getLocation(), tmpTile) < 14){
				Energy();
				if(Walking.walk(tmpTile))
					currentPoint++;

				Time.sleep(Random.nextInt(800,900));
			} else return;
		}
	}
	public static void Traverse(Tile[] t){
		Tile walkHere = getNextTileOnPathReturnsTile(t);
		while(Calculations.distance(walkHere, Players.getLocal().getLocation())>2){
				Energy();
				Walking.walk(walkHere);
				Time.sleep(Random.nextInt(800,900));
				walkHere = getNextTileOnPathReturnsTile(t);
		}
	}
	public static Tile getNextTileOnPathReturnsTile(Tile[] t){
		Player user = Players.getLocal();
		for(int i = t.length-1; i >= 0; i--){
			if(Calculations.distance(t[i], user.getLocation()) < 14){
				return t[i];
			}
		}
		return null;
		/*for(int i = 0; i < t.length-1; i++){
			if(Calculations.distance(user.getLocation(), t[i]) < 14){
				if(Calculations.distance(user.getLocation(), t[i+1]) > 14){
					return t[i];
				}
			}
		}
		if(Calculations.distance(user.getLocation(), t[t.length -1]) <18){
			return t[t.length-1];
		}
		return t[-1];*/
	}
	public static int getNextTileOnPath(Tile[] t){
		Player user = Players.getLocal();
		for(int i = 0; i < t.length-1; i++){
			if(Calculations.distance(user.getLocation(), t[i]) < 18){
				if(Calculations.distance(user.getLocation(), t[i+1]) > 18){
					return i;
				}
			}
		}
		if(Calculations.distance(user.getLocation(), t[t.length -1]) <18){
			return t.length-1;
		}
		return -1;
	}

	public static int getCountOfItem(final int id){
		int count = Inventory.getCount(new Filter<Item>() {

			@Override
			public boolean accept(Item i) {

				return i.getId() == id;
			}
		});

		return count;
	}

	public static void Energy(){
		int r = Random.nextInt(15, 20);
		if(Walking.isRunEnabled() == false){
			Walking.setRun(true);
		}
		if(Walking.getEnergy() < r){
			Widgets.get(750, 2).interact("Rest");
			while(Walking.getEnergy() != 100 && !Players.getLocal().isInCombat()){
				Time.sleep(50);
			}
		}
	}

	public static int getFoodID(String s){
		if(s.equalsIgnoreCase("shark")){
			return 385;
		} else if(s.equalsIgnoreCase("lobster")){
			return 379;
		} else if(s.equalsIgnoreCase("monkfish")){
			return 7946;
		} else if(s.equalsIgnoreCase("swordfish")){
			return 373;
		} else if(s.equalsIgnoreCase("trout")){
			return 333;
		} else if(s.equalsIgnoreCase("tuna")){
			return 361;
		} else if(s.equalsIgnoreCase("salmon")){
			return 329;
		}
		return 0;

	}
	private static Point getCorrectTeleportWidget(String s){
		if(s.equals("seers")){
			return new Point(189,156);
		} else if (s.equalsIgnoreCase("catherby")){
			return new Point(220,181);
		} else if (s.equalsIgnoreCase("edgeville")){
			return new Point(292, 155);
		} else if (s.equalsIgnoreCase("lumbridge")){
			return new Point(324, 237);
		} else if(s.equalsIgnoreCase("draynor")){
			return new Point(307,210);
		} else if(s.equalsIgnoreCase("burthorpe")){
			return new Point(249,146);
		} else if(s.equalsIgnoreCase("ardougne")){
			return new Point(165,213);
		} else if (s.equalsIgnoreCase("al kharid")){
			return new Point(361,266);
		} else if (s.equalsIgnoreCase("bandit camp")){
			return new Point(325,292);
		} else if (s.equalsIgnoreCase("falador")){
			return new Point(276,203);
		} else if (s.equalsIgnoreCase("port sarim")){
			return new Point(275,246);
		} else if (s.equalsIgnoreCase("taverly")){
			return new Point(252, 180);
		} else if(s.equalsIgnoreCase("varrock")){
			return new Point(344,177);
		} else if(s.equalsIgnoreCase("yanile")){
			return new Point(158,261);
		}else if (s.equalsIgnoreCase("lunar isle")){
			return new Point(43, 76);
		} else {
			return new Point();
		}

	}
	public static void teleportHome(String teleportSceneObject){
		if(Players.getLocal().getAnimation() == -1){
			Widgets.get(548, 96).click(true);
			Time.sleep(500);

			if(Widgets.get(192, 15).getTextureId() != 1703){
				Widgets.get(192, 15).click(true);
				Time.sleep(200);
			} else {
				Mouse.click(574, 230, true);
				Time.sleep(1000);
			}

			Mouse.move((int)getCorrectTeleportWidget(teleportSceneObject).getX(),(int) getCorrectTeleportWidget(teleportSceneObject).getY());
			Mouse.click((int)getCorrectTeleportWidget(teleportSceneObject).getX(),(int) getCorrectTeleportWidget(teleportSceneObject).getY(), true);
			Time.sleep(10000);
			Widgets.get(548,93).click(true);
			Time.sleep(1000);
		}
	}

}
