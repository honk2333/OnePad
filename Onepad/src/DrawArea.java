
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

	int chooseni = 0;// ��ǰѡ��ͼ�ε������±�
	int x0, y0;// ��¼�ƶ�ͼ�������ʼλ��
	private int chosenStatus = 3;// ����Ĭ�ϻ���ͼ��״̬Ϊ��ʻ�
	int index = 0;// ��ǰ�Ѿ����Ƶ�ͼ����Ŀ
	private Color color = Color.black;// ��ǰ���ʵ���ɫ
	int R, G, B;// ������ŵ�ǰ��ɫ�Ĳ�ֵ
	int f1, f2;// ������ŵ�ǰ����ķ��
	String style;// ��ŵ�ǰ����
	float stroke = 1.0f;// ���û��ʵĴ�ϸ ��Ĭ�ϵ��� 1.0
	JTextArea tarea = new JTextArea("");
	int tx, ty;

	DrawArea(DrawPad dp) {
		drawpad = dp;
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// ��������ó�ʮ����
		setBackground(Color.white);// ���û������ı����ǰ�ɫ
		addMouseListener(new MouseA());// �������¼�
		addMouseMotionListener(new MouseB());
		createNewitem();
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

	// �½�һ��ͼ�εĻ�����Ԫ����ĳ����
	void createNewitem() {
		if (chosenStatus == 13)// �������������Ӧ������Ϊ�ı������ʽ
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		else
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		switch (chosenStatus) {// button�����ı�currentChoice��ֵ���ɴ˵õ����¼������
		case 2:
			itemList[index] = new Eraser();
			break;
		case 3:
			itemList[index] = new Pencil();
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
		case 13:
			itemList[index] = new Word();
			break;
		}
		if (chosenStatus >= 2 && chosenStatus <= 13) {
			itemList[index].type = chosenStatus;
			itemList[index].R = R;
			itemList[index].G = G;
			itemList[index].B = B;
			itemList[index].stroke = stroke;
		}

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
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}

	public void colorBar(int cR, int cG, int cB) {
		R = cR;
		G = cG;
		B = cB;
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}

	public void changeColor()// �ı䵱ǰͼƬ����ɫ
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
		itemList[chooseni].R = R;
		itemList[chooseni].G = G;
		itemList[chooseni].B = B;
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

	public void changeStroke()// ���ʴ�ϸ�ĸı䣨��Ҫ��Կ���ͼ�Ρ�ֱ�ߡ���ʻ���
	{
		String input;
		input = JOptionPane.showInputDialog("�����뻭�ʵĴ�ϸ( >0 )");
		try {
			stroke = Float.parseFloat(input);

		} catch (Exception e) {
			stroke = 1.0f;

		}
		itemList[chooseni].stroke = stroke;

	}

	public void setChosenStatus(int i)// ���õ�ǰѡ��button����ʱʹ�ã�
	{
		chosenStatus = i;
	}

	public void changeText() {// �޸���������
		String input;
		input = JOptionPane.showInputDialog("��������Ҫ�޸�Ϊ������");
		itemList[chooseni].s1 = input;// ����ѡ���ı���ĸ�����
		itemList[chooseni].type = f1 + f2;
		itemList[chooseni].s2 = style;
		itemList[chooseni].stroke = stroke;
		itemList[chooseni].R = R;
		itemList[chooseni].G = G;
		itemList[chooseni].B = B;
	}

	public void setFont(int i, int font)// ��������
	{
		if (i == 1) {
			f1 = font;
		} else
			f2 = font;
	}

	public void fillColor(Drawing nowdrawing) {// ���
		int choice = nowdrawing.gettypechoice();// �����ж����ͼ������
		if (choice == 5) {
			itemList[chooseni] = new fillRect();
		} else if (choice == 7) {
			itemList[chooseni] = new fillOval();
		} else if (choice == 9) {
			itemList[chooseni] = new fillCircle();
		} else if (choice == 11) {
			itemList[chooseni] = new fillRoundRect();
		}
		itemList[chooseni].x1 = nowdrawing.x1;
		itemList[chooseni].x2 = nowdrawing.x2;
		itemList[chooseni].y1 = nowdrawing.y1;
		itemList[chooseni].y2 = nowdrawing.y2;
		itemList[chooseni].R = R;
		itemList[chooseni].G = G;
		itemList[chooseni].B = B;
	}

	public void deletePaint(Drawing nowdrawing) {// ɾ��
		int choice = nowdrawing.gettypechoice();
		if (choice >= 2 && choice <= 13) {
			itemList[chooseni] = new Line();
		}
	}

	// ����¼�MouseA��̳���MouseAdapter���������������Ӧ�¼��Ĳ���
	class MouseA extends MouseAdapter {
		public void mouseEntered(MouseEvent me) {
			// ������
			drawpad.setStratBar("�������ڣ�[" + me.getX() + " ," + me.getY() + "]");// ����״̬����ʾ
		}

		public void mouseExited(MouseEvent me) {
			// ����˳�
			drawpad.setStratBar("����˳��ڣ�[" + me.getX() + " ," + me.getY() + "]");
		}

		public void mousePressed(MouseEvent me) {
			// ��갴��
			drawpad.setStratBar("��갴���ڣ�[" + me.getX() + " ," + me.getY() + "]");
			if (chosenStatus >= 15 && chosenStatus <= 21)
			// ɾ�����ƶ������Ĵ�С��������ɫ���������ͣ�������ֲ�������Ҫѡ��ͼ��
			{
				for (chooseni = index - 1; chooseni >= 0; chooseni--) {
					// �Ӻ�ǰѰ�ҵ�ǰ����Ƿ���ĳ��ͼ���ڲ�
					if (itemList[chooseni].in(me.getX(), me.getY())) {
						if (chosenStatus == 16)// �ƶ�ͼ����Ҫ��¼pressʱ������
						{
							x0 = me.getX();
							y0 = me.getY();
						}
						break;// ��������ֻ���ҵ�currenti����
					}
				}
				if (chooseni >= 0) {// ��ͼ�α�ѡ��
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// ���������ʽΪ����
					if (chosenStatus == 20) {// �������
						fillColor(itemList[chooseni]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// �����ʽ���ʮ�ֻ�
						repaint();
					} else if (chosenStatus == 15) {// ����ɾ��
						deletePaint(itemList[chooseni]);
						setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						repaint();
					} else if (chosenStatus == 18) {// �ı�����ͼ�ε���ɫ
						changeColor();
						repaint();
					} else if (chosenStatus == 19) {// �ı�����ͼ�ε�����
						changeStroke();
						repaint();
					} else if (chosenStatus == 21) {// �ı���������
						changeText();
						repaint();
					}
				}
			} else {
				itemList[index].x1 = itemList[index].x2 = me.getX();
				itemList[index].y1 = itemList[index].y2 = me.getY();// x1,x2,y1,y2��ʼ��
				// �����ǰѡ��Ϊ��ʻ����������Ĳ���
				if (chosenStatus == 2) {
					itemList[index].x1 = itemList[index].x2 = me.getX();
					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				}
				if (chosenStatus == 3) {
					itemList[index].x1 = itemList[index].x2 = me.getX();
					itemList[index].y1 = itemList[index].y2 = me.getY();
					index++;
					createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				}
				// ���ѡ��ͼ�ε��������룬���������Ĳ���
				if (chosenStatus == 13) {
					tx = me.getX();
					ty = me.getY();
					tarea.setBounds(tx, ty, 0, 0);
					tarea.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
				}
			}
		}

		public void mouseReleased(MouseEvent me) {
			// ����ɿ�
			drawpad.setStratBar("����ɿ��ڣ�[" + me.getX() + " ," + me.getY() + "]");
			if (chosenStatus == 16) {// �ƶ�����

				if (chooseni >= 0) {// ���ɹ�ѡ����ĳ��ͼ��
					itemList[chooseni].x1 = itemList[chooseni].x1 + me.getX() - x0;
					itemList[chooseni].y1 = itemList[chooseni].y1 + me.getY() - y0;
					itemList[chooseni].x2 = itemList[chooseni].x2 + me.getX() - x0;
					itemList[chooseni].y2 = itemList[chooseni].y2 + me.getY() - y0;
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else if (chosenStatus == 17) {// �Ŵ���С����
				if (chooseni >= 0) {// ���ɹ�ѡ����ĳ��ͼ��
					itemList[chooseni].x2 = me.getX();
					itemList[chooseni].y2 = me.getY();
					repaint();
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			} else {
				if (chosenStatus == 3) {// ��ʻ����ƽ���
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
				} else if (chosenStatus == 2) {// �������ƽ���
					itemList[index].x1 = me.getX();
					itemList[index].y1 = me.getY();
				} else if (chosenStatus == 13) {// �ı�����ƽ���
					tarea.setBounds(Math.min(tx, me.getX()) + 130, Math.min(ty, me.getY()), Math.abs(tx - me.getX()),
							Math.abs(ty - me.getY()));// �����ı���
					String input;
					input = JOptionPane.showInputDialog("��������Ҫд�������");
					tarea.setText(input);
					itemList[index].s1 = input;
					itemList[index].type = f1 + f2;// ���ô��塢б��
					itemList[index].x2 = me.getX();
					itemList[index].y2 = me.getY();
					itemList[index].s2 = style;// ��������

					index++;
					chosenStatus = 13;
					createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
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
			drawpad.setStratBar("����϶��ڣ�[" + me.getX() + " ," + me.getY() + "]");
			if (chosenStatus == 3) {// �����ߵĻ���
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				repaint();
			} else if (chosenStatus == 2) {// �����ߵĻ���
				itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
				itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
				index++;
				createNewitem();// �����µ�ͼ�εĻ�����Ԫ����
				repaint();
			} else if (chosenStatus == 16) {
				if (chooseni >= 0) {// �ƶ��Ĺ���
					itemList[chooseni].x1 = itemList[chooseni].x1 + me.getX() - x0;
					itemList[chooseni].y1 = itemList[chooseni].y1 + me.getY() - y0;
					itemList[chooseni].x2 = itemList[chooseni].x2 + me.getX() - x0;
					itemList[chooseni].y2 = itemList[chooseni].y2 + me.getY() - y0;
					x0 = me.getX();
					y0 = me.getY();
					repaint();
				}
			} else if (chosenStatus == 17) {// �Ŵ���С�Ĺ���
				if (chooseni >= 0) {
					itemList[chooseni].x2 = me.getX();
					itemList[chooseni].y2 = me.getY();
					repaint();
				}
			} else if (chosenStatus >= 2 && chosenStatus <= 12) {// ����ͼ�εĹ���
				itemList[index].x2 = me.getX();
				itemList[index].y2 = me.getY();
				repaint();
			}
			// repaint();
		}

		public void mouseMoved(MouseEvent me)// �����ƶ�
		{
			drawpad.setStratBar("����ƶ��ڣ�[" + me.getX() + " ," + me.getY() + "]");
			for (chooseni = index - 1; chooseni >= 0; chooseni--) {
				// �Ӻ�ǰѰ�ҵ�ǰ����Ƿ���ĳ��ͼ���ڲ�
				if (itemList[chooseni].in(me.getX(), me.getY())) {
					break;// ��������ֻ���ҵ�currenti����
				}
			}
			if (chooseni >= 0) {// ��ͼ�α�ѡ��
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// ���������ʽΪ��ͷ
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}
		}
	}

}
