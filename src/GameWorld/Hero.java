package GameWorld;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Hero extends Creature {
    protected int level = 0;
    protected int exp = 0;
    protected int potions = 0;

    Hero(int hitPoints, int strength,
                int agility, String name, Game game) {
        super(hitPoints, strength, agility, name, game);
    }

    public final int getLevel() {
        return level;
    }

    final void levelUp() {
        level++;
        Game.message("Поздравляем! Вы достигли уровня " +
                level + ".");
        Game.message("Распределите 6 очков между силой и ловкостью.");
        Game.message("Введите через пробел два целых " +
                "неотрицательных числа с суммой 6:");

        Scanner scanner = new Scanner(System.in);
        try {
            int upStrength = scanner.nextInt();
            int upAgility = scanner.nextInt();
            if (upAgility + upStrength == 6 &&
                    upAgility >= 0 && upStrength >= 0) {
                strengthUp(upStrength);
                agilityUp(upAgility);
            } else throw new IllegalArgumentException();
        } catch (InputMismatchException | IllegalArgumentException e) {
            Game.message("Мимо пролетела ворона и обкакала вас.");
            Game.message("Поймать ворону не удалось.");
            strengthUp(2);
            agilityUp(2);
        }
    }

    private void strengthUp(int upStrength) {
        strength += upStrength;
        Game.message("Сила повышена на " + upStrength + ".");
    }

    private void agilityUp(int upAgility) {
        strength += upAgility;
        Game.message("Ловкость повышена на " + upAgility + ".");
    }
}
