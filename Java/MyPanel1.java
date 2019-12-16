package GUI.copy;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class MyPanel1 extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage image;//バッファリング用の描画空間（こちらに描画して最後にPanelにpaintする）
	
	public MyPanel1() {
		image = new BufferedImage(MyFrame1.WINDOW_WIDTH, MyFrame1.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	@Override
/*	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(image,0,0,this);
	}*/
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image,0,0,this);
	}
	
	public void draw() {
		this.repaint();
	}
}
