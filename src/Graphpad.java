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
	// 图形部分------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar graphpanel;
	Icon graphIcons[];
	String graphtext[] = { "画空心的矩形", "填充矩形", "画空心的椭圆", "填充椭圆", "画空心的圆", "填充圆", "画圆角矩形", "填充圆角矩形", "移动图形", "删除一个图形",
			"填充图形", "改变大小", "改变颜色" };
	String graphnames[] = { "rect", "frect", "oval", "foval", "circle", "fcircle", "roundrect", "froundrect", "move",
			"delete", "fill", "resize", "recolor" };
	JButton graphbutton[];

	JToolBar getoolbar() { // 供drawpad调用
		return graphpanel;
	}

	Graphpad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
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
		graphpanel.setBounds(1550, 10, 350, 280);
	}

	// 当按下按钮时,将按钮对应的编号传给画板类处理
	public void actionPerformed(ActionEvent e) {
		// 添加图形相关,从1开始编号(1-4在toolpad里,故从5开始编号)
		int cnt = 5;
		for (int i = 0; i <= 7; i++) { // 画图形相关
			if (e.getSource() == graphbutton[i]) {
				drawarea.chosenStatus = cnt;
				drawarea.createNewitem();
				drawarea.repaint();
			}
			cnt++;
		}

		// 更改图形相关,从21开始编号(21-22在toolpad里,故从23编号)
		cnt = 23;
		for (int i = 8; i < graphbutton.length; i++) { // 移动图形23,删除一个图形24,填充图形25, 改变大小26, 改变颜色27
			if (e.getSource() == graphbutton[i]) {
				drawarea.chosenStatus = cnt;
			}
			cnt++;
		}

	}
}