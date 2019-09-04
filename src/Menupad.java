import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Menupad extends JFrame implements ActionListener {
	DrawPad dp;
	FileClass fileclass;
	DrawArea drawarea;
	// 标签页部分-------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar Labelpanel;
	String Labelfilename[];
	Icon LabelIcons[];
	String Labeltiptext[];
	JButton Labelbutton[];

	JToolBar getoolbar() {
		return Labelpanel;
	}

	// 菜单部分----------------------------------------------------------------------------------------------------------------------------------------------
	JMenuBar bar;// 定义菜单条
	JMenu file, color, stroke, help, edit, background;// 定义菜单
	JMenuItem newfile, openfile, savefile, exit;// file 菜单中的菜单项
	JMenuItem helpin, helpmain, colorchoice, strokeitem;// help 菜单中的菜单项
	JMenuItem editgraph, editcolor, editstroke, edittext;// 编辑菜单中的选项
	JMenuItem clear, changebackground;
	Icon nf, sf, of;// 文件菜单项的图标对象
	Help helpobject; // 定义一个帮助类对象

	JMenuBar getmenubar() {
		return bar;
	}

	Menupad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
		helpobject = new Help(dp);
		// 标签部分----------------------------------------------------------------------------------------------------------------------------------------------
		Labelpanel = new JToolBar(JToolBar.HORIZONTAL);
		Labelpanel.setLayout(new GridLayout(1, 0, 0, 0));
		Labelpanel.setFloatable(false);

		// 菜单部分----------------------------------------------------------------------------------------------------------------------------------------------
		file = new JMenu("文件");
		edit = new JMenu("编辑");
		background = new JMenu("背景");
		color = new JMenu("颜色");
		stroke = new JMenu("画笔");
		help = new JMenu("帮助");
		// 界面添加菜单条
		bar = new JMenuBar();
		bar.add(file);
		bar.add(edit);
		bar.add(background);
		bar.add(color);
		bar.add(stroke);
		bar.add(help);
		dp.setJMenuBar(bar);
		// 菜单中添加快捷键
		file.setMnemonic('F');
		edit.setMnemonic('E');
		background.setMnemonic('B');
		color.setMnemonic('C');
		stroke.setMnemonic('S');
		help.setMnemonic('H');

		// background菜单栏设置
		clear = new JMenuItem("清屏");
		changebackground = new JMenuItem("更改背景颜色");

		// 事件关联程序设置
		clear.addActionListener(this);
		changebackground.addActionListener(this);

		background.add(clear);
		background.add(changebackground);
		
		// File
		nf = new ImageIcon("images/newfile.png");// 创建图标
		sf = new ImageIcon("images/savefile.png");
		of = new ImageIcon("images/openfile.png");
		newfile = new JMenuItem("新建", nf);
		openfile = new JMenuItem("打开", of);
		savefile = new JMenuItem("保存", sf);
		exit = new JMenuItem("退出");
		// File快捷键设置
		newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		// File事件处理程序关联
		newfile.addActionListener(this);
		openfile.addActionListener(this);
		savefile.addActionListener(this);
		exit.addActionListener(this);

		// File
		file.add(newfile);
		file.add(openfile);
		file.add(savefile);
		file.add(exit);

		// Color
		colorchoice = new JMenuItem("调色板");
		colorchoice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		colorchoice.addActionListener(this);
		color.add(colorchoice);

		// Help
		helpmain = new JMenuItem("帮助");
		helpin = new JMenuItem("关于OnePad");
		// Help
		helpin.addActionListener(this);
		helpmain.addActionListener(this);
		// Help
		help.add(helpmain);
		help.addSeparator();// 添加分隔符
		help.add(helpin);

		// Stroke
		strokeitem = new JMenuItem("设置画笔粗细");
		strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		stroke.add(strokeitem);
		strokeitem.addActionListener(this);

		// Edit
		editgraph = new JMenuItem("更改图形大小");
		editcolor = new JMenuItem("更改图形颜色");
		editstroke = new JMenuItem("更改直线粗细");
		edittext = new JMenuItem("编辑文字内容");
		// Edit
		editgraph.addActionListener(this);
		editcolor.addActionListener(this);
		editstroke.addActionListener(this);
		edittext.addActionListener(this);
		// Edit
		edit.add(editgraph);
		edit.add(editcolor);
		edit.add(editstroke);
		edit.add(edittext);
	}

	// 当按下按钮时,将按钮对应的编号传给画板类处理
	public void actionPerformed(ActionEvent e) {
		// 按钮组件编号设置
		if (e.getSource() == strokeitem) { // 设置画笔粗细
			drawarea.chooseStroke();
		}
		if (e.getSource() == exit) {
			System.exit(0);
		}
		if (e.getSource() == colorchoice) {
			drawarea.chooseColor();
		}
		if (e.getSource() == clear) {
			drawarea.index = 0;
			drawarea.chosenStatus = 1;
			drawarea.createNewitem();
			drawarea.repaint();
		}
		if (e.getSource() == changebackground) {
			Color col = Color.white;
			try {
				col = JColorChooser.showDialog(dp, "请选择颜色", col); // 调用Java自带的选择颜色部件
			} catch (Exception exc) {
				col = Color.white;
			}
			drawarea.setBackground(col);
		}

		// 更改图形相关,从21开始编号
		if (e.getSource() == editcolor) { // 更改图形颜色27
			drawarea.chosenStatus = 27;
		}
		if (e.getSource() == edittext) { // 更改文本框21
			drawarea.chosenStatus = 21;
		}
		if (e.getSource() == helpin) {
			helpobject.AboutBook();
		}
		if (e.getSource() == helpmain) {
			helpobject.MainHelp();
		}

	}
}