package github.rmagur1203.deathinventory;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class DeathListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER)
            return;
        String uuid = UUID.randomUUID().toString();
        Player player = (Player) event.getEntity();
        player.chat(player.getName() + "'s death information has been saved to uuid " + uuid);
        new PlayerDeathData(
                player.getName(),
                event.getDroppedExp(),
                event.getDrops(),
                player.getLastDeathLocation()
        ).saveData(uuid);
    }
}
