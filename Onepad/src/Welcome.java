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
	String names[] = { "newfile", "openfile", "exit" };// 定义工具栏图标的名称
	Icon icons[];// 定义按钮图标数组
	String tiptext[] = { "新建一个图片", "打开图片", "退出" };// 这里是鼠标移到相应的按钮上给出相应的提示
	JButton button[];// 定义工具条中的按钮组
	FileClass fileclass;// 文件对象
	DrawPad drawpad;
	DrawArea drawarea;// 画布类的定义

	JFrame WelFrame;
	JLayeredPane layeredPane;

	Welcome(String s) {
		super(s);
//		drawpad = new DrawPad("Onepad");
//		drawpad.setVisible(false);

		WelFrame = new JFrame(s);
		WelFrame.setDefaultCloseOperation(3);

		WelFrame.setSize(1200, 900);
		WelFrame.setLocationRelativeTo(null); // 设置窗体显示在居中位置
		WelFrame.setResizable(false); // 设置窗口无法调整大小

//		JLabel Backimage= new JLabel();
//		Backimage.setIcon(new ImageIcon("images/background.jpg"));
//		WelFrame.add(Backimage);

		JToolBar Welpanel;// 定义按钮面板
		Welpanel = new JToolBar(JToolBar.HORIZONTAL);// 设置水平方向排列
		Welpanel.setLayout(new GridLayout(0, 3, 0, 0));// 设置布局
		Welpanel.setFloatable(false);// 不可浮动

		icons = new ImageIcon[names.length];// 初始化工具栏中的图标
		button = new JButton[names.length]; // 创建工具栏中的按钮

		for (int i = 0; i < names.length; i++) {
			icons[i] = new ImageIcon("images/" + names[i] + ".png");// 获得图片（以类路径为基准）
			button[i] = new JButton(tiptext[i], icons[i]);// 创建工具条中的按钮
			button[i].setToolTipText(tiptext[i]);// 鼠标移动到相应的按钮上给出相应的提示
			Welpanel.add(button[i]);// 添加按钮
			button[i].setBackground(Color.white);
			button[i].addActionListener(this);
		}
		Welpanel.setBounds(400, 350, 450, 150);//

		Toolkit tool = getToolkit();//
		Dimension dim = tool.getScreenSize();//

		// setVisible(true);
		// setBounds(0, 0, dim.width, dim.height);

		layeredPane = new JLayeredPane() {
			public void paintComponent(Graphics g) {// 重写绘制面板的方法
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("images/background.jpg");// 导入图片
				image.setImage(image.getImage().getScaledInstance(1200, 900, Image.SCALE_AREA_AVERAGING));// 设置图片大小跟随面板大小
				g.drawImage(image.getImage(), 0, 0, this);// 重新绘制面板
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