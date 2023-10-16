package me.ikevoodoo.damagemodifier.modifiers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class DamageModifierRegistry {

    // Performance reasons
    private static final String MODE_NAMES = String.join(", ", Arrays.stream(DamageModifierMode.values()).map(Enum::name).toArray(String[]::new));
    private static final Multimap<EntityType, DamageModifier> MODIFIERS = HashMultimap.create();
    private static final List<DamageModifier> GENERIC_MODIFIERS = new LinkedList<>();

    private DamageModifierRegistry() {

    }

    public static int size() {
        return MODIFIERS.size() + GENERIC_MODIFIERS.size();
    }

    public static void clear() {
        MODIFIERS.clear();
        GENERIC_MODIFIERS.clear();
    }

    public static Map<String, IllegalArgumentException> addAllFromConfig(ConfigurationSection section) {
        Map<String, IllegalArgumentException> errors = new HashMap<>();
        for (String key : section.getKeys(false)) {
            ConfigurationSection child = section.getConfigurationSection(key);
            if (child == null) continue;

            try {
                addFromConfig(child);
            } catch (IllegalArgumentException exception) {
                errors.put(key, exception);
            }
        }

        return errors;
    }

    public static void addFromConfig(ConfigurationSection section) {
        EntityType type = loadEntityType(section, "entity", true);
        EntityType source = loadEntityType(section, "source", true);

        String modeName = section.getString("mode");
        if (modeName == null) {
            throw new IllegalArgumentException("Please provide the option \"mode\", allowed values are: " + MODE_NAMES);
        }

        DamageModifierMode mode;
        try {
            mode = DamageModifierMode.valueOf(modeName.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid modifier mode! Please use one of the following: " + MODE_NAMES);
        }

        String causeName = section.getString("cause");
        if (causeName == null) {
            throw new IllegalArgumentException("Please provide the option \"cause\"!");
        }

        EntityDamageEvent.DamageCause cause;
        try {
            cause = EntityDamageEvent.DamageCause.valueOf(causeName.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid damage cause!");
        }

        if (!section.contains("value")) {
            throw new IllegalArgumentException("Please provide the option \"value\"! (It can be any complex number.)");
        }

        double value = section.getDouble("value");

        addModifier(type, new DamageModifier(mode, cause, source, value));
    }

    public static void addModifier(EntityType type, DamageModifier modifier) {
        if (type == null) {
            GENERIC_MODIFIERS.add(modifier);
            return;
        }

        MODIFIERS.put(type, modifier);
    }

    public static double applyModifiers(EntityType type, EntityType source, EntityDamageEvent.DamageCause cause, double value) {
        Collection<DamageModifier> modifiers = MODIFIERS.get(type);
        double out = value;

        if (modifiers != null) {
            for (DamageModifier modifier : modifiers) {
                boolean matchesCause = modifier.getCause() == cause;
                boolean matchesSource = source == null || modifier.getSourceEntityType() == source;

                if (!matchesCause || !matchesSource) continue;

                out = modifier.getMode().scale(out, modifier.getValue());
            }
        }

        for (DamageModifier modifier : GENERIC_MODIFIERS) {
            out = modifier.getMode().scale(out, modifier.getValue());
        }

        return out;
    }

    private static EntityType loadEntityType(ConfigurationSection section, String option, boolean nullable) {
        String entityName = section.getString(option);
        if (entityName == null) {
            if (!nullable) {
                throw new IllegalStateException("Please provide the option \"" + option + "\"!");
            }

            return null;
        }

        try {
            return EntityType.valueOf(entityName.toLowerCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException("Unknown entity type: \"" + entityName + "\"!");
        }
    }

}
