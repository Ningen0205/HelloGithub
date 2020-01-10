import java.awt.*;
import java.util.ArrayList;
public class Draw {
	//マップ関係
	public int[] mapLayer1;	//マップマスのID
	public int[] mapLayer2; //マップマスのID
	public int[] itemLayer;	//アイテムID
	int mapIndex;	//描画するマスの配列に対するインデックス
	int squareIdLayer1;	//レイヤ1の描画するときの対応ID
	int squareIdLayer2;//レイヤ2の描画するときの対応ID
	int itemIDLayer;	//描画する際の対応ID
	int squareIdImageBitXLayer1;//マップチップの描画する場所(横)
	int squareIdImageBitXLayer2;//マップチップの描画する場所（横）
	int squareIdImageBitYLayer1;//マップチップの描画する場所（縦）
	int squareIdImageBitYLayer2;//マップチップの描画する場所（縦）

	Font font;
	FontMetrics metrics;

	final int SQUARE_SIZE = 32;	//１マスのサイズ
	final int SQUARE_SUM = 15;	//縦横のマスのサイズ
	
	Character character;
	Image mapImage;
	Image charImage;
	Image itemBoxImage;
	Graphics g;


	
	Draw(){
		character = MyMain1.character;
		mapImage = MyMain1.mapImage;
		charImage = MyMain1.charImage;
		itemBoxImage = MyMain1.itemBoxImage;
        mapLayer1 = Map.getLayer1();
		mapLayer2 = Map.getLayer2();
		itemLayer = Map.getItemLayer();
        
		g = MyMain1.myFrame1.panel.image.getGraphics();	//パネル
	}
	
	void drawLayer(){
		mapIndex = 0;
		//レイヤ1,2の描画
		for(int i=0; i<SQUARE_SUM; i++) {	
			for(int j=0; j<SQUARE_SUM; j++) {
				squareIdLayer1 = mapLayer1[mapIndex];
				squareIdLayer2 = mapLayer2[mapIndex];
				itemIDLayer = itemLayer[mapIndex];
				squareIdImageBitXLayer1 = (int)(squareIdLayer1%16)*32;//マップチップの描画する場所(横)
				squareIdImageBitXLayer2 = (int)(squareIdLayer2%16)*32;//マップチップの描画する場所（横）
				squareIdImageBitYLayer1 = (int)(squareIdLayer1/16)*32;//マップチップの描画する場所（縦）
				squareIdImageBitYLayer2 = (int)(squareIdLayer2/16)*32;//マップチップの描画する場所（縦）
				g.drawImage(mapImage,j*32,i*32,(j*32)+32,(i*32)+32,
						squareIdImageBitXLayer1,squareIdImageBitYLayer1,squareIdImageBitXLayer1+32,squareIdImageBitYLayer1+32,MyMain1.myFrame1);
				if(itemIDLayer != 0){
					g.drawImage(itemBoxImage,j*32,i*32,(j*32)+32,(i*32)+32,
						0,0,32,32,MyMain1.myFrame1);
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
	
	void drawChar(int positionX, int positionY) {
		g.drawImage(charImage, character.getPositonX(),character.getPositionY(), character.getPositonX()+32,character.getPositionY()+32,
								32, 32*character.getDirection(), 64, 32*character.getDirection() + 32, MyMain1.myFrame1);
	}

	void drawMenu(int selectedIndex){
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

<<<<<<< HEAD
	void drawMenuItem(int selectedIndex){
=======
	void drawMenuItem(){
>>>>>>> 699e48be48dd2da29a452ef038a53a5a7dbf7a3f
		drawLayer();
		drawChar(character.getPositionX(),character.getPositionY());
		font = new Font("SansSerif", Font.PLAIN, 15);
		g.setColor(Color.BLACK);
		g.fillRect(50,50,200,300);//枠内描画
		g.setColor(Color.WHITE);
		g.drawRect(50,50,200,300);//外枠描画
<<<<<<< HEAD

		ArrayList<Integer> itemsOwnedList = character.getItemsOwnedList();
		int itemPositionX = 100;
		int itemPositionY = 80; 
		if(itemsOwnedList != null){
			for (Integer value : itemsOwnedList) {
				g.drawString(ItemID.ITEM_NAME[value],itemPositionX,itemPositionY);
				itemPositionY += 30;
			}
		}

		g.drawLine(100,(30+30*selectedIndex),150,(30+30*selectedIndex));	//選択している場所へ線の描画
=======
>>>>>>> 699e48be48dd2da29a452ef038a53a5a7dbf7a3f
	}

	void drawMenuStatus(){
		drawLayer();
		drawChar(character.getPositionX(),character.getPositionY());
		font = new Font("SansSerif", Font.PLAIN, 15);
		g.setColor(Color.BLACK);
		g.fillRect(50,50,200,300);//枠内描画
		g.setColor(Color.WHITE);
		g.drawRect(50,50,200,300);//外枠描画
	}

}
