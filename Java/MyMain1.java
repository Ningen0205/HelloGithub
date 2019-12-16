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

import io.github.kusaanko.Shooting.EnumShootingScreen;
import io.github.kusaanko.Shooting.Keyboard;

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
    
    //Characterの位置とか向き
    static Character character;

	
	public MyMain1(){
		
		boolean test = true;
		
		character = new Character();
		myFrame1 = new MyFrame1();
		WINDOW_WIDTH = MyFrame1.WINDOW_WIDTH;
		WINDOW_HEIGHT = MyFrame1.WINDOW_HEIGHT;
		JTextField name = new JTextField();
		charImage = Toolkit.getDefaultToolkit().getImage("./pipo-xmaschara01.png");
		mapImage = Toolkit.getDefaultToolkit().getImage("./WorldMap-A2.png");//マップのマップチップ
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
