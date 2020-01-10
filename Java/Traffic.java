import GUI.copy.Map;

public class Traffic{
    private static boolean result;
    private static final int DOWN = 0;
	private static final int LEFT = 1;
	private static final int RIGHT = 2;
    private static final int UP = 3;
    
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
}