import java.awt.Color;
import java.awt.Container;
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

	private static final long serialVersionUID = -2551980583852173918L;
	private JToolBar buttonpanel;// 定义按钮面板
	private JMenuBar bar;// 定义菜单条
	private JMenu file, color, stroke, help, edit,background;// 定义菜单
	private JMenuItem newfile, openfile, savefile, exit;// file 菜单中的菜单项
	private JMenuItem helpin, helpmain, colorchoice, strokeitem;// help 菜单中的菜单项
	private JMenuItem editgraph, editcolor, editstroke, edittext;// 编辑菜单中的选项
	private Icon nf, sf, of;// 文件菜单项的图标对象
	private JLabel startbar;// 状态栏
	DrawArea drawarea;// 画布类的定义
	private Help helpobject; // 定义一个帮助类对象
	private FileClass fileclass;// 文件对象
	String[] fontName;// 字体名称
	private JCheckBox bold, italic, plain;// 工具条字体的风格（复选框）
	private JComboBox styles;// 工具条中的字体的样式（下拉列表）

	private String names[] = { "newfile", "openfile", "savefile", "pen", "line", "rect", "frect", "oval", "foval",
			"circle", "fcircle", "roundrect", "froundrect", "word", "stroke", "delete", "move", "fill" };// 定义工具栏图标的名称

	private Icon icons[];// 定义按钮图标数组

	private String tiptext[] = { // 这里是鼠标移到相应的按钮上给出相应的提示
			"新建一个图片", "打开图片", "保存图片", "随笔画", "画直线", "画空心的矩形", "填充矩形", "画空心的椭圆", "填充椭圆", "画空心的圆", "填充圆", "画圆角矩形",
			"填充圆角矩形", "文字的输入", "选择线条的粗细", "删除一个图形", "移动图形", "填充图形" };

	JButton button[];// 定义工具条中的按钮组

	DrawPad(String string) {
		super(string);
		file = new JMenu("文件");
		edit = new JMenu("编辑");
		background = new JMenu("背景");
		color = new JMenu("颜色");
		stroke = new JMenu("画笔");
		help = new JMenu("帮助");
     
		
		bar = new JMenuBar();
		bar.add(file);
		bar.add(edit);
		bar.add(background);
		bar.add(color);
		bar.add(stroke);
		bar.add(help);

		// 界面添加菜单条
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
		file.add(newfile);
		file.add(openfile);
		file.add(savefile);
		file.add(exit);

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

		// Color
		colorchoice = new JMenuItem("调色板");
		colorchoice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		colorchoice.addActionListener(this);
		color.add(colorchoice);

		// Help
		helpmain = new JMenuItem("帮助主题");
		helpin = new JMenuItem("关于OnePad");

		// Help
		help.add(helpmain);
		help.addSeparator();// 娣诲姞鍒嗗壊绾�
		help.add(helpin);

		// Help
		helpin.addActionListener(this);
		helpmain.addActionListener(this);

		// Stroke
		strokeitem = new JMenuItem("设置画笔");
		strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		stroke.add(strokeitem);
		strokeitem.addActionListener(this);

		// Edit鑿滃崟椤瑰垵濮嬪寲
		editgraph = new JMenuItem("编辑图形");
		editcolor = new JMenuItem("更改颜色");
		editstroke = new JMenuItem("更改线型");
		edittext = new JMenuItem("编辑文字");

		// Edit
		edit.add(editgraph);
		edit.add(editcolor);
		edit.add(editstroke);
		edit.add(edittext);

		// Edit
		editgraph.addActionListener(this);
		editcolor.addActionListener(this);
		editstroke.addActionListener(this);
		edittext.addActionListener(this);

		// 工具栏的初始化
		buttonpanel = new JToolBar(JToolBar.HORIZONTAL);// 设置水平方向排列
		buttonpanel.setLayout(new GridLayout(2, 0, 0, 0));// 设置布局
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

		// 状态栏
		startbar = new JLabel("OnePad");

		// 閫夎壊鍖哄垵濮嬪寲
		Icon cicon = new ImageIcon("images/color.png");// 鑷畾涔夐鑹叉寜閽�
		JButton cbutton = new JButton("", cicon);
		cbutton.setToolTipText("鑷畾涔夐鑹�");
		cbutton.addActionListener(e -> drawarea.chooseColor());
		JButton colorButton[] = new JButton[20];// 鍙傝�冮鑹叉寜閽�
		JToolBar colorButtonPanel = new JToolBar(JToolBar.VERTICAL);// 鍙傝�冮鑹叉爮鍒濆鍖�
		colorButtonPanel.setLayout(new GridLayout(6, 5, 5, 5));
		colorButtonPanel.setFloatable(false);// 涓嶅彲娴姩
		for (int i = 0; i < 20; i++) {
			colorButton[i] = new JButton("");// 鍒涘缓棰滆壊鏍忎腑鐨勬寜閽�
			colorButtonPanel.add(colorButton[i]);
		}
		// 20涓弬鑰冮鑹叉寜閽垵濮嬪寲
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
		for (int i = 0; i < 20; i++) {// 缁欏弬鑰冮鑹叉寜閽坊鍔犱簨浠讹紝鎸変笅鏃跺皢褰撳墠棰滆壊璁剧疆鎴愪笌閫変腑鍙傝�冮鑹茬浉鍚�
			int finalI = i;
			colorButton[i].addActionListener(e -> drawarea.colorBar(colorButton[finalI].getBackground().getRed(),
					colorButton[finalI].getBackground().getGreen(), colorButton[finalI].getBackground().getBlue()));
		}

		// 缁樼敾鍖虹殑鍒濆鍖�
		drawarea = new DrawArea(this);
		helpobject = new Help(this);
		fileclass = new FileClass(this, drawarea);

		Container con = getContentPane();// 寰楀埌鍐呭闈㈡澘
		Toolkit tool = getToolkit();// 寰楀埌涓�涓猅olkit绫荤殑瀵硅薄锛堜富瑕佺敤浜庡緱鍒板睆骞曠殑澶у皬锛�
		Dimension dim = tool.getScreenSize();// 寰楀埌灞忓箷鐨勫ぇ灏� 锛堣繑鍥濪imension瀵硅薄锛�
		con.setLayout(null);
		buttonpanel.setBounds(0, 0, 1000, 75);// 缁欏悇宸ュ叿鏍忓畨鎺掍綅缃�
		startbar.setBounds(dim.width - 300, dim.height - 150, 300, 100);
		drawarea.setBounds(0, 75, dim.width - 400, dim.height - 400);
		colorButtonPanel.setBounds(dim.width - 350, dim.height - 400, 300, 300);
		fontpanel.setBounds(dim.width - 850, 0, 300, 75);
		cbutton.setBounds(dim.width - 350, dim.height - 500, 300, 100);
		con.add(buttonpanel);// 灏嗗悇宸ュ叿鏍忔坊鍔犲埌涓婚潰鏉垮唴
		con.add(drawarea);
		con.add(startbar);
		con.add(colorButtonPanel);
		con.add(fontpanel);
		con.add(cbutton);
		con.add(drawarea.tarea);
		setBounds(0, 0, dim.width, dim.height);
		setVisible(true);
		validate();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	// 璁剧疆鐘舵�佹爮鏄剧ず鐨勫瓧绗�
	void setStratBar(String s) {
		startbar.setText(s);
	}

	// 浜嬩欢鐨勫鐞�
	public void actionPerformed(ActionEvent e) {
		for (int i = 3; i <= 13; i++) {// 鐢诲悇绫诲浘褰�
			if (e.getSource() == button[i]) {
				drawarea.setChosenStatus(i);
				drawarea.createNewitem();
				drawarea.repaint();
			}
		}
		if (e.getSource() == button[14] || e.getSource() == strokeitem) {
			drawarea.setStroke();// 鐢荤瑪绮楃粏鐨勮皟鏁�
		} else if (e.getSource() == button[15]) {
			drawarea.setChosenStatus(15);// 鍒犻櫎涓�涓浘褰�
		} else if (e.getSource() == button[16]) {
			drawarea.setChosenStatus(16);// 鎷栧姩鍥惧舰
		} else if (e.getSource() == editgraph) {
			drawarea.setChosenStatus(17);// 鏀瑰彉宸叉湁鍥惧舰澶у皬
		} else if (e.getSource() == editcolor) {
			drawarea.setChosenStatus(18);// 鏀瑰彉宸叉湁鍥惧舰棰滆壊
		} else if (e.getSource() == editstroke) {
			drawarea.setChosenStatus(19);// 鏀瑰彉宸叉湁鍥惧舰绾垮瀷
		} else if (e.getSource() == button[17]) {
			drawarea.setChosenStatus(20);// 濉厖鍥剧墖
		} else if (e.getSource() == edittext) {
			drawarea.setChosenStatus(21);// 缂栬緫宸茶緭鍏ョ殑鏂囧瓧
		} else if (e.getSource() == newfile || e.getSource() == button[0]) {
			fileclass.newFile();// 鏂板缓
		} else if (e.getSource() == openfile || e.getSource() == button[1]) {
			fileclass.openFile();// 鎵撳紑
		} else if (e.getSource() == savefile || e.getSource() == button[2]) {
			fileclass.saveFile();// 淇濆瓨
		} else if (e.getSource() == exit) {
			System.exit(0);// 閫�鍑虹▼搴�
		} else if (e.getSource() == colorchoice) {
			drawarea.chooseColor();// 棰滆壊鐨勯�夋嫨
		} else if (e.getSource() == helpin) {
			helpobject.AboutBook();// 甯姪淇℃伅
		} else if (e.getSource() == helpmain) {
			helpobject.MainHelp();// 甯姪涓婚
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
