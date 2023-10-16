package me.ikevoodoo.damagemodifier.modifiers;

public enum DamageModifierMode {

    MULTIPLY {
        @Override
        public double scale(double value, double scaling) {
            return value * scaling;
        }
    },
    ADD {
        @Override
        public double scale(double value, double scaling) {
            return value + scaling;
        }
    },
    POWER {
        @Override
        public double scale(double value, double scaling) {
            return Math.pow(value, scaling);
        }
    };

    public abstract double scale(double value, double scaling);

}
