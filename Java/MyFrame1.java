
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
	static final int WINDOW_HEIGHT = 512+7;//15*15���s�b�^������T�C�Y
	static final int WINDOW_WIDTH = 496;//15*15���s�b�^������T�C�Y
	
	static final int STATUS_HEIGHT = (int)(WINDOW_HEIGHT * 0.2);	//�X�e�[�^�X�̎l�p�`�̍���
	static final int STATUS_WIDTH = (int)(WINDOW_WIDTH * 0.25);//�X�e�[�^�X�̎l�p�`�̕�
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
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Window������Ƃ��̏���
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		addKeyListener(new MyKeyboard1());
		setResizable(false);	//��ʂ̃T�C�Y�ύX���ł��Ȃ��悤�ɂ���
		setLocationRelativeTo(null);	//�����ɕ\������
		setVisible(true);

	}

}
