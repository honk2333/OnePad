import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
	JLabel ding;
	boolean catchcoloring = false;

	FileClass GetFileClass() {
		return fileclass;
	}

	DrawArea GetDrawArea() {
		return drawarea;
	}

	public int getPixel(int x, int y) throws AWTException {
		Robot rb = new Robot(); // 在此用来抓取屏幕，即截屏。详细可见API
		Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
		Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
		Rectangle rec = new Rectangle(0, 0, di.width, di.height);
		BufferedImage bi = rb.createScreenCapture(rec);
		int pixelColor = bi.getRGB(x, y);// 获得自定坐标的像素值
		// pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
		System.out.println(pixelColor);
		return 16777216 + pixelColor;
	}

	public void catchcolor(int x, int y) {
		try {
			int pixel = getPixel(x, y);
			System.out.println(pixel);
			drawarea.R = (pixel & (0xff0000)) / (0x10000);
			drawarea.G = pixel & (0x00ff00) / (0x100);
			drawarea.B = pixel & (0x0000ff);
		} catch (AWTException e) {
			drawarea.R = 0;
			drawarea.G = 0;
			drawarea.B = 0;
		}
		System.out.println(drawarea.R + " " + drawarea.G + " " + drawarea.B);
	}

	DrawPad(String string) {
		super(string);
		// 各个组件的创建
		catchcoloring = false;
		addMouseListener(new MouseC());// 添加鼠标事件
		Icon img = new ImageIcon("images/ding.png");
		ding = new JLabel(img);
		ding.setBounds(20, 100, 50, 50);
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

		con.add(ding);
		con.add(toolpad.getoolbar());
		con.add(graphpad.getoolbar());
		// con.add(menupad.getoolbar());
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

	class MouseC extends MouseAdapter {
		public void mouseclicked(MouseEvent me) { // 鼠标按下
			System.out.println("w");
//			if (catchcoloring) {
//				catchcolor(me.getX(), me.getY());
//			}
		}
	}

}
