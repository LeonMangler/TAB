package me.neznamy.tab.platforms.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import me.neznamy.tab.api.PlaceholderManager;
import me.neznamy.tab.api.placeholder.ServerPlaceholder;
import me.neznamy.tab.shared.proxy.ProxyPlaceholderRegistry;

/**
 * Velocity registry to register velocity-only placeholders
 */
public class VelocityPlaceholderRegistry extends ProxyPlaceholderRegistry {

	//instance of proxyserver
	private ProxyServer server;
	
	/**
	 * Constructs new instance with given parameter
	 * @param server - instance of proxyserver
	 */
	public VelocityPlaceholderRegistry(ProxyServer server) {
		this.server = server;
	}
	
	@Override
	public void registerPlaceholders(PlaceholderManager manager) {
		super.registerPlaceholders(manager);
		for (RegisteredServer rServer : server.getAllServers()) {
			manager.registerServerPlaceholder(new ServerPlaceholder("%online_" + rServer.getServerInfo().getName() + "%", 1000) {
				public Object get() {
					return rServer.getPlayersConnected().size();
				}
			});
		}
	}
}