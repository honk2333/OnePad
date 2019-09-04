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
	// ��ǩҳ����-------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar Labelpanel;
	String Labelfilename[];
	Icon LabelIcons[];
	String Labeltiptext[];
	JButton Labelbutton[];

	JToolBar getoolbar() {
		return Labelpanel;
	}

	// �˵�����----------------------------------------------------------------------------------------------------------------------------------------------
	JMenuBar bar;// ����˵���
	JMenu file, color, stroke, help, edit, background;// ����˵�
	JMenuItem newfile, openfile, savefile, exit;// file �˵��еĲ˵���
	JMenuItem helpin, helpmain, colorchoice, strokeitem;// help �˵��еĲ˵���
	JMenuItem editgraph, editcolor, editstroke, edittext;// �༭�˵��е�ѡ��
	JMenuItem clear, changebackground;
	Icon nf, sf, of;// �ļ��˵����ͼ�����
	Help helpobject; // ����һ�����������

	JMenuBar getmenubar() {
		return bar;
	}

	Menupad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
		helpobject = new Help(dp);
		// ��ǩ����----------------------------------------------------------------------------------------------------------------------------------------------
		Labelpanel = new JToolBar(JToolBar.HORIZONTAL);
		Labelpanel.setLayout(new GridLayout(1, 0, 0, 0));
		Labelpanel.setFloatable(false);

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
		dp.setJMenuBar(bar);
		// �˵�����ӿ�ݼ�
		file.setMnemonic('F');
		edit.setMnemonic('E');
		background.setMnemonic('B');
		color.setMnemonic('C');
		stroke.setMnemonic('S');
		help.setMnemonic('H');

		// background�˵�������
		clear = new JMenuItem("����");
		changebackground = new JMenuItem("���ı�����ɫ");

		// �¼�������������
		clear.addActionListener(this);
		changebackground.addActionListener(this);

		background.add(clear);
		background.add(changebackground);
		
		// File
		nf = new ImageIcon("images/newfile.png");// ����ͼ��
		sf = new ImageIcon("images/savefile.png");
		of = new ImageIcon("images/openfile.png");
		newfile = new JMenuItem("�½�", nf);
		openfile = new JMenuItem("��", of);
		savefile = new JMenuItem("����", sf);
		exit = new JMenuItem("�˳�");
		// File��ݼ�����
		newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		// File�¼�����������
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
		helpmain = new JMenuItem("����");
		helpin = new JMenuItem("����OnePad");
		// Help
		helpin.addActionListener(this);
		helpmain.addActionListener(this);
		// Help
		help.add(helpmain);
		help.addSeparator();// ��ӷָ���
		help.add(helpin);

		// Stroke
		strokeitem = new JMenuItem("���û��ʴ�ϸ");
		strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		stroke.add(strokeitem);
		strokeitem.addActionListener(this);

		// Edit
		editgraph = new JMenuItem("����ͼ�δ�С");
		editcolor = new JMenuItem("����ͼ����ɫ");
		editstroke = new JMenuItem("����ֱ�ߴ�ϸ");
		edittext = new JMenuItem("�༭��������");
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

	// �����°�ťʱ,����ť��Ӧ�ı�Ŵ��������ദ��
	public void actionPerformed(ActionEvent e) {
		// ��ť����������
		if (e.getSource() == strokeitem) { // ���û��ʴ�ϸ
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
				col = JColorChooser.showDialog(dp, "��ѡ����ɫ", col); // ����Java�Դ���ѡ����ɫ����
			} catch (Exception exc) {
				col = Color.white;
			}
			drawarea.setBackground(col);
		}

		// ����ͼ�����,��21��ʼ���
		if (e.getSource() == editcolor) { // ����ͼ����ɫ27
			drawarea.chosenStatus = 27;
		}
		if (e.getSource() == edittext) { // �����ı���21
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