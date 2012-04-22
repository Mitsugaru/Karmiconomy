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

import com.mitsugaru.Karmiconomy.DatabaseHandler;
import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.KarmicEcon;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;
import com.mitsugaru.Karmiconomy.config.Config;
import com.mitsugaru.Karmiconomy.events.EventLogic.DenyType;

public class KarmiconomyListener implements Listener
{
	private Karmiconomy plugin;
	private DatabaseHandler db;
	private Config config;

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
			if (EventLogic.deny(Field.CHAT, player, config.chatDenyPay,
					config.getPayValue(Field.CHAT, null, null),
					config.chatDenyLimit,
					config.getLimitValue(Field.CHAT, null, null), null, null))
			{
				// Cancel event
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Message", event.getMessage());
					details.put("Cancelled", "true");
					EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.CHAT, player,
					config.getLimitValue(Field.CHAT, null, null), null, null))
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
				EventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void commandValid(final PlayerCommandPreprocessEvent event)
	{
		if (!event.isCancelled() && config.command && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (EventLogic.deny(
					Field.COMMAND,
					player,
					config.commandDenyPay,
					config.getPayValue(Field.CHAT, null, event.getMessage()),
					config.commandDenyLimit,
					config.getLimitValue(Field.COMMAND, null,
							event.getMessage()), null, event.getMessage()))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Message", event.getMessage());
					details.put("Cancelled", "true");
					EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(
					Field.COMMAND,
					player,
					config.getLimitValue(Field.COMMAND, null,
							event.getMessage()), null, event.getMessage()))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.COMMAND, player, null,
						event.getMessage()))
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
				EventLogic.debugEvent(event, details);
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

				if (EventLogic.deny(Field.BLOCK_PLACE, player, denyPay,
						config.getPayValue(Field.BLOCK_PLACE, placed, null),
						denyLimit,
						config.getLimitValue(Field.BLOCK_PLACE, placed, null),
						placed, null))
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
						EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.BLOCK_PLACE, player,
					config.getLimitValue(Field.BLOCK_PLACE, placed, null),
					placed, null))
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
				EventLogic.debugEvent(event, details);
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

				if (EventLogic
						.deny(Field.BLOCK_DESTROY, player, denyPay, config
								.getPayValue(Field.BLOCK_DESTROY, destroyed,
										null), denyLimit, config.getLimitValue(
								Field.BLOCK_DESTROY, destroyed, null),
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
						EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.BLOCK_DESTROY, player,
					config.getLimitValue(Field.BLOCK_DESTROY, destroyed, null),
					destroyed, null))
			{
				// Try to pay
				if (KarmicEcon
						.pay(Field.BLOCK_DESTROY, player, destroyed, null))
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
				EventLogic.debugEvent(event, details);
			}
		}
	}

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

					if (EventLogic
							.deny(Field.ITEM_CRAFT, player, denyPay,
									config.getPayValue(Field.ITEM_CRAFT, craft,
											null), denyLimit, config
											.getLimitValue(Field.ITEM_CRAFT,
													craft, null), craft, null))
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
							EventLogic.debugEvent(event, details);
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
				if (!EventLogic.hitLimit(Field.ITEM_CRAFT, player,
						config.getLimitValue(Field.ITEM_CRAFT, craft, null),
						craft, null))
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
					EventLogic.debugEvent(event, details);
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

				if (EventLogic
						.deny(Field.ITEM_ENCHANT, player, denyPay,
								config.getPayValue(Field.ITEM_ENCHANT, enchant,
										null), denyLimit, config.getLimitValue(
										Field.ITEM_ENCHANT, enchant, null),
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
						EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.ITEM_ENCHANT, player,
					config.getLimitValue(Field.ITEM_ENCHANT, enchant, null),
					enchant, null))
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
				EventLogic.debugEvent(event, details);
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
						if (EventLogic.deny(Field.PORTAL_CREATE_NETHER,
								player, config.portalCreateNetherDenyPay,
								config.getPayValue(Field.PORTAL_CREATE_NETHER,
										null, null),
								config.portalCreateNetherDenyLimit, config
										.getLimitValue(
												Field.PORTAL_CREATE_NETHER,
												null, null), null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					case ENDER:
					{
						if (EventLogic.deny(Field.PORTAL_CREATE_END,
								player, config.portalCreateEndDenyPay, config
										.getPayValue(Field.PORTAL_CREATE_END,
												null, null),
								config.portalCreateEndDenyLimit, config
										.getLimitValue(Field.PORTAL_CREATE_END,
												null, null), null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					case CUSTOM:
					{
						if (EventLogic.deny(Field.PORTAL_CREATE_CUSTOM,
								player, config.portalCreateCustomDenyPay,
								config.getPayValue(Field.PORTAL_CREATE_CUSTOM,
										null, null),
								config.portalCreateCustomDenyLimit, config
										.getLimitValue(
												Field.PORTAL_CREATE_CUSTOM,
												null, null), null, null))
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
						EventLogic.debugEvent(event, details);
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
						if (!EventLogic.hitLimit(
								Field.PORTAL_CREATE_NETHER, player, config
										.getLimitValue(
												Field.PORTAL_CREATE_NETHER,
												null, null), null, null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.PORTAL_CREATE_NETHER,
									player, null, null))
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
						if (!EventLogic.hitLimit(Field.PORTAL_CREATE_END,
								player, config.getLimitValue(
										Field.PORTAL_CREATE_END, null, null),
								null, null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.PORTAL_CREATE_END, player,
									null, null))
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
						if (!EventLogic.hitLimit(
								Field.PORTAL_CREATE_CUSTOM, player, config
										.getLimitValue(
												Field.PORTAL_CREATE_CUSTOM,
												null, null), null, null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.PORTAL_CREATE_CUSTOM,
									player, null, null))
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
					EventLogic.debugEvent(event, details);
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
				if (!EventLogic.hitLimit(Field.PORTAL_ENTER, player,
						config.getLimitValue(Field.PORTAL_ENTER, null, null),
						null, null))
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
					EventLogic.debugEvent(event, details);
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
					EventLogic.sendLackMessage(player, DenyType.FORCE,
							Field.BOW_SHOOT.name(), "" + event.getForce());
				}

				else if (EventLogic.deny(Field.BOW_SHOOT, player,

				config.shootBowDenyPay,
						config.getPayValue(Field.BOW_SHOOT, null, null),
						config.shootBowDenyLimit,
						config.getLimitValue(Field.BOW_SHOOT, null, null),
						null, null))
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
						EventLogic.debugEvent(event, details);
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
				if (!EventLogic.hitLimit(Field.BOW_SHOOT, player,
						config.getLimitValue(Field.BOW_SHOOT, null, null),
						null, null))
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
					EventLogic.debugEvent(event, details);
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
						if (EventLogic.deny(Field.TAME_OCELOT, player,
								config.tameOcelotDenyPay, config.getPayValue(
										Field.TAME_OCELOT, null, null),
								config.tameOcelotDenyLimit, config
										.getLimitValue(Field.TAME_OCELOT, null,
												null), null, null))
						{
							// Deny
							cancel = true;
						}
						break;
					}
					case WOLF:
					{
						// Wolf
						if (EventLogic
								.deny(Field.TAME_WOLF, player,
										config.tameWolfDenyPay, config
												.getPayValue(Field.TAME_WOLF,
														null, null),
										config.tameWolfDenyLimit, config
												.getLimitValue(Field.TAME_WOLF,
														null, null), null, null))
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
						EventLogic.debugEvent(event, details);
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
						if (!EventLogic.hitLimit(Field.TAME_OCELOT, player,
								config.getLimitValue(Field.TAME_OCELOT, null,
										null), null, null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.TAME_OCELOT, player, null,
									null))
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
						if (!EventLogic.hitLimit(Field.TAME_WOLF, player,
								config.getLimitValue(Field.TAME_WOLF, null,
										null), null, null))
						{
							// Try to pay
							if (KarmicEcon.pay(Field.TAME_WOLF, player, null,
									null))
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
					EventLogic.debugEvent(event, details);
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
			if (EventLogic.deny(Field.PAINTING_PLACE, player,
					config.paintingPlaceDenyPay,
					config.getPayValue(Field.PAINTING_PLACE, null, null),
					config.paintingPlaceDenyLimit,
					config.getLimitValue(Field.PAINTING_PLACE, null, null),
					null, null))
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
					EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.PAINTING_PLACE, player,
					config.getLimitValue(Field.PAINTING_PLACE, null, null),
					null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.PAINTING_PLACE, player, null, null))
				{
					// Increment
					db.incrementData(Field.PAINTING_PLACE, player.getName(),
							null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getPainting() != null)
				{
					details.put("Painting", event.getPainting().toString());
				}
				EventLogic.debugEvent(event, details);
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
			if (EventLogic.deny(Field.BED_ENTER, player,
					config.bedEnterDenyPay,
					config.getPayValue(Field.BED_ENTER, null, null),
					config.bedEnterDenyLimit,
					config.getLimitValue(Field.BED_ENTER, null, null), null,
					null))
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
					EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.BED_ENTER, player,
					config.getLimitValue(Field.BED_ENTER, null, null), null,
					null))
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
				EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.BED_LEAVE, player,
					config.getLimitValue(Field.BED_LEAVE, null, null), null,
					null))
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
				EventLogic.debugEvent(event, details);
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
					if (EventLogic.deny(Field.BUCKET_EMPTY_LAVA, player,
							config.bucketEmptyLavaDenyPay, config.getPayValue(
									Field.BUCKET_EMPTY_LAVA, null, null),
							config.bucketEmptyLavaDenyLimit, config
									.getLimitValue(Field.BUCKET_EMPTY_LAVA,
											null, null), null, null))
					{
						// deny
						cancel = true;
					}
					break;
				}
				case WATER_BUCKET:
				{
					if (EventLogic.deny(Field.BUCKET_EMPTY_WATER, player,
							config.bucketEmptyWaterDenyPay, config.getPayValue(
									Field.BUCKET_EMPTY_WATER, null, null),
							config.bucketEmptyWaterDenyLimit, config
									.getLimitValue(Field.BUCKET_EMPTY_WATER,
											null, null), null, null))
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
					EventLogic.debugEvent(event, details);
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
					if (!EventLogic.hitLimit(Field.BUCKET_EMPTY_LAVA,
							player, config.getLimitValue(
									Field.BUCKET_EMPTY_LAVA, null, null), null,
							null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_EMPTY_LAVA, player,
								null, null))
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
					if (!EventLogic.hitLimit(Field.BUCKET_EMPTY_WATER,
							player, config.getLimitValue(
									Field.BUCKET_EMPTY_WATER, null, null),
							null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_EMPTY_WATER, player,
								null, null))
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
				EventLogic.debugEvent(event, details);
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
					if (EventLogic.deny(Field.BUCKET_FILL_LAVA, player,
							config.bucketFillLavaDenyPay, config.getPayValue(
									Field.BUCKET_FILL_LAVA, null, null),
							config.bucketFillLavaDenyLimit, config
									.getLimitValue(Field.BUCKET_FILL_LAVA,
											null, null), null, null))
					{
						// Deny
						cancel = true;
					}
					break;
				}
				case STATIONARY_WATER:
				{
					if (EventLogic.deny(Field.BUCKET_FILL_WATER, player,
							config.bucketFillWaterDenyPay, config.getPayValue(
									Field.BUCKET_FILL_WATER, null, null),
							config.bucketFillWaterDenyLimit, config
									.getLimitValue(Field.BUCKET_FILL_WATER,
											null, null), null, null))
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
					EventLogic.debugEvent(event, details);
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
					if (!EventLogic.hitLimit(Field.BUCKET_FILL_LAVA,
							player, config.getLimitValue(
									Field.BUCKET_FILL_LAVA, null, null), null,
							null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_FILL_LAVA, player,
								null, null))
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
					if (!EventLogic.hitLimit(Field.BUCKET_FILL_WATER,
							player, config.getLimitValue(
									Field.BUCKET_FILL_WATER, null, null), null,
							null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.BUCKET_FILL_WATER, player,
								null, null))
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
					details.put("Bucket", event.getBucket().toString());
				}
				if (event.getBlockClicked() != null)
				{
					details.put("Block", event.getBlockClicked().getType()
							.name());
				}
				EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.WORLD_CHANGE, player,
					config.getLimitValue(Field.WORLD_CHANGE, null, null), null,
					null))
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
				EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.DEATH, player,
					config.getLimitValue(Field.DEATH, null, null), null, null))
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
				EventLogic.debugEvent(event, details);
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
			if (!EventLogic
					.hitLimit(Field.RESPAWN, player,
							config.getLimitValue(Field.RESPAWN, null, null),
							null, null))
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
				EventLogic.debugEvent(event, details);
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

				if (EventLogic.deny(Field.ITEM_PICKUP, player, denyPay,
						config.getPayValue(Field.ITEM_PICKUP, pickup, null),
						denyLimit,
						config.getLimitValue(Field.ITEM_PICKUP, pickup, null),
						pickup, null))
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
						EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.ITEM_PICKUP, player,
					config.getLimitValue(Field.ITEM_PICKUP, pickup, null),
					pickup, null))
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
				EventLogic.debugEvent(event, details);
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

				if (EventLogic.deny(Field.ITEM_DROP, player, denyPay,
						config.getPayValue(Field.ITEM_DROP, dropped, null),
						denyLimit,
						config.getLimitValue(Field.ITEM_DROP, dropped, null),
						dropped, null))
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
						EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.ITEM_DROP, player,
					config.getLimitValue(Field.ITEM_DROP, dropped, null),
					dropped, null))
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
				EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.EGG_THROW, player,
					config.getLimitValue(Field.EGG_THROW, null, null), null,
					null))
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
				EventLogic.debugEvent(event, details);
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
					if (EventLogic.deny(Field.CREATIVE, player,
							config.gameModeCreativeDenyPay,
							config.getPayValue(Field.CREATIVE, null, null),
							config.gameModeCreativeDenyLimit,
							config.getLimitValue(Field.CREATIVE, null, null),
							null, null))
					{
						// Deny
						cancel = true;
					}
					break;

				}
				case SURVIVAL:
				{
					if (EventLogic.deny(Field.SURVIVAL, player,
							config.gameModeSurvivalDenyPay,
							config.getPayValue(Field.SURVIVAL, null, null),
							config.gameModeSurvivalDenyLimit,
							config.getLimitValue(Field.SURVIVAL, null, null),
							null, null))
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
					EventLogic.debugEvent(event, details);
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
					if (!EventLogic.hitLimit(Field.CREATIVE, player,
							config.getLimitValue(Field.CREATIVE, null, null),
							null, null))
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
					if (!EventLogic.hitLimit(Field.SURVIVAL, player,
							config.getLimitValue(Field.SURVIVAL, null, null),
							null, null))
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
				EventLogic.debugEvent(event, details);
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
				if (!EventLogic.hitLimit(Field.JOIN, player,
						config.getLimitValue(Field.JOIN, null, null), null,
						null))
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
					EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.KICK, player,
					config.getLimitValue(Field.KICK, null, null), null, null))
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
				EventLogic.debugEvent(event, details);
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
				if (!EventLogic.hitLimit(Field.QUIT, player,
						config.getLimitValue(Field.QUIT, null, null), null,
						null))
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
					EventLogic.debugEvent(event, details);
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
			if (EventLogic.deny(Field.SNEAK, player, config.sneakDenyPay,
					config.getPayValue(Field.SNEAK, null, null),
					config.sneakDenyLimit,
					config.getLimitValue(Field.SNEAK, null, null), null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Cancelled", "true");
					EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.SNEAK, player,
					config.getLimitValue(Field.SNEAK, null, null), null, null))
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
				EventLogic.debugEvent(event, details);
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
			if (EventLogic.deny(Field.SPRINT, player, config.sprintDenyPay,
					config.getPayValue(Field.SPRINT, null, null),
					config.sprintDenyLimit,
					config.getLimitValue(Field.SPRINT, null, null), null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Cancelled", "true");
					EventLogic.debugEvent(event, details);
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
			if (!EventLogic.hitLimit(Field.SPRINT, player,
					config.getLimitValue(Field.SPRINT, null, null), null, null))
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
				EventLogic.debugEvent(event, details);
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
					EventLogic.debugEvent(event, details);
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
					EventLogic.debugEvent(event, details);
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
					EventLogic.debugEvent(event, details);
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
					EventLogic.debugEvent(event, details);
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
			EventLogic.debugEvent(event, details);
		}
	}

	// TODO use all events, and perhaps make all players get affected due to
	// event?

	/**
	 * Missing events: ExpChange Fish LevelChange Move Pickup Shear SignChange
	 */
}
