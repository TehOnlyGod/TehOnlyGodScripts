package us.tehonlygod.rockcrabs;


import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;


public class EatFood extends Strategy implements Task{
	protected int FOOD = 0;
	protected int PRECENTTOEATAT;
	protected boolean Enabled = false;
	protected FlawlessRockCrabGUI gui;
	public EatFood(FlawlessRockCrabGUI g){
		gui = g;
		PRECENTTOEATAT = Random.nextInt(35, 45);

	}
	public int getFOOD() {
		return FOOD;
	}
	public void setFOOD(int fOOD) {
		FOOD = fOOD;
	}
	public int getPRECENTTOEATAT() {
		return PRECENTTOEATAT;
	}
	public void setPRECENTTOEATAT(int pRECENTTOEATAT) {
		PRECENTTOEATAT = pRECENTTOEATAT;
	}
	public boolean isEnabled() {
		return Enabled;
	}
	public void setEnabled(boolean enabled) {
		Enabled = enabled;
		
	}
	@Override
	public boolean validate(){
		Enabled = gui.getStart();
		FOOD = CustomMethods.getFoodID(gui.getComboBox());
		return Enabled && (Inventory.getCount(FOOD) != 0 && Players.getLocal().getHpPercent() <= PRECENTTOEATAT);

	}

	@Override
	public void run() {
		Item[] items = Inventory.getItems();
		if(Tabs.getCurrent() != Tabs.INVENTORY)
			Widgets.get(548,93).click(true);
		for(Item i : items){
			if(i.getId() == FOOD){
				if(Players.getLocal().getHpPercent() <= PRECENTTOEATAT){
					i.getWidgetChild().click(true);
					Time.sleep(2000);
				}
			}
		}

	}
}
