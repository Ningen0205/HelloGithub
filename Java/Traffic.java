public class Traffic{
    private static int next;    //次のマスの配列インデックス
    private static boolean result;  //通行可能かどうかの変数
    private static final int test[] = {15,-1,1,-15};
    private static final int DOWN = 0;
	private static final int LEFT = 1;
	private static final int RIGHT = 2;
    private static final int UP = 3;
    
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
}