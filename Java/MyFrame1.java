
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
public class MyFrame1 extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel1 panel;
	JLabel label1;
	Container pane;
//	480
	static final int WINDOW_HEIGHT = 512+7;//15*15���s�b�^������T�C�Y
	static final int WINDOW_WIDTH = 496;//15*15���s�b�^������T�C�Y
	
	static final int STATUS_HEIGHT = (int)(WINDOW_HEIGHT * 0.2);	//�X�e�[�^�X�̎l�p�`�̍���
	static final int STATUS_WIDTH = (int)(WINDOW_WIDTH * 0.25);//�X�e�[�^�X�̎l�p�`�̕�
	MyFrame1(){
		//contentpane
/*		pane = getContentPane();
		pane.setLayout(null);
		comp = new MyComponent1();
		pane.add(comp);*/
		
		//panel
		panel = new MyPanel1();
//		pane.add(panel);
		add(panel);

//		getContentPane().add(jc);

//		add(panel);

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
