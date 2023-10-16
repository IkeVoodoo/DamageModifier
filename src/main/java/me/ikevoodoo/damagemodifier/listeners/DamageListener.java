package me.ikevoodoo.damagemodifier.listeners;

import me.ikevoodoo.damagemodifier.modifiers.DamageModifierRegistry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public final class DamageListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if(event instanceof EntityDamageByEntityEvent) {
            return;
        }

        Entity attacked = event.getEntity();
        EntityType type = attacked.getType();

        EntityDamageEvent.DamageCause cause = event.getCause();
        double damage = event.getDamage();

        event.setDamage(DamageModifierRegistry.applyModifiers(type, null, cause, damage));
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity attacked = event.getEntity();
        EntityType type = attacked.getType();

        Entity attacker = event.getDamager();
        EntityType attackerType = attacker.getType();

        EntityDamageEvent.DamageCause cause = event.getCause();
        double damage = event.getDamage();

        event.setDamage(DamageModifierRegistry.applyModifiers(type, attackerType, cause, damage));
    }
}
