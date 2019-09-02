
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

//绘图区类（各种图形的绘制和鼠标事件）
public class DrawArea extends JPanel {
	DrawPad drawpad = null;
	Drawing[] itemList = new Drawing[5000];// 绘制图形及相关参数全部存到该数组

	int pos = 0;// 当前选中图形的数组下标
	int x0, y0;// 记录移动图形鼠标起 始位置
	int x1, y1;
	int chosenStatus = 1;// 设置默认基本图形状态为随笔画
	int index = 0;// 当前已经绘制的图形数目
	Color color = Color.black;// 当前画笔的颜色
	float stroke = 3.0f;// 设置画笔的粗细 ，默认的是 3.0
	int R, G, B;// 用来存放当前颜色的彩值
	int f1, f2; // 用来存放当前字体的风格
	String style; // 存放当前字体
	JTextArea tarea = new JTextArea(""); // 文本框
	int tx, ty;

	DrawArea(DrawPad dp) { // 构造函数
		drawpad = dp;
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// 把鼠标设置成十字形
		setBackground(Color.white);// 设置绘制区的背景是白色
		addMouseListener(new MouseA());// 添加鼠标事件
		addMouseMotionListener(new MouseB());
		createNewitem();
	}

	public void setRGB(int cR, int cG, int cB) { // 设置当前颜色
		R = cR;
		G = cG;
		B = cB;
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}

	public void setFont(int i, int font) // 设置字体
	{
		if (i == 1) {
			f1 = font;
		} else
			f2 = font;
	}

	// 新建一个图形的基本单元对象的程序段
	void createNewitem() {
		// 鼠标形状设置
		if (chosenStatus == 3)
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		else
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		switch (chosenStatus) {// button触发改变currentChoice的值，由此得到各事件的入口
		case 1:
			itemList[index] = new Pencil();
			break;
		case 2:
			itemList[index] = new Eraser();
			break;
		case 3:
			itemList[index] = new Word();
			break;
		case 4:
			itemList[index] = new Line();
			break;
		case 5:
			itemList[index] = new Rect();
			break;
		case 6:
			itemList[index] = new fillRect();
			break;
		case 7:
			itemList[index] = new Oval();
			break;
		case 8:
			itemList[index] = new fillOval();
			break;
		case 9:
			itemList[index] = new Circle();
			break;
		case 10:
			itemList[index] = new fillCircle();
			break;
		case 11:
			itemList[index] = new RoundRect();
			break;
		case 12:
			itemList[index] = new fillRoundRect();
			break;
		}
		if (chosenStatus >= 1 && chosenStatus <= 12) {
			itemList[index].type = chosenStatus; // 形状设置
			itemList[index].R = R; // 颜色设置
			itemList[index].G = G;
			itemList[index].B = B;
			itemList[index].stroke = stroke; // 画笔粗细设置
		}

	}

	public void paintComponent(Graphics g) { // repaint()需要调用
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int j = 0;
		while (j <= index) {
			draw(g2d, itemList[j]);
			j++;
		} // 将itemList数组重画一遍
	}

	void draw(Graphics2D g2d, Drawing i) {
		i.draw(g2d);// 将画笔传到个各类的子类中
	}

	public void chooseColor()// 选择当前颜色
	{
		color = JColorChooser.showDialog(drawpad, "请选择颜色", color);
		try {
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
		} catch (Exception e) {
			R = 100;
			G = 100;
			B = 100;
		}
		setRGB(R, G, B);
	}

	public void chooseColor(int num)// 改变当前选中的颜色,实现函数的重载
	{
		color = JColorChooser.showDialog(drawpad, "请选择颜色", color); // 调用Java自带的选择颜色部件
		try {
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
		} catch (Exception e) {
			R = 0;
			G = 0;
			B = 0;
		}
		itemList[num].R = R;
		itemList[num].G = G;
		itemList[num].B = B;
	}

	public void chooseStroke()// 画笔粗细的调整
	{
		String input;
		input = JOptionPane.showInputDialog("请输入画笔的粗细( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 3.0f;

		}
		itemList[index].stroke = stroke;

	}

	public void chooseStroke(int num)// 画笔粗细的改变,与重名的函数构成重载
	{
		String input;
		input = JOptionPane.showInputDialog("请输入画笔的粗细( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 3.0f;

		}
		itemList[num].stroke = stroke;

	}

	public void chooseText() { // 创建文字
		String input;
		input = JOptionPane.showInputDialog("请输入你要写入的文字");
		tarea.setText(input);
		itemList[index].s1 = input;// 重设选中文本框的各参数
		itemList[index].s2 = style;
		itemList[index].fontype = f1 + f2;
		itemList[index].stroke = stroke;
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}

	public void chooseText(int num) { // 修改已有文字
		String input;
		input = JOptionPane.showInputDialog("请输入你要写入的文字");
		tarea.setText(input);
		itemList[num].s1 = input;// 重设选中文本框的各参数
		itemList[num].s2 = style;
		itemList[num].fontype = f1 + f2;
		itemList[num].stroke = stroke;
		itemList[num].R = R;
		itemList[num].G = G;
		itemList[num].B = B;
	}

	public void fillColor(Drawing nowdrawing) { // 填充
		int choice = nowdrawing.gettype(); // 用于判断填充图形类型
		R = itemList[pos].R;
		G = itemList[pos].G;
		B = itemList[pos].B;
		x0 = itemList[pos].x1;
		y0 = itemList[pos].y1;
		x1 = itemList[pos].x2;
		y1 = itemList[pos].y2;
		if (choice == 5) {
			itemList[pos] = new fillRect();
		} else if (choice == 7) {
			itemList[pos] = new fillOval();
		} else if (choice == 9) {
			itemList[pos] = new fillCircle();
		} else if (choice == 11) {
			itemList[pos] = new fillRoundRect();
		}
		itemList[pos].R = R;
		itemList[pos].G = G;
		itemList[pos].B = B;
		itemList[pos].x1 = x0;
		itemList[pos].y1 = y0;
		itemList[pos].x2 = x1;
		itemList[pos].y2 = y1;
		repaint();
	}

	public void deletePaint(Drawing nowdrawing) { // 删除图形
		int choice = nowdrawing.gettype();
		if (choice >= 1 && choice <= 12) {
			itemList[pos] = new Line(); // 将原来的图形用一条看不见的线代替,起到删除的效果
		}
	}

	public int getPixel(int x, int y) throws AWTException {
		Robot rb = new Robot(); // 在此用来抓取屏幕，即截屏。详细可见API
		Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
		Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
		Rectangle rec = new Rectangle(100, 50 + 80 + 52, 1350, di.height - 300);
		BufferedImage bi = rb.createScreenCapture(rec);
		int pixelColor = bi.getRGB(x, y);// 获得自定坐标的像素值
		// pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
		//System.out.println(pixelColor);
		return 16777216 + pixelColor;
		// return rb.getPixelColor(x, y);
	}

	public void catchcolor(int x, int y) {
		try {
			int pixel = getPixel(x, y);
//			R = pixel.getRed();
//			G = pixel.getGreen();
//			B = pixel.getBlue();
			R = (pixel & 0xff0000) / (0x10000);
			G = (pixel & 0x00ff00) / (0x100);
			B = (pixel & 0x0000ff);
		} catch (AWTException e) {
			R = 0;
			G = 0;
			B = 0;
		}
		//System.out.println(R + " " + G + " " + B);
	}

	// 鼠标事件MouseA类继承了MouseAdapter，用来完成鼠标的响应事件的操作
	class MouseA extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			if (chosenStatus == 13)
				catchcolor(me.getX(), me.getY());
		}

		public void mousePressed(MouseEvent me) { // 鼠标按下
			if (chosenStatus >= 21 && chosenStatus <= 27) { // 删除，移动，更改大小，更改颜色，更改线型，填充六种操作都需要选定图形
				for (pos = index - 1; pos >= 0; pos--) { // 从后到前寻找当前鼠标是否在某个图形内部
					if (itemList[pos].IsIn(me.getX(), me.getY())) {
						if (chosenStatus == 23) { // 移动图形需要记录press时的坐标
							x0 = me.getX();
							y0 = me.getY();
						}
						break; // 其它操作只需找到currenti即可
					}
				}
				if (pos >= 0) { // 有图形被选中
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// 更改鼠标样式为手形
					if (chosenStatus == 25) { // 触发填充
						fillColor(itemList[pos]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// 鼠标样式变回十字花
						repaint();
					} else if (chosenStatus == 24) {// 触发删除
						deletePaint(itemList[pos]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						repaint();
					} else if (chosenStatus == 27) {// 改变已有图形的颜色
						chooseColor(pos);
						repaint();
					} else if (chosenStatus == 22) { // 改变已有图形的线型
						chooseStroke(pos);
						repaint();
					} else if (chosenStatus == 21) { // 改变已有文字
						chooseText(pos);
						repaint();
					}
				}
			} else {
				itemList[index].x1 = itemList[index].x2 = me.getX();
				itemList[index].y1 = itemList[index].y2 = me.getY();// x1,x2,y1,y2初始化
				// 如果当前选择为随笔画则进行下面的操作
				if (chosenStatus == 1) {
//					itemList[index].x1 = itemList[index].x2 = me.getX();
//					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// 创建新的图形的基本单元对象
				}
				// 如果当前选择为橡皮擦则进行下面的操作
				if (chosenStatus == 2) {
//					itemList[index].x1 = itemList[index].x2 = me.getX();
//					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// 创建新的图形的基本单元对象
				}
				// 如果选择图形的文字输入，则进行下面的操作
				if (chosenStatus == 3) {
					tx = me.getX();
					ty = me.getY();
					tarea.setBounds(tx, ty, 0, 0);
					tarea.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
				}
			}
		}

		public void mouseReleased(MouseEvent me) {
			// 鼠标松开
			if (chosenStatus == 23) { // 移动结束
				if (pos >= 0) { // 鼠标成功选择了某个图形
					itemList[pos].x1 = itemList[pos].x1 + me.getX() - x0;
					itemList[pos].y1 = itemList[pos].y1 + me.getY() - y0;
					itemList[pos].x2 = itemList[pos].x2 + me.getX() - x0;
					itemList[pos].y2 = itemList[pos].y2 + me.getY() - y0;
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else if (chosenStatus == 26) { // 放大缩小结束
				if (pos >= 0) { // 鼠标成功选择了某个图形
					itemList[pos].x2 = me.getX();
					itemList[pos].y2 = me.getY();
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else {
				if (chosenStatus == 1) { // 随笔画绘制结束
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					repaint();
					index++;
					createNewitem(); // 创建新的图形的基本单元对象
				} else if (chosenStatus == 2) { // 擦除绘制结束
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					repaint();
					index++;
					createNewitem();// 创建新的图形的基本单元对象
				} else if (chosenStatus == 3) { // 文本框绘制结束
					tarea.setBounds(Math.min(tx, me.getX()) + 130, Math.min(ty, me.getY()), Math.abs(tx - me.getX()),
							Math.abs(ty - me.getY())); // 绘制文本框
					chooseText();
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					index++;
					createNewitem(); // 创建新的图形的基本单元对象
					repaint();
					tarea.setText(""); // 重设文本框，为下一次使用做准备
					tarea.setBounds(tx, ty, 0, 0);
				} else if (chosenStatus >= 1 && chosenStatus <= 12) {
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					repaint();
					index++;
					createNewitem();// 创建新的图形的基本单元对象
				}
			}
		}
	}

	// 鼠标事件MouseB继承了MouseMotionAdapter，用来处理鼠标的滚动与拖动
	class MouseB extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent me) // 鼠标的拖动
		{
			if (chosenStatus == 1) {// 任意线的画法
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem();// 创建新的图形的基本单元对象
				repaint();
			} else if (chosenStatus == 2) {// 橡皮擦擦除内容
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem(); // 创建新的图形的基本单元对象
				repaint();
			} else if (chosenStatus == 23) { // 移动图形的过程
				if (pos >= 0) {
					itemList[pos].x1 = itemList[pos].x1 + me.getX() - x0;
					itemList[pos].y1 = itemList[pos].y1 + me.getY() - y0;
					itemList[pos].x2 = itemList[pos].x2 + me.getX() - x0;
					itemList[pos].y2 = itemList[pos].y2 + me.getY() - y0;
					x0 = me.getX();
					y0 = me.getY();
					repaint();
				}
			} else if (chosenStatus == 26) {// 放大缩小的过程
				if (pos >= 0) {
					itemList[pos].x2 = me.getX();
					itemList[pos].y2 = me.getY();
					repaint();
				}
			} else if (chosenStatus >= 1 && chosenStatus <= 12) {// 绘制图形的过程
				itemList[index].x2 = me.getX();
				itemList[index].y2 = me.getY();
				repaint();
			}
			// repaint();
		}

		public void mouseMoved(MouseEvent me)// 鼠标的移动
		{
			for (pos = index - 1; pos >= 0; pos--) {
				// 从后到前寻找当前鼠标是否在某个图形内部
				if (itemList[pos].IsIn(me.getX(), me.getY())) {
					break;// 其它操作只需找到currenti即可
				}
			}
			if (pos >= 0) {// 有图形被选中
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// 更改鼠标样式为箭头
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}
		}
	}

}
