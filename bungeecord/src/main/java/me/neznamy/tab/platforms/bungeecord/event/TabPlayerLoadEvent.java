package me.neznamy.tab.platforms.bungeecord.event;

import me.neznamy.tab.api.TabPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * Bungeecord event that is called when player is successfully loaded after joining. This also includes plugin reloading.
 */
public class TabPlayerLoadEvent extends Event {

	private TabPlayer player;
	
	public TabPlayerLoadEvent(TabPlayer player) {
		this.player = player;
	}
	
	public TabPlayer getPlayer() {
		return player;
	}
}