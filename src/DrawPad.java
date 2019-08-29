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
public class DrawPad extends JFrame {
	FileClass fileclass; // 文件对象
	DrawArea drawarea; // 画板部分
	// 组成的各个部分
	Toolpad toolpad;
	Graphpad graphpad;
	Fontpad fontpad;
	Colorpad colorpad;
	Menupad menupad;

	FileClass GetFileClass() {
		return fileclass;
	}

	DrawArea GetDrawArea() {
		return drawarea;
	}

	DrawPad(String string) {
		super(string);
		// 各个组件的创建
		drawarea = new DrawArea(this);// 画板部分
		fileclass = new FileClass(this, drawarea);// 文件对象
		toolpad = new Toolpad(this);
		graphpad = new Graphpad(this);
		fontpad = new Fontpad(this);
		colorpad = new Colorpad(this);
		menupad = new Menupad(this);

		// 设置各部分位置
		Container con = getContentPane();//
		Toolkit tool = getToolkit();//
		Dimension dim = tool.getScreenSize();//
		con.setLayout(null);
		// toolpad.setBounds(30, 10, 1500, 80);
		// graphpad.setBounds(1550, 10, 350, 280);
		drawarea.setBounds(20, 100, dim.width - 400, dim.height - 200);
		// colorpad.setBounds(dim.width - 350, dim.height - 500, 300, 300);
		// fontpad.setBounds(dim.width - 350, 350, 300, 75);
		// cbutton.setBounds(dim.width - 350, dim.height - 600, 300, 100);
		con.add(toolpad.getoolbar());
		con.add(graphpad.getoolbar());
		con.add(drawarea);
		con.add(colorpad.getoolbar());
		con.add(fontpad.getoolbar());
		con.add(colorpad.getbutton());
		con.add(colorpad.getaken());
		con.add(drawarea.tarea);
		setBounds(0, 0, dim.width, dim.height);
		setVisible(true);
		validate();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
