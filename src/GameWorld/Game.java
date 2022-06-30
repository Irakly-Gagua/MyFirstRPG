package GameWorld;

import java.util.Scanner;

public class Game {
    Hero hero;
    boolean endOfGame = false;

    private Game() {
        createHero();
    }

    public static void start() {
        Scanner scanner;
        do {
            new Game().inMainTown();
            message("\n\nХотите начать игру сначала? (y/n)");
            scanner = new Scanner(System.in);
        } while (scanner.nextLine().toLowerCase().startsWith("y"));
    }

    private void createHero() {
        message("\nЗдравствуйте, путник! Как вас зовут?");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        while (name.length() > 20) {
            message("В этом мире не бывает таких длинных имён!");
            message("Путник, введите имя не длинее 20 символов.");
            name = scanner.nextLine();
        }
        scanner.close();
        hero = new Hero(100, 12, 10, name, this);
        message(name + ", вы наш новый герой!");
    }

    static void message(Object message) {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }

    private void inMainTown () {
        while (!endOfGame) {
            message(hero.getName() + ", вы в Главном Городе.");
            message("Куда направитесь? Введите цифру 1, 2 или 3." +
                    "\n1) В тёмный лес (бой с монстром)." +
                    "\n2) К торговцу (купить зелья здоровья)." +
                    "\n3) Покинуть игру.");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            while (Command(command) == 0) {
                message(hero.getName() + ", вас не слышно. Повторите " +
                        "ввод цифры и нажмите Enter.");
                command = scanner.nextLine();
            }

            switch (Command(command)) {
                case 1 -> fight();
                case 2 -> trade();
                case 3 -> end();
            }
        }
    }

    private int Command(String s) {
        if (s == null || s.length() < 1) return 0;
        return switch (s.charAt(0)) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            default -> 0;
        };
    }

    private void trade() {
        //
        inMainTown();
    }

    private void fight() {
        Fight fight = new Fight(this);
        Thread fighting = new Thread(fight);
        message("В лесу на вас нападает " + fight.monster.getName() + "!");
        message("Придётся сражаться! Кстати, вы запаслись лечебными зельями?");
        message("Чтобы выпить зелье, введите h и нажмите Enter.");
        fighting.start();
        while (fighting.isAlive()) {
            //
        }
        if (hero.isDead()) heroIsDead();
    }

    private void heroIsDead() {
        message("Злой монстр убил вас :(");
        message("Ваши похороны попали в Книгу Рекордов Гиннесса!!");
        message("(по количеству порванных баянов)");
        end();
    }

    private void end() {
        message(hero.getName() + ", вы достигли уровня " + hero.getLevel());
        message("До новых встреч!");
        endOfGame = true;
    }
}
