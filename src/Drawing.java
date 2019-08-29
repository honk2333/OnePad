import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;

//ͼ�λ����� ���ڻ��Ƹ���ͼ��
//���࣬����ͼ�ε�Ԫ���õ����еĽӿڣ�����ʹ�õ�
//���������Էŵ��������У�������Ա����ظ�����
public abstract class Drawing implements Serializable {
	int x1, x2, y1, y2; // ������������
	int R, G, B; // ����ɫ������
	float stroke; // ����������ϸ������
	int fontype; // ������������
	String s1; // ��������ķ��
	String s2; // ��������ķ��
	int type;// ��¼ͼ�����ԣ���currentchoice��ƥ��

	int gettype() {
		return type;
	}// ��ȡtype�������

	void draw(Graphics2D g2d) {
	} // �����ͼ����

	boolean IsIn(int x, int y) {
		return false;
	} // �жϵ�ǰ���Ƿ��ڴ�ͼ���ڣ��򸽽�����ѡ��ͼ��ʱʹ��
}

class Line extends Drawing// ֱ����
{
	int gettype() {
		type = 4;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));// Ϊ Graphics2D ���������� Paint ���ԡ�
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		// setStroke(Stroke s)Ϊ Graphics2D ���������� Stroke
		g2d.drawLine(x1, y1, x2, y2);// ��ֱ��
	}

	boolean IsIn(int x, int y) { // ѡ��ֱ�ߵ��������жϵ�ǰ���Ƿ񿿽�ֱ�ߣ�������������ֱ�ߡ��ķ���
		if (Math.abs(x2 - x1) <= 5) {
			if (((x >= (x1 - 5)) && (x <= (x1 + 5))) && (y >= Math.min(y1, y2) && y <= Math.max(y1, y2))) {
				return true;
			}
		}
		if (Math.abs(y2 - y1) <= 5) {
			if (((y >= (y1 - 5)) && (y <= (y1 + 5))) && (x >= Math.min(x1, x2) && x <= Math.max(x1, x2))) {
				return true;
			}
		}
		if (Math.abs((x2 - x1) * (y - y1) - (y2 - y1) * (x - x1)) < Math.abs(5 * (x2 - x1))// ֱ�߷��̱���
				&& ((x >= Math.min(x1, x2)) && (x <= Math.max(x1, x2))
						&& ((y >= Math.min(y1, y2)) && (y <= Math.max(y1, y2))))) {
			return true;
		}
		return false;
	}
}

class Rect extends Drawing {// ������
	int gettype() {
		type = 5;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	boolean IsIn(int x, int y) {// �жϵ�ǰ���Ƿ��ھ�����
		if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
			return true;
		} else {
			return false;
		}
	}
}

class fillRect extends Drawing {// ʵ�ľ�����
	int gettype() {
		type = 6;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	boolean IsIn(int x, int y) {// �жϵ��Ƿ���ʵ�ľ�����
		if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
			return true;
		} else {
			return false;
		}
	}
}

class Oval extends Drawing {// ��Բ��

	int gettype() {
		type = 7;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	boolean IsIn(int x, int y) {// �жϵ��Ƿ�����Բ�ڣ�����drawOval�����������弰��Բ��ѧ�����Ƶ���
		double x0 = ((double) (x2 + x1) / 2);
		double y0 = ((double) (y2 + y1) / 2);
		double xi = Math.pow((x2 - x1), 2);
		double yi = Math.pow((y2 - y1), 2);
		if (4 * Math.pow((x - x0), 2) * yi + 4 * Math.pow((y - y0), 2) * xi <= (xi * yi)) {
			return true;
		}
		return false;
	}
}

class fillOval extends Drawing {// ʵ����Բ��

	int gettype() {
		type = 8;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	boolean IsIn(int x, int y) {
		double x0 = ((double) (x2 + x1) / 2);
		double y0 = ((double) (y2 + y1) / 2);
		double xi = Math.pow((x2 - x1), 2);
		double yi = Math.pow((y2 - y1), 2);
		if (4 * Math.pow((x - x0), 2) * yi + 4 * Math.pow((y - y0), 2) * xi <= (xi * yi)) {
			return true;
		}
		return false;
	}
}

class Circle extends Drawing {// Բ����

	int gettype() {
		type = 9;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
				Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
	}

	boolean IsIn(int x, int y) {// �жϵ��Ƿ���Բ�ڣ�����drawOval�����������弰��Բ��ѧ�����Ƶ���
		double a = Math.min(x1, x2);
		double b = Math.min(y1, y2);
		double d = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
		double x0 = (a + d / 2);
		double y0 = (b + d / 2);
		if ((Math.pow(x - x0, 2) + Math.pow(y - y0, 2)) <= Math.pow(d / 2, 2)) {
			return true;
		}
		return false;

	}
}

class fillCircle extends Drawing {// ʵ��Բ��
	int gettype() {
		type = 10;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
				Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
	}

	boolean IsIn(int x, int y) {// �жϵ��Ƿ���Բ�ڣ�����drawOval�����������弰��Բ��ѧ�����Ƶ���
		double a = Math.min(x1, x2);
		double b = Math.min(y1, y2);
		double d = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
		double x0 = (a + d / 2);
		double y0 = (b + d / 2);
		if ((Math.pow(x - x0, 2) + Math.pow(y - y0, 2)) <= Math.pow(d / 2, 2)) {
			return true;
		}
		return false;
	}
}

class RoundRect extends Drawing {// Բ�Ǿ�����
	int gettype() {
		type = 11;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
	}

	boolean IsIn(int x, int y) {// �жϵ��Ƿ���Բ�Ǿ����ڣ����ƾ��δ���
		if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
			return true;
		} else {
			return false;
		}
	}
}

class fillRoundRect extends Drawing {// ʵ��Բ�Ǿ�����
	int gettype() {
		type = 12;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
	}

	boolean IsIn(int x, int y) {
		if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
			return true;
		} else {
			return false;
		}
	}
}

class Pencil extends Drawing {// ��ʻ���
	int gettype() {
		type = 1;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
	}
	// in�����̳з���false������ʻ����ܱ�ѡ��
}

class Eraser extends Drawing { // ��Ƥ����
	int gettype() {
		type = 2;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(255, 255, 255));
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
	}
	// in�����̳з���false������Ƥ�����ܱ�ѡ��
}

class Word extends Drawing {// ����������
	int gettype() {
		type = 3;
		return type;
	}

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setFont(new Font(s2, fontype, ((int) stroke) * 18));// ��������
		if (s1 != null) {
			g2d.drawString(s1, x1, y1 + (int) stroke * 18);
		}
	}

	boolean IsIn(int x, int y) {
		if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
			return true;
		} else {
			return false;
		}
	}
}