package me.ikevoodoo.damagemodifier.commands;

import me.ikevoodoo.damagemodifier.modifiers.DamageModifierRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ReloadCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        long start = System.nanoTime();
        this.plugin.reloadConfig();
        long end = System.nanoTime();

        sender.sendMessage(String.format("§6Reloaded %,d modifier(s) in %.2fms!", DamageModifierRegistry.size(), (end - start) / 1_000_000D));
        return true;
    }
}
