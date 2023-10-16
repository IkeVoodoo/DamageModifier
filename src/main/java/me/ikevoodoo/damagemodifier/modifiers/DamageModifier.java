package me.ikevoodoo.damagemodifier.modifiers;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageModifier {

    private final DamageModifierMode mode;
    private final EntityDamageEvent.DamageCause cause;
    private final EntityType sourceEntityType;
    private final double value;

    public DamageModifier(DamageModifierMode mode, EntityDamageEvent.DamageCause cause, EntityType sourceEntityType, double value) {
        this.mode = mode;
        this.cause = cause;
        this.sourceEntityType = sourceEntityType;
        this.value = value;
    }

    public DamageModifierMode getMode() {
        return mode;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    public EntityType getSourceEntityType() {
        return sourceEntityType;
    }

    public double getValue() {
        return value;
    }
}
