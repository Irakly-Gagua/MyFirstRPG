package GameWorld;

public class Monster extends Creature {
    Monster(int hitPoints, int strength, int agility, String name, Game game) {
        super(hitPoints, strength, agility, name, game);
    }

    static Monster createMonster(Game game) {
        return switch ((int) (2 * Math.random())) {
            case 0 -> createGoblin(game);
            case 1 -> createSkeleton(game);
            default -> throw new RuntimeException("Something is wrong");
        };
    }

    private static Monster createGoblin(Game game) {
        return new Monster(100, 12,
                8, "Goblin", game);
    }

    private static Monster createSkeleton(Game game) {
        return new Monster(100, 10,
                10, "Skeleton", game);
    }
}
