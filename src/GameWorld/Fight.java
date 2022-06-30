package GameWorld;

public class Fight implements Runnable{
    Game game;
    Monster monster;

    Fight(Game game) {
        this.game = game;
    }

    public void run() {
        monster = Monster.createMonster(game);
        //
    }

}
