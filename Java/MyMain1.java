

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
//import javax.swing.JTextField;
import java.util.Random;



/*import io.github.kusaanko.Shooting.EnumShootingScreen;
import io.github.kusaanko.Shooting.Keyboard;*/

public class MyMain1 /*implements Runnable*/{
	//基礎描画関連
	public static MyFrame1 myFrame1;
	Graphics g;

	public static boolean loop;
	Status status;
	
	public static Image mapImage;		//マップチップ
	public static Image itemBoxImage;
	public static Image charImage;		//キャラチップ

	//ウィンドウサイズ
	final int WINDOW_WIDTH;	//windowサイズ
	final int WINDOW_HEIGHT;//windowサイズ
	
	final int SQUARE_SIZE = 32;	//１マスのサイズ
	final int SQUARE_SUM = 15;	//縦横のマスのサイズ
	
	//マップ関係
	Draw draw;
	public int[] mapLayer1;	//マップマスのID
	public int[] mapLayer2; //マップマスのID
	int mapIndex;	//描画するマスの配列に対するインデックス
	int squareIdLayer1;	//レイヤ1の描画するときの対応ID
	int squareIdLayer2;//レイヤ2の描画するときの対応ID
	int squareIdImageBitXLayer1;//マップチップの描画する場所(横)
	int squareIdImageBitXLayer2;//マップチップの描画する場所（横）
	int squareIdImageBitYLayer1;//マップチップの描画する場所（縦）
	int squareIdImageBitYLayer2;//マップチップの描画する場所（縦）
	
    //FPS
    long startTime;
    long fpsTime = 0;
    int fps = 30;
    int FPS = 0;
	int FPSCount = 0;
	
	int selectedIndex = 0;

	//エンカウント関連
	Random random;
	int encounter;
    
    //Characterの位置とか向き(ステータスとかも)
	static Character character;
	Enemy enemy;
	int nextItemId;	//あいてむのiD

	
	public MyMain1(){
		
//		boolean test = true;

		random = new Random();
		encounter = 0;

		character = new Character();
		enemy = new Enemy();
		myFrame1 = new MyFrame1();
		WINDOW_WIDTH = MyFrame1.WINDOW_WIDTH;
		WINDOW_HEIGHT = MyFrame1.WINDOW_HEIGHT;
		charImage = Toolkit.getDefaultToolkit().getImage("Java/Images/pipo-xmaschara01.png");
		itemBoxImage = Toolkit.getDefaultToolkit().getImage("java/Images/itemBox.png");
		mapImage = Toolkit.getDefaultToolkit().getImage("Java/Images/WorldMap-A2.png");// マップのマップチップ


		g = myFrame1.panel.image.getGraphics(); // パネル

		draw = new Draw(g);
//		g = myFrame1.comp.getGraphics(); //パネル
		status = Status.START;
		loop = true;

		// マップID取得
		mapLayer1 = Map.getLayer1();
		mapLayer2 = Map.getLayer2();

	}

	public static void main(final String[] args) {
		MyMain1 mainObject = new MyMain1();
		mainObject.Main();
	}

	void Main(){
		while (loop) {
			if ((System.currentTimeMillis() - fpsTime) >= 1000) {
				fpsTime = System.currentTimeMillis();
				FPS = FPSCount;
				FPSCount = 0;
			}

			FPSCount++;
			startTime = System.currentTimeMillis();

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			switch (status) {
			case START: // スタイル サイズ
				Font font = new Font("SansSerif", Font.PLAIN, 30);
				g.setFont(font);
				// フォントの位置に必要なピクセル数などを教えてくれる
				FontMetrics metrics = g.getFontMetrics(font);
				g.setColor(Color.BLACK);
				// 横の描画に必要なピクセル数を教えてくれる
				g.drawString("Hello RPG!", (WINDOW_WIDTH / 2) - (metrics.stringWidth("Hello RPG!") / 2), 100);
				g.drawString("Press SPACE to Start",
						(WINDOW_WIDTH / 2) - (metrics.stringWidth("Press SPACE to Start") / 2), 200);
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_SPACE)) {
					// status = Status.PLAYER_NAME_DECISION;
					status = Status.GAME;
				}
				break;
			case PLAYER_NAME_DECISION:
				new PlayerNameFrame();
				break;
			case GAME:
				draw.drawLayer();
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)) {
					if(Traffic.getNext(character.getDirection(),character.getPositionX(), character.getPositionY()) < 0 ||
						Traffic.getNext(character.getDirection(),character.getPositionX(), character.getPositionY()) > 224){
							//処理なし
						}
					else{
						nextItemId = Map.ITEMID_LAYER[Traffic.getNext(character.getDirection(),
						character.getPositionX(), character.getPositionY())];
						if (nextItemId != 0) {
							character.additems(nextItemId);
							Map.EmptyTreasureBox(Traffic.getNext(character.getDirection(),character.getPositionX(), character.getPositionY()));
							status = Status.GAME_ITEMGET;
						}
					}

//					System.out.println(nextItemId);


				}
				// SHIFTでメニュー表示
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_SHIFT)) {
					selectedIndex = 0; // 一番上にカーソルを合わせる
					status = Status.MENU;
				}

				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_LEFT) || MyKeyboard1.isKeyPressed(KeyEvent.VK_RIGHT) || 
						MyKeyboard1.isKeyPressed(KeyEvent.VK_DOWN) || MyKeyboard1.isKeyPressed(KeyEvent.VK_UP)){
						encounter = random.nextInt(100);
						if(encounter > 90){
							selectedIndex = 0;
							enemy = new Enemy();
							draw.getEnemy(enemy);
							status = Status.BATTLE;
						}
				}

				// どのキーを押したかのチェック
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_LEFT)) {
					if (character.getDirection() == Character.LEFT) {
						if (Traffic.canTraffic(character.getDirection(), character.getPositionX(),
								character.getPositionY())) {
							character.setPositionX(character.getPositionX() - 32);
						}
					} else {
						character.setDirection(Character.LEFT);
					}
				}
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_RIGHT)) {
					if (character.getDirection() == Character.RIGHT) {
						if (Traffic.canTraffic(character.getDirection(), character.getPositionX(),character.getPositionY())) {
							character.setPositionX(character.getPositionX() + 32);
						}

					} else {
						character.setDirection(Character.RIGHT);
					}
				}
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_DOWN)) {
					if (character.getDirection() == Character.DOWN) {
						if (Traffic.canTraffic(character.getDirection(), character.getPositionX(),
								character.getPositionY())) {
							character.setPositionY(character.getPositionY() + 32);
						}

					} else {
						character.setDirection(Character.DOWN);
					}
				}
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_UP)) {
					if (character.getDirection() == Character.UP) {
						if (Traffic.canTraffic(character.getDirection(), character.getPositionX(),
								character.getPositionY())) {
							character.setPositionY(character.getPositionY() - 32);
						}

					} else {
						character.setDirection(Character.UP);
					}
				}
				draw.drawChar(character.getPositionX(), character.getPositionY());
				break;
			case GAME_ITEMGET:
				draw.drawLayer();
				draw.drawChar(character.getPositionX(), character.getPositionY());
				draw.drawTextBox(nextItemId);
				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					status = Status.GAME;
				}
				break;
			case MENU:
				draw.drawMenu(selectedIndex);

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_DOWN)) {
					selectedIndex += 1;
					selectedIndex %= 4;
				}

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_UP)) {
					selectedIndex -= 1;
					if (selectedIndex < 0)
						selectedIndex = 3;
				}

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)) {
					switch (selectedIndex) {
					case 0:
						status = Status.MENU_ITEM;
						selectedIndex = 0;
						break;
					case 1:
						status = Status.MENU_STATUS;
						break;
					case 2:
						status = Status.MENU_SAVE;
						break;
					case 3:
						status = Status.MENU_TERMINATION;
						break;
					}
				}

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_SHIFT)) {
					status = Status.GAME;
				}
				break;

			case MENU_ITEM:
				draw.drawMenuItem(selectedIndex);
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)) {
					if(character.getItemsOwnedList().size() == 0){
						//処理なし
					}
					else{
						status = Status.MENU_ITEM_USEITEM;
					}
				}

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_DOWN)) {
					if(character.getItemsOwnedList().size() == 0){
						//処理なし
					}
					else{
						selectedIndex += 1;
						selectedIndex %= character.getItemsOwnedList().size();
					}

				}

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_UP)) {
					selectedIndex -= 1;
					if (selectedIndex < 0){
						if(character.getItemsOwnedList().size() == 0){
							selectedIndex = 0;
						}
						else{
							selectedIndex = character.getItemsOwnedList().size() - 1;
						}

					}

				}

				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_SHIFT)){
					status = Status.MENU;
				}
				break;
			case MENU_ITEM_USEITEM:
				draw.drawMenuItem(selectedIndex);
				draw.drawUseItem();
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_SHIFT)) {
					status = Status.MENU_ITEM;
				}
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)) {
					status = Status.ITEM_USED;
					character.useItem(character.getItemsOwnedList().get(selectedIndex));
				}
				break;
			case ITEM_USED:
				draw.drawLayer();
				draw.drawChar(character.getPositionX(), character.getPositionY());
				draw.drawItemUsed(character.getItemsOwnedList().get(selectedIndex));
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)) {
					status = Status.GAME;
					character.getItemsOwnedList().remove(selectedIndex);
				}
				break;
			case MENU_STATUS:
				draw.drawMenuStatus(character);
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_SHIFT)) {
					status = Status.MENU;
				}
				break;
			case MENU_SAVE:

				break;
			case MENU_TERMINATION:
				System.exit(0);
				break;
			case BATTLE:
				draw.drawBattleBasic();
				draw.drawBattle();
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER))
					status = Status.BATTLE_COMMAND;
				break;
			case BATTLE_COMMAND:
				draw.drawBattleBasic();
				draw.drawBattleCommand(selectedIndex);
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_DOWN)) {
					selectedIndex += 1;
					selectedIndex %= 2;
				}

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_UP)) {
					selectedIndex -= 1;
					if (selectedIndex < 0)
						selectedIndex = 1;
				}

				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					status = Status.BATTLE_MESSAGE1;
				}
				break;
			case BATTLE_MESSAGE1:
				draw.drawBattleBasic();
				draw.drawBattleMessage1(selectedIndex);
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					status = Status.BATTLE_MESSAGE2;
					character.turn(selectedIndex, enemy);
					enemy.endOfTurn();
				}
				break;
			case BATTLE_MESSAGE2:
				draw.drawBattleBasic();
				draw.drawBattleMessage2(selectedIndex);
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					if(enemy.eStatus == EnumBattleStatus.Died){
						status = Status.BATTLE_PLAYER_WIN;
					}
					else{
						status = Status.BATTLE_MESSAGE3;
						enemy.turn(character);
						character.endOfTurn();
					}

				}
				break;
			case BATTLE_MESSAGE3:
				draw.drawBattleBasic();
				draw.drawBattleMessage3(enemy.getSelect());
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					status = Status.BATTLE_MESSAGE4;
				}
				break;
			case BATTLE_MESSAGE4:
				draw.drawBattleBasic();
				draw.drawBattleMessage4(enemy.getSelect());
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					if(character.eStatus == EnumBattleStatus.Died){
						status = Status.BATTLE_PLAYER_LOSE;
					}
					else{
						status = Status.BATTLE_COMMAND;
					}
				}
				break;

			case BATTLE_PLAYER_WIN:
				draw.drawBattleBasic();
				draw.drawBattleWin();
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					character.addEXP(enemy);
					if(character.canLevelup()){
						
						java.awt.Toolkit.getDefaultToolkit().beep();
						character.levelup();
						status = Status.PLAYER_LEVELUP;
					}
					else{
						status = Status.GAME;
					}
				}
				break;
			case PLAYER_LEVELUP:
				draw.drawBattleBasic();
				draw.drawCharacterLevelUp();
				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					if(character.canLevelup()){
						
						java.awt.Toolkit.getDefaultToolkit().beep();
						character.levelup();
						status = Status.PLAYER_LEVELUP;
					}
					else{
						status = Status.GAME;
					}
				}
				break;
			case BATTLE_PLAYER_LOSE:
				draw.drawBattleBasic();
				draw.drawBattleLose();
				if (MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					status = Status.GAME_OVER;
				}
				break;
			case GAME_OVER:
				draw.drawGameOver();
				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_SPACE)){
					ArrayList<Integer> itemList = character.getItemsOwnedList();
					character = new Character();
					character.setItemsOwnedList(itemList);
					enemy = new Enemy();
					draw.reStart(character, enemy);
					status = Status.START;
				}
				break;
			}

			/*
			 * g.setColor(Color.BLACK); g.setFont(new Font("SansSerif", Font.PLAIN, 10));
			 * g.drawString(FPS + "FPS", 0, 450);
			 */

			myFrame1.panel.draw();
			// ここの処理？
			// System.out.println(status);
			try {
				final long runTime = System.currentTimeMillis() - startTime;
				if (runTime < (2000 / fps)) {
					Thread.sleep((3000 / fps) - (runTime));
				}
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

		}
	}


}
