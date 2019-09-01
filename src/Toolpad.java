import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Toolpad extends JFrame implements ActionListener {
	DrawPad dp;
	FileClass fileclass;
	DrawArea drawarea;
	// ���߰�ť����------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar buttonpanel;// ���尴ť���
	String names[] = { "newfile", "openfile", "savefile", "pen", "eraser", "stroke", "word", "exitword", "line",
			"linestroke", "undo", "redo" };// ���幤����ͼ�������
	Icon icons[];// ���尴ťͼ������
	String tiptext[] = { // ����������Ƶ���Ӧ�İ�ť�ϸ�����Ӧ����ʾ
			"�½�һ��ͼƬ", "��ͼƬ", "����ͼƬ", "��ʻ�", "��Ƥ��", "ѡ�������Ĵ�ϸ", "���ֵ�����", "�ı���༭", "��ֱ��", "��������", "����", "�ָ�" };
	JButton button[];// ���幤�����еİ�ť��

	JToolBar getoolbar() { // ��drawpad����
		return buttonpanel;
	}

	Toolpad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
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
			button[i].addActionListener(this);// �����¼��������
			button[i].setBackground(Color.white);
		}
		buttonpanel.setBounds(30, 10, 1500, 80);
		// buttonpanel.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// ��ť����������
		if (e.getSource() == button[0]) {
			fileclass.newFile();
		}
		if (e.getSource() == button[1]) {
			fileclass.openFile();
		}
		if (e.getSource() == button[2]) {
			fileclass.saveFile();
		}
		if (e.getSource() == button[5]) { // ���û��ʵĴ�ϸ
			drawarea.chooseStroke();
		}
		if (e.getSource() == button[10]) {// �����ͻָ�
			drawarea.index = drawarea.index - 1;
			drawarea.repaint();
		}
		if (e.getSource() == button[11]) {
			drawarea.index = drawarea.index + 1;
			drawarea.repaint();
		}

		// ����ͼ�����,��21��ʼ���
		if (e.getSource() == button[7]) { // �����ı���21
			drawarea.chosenStatus=21;
		}
		if (e.getSource() == button[9]) { // ��������22
			drawarea.chosenStatus=22;
		}

		// ���ͼ�����,��1��ʼ���
		if (e.getSource() == button[3]) { // ��ʻ�1
			drawarea.chosenStatus = 1;
			drawarea.createNewitem();
			drawarea.repaint();
		}
		if (e.getSource() == button[4]) { // ��Ƥ��2
			drawarea.chosenStatus = 2;
			drawarea.createNewitem();
			drawarea.repaint();
		}
		if (e.getSource() == button[6]) { // ����ı���3
			drawarea.chosenStatus = 3;
			drawarea.createNewitem();
			drawarea.repaint();
		}
		if (e.getSource() == button[8]) { // ���ֱ��4
			drawarea.chosenStatus = 4;
			drawarea.createNewitem();
			drawarea.repaint();
		}

	}

}