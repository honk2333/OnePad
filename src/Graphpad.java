import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Graphpad extends JFrame implements ActionListener {
	DrawPad dp;
	FileClass fileclass;
	DrawArea drawarea;
	// ͼ�β���------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar graphpanel;
	Icon graphIcons[];
	String graphtext[] = { "�����ĵľ���", "������", "�����ĵ���Բ", "�����Բ", "�����ĵ�Բ", "���Բ", "��Բ�Ǿ���", "���Բ�Ǿ���", "�ƶ�ͼ��", "ɾ��һ��ͼ��",
			"���ͼ��", "�ı��С", "�ı���ɫ" };
	String graphnames[] = { "rect", "frect", "oval", "foval", "circle", "fcircle", "roundrect", "froundrect", "move",
			"delete", "fill", "resize", "recolor" };
	JButton graphbutton[];

	JToolBar getoolbar() { // ��drawpad����
		return graphpanel;
	}

	Graphpad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
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
		graphpanel.setBounds(1550, 10, 350, 280);
	}

	// �����°�ťʱ,����ť��Ӧ�ı�Ŵ��������ദ��
	public void actionPerformed(ActionEvent e) {
		// ���ͼ�����,��1��ʼ���(1-4��toolpad��,�ʴ�5��ʼ���)
		int cnt = 5;
		for (int i = 0; i <= 7; i++) { // ��ͼ�����
			if (e.getSource() == graphbutton[i]) {
				drawarea.chosenStatus = cnt;
				drawarea.createNewitem();
				drawarea.repaint();
			}
			cnt++;
		}

		// ����ͼ�����,��21��ʼ���(21-22��toolpad��,�ʴ�23���)
		cnt = 23;
		for (int i = 8; i < graphbutton.length; i++) { // �ƶ�ͼ��23,ɾ��һ��ͼ��24,���ͼ��25, �ı��С26, �ı���ɫ27
			if (e.getSource() == graphbutton[i]) {
				drawarea.chosenStatus = cnt;
			}
			cnt++;
		}

	}
}