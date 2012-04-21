package com.mitsugaru.Karmiconomy.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.party.McMMOPartyTeleportEvent;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.KconMcMMOConfig;
import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;

public class McMMOListener implements Listener
{
	private Karmiconomy plugin;
	private KconMcMMOConfig config;

	public McMMOListener(Karmiconomy plugin)
	{
		this.plugin = plugin;
		config = new KconMcMMOConfig(plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerLevelUp(McMMOPlayerLevelUpEvent event)
	{
		if (event.getPlayer() != null && event.getSkill() != null)
		{
			switch (event.getSkill())
			{
				case ACROBATICS:
				{
					break;
				}
				case ARCHERY:
				{
					break;
				}
				case AXES:
				{
					break;
				}
				case EXCAVATION:
				{
					break;
				}
				case FISHING:
				{
					break;
				}
				case HERBALISM:
				{
					break;
				}
				case MINING:
				{
					break;
				}
				case REPAIR:
				{
					break;
				}
				case SWORDS:
				{
					break;
				}
				case TAMING:
				{
					break;
				}
				case UNARMED:
				{
					break;
				}
				case WOODCUTTING:
				{
					break;
				}
				default:
				{
					if (plugin.getPluginConfig().debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled skill '" + event.getSkill().name());
					}
					break;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void partyTeleportValid(McMMOPartyTeleportEvent event)
	{
		if (!event.isCancelled() && config.partyTeleport && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if(KconEventLogic.deny(Field.MCMMO_PARTY_TELEPORT, player, config.partyTeleportDenyPay, config.partyTeleportDenyLimit, null, null))
			{
				// Cancel event
				event.setCancelled(true);
				if (plugin.getPluginConfig().debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Party", event.getParty());
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}
	
	
}
