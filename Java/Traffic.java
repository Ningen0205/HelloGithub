<<<<<<< HEAD
public class Traffic{
    private static int next;    //次のマスの配列インデックス
    private static boolean result;  //通行可能かどうかの変数
    private static final int test[] = {15,-1,1,-15};
=======
import GUI.copy.Map;

public class Traffic{
    private static boolean result;
>>>>>>> 699e48be48dd2da29a452ef038a53a5a7dbf7a3f
    private static final int DOWN = 0;
	private static final int LEFT = 1;
	private static final int RIGHT = 2;
    private static final int UP = 3;
    
<<<<<<< HEAD
    //未完成
    static boolean canTraffic(final int direction, int positionX, int positionY) {
        result = true;
        positionX = (int) (positionX / 32);
        positionY = (int) (positionY / 32);
        next = (positionX + positionY * 15) + test[direction];

        System.out.println("positionX : " + positionX);
        System.out.println("positionY : " + positionY);
        System.out.println(next);

        if ((positionY >= 14) && (direction == DOWN))
            return false;
        if ((positionX <= 0) && (direction == LEFT))
            return false;
        if ((positionX >= 14) && (direction == RIGHT))
            return false;
        if ((positionY <= 0) && (direction == UP))
            return false;

        if ((Map.ITEM_LAYER[next] != 0) || (Map.LAYER2[next] != 7))
            result = false;

        return result;
    }

    static int getNext(int direction, int positionX, int positionY) {
        positionX = (int) (positionX / 32);
        positionY = (int) (positionY / 32);
        next = (positionX + positionY * 15) + test[direction];

        return next;
    }
=======
    static boolean canTraffic(int direction,int positionX,int positionY){
        positionX = (int)(positionX % 16); 
        positionY = (int)(positionY / 16);
        switch(direction){
            case DOWN:
                if((Map.ITEM_LAYER[positionX+positionY+16] == 0) && (Map.ITEM_LAYER[positionX+positionY+16] != 7)){
                    result = true;
                }
                else{
                    result = false;
                }
                break;
            case LEFT:
                if((Map.ITEM_LAYER[positionX+positionY-1] == 0) && (Map.ITEM_LAYER[positionX+positionY-1] != 7)){
                    result = true;
                }
                else{
                    result = false;
                }
                break;
            case RIGHT:
                if((Map.ITEM_LAYER[positionX+positionY+1] == 0) && (Map.ITEM_LAYER[positionX+positionY+1] != 7)){
                    result = true;
                }
                else{
                    result = false;
                }
                break;
            case UP:
                if((Map.ITEM_LAYER[positionX+positionY-16] == 0) && (Map.ITEM_LAYER[positionX+positionY-16] != 7)){
                    result = true;
                }

                else{
                    result = false;
                }
                break;
        }
        return result;
    }
>>>>>>> 699e48be48dd2da29a452ef038a53a5a7dbf7a3f
}