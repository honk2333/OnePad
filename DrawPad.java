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

//��������
public class DrawPad extends JFrame implements ActionListener {
	// ��ǩҳ����-------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar Labelpanel;
	String Labelfilename[];
	Icon LabelIcons[];
	String Labeltiptext[];
	JButton Labelbutton[];

	// ���߰�ť����------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar buttonpanel;// ���尴ť���
	String names[] = { "newfile", "openfile", "savefile", "pen", "eraser", "stroke", "word", "line", "linestroke",
			"undo", "redo" };// ���幤����ͼ�������
	Icon icons[];// ���尴ťͼ������
	String tiptext[] = { // ����������Ƶ���Ӧ�İ�ť�ϸ�����Ӧ����ʾ
			"�½�һ��ͼƬ", "��ͼƬ", "����ͼƬ", "��ʻ�", "��Ƥ��", "ѡ�������Ĵ�ϸ", "���ֵ�����", "��ֱ��", "��������", "����", "�ָ�" };
	JButton button[];// ���幤�����еİ�ť��

	// ͼ�β���------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar graphpanel;
	Icon graphIcons[];
	String graphtext[] = { "�����ĵľ���", "������", "�����ĵ���Բ", "�����Բ", "�����ĵ�Բ", "���Բ", "��Բ�Ǿ���", "���Բ�Ǿ���", "�ƶ�ͼ��", "ɾ��һ��ͼ��",
			"���ͼ��", "�ı��С", "�ı���ɫ" };
	String graphnames[] = { "rect", "frect", "oval", "foval", "circle", "fcircle", "roundrect", "froundrect", "move",
			"delete", "fill", "resize", "recolor" };
	JButton graphbutton[];

	// ���岿��----------------------------------------------------------------------------------------------------------------------------------------------
	String[] fontName;// ��������
	JCheckBox bold, italic, plain;// ����������ķ�񣨸�ѡ��
	JComboBox styles;// �������е��������ʽ�������б�

	// ��ɫ����----------------------------------------------------------------------------------------------------------------------------------------------
	JButton cbutton;
	JButton colorButton[];

	// ���岿��----------------------------------------------------------------------------------------------------------------------------------------------
	DrawArea drawarea;// ������Ķ���

	// �˵�����----------------------------------------------------------------------------------------------------------------------------------------------
	JMenuBar bar;// ����˵���
	FileClass fileclass;// �ļ�����
	JMenu file, color, stroke, help, edit, background;// ����˵�
	JMenuItem newfile, openfile, savefile, exit;// file �˵��еĲ˵���
	JMenuItem helpin, helpmain, colorchoice, strokeitem;// help �˵��еĲ˵���
	JMenuItem editgraph, editcolor, editstroke, edittext;// �༭�˵��е�ѡ��
	Icon nf, sf, of;// �ļ��˵����ͼ�����
	Help helpobject; // ����һ�����������

	DrawPad(String string) {
		super(string);

		// ��ǩҳ����-------------------------------------------------------------------------------------------------------------------------------------------
		Labelpanel = new JToolBar(JToolBar.HORIZONTAL);
		Labelpanel.setLayout(new GridLayout(1, 0, 0, 0));
		Labelpanel.setFloatable(false);

		// ���߰�ť����------------------------------------------------------------------------------------------------------------------------------------------
		// �������ĳ�ʼ��
		buttonpanel = new JToolBar(JToolBar.HORIZONTAL);// ����ˮƽ��������
		buttonpanel.setLayout(new GridLayout(1, 0, 0, 0));// ���ò���
		buttonpanel.setFloatable(false);// ���ɸ���

		icons = new ImageIcon[names.length];// ��ʼ���������е�ͼ��
		button = new JButton[names.length]; // �����������еİ�ť
		for (int i = 0; i < names.length; i++) {
			icons[i] = new ImageIcon("images/" + names[i] + ".png");// ���ͼƬ������·��Ϊ��׼��
			button[i] = new JButton(tiptext[i], icons[i]);// �����������еİ�ť
			button[i].setToolTipText(tiptext[i]);// ����ƶ�����Ӧ�İ�ť�ϸ�����Ӧ����ʾ
			buttonpanel.add(button[i]);// ��Ӱ�ť
			button[i].setBackground(Color.white);
			button[i].addActionListener(this);
		}

		// ͼ�β���------------------------------------------------------------------------------------------------------------------------------------------
		graphpanel = new JToolBar(JToolBar.HORIZONTAL);
		graphpanel.setLayout(new GridLayout(0, 3, 0, 0));
		graphpanel.setFloatable(false);
		graphIcons = new ImageIcon[graphnames.length];// ��ʼ���������е�ͼ��
		graphbutton = new JButton[graphnames.length]; // �����������еİ�ť
		for (int i = 0; i < graphnames.length; i++) {
			graphIcons[i] = new ImageIcon("images/" + graphnames[i] + ".png");// ���ͼƬ������·��Ϊ��׼��
			graphbutton[i] = new JButton(graphtext[i], graphIcons[i]);// �����������еİ�ť
			graphbutton[i].setToolTipText(graphtext[i]);// ����ƶ�����Ӧ�İ�ť�ϸ�����Ӧ����ʾ
			graphpanel.add(graphbutton[i]);// ��Ӱ�ť
			graphbutton[i].setBackground(Color.white);
			graphbutton[i].addActionListener(this);
		}

		// �˵�����----------------------------------------------------------------------------------------------------------------------------------------------
		file = new JMenu("�ļ�");
		edit = new JMenu("�༭");
		background = new JMenu("����");
		color = new JMenu("��ɫ");
		stroke = new JMenu("����");
		help = new JMenu("����");

		// ������Ӳ˵���
		bar = new JMenuBar();
		bar.add(file);
		bar.add(edit);
		bar.add(background);
		bar.add(color);
		bar.add(stroke);
		bar.add(help);
		setJMenuBar(bar);

		// �˵�����ӿ�ݼ�
		file.setMnemonic('F');
		edit.setMnemonic('E');
		background.setMnemonic('B');
		color.setMnemonic('C');
		stroke.setMnemonic('S');
		help.setMnemonic('H');

		// File
		nf = new ImageIcon("images/newfile.png");// ����ͼ��
		sf = new ImageIcon("images/savefile.png");
		of = new ImageIcon("images/openfile.png");
		newfile = new JMenuItem("�½�", nf);
		openfile = new JMenuItem("��", of);
		savefile = new JMenuItem("����", sf);
		exit = new JMenuItem("�˳�");
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
		colorchoice = new JMenuItem("��ɫ��");
		colorchoice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		colorchoice.addActionListener(this);
		color.add(colorchoice);

		// Help
		helpmain = new JMenuItem("��������");
		helpin = new JMenuItem("����OnePad");
		// Help
		helpin.addActionListener(this);
		helpmain.addActionListener(this);
		// Help
		help.add(helpmain);
		help.addSeparator();// ��ӷָ���
		help.add(helpin);

		// Stroke
		strokeitem = new JMenuItem("���û���");
		strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		stroke.add(strokeitem);
		strokeitem.addActionListener(this);

		// Edit
		editgraph = new JMenuItem("�༭ͼ��");
		editcolor = new JMenuItem("������ɫ");
		editstroke = new JMenuItem("��������");
		edittext = new JMenuItem("�༭����");
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

		// ���岿��----------------------------------------------------------------------------------------------------------------------------------------------
		CheckBoxHandler CHandler = new CheckBoxHandler();// ������ʽ������
		bold = new JCheckBox("����");
		bold.setFont(new Font(Font.DIALOG, Font.BOLD, 30));// ��������
		bold.addItemListener(CHandler);// boldע�����
		italic = new JCheckBox("б��");
		italic.setFont(new Font(Font.DIALOG, Font.ITALIC, 30));// ��������
		italic.addItemListener(CHandler);// italicע�����
		plain = new JCheckBox("����");
		plain.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		plain.addItemListener(CHandler);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();// �������������õ�����
		fontName = ge.getAvailableFontFamilyNames();
		styles = new JComboBox(fontName);// �����б�ĳ�ʼ��
		styles.addItemListener(CHandler);// stylesע�����
		styles.setMaximumSize(new Dimension(200, 50));// ���������б�����ߴ�
		styles.setMinimumSize(new Dimension(100, 40));
		styles.setFont(new Font(Font.DIALOG, Font.BOLD, 20));// ��������
		// �������ʽ����
		JToolBar fontpanel = new JToolBar("����ѡ��", JToolBar.HORIZONTAL);// ˮƽ����
		fontpanel.setLayout(new GridLayout(2, 3, 0, 0));
		fontpanel.setFloatable(false);
		fontpanel.add(bold);
		fontpanel.add(italic);
		fontpanel.add(plain);
		fontpanel.add(styles);

		// ��ɫ����----------------------------------------------------------------------------------------------------------------------------------------------
		Icon cicon = new ImageIcon("images/color.png");
		cbutton = new JButton("", cicon);
		cbutton.setToolTipText("�Զ�����ɫ");
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
		for (int i = 0; i < 20; i++) {// ����ɫ��ť����¼�
			int finalI = i;
			colorButton[i].addActionListener(e -> drawarea.setRGB(colorButton[finalI].getBackground().getRed(),
					colorButton[finalI].getBackground().getGreen(), colorButton[finalI].getBackground().getBlue()));
		}

		// ���岿��----------------------------------------------------------------------------------------------------------------------------------------------
		drawarea = new DrawArea(this);

		helpobject = new Help(this);
		fileclass = new FileClass(this, drawarea);

		// ���ø�����λ��----------------------------------------------------------------------------------------------------------------------------------------
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

	// �����°�ťʱ,����ť��Ӧ�ı�Ŵ��������ദ��
	public void actionPerformed(ActionEvent e) {

		// ��ť����������
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
		for (int i = 3; i <= 4; i++) { // ��ͼ�����
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
		if (e.getSource() == button[5] || e.getSource() == strokeitem) { // ���ô�ϸ���
			drawarea.chooseStroke();
		}
		cnt++;
		for (int i = 0; i < graphbutton.length - 5; i++) { // ��ͼ�����
			if (e.getSource() == graphbutton[i]) {
				drawarea.setChosenStatus(cnt);
				drawarea.createNewitem();
				drawarea.repaint();
			}
			cnt++;
		}
		// ͼ���޸Ĳ��ֱ��16-20
		for (int i = graphbutton.length - 5; i < graphbutton.length - 1; i++) {
			if (e.getSource() == graphbutton[i]) {
				drawarea.setChosenStatus(cnt);
			}
			cnt++;
		}
		// ����ͼ����ɫ20
		if (e.getSource() == graphbutton[graphbutton.length - 1] || e.getSource() == editcolor) {
			drawarea.setChosenStatus(cnt);
		}
		cnt++;
		// ��������21
		if (e.getSource() == button[8] || e.getSource() == editstroke) {
			drawarea.setChosenStatus(cnt);
		}
		cnt++;
		// �����ı���22
		if (e.getSource() == edittext) {
			drawarea.setChosenStatus(cnt);//
		}
		cnt++;
		// �����ͻָ�
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

	// ������ʽ�����ࣨ���塢б�塢�������ƣ�
	class CheckBoxHandler implements ItemListener {

		public void itemStateChanged(ItemEvent ie) {
			if (ie.getSource() == bold)// �������
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.BOLD);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == italic)// ����б��
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(2, Font.ITALIC);
				else
					drawarea.setFont(2, Font.PLAIN);

			} else if (ie.getSource() == plain)// ����
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.PLAIN);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == styles)// ���������
			{
				drawarea.style = fontName[styles.getSelectedIndex()];
			}
		}

	}
}
