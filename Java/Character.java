package GUI.copy;
import java.util.ArrayList;
public class Character {
	
	private int positionX;	//キャラのX座標の位置
	private int positionY;	//キャラのY座標の位置
	private int direction;	//キャラの向き(下:0 左:1 右:2 上:3)
	
	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	
	public ArrayList<Integer> itemsOwnedList = new ArrayList<Integer>();	//所持しているアイテムのアイテムIDを管理

	Character(){
		positionX = 32;
		positionY = 32;
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
}
