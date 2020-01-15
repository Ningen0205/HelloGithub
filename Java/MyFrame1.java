
import javax.swing.*;
import java.awt.event.*;
public class MyFrame1 extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel1 panel;
	JLabel label1;

//	480
	static final int WINDOW_HEIGHT = 512+7;//15*15がピッタリ入るサイズ
	static final int WINDOW_WIDTH = 496;//15*15がピッタリ入るサイズ
	
	static final int STATUS_HEIGHT = (int)(WINDOW_HEIGHT * 0.2);	//ステータスの四角形の高さ
	static final int STATUS_WIDTH = (int)(WINDOW_WIDTH * 0.25);//ステータスの四角形の幅
	MyFrame1(){

		//panel
		panel = new MyPanel1();
		add(panel);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				MyMain1.loop = false;
			}
		});
		
		setTitle("test");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Windowを閉じたときの処理
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		addKeyListener(new MyKeyboard1());
		setResizable(false);	//画面のサイズ変更をできないようにする
		setLocationRelativeTo(null);	//中央に表示する
		setVisible(true);

	}

}
