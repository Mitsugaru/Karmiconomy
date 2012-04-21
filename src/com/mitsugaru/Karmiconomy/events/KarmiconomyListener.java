package com.mitsugaru.Karmiconomy.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import com.mitsugaru.Karmiconomy.Config;
import com.mitsugaru.Karmiconomy.DatabaseHandler;
import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.KarmicEcon;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;
import com.mitsugaru.Karmiconomy.events.KconEventLogic.DenyType;

public class KarmiconomyListener implements Listener
{
	private Karmiconomy plugin;
	private DatabaseHandler db;
	private Config config;
	// TODO create thread that resets this every so often? May not be necessary
	// Maybe every 2 minutes?

	public KarmiconomyListener(Karmiconomy plugin)
	{
		this.plugin = plugin;
		this.db = plugin.getDatabaseHandler();
		this.config = plugin.getPluginConfig();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void chatValid(final PlayerChatEvent event)
	{
		if (!event.isCancelled() && config.chat && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic.deny(Field.CHAT, player, config.chatDenyPay,
					config.chatDenyLimit, null, null))
			{
				// Cancel event
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Message", event.getMessage());
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void chat(final PlayerChatEvent event)
	{
		if (!event.isCancelled() && config.chat && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Check if hit limit
			if (!KconEventLogic.hitLimit(Field.CHAT, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.CHAT, player, null, null))
				{
					// Increment
					db.incrementData(Field.CHAT, player.getName(), null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				details.put("Message", event.getMessage());
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void commandValid(final PlayerCommandPreprocessEvent event)
	{
		if (!event.isCancelled() && config.command && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic.deny(Field.COMMAND, player, config.commandDenyPay,
					config.commandDenyLimit, null, event.getMessage()))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Message", event.getMessage());
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void command(final PlayerCommandPreprocessEvent event)
	{
		if (!event.isCancelled() && config.command && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on command
			// Check if hit limit
			if (!KconEventLogic.hitLimit(Field.COMMAND, player, null, event.getMessage()))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.COMMAND, player, null, event.getMessage()))
				{
					// Increment
					db.incrementData(Field.COMMAND, player.getName(), null,
							event.getMessage());
				}
			}
			// TODO pay based on command match
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				details.put("Message", event.getMessage());
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void blockPlaceValid(final BlockPlaceEvent event)
	{
		if (!event.isCancelled() && config.blockPlace)
		{
			final Item placed = new Item(event.getBlockPlaced().getTypeId(),
					event.getBlockPlaced().getData(), (short) 0);
			final boolean denyPay = config.getItemDenyPay(Field.BLOCK_PLACE,
					placed);
			final boolean denyLimit = config.getItemDenyLimit(
					Field.BLOCK_PLACE, placed);
			if ((denyPay || denyLimit) && event.getPlayer() != null)
			{
				final Player player = event.getPlayer();

				if (KconEventLogic.deny(Field.BLOCK_PLACE, player, denyPay, denyLimit, placed,
						null))
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getBlock() != null)
						{
							details.put("Block", event.getBlock().toString());
						}
						if (event.getBlockAgainst() != null)
						{
							details.put("Block against", event
									.getBlockAgainst().toString());
						}
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockPlace(final BlockPlaceEvent event)
	{
		if (!event.isCancelled() && config.blockPlace
				&& event.getPlayer() != null)
		{
			// Pay on block place

			final Player player = event.getPlayer();
			final Item placed = new Item(event.getBlockPlaced().getTypeId(),
					event.getBlockPlaced().getData(), (short) 0);
			if (!KconEventLogic.hitLimit(Field.BLOCK_PLACE, player, placed, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.BLOCK_PLACE, player, placed, null))
				{
					// Increment
					db.incrementData(Field.BLOCK_PLACE, player.getName(),
							placed, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBlock() != null)
				{
					details.put("Block", event.getBlock().toString());
				}
				if (event.getBlockAgainst() != null)
				{
					details.put("Block against", event.getBlockAgainst()
							.toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void blockDestroyValid(final BlockBreakEvent event)
	{
		if (!event.isCancelled() && config.blockDestroy)
		{
			final Item destroyed = new Item(event.getBlock().getTypeId(), event
					.getBlock().getData(), (short) 0);
			final boolean denyPay = config.getItemDenyPay(Field.BLOCK_DESTROY,
					destroyed);
			final boolean denyLimit = config.getItemDenyLimit(
					Field.BLOCK_DESTROY, destroyed);
			if ((denyPay || denyLimit) && event.getPlayer() != null)
			{
				final Player player = event.getPlayer();

				if (KconEventLogic.deny(Field.BLOCK_DESTROY, player, denyPay, denyLimit,
						destroyed, null))
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getBlock() != null)
						{
							details.put("Block", event.getBlock().toString());
						}
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockDestroy(final BlockBreakEvent event)
	{
		if (!event.isCancelled() && config.blockDestroy
				&& event.getPlayer() != null)
		{
			// Pay on block break
			final Player player = event.getPlayer();
			final Item destroyed = new Item(event.getBlock().getTypeId(), event
					.getBlock().getData(), (short) 0);
			if (!KconEventLogic.hitLimit(Field.BLOCK_DESTROY, player, destroyed, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.BLOCK_DESTROY, player, destroyed, null))
				{
					// Increment
					db.incrementData(Field.BLOCK_DESTROY, player.getName(),
							destroyed, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBlock() != null)
				{
					details.put("Block", event.getBlock().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	/*
	 * @EventHandler(priority = EventPriority.MONITOR) public void
	 * blockIgniteValid(final BlockIgniteEvent event) { // Grab player if there
	 * is one if (!event.isCancelled() && (config.blockIgniteDenyPay ||
	 * config.blockIgniteDenyLimit) && event.getPlayer() != null) { final Player
	 * player = event.getPlayer(); // TODO THIS IS NOT FOR PLAYERS? //may just
	 * get rid of this event // pay by type if(config.debugEvents) { final
	 * Map<String, String> details = new HashMap<String, String>();
	 * details.put("Player", player.getName()); if(event.getBlock() != null) {
	 * details.put("Block", event.getBlock().toString()); }
	 * details.put("Cancelled", "true"); KconEventLogic.debugEvent(event, details); } } }
	 * @EventHandler(priority = EventPriority.MONITOR) public void
	 * blockIgnite(final BlockIgniteEvent event) { // Grab player if there is
	 * one if (!event.isCancelled() && config.blockIgnite && event.getPlayer()
	 * != null) { final Player player = event.getPlayer(); // TODO THIS IS NOT
	 * FOR PLAYERS? //may just get rid of this event // pay by type
	 * if(config.debugEvents) { final Map<String, String> details = new
	 * HashMap<String, String>(); details.put("Player", player.getName());
	 * if(event.getBlock() != null) { details.put("Block",
	 * event.getBlock().toString()); } KconEventLogic.debugEvent(event, details); } } }
	 */

	@EventHandler(priority = EventPriority.LOWEST)
	public void craftItemValid(final CraftItemEvent event)
	{
		if (!event.isCancelled() && config.craftItem)
		{
			final Item craft = new Item(event.getRecipe().getResult());
			final boolean denyPay = config.getItemDenyPay(Field.ITEM_CRAFT,
					craft);
			final boolean denyLimit = config.getItemDenyLimit(Field.ITEM_CRAFT,
					craft);
			if ((denyPay || denyLimit) && event.getWhoClicked() != null)
			{
				if (event.getWhoClicked() instanceof Player)
				{
					final Player player = (Player) event.getWhoClicked();

					if (KconEventLogic.deny(Field.ITEM_CRAFT, player, denyPay, denyLimit,
							craft, null))
					{
						// Deny
						event.setCancelled(true);
						if (config.debugEvents)
						{
							final Map<String, String> details = new HashMap<String, String>();
							details.put("Player", player.getName());
							if (event.getRecipe().getResult() != null)
							{
								details.put("Result", event.getRecipe()
										.getResult().toString());
							}
							details.put("Cancelled", "true");
							KconEventLogic.debugEvent(event, details);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void craftItem(final CraftItemEvent event)
	{
		if (!event.isCancelled() && config.craftItem
				&& event.getWhoClicked() != null)
		{
			if (event.getWhoClicked() instanceof Player)
			{
				final Player player = (Player) event.getWhoClicked();
				final Item craft = new Item(event.getRecipe().getResult());
				// Pay on craft
				if (!KconEventLogic.hitLimit(Field.ITEM_CRAFT, player, craft, null))
				{
					// Try to pay
					if (KarmicEcon.pay(Field.ITEM_CRAFT, player, craft, null))
					{
						// Increment
						db.incrementData(Field.ITEM_CRAFT, player.getName(),
								craft, null);
					}
				}
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getRecipe().getResult() != null)
					{
						details.put("Result", event.getRecipe().getResult()
								.toString());
					}
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void enchantItemValid(final EnchantItemEvent event)
	{
		if (!event.isCancelled() && config.enchantItem)
		{
			final Item enchant = new Item(event.getItem());
			final boolean denyPay = config.getItemDenyPay(Field.ITEM_ENCHANT,
					enchant);
			final boolean denyLimit = config.getItemDenyLimit(
					Field.ITEM_ENCHANT, enchant);
			if ((denyPay || denyLimit) && event.getEnchanter() != null)
			{
				final Player player = (Player) event.getEnchanter();

				if (KconEventLogic.deny(Field.ITEM_ENCHANT, player, denyPay, denyLimit,
						enchant, null))
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getEnchantsToAdd() != null)
						{
							for (Map.Entry<Enchantment, Integer> entry : event
									.getEnchantsToAdd().entrySet())
							{
								details.put("Enchantment/Level", entry.getKey()
										.getName()
										+ "/"
										+ entry.getValue().toString());
							}
						}
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void enchantItem(final EnchantItemEvent event)
	{
		if (!event.isCancelled() && config.enchantItem
				&& event.getEnchanter() != null)
		{
			final Player player = event.getEnchanter();
			// Pay on enchant
			final Item enchant = new Item(event.getItem());
			if (!KconEventLogic.hitLimit(Field.ITEM_ENCHANT, player, enchant, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.ITEM_ENCHANT, player, enchant, null))
				{
					// Increment
					db.incrementData(Field.ITEM_ENCHANT, player.getName(),
							enchant, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getEnchantsToAdd() != null)
				{
					for (Map.Entry<Enchantment, Integer> entry : event
							.getEnchantsToAdd().entrySet())
					{
						details.put("Enchantment/Level", entry.getKey()
								.getName() + "/" + entry.getValue().toString());
					}
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void createPortalValid(final EntityCreatePortalEvent event)
	{
		if (!event.isCancelled()
				&& (config.portalCreateNether || config.portalCreateEnd || config.portalCreateCustom)
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				// Deny based on portal type
				boolean cancel = false;
				switch (event.getPortalType())
				{
					case NETHER:
					{
						if (KconEventLogic.deny(Field.PORTAL_CREATE_NETHER, player,
								config.portalCreateNetherDenyPay,
								config.portalCreateNetherDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					case ENDER:
					{
						if (KconEventLogic.deny(Field.PORTAL_CREATE_END, player,
								config.portalCreateEndDenyPay,
								config.portalCreateEndDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					case CUSTOM:
					{
						if (KconEventLogic.deny(Field.PORTAL_CREATE_CUSTOM, player,
								config.portalCreateCustomDenyPay,
								config.portalCreateCustomDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					default:
					{
						// Unknown
						if (config.debugUnhandled)
						{
							plugin.getLogger().warning(
									"Unhandled '" + event.getEventName()
											+ "' for portal: "
											+ event.getPortalType());
						}
						break;
					}
				}
				if (cancel)
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getPortalType() != null)
						{
							details.put("Portal", event.getPortalType()
									.toString());
						}
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void createPortal(final EntityCreatePortalEvent event)
	{
		if (!event.isCancelled()
				&& (config.portalCreateNether || config.portalCreateEnd || config.portalCreateCustom)
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				// Pay on portal create, based on portal type
				switch (event.getPortalType())
				{
					case NETHER:
					{
						if (!KconEventLogic.hitLimit(Field.PORTAL_CREATE_NETHER, player, null,
								null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.PORTAL_CREATE_NETHER, player, null,
									null))
							{
								// Increment
								db.incrementData(Field.PORTAL_CREATE_NETHER,
										player.getName(), null, null);
							}
						}
						break;
					}
					case ENDER:
					{
						if (!KconEventLogic.hitLimit(Field.PORTAL_CREATE_END, player, null,
								null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.PORTAL_CREATE_END, player, null, null))
							{
								// Increment
								db.incrementData(Field.PORTAL_CREATE_END,
										player.getName(), null, null);
							}
						}
						break;
					}
					case CUSTOM:
					{
						if (!KconEventLogic.hitLimit(Field.PORTAL_CREATE_CUSTOM, player, null,
								null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.PORTAL_CREATE_CUSTOM, player, null,
									null))
							{
								// Increment
								db.incrementData(Field.PORTAL_CREATE_CUSTOM,
										player.getName(), null, null);
							}
						}
						break;
					}
					default:
					{
						// Unknown
						if (config.debugUnhandled)
						{
							plugin.getLogger().warning(
									"Unhandled '" + event.getEventName()
											+ "' for portal: "
											+ event.getPortalType());
						}
						break;
					}
				}
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getPortalType() != null)
					{
						details.put("Portal", event.getPortalType().toString());
					}
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void enterPortal(final EntityPortalEnterEvent event)
	{
		if (config.portalEnter && event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				// Pay on portal enter
				if (!KconEventLogic.hitLimit(Field.PORTAL_ENTER, player, null, null))
				{
					// Try to pay
					if (KarmicEcon.pay(Field.PORTAL_ENTER, player, null, null))
					{
						// Increment
						db.incrementData(Field.PORTAL_ENTER, player.getName(),
								null, null);
					}
				}
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void shootBowValid(final EntityShootBowEvent event)
	{
		if (!event.isCancelled() && config.shootBow
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				boolean cancel = false;
				if (config.shootBowDenyForce
						&& (event.getForce() < config.shootBowForce))
				{
					cancel = true;
					KconEventLogic.sendLackMessage(player, DenyType.FORCE,
							Field.BOW_SHOOT.name(), "" + event.getForce());
				}

				else if (KconEventLogic.deny(Field.BOW_SHOOT, player, config.shootBowDenyPay,
						config.shootBowDenyLimit, null, null))
				{
					cancel = true;
				}
				if (cancel)
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						details.put("Force", "" + event.getForce());
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void shootBow(final EntityShootBowEvent event)
	{
		if (!event.isCancelled() && config.shootBow
				&& event.getEntity() != null
				&& (event.getForce() >= config.shootBowForce))
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				if (!KconEventLogic.hitLimit(Field.BOW_SHOOT, player, null, null))
				{
					// Try to pay
					if (KarmicEcon.pay(Field.BOW_SHOOT, player, null, null))
					{
						// Increment
						db.incrementData(Field.BOW_SHOOT, player.getName(),
								null, null);
					}
				}
				// TODO can pay based on force
				// TODO can pay based on entity?
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Force", "" + event.getForce());
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void tameValid(final EntityTameEvent event)
	{
		// Move config check inside if
		if (!event.isCancelled() && (config.tameOcelot || config.tameWolf)
				&& event.getOwner() != null)
		{
			if (event.getOwner() instanceof Player)
			{
				boolean cancel = false;
				final Player player = (Player) event.getEntity();
				// Check entity tamed
				switch (event.getEntityType())
				{
					case OCELOT:
					{
						// Ocelot
						if (KconEventLogic.deny(Field.TAME_OCELOT, player,
								config.tameOcelotDenyPay,
								config.tameOcelotDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					case WOLF:
					{
						// Wolf
						if (KconEventLogic.deny(Field.TAME_WOLF, player,
								config.tameWolfDenyPay,
								config.tameWolfDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					default:
					{
						if (config.debugUnhandled)
						{
							plugin.getLogger().warning(
									"Unhandled " + event.getEventName()
											+ " for entity '"
											+ event.getEntityType() + "'");
						}
						break;
					}
				}
				// Cancel
				if (cancel)
				{
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getEntityType() != null)
						{
							details.put("EntityType", event.getEntityType()
									.toString());
						}
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void tame(final EntityTameEvent event)
	{
		// Move config check inside if
		if (!event.isCancelled() && (config.tameOcelot || config.tameWolf)
				&& event.getOwner() != null)
		{
			if (event.getOwner() instanceof Player)
			{
				// Pay on tame
				final Player player = (Player) event.getEntity();
				// Check entity tamed
				switch (event.getEntityType())
				{
					case OCELOT:
					{
						// Ocelot
						if (!KconEventLogic.hitLimit(Field.TAME_OCELOT, player, null, null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.TAME_OCELOT, player, null, null))
							{
								// Increment
								db.incrementData(Field.TAME_OCELOT,
										player.getName(), null, null);
							}
						}
						break;
					}
					case WOLF:
					{
						// Wolf
						if (!KconEventLogic.hitLimit(Field.TAME_WOLF, player, null, null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.TAME_WOLF, player, null, null))
							{
								// Increment
								db.incrementData(Field.TAME_WOLF,
										player.getName(), null, null);
							}
						}
						break;
					}
					default:
					{
						if (config.debugUnhandled)
						{
							plugin.getLogger().warning(
									"Unhandled " + event.getEventName()
											+ " for entity '"
											+ event.getEntityType() + "'");
						}
						break;
					}
				}
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getEntityType() != null)
					{
						details.put("EntityType", event.getEntityType()
								.toString());
					}
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void paintingPlaceValid(final PaintingPlaceEvent event)
	{
		if (!event.isCancelled() && config.paintingPlace
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic.deny(Field.PAINTING_PLACE, player, config.paintingPlaceDenyPay,
					config.paintingPlaceDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				// TODO also need to get painting break
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getPainting() != null)
					{
						details.put("Painting", event.getPainting().toString());
					}
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void paintingPlace(final PaintingPlaceEvent event)
	{
		if (!event.isCancelled() && config.paintingPlace
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on place
			if (!KconEventLogic.hitLimit(Field.PAINTING_PLACE, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.PAINTING_PLACE, player, null, null))
				{
					// Increment
					db.incrementData(Field.PAINTING_PLACE, player.getName(),
							null, null);
				}
			}
			// TODO also need to get painting break
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getPainting() != null)
				{
					details.put("Painting", event.getPainting().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bedEnterValid(final PlayerBedEnterEvent event)
	{
		if (!event.isCancelled() && config.bedEnter
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic.deny(Field.BED_ENTER, player, config.bedEnterDenyPay,
					config.bedEnterDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getBed() != null)
					{
						details.put("Bed", event.getBed().toString());
					}
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void bedEnter(final PlayerBedEnterEvent event)
	{
		if (!event.isCancelled() && config.bedEnter
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on enter
			if (!KconEventLogic.hitLimit(Field.BED_ENTER, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.BED_ENTER, player, null, null))
				{
					// Increment
					db.incrementData(Field.BED_ENTER, player.getName(), null,
							null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBed() != null)
				{
					details.put("Bed", event.getBed().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void bedLeave(final PlayerBedLeaveEvent event)
	{
		if (config.bedLeave && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on leave
			if (!KconEventLogic.hitLimit(Field.BED_LEAVE, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.BED_LEAVE, player, null, null))
				{
					// Increment
					db.incrementData(Field.BED_LEAVE, player.getName(), null,
							null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBed() != null)
				{
					details.put("Bed", event.getBed().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bucketEmptyValid(final PlayerBucketEmptyEvent event)
	{
		if (!event.isCancelled()
				&& (config.bucketEmptyWater || config.bucketEmptyLava)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			boolean cancel = false;
			// switch for type of bucket
			switch (event.getBucket())
			{
				case LAVA_BUCKET:
				{
					if (KconEventLogic.deny(Field.BUCKET_EMPTY_LAVA, player,
							config.bucketEmptyLavaDenyPay,
							config.bucketEmptyLavaDenyLimit, null, null))
					{
						// deny
						cancel = true;
					}
					break;
				}
				case WATER_BUCKET:
				{
					if (KconEventLogic.deny(Field.BUCKET_EMPTY_WATER, player,
							config.bucketEmptyWaterDenyPay,
							config.bucketEmptyWaterDenyLimit, null, null))
					{
						// Deny
						cancel = true;
					}
					break;
				}
				default:
				{
					if (config.debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled " + event.getEventName()
										+ " for type '"
										+ event.getBucket().name() + "'");
					}
					break;
				}
			}
			if (cancel)
			{
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getBucket() != null)
					{
						details.put("Bucket", event.getBucket().toString());
					}
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void bucketEmpty(final PlayerBucketEmptyEvent event)
	{
		if (!event.isCancelled()
				&& (config.bucketEmptyLava || config.bucketEmptyWater)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Switch for type of bucket
			switch (event.getBucket())
			{
				case LAVA_BUCKET:
				{
					// Pay on empty
					if (!KconEventLogic.hitLimit(Field.BUCKET_EMPTY_LAVA, player, null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_EMPTY_LAVA, player, null, null))
						{
							// Increment
							db.incrementData(Field.BUCKET_EMPTY_LAVA,
									player.getName(), null, null);
						}
					}
					break;
				}
				case WATER_BUCKET:
				{
					// Pay on empty
					if (!KconEventLogic.hitLimit(Field.BUCKET_EMPTY_WATER, player, null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_EMPTY_WATER, player, null, null))
						{
							// Increment
							db.incrementData(Field.BUCKET_EMPTY_WATER,
									player.getName(), null, null);
						}
					}
					break;
				}
				default:
				{
					if (config.debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled " + event.getEventName()
										+ " for type '"
										+ event.getBucket().name() + "'");
					}
					break;
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBucket() != null)
				{
					details.put("Bucket", event.getBucket().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bucketFillValid(final PlayerBucketFillEvent event)
	{
		if (!event.isCancelled()
				&& (config.bucketFillWater || config.bucketFillLava)
				&& event.getPlayer() != null && event.getBlockClicked() != null)
		{
			final Player player = event.getPlayer();
			boolean cancel = false;
			// Switch for type of bucket
			switch (event.getBlockClicked().getType())
			{
				case STATIONARY_LAVA:
				{
					if (KconEventLogic.deny(Field.BUCKET_FILL_LAVA, player,
							config.bucketFillLavaDenyPay,
							config.bucketFillLavaDenyLimit, null, null))
					{
						// Deny
						cancel = true;
					}
					break;
				}
				case STATIONARY_WATER:
				{
					if (KconEventLogic.deny(Field.BUCKET_FILL_WATER, player,
							config.bucketFillWaterDenyPay,
							config.bucketFillWaterDenyLimit, null, null))
					{
						// Deny
						cancel = true;
					}
					break;
				}
				default:
				{
					if (config.debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled " + event.getEventName()
										+ " for type '"
										+ event.getBucket().name() + "'");
					}
					break;
				}
			}
			if (cancel)
			{
				// deny
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getBucket() != null)
					{
						details.put("Bucket", event.getBucket().toString());
					}
					if (event.getBlockClicked() != null)
					{
						details.put("Block", event.getBlockClicked().getType()
								.name());
					}
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void bucketFill(final PlayerBucketFillEvent event)
	{
		if (!event.isCancelled()
				&& (config.bucketFillWater || config.bucketFillLava)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Switch for type of bucket
			switch (event.getBlockClicked().getType())
			{
				case STATIONARY_LAVA:
				{
					// Pay on fill
					if (!KconEventLogic.hitLimit(Field.BUCKET_FILL_LAVA, player, null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_FILL_LAVA, player, null, null))
						{
							// Increment
							db.incrementData(Field.BUCKET_FILL_LAVA,
									player.getName(), null, null);
						}
					}
					break;
				}
				case STATIONARY_WATER:
				{
					// Pay on fill
					if (!KconEventLogic.hitLimit(Field.BUCKET_FILL_WATER, player, null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_FILL_WATER, player, null, null))
						{
							// Increment
							db.incrementData(Field.BUCKET_FILL_WATER,
									player.getName(), null, null);
						}
					}
					break;
				}
				default:
				{
					if (config.debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled " + event.getEventName()
										+ " for type '"
										+ event.getBucket().name() + "'");
					}
					break;
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBucket() != null)
				{
					// TODO change this the specific type
					details.put("Bucket", event.getBucket().toString());
				}
				if (event.getBlockClicked() != null)
				{
					details.put("Block", event.getBlockClicked().getType()
							.name());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void worldChange(final PlayerChangedWorldEvent event)
	{
		if (config.worldChange && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on change
			if (!KconEventLogic.hitLimit(Field.WORLD_CHANGE, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.WORLD_CHANGE, player, null, null))
				{
					// Increment
					db.incrementData(Field.WORLD_CHANGE, player.getName(),
							null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getFrom() != null)
				{
					details.put("Past World", event.getFrom().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerDeath(final PlayerDeathEvent event)
	{
		if (config.death && event.getEntity() != null)
		{
			final Player player = event.getEntity();
			// Pay on death
			if (!KconEventLogic.hitLimit(Field.DEATH, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.DEATH, player, null, null))
				{
					// Increment
					db.incrementData(Field.DEATH, player.getName(), null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getDeathMessage() != null)
				{
					details.put("Message", event.getDeathMessage());

				}
				if (player.getLocation() != null)
				{
					details.put("Location", player.getLocation().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerRespawn(final PlayerRespawnEvent event)
	{
		if (config.respawn && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on respawn
			if (!KconEventLogic.hitLimit(Field.RESPAWN, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.RESPAWN, player, null, null))
				{
					// Increment
					db.incrementData(Field.RESPAWN, player.getName(), null,
							null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getRespawnLocation() != null)
				{
					details.put("Location", event.getRespawnLocation()
							.toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void itemPickupValid(final PlayerPickupItemEvent event)
	{
		if (!event.isCancelled() && config.pickup)
		{
			final Item pickup = new Item(event.getItem().getItemStack());
			final boolean denyPay = config.getItemDenyPay(Field.ITEM_PICKUP,
					pickup);
			final boolean denyLimit = config.getItemDenyLimit(
					Field.ITEM_PICKUP, pickup);
			if ((denyPay || denyLimit) && event.getPlayer() != null)
			{
				final Player player = event.getPlayer();

				if (KconEventLogic.deny(Field.ITEM_PICKUP, player, denyPay, denyLimit, pickup,
						null))
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getItem() != null)
						{
							details.put("Item", event.getItem().getItemStack()
									.toString());
						}
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void itemPickup(final PlayerPickupItemEvent event)
	{
		if (!event.isCancelled() && config.itemDrop
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on drop
			final Item pickup = new Item(event.getItem().getItemStack());
			if (!KconEventLogic.hitLimit(Field.ITEM_PICKUP, player, pickup, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.ITEM_PICKUP, player, pickup, null))
				{
					// Increment
					db.incrementData(Field.ITEM_PICKUP, player.getName(),
							pickup, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getItem() != null)
				{
					details.put("Item", event.getItem().getItemStack()
							.toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void itemDropValid(final PlayerDropItemEvent event)
	{
		if (!event.isCancelled() && config.itemDrop)
		{
			final Item dropped = new Item(event.getItemDrop().getItemStack());
			final boolean denyPay = config.getItemDenyPay(Field.ITEM_DROP,
					dropped);
			final boolean denyLimit = config.getItemDenyLimit(Field.ITEM_DROP,
					dropped);
			if ((denyPay || denyLimit) && event.getPlayer() != null)
			{
				final Player player = event.getPlayer();

				if (KconEventLogic.deny(Field.ITEM_DROP, player, denyPay, denyLimit, dropped,
						null))
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getItemDrop() != null)
						{
							details.put("Item", event.getItemDrop()
									.getItemStack().toString());
						}
						details.put("Cancelled", "true");
						KconEventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void itemDrop(final PlayerDropItemEvent event)
	{
		if (!event.isCancelled() && config.itemDrop
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on drop
			final Item dropped = new Item(event.getItemDrop().getItemStack());
			if (!KconEventLogic.hitLimit(Field.ITEM_DROP, player, dropped, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.ITEM_DROP, player, dropped, null))
				{
					// Increment
					db.incrementData(Field.ITEM_DROP, player.getName(),
							dropped, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getItemDrop() != null)
				{
					details.put("Item", event.getItemDrop().getItemStack()
							.toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void eggThrow(final PlayerEggThrowEvent event)
	{
		if (config.eggThrow && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on throw
			if (!KconEventLogic.hitLimit(Field.EGG_THROW, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.EGG_THROW, player, null, null))
				{
					// Increment
					db.incrementData(Field.EGG_THROW, player.getName(), null,
							null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getEgg() != null)
				{
					// TODO pay for specifics?
					details.put("Egg", event.getEgg().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void gameModeValid(final PlayerGameModeChangeEvent event)
	{
		if (!event.isCancelled()
				&& (config.gameModeCreative || config.gameModeSurvival)
				&& event.getPlayer() != null)
		{
			boolean cancel = false;
			final Player player = event.getPlayer();
			switch (event.getNewGameMode())
			{
				case CREATIVE:
				{
					if (KconEventLogic.deny(Field.CREATIVE, player,
							config.gameModeCreativeDenyPay,
							config.gameModeCreativeDenyLimit, null, null))
					{
						// Deny
						cancel = true;
					}
					break;

				}
				case SURVIVAL:
				{
					if (KconEventLogic.deny(Field.SURVIVAL, player,
							config.gameModeSurvivalDenyPay,
							config.gameModeSurvivalDenyLimit, null, null))
					{
						// Deny
						cancel = true;
					}
					break;
				}
				default:
				{
					if (config.debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled " + event.getEventName()
										+ " for game mode '"
										+ event.getNewGameMode() + "'");
					}
					break;
				}
			}
			// deny
			if (cancel)
			{
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getNewGameMode() != null)
					{
						details.put("GameMode", event.getNewGameMode()
								.toString());
					}
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void gameMode(final PlayerGameModeChangeEvent event)
	{
		if (!event.isCancelled()
				&& (config.gameModeCreative || config.gameModeSurvival)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on mode
			switch (event.getNewGameMode())
			{
				case CREATIVE:
				{
					if (!KconEventLogic.hitLimit(Field.CREATIVE, player, null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.CREATIVE, player, null, null))
						{
							// Increment
							db.incrementData(Field.CREATIVE, player.getName(),
									null, null);
						}
					}
					break;
				}
				case SURVIVAL:
				{
					if (!KconEventLogic.hitLimit(Field.SURVIVAL, player, null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.SURVIVAL, player, null, null))
						{
							// Increment
							db.incrementData(Field.SURVIVAL, player.getName(),
									null, null);
						}
					}
					break;
				}
				default:
				{
					if (config.debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled " + event.getEventName()
										+ " for game mode '"
										+ event.getNewGameMode() + "'");
					}
					break;
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getNewGameMode() != null)
				{
					details.put("GameMode", event.getNewGameMode().toString());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	/**
	 * Event called when player joins the server
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void join(final PlayerJoinEvent event)
	{
		if (event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Add player if they don't exist to database
			final boolean add = db.addPlayer(player.getName());
			if (config.debugEvents && add)
			{
				plugin.getLogger().info(
						"Added player '" + player.getName() + "' to database");
			}
			// Check their last on date for potential reset
			if (!db.checkDateReset(player.getName()))
			{
				if (config.debugEvents)
				{
					plugin.getLogger().info(
							"Reset values for player '" + player.getName()
									+ "'");
				}
				db.resetAllValues(player.getName());
			}
			if (config.join)
			{
				// Pay on join
				if (!KconEventLogic.hitLimit(Field.JOIN, player, null, null))
				{
					// Try to pay
					if (KarmicEcon.pay(Field.JOIN, player, null, null))
					{
						// Increment
						db.incrementData(Field.JOIN, player.getName(), null,
								null);
					}
				}
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getJoinMessage() != null)
					{
						details.put("Message", event.getJoinMessage());
					}
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void kick(final PlayerKickEvent event)
	{
		if (config.kick && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on kick
			if (!KconEventLogic.hitLimit(Field.KICK, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.KICK, player, null, null))
				{
					// Increment
					db.incrementData(Field.KICK, player.getName(), null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getReason() != null)
				{
					details.put("Bucket", event.getReason());
				}
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void quit(final PlayerQuitEvent event)
	{
		if (event.getPlayer() != null)
		{
			// Remove any message from cache
			final Player player = event.getPlayer();
			if (Karmiconomy.sentMessages.containsKey(player.getName()))
			{
				Karmiconomy.sentMessages.remove(player.getName());
			}
			if (config.quit)
			{

				// Pay on quit
				if (!KconEventLogic.hitLimit(Field.QUIT, player, null, null))
				{
					// Try to pay
					if (KarmicEcon.pay(Field.QUIT, player, null, null))
					{
						// Increment
						db.incrementData(Field.QUIT, player.getName(), null,
								null);
					}
				}
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getQuitMessage() != null)
					{
						details.put("Message", event.getQuitMessage());
					}
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void sneakValid(final PlayerToggleSneakEvent event)
	{
		if (!event.isCancelled() && event.isSneaking() && config.sneak
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic.deny(Field.SNEAK, player, config.sneakDenyPay,
					config.sneakDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void sneak(final PlayerToggleSneakEvent event)
	{
		if (!event.isCancelled() && event.isSneaking() && config.sneak
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on sneak
			if (!KconEventLogic.hitLimit(Field.SNEAK, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.SNEAK, player, null, null))
				{
					// Increment
					db.incrementData(Field.SNEAK, player.getName(), null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void sprintValid(final PlayerToggleSprintEvent event)
	{
		if (!event.isCancelled() && event.isSprinting() && config.sprint
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic.deny(Field.SPRINT, player, config.sprintDenyPay,
					config.sprintDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void sprint(final PlayerToggleSprintEvent event)
	{
		if (!event.isCancelled() && event.isSprinting() && config.sprint
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on sprint
			if (!KconEventLogic.hitLimit(Field.SPRINT, player, null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.SPRINT, player, null, null))
				{
					// Increment
					db.incrementData(Field.SPRINT, player.getName(), null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleEnterValid(final VehicleEnterEvent event)
	{
		if (!event.isCancelled() && config.vehicleEnter
				&& event.getEntered() != null)
		{
			if (event.getEntered() instanceof Player)
			{
				final Player player = (Player) event.getEntered();
				// TODO deny
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getVehicle() != null)
					{
						details.put("Vehicle", event.getVehicle().toString());
					}
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void vehicleEnter(final VehicleEnterEvent event)
	{
		if (!event.isCancelled() && config.vehicleEnter
				&& event.getEntered() != null)
		{
			if (event.getEntered() instanceof Player)
			{
				final Player player = (Player) event.getEntered();
				// TODO pay on enter
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getVehicle() != null)
					{
						details.put("Vehicle", event.getVehicle().toString());
					}
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleExitValid(final VehicleExitEvent event)
	{
		if (!event.isCancelled() && config.vehicleExit
				&& event.getExited() != null)
		{
			if (event.getExited() instanceof Player)
			{
				final Player player = (Player) event.getExited();
				// TODO deny
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getVehicle() != null)
					{
						details.put("Vehicle", event.getVehicle().toString());
					}
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void vehicleExit(final VehicleExitEvent event)
	{
		if (!event.isCancelled() && config.vehicleExit
				&& event.getExited() != null)
		{
			if (event.getExited() instanceof Player)
			{
				final Player player = (Player) event.getExited();
				// TODO pay on enter
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getVehicle() != null)
					{
						details.put("Vehicle", event.getVehicle().toString());
					}
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void inventoryClick(final InventoryClickEvent event)
	{
		if (config.debugEvents)
		{
			final Map<String, String> details = new HashMap<String, String>();
			details.put("Player", event.getWhoClicked().getName());
			KconEventLogic.debugEvent(event, details);
		}
	}

	// TODO use all events, and perhaps make all players get affected due to
	// event?

	/**
	 * Missing events: ExpChange Fish LevelChange Move Pickup Shear SignChange
	 */
}
