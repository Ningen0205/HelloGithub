package GUI.copy;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*import io.github.kusaanko.Shooting.EnumShootingScreen;
import io.github.kusaanko.Shooting.Keyboard;*/

public class MyMain1 /*implements Runnable*/{
	//基礎描画関連
	public static MyFrame1 myFrame1;
	Graphics g;

	public static boolean loop;
	Status status;
	
	public static Image mapImage;		//マップチップ
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
    
    //Characterの位置とか向き
    static Character character;

	
	public MyMain1(){
		
		boolean test = true;
		
		character = new Character();
		myFrame1 = new MyFrame1();
		WINDOW_WIDTH = MyFrame1.WINDOW_WIDTH;
		WINDOW_HEIGHT = MyFrame1.WINDOW_HEIGHT;
		JTextField name = new JTextField();
		charImage = Toolkit.getDefaultToolkit().getImage("Java/Images/pipo-xmaschara01.png");
//	charImage = Toolkit.getDefaultToolkit().getImage(Java\Images\pipo-xmaschara01.png)
		mapImage = Toolkit.getDefaultToolkit().getImage("Java/Images/WorldMap-A2.png");//マップのマップチップ
		draw = new Draw();
		g = myFrame1.panel.image.getGraphics();	//パネル
//		g = myFrame1.comp.getGraphics();	//パネル
		status = Status.START;
		loop = true;
		
        //FPS
        long startTime;
        long fpsTime = 0;
        int fps = 30;
        int FPS = 0;
        int FPSCount = 0;

        //マップID取得
        mapLayer1 = Map.getLayer1();
        mapLayer2 = Map.getLayer2();
        
        while(loop) {			
           if((System.currentTimeMillis() - fpsTime) >= 1000) {
                fpsTime = System.currentTimeMillis();
                FPS = FPSCount;
                FPSCount = 0;
            }
            
            FPSCount++;
            startTime = System.currentTimeMillis();            

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WINDOW_WIDTH,WINDOW_HEIGHT);
			switch(status) {
			case START:								//スタイル	　サイズ
				Font font = new Font("SansSerif", Font.PLAIN, 30);
                g.setFont(font);
                //フォントの位置に必要なピクセル数などを教えてくれる
                FontMetrics metrics = g.getFontMetrics(font);
				g.setColor(Color.BLACK);
				//横の描画に必要なピクセル数を教えてくれる
				g.drawString("Hello RPG!", (WINDOW_WIDTH / 2) - (metrics.stringWidth("Hello RPG!") / 2), 100);
				g.drawString("Press SPACE to Start", (WINDOW_WIDTH / 2) - (metrics.stringWidth("Press SPACE to Start") / 2), 200);
                if(MyKeyboard1.isKeyPressed(KeyEvent.VK_SPACE)) {
//              	status = Status.PLAYER_NAME_DECISION;
                	status = Status.GAME;
                }
				break;
			case PLAYER_NAME_DECISION:
				new PlayerNameFrame();
				break;
			case GAME:
				draw.drawLayer();
				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					status = Status.BATTLE;
				}
				//SHIFTでメニュー表示
				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_SHIFT)){
					selectedIndex = 0;	//一番上にカーソルを合わせる
					status = Status.MENU;
				}
				//どのキーを押したかのチェック
                if(MyKeyboard1.isKeyPressed(KeyEvent.VK_LEFT)) {
                	if(character.getDirection() == Character.LEFT) {
                		character.setPositionX(character.getPositionX()-32);
                	}
                	else{
                		character.setDirection(Character.LEFT);
                	}
                }
                if(MyKeyboard1.isKeyPressed(KeyEvent.VK_RIGHT)) {
                	if(character.getDirection() == Character.RIGHT) {
                		character.setPositionX(character.getPositionX()+32);
                	}
                	else {
                		character.setDirection(Character.RIGHT);
                	}
                }
                if(MyKeyboard1.isKeyPressed(KeyEvent.VK_DOWN)) {
                	if(character.getDirection() == Character.DOWN) {
                		character.setPositionY(character.getPositionY()+32);
                	}
                	else {
                		character.setDirection(Character.DOWN);
                	}
                }
                if(MyKeyboard1.isKeyPressed(KeyEvent.VK_UP)) {
                	if(character.getDirection() == Character.UP) {
                		character.setPositionY(character.getPositionY()-32);
                	}
                	else {
                		character.setDirection(Character.UP);
                	}
                }
                draw.drawChar(character.getPositionX(),character.getPositionY());
				break;
			case MENU:
				draw.drawMenu(selectedIndex);
/*				draw.drawLayer();
				draw.drawChar(character.getPositionX(),character.getPositionY());
				
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

				g.drawLine(20,(30+40*selectedIndex),130,(30+40*selectedIndex));	//選択している場所へ線の描画*/
				
				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_DOWN)){
					selectedIndex += 1;
					selectedIndex %= 4;
				}

				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_UP)){
					selectedIndex -= 1;
					if(selectedIndex < 0) selectedIndex = 3;
				}

				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)){
					switch(selectedIndex){
						case 0:
							status = status.MENU_ITEM;
							break;
						case 1:
							status = status.MENU_STATUS;
							break;
						case 2:
							status = status.MENU_SAVE;
							break;
						case 3:
							status = status.MENU_TERMINATION;
							break;
					}
				}

				if(MyKeyboard1.isKeyPressed(KeyEvent.VK_SHIFT)){
					status = Status.GAME;
				}
				break;

			case MENU_ITEM:
				g.fillRect(100,100,200,400);//枠内描画
				g.setColor(Color.WHITE);
				g.drawRect(100,100,200,400);//外枠描画
				break;
			case MENU_STATUS:
				break;
			case MENU_SAVE:
				break;
			case MENU_TERMINATION:
				break;
			case BATTLE:
				font = new Font("SansSerif", Font.PLAIN, 20);
				metrics = g.getFontMetrics(font);
				g.setFont(font);
	            g.setColor(Color.BLACK);
	            g.fillRect(0, 0, WINDOW_WIDTH,WINDOW_HEIGHT);
	            g.setColor(Color.WHITE);
	            g.drawRect(0,300,479,179);  
	            g.drawRect(0,0,MyFrame1.STATUS_WIDTH,MyFrame1.STATUS_HEIGHT);
	            g.drawString("人間",35,30);	//一人目　キャラ名
	            g.drawString("HP:"+Player.hitPoint,(MyFrame1.STATUS_WIDTH / 2) - (metrics.stringWidth("HP:"+Player.hitPoint) / 2) ,60);	//HP
	            g.drawString("MP:"+Player.hitPoint,(MyFrame1.STATUS_WIDTH / 2) - (metrics.stringWidth("MP:"+Player.magicPoint) / 2), 90);	//MP
	            if(MyKeyboard1.isKeyPressed(KeyEvent.VK_ENTER)) status = Status.GAME;
				break;
			case GAME_OVER:
				break;
			}
			
/*            g.setColor(Color.BLACK);
           g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(FPS + "FPS", 0, 450);*/

			myFrame1.panel.draw();
			//ここの処理？
			System.out.println(status);
			try {
	        	long runTime = System.currentTimeMillis() - startTime;
	            if(runTime<(2000 / fps)) {
	            	Thread.sleep((3000 / fps) - (runTime));
	            }
	        } 
	        catch (InterruptedException e) {
	        	e.printStackTrace();
	        }

		}
	}


	public static void main(String[] args) {
			new MyMain1();
	}

}
