import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Colorpad extends JFrame implements ActionListener {
	DrawPad dp;
	FileClass fileclass;
	DrawArea drawarea;

	// ��ɫ����----------------------------------------------------------------------------------------------------------------------------------------------
	JButton cbutton;
	JButton taken;
	JButton colorButton[];
	JToolBar colorButtonPanel;

	JButton getbutton() {
		return cbutton;
	}

	JButton getaken() {
		return taken;
	}

	JToolBar getoolbar() { // ��drawpad����
		return colorButtonPanel;
	}

	Colorpad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
		// ��ɫ����----------------------------------------------------------------------------------------------------------------------------------------------
		Icon cicon = new ImageIcon("images/color.png");
		cbutton = new JButton("��׼��ɫ", cicon);
		cbutton.setToolTipText("�Զ�����ɫ");
		cbutton.addActionListener(this);
		cicon = new ImageIcon("images/getcolor.png");
		taken = new JButton("ȡɫ��", cicon);
		taken.setToolTipText("ȡɫ��");
		taken.addActionListener(this);
		Toolkit tool = getToolkit();
		Dimension dim = tool.getScreenSize();
		cbutton.setBounds(1470, 450, 230, 100);
		taken.setBounds(1350, 450, 120, 100);
		colorButton = new JButton[20];//
		colorButtonPanel = new JToolBar(JToolBar.VERTICAL);//
		colorButtonPanel.setLayout(new GridLayout(0, 5, 0, 0));
		colorButtonPanel.setFloatable(false);
		colorButtonPanel.setBounds(1350, 550, 355, 315);
		for (int i = 0; i < 20; i++) {
			colorButton[i] = new JButton("");//
			colorButtonPanel.add(colorButton[i]);
		}
		//
		colorButton[0].setBackground(new Color(0xffffff));
		colorButton[1].setBackground(new Color(0x000000));
		colorButton[2].setBackground(new Color(0x848683));
		colorButton[3].setBackground(new Color(0xc3c3be));
		colorButton[4].setBackground(new Color(0xcdbedb));
		colorButton[5].setBackground(new Color(0xb97b56));
		colorButton[6].setBackground(new Color(0xffadd6));
		colorButton[7].setBackground(new Color(0xf01e1f));
		colorButton[8].setBackground(new Color(0x89010d));
		colorButton[9].setBackground(new Color(0xfef007));
		colorButton[10].setBackground(new Color(0xffc80c));
		colorButton[11].setBackground(new Color(0xff7c26));
		colorButton[12].setBackground(new Color(0xefe2ab));
		colorButton[13].setBackground(new Color(0xb6e51d));
		colorButton[14].setBackground(new Color(0x24b04f));
		colorButton[15].setBackground(new Color(0x93dceb));
		colorButton[16].setBackground(new Color(0x6997bb));
		colorButton[17].setBackground(new Color(0x07a0e6));
		colorButton[18].setBackground(new Color(0xd9a2dc));
		colorButton[19].setBackground(new Color(0x9c4ca1));
		for (int i = 0; i < 20; i++) {// ����ɫ��ť����¼�
			int finalI = i;
			colorButton[i].addActionListener(e -> drawarea.setRGB(colorButton[finalI].getBackground().getRed(),
					colorButton[finalI].getBackground().getGreen(), colorButton[finalI].getBackground().getBlue()));
		}
	}

	// �����°�ťʱ,����ť��Ӧ�ı�Ŵ��������ദ��
	public void actionPerformed(ActionEvent e) {
		// ��ť����������
		if (e.getSource() == cbutton) {
			drawarea.chooseColor();
		}
		// ȡɫ
		if (e.getSource() == taken) {
			drawarea.chosenStatus = 13;
		}
	}
}