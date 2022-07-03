package GameWorld;

public class Fight implements Runnable{
    Game game;
    Monster monster;
    int hits = 0; //число ударов по монстру

    Fight(Game game) {
        this.game = game;
        monster = Monster.createMonster(game, game.hero.level);
    }

    public void run() {
        while (monster.isAlive()) {
            synchronized (game.hero.lock) {
                monsterHits();
                if (game.hero.isDead()) break;
            }
            heroHits();
        }
    }

    private void monsterHits() {
        if (monster.hit(game.hero)) {
            game.message(monster.getName() + " ударил вас. " +
                    "Ваше зодровье: " + game.hero.getHitPoints() + ".");
        } else {
            game.message(monster.getName() + " промахнулся.");
        }
    }

    private void heroHits() {
        hits++;
        if (game.hero.hit(monster)) {
            game.message("Вы ударили монстра. " +
                    "Его здоровье: " + monster.getHitPoints() + ".");
        } else {
            game.message("Вы промахнулись.");
        }
    }
}
