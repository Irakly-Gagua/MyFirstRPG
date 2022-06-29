package GameWorld;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Hero extends Creature {
    protected int level = 0;
    protected int exp = 0;

    Hero(int hitPoints, int strength,
                int agility, String name, Game game) {
        super(hitPoints, strength, agility, name, game);
    }

    public final int getLevel() {
        return level;
    }

    final void levelUp() {
        level++;
        game.message("Поздравляем! Вы достигли уровня " +
                level + ".");
        game.message("Распределите 6 очков между силой и ловкостью.");
        game.message("Введите через пробел два целых " +
                "неотрицательных числа с суммой 6:");

        try (Scanner scanner = new Scanner(System.in)) {
            int upStrength = scanner.nextInt();
            int upAgility = scanner.nextInt();
            if (upAgility + upStrength == 6 &&
                    upAgility >= 0 && upStrength >= 0) {
                strengthUp(upStrength);
                agilityUp(upAgility);
            } else throw new IllegalArgumentException();
        } catch (InputMismatchException | IllegalArgumentException e) {
            game.message("Мимо пролетела ворона и обкакала вас.");
            game.message("Поймать ворону не удалось.");
            strengthUp(2);
            agilityUp(2);
        }
    }

    private void strengthUp(int upStrength) {
        strength += upStrength;
        game.message("Сила повышена на " + upStrength + ".");
    }

    private void agilityUp(int upAgility) {
        strength += upAgility;
        game.message("Ловкость повышена на " + upAgility + ".");
    }
}
