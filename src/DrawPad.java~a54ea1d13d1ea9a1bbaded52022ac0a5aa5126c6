import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

//主界面类
public class DrawPad extends JFrame implements ActionListener {
	// 标签页部分-------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar Labelpanel;
	String Labelfilename[];
	Icon LabelIcons[];
	String Labeltiptext[];
	JButton Labelbutton[];

	// 工具按钮部分------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar buttonpanel;// 定义按钮面板
	String names[] = { "newfile", "openfile", "savefile", "pen", "eraser", "stroke", "word", "line", "linestroke",
			"undo", "redo" };// 定义工具栏图标的名称
	Icon icons[];// 定义按钮图标数组
	String tiptext[] = { // 这里是鼠标移到相应的按钮上给出相应的提示
			"新建一个图片", "打开图片", "保存图片", "随笔画", "橡皮擦", "选择线条的粗细", "文字的输入", "画直线", "更改线型", "撤销", "恢复" };
	JButton button[];// 定义工具条中的按钮组

	// 图形部分------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar graphpanel;
	Icon graphIcons[];
	String graphtext[] = { "画空心的矩形", "填充矩形", "画空心的椭圆", "填充椭圆", "画空心的圆", "填充圆", "画圆角矩形", "填充圆角矩形", "移动图形", "删除一个图形",
			"填充图形", "改变大小", "改变颜色" };
	String graphnames[] = { "rect", "frect", "oval", "foval", "circle", "fcircle", "roundrect", "froundrect", "move",
			"delete", "fill", "resize", "recolor" };
	JButton graphbutton[];

	// 字体部分----------------------------------------------------------------------------------------------------------------------------------------------
	String[] fontName;// 字体名称
	JCheckBox bold, italic, plain;// 工具条字体的风格（复选框）
	JComboBox styles;// 工具条中的字体的样式（下拉列表）

	// 调色部分----------------------------------------------------------------------------------------------------------------------------------------------
	JButton cbutton;
	JButton colorButton[];

	// 画板部分----------------------------------------------------------------------------------------------------------------------------------------------
	DrawArea drawarea;// 画布类的定义

	// 菜单部分----------------------------------------------------------------------------------------------------------------------------------------------
	JMenuBar bar;// 定义菜单条
	FileClass fileclass;// 文件对象
	JMenu file, color, stroke, help, edit, background;// 定义菜单
	JMenuItem newfile, openfile, savefile, exit;// file 菜单中的菜单项
	JMenuItem helpin, helpmain, colorchoice, strokeitem;// help 菜单中的菜单项
	JMenuItem editgraph, editcolor, editstroke, edittext;// 编辑菜单中的选项
	Icon nf, sf, of;// 文件菜单项的图标对象
	Help helpobject; // 定义一个帮助类对象

	DrawPad(String string) {
		super(string);

		// 标签页部分-------------------------------------------------------------------------------------------------------------------------------------------
		Labelpanel = new JToolBar(JToolBar.HORIZONTAL);
		Labelpanel.setLayout(new GridLayout(1, 0, 0, 0));
		Labelpanel.setFloatable(false);

		// 工具按钮部分------------------------------------------------------------------------------------------------------------------------------------------
		// 工具栏的初始化
		buttonpanel = new JToolBar(JToolBar.HORIZONTAL);// 设置水平方向排列
		buttonpanel.setLayout(new GridLayout(1, 0, 0, 0));// 设置布局
		buttonpanel.setFloatable(false);// 不可浮动

		icons = new ImageIcon[names.length];// 初始化工具栏中的图标
		button = new JButton[names.length]; // 创建工具栏中的按钮
		for (int i = 0; i < names.length; i++) {
			icons[i] = new ImageIcon("images/" + names[i] + ".png");// 获得图片（以类路径为基准）
			button[i] = new JButton(tiptext[i], icons[i]);// 创建工具条中的按钮
			button[i].setToolTipText(tiptext[i]);// 鼠标移动到相应的按钮上给出相应的提示
			buttonpanel.add(button[i]);// 添加按钮
			button[i].setBackground(Color.white);
			button[i].addActionListener(this);
		}

		// 图形部分------------------------------------------------------------------------------------------------------------------------------------------
		graphpanel = new JToolBar(JToolBar.HORIZONTAL);
		graphpanel.setLayout(new GridLayout(0, 3, 0, 0));
		graphpanel.setFloatable(false);
		graphIcons = new ImageIcon[graphnames.length];// 初始化工具栏中的图标
		graphbutton = new JButton[graphnames.length]; // 创建工具栏中的按钮
		for (int i = 0; i < graphnames.length; i++) {
			graphIcons[i] = new ImageIcon("images/" + graphnames[i] + ".png");// 获得图片（以类路径为基准）
			graphbutton[i] = new JButton(graphtext[i], graphIcons[i]);// 创建工具条中的按钮
			graphbutton[i].setToolTipText(graphtext[i]);// 鼠标移动到相应的按钮上给出相应的提示
			graphpanel.add(graphbutton[i]);// 添加按钮
			graphbutton[i].setBackground(Color.white);
			graphbutton[i].addActionListener(this);
		}

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
		setJMenuBar(bar);

		// 菜单中添加快捷键
		file.setMnemonic('F');
		edit.setMnemonic('E');
		background.setMnemonic('B');
		color.setMnemonic('C');
		stroke.setMnemonic('S');
		help.setMnemonic('H');

		// File
		nf = new ImageIcon("images/newfile.png");// 创建图标
		sf = new ImageIcon("images/savefile.png");
		of = new ImageIcon("images/openfile.png");
		newfile = new JMenuItem("新建", nf);
		openfile = new JMenuItem("打开", of);
		savefile = new JMenuItem("保存", sf);
		exit = new JMenuItem("退出");
		// File
		newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		// File
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
		helpmain = new JMenuItem("帮助主题");
		helpin = new JMenuItem("关于OnePad");
		// Help
		helpin.addActionListener(this);
		helpmain.addActionListener(this);
		// Help
		help.add(helpmain);
		help.addSeparator();// 添加分隔符
		help.add(helpin);

		// Stroke
		strokeitem = new JMenuItem("设置画笔");
		strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		stroke.add(strokeitem);
		strokeitem.addActionListener(this);

		// Edit
		editgraph = new JMenuItem("编辑图形");
		editcolor = new JMenuItem("更改颜色");
		editstroke = new JMenuItem("更改线型");
		edittext = new JMenuItem("编辑文字");
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

		// 字体部分----------------------------------------------------------------------------------------------------------------------------------------------
		CheckBoxHandler CHandler = new CheckBoxHandler();// 字体样式处理类
		bold = new JCheckBox("粗体");
		bold.setFont(new Font(Font.DIALOG, Font.BOLD, 30));// 设置字体
		bold.addItemListener(CHandler);// bold注册监听
		italic = new JCheckBox("斜体");
		italic.setFont(new Font(Font.DIALOG, Font.ITALIC, 30));// 设置字体
		italic.addItemListener(CHandler);// italic注册监听
		plain = new JCheckBox("常规");
		plain.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		plain.addItemListener(CHandler);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();// 计算机上字体可用的名称
		fontName = ge.getAvailableFontFamilyNames();
		styles = new JComboBox(fontName);// 下拉列表的初始化
		styles.addItemListener(CHandler);// styles注册监听
		styles.setMaximumSize(new Dimension(200, 50));// 设置下拉列表的最大尺寸
		styles.setMinimumSize(new Dimension(100, 40));
		styles.setFont(new Font(Font.DIALOG, Font.BOLD, 20));// 设置字体
		// 添加字体式样栏
		JToolBar fontpanel = new JToolBar("字体选项", JToolBar.HORIZONTAL);// 水平方向
		fontpanel.setLayout(new GridLayout(2, 3, 0, 0));
		fontpanel.setFloatable(false);
		fontpanel.add(bold);
		fontpanel.add(italic);
		fontpanel.add(plain);
		fontpanel.add(styles);

		// 调色部分----------------------------------------------------------------------------------------------------------------------------------------------
		Icon cicon = new ImageIcon("images/color.png");
		cbutton = new JButton("", cicon);
		cbutton.setToolTipText("自定义颜色");
		cbutton.addActionListener(this);

		colorButton = new JButton[20];//
		JToolBar colorButtonPanel = new JToolBar(JToolBar.VERTICAL);//
		colorButtonPanel.setLayout(new GridLayout(0, 5, 0, 0));
		colorButtonPanel.setFloatable(false);//
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
		for (int i = 0; i < 20; i++) {// 给颜色按钮添加事件
			int finalI = i;
			colorButton[i].addActionListener(e -> drawarea.setRGB(colorButton[finalI].getBackground().getRed(),
					colorButton[finalI].getBackground().getGreen(), colorButton[finalI].getBackground().getBlue()));
		}

		// 画板部分----------------------------------------------------------------------------------------------------------------------------------------------
		drawarea = new DrawArea(this);

		helpobject = new Help(this);
		fileclass = new FileClass(this, drawarea);

		// 设置各部分位置----------------------------------------------------------------------------------------------------------------------------------------
		Container con = getContentPane();//
		Toolkit tool = getToolkit();//
		Dimension dim = tool.getScreenSize();//
		con.setLayout(null);

		buttonpanel.setBounds(30, 10, 1500, 80);//
		graphpanel.setBounds(1550, 10, 350, 280);
		drawarea.setBounds(20, 100, dim.width - 400, dim.height - 200);
		colorButtonPanel.setBounds(dim.width - 350, dim.height - 500, 300, 300);
		fontpanel.setBounds(dim.width - 350, 350, 300, 75);
		cbutton.setBounds(dim.width - 350, dim.height - 600, 300, 100);
		con.add(buttonpanel);//
		con.add(graphpanel);
		con.add(drawarea);
		con.add(colorButtonPanel);
		con.add(fontpanel);
		con.add(cbutton);
		con.add(drawarea.tarea);
		setBounds(0, 0, dim.width, dim.height);
		setVisible(true);
		validate();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	// 当按下按钮时,将按钮对应的编号传给画板类处理
	public void actionPerformed(ActionEvent e) {

		// 按钮组件编号设置
		if (e.getSource() == newfile || e.getSource() == button[0]) {
			fileclass.newFile();
		}
		if (e.getSource() == openfile || e.getSource() == button[1]) {
			fileclass.openFile();
		}
		if (e.getSource() == savefile || e.getSource() == button[2]) {
			fileclass.saveFile();
		}
		int cnt = 3;
		for (int i = 3; i <= 4; i++) { // 画图形相关
			if (e.getSource() == button[i]) {
				drawarea.setChosenStatus(cnt);
				drawarea.createNewitem();
				drawarea.repaint();
			}
			cnt++;
		}
		for (int i = 6; i <= 7; i++) {
			if (e.getSource() == button[i]) {
				drawarea.setChosenStatus(cnt);
				drawarea.createNewitem();
				drawarea.repaint();
			}
			cnt++;
		}
		if (e.getSource() == button[5] || e.getSource() == strokeitem) { // 设置粗细相关
			drawarea.chooseStroke();
		}
		cnt++;
		for (int i = 0; i < graphbutton.length - 5; i++) { // 画图形相关
			if (e.getSource() == graphbutton[i]) {
				drawarea.setChosenStatus(cnt);
				drawarea.createNewitem();
				drawarea.repaint();
			}
			cnt++;
		}
		// 图形修改部分编号16-20
		for (int i = graphbutton.length - 5; i < graphbutton.length - 1; i++) {
			if (e.getSource() == graphbutton[i]) {
				drawarea.setChosenStatus(cnt);
			}
			cnt++;
		}
		// 更改图形颜色20
		if (e.getSource() == graphbutton[graphbutton.length - 1] || e.getSource() == editcolor) {
			drawarea.setChosenStatus(cnt);
		}
		cnt++;
		// 更改线型21
		if (e.getSource() == button[8] || e.getSource() == editstroke) {
			drawarea.setChosenStatus(cnt);
		}
		cnt++;
		// 更改文本框22
		if (e.getSource() == edittext) {
			drawarea.setChosenStatus(cnt);//
		}
		cnt++;
		// 撤销和恢复
		if (e.getSource() == button[9]) {
			drawarea.setChosenStatus(cnt);
			drawarea.setIndex(drawarea.getIndex() - 1);
			drawarea.repaint();
		}
		cnt++;
		if (e.getSource() == button[10]) {
			drawarea.setChosenStatus(cnt);
			drawarea.setIndex(drawarea.getIndex() + 1);
			drawarea.repaint();
		}
		cnt++;
		if (e.getSource() == exit) {
			System.exit(0);
		}
		if (e.getSource() == cbutton || e.getSource() == colorchoice) {
			drawarea.chooseColor();
		}
		if (e.getSource() == helpin) {
			helpobject.AboutBook();
		}
		if (e.getSource() == helpmain) {
			helpobject.MainHelp();
		}

	}

	// 字体样式处理类（粗体、斜体、字体名称）
	class CheckBoxHandler implements ItemListener {

		public void itemStateChanged(ItemEvent ie) {
			if (ie.getSource() == bold)// 字体粗体
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.BOLD);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == italic)// 字体斜体
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(2, Font.ITALIC);
				else
					drawarea.setFont(2, Font.PLAIN);

			} else if (ie.getSource() == plain)// 字体
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.PLAIN);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == styles)// 字体的名称
			{
				drawarea.style = fontName[styles.getSelectedIndex()];
			}
		}

	}
}
