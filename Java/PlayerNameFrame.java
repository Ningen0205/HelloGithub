
import javax.swing.JFrame;

public class PlayerNameFrame extends JFrame{
	static final long serialVersionUID = 1L;
	static final int WINDOW_HEIGHT = 512+7;//15*15���s�b�^������T�C�Y
	static final int WINDOW_WIDTH = 496;//15*15���s�b�^������T�C�Y
	
	PlayerNameFrame(){
		setTitle("test");
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Window������Ƃ��̏���
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		addKeyListener(new MyKeyboard1());
		setResizable(false);	//��ʂ̃T�C�Y�ύX���ł��Ȃ��悤�ɂ���
		setLocationRelativeTo(null);	//�����ɕ\������
		setVisible(true);
	}
}
