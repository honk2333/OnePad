
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

//��ͼ���ࣨ����ͼ�εĻ��ƺ�����¼���
public class DrawArea extends JPanel {
	DrawPad drawpad = null;
	Drawing[] itemList = new Drawing[5000];// ����ͼ�μ���ز���ȫ���浽������

	int pos = 0;// ��ǰѡ��ͼ�ε������±�
	int x0, y0;// ��¼�ƶ�ͼ�������ʼλ��
	private int chosenStatus = 3;// ����Ĭ�ϻ���ͼ��״̬Ϊ��ʻ�
	int index = 0;// ��ǰ�Ѿ����Ƶ�ͼ����Ŀ
	Color color = Color.black;// ��ǰ���ʵ���ɫ
	float stroke = 3.0f;// ���û��ʵĴ�ϸ ��Ĭ�ϵ��� 1.0
	int R, G, B;// ������ŵ�ǰ��ɫ�Ĳ�ֵ
	int f1, f2;// ������ŵ�ǰ����ķ��
	String style;// ��ŵ�ǰ����

	JTextArea tarea = new JTextArea(""); // �ı���
	int tx, ty;

	DrawArea(DrawPad dp) { // ���캯��
		drawpad = dp;
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// ��������ó�ʮ����
		setBackground(Color.white);// ���û������ı����ǰ�ɫ
		addMouseListener(new MouseA());// �������¼�
		addMouseMotionListener(new MouseB());
		createNewitem();
	}

	public void setIndex(int x) {// ����index�Ľӿ�
		index = x;
	}

	public int getIndex() {// ��ȡindex�Ľӿ�
		return index;
	}

	public void setColor(Color color)// ������ɫ��ֵ
	{
		this.color = color;
	}

	public void setStroke(float f)// ���û��ʴ�ϸ�Ľӿ�
	{
		stroke = f;
	}

	public void setChosenStatus(int i)// ���õ�ǰѡ��button����ʱʹ�ã�
	{
		chosenStatus = i;
	}

	public void setRGB(int cR, int cG, int cB) { // ���õ�ǰ��ɫ
		R = cR;
		G = cG;
		B = cB;
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}

	public void setFont(int i, int font)// ��������
	{
		if (i == 1) {
			f1 = font;
		} else
			f2 = font;
	}

	// �½�һ��ͼ�εĻ�����Ԫ����ĳ����
	void createNewitem() {
		// �����״����
		if (chosenStatus == 5)
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		else
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		switch (chosenStatus) {// button�����ı�currentChoice��ֵ���ɴ˵õ����¼������
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
			itemList[index].type = chosenStatus; // ��״����
			itemList[index].R = R; // ��ɫ����
			itemList[index].G = G;
			itemList[index].B = B;
			itemList[index].stroke = stroke; // ���ʴ�ϸ����
		}

	}

	public void paintComponent(Graphics g) {// repaint()��Ҫ����
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int j = 0;
		while (j <= index) {
			draw(g2d, itemList[j]);
			j++;
		} // ��itemList�����ػ�һ��
	}

	void draw(Graphics2D g2d, Drawing i) {
		i.draw(g2d);// �����ʴ����������������
	}

	public void chooseColor()// ѡ��ǰ��ɫ
	{
		color = JColorChooser.showDialog(drawpad, "��ѡ����ɫ", color);
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

	public void chooseColor(int num)// �ı䵱ǰѡ�е���ɫ,ʵ�ֺ���������
	{
		color = JColorChooser.showDialog(drawpad, "��ѡ����ɫ", color);
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

	public void setStroke()// ���ʴ�ϸ�ĵ���
	{
		String input;
		input = JOptionPane.showInputDialog("�����뻭�ʵĴ�ϸ( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 1.0f;

		}
		itemList[index].stroke = stroke;

	}

	public void setStroke(int num)// ���ʴ�ϸ�ĸı�,�������ĺ�����������
	{
		String input;
		input = JOptionPane.showInputDialog("�����뻭�ʵĴ�ϸ( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 1.0f;

		}
		itemList[num].stroke = stroke;

	}

	public void setText(int num) {// �޸���������
		String input;
		input = JOptionPane.showInputDialog("��������Ҫд�������");
		tarea.setText(input);
		itemList[num].s1 = input;// ����ѡ���ı���ĸ�����
		itemList[num].s2 = style;
		itemList[num].type = f1 + f2;
		itemList[num].stroke = stroke;
		itemList[num].R = R;
		itemList[num].G = G;
		itemList[num].B = B;
		
	}

	public void fillColor(Drawing nowdrawing) {// ���
		int choice = nowdrawing.gettype();// �����ж����ͼ������
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

	public void deletePaint(Drawing nowdrawing) {// ɾ��ͼ��
		int choice = nowdrawing.gettype();
		if (choice >= 3 && choice <= 15) {
			itemList[pos] = new Line();
		}
	}

	// ����¼�MouseA��̳���MouseAdapter���������������Ӧ�¼��Ĳ���
	class MouseA extends MouseAdapter {
		public void mousePressed(MouseEvent me) { // ��갴��
			if ((chosenStatus >= 16 && chosenStatus <= 22))
			// ɾ�����ƶ������Ĵ�С��������ɫ���������ͣ�������ֲ�������Ҫѡ��ͼ��
			{
				for (pos = index - 1; pos >= 0; pos--) {
					// �Ӻ�ǰѰ�ҵ�ǰ����Ƿ���ĳ��ͼ���ڲ�
					if (itemList[pos].IsIn(me.getX(), me.getY())) {
						if (chosenStatus == 16)// �ƶ�ͼ����Ҫ��¼pressʱ������
						{
							x0 = me.getX();
							y0 = me.getY();
						}
						break;// ��������ֻ���ҵ�currenti����
					}
				}
				if (pos >= 0) {// ��ͼ�α�ѡ��
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// ���������ʽΪ����
					if (chosenStatus == 18) {// �������
						fillColor(itemList[pos]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// �����ʽ���ʮ�ֻ�
						repaint();
					} else if (chosenStatus == 17) {// ����ɾ��
						deletePaint(itemList[pos]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						repaint();
					} else if (chosenStatus == 20) {// �ı�����ͼ�ε���ɫ
						chooseColor(pos);
						repaint();
					} else if (chosenStatus == 21) { // �ı�����ͼ�ε�����
						setStroke();
						repaint();
					} else if (chosenStatus == 22) {// �ı���������
						setText(pos);
						repaint();
					}
				}
			} else {
				itemList[index].x1 = itemList[index].x2 = me.getX();
				itemList[index].y1 = itemList[index].y2 = me.getY();// x1,x2,y1,y2��ʼ��
				// �����ǰѡ��Ϊ��Ƥ�����������Ĳ���
				if (chosenStatus == 3) {
					itemList[index].x1 = itemList[index].x2 = me.getX();
					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				}
				// �����ǰѡ��Ϊ��ʻ����������Ĳ���
				if (chosenStatus == 4) {
					itemList[index].x1 = itemList[index].x2 = me.getX();
					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				}
				// ���ѡ��ͼ�ε��������룬���������Ĳ���
				if (chosenStatus == 5) {
					tx = me.getX();
					ty = me.getY();
					tarea.setBounds(tx, ty, 0, 0);
					tarea.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
				}
			}
		}

		public void mouseReleased(MouseEvent me) {
			// ����ɿ�
			if (chosenStatus == 16) {// �ƶ�����
				if (pos >= 0) {// ���ɹ�ѡ����ĳ��ͼ��
					itemList[pos].x1 = itemList[pos].x1 + me.getX() - x0;
					itemList[pos].y1 = itemList[pos].y1 + me.getY() - y0;
					itemList[pos].x2 = itemList[pos].x2 + me.getX() - x0;
					itemList[pos].y2 = itemList[pos].y2 + me.getY() - y0;
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else if (chosenStatus == 19) { // �Ŵ���С����
				if (pos >= 0) {// ���ɹ�ѡ����ĳ��ͼ��
					itemList[pos].x2 = me.getX();
					itemList[pos].y2 = me.getY();
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else {
				if (chosenStatus == 3) { // ��ʻ����ƽ���
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
				} else if (chosenStatus == 4) { // �������ƽ���
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
				} else if (chosenStatus == 5) { // �ı�����ƽ���
					tarea.setBounds(Math.min(tx, me.getX()) + 130, Math.min(ty, me.getY()), Math.abs(tx - me.getX()),
							Math.abs(ty - me.getY()));// �����ı���
					setText(index);
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					index++;
					createNewitem(); // �����µ�ͼ�εĻ�����Ԫ����
					repaint();
					tarea.setText("");// �����ı���Ϊ��һ��ʹ����׼��
					tarea.setBounds(tx, ty, 0, 0);
				}
				if (chosenStatus >= 2 && chosenStatus <= 12) {
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					repaint();
					index++;
					createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				}
			}
		}
	}

	// ����¼�MouseB�̳���MouseMotionAdapter�������������Ĺ������϶�
	class MouseB extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent me)// �����϶�
		{
			if (chosenStatus == 3) {// �����ߵĻ���
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				repaint();
			} else if (chosenStatus == 4) {// ��Ƥ����������
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem(); // �����µ�ͼ�εĻ�����Ԫ����
				repaint();
			} else if (chosenStatus == 16) {
				if (pos >= 0) { // �ƶ��Ĺ���
					itemList[pos].x1 = itemList[pos].x1 + me.getX() - x0;
					itemList[pos].y1 = itemList[pos].y1 + me.getY() - y0;
					itemList[pos].x2 = itemList[pos].x2 + me.getX() - x0;
					itemList[pos].y2 = itemList[pos].y2 + me.getY() - y0;
					x0 = me.getX();
					y0 = me.getY();
					repaint();
				}
			} else if (chosenStatus == 19) {// �Ŵ���С�Ĺ���
				if (pos >= 0) {
					itemList[pos].x2 = me.getX();
					itemList[pos].y2 = me.getY();
					repaint();
				}
			} else if (chosenStatus >= 3 && chosenStatus <= 15) {// ����ͼ�εĹ���
				itemList[index].x2 = me.getX();
				itemList[index].y2 = me.getY();
				repaint();
			}
			// repaint();
		}

		public void mouseMoved(MouseEvent me)// �����ƶ�
		{
			for (pos = index - 1; pos >= 0; pos--) {
				// �Ӻ�ǰѰ�ҵ�ǰ����Ƿ���ĳ��ͼ���ڲ�
				if (itemList[pos].IsIn(me.getX(), me.getY())) {
					break;// ��������ֻ���ҵ�currenti����
				}
			}
			if (pos >= 0) {// ��ͼ�α�ѡ��
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// ���������ʽΪ��ͷ
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}
		}
	}

}
