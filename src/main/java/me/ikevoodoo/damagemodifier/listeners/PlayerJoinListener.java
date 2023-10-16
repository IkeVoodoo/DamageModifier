package me.ikevoodoo.damagemodifier.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {

    private final NamespacedKey adminSetupKey;

    public PlayerJoinListener(NamespacedKey adminSetupKey) {
        this.adminSetupKey = adminSetupKey;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer container = player.getPersistentDataContainer();

        if(container.has(this.adminSetupKey, PersistentDataType.INTEGER)) {
           return;
        }

        container.set(this.adminSetupKey, PersistentDataType.INTEGER, 0);
        player.sendMessage("§aThanks for using §f§lDamageModifier§a! Please head over to the config to edit the damage types. By default §f§lZombies§a will do 2.5 hearts more damage, you can also add other types of damage modifiers. For a full list please check the README on the GitHub Page: https://spigotmc.org");
    }
}
