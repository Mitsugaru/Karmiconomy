package com.mitsugaru.Karmiconomy;

import java.util.HashMap;
import java.util.Map;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
// import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;

public class KarmiconomyListener implements Listener
{
	private Karmiconomy plugin;
	private DatabaseHandler db;
	private Economy eco;
	private Config config;

	public KarmiconomyListener(Karmiconomy plugin)
	{
		this.plugin = plugin;
		this.eco = plugin.getEconomy();
		this.db = plugin.getDatabaseHandler();
		this.config = plugin.getPluginConfig();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void chatValid(final PlayerChatEvent event)
	{
		if (!event.isCancelled()
				&& (config.chatDenyPay || config.chatDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (deny(Field.CHAT, player, config.chatDenyPay,
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
					debugEvent(event, details);
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
			if (!hitLimit(Field.CHAT, player, null, null))
			{
				// Try to pay
				if (pay(Field.CHAT, player, null, null))
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void commandValid(final PlayerCommandPreprocessEvent event)
	{
		if (!event.isCancelled()
				&& (config.commandDenyPay || config.commandDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (deny(Field.COMMAND, player, config.commandDenyPay,
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
					debugEvent(event, details);
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
			// TODO pay on command
			// Check if hit limit
			if (!hitLimit(Field.COMMAND, player, null, event.getMessage()))
			{
				// Try to pay
				if (pay(Field.COMMAND, player, null, null))
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void blockPlaceValid(final BlockPlaceEvent event)
	{
		if (!event.isCancelled()
				&& (config.blockPlaceDenyPay || config.blockPlaceDenyLimit)
				&& event.getPlayer() != null)
		{
			// TODO deny if lack money
			// pay by type
			final Player player = event.getPlayer();
			if (config.debugEvents && event.isCancelled())
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
				details.put("Cancelled", "true");
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockPlace(final BlockPlaceEvent event)
	{
		if (!event.isCancelled() && config.blockPlace
				&& event.getPlayer() != null)
		{
			// TODO pay on block create
			// pay by type
			final Player player = event.getPlayer();
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void blockDestroyValid(final BlockBreakEvent event)
	{
		if (!event.isCancelled()
				&& (config.blockDestroyDenyPay || config.blockDestroyDenyLimit)
				&& event.getPlayer() != null)
		{
			// TODO pay on block break
			// pay by type
			final Player player = event.getPlayer();
			if (config.debugEvents && event.isCancelled())
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBlock() != null)
				{
					details.put("Block", event.getBlock().toString());
				}
				details.put("Cancelled", "true");
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockDestroy(final BlockBreakEvent event)
	{
		if (!event.isCancelled() && config.blockDestroy
				&& event.getPlayer() != null)
		{
			// TODO pay on block break
			// pay by type
			final Player player = event.getPlayer();
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBlock() != null)
				{
					details.put("Block", event.getBlock().toString());
				}
				debugEvent(event, details);
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
	 * details.put("Cancelled", "true"); debugEvent(event, details); } } }
	 * @EventHandler(priority = EventPriority.MONITOR) public void
	 * blockIgnite(final BlockIgniteEvent event) { // Grab player if there is
	 * one if (!event.isCancelled() && config.blockIgnite && event.getPlayer()
	 * != null) { final Player player = event.getPlayer(); // TODO THIS IS NOT
	 * FOR PLAYERS? //may just get rid of this event // pay by type
	 * if(config.debugEvents) { final Map<String, String> details = new
	 * HashMap<String, String>(); details.put("Player", player.getName());
	 * if(event.getBlock() != null) { details.put("Block",
	 * event.getBlock().toString()); } debugEvent(event, details); } } }
	 */

	@EventHandler(priority = EventPriority.LOWEST)
	public void craftItemValid(final CraftItemEvent event)
	{
		if (!event.isCancelled()
				&& (config.craftItemDenyPay || config.craftItemDenyLimit)
				&& event.getWhoClicked() != null)
		{
			if (event.getWhoClicked() instanceof Player)
			{
				final Player player = (Player) event.getWhoClicked();
				// TODO deny
				// pay by type
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getRecipe().getResult() != null)
					{
						details.put("Result", event.getRecipe().getResult()
								.toString());
					}
					details.put("Cancelled", "true");
					debugEvent(event, details);
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
				// TODO pay on craft
				// pay by type
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getRecipe().getResult() != null)
					{
						details.put("Result", event.getRecipe().getResult()
								.toString());
					}
					debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void enchantItemValid(final EnchantItemEvent event)
	{
		if (!event.isCancelled() && config.enchantItem
				&& event.getEnchanter() != null)
		{
			final Player player = event.getEnchanter();
			// TODO deny
			// pay by type
			if (config.debugEvents && event.isCancelled())
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
				details.put("Cancelled", "true");
				debugEvent(event, details);
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
			// TODO pay on enchant
			// pay by type
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void createPortalValid(final EntityCreatePortalEvent event)
	{
		if (!event.isCancelled()
				&& (config.portalCreateNetherDenyPay
						|| config.portalCreateNetherDenyLimit
						|| config.portalCreateEndDenyPay
						|| config.portalCreateEndDenyLimit
						|| config.portalCreateCustomDenyPay || config.portalCreateCustomDenyLimit)
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
						if (deny(Field.PORTAL_CREATE_NETHER, player,
								config.portalCreateNetherDenyPay,
								config.portalCreateNetherDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
					}
					case ENDER:
					{
						if (deny(Field.PORTAL_CREATE_END, player,
								config.portalCreateEndDenyPay,
								config.portalCreateEndDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
					}
					case CUSTOM:
					{
						if (deny(Field.PORTAL_CREATE_CUSTOM, player,
								config.portalCreateCustomDenyPay,
								config.portalCreateCustomDenyLimit, null, null))
						{
							// Deny
							cancel = true;
						}
					}
					default:
					{
						// INFO unknown
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
						if (event.getPortalType() != null)
						{
							details.put("Portal", event.getPortalType()
									.toString());
						}
						details.put("Cancelled", "true");
						debugEvent(event, details);
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
						if (!hitLimit(Field.PORTAL_CREATE_NETHER, player, null,
								null))
						{
							// Try to pay
							if (pay(Field.PORTAL_CREATE_NETHER, player, null,
									null))
							{
								// Increment
								db.incrementData(Field.PORTAL_CREATE_NETHER,
										player.getName(), null, null);
							}
						}
					}
					case ENDER:
					{
						if (!hitLimit(Field.PORTAL_CREATE_END, player, null,
								null))
						{
							// Try to pay
							if (pay(Field.PORTAL_CREATE_END, player, null, null))
							{
								// Increment
								db.incrementData(Field.PORTAL_CREATE_END,
										player.getName(), null, null);
							}
						}
					}
					case CUSTOM:
					{
						if (!hitLimit(Field.PORTAL_CREATE_CUSTOM, player, null,
								null))
						{
							// Try to pay
							if (pay(Field.PORTAL_CREATE_CUSTOM, player, null,
									null))
							{
								// Increment
								db.incrementData(Field.PORTAL_CREATE_CUSTOM,
										player.getName(), null, null);
							}
						}
					}
					default:
					{
						// INFO unknown
						break;
					}
				}
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getPortalType() != null)
					{
						details.put("Portal", event.getPortalType().toString());
					}
					debugEvent(event, details);
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
				if (!hitLimit(Field.PORTAL_ENTER, player, null, null))
				{
					// Try to pay
					if (pay(Field.PORTAL_ENTER, player, null, null))
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
					debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void shootBowValid(final EntityShootBowEvent event)
	{
		if (!event.isCancelled()
				&& (config.shootBowDenyPay || config.shootBowDenyLimit)
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				if (deny(Field.BOW_SHOOT, player, config.shootBowDenyPay,
						config.shootBowDenyLimit, null, null))
				{
					// Deny
					event.setCancelled(true);
					if (config.debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						details.put("Force", "" + event.getForce());
						details.put("Cancelled", "true");
						debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void shootBow(final EntityShootBowEvent event)
	{
		if (!event.isCancelled() && config.shootBow
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				if (!hitLimit(Field.BOW_SHOOT, player, null, null))
				{
					// Try to pay
					if (pay(Field.BOW_SHOOT, player, null, null))
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
					debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void tameValid(final EntityTameEvent event)
	{
		// Move config check inside if
		if (!event.isCancelled()
				&& (config.tameOcelotDenyPay || config.tameWolfDenyPay
						|| config.tameOcelotDenyLimit || config.tameWolfDenyLimit)
				&& event.getOwner() != null)
		{
			if (event.getOwner() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				// TODO cancel
				// check entity tamed
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getEntityType() != null)
					{
						details.put("EntityType", event.getEntityType()
								.toString());
					}
					details.put("Cancelled", "true");
					debugEvent(event, details);
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
				final Player player = (Player) event.getEntity();
				// TODO pay on tame
				// check entity tamed
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getEntityType() != null)
					{
						details.put("EntityType", event.getEntityType()
								.toString());
					}
					debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void paintingPlaceValid(final PaintingPlaceEvent event)
	{
		if (!event.isCancelled()
				&& (config.paintingPlaceDenyPay || config.paintingPlaceDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (deny(Field.PAINTING_PLACE, player, config.paintingPlaceDenyPay,
					config.paintingPlaceDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				// TODO also need to get painting break
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getPainting() != null)
					{
						details.put("Painting", event.getPainting().toString());
					}
					details.put("Cancelled", "true");
					debugEvent(event, details);
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
			if (!hitLimit(Field.PAINTING_PLACE, player, null, null))
			{
				// Try to pay
				if (pay(Field.PAINTING_PLACE, player, null, null))
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bedEnterValid(final PlayerBedEnterEvent event)
	{
		if (!event.isCancelled()
				&& (config.bedEnterDenyPay || config.bedEnterDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (deny(Field.BED_ENTER, player, config.bedEnterDenyPay,
					config.bedEnterDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getBed() != null)
					{
						details.put("Bed", event.getBed().toString());
					}
					details.put("Cancelled", "true");
					debugEvent(event, details);
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
			if (!hitLimit(Field.BED_ENTER, player, null, null))
			{
				// Try to pay
				if (pay(Field.BED_ENTER, player, null, null))
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
				debugEvent(event, details);
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
			if (!hitLimit(Field.BED_LEAVE, player, null, null))
			{
				// Try to pay
				if (pay(Field.BED_LEAVE, player, null, null))
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bucketEmptyValid(final PlayerBucketEmptyEvent event)
	{
		if (!event.isCancelled()
				&& (config.bucketEmptyWaterDenyPay
						|| config.bucketEmptyWaterDenyLimit
						|| config.bucketEmptyLavaDenyPay || config.bucketEmptyLavaDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO switch for type of bucket
			// TODO pay on empty
			if (config.debugEvents && event.isCancelled())
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBucket() != null)
				{
					details.put("Bucket", event.getBucket().toString());
				}
				details.put("Cancelled", "true");
				debugEvent(event, details);
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
			// TODO switch for type of bucket
			// TODO pay on empty
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBucket() != null)
				{
					details.put("Bucket", event.getBucket().toString());
				}
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bucketFillValid(final PlayerBucketFillEvent event)
	{
		if (!event.isCancelled()
				&& (config.bucketFillWaterDenyPay
						|| config.bucketFillWaterDenyLimit
						|| config.bucketFillLavaDenyPay || config.bucketFillLavaDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO switch for type of bucket
			// TODO deny
			if (config.debugEvents && event.isCancelled())
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBucket() != null)
				{
					// TODO change this the specific type
					details.put("Bucket", event.getBucket().toString());
				}
				details.put("Cancelled", "true");
				debugEvent(event, details);
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
			// TODO switch for type of bucket
			// TODO pay on fill
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getBucket() != null)
				{
					// TODO change this the specific type
					details.put("Bucket", event.getBucket().toString());
				}
				debugEvent(event, details);
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
			if (!hitLimit(Field.WORLD_CHANGE, player, null, null))
			{
				// Try to pay
				if (pay(Field.WORLD_CHANGE, player, null, null))
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
				debugEvent(event, details);
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
			if (!hitLimit(Field.DEATH, player, null, null))
			{
				// Try to pay
				if (pay(Field.DEATH, player, null, null))
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
				debugEvent(event, details);
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
			if (!hitLimit(Field.RESPAWN, player, null, null))
			{
				// Try to pay
				if (pay(Field.RESPAWN, player, null, null))
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void itemDropValid(final PlayerDropItemEvent event)
	{
		if (!event.isCancelled()
				&& (config.itemDropDenyPay || config.itemDropDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			final Item dropped = new Item(event.getItemDrop().getItemStack());
			if (deny(Field.ITEM_DROP, player, config.bedEnterDenyPay,
					config.bedEnterDenyLimit, dropped, null))
			{
				event.setCancelled(true);
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getItemDrop() != null)
					{
						details.put("Item", event.getItemDrop().getItemStack()
								.toString());
					}
					details.put("Cancelled", "true");
					debugEvent(event, details);
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
			if (!hitLimit(Field.ITEM_DROP, player, dropped, null))
			{
				// Try to pay
				if (pay(Field.ITEM_DROP, player, dropped, null))
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
				debugEvent(event, details);
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
			if (!hitLimit(Field.EGG_THROW, player, null, null))
			{
				// Try to pay
				if (pay(Field.EGG_THROW, player, null, null))
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
					// TODO pay for specifics
					details.put("Egg", event.getEgg().toString());
				}
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void gameModeValid(final PlayerGameModeChangeEvent event)
	{
		// TODO move config check inside if
		if (!event.isCancelled()
				&& (config.gameModeCreativeDenyPay
						|| config.gameModeSurvivalDenyPay
						|| config.gameModeCreativeDenyLimit || config.gameModeSurvivalDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO deny
			if (config.debugEvents && event.isCancelled())
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getNewGameMode() != null)
				{
					details.put("GameMode", event.getNewGameMode().toString());
				}
				details.put("Cancelled", "true");
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void gameMode(final PlayerGameModeChangeEvent event)
	{
		// TODO move config check inside if
		if (!event.isCancelled()
				&& (config.gameModeCreative || config.gameModeSurvival)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO pay on mode
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getNewGameMode() != null)
				{
					details.put("GameMode", event.getNewGameMode().toString());
				}
				debugEvent(event, details);
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
			if (db.checkDateReset(player.getName()))
			{
				db.resetAllValues(player.getName());
			}
			if (config.join)
			{
				// Pay on join
				if (!hitLimit(Field.JOIN, player, null, null))
				{
					// Try to pay
					if (pay(Field.JOIN, player, null, null))
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
					debugEvent(event, details);
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
			if (!hitLimit(Field.KICK, player, null, null))
			{
				// Try to pay
				if (pay(Field.KICK, player, null, null))
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void quit(final PlayerQuitEvent event)
	{
		if (config.quit && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Pay on quit
			if (!hitLimit(Field.QUIT, player, null, null))
			{
				// Try to pay
				if (pay(Field.QUIT, player, null, null))
				{
					// Increment
					db.incrementData(Field.QUIT, player.getName(), null, null);
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
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void sneakValid(final PlayerToggleSneakEvent event)
	{
		if (!event.isCancelled() && event.isSneaking()
				&& (config.sneakDenyPay || config.sneakDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (deny(Field.SNEAK, player, config.sneakDenyPay,
					config.sneakDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Cancelled", "true");
					debugEvent(event, details);
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
			if (!hitLimit(Field.SNEAK, player, null, null))
			{
				// Try to pay
				if (pay(Field.SNEAK, player, null, null))
				{
					// Increment
					db.incrementData(Field.SNEAK, player.getName(), null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void sprintValid(final PlayerToggleSprintEvent event)
	{
		if (!event.isCancelled() && event.isSprinting()
				&& (config.sprintDenyPay || config.sprintDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (deny(Field.SPRINT, player, config.sprintDenyPay,
					config.sprintDenyLimit, null, null))
			{
				// Deny
				event.setCancelled(true);
				if (config.debugEvents && event.isCancelled())
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Cancelled", "true");
					debugEvent(event, details);
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
			if (!hitLimit(Field.SPRINT, player, null, null))
			{
				// Try to pay
				if (pay(Field.SPRINT, player, null, null))
				{
					// Increment
					db.incrementData(Field.SPRINT, player.getName(), null, null);
				}
			}
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleEnterValid(final VehicleEnterEvent event)
	{
		if (!event.isCancelled()
				&& (config.vehicleEnterDenyPay || config.vehicleEnterDenyLimit)
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
					debugEvent(event, details);
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
					debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleExitValid(final VehicleExitEvent event)
	{
		if (!event.isCancelled()
				&& (config.vehicleExitDenyPay || config.vehicleExitDenyLimit)
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
					debugEvent(event, details);
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
					debugEvent(event, details);
				}
			}
		}
	}

	private void debugEvent(Event event, Map<String, String> details)
	{
		plugin.getLogger().info("Event: " + event.getEventName());
		for (Map.Entry<String, String> entry : details.entrySet())
		{
			plugin.getLogger().info(entry.getKey() + " : " + entry.getValue());
		}
	}

	private boolean pay(Field field, Player player, Item item, String command)
	{
		final double amount = config.getPayValue(field, item, command);
		EconomyResponse response = null;
		if (amount == 0.0)
		{
			// Just record that it happened
			return true;
		}
		else if (amount > 0.0)
		{
			response = eco.depositPlayer(player.getName(), amount);
		}
		else if (amount < 0.0)
		{
			response = eco.withdrawPlayer(player.getName(), (amount * -1));
		}
		if (response != null)
		{
			switch (response.type)
			{
				case FAILURE:
				{
					player.sendMessage("Could not pay " + amount + "! D:");
					plugin.getLogger().severe(
							"Eco Failure: " + response.errorMessage);
					break;
				}
				case NOT_IMPLEMENTED:
				{
					player.sendMessage("Could not pay " + amount + "! D:");
					plugin.getLogger().severe(
							"Eco not implemented: " + response.errorMessage);
					break;
				}
				case SUCCESS:
				{
					if (config.debugEconomy)
					{
						plugin.getLogger().info(
								"Eco success for player '" + player.getName()
										+ "' of amount: " + amount);
					}
					return true;
				}
				default:
					break;
			}
		}
		// Unsuccessful transaction
		return false;
	}

	private boolean deny(Field field, Player player, boolean denyPay,
			boolean denyLimit, Item item, String command)
	{
		if (denyPay)
		{
			// Deny by player balance
			final double balance = eco.getBalance(player.getName());
			double pay = config.getPayValue(field, item, command);
			if (pay < 0.0)
			{
				// Only care about negatives. Need to change to positive for
				// comparison.
				pay *= -1;
				if (pay > balance)
				{
					sendLackMessage(player, DenyType.MONEY, "chat");
					if (config.debugEvents)
					{
						plugin.getLogger().info(
								"Denied " + field + " for player "
										+ player.getName() + " for "
										+ DenyType.MONEY);
					}
					return true;
				}
			}
		}
		if (denyLimit)
		{
			final int cLimit = config.getLimitValue(field, item, command);
			if (cLimit >= 0)
			{
				// Deny by player limit
				final int limit = db.getData(field, player.getName(), item,
						command);
				if (limit >= cLimit)
				{
					sendLackMessage(player, DenyType.LIMIT, "chat");
					if (config.debugEvents)
					{
						plugin.getLogger().info(
								"Denied " + field + " for player "
										+ player.getName() + " for "
										+ DenyType.LIMIT);
					}
					return true;
				}
			}
		}
		return false;
	}

	private boolean hitLimit(Field field, Player player, Item item,
			String command)
	{
		final int cLimit = config.getLimitValue(field, item, command);
		final int limit = db.getData(field, player.getName(), item, command);
		if (cLimit >= 0)
		{
			if (limit >= cLimit)
			{
				// They hit the config limit
				return true;
			}
		}
		return false;
	}

	private void sendLackMessage(Player player, DenyType type, String action)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.RED + Karmiconomy.TAG);
		switch (type)
		{
			case MONEY:
				sb.append(" Lack money ");
				break;
			case LIMIT:
				sb.append(" Hit limit ");
				break;
			default:
				sb.append(" Unknown DenyType ");
				break;
		}
		sb.append("for action: " + ChatColor.GOLD + action);
		player.sendMessage(sb.toString());
	}

	private enum DenyType
	{
		MONEY, LIMIT;
	}

	// TODO use all events, and perhaps make all players get affected due to
	// event?

	/**
	 * Missing events: ExpChange Fish LevelChange Move Pickup Shear SignChange
	 */
}
