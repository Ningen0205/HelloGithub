import java.util.ArrayList;
public class Character {
	
	private int positionX;	//キャラのX座標の位置
	private int positionY;	//キャラのY座標の位置
	private int direction;	//キャラの向き(下:0 左:1 右:2 上:3)
	
	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;

	//バトル関連
	private String name;
	private int maxHitPoint;
	EnumBattleStatus eStatus;
	private int level;
	private int hitPoint;
	private int attack;
	private int defense;
	private int turnDefense;
	private int Exp;
	private int sumExp;
	private int nextLevelupExp;

	int damage;
	
	
	private static ArrayList<Integer> itemsOwnedList = new ArrayList<Integer>();	//所持しているアイテムのアイテムIDを管理

	Character(){
		eStatus = EnumBattleStatus.Existence;
		name = "勇者";
		positionX = 64;
		positionY = 64;

		level = 1;
		maxHitPoint = 40;
		hitPoint = 40;
		attack = 10;
		defense = 2;
		turnDefense = defense;

		Exp = 0;
		sumExp = 0;
		nextLevelupExp = 2;

		damage = 0;
	}

	void turn(int select,Enemy enemy){
		if(select == 0){
			attack(enemy);
		}
		else{
			defense();
		}
	}

	void attack(Enemy enemy){
		damage = attack - enemy.getTurnDefanse();
		if(damage <= 0 ){
			damage = 1;
		}
        enemy.underAttack(damage);
	}

	void defense(){
        turnDefense = (int)(defense * 1.5);
    }

	void levelup(){
		level += 1;
		maxHitPoint += 10;
		hitPoint = maxHitPoint;
		attack += 3;
		defense += 2;

		Exp -= nextLevelupExp;
		nextLevelupExp += 3;
		if(Exp >= nextLevelupExp){
			levelup();
		}
	}



    void underAttack(int damage){
		hitPoint -= damage;
		if(hitPoint <= 0){
			hitPoint = 0;
			eStatus = EnumBattleStatus.Died;
		}
	}

	void endOfTurn(){
        turnDefense = defense;
    }
	
	int getTurnDefanse(){
		return turnDefense;
	}

	int getDamage(){
		return damage;
	}



//-------------------------------------------------------------------------//
	ArrayList<Integer> getItemsOwnedList(){
		return itemsOwnedList;
	} 
	
	void additems(int itemID){
		itemsOwnedList.add(itemID);
	}

	int getDirection() {
		return direction;
	}
	
	void setDirection(int direction) {
		this.direction = direction;
	}
	
	int getPositonX(){
		return positionX;
	}
	
	int getPositonY(){
		return positionY;
	}
	
	void setPositionX(int position){
		if(position <= MyFrame1.WINDOW_WIDTH -32 && position >= 0) {
			positionX = position;
		}
	}
	
	int getPositionX(){
		return positionX;
	}
	
	void setPositionY(int position){
		if(position <= MyFrame1.WINDOW_HEIGHT -64 && position >= 0) {
			positionY = position;
		}
	}
	
	int getPositionY(){
		return positionY;
	}

	int getHitpoint(){
		return hitPoint;
	}

	String getName(){
		return name;
	}

}
