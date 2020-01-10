
import javax.swing.JFrame;

public class PlayerNameFrame extends JFrame{
	static final long serialVersionUID = 1L;
	static final int WINDOW_HEIGHT = 512+7;//15*15がピッタリ入るサイズ
	static final int WINDOW_WIDTH = 496;//15*15がピッタリ入るサイズ
	
	PlayerNameFrame(){
		setTitle("test");
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Windowを閉じたときの処理
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		addKeyListener(new MyKeyboard1());
		setResizable(false);	//画面のサイズ変更をできないようにする
		setLocationRelativeTo(null);	//中央に表示する
		setVisible(true);
	}
}
