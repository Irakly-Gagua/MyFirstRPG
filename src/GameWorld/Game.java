package GameWorld;

import java.util.Scanner;

public class Game {
    Hero hero;

    private Game() {
        createHero();
    }

    public static void start() {
        new Game().inMainTown();
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

    void message(Object message) {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }

    private void inMainTown () {
        message(hero.getName() + ", вы в главном городе");
        hero.levelUp();
        hero.levelUp();
        message("Вы отравились вином из одуванчиков.");
        heroIsDead();
    }

    private void fight() {
        //
    }

    private void heroIsDead() {
        message("Злой монстр убил вас :(");
        message("Ваши похороны попали в Книгу Рекордов Гиннесса!!");
        message("(по числу порванных баянов)");
        end();
    }

    private void end() {
        message("\nХотите начать игру сначала? (y/n)");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().toLowerCase().startsWith("y"))
            Game.start();
    }
}
