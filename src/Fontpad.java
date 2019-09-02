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

	// 字体部分----------------------------------------------------------------------------------------------------------------------------------------------
	String[] fontName;// 字体名称
	JCheckBox bold, italic, plain;// 工具条字体的风格（复选框）
	JComboBox styles;// 工具条中的字体的样式（下拉列表）
	JToolBar fontpanel;

	JToolBar getoolbar() { // 供drawpad调用
		return fontpanel;
	}

	Fontpad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
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
		fontpanel = new JToolBar("字体选项", JToolBar.HORIZONTAL);// 水平方向
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

	// 字体样式处理类（粗体、斜体、字体名称）
	class CheckBoxHandler implements ItemListener {
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getSource() == bold) { // 字体粗体
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.BOLD);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == italic) { // 字体斜体
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(2, Font.ITALIC);
				else
					drawarea.setFont(2, Font.PLAIN);

			} else if (ie.getSource() == plain) { // 普通字体
				if (ie.getStateChange() == ItemEvent.SELECTED)
					drawarea.setFont(1, Font.PLAIN);
				else
					drawarea.setFont(1, Font.PLAIN);
			} else if (ie.getSource() == styles) {// 字体的名称
				drawarea.style = fontName[styles.getSelectedIndex()];
			}
		}
	}
}
