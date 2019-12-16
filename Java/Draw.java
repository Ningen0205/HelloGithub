package GUI.copy;

import java.awt.*;

public class Draw {
	//�}�b�v�֌W
	public int[] mapLayer1;	//�}�b�v�}�X��ID
	public int[] mapLayer2; //�}�b�v�}�X��ID
	int mapIndex;	//�`�悷��}�X�̔z��ɑ΂���C���f�b�N�X
	int squareIdLayer1;	//���C��1�̕`�悷��Ƃ��̑Ή�ID
	int squareIdLayer2;//���C��2�̕`�悷��Ƃ��̑Ή�ID
	int squareIdImageBitXLayer1;//�}�b�v�`�b�v�̕`�悷��ꏊ(��)
	int squareIdImageBitXLayer2;//�}�b�v�`�b�v�̕`�悷��ꏊ�i���j
	int squareIdImageBitYLayer1;//�}�b�v�`�b�v�̕`�悷��ꏊ�i�c�j
	int squareIdImageBitYLayer2;//�}�b�v�`�b�v�̕`�悷��ꏊ�i�c�j
	
	final int SQUARE_SIZE = 32;	//�P�}�X�̃T�C�Y
	final int SQUARE_SUM = 15;	//�c���̃}�X�̃T�C�Y
	
	Character character;
	Image mapImage;
	Image charImage;
	Graphics g;
	
	Draw(){
		character = MyMain1.character;
		mapImage = MyMain1.mapImage;
		charImage = MyMain1.charImage;
        mapLayer1 = Map.getLayer1();
        mapLayer2 = Map.getLayer2();
        
		g = MyMain1.myFrame1.panel.image.getGraphics();	//�p�l��
	}
	
	void drawLayer(){
		mapIndex = 0;
		//���C��1,2�̕`��
		for(int i=0; i<SQUARE_SUM; i++) {	
			for(int j=0; j<SQUARE_SUM; j++) {
				squareIdLayer1 = mapLayer1[mapIndex];
				squareIdLayer2 = mapLayer2[mapIndex];
				squareIdImageBitXLayer1 = (int)(squareIdLayer1%16)*32;//�}�b�v�`�b�v�̕`�悷��ꏊ(��)
				squareIdImageBitXLayer2 = (int)(squareIdLayer2%16)*32;//�}�b�v�`�b�v�̕`�悷��ꏊ�i���j
				squareIdImageBitYLayer1 = (int)(squareIdLayer1/16)*32;//�}�b�v�`�b�v�̕`�悷��ꏊ�i�c�j
				squareIdImageBitYLayer2 = (int)(squareIdLayer2/16)*32;//�}�b�v�`�b�v�̕`�悷��ꏊ�i�c�j
				g.drawImage(mapImage,j*32,i*32,(j*32)+32,(i*32)+32,
						squareIdImageBitXLayer1,squareIdImageBitYLayer1,squareIdImageBitXLayer1+32,squareIdImageBitYLayer1+32,MyMain1.myFrame1);
				if(squareIdLayer1 == squareIdLayer2) {	//���C���P�ƂQ������ID�Ȃ�΃��C��2�̕`����s��Ȃ�
					mapIndex++;
					continue;
				}
				else {
				g.drawImage(mapImage,j*32,i*32,(j*32)+32,(i*32)+32,
						squareIdImageBitXLayer2,squareIdImageBitYLayer2,squareIdImageBitXLayer2+32,squareIdImageBitYLayer2+32,MyMain1.myFrame1);
					mapIndex++;
				}
				
			}
		}
	}
	
	void drawChar(int positionX, int positionY) {
		g.drawImage(charImage, character.getPositonX(),character.getPositionY(), character.getPositonX()+32,character.getPositionY()+32,
								32, 32*character.getDirection(), 64, 32*character.getDirection() + 32, MyMain1.myFrame1);
	}

}
