package listeners;

import manager.RozdajManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final RozdajManager rozdajManager;

    public PlayerJoinListener(RozdajManager rozdajManager) {
        this.rozdajManager = rozdajManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (rozdajManager.isRozdajActive()) {
            rozdajManager.getBossBar().addPlayer(event.getPlayer());
        }
    }
}
