
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
/*
 * credit for banking goes to xMunch
 * I claim no rights to it.
 */
public class Bank {

	private static final int[] BANKERS = {44, 45, 494, 495, 496, 497,
			498, 499, 553, 909, 958, 1036, 2271, 2354, 2355, 2718, 2759, 3198,
			3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 5488, 5901, 5912,
			6362, 6532, 6533, 6534, 6535, 7605, 8948, 9710, 14367};
	private static final int[] BANK_BOOTHS = {782, 2213, 2995, 5276,
			6084, 10517, 11402, 11758, 12759, 14367, 19230, 20325, 24914, 11338,
			25808, 26972, 29085, 52589, 34752, 35647, 36786, 2012, 2015, 2019,
			42217, 42377, 42378};
	private static final int[] DEPOSIT_BOXES = {2045, 9398, 20228, 24995, 25937,
			26969, 32924, 32930, 32931, 34755, 36788, 39830, 45079};
	private static final int[] BANK_CHESTS = {2693, 4483, 8981, 12308, 21301, 20607,
			21301, 27663, 42192};
	private static final int[] WIDGET_BANK_TAB = {65, 63, 61, 59, 57, 55, 53, 51, 49};

	private static final int BANK_TOGGLE_REARRANGE_MODE_SETTING = 304;
	private static final int BANK_SEARCH_SETTING = 1248;
	private static final int BANK_TOGGLE_WITHDRAW_MODE_SETTING = 115;
	private static final int WIDGET_BANK = 762;
	private static final int WIDGET_BANK_BUTTON_CLOSE = 45;
	private static final int WIDGET_BANK_BUTTON_DEPOSIT_EQUIPPED_ITEMS = 38;
	private static final int WIDGET_BANK_BUTTON_DEPOSIT_FAMILIAR_INVENTORY = 40;
	private static final int WIDGET_BANK_BUTTON_DEPOSIT_INVENTORY_ITEMS = 34;
	private static final int WIDGET_BANK_BUTTON_DEPOSIT_MONEY_POUCH = 36;
	private static final int WIDGET_BANK_BUTTON_HELP = 46;
	private static final int WIDGET_BANK_BUTTON_OPEN_EQUIP = 120;
	private static final int WIDGET_BANK_BUTTON_SEARCH = 18;
	private static final int WIDGET_BANK_BUTTON_SWAP = 16;
	private static final int WIDGET_BANK_BUTTON_WITHDRAW_TOGGLE = 19;
	private static final int WIDGET_BANK_INVENTORY = 95;
	private static final int WIDGET_BANK_ITEM_FREE_COUNT = 29;
	private static final int WIDGET_BANK_ITEM_FREE_MAX = 30;
	private static final int WIDGET_BANK_ITEM_MEMBERS_COUNT = 31;
	private static final int WIDGET_BANK_ITEM_MEMBERS_MAX = 32;
	private static final int WIDGET_BANK_SCROLLBAR = 116;
	private static final int WIDGET_BANK_SEARCH = 752;
	private static final int WIDGET_BANK_SEARCH_INPUT = 5;
	private static final int WIDGET_EQUIPMENT = 667;
	private static final int WIDGET_EQUIPMENT_COMPONENT = 9;
	private static final int WIDGET_COLLECTION_BOX = 109;
	private static final int WIDGET_COLLECTION_BOX_CLOSE = 14;
	private static final int WIDGET_DEPOSIT_BOX = 11;
	private static final int WIDGET_DEPOSIT_BOX_BUTTON_CLOSE = 15;
	private static final int WIDGET_DEPOSIT_BOX_INVENTORY = 17;
	private static final int WIDGET_DEPOSIT_BUTTON_DEPOSIT_EQUIPPED_ITEMS = 23;
	private static final int WIDGET_DEPOSIT_BUTTON_DEPOSIT_FAMILIAR_INVENTORY = 25;
	private static final int WIDGET_DEPOSIT_BUTTON_DEPOSIT_INVENTORY_ITEMS = 19;
	private static final int WIDGET_DEPOSIT_BUTTON_DEPOSIT_MONEY_POUCH = 21;

	public static boolean isOpen() {
		return Widgets.get(WIDGET_BANK).validate();
	}

	public static boolean isDepositOpen() {
		return Widgets.get(WIDGET_DEPOSIT_BOX).validate();
	}

	public static boolean open() {
		if (isOpen() || isDepositOpen()) {
			return true;
		}
		final Entity bankEntity = findBankEntity();
		if (bankEntity != null) {
			if (bankEntity.isOnScreen()) {
				if (bankEntity.interact("Bank") || bankEntity.interact("Use")
						|| bankEntity.interact("Open") || bankEntity.interact("Deposit")) {
					for (int i = 0; i < 10 && (!isOpen() || !isDepositOpen()); i++) {
						if (Players.getLocal().isMoving()) {
							i = 0;
						}
						Time.sleep(200);
					}
				}
			} else {
				final Locatable LBE = (Locatable) bankEntity;
				if (!Players.getLocal().isMoving()) {
					if (Walking.walk(LBE.getLocation())) {
						for (int i = 0; i < 50 && Players.getLocal().isMoving() && !bankEntity.isOnScreen(); i++) {
							Time.sleep(50);
						}
					}
				}
			}
		}
		return isOpen();
	}

	public static boolean close() {
		if (Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_CLOSE).interact("Close") ||
				Widgets.get(WIDGET_DEPOSIT_BOX, WIDGET_DEPOSIT_BOX_BUTTON_CLOSE).interact("Close")) {
			for (int i = 0; i < 10 && !(isDepositOpen() || isOpen()); i++) {
				Time.sleep(150);
			}
		}
		return !isOpen() && !isDepositOpen();
	}

	public static boolean withdraw(final int itemID, final int amount) {
		if (isOpen()) {
			if (amount >= -1) {
				final Item item = getItem(itemID);
				if (item == null) {
					return false;
				}

				if (!searchItem(item.getName())) {
					return !close();
				}
				final Point itemPoint = new Point(item.getWidgetChild().getRelativeX() + Random.nextInt(32, 46),
						item.getWidgetChild().getRelativeY() + Random.nextInt(96, 102));
				final int count = Inventory.getCount();
				switch (amount) {
					case -1:
						if (Mouse.click(itemPoint.x, itemPoint.y, false)) {
							Time.sleep(Random.nextInt(75, 125));
							Menu.select("Withdraw-All but one", item.getName());
						}
						break;
					case 0:
						if (Mouse.click(itemPoint.x, itemPoint.y, false)) {
							Time.sleep(Random.nextInt(75, 125));
							Menu.select("Withdraw-All", item.getName());
						}
						break;
					case 1:
						Mouse.click(itemPoint.x, itemPoint.y, true);
						break;
					default:
						if (Mouse.click(itemPoint.x, itemPoint.y, false)) {
							Time.sleep(Random.nextInt(75, 125));
							if (!Menu.contains("Withdraw-" + amount, item.getName())) {
								if (Menu.select("Withdraw-X", item.getName())) {
									Time.sleep(Random.nextInt(1000, 1300));
									Keyboard.sendText(String.valueOf(amount), true);
								}
							} else {
								Menu.select("Withdraw-" + amount, item.getName());
							}
						}
						break;
				}
				for (int i = 0; i < 150; i++) {
					final int newCount = Inventory.getCount();
					if (newCount > count || newCount == 28) {
						Time.sleep(Random.nextInt(100, 125));
						return true;
					}
					Time.sleep(20);
				}
			}
		}
		return false;
	}

	public static boolean withdraw(final String itemName, final int amount) {
		return withdraw(nameToID(itemName), amount);
	}

	public static boolean deposit(final int itemID, final int amount) {
		if (isOpen() || isDepositOpen()) {
			if (isOpen() ? Inventory.getCount() == 0 : getBoxCount() == 0) {
				return true;
			}
			if (amount >= 0) {
				final int count = isOpen() ? Inventory.getCount(true, itemID) : getBoxCount(true, itemID);
				final Item item = isOpen() ? getInventoryItem(itemID) : getBoxItem(itemID);
				if (item == null) {
					return true;
				}
				final WidgetChild itemWidget = item.getWidgetChild();
				switch (amount) {
					case 0:
						itemWidget.interact((count > 1 ? "Deposit-All" : "Deposit"), item.getName());
						break;
					case 1:
						itemWidget.interact("Deposit", item.getName());
						break;
					default:
						if (!itemWidget.interact("Deposit-" + amount, item.getName())) {
							if (itemWidget.interact("Deposit-X", item.getName())) {
								Time.sleep(Random.nextInt(1000, 1300));
								Keyboard.sendText(String.valueOf(amount), true);
							}
						}
						break;
				}
				for (int i = 0; i < 100; i++) {
					final int newCount = isOpen() ? Inventory.getCount(true, itemID) : getBoxCount(true, itemID);
					if (newCount < count || newCount == 0) {
						Time.sleep(Random.nextInt(100, 125));
						return true;
					}
					Time.sleep(Random.nextInt(10, 15));
				}
			}
		}
		return false;
	}

	public static boolean deposit(final String itemName, final int amount) {
		return deposit(nameToID(itemName), amount);
	}

	public static boolean depositAllExcept(final int... itemIDs) {
		if (isOpen() || isDepositOpen()) {
			if (isOpen() ? Inventory.getCount() == 0 : getBoxCount() == 0) {
				return true;
			}
			if (isOpen() ? getInventoryCount(false, itemIDs) == 0 : getBoxCount(false, itemIDs) == 0) {
				return depositAll();
			}
			final Item[] items = isOpen() ? Inventory.getItems() : getBoxItems();
			outer:
			for (final Item item : items) {
				if (item != null && item.getId() != -1) {
					for (final int itemID : itemIDs) {
						if (item.getId() == itemID) {
							continue outer;
						}
					}
					for (int j = 0; j < 5 && Inventory.getCount(item.getId()) != 0; j++) {
						if (deposit(item.getId(), 0)) {
							Time.sleep(Random.nextInt(75, 125));
						}
					}
				}
			}
			return isOpen() ? getInventoryCount(true) - getInventoryCount(true, itemIDs) == 0
					: getBoxCount(true) - getBoxCount(true, itemIDs) == 0;
		}
		return false;
	}

	public static int getBoxCount(final boolean includeStacks, final int... itemIDs) {
		int count = 0;
		if (Widgets.get(WIDGET_DEPOSIT_BOX, WIDGET_DEPOSIT_BOX_INVENTORY).validate()) {
			for (final Item item : getBoxItems(itemIDs)) {
				if (item != null) {
					count += includeStacks ? item.getStackSize() : 1;
				}
			}
		}
		return count;
	}

	public static int getBoxCount(final int... itemIDs) {
		return getBoxCount(false, itemIDs);
	}

	public static int getBoxCount(final boolean includeStacks) {
		return getBoxCountExcept(includeStacks, -1);
	}

	public static int getBoxCount() {
		return getBoxCountExcept(false, -1);
	}

	public static int getBoxCountExcept(final boolean includeStacks, final int... itemIDs) {
		int count = 0;
		outer:
		for (final Item item : getBoxItems()) {
			if (item != null) {
				for (final int itemID : itemIDs) {
					if (item.getId() == itemID) {
						continue outer;
					}
				}
				count += includeStacks ? item.getStackSize() : 1;
			}
		}
		return count;
	}

	public static int getBoxCountExcept(final int... itemIDs) {
		return getBoxCountExcept(false, itemIDs);
	}

	public static int getCount(final boolean includeStacks, final int... itemIDs) {
		int count = 0;
		for (final Item item : getItems(itemIDs)) {
			if (item != null) {
				count += includeStacks ? item.getStackSize() : 1;
			}
		}
		return count;
	}

	public static int getCount(final int... itemIDs) {
		return getCount(false, itemIDs);
	}

	public static int getCount(final boolean includeStacks) {
		return getCountExcept(includeStacks, -1);
	}

	public static int getCount() {
		return getCountExcept(false, -1);
	}

	public static int getCountExcept(final boolean includeStacks, final int... itemIDs) {
		int count = 0;
		outer:
		for (final Item item : getItems()) {
			if (item != null) {
				for (int itemID : itemIDs) {
					if (item.getId() == itemID) {
						continue outer;
					}
				}
				count += includeStacks ? item.getStackSize() : 1;
			}
		}
		return count;
	}

	public static int getCountExcept(final int... itemIDs) {
		return getCountExcept(false, itemIDs);
	}

	public static Item[] getItems() {
		final List<Item> items = new LinkedList<Item>();
		if (Widgets.get(WIDGET_BANK, WIDGET_BANK_INVENTORY).validate()) {
			for (final WidgetChild item : Widgets.get(WIDGET_BANK, WIDGET_BANK_INVENTORY).getChildren()) {
				if (item != null && item.getChildId() != -1) {
					items.add(new Item(item));
				}
			}
		}
		return items.toArray(new Item[items.size()]);
	}

	public static Item[] getItems(final int... itemIDs) {
		final List<Item> items = new LinkedList<Item>();
		for (final Item item : getItems()) {
			for (final int itemID : itemIDs) {
				if (item.getId() == itemID) {
					items.add(item);
				}
			}
		}
		return items.toArray(new Item[items.size()]);
	}

	public static Item getItem(final int itemID) {
		for (final Item item : getItems()) {
			if (item.getId() == itemID) {
				return item;
			}
		}
		return null;
	}

	public static Item getItem(final String itemName) {
		return getItem(nameToID(itemName));
	}

	public static Item[] getBoxItems() {
		final List<Item> items = new LinkedList<Item>();
		if (Widgets.get(WIDGET_DEPOSIT_BOX, WIDGET_DEPOSIT_BOX_INVENTORY).validate()) {
			for (final WidgetChild item : Widgets.get(WIDGET_DEPOSIT_BOX, WIDGET_DEPOSIT_BOX_INVENTORY).getChildren()) {
				if (item != null && item.getChildId() != -1) {
					items.add(new Item(item));
				}
			}
		}
		return items.toArray(new Item[items.size()]);
	}

	public static Item[] getBoxItems(final int... itemIDs) {
		final List<Item> items = new LinkedList<Item>();
		for (final Item item : getBoxItems()) {
			for (final int itemID : itemIDs) {
				if (item.getId() == itemID) {
					items.add(item);
				}
			}
		}
		return items.toArray(new Item[items.size()]);
	}


	public static Item getBoxItem(final int itemID) {
		for (final Item item : getBoxItems()) {
			if (item.getId() == itemID) {
				return item;
			}
		}
		return null;
	}

	public static Item getBoxItem(final String itemName) {
		return getBoxItem(nameToID(itemName));
	}

	public static boolean dbDepositAll() {
		return Widgets.get(WIDGET_DEPOSIT_BOX,
				WIDGET_DEPOSIT_BUTTON_DEPOSIT_INVENTORY_ITEMS).interact("Deposit carried items");
	}

	public static boolean dbDepositEquipped() {
		return Widgets.get(WIDGET_DEPOSIT_BOX,
				WIDGET_DEPOSIT_BUTTON_DEPOSIT_EQUIPPED_ITEMS).interact("Deposit worn items");
	}


	public static boolean dbDepositFamiliar() {
		return Widgets.get(WIDGET_DEPOSIT_BOX,
				WIDGET_DEPOSIT_BUTTON_DEPOSIT_FAMILIAR_INVENTORY).interact("Deposit beast of burden inventory");
	}

	public static boolean dbDepositMoneyPouch() {
		return Widgets.get(WIDGET_DEPOSIT_BOX,
				WIDGET_DEPOSIT_BUTTON_DEPOSIT_MONEY_POUCH).interact("Deposit money pouch contents");
	}
	
	public static boolean depositAll() {
		return Widgets.get(WIDGET_BANK,
				WIDGET_BANK_BUTTON_DEPOSIT_INVENTORY_ITEMS).interact("Deposit carried items");
	}
	
	public static boolean depositEquipped() {
		return Widgets.get(WIDGET_BANK,
				WIDGET_BANK_BUTTON_DEPOSIT_EQUIPPED_ITEMS).interact("Deposit worn items");
	}
	
	public static boolean depositFamiliar() {
		return Widgets.get(WIDGET_BANK,
				WIDGET_BANK_BUTTON_DEPOSIT_FAMILIAR_INVENTORY).interact("Deposit money pouch contents");
	}
	
	public static boolean depositMoneyPouch() {
		return Widgets.get(WIDGET_BANK,
				WIDGET_BANK_BUTTON_DEPOSIT_MONEY_POUCH).interact("Deposit carried items");
	}
	
	public static boolean setWithdrawModeToItem() {
		return (Settings.get(BANK_TOGGLE_WITHDRAW_MODE_SETTING) == 0
				|| Settings.get(BANK_TOGGLE_WITHDRAW_MODE_SETTING) == 1
				&& Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_WITHDRAW_TOGGLE).interact("Switch to item"));
	}

	public static boolean setWithdrawModeToNote() {
		return (Settings.get(BANK_TOGGLE_WITHDRAW_MODE_SETTING) == 1
				|| Settings.get(BANK_TOGGLE_WITHDRAW_MODE_SETTING) == 0 &&
				Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_WITHDRAW_TOGGLE).interact("Switch to note"));
	}

	public static boolean setRearrangeModeToInsert() {
		return (Settings.get(BANK_TOGGLE_REARRANGE_MODE_SETTING) == 1 ||
				Settings.get(BANK_TOGGLE_REARRANGE_MODE_SETTING) != 1
						&& Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_SWAP).interact("Switch to insert"));
	}

	public static boolean setRearrangeModeToSwap() {
		return (Settings.get(BANK_TOGGLE_REARRANGE_MODE_SETTING) == 0 ||
				Settings.get(BANK_TOGGLE_REARRANGE_MODE_SETTING) == 1
						&& Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_SWAP).interact("Switch to swap"));
	}

	public static boolean openEquipment() {
		return Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_OPEN_EQUIP).interact("Show Equipment Stats");
	}

	public static int getCurrentTab() {
		return ((Settings.get(BANK_SEARCH_SETTING) >>> 24) - 136) / 8;
	}

	public static boolean openTab(final int tab) {
		try {
			return Widgets.get(WIDGET_BANK, WIDGET_BANK_TAB[tab - 1]).interact("View " + (tab == 1 ? "all" : "tab " + tab));
		} catch (ArrayIndexOutOfBoundsException ignored) {
		}
		return false;
	}

	public static boolean isSearchOpen() {
		return Settings.get(BANK_SEARCH_SETTING) == Integer.MIN_VALUE;
	}

	public static boolean searchItem(final String itemName) {
		if (isSearchOpen()) {
			if (!Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_SEARCH).interact("Search")) {
				return false;
			}
			for (int i = 0; i < 30 && isSearchOpen(); i++) {
				Time.sleep(50);
			}
			Time.sleep(Random.nextInt(130, 285));
		}
		if (Widgets.get(WIDGET_BANK, WIDGET_BANK_BUTTON_SEARCH).interact("Search")) {
			for (int i = 0; i < 30 && !isSearchOpen(); i++) {
				Time.sleep(50);
			}
			if (isSearchOpen()) {
				Time.sleep(Random.nextInt(130, 285));
				Keyboard.sendText(itemName, true);
				Time.sleep(Random.nextInt(500, 800));
				return true;
			}
		}
		return false;
	}

	private static Entity findBankEntity() {
		final SceneObject bankBooth = SceneEntities.getNearest(BANK_BOOTHS);
		final SceneObject bankChest = SceneEntities.getNearest(BANK_CHESTS);
		final SceneObject depositBox = SceneEntities.getNearest(DEPOSIT_BOXES);
		final NPC banker = NPCs.getNearest(BANKERS);
		Entity bankEntity = null;
		if (bankBooth != null) {
			if (banker != null) {
				if (Random.nextInt(1, 11) < 7) {
					bankEntity = bankBooth;
				} else {
					bankEntity = banker;
				}
			} else {
				bankEntity = bankBooth;
			}
		} else if (banker != null) {
			bankEntity = banker;
		} else if (bankChest != null) {
			bankEntity = bankChest;
		} else if (depositBox != null) {
			bankEntity = depositBox;
		}
		return bankEntity;
	}

	private static int nameToID(final String itemName) {
		final Item[] items = isOpen() ? getItems() : getBoxItems();
		for (final Item item : items) {
			if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
				return item.getId();
			}
			if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
				return item.getId();
			}
		}
		return -1;
	}

	private static Item getInventoryItem(final int itemID) {
		for (final Item item : Inventory.getItems()) {
			if (item.getId() == itemID) {
				return new Item(item.getWidgetChild());
			}
		}
		return null;
	}

	private static int getInventoryCount(final boolean includeStacks) {
		int count = 0;
		for (final Item item : Inventory.getItems()) {
			count += includeStacks ? item.getStackSize() : 1;
		}
		return count;
	}

	private static int getInventoryCount(final boolean includeStacks, final int... itemIDs) {
		int count = 0;
		for (final int itemID : itemIDs) {
			count += Inventory.getCount(includeStacks, itemID);
		}
		return count;
	}
}
