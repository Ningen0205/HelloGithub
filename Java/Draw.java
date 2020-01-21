import java.awt.*;
import java.util.ArrayList;
public class Draw {
	//マップ関係
	public int[] mapLayer1;	//マップマスのID
	public int[] mapLayer2; //マップマスのID
	public int[] itemLayer;	//宝箱の種類
	int mapIndex;	//描画するマスの配列に対するインデックス
	int squareIdLayer1;	//レイヤ1の描画するときの対応ID
	int squareIdLayer2;//レイヤ2の描画するときの対応ID
	int drawItemLayer;	//描画する際の宝箱の種類
	int squareIdImageBitXLayer1;//マップチップの描画する場所(横)
	int squareIdImageBitXLayer2;//マップチップの描画する場所（横）
	int squareIdImageBitYLayer1;//マップチップの描画する場所（縦）
	int squareIdImageBitYLayer2;//マップチップの描画する場所（縦）

	final int WINDOW_WIDTH;
	final int WINDOW_HEIGHT;

	Font font;
	FontMetrics metrics;

	final int SQUARE_SIZE = 32;	//１マスのサイズ
	final int SQUARE_SUM = 15;	//縦横のマスのサイズ
	
	Character character;
	Enemy enemy;
	Image mapImage;
	Image charImage;
	Image itemBoxImage;
	Graphics g;


	
	Draw(Graphics gra){
		WINDOW_WIDTH = MyFrame1.WINDOW_WIDTH;
		WINDOW_HEIGHT = MyFrame1.WINDOW_HEIGHT;

		character = MyMain1.character;
//		enemy = MyMain1.enemy;
		charImage = Toolkit.getDefaultToolkit().getImage("Java/Images/pipo-xmaschara01.png");
		itemBoxImage = Toolkit.getDefaultToolkit().getImage("java/Images/itemBox.png");
		mapImage = Toolkit.getDefaultToolkit().getImage("Java/Images/WorldMap-A2.png");// マップのマップチップ
        mapLayer1 = Map.getLayer1();
		mapLayer2 = Map.getLayer2();
		itemLayer = Map.getItemLayer();
        
//		g = MyMain1.myFrame1.panel.image.getGraphics();	//パネル
		g = gra;
	}
	
	void drawLayer(){
		mapIndex = 0;
		//レイヤ1,2の描画
		for(int i=0; i<SQUARE_SUM; i++) {	
			for(int j=0; j<SQUARE_SUM; j++) {
				squareIdLayer1 = mapLayer1[mapIndex];
				squareIdLayer2 = mapLayer2[mapIndex];
				drawItemLayer = itemLayer[mapIndex];
				squareIdImageBitXLayer1 = (int)(squareIdLayer1%16)*32;//マップチップの描画する場所(横)
				squareIdImageBitXLayer2 = (int)(squareIdLayer2%16)*32;//マップチップの描画する場所（横）
				squareIdImageBitYLayer1 = (int)(squareIdLayer1/16)*32;//マップチップの描画する場所（縦）
				squareIdImageBitYLayer2 = (int)(squareIdLayer2/16)*32;//マップチップの描画する場所（縦）
				g.drawImage(mapImage,j*32,i*32,(j*32)+32,(i*32)+32,
						squareIdImageBitXLayer1,squareIdImageBitYLayer1,squareIdImageBitXLayer1+32,squareIdImageBitYLayer1+32,MyMain1.myFrame1);
				
				if(drawItemLayer != 0){
					drawItemLayer -= 1;//(一つずらす)
					g.drawImage(itemBoxImage,j*32,i*32,(j*32)+32,(i*32)+32,
								0,drawItemLayer*32,32,(drawItemLayer*32)+32,MyMain1.myFrame1);
				}
				if(squareIdLayer1 == squareIdLayer2) {	//レイヤ１と２が同じIDならばレイヤ2の描画を行わない
					mapIndex++;
					continue;
				}
				else {
				g.drawImage(mapImage,j*32,i*32,(j*32)+32,(i*32)+32,
					squareIdImageBitXLayer2,squareIdImageBitYLayer2,squareIdImageBitXLayer2+32,squareIdImageBitYLayer2+32,MyMain1.myFrame1);
					mapIndex++;
				}
				
			}
		}
	}
	
	void drawChar(final int positionX, final int positionY) {
		g.drawImage(charImage, character.getPositonX(),character.getPositionY(), character.getPositonX()+32,character.getPositionY()+32,
								32, 32*character.getDirection(), 64, 32*character.getDirection() + 32, MyMain1.myFrame1);
	}

	void drawMenu(final int selectedIndex){
		drawLayer();
		drawChar(character.getPositionX(),character.getPositionY());
		
		font = new Font("SansSerif", Font.PLAIN, 15);
		g.setColor(Color.BLACK);
		metrics = g.getFontMetrics(font);
		g.setFont(font);
		g.fillRect(0,0,160,170);//枠内描画
		g.setColor(Color.WHITE);
		g.drawRect(0,0,160,170);//外枠描画
		g.drawString("アイテム",(150/2) - (metrics.stringWidth("アイテム") /2),30);
		g.drawString("ステータス",(150/2) - (metrics.stringWidth("ステータス") /2),70);
		g.drawString("セーブ",(150/2) - (metrics.stringWidth("セーブ") /2),110);
		g.drawString("終了",(150/2) - (metrics.stringWidth("終了") /2),150);

		g.drawLine(20,(30+40*selectedIndex),130,(30+40*selectedIndex));	//選択している場所へ線の描画
	}

	void drawMenuItem(final int selectedIndex){
		drawLayer();
		drawChar(character.getPositionX(),character.getPositionY());
		font = new Font("SansSerif", Font.PLAIN, 15);
		g.setColor(Color.BLACK);
		g.fillRect(50,50,200,300);//枠内描画
		g.setColor(Color.WHITE);
		g.drawRect(50,50,200,300);//外枠描画

		final ArrayList<Integer> itemsOwnedList = character.getItemsOwnedList();
		final int itemPositionX = 100;
		int itemPositionY = 80; 
		if(itemsOwnedList != null){
			for (final Integer value : itemsOwnedList) {
				g.drawString(ItemID.ITEM_NAME[value],itemPositionX,itemPositionY);
				itemPositionY += 30;
			}
		}

		g.drawLine(100,(80+30*selectedIndex),180,(80+30*selectedIndex));	//選択している場所へ線の描画
	}

	void drawUseItem(){
		font = new Font("SansSerif", Font.PLAIN, 15);
		g.setColor(Color.BLACK);
		g.fillRect(200,200,80,50);//枠内描画
		g.setColor(Color.WHITE);
		g.drawRect(200,200,80,50);//外枠描画
		g.drawString("使う",240-(metrics.stringWidth("使う")/2),225);
	}

	void drawItemUsed(int itemId){
		font = new Font("SansSerif", Font.PLAIN, 25);
		g.setColor(Color.BLACK);
		metrics = g.getFontMetrics(font);
		g.setFont(font);
		g.fillRect(0, 300, 479, 179);
		g.setColor(Color.WHITE);
		g.drawRect(0, 300, 479, 179);
		g.drawString(ItemID.ITEM_NAME[itemId]+"を使用した！", (MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(ItemID.ITEM_NAME[itemId]+"を使用した!") / 2), 350);
		switch(itemId){
			case 1:
				g.drawString("HPが20回復した！", (MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth("HPが20回復した！") / 2), 400);
			break;
			case 2:
				g.drawString("最大HPが3増加した！", (MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth("最大HPが3増加した！") / 2), 400);
			break;
			case 3:
				g.drawString("攻撃力が2増加した！", (MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth("攻撃力が2増加した！") / 2), 400);
			break;
			case 4:
				g.drawString("防御力が2増加した！", (MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth("防御力が2増加した！") / 2), 400);
			break;
		}
	}

	void drawMenuStatus(Character c){
		drawLayer();
		drawChar(character.getPositionX(),character.getPositionY());
		font = new Font("SansSerif", Font.PLAIN, 15);
		g.setColor(Color.BLACK);
		g.fillRect(50,50,200,300);//枠内描画
		g.setColor(Color.WHITE);
		g.drawRect(50,50,200,300);//外枠描画
		g.drawString("レベル:          "+c.getLevel(),200 - metrics.stringWidth("レベル:          "+c.getLevel()),100);
		g.drawString("最大HP:          "+c.getMaxHitPoint(),200 - metrics.stringWidth("最大HP:          "+c.getMaxHitPoint()), 120);
		g.drawString("現在HP:          "+c.getHitPoint(),200 - metrics.stringWidth("現在HP:          "+c.getHitPoint()), 140);
		g.drawString("攻撃力:          "+c.getAttack(),200 - metrics.stringWidth("攻撃力:          "+c.getAttack()), 160);
		g.drawString("防御力:          "+c.getDefense(),200 - metrics.stringWidth("防御力:          "+c.getDefense()), 180);
		g.drawString("合計経験値:          "+c.getSumExp(),200 - metrics.stringWidth("合計経験値:          "+c.getSumExp()), 320);

	}

	void drawTextBox(final int itemId){
		font = new Font("SansSerif", Font.PLAIN, 25);
		g.setColor(Color.BLACK);
		metrics = g.getFontMetrics(font);
		g.setFont(font);
		g.fillRect(0, 300, 479, 179);
		g.setColor(Color.WHITE);
		g.drawRect(0, 300, 479, 179);
		g.drawString(ItemID.ITEM_NAME[itemId]+"をゲットした!", (MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(ItemID.ITEM_NAME[itemId]+"をゲットした!") / 2), 400);

	}

	void drawBattleBasic(){
		font = new Font("SansSerif", Font.PLAIN, 20);
		metrics = g.getFontMetrics(font);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		g.setColor(Color.WHITE);
		g.drawRect(0, 300, 479, 179);
		g.drawRect(0, 0, MyFrame1.STATUS_WIDTH, MyFrame1.STATUS_HEIGHT);
		g.drawString(character.getName(),(MyFrame1.STATUS_WIDTH / 2) - (metrics.stringWidth(character.getName()) / 2), 30); // 一人目 キャラ名
		g.drawString("HP:" + character.getHitpoint(),
				(MyFrame1.STATUS_WIDTH / 2) - (metrics.stringWidth("HP:" + character.getHitpoint()) / 2), 60); // HP
		
		if(enemy.eStatus == EnumBattleStatus.Existence){
			g.drawImage(enemy.getImage(), 100,100,246,365,0,0,146,265,MyMain1.myFrame1);
		}
	}
	void drawBattle(){
		g.drawString(enemy.getName()+"が現れた！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(enemy.getName()+"が現れた！") / 2), 400);
	}

	void drawBattleCommand(int selectedIndex){		
		font = new Font("SansSerif", Font.PLAIN, 20);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("攻撃",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth("攻撃")/2),360);
		g.drawString("防御",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth("防御")/2),420);
		//(MyFrame1.STATUS_WIDTH / 2) - (metrics.stringWidth("攻撃")/2)
		g.drawLine((MyFrame1.WINDOW_WIDTH/2)-(metrics.stringWidth("攻撃")),(360+60*selectedIndex),(MyFrame1.WINDOW_WIDTH/2)+(metrics.stringWidth("攻撃")),(360+60*selectedIndex));	//選択している場所へ線の描画
	}

	void drawBattleMessage1(int selectedIndex){
		font = new Font("SansSerif", Font.PLAIN, 30);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		if(selectedIndex == 0){
			g.drawString(character.getName()+"の攻撃!",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"の攻撃!")/2),390);
		}
		else{
	 		g.drawString(character.getName()+"は防御した!",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"は防御した!")/2),390);
		}
	}

	void drawBattleMessage2(int selectedIndex){
		font = new Font("SansSerif", Font.PLAIN, 30);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		if(selectedIndex == 0){
			g.drawString(enemy.getName()+"に"+character.getDamage()+"のダメージ！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(enemy.getName()+"に"+character.getDamage()+"のダメージ！")/2),390);
		}
		else{
	 		g.drawString(character.getName()+"は受けるダメージが減った！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"は受けるダメージが減った！")/2),390);
		}
	}

	void drawBattleMessage3(int selectedIndex){
		font = new Font("SansSerif", Font.PLAIN, 30);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		if(selectedIndex == 0){
			g.drawString(enemy.getName()+"の攻撃!",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(enemy.getName()+"の攻撃!")/2),390);
		}
		else{
	 		g.drawString(enemy.getName()+"は防御した!",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(enemy.getName()+"は防御した!")/2),390);
		}
	}

	void drawBattleMessage4(int selectedIndex){
		font = new Font("SansSerif", Font.PLAIN, 30);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		if(selectedIndex == 0){
			g.drawString(character.getName()+"に"+enemy.getDamage()+"のダメージ！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"に"+enemy.getDamage()+"のダメージ！")/2),390);
		}
		else{
	 		g.drawString(enemy.getName()+"は受けるダメージが減った！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(enemy.getName()+"は受けるダメージが減った！")/2),390);
		}
	}

	void drawBattleWin(){
		font = new Font("SansSerif", Font.PLAIN, 30);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(character.getName()+"は敵をたおした！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"は敵をたおした！")/2),390);

	}

	void drawBattleLose(){
		font = new Font("SansSerif", Font.PLAIN, 30);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(character.getName()+"は負けてしまった...",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"は負けてしまった...")/2),390);
		
	}

	void drawCharacterLevelUp(){
		font = new Font("SansSerif", Font.PLAIN, 30);
		metrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(character.getName()+"はレベルアップした！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"はレベルアップした！")/2),390);
		g.drawString(character.getName()+"は"+character.getLevel()+"レベルになった！",(MyFrame1.WINDOW_WIDTH / 2) - (metrics.stringWidth(character.getName()+"は"+character.getLevel()+"レベルになった！")/2),430);
	}

	void drawGameOver(){
		Font font = new Font("SansSerif", Font.PLAIN, 30);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics(font);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOW_WIDTH,WINDOW_HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString("GAME OVER", (WINDOW_WIDTH / 2) - (metrics.stringWidth("GAME OVER") / 2), 100);
		g.drawString("Press SPACE to Title",
				(WINDOW_WIDTH / 2) - (metrics.stringWidth("Press SPACE to Title") / 2), 200);
	}
	void getEnemy(Enemy enemy){
		this.enemy = enemy;
	}

	void reStart(Character c,Enemy e){
		character = c;
		enemy = e;
	}

}
