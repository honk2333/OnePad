
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

//绘图区类（各种图形的绘制和鼠标事件）
public class DrawArea extends JPanel {
	DrawPad drawpad = null;
	Drawing[] itemList = new Drawing[5000];// 绘制图形及相关参数全部存到该数组

	int pos = 0;// 当前选中图形的数组下标
	int x0, y0;// 记录移动图形鼠标起始位置
	private int chosenStatus = 3;// 设置默认基本图形状态为随笔画
	int index = 0;// 当前已经绘制的图形数目
	Color color = Color.black;// 当前画笔的颜色
	float stroke = 3.0f;// 设置画笔的粗细 ，默认的是 1.0
	int R, G, B;// 用来存放当前颜色的彩值
	int f1, f2;// 用来存放当前字体的风格
	String style;// 存放当前字体

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

	public void setIndex(int x) {// 设置index的接口
		index = x;
	}

	public int getIndex() {// 读取index的接口
		return index;
	}

	public void setColor(Color color)// 设置颜色的值
	{
		this.color = color;
	}

	public void setStroke(float f)// 设置画笔粗细的接口
	{
		stroke = f;
	}

	public void setChosenStatus(int i)// 设置当前选择（button触发时使用）
	{
		chosenStatus = i;
	}

	public void setRGB(int cR, int cG, int cB) { // 设置当前颜色
		R = cR;
		G = cG;
		B = cB;
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}

	public void setFont(int i, int font)// 设置字体
	{
		if (i == 1) {
			f1 = font;
		} else
			f2 = font;
	}

	// 新建一个图形的基本单元对象的程序段
	void createNewitem() {
		// 鼠标形状设置
		if (chosenStatus == 5)
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		else
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		switch (chosenStatus) {// button触发改变currentChoice的值，由此得到各事件的入口
		case 3:
			itemList[index] = new Pencil();
			break;
		case 4:
			itemList[index] = new Eraser();
			break;
		case 5:
			itemList[index] = new Word();
			break;
		case 6:
			itemList[index] = new Line();
			break;
		case 8:
			itemList[index] = new Rect();
			break;
		case 9:
			itemList[index] = new fillRect();
			break;
		case 10:
			itemList[index] = new Oval();
			break;
		case 11:
			itemList[index] = new fillOval();
			break;
		case 12:
			itemList[index] = new Circle();
			break;
		case 13:
			itemList[index] = new fillCircle();
			break;
		case 14:
			itemList[index] = new RoundRect();
			break;
		case 15:
			itemList[index] = new fillRoundRect();
			break;
		}
		if (chosenStatus >= 3 && chosenStatus <= 15) {
			itemList[index].type = chosenStatus; // 形状设置
			itemList[index].R = R; // 颜色设置
			itemList[index].G = G;
			itemList[index].B = B;
			itemList[index].stroke = stroke; // 画笔粗细设置
		}

	}

	public void paintComponent(Graphics g) {// repaint()需要调用
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
			R = 0;
			G = 0;
			B = 0;
		}
		setRGB(R, G, B);
	}

	public void chooseColor(int num)// 改变当前选中的颜色,实现函数的重载
	{
		color = JColorChooser.showDialog(drawpad, "请选择颜色", color);
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

	public void setStroke()// 画笔粗细的调整
	{
		String input;
		input = JOptionPane.showInputDialog("请输入画笔的粗细( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 1.0f;

		}
		itemList[index].stroke = stroke;

	}

	public void setStroke(int num)// 画笔粗细的改变,与重名的函数构成重载
	{
		String input;
		input = JOptionPane.showInputDialog("请输入画笔的粗细( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 1.0f;

		}
		itemList[num].stroke = stroke;

	}

	public void setText(int num) {// 修改已有文字
		String input;
		input = JOptionPane.showInputDialog("请输入你要写入的文字");
		tarea.setText(input);
		itemList[num].s1 = input;// 重设选中文本框的各参数
		itemList[num].s2 = style;
		itemList[num].type = f1 + f2;
		itemList[num].stroke = stroke;
		itemList[num].R = R;
		itemList[num].G = G;
		itemList[num].B = B;
		
	}

	public void fillColor(Drawing nowdrawing) {// 填充
		int choice = nowdrawing.gettype();// 用于判断填充图形类型
		if (choice == 8) {
			itemList[pos] = new fillRect();
		} else if (choice == 10) {
			itemList[pos] = new fillOval();
		} else if (choice == 12) {
			itemList[pos] = new fillCircle();
		} else if (choice == 14) {
			itemList[pos] = new fillRoundRect();
		}
	}

	public void deletePaint(Drawing nowdrawing) {// 删除图形
		int choice = nowdrawing.gettype();
		if (choice >= 3 && choice <= 15) {
			itemList[pos] = new Line();
		}
	}

	// 鼠标事件MouseA类继承了MouseAdapter，用来完成鼠标的响应事件的操作
	class MouseA extends MouseAdapter {
		public void mousePressed(MouseEvent me) { // 鼠标按下
			if ((chosenStatus >= 16 && chosenStatus <= 22))
			// 删除，移动，更改大小，更改颜色，更改线型，填充六种操作都需要选定图形
			{
				for (pos = index - 1; pos >= 0; pos--) {
					// 从后到前寻找当前鼠标是否在某个图形内部
					if (itemList[pos].IsIn(me.getX(), me.getY())) {
						if (chosenStatus == 16)// 移动图形需要记录press时的坐标
						{
							x0 = me.getX();
							y0 = me.getY();
						}
						break;// 其它操作只需找到currenti即可
					}
				}
				if (pos >= 0) {// 有图形被选中
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// 更改鼠标样式为手形
					if (chosenStatus == 18) {// 触发填充
						fillColor(itemList[pos]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// 鼠标样式变回十字花
						repaint();
					} else if (chosenStatus == 17) {// 触发删除
						deletePaint(itemList[pos]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						repaint();
					} else if (chosenStatus == 20) {// 改变已有图形的颜色
						chooseColor(pos);
						repaint();
					} else if (chosenStatus == 21) { // 改变已有图形的线型
						setStroke();
						repaint();
					} else if (chosenStatus == 22) {// 改变已有文字
						setText(pos);
						repaint();
					}
				}
			} else {
				itemList[index].x1 = itemList[index].x2 = me.getX();
				itemList[index].y1 = itemList[index].y2 = me.getY();// x1,x2,y1,y2初始化
				// 如果当前选择为橡皮擦则进行下面的操作
				if (chosenStatus == 3) {
					itemList[index].x1 = itemList[index].x2 = me.getX();
					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// 创建新的图形的基本单元对象
				}
				// 如果当前选择为随笔画则进行下面的操作
				if (chosenStatus == 4) {
					itemList[index].x1 = itemList[index].x2 = me.getX();
					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// 创建新的图形的基本单元对象
				}
				// 如果选择图形的文字输入，则进行下面的操作
				if (chosenStatus == 5) {
					tx = me.getX();
					ty = me.getY();
					tarea.setBounds(tx, ty, 0, 0);
					tarea.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
				}
			}
		}

		public void mouseReleased(MouseEvent me) {
			// 鼠标松开
			if (chosenStatus == 16) {// 移动结束
				if (pos >= 0) {// 鼠标成功选择了某个图形
					itemList[pos].x1 = itemList[pos].x1 + me.getX() - x0;
					itemList[pos].y1 = itemList[pos].y1 + me.getY() - y0;
					itemList[pos].x2 = itemList[pos].x2 + me.getX() - x0;
					itemList[pos].y2 = itemList[pos].y2 + me.getY() - y0;
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else if (chosenStatus == 19) { // 放大缩小结束
				if (pos >= 0) {// 鼠标成功选择了某个图形
					itemList[pos].x2 = me.getX();
					itemList[pos].y2 = me.getY();
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else {
				if (chosenStatus == 3) { // 随笔画绘制结束
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
				} else if (chosenStatus == 4) { // 擦除绘制结束
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
				} else if (chosenStatus == 5) { // 文本框绘制结束
					tarea.setBounds(Math.min(tx, me.getX()) + 130, Math.min(ty, me.getY()), Math.abs(tx - me.getX()),
							Math.abs(ty - me.getY()));// 绘制文本框
					setText(index);
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					index++;
					createNewitem(); // 创建新的图形的基本单元对象
					repaint();
					tarea.setText("");// 重设文本框，为下一次使用做准备
					tarea.setBounds(tx, ty, 0, 0);
				}
				if (chosenStatus >= 2 && chosenStatus <= 12) {
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
		public void mouseDragged(MouseEvent me)// 鼠标的拖动
		{
			if (chosenStatus == 3) {// 任意线的画法
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem();// 创建新的图形的基本单元对象
				repaint();
			} else if (chosenStatus == 4) {// 橡皮擦擦除内容
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem(); // 创建新的图形的基本单元对象
				repaint();
			} else if (chosenStatus == 16) {
				if (pos >= 0) { // 移动的过程
					itemList[pos].x1 = itemList[pos].x1 + me.getX() - x0;
					itemList[pos].y1 = itemList[pos].y1 + me.getY() - y0;
					itemList[pos].x2 = itemList[pos].x2 + me.getX() - x0;
					itemList[pos].y2 = itemList[pos].y2 + me.getY() - y0;
					x0 = me.getX();
					y0 = me.getY();
					repaint();
				}
			} else if (chosenStatus == 19) {// 放大缩小的过程
				if (pos >= 0) {
					itemList[pos].x2 = me.getX();
					itemList[pos].y2 = me.getY();
					repaint();
				}
			} else if (chosenStatus >= 3 && chosenStatus <= 15) {// 绘制图形的过程
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
