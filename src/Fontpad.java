import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Fontpad extends JFrame {
	DrawPad dp;
	FileClass fileclass;
	DrawArea drawarea;

	// ���岿��----------------------------------------------------------------------------------------------------------------------------------------------
	String[] fontName;// ��������
	JCheckBox bold, italic, plain;// ����������ķ�񣨸�ѡ��
	JComboBox styles;// �������е��������ʽ�������б�
	JToolBar fontpanel;

	JToolBar getoolbar() { // ��drawpad����
		return fontpanel;
	}

	Fontpad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
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
		fontpanel = new JToolBar("����ѡ��", JToolBar.HORIZONTAL);// ˮƽ����
		fontpanel.setLayout(new GridLayout(0, 3, 0, 0));
		fontpanel.setFloatable(false);
		fontpanel.add(bold);
		fontpanel.add(italic);
		fontpanel.add(plain);
		fontpanel.add(styles);
		Toolkit tool = getToolkit();
		Dimension dim = tool.getScreenSize();
		fontpanel.setBounds(1350, 350, 350, 100);
	}

	// ������ʽ�����ࣨ���塢б�塢�������ƣ�
	class CheckBoxHandler implements ItemListener {
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getSource() == bold) { // �������
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.BOLD);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == italic) { // ����б��
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(2, Font.ITALIC);
				else
					drawarea.setFont(2, Font.PLAIN);

			} else if (ie.getSource() == plain) { // ��ͨ����
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.PLAIN);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == styles) {// ���������
				drawarea.style = fontName[styles.getSelectedIndex()];
			}
		}
	}
}
