import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Welcome extends JFrame implements ActionListener {
	String names[] = { "newfile", "openfile", "exit" };// ���幤����ͼ�������
	Icon icons[];// ���尴ťͼ������
	String tiptext[] = { "�½�һ��ͼƬ", "��ͼƬ", "�˳�" };// ����������Ƶ���Ӧ�İ�ť�ϸ�����Ӧ����ʾ
	JButton button[];// ���幤�����еİ�ť��
	FileClass fileclass;// �ļ�����
	DrawPad drawpad;
	DrawArea drawarea;// ������Ķ���

	JFrame WelFrame;
	JLayeredPane layeredPane;

	Welcome(String s) {
		super(s);
//		drawpad = new DrawPad("Onepad");
//		drawpad.setVisible(false);

		WelFrame = new JFrame(s);
		WelFrame.setDefaultCloseOperation(3);

		WelFrame.setSize(1200, 900);
		WelFrame.setLocationRelativeTo(null); // ���ô�����ʾ�ھ���λ��
		WelFrame.setResizable(false); // ���ô����޷�������С

//		JLabel Backimage= new JLabel();
//		Backimage.setIcon(new ImageIcon("images/background.jpg"));
//		WelFrame.add(Backimage);

		JToolBar Welpanel;// ���尴ť���
		Welpanel = new JToolBar(JToolBar.HORIZONTAL);// ����ˮƽ��������
		Welpanel.setLayout(new GridLayout(0, 3, 0, 0));// ���ò���
		Welpanel.setFloatable(false);// ���ɸ���

		icons = new ImageIcon[names.length];// ��ʼ���������е�ͼ��
		button = new JButton[names.length]; // �����������еİ�ť

		for (int i = 0; i < names.length; i++) {
			icons[i] = new ImageIcon("images/" + names[i] + ".png");// ���ͼƬ������·��Ϊ��׼��
			button[i] = new JButton(tiptext[i], icons[i]);// �����������еİ�ť
			button[i].setToolTipText(tiptext[i]);// ����ƶ�����Ӧ�İ�ť�ϸ�����Ӧ����ʾ
			Welpanel.add(button[i]);// ��Ӱ�ť
			button[i].setBackground(Color.white);
			button[i].addActionListener(this);
		}
		Welpanel.setBounds(400, 350, 450, 150);//

		Toolkit tool = getToolkit();//
		Dimension dim = tool.getScreenSize();//

		// setVisible(true);
		// setBounds(0, 0, dim.width, dim.height);

		layeredPane = new JLayeredPane() {
			public void paintComponent(Graphics g) {// ��д�������ķ���
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("images/background.jpg");// ����ͼƬ
				image.setImage(image.getImage().getScaledInstance(1200, 900, Image.SCALE_AREA_AVERAGING));// ����ͼƬ��С��������С
				g.drawImage(image.getImage(), 0, 0, this);// ���»������
			}
		};
		WelFrame.add(Welpanel);//
		WelFrame.add(layeredPane);
		WelFrame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button[0]) {
			
			drawpad = new DrawPad("Onepad");
			drawpad.setVisible(true);
			WelFrame.setVisible(false);
		} else if (e.getSource() == button[1]) {
			drawpad = new DrawPad("Onepad");
			fileclass = new FileClass(drawpad, drawpad.drawarea);
			fileclass.openFile();//
			drawpad.setVisible(true);
			WelFrame.setVisible(false);
		} else if (e.getSource() == button[2]) {
			setVisible(false);
			System.exit(0);
		}

	}

}