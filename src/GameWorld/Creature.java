package GameWorld;

import java.lang.*;
import java.util.Scanner;

public abstract class Creature {
    protected int hitPoints;
    protected int strength;
    protected int agility;
    private final String name;
    protected Game game;

    Creature(int hitPoints, int strength,
                    int agility, String name, Game game) {
        this.hitPoints = hitPoints;
        this.strength = strength;
        this.agility = agility;
        this.name = name;
        this.game = game;
    }

    public final boolean isAlive() {
        return hitPoints > 0;
    }

    public final boolean isDead() {
        return hitPoints <= 0;
    }

    public final int getHitPoints() {
        return hitPoints;
    }

    public final int getStrength() {
        return strength;
    }

    public final int getAgility() {
        return agility;
    }

    public final String getName() {
        return name;
    }

    protected final int hit(Creature creature) {
        if (creature == null) {
            throw new IllegalArgumentException();
        }
        return (3 * agility > 100 * Math.random()) ?
                creature.hitPoints -= strength : 0;
    }
}
