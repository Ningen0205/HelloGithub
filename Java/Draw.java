package GUI.copy;

import java.awt.*;

public class Draw {
	//マップ関係
	public int[] mapLayer1;	//マップマスのID
	public int[] mapLayer2; //マップマスのID
	int mapIndex;	//描画するマスの配列に対するインデックス
	int squareIdLayer1;	//レイヤ1の描画するときの対応ID
	int squareIdLayer2;//レイヤ2の描画するときの対応ID
	int squareIdImageBitXLayer1;//マップチップの描画する場所(横)
	int squareIdImageBitXLayer2;//マップチップの描画する場所（横）
	int squareIdImageBitYLayer1;//マップチップの描画する場所（縦）
	int squareIdImageBitYLayer2;//マップチップの描画する場所（縦）
	
	final int SQUARE_SIZE = 32;	//１マスのサイズ
	final int SQUARE_SUM = 15;	//縦横のマスのサイズ
	
	Character character;
	Image mapImage;
	Image charImage;
	Graphics g;
	
	Draw(){
		character = MyMain1.character;
		mapImage = MyMain1.mapImage;
		charImage = MyMain1.charImage;
        mapLayer1 = Map.getLayer1();
        mapLayer2 = Map.getLayer2();
        
		g = MyMain1.myFrame1.panel.image.getGraphics();	//パネル
	}
	
	void drawLayer(){
		mapIndex = 0;
		//レイヤ1,2の描画
		for(int i=0; i<SQUARE_SUM; i++) {	
			for(int j=0; j<SQUARE_SUM; j++) {
				squareIdLayer1 = mapLayer1[mapIndex];
				squareIdLayer2 = mapLayer2[mapIndex];
				squareIdImageBitXLayer1 = (int)(squareIdLayer1%16)*32;//マップチップの描画する場所(横)
				squareIdImageBitXLayer2 = (int)(squareIdLayer2%16)*32;//マップチップの描画する場所（横）
				squareIdImageBitYLayer1 = (int)(squareIdLayer1/16)*32;//マップチップの描画する場所（縦）
				squareIdImageBitYLayer2 = (int)(squareIdLayer2/16)*32;//マップチップの描画する場所（縦）
				g.drawImage(mapImage,j*32,i*32,(j*32)+32,(i*32)+32,
						squareIdImageBitXLayer1,squareIdImageBitYLayer1,squareIdImageBitXLayer1+32,squareIdImageBitYLayer1+32,MyMain1.myFrame1);
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
	
	void drawChar(int positionX, int positionY) {
		g.drawImage(charImage, character.getPositonX(),character.getPositionY(), character.getPositonX()+32,character.getPositionY()+32,
								32, 32*character.getDirection(), 64, 32*character.getDirection() + 32, MyMain1.myFrame1);
	}

	void drawMenu(int selectedIndex){
		drawLayer();
		drawChar(character.getPositionX(),character.getPositionY());
		
		Font font = new Font("SansSerif", Font.PLAIN, 15);
		g.setColor(Color.BLACK);
		FontMetrics metrics = g.getFontMetrics(font);
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

}
