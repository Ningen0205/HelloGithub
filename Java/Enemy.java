import java.util.Random;
import java.awt.Image;
import java.awt.Toolkit;
class Enemy{
    //バトル関連
    EnumBattleStatus eStatus = EnumBattleStatus.Existence;

    private final String name;
    private final int maxHitPoint;
    private int hitPoint;
    private final int attack;
    private final int defense;
    private int turnDefense;// 防御した際の防御力
    private final int EXP;

    Random r;
    int select;
    Image enemyImage;
    int damage;

    Enemy() {
        enemyImage = Toolkit.getDefaultToolkit().getImage("Java/Images/enemy.png");
        name = "スライム";
        maxHitPoint = 10;
        hitPoint = 10;
        attack = 7;
        defense = 3;
        EXP = 100;
        turnDefense = defense;

        r = new Random();
    }

    void turn(Character character) {
        select = r.nextInt(2);
        if (select == 0) {
            attack(character);
        } else {
            defense();
        }
    }

    void attack(Character character) {
        damage = attack - character.getTurnDefanse();
        if (damage <= 0) {
            damage = 1;
        }
        character.underAttack(damage);
    }

    void defense() {
        turnDefense = (int) (defense * 1.5);
    }

    int getTurnDefanse() {
        return turnDefense;
    }

    void underAttack(int damage) {
        hitPoint -= damage;
        if(hitPoint <= 0){
            hitPoint = 0;
			eStatus = EnumBattleStatus.Died;
        }
    }
    
    void endOfTurn(){
        turnDefense = defense;
    }

    String getName(){
        return name;
    }

    int getDamage(){
        return damage;
    }

    int getSelect(){
        return select;
    }

    Image getImage(){
        return enemyImage;
    }

    int getEXP(){
        return EXP;
    }
}