package com.mitsugaru.Karmiconomy;

import java.util.HashMap;
import java.util.Map;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
//import org.bukkit.event.block.BlockIgniteEvent;
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
			if(config.chatDenyPay)
			{
				final double balance = eco.getBalance(player.getName());
				if(config.chatPay > balance)
				{
					event.setCancelled(true);
					sendLackMessage(player, DenyType.MONEY, "chat");
				}
			}
			if(config.chatDenyLimit)
			{
				//TODO deny by player limit
			}
			if (config.debugEvents && event.isCancelled())
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				details.put("Message", event.getMessage());
				details.put("Cancelled", "true");
				debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void chat(final PlayerChatEvent event)
	{
		if (!event.isCancelled() && config.chat && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO pay on chat
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
			if (config.debugEvents)
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
				if (config.debugEvents)
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void createPortalValid(final EntityCreatePortalEvent event)
	{
		if (!event.isCancelled()
				&& (config.portalCreateDenyPay || config.portalCreateDenyLimit)
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				// TODO deny
				// TODO can pay based on portal type
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getPortalType() != null)
					{
						details.put("Portal", event.getPortalType().toString());
					}
					details.put("Cancelled", "true");
					debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void createPortal(final EntityCreatePortalEvent event)
	{
		if (!event.isCancelled() && config.portalCreate
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				// TODO pay on portal create
				// TODO can pay based on portal type
				if (config.debugEvents)
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
				// TODO pay on portal create
				// TODO can pay based on portal type
				if (config.debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					if (event.getLocation() != null)
					{
						Block block = event.getLocation().getBlock();
						details.put("Block", block.toString());
					}
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
				// TODO deny
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void shootBow(final EntityShootBowEvent event)
	{
		if (!event.isCancelled() && config.shootBow
				&& event.getEntity() != null)
		{
			if (event.getEntity() instanceof Player)
			{
				final Player player = (Player) event.getEntity();
				// TODO pay on portal create
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void paintingPlaceValid(final PaintingPlaceEvent event)
	{
		if (!event.isCancelled()
				&& (config.paintingPlaceDenyPay || config.paintingPlaceDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO deny
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
				debugEvent(event, details);
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
			// TODO pay on place
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void bedEnterValid(final PlayerBedEnterEvent event)
	{
		if (!event.isCancelled()
				&& (config.bedEnterDenyPay || config.bedEnterDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO deny
			if (config.debugEvents)
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void bedEnter(final PlayerBedEnterEvent event)
	{
		if (!event.isCancelled() && config.bedEnter
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO pay on enter
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
			// TODO pay on leave
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
	public void bucketEmptyValid(final PlayerBucketEmptyEvent event)
	{
		if (!event.isCancelled()
				&& (config.bucketEmptyDenyPay || config.bucketEmptyDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO pay on empty
			if (config.debugEvents)
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
		if (!event.isCancelled() && config.bucketEmpty
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
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
				&& (config.bucketFillDenyPay || config.bucketFillDenyLimit)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO deny
			if (config.debugEvents)
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
		if (!event.isCancelled() && config.bucketFill
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
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
			// TODO pay on change
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
			// TODO pay on death
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
			// TODO deny
			if (config.debugEvents)
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void itemDrop(final PlayerDropItemEvent event)
	{
		if (!event.isCancelled() && config.itemDrop
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO pay on drop
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
			// TODO pay on throw
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
			if (config.debugEvents)
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void join(final PlayerJoinEvent event)
	{
		if (config.join && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO pay on join
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void kick(final PlayerKickEvent event)
	{
		if (config.kick && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// TODO pay on kick
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
			// TODO pay on quit
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
			// TODO deny
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				details.put("Cancelled", "true");
				debugEvent(event, details);
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
			// TODO pay on sneak
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
			// TODO deny
			if (config.debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				details.put("Cancelled", "true");
				debugEvent(event, details);
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
			// TODO pay on sneak
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
				if (config.debugEvents)
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
				if (config.debugEvents)
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
	
	private void sendLackMessage(Player player, DenyType type, String action)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.RED + Karmiconomy.TAG + " Lack ");
		switch(type)
		{
			case MONEY:
				sb.append("money ");
				break;
			case LIMIT:
				sb.append("limit ");
				break;
			default:
				sb.append("<reason> ");
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
