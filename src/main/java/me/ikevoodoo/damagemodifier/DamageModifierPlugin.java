package me.ikevoodoo.damagemodifier;

import me.ikevoodoo.damagemodifier.commands.ReloadCommand;
import me.ikevoodoo.damagemodifier.listeners.DamageListener;
import me.ikevoodoo.damagemodifier.listeners.PlayerJoinListener;
import me.ikevoodoo.damagemodifier.modifiers.DamageModifierRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.logging.Level;

public final class DamageModifierPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        getCommand("reload-modifiers").setExecutor(new ReloadCommand(this));

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(new NamespacedKey(this, "admin_setup_key")), this);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        DamageModifierRegistry.clear();

        ConfigurationSection section = getConfig().getConfigurationSection("modifiers");

        if (section == null) {
            getLogger().log(Level.SEVERE, "The config contains no modifier key! Unable to load modifiers.");
            return;
        }

        Map<String, IllegalArgumentException> errors = DamageModifierRegistry.addAllFromConfig(section);

        for (Map.Entry<String, IllegalArgumentException> entry : errors.entrySet()) {
            getLogger().log(Level.SEVERE, "Unable to load modifier '{0}' due to the following error: {1}", new Object[] {
                    entry.getKey(),
                    entry.getValue().getMessage()
            });
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
