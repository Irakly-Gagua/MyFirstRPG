package GameWorld;

public class Monster extends Creature {
    Monster(int hitPoints, int strength, int agility, String name, Game game) {
        super(hitPoints, strength, agility, name, game);
    }

    static Monster createMonster(Game game, int level) {
        return switch ((int) (2 * Math.random())) {
            case 0 -> createGoblin(game, level);
            case 1 -> createSkeleton(game, level);
            default -> throw new RuntimeException("Something is wrong");
        };
    }

    private static Monster createGoblin(Game game, int level) {
        return new Monster(100 + level, 12 + level,
                8 + level, "Goblin_" + level, game);
    }

    private static Monster createSkeleton(Game game, int level) {
        return new Monster(100 + level, 10 + level,
                10 + level, "Skeleton_" + level, game);
    }
}
