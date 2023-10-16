# Damage Modifier
This plugin allows you to modify every source of damage, for every combination of attacked entity and attacker entity.

The config uses multiple types to determine when to apply damage modification, here is a list to make it easier to work with:

### The "cause" option
This option is referring to the [Entity Damage Cause](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html) enum.
You can use any of the options that are all-uppercase. For example, you can use `BLOCK_EXPLOSION`, `CONTACT` etc.

Each cause has a description specifying when this cause is triggered.


### The "entity" and "source" options
This option is referring to the [Entity Type Enum](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html).
You can use any of the options that are all-uppercase. For example, you can use `ALLAY`, `CHEST_BOAT` etc.

Each entity has a description in case that the name is not clear; most names reflect in-game names.

### The "mode" option
This option can be one of the following:

- `ADD` Adds the value to the damage.
- `MULTIPLY` Multiplies the damage by the value.
- `POWER` Elevates the damage to the value.


### The "value" option
This option specifies the actual scaling factor.

In `ADD` mode, this means that `1` is half a heart, and `2` is one heart.

Negative values are supported to decrease damage.