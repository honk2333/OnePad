import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

//��������
public class DrawPad extends JFrame {
	FileClass fileclass; // �ļ�����
	DrawArea drawarea; // ���岿��
	// ��ɵĸ�������
	Toolpad toolpad;
	Graphpad graphpad;
	Fontpad fontpad;
	Colorpad colorpad;
	Menupad menupad;
	JLabel ding;

	FileClass GetFileClass() {
		return fileclass;
	}

	DrawArea GetDrawArea() {
		return drawarea;
	}

	DrawPad(String string) {
		super(string);
		// ��������Ĵ���
		Icon img = new ImageIcon("images/ding.png");
		ding = new JLabel(img);
		ding.setBounds(20, 100, 50, 50);
		drawarea = new DrawArea(this);// ���岿��
		fileclass = new FileClass(this, drawarea);// �ļ�����
		toolpad = new Toolpad(this);
		graphpad = new Graphpad(this);
		fontpad = new Fontpad(this);
		colorpad = new Colorpad(this);
		menupad = new Menupad(this);

		// ���ø�����λ��
		Container con = getContentPane();//
		Toolkit tool = getToolkit();//
		Dimension dim = tool.getScreenSize();//
		con.setLayout(null);

		// toolpad.setBounds(30, 10, 1500, 80);
		// graphpad.setBounds(1550, 10, 350, 280);
		drawarea.setBounds(0, 80, 1350, dim.height - 300);
		// colorpad.setBounds(dim.width - 350, dim.height - 500, 300, 300);
		// fontpad.setBounds(dim.width - 350, 350, 300, 75);
		// cbutton.setBounds(dim.width - 350, dim.height - 600, 300, 100);
		con.add(ding);
		con.add(toolpad.getoolbar());
		con.add(graphpad.getoolbar());
		// con.add(menupad.getoolbar());
		con.add(drawarea);
		con.add(colorpad.getoolbar());
		con.add(fontpad.getoolbar());
		con.add(colorpad.getbutton());
		con.add(colorpad.getaken());
		con.add(drawarea.tarea);
		setBounds(100, 50, 1710, dim.height - 150);
		setVisible(true);
		validate();
		setResizable(false); // ��ֹ�ı䴰���С
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (drawarea.saven) {
					setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				} else {
					int selected = JOptionPane.showConfirmDialog(drawarea, "���и���δ���棬�Ƿ񱣴�? ", "��ʾ ",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (JOptionPane.NO_OPTION == selected) {
						System.exit(0);
					} else if (JOptionPane.OK_OPTION == selected) {
						fileclass.saveFile();
						System.exit(0);
					} else {
						setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					}
				}

			}
		});

	}

}
