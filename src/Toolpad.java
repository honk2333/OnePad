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
	// 工具按钮部分------------------------------------------------------------------------------------------------------------------------------------------
	JToolBar buttonpanel;// 定义按钮面板
	String names[] = { "newfile", "openfile", "savefile", "pen", "eraser", "stroke", "word", "exitword", "line",
			"linestroke", "undo", "redo" };// 定义工具栏图标的名称
	Icon icons[];// 定义按钮图标数组
	String tiptext[] = { // 这里是鼠标移到相应的按钮上给出相应的提示
			"新建一个图片", "打开图片", "保存图片", "随笔画", "橡皮擦", "选择线条的粗细", "文字的输入", "文本框编辑", "画直线", "更改线型", "撤销", "恢复" };
	JButton button[];// 定义工具条中的按钮组

	JToolBar getoolbar() { // 供drawpad调用
		return buttonpanel;
	}

	Toolpad(DrawPad drawpad) {
		dp = drawpad;
		fileclass = dp.GetFileClass();
		drawarea = dp.GetDrawArea();
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
			button[i].addActionListener(this);// 关联事件处理程序
			button[i].setBackground(Color.white);
		}
		buttonpanel.setBounds(30, 10, 1500, 80);
		// buttonpanel.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// 按钮组件编号设置
		if (e.getSource() == button[0]) {
			fileclass.newFile();
		}
		if (e.getSource() == button[1]) {
			fileclass.openFile();
		}
		if (e.getSource() == button[2]) {
			fileclass.saveFile();
		}
		if (e.getSource() == button[5]) { // 设置画笔的粗细
			drawarea.chooseStroke();
		}
		if (e.getSource() == button[10]) {// 撤销和恢复
			drawarea.index = drawarea.index - 1;
			drawarea.repaint();
		}
		if (e.getSource() == button[11]) {
			drawarea.index = drawarea.index + 1;
			drawarea.repaint();
		}

		// 更改图形相关,从21开始编号
		if (e.getSource() == button[7]) { // 更改文本框21
			drawarea.chosenStatus=21;
		}
		if (e.getSource() == button[9]) { // 更改线型22
			drawarea.chosenStatus=22;
		}

		// 添加图形相关,从1开始编号
		if (e.getSource() == button[3]) { // 随笔画1
			drawarea.chosenStatus = 1;
			drawarea.createNewitem();
			drawarea.repaint();
		}
		if (e.getSource() == button[4]) { // 橡皮擦2
			drawarea.chosenStatus = 2;
			drawarea.createNewitem();
			drawarea.repaint();
		}
		if (e.getSource() == button[6]) { // 添加文本框3
			drawarea.chosenStatus = 3;
			drawarea.createNewitem();
			drawarea.repaint();
		}
		if (e.getSource() == button[8]) { // 添加直线4
			drawarea.chosenStatus = 4;
			drawarea.createNewitem();
			drawarea.repaint();
		}

	}

}