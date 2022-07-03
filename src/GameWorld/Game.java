package GameWorld;

import java.io.IOException;
import java.util.InputMismatchException;
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

            System.out.println("\n\nХотите начать игру сначала? (y/n)");
            scanner = new Scanner(System.in);
        } while (scanner.nextLine().toLowerCase().startsWith("y"));
    }

    private void createHero() {
        message("\nЗдравствуйте, путник! Как вас зовут?");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().trim();
        while (name.length() > 20 || name.length() < 1) {
            message("В этом мире не бывает таких странных имён!");
            message("Путник, введите имя длиной от 1 до 20 символов.");
            name = scanner.nextLine().trim();
        }
        scanner.close();
        hero = new Hero(100, 12, 10, name, this);
        message(name + ", вы наш новый герой!");
        message("У вас 100 очков здоровья, сила 12, ловкость 10.");
    }

    void message(Object message, boolean clear) {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (clear) {
            try {
                System.in.skip(System.in.available());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(message);
    }

    void message(Object message) {
        message(message, false);
    }

    private void inMainTown() {
        while (!endOfGame) {
            message(hero.getName() + ", вы в Главном Городе.");
            message("""
                    Куда направитесь? Введите цифру 1, 2 или 3.
                    1) В тёмный лес (бой с монстром).
                    2) К торговцу (купить зелья здоровья).
                    3) Покинуть игру.""", true);
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            while (Command(command) == 0) {
                message(hero.getName() + ", вас не слышно. Повторите " +
                        "ввод цифры и нажмите Enter.", true);
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
        message("\"" + hero.getName() + ", приветствую тебя " +
                "в моём магазине зелий!\"\n" +
                "\"Я - Знахарь. Каждое моё зелье восстановит тебе " +
                "15 единиц здоровья\".\n" +
                "\"Зелье стоит 4 золотых. Но я наслышан о твоих подвигах.\"\n" +
                "\"Поэтому при покупке сразу 10 зелий " +
                "я дам тебе одно в подарок!\"");
        message("\nЧисло ваших золотых монет равно: " + hero.gold +
                ". Сколько " +
                "зелий вы купите? Введите 0, " +
                "чтобы уйти без покупок.", true);
        int count;
        try {
            count = new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            message("\"Что-что? Да ты пьян, дружище!\"\n" +
                    "\"Иди проспись, а потом приходи ко мне\".");
            message("Стража выволокла вас из магазина Знахаря.");
            return;
        }
        if (4 * count > hero.gold) {
            message("\"Столько зелей тебе пока не по карману\".\n" +
                    "\"Подзаработай деньжат и тогда возвращайся ко мне\".");
            return;
        }
        int gift = count / 10;
        hero.potions += count + gift;
        hero.gold -= 4 * count;
        message("Знахарь протягивает вам зелья в количестве: " +
                (count + gift) + ".");
        message("Теперь число ваших зелей равно: " + hero.potions + ".");
    }

    private void fight() {
        Fight fight = new Fight(this);
        Thread fighting = new Thread(fight);
        message("В лесу на вас нападает " + fight.monster.getName() + "!");
        message("Придётся сражаться! Кстати, вы запаслись лечебными зельями?");
        message("Чтобы выпить зелье, в любой момент боя " +
                "нажмите Enter.");
        message("Готовы? (нажмите Enter)", true);
        new Scanner(System.in).nextLine();
        fighting.start();
        do {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (System.in.available() > 0) {
                    new Scanner(System.in).nextLine();
                    synchronized (hero.lock) {
                        if (hero.potions <= 0) {
                            message("У вас нет зелий!", true);
                        } else if (hero.isAlive()) {
                            hero.potions--;
                            hero.hitPoints += 15;
                            message("Вы выпили зелье: " +
                                    "+15 к здоровью. " +
                                    "Зелий осталось: " +
                                    hero.potions + ".");
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (fighting.isAlive());
        if (hero.isDead()) {
            heroIsDead();
        } else {
            message(hero.getName() + ", вы одолели монстра " +
                    fight.monster.getName() + "!");
            int newGold = (int) (15 * Math.random() + 15);
            hero.gold += newGold;
            message("В норе повершенного врага вы нашли " +
                    newGold + " золотых монет." +
                    "\nТеперь у вас " + hero.gold + " золота.");
            hero.exp += fight.hits;
            message("Ваш опыт возрос на число ударов по " +
                    "монстру, включая промахи: " +
                    hero.exp + "/100 (+" + fight.hits + ").");
            while (hero.exp >= 100) {
                hero.exp -= 100;
                hero.levelUp();
            }
        }
    }

    private void heroIsDead() {
        message("Злой монстр убил вас :(");
        message("Ваши похороны попали в Книгу Рекордов Гиннесса!!");
        message("(по количеству порванных баянов)");
        end();
    }

    private void end() {
        message(hero.getName() + ", вы достигли уровня " + hero.getLevel());
        message("До новых встреч!", true);
        endOfGame = true;
    }
}
