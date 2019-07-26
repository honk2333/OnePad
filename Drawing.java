
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.util.HashMap;
//图形绘制类，父类为基本图形单元，公共属性放到超类中，可以避免子类的重复定义
public class Drawing implements Serializable {

    int x1, x2, y1, y2;        //定义坐标属性
    int R, G, B;               //定义色彩RGB属性
    float stroke;              //定义线条粗细属性
    int type;                  //定义字体属性
    String s1;                 //定义字体风格：粗体
    String s2;                 //定义字体风格：斜体
    int typechoice;// 记录图形的属性，并与当前属性相匹配

    int gettypechoice() {
        return typechoice;
    }// 获取填充用的typechoice

    void draw(Graphics2D g2d) {
    }//定义绘图函数

    boolean in(int x, int y) {
        return false;
    }// 判断当前的点是否在图形内部活附近，用于选择图形
}

class Line extends Drawing//直线类
{
    int gettypechoice() {
        typechoice = 4;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));// 为 Graphics2D上下文设置色彩Paint属性
        g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL));
        // setStroke(Stroke s)为 Graphics2D上下文设置 字体粗细属性
        g2d.drawLine(x1, y1, x2, y2);//画直线
    }

    boolean in(int x, int y) {//选中直线的条件：判断当前点是否靠近直线
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
        if (Math.abs((x2 - x1) * (y - y1) - (y2 - y1) * (x - x1)) < Math.abs(5 * (x2 - x1))// 直线方程变形
                && ((x >= Math.min(x1, x2)) && (x <= Math.max(x1, x2))
                && ((y >= Math.min(y1, y2)) && (y <= Math.max(y1, y2))))) {
            return true;
        }
        return false;
    }
}

class Rect extends Drawing {//矩形类

    int gettypechoice() {
        typechoice = 5;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    boolean in(int x, int y) {//判断当前点是否在矩形内
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}

class fillRect extends Drawing {//实心矩形类

    int gettypechoice() {
        typechoice = 6;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    boolean in(int x, int y) {// 判断点是否在实心矩形内
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}

class Oval extends Drawing {//椭圆类

    int gettypechoice() {
        typechoice = 7;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    boolean in(int x, int y) {//判断点是否在再椭圆内
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

class fillOval extends Drawing {//实心椭圆类

    int gettypechoice() {
        typechoice = 8;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    boolean in(int x, int y) {
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

class Circle extends Drawing {//圆形类

    int gettypechoice() {
        typechoice = 9;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2),
                Math.abs(y1 - y2)), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }

    boolean in(int x, int y) {// 判断点是否在圆内
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

class fillCircle extends Drawing {//实心圆类

    int gettypechoice() {
        typechoice = 10;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2),
                Math.abs(y1 - y2)), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }

    boolean in(int x, int y) {// 判断点是否在圆内
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

class RoundRect extends Drawing {//圆角矩形类

    int gettypechoice() {
        typechoice = 11;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
    }

    boolean in(int x, int y) {//  判断点是否在圆角矩形内（近似矩形处理）
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }

}

class fillRoundRect extends Drawing {//实心圆角矩形类

    int gettypechoice() {
        typechoice = 12;
        return typechoice;
    }

    void draw(Graphics2D g2d) {  //判断点是否在圆角矩形内（近似矩形处理）
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
    }

    boolean in(int x, int y) {
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }

}

class Pencil extends Drawing {////随笔画类

    int gettypechoice() {
        typechoice = 3;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
    // in函数继承返回false，即随笔画不能被选中
}
class Eraser extends Drawing {////随笔画类

    int gettypechoice() {
        typechoice = 2;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(255, 255, 255));
        g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
    // in函数继承返回false，即随笔画不能被选中
}





class Word extends Drawing {//输入文字类

    int gettypechoice() {
        typechoice = 13;
        return typechoice;
    }

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setFont(new Font(s2, type, ((int) stroke) * 18));//设置字体
        if (s1 != null) {
            g2d.drawString(s1, x1, y1 + (int) stroke * 18);
        }
    }

    boolean in(int x, int y) {
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            return true;
        } else {
            return false;
        }
    }
}