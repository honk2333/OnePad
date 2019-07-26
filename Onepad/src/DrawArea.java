
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

    int chooseni = 0;// 褰撳墠閫変腑鍥惧舰鐨勬暟缁勪笅鏍�
    int x0, y0;//璁板綍绉诲姩鍥惧舰榧犳爣璧峰浣嶇疆
    private int chosenStatus = 3;//璁剧疆榛樿鍩烘湰鍥惧舰鐘舵�佷负闅忕瑪鐢�
    int index = 0;//褰撳墠宸茬粡缁樺埗鐨勫浘褰㈡暟鐩�
    private Color color = Color.black;//褰撳墠鐢荤瑪鐨勯鑹�
    int R, G, B;//鐢ㄦ潵瀛樻斁褰撳墠棰滆壊鐨勫僵鍊�
    int f1, f2;//鐢ㄦ潵瀛樻斁褰撳墠瀛椾綋鐨勯鏍�
    String style;// 瀛樻斁褰撳墠瀛椾綋
    float stroke = 1.0f;//璁剧疆鐢荤瑪鐨勭矖缁� 锛岄粯璁ょ殑鏄� 1.0
    JTextArea tarea = new JTextArea("");
    int tx, ty;

    DrawArea(DrawPad dp) {
        drawpad = dp;
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        // 鎶婇紶鏍囪缃垚鍗佸瓧褰�
        setBackground(Color.white);// 璁剧疆缁樺埗鍖虹殑鑳屾櫙鏄櫧鑹�
        addMouseListener(new MouseA());// 娣诲姞榧犳爣浜嬩欢
        addMouseMotionListener(new MouseB());
        createNewitem();
    }

    public void paintComponent(Graphics g) {// repaint()闇�瑕佽皟鐢�
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int j = 0;
        while (j <= index) {
            draw(g2d, itemList[j]);
            j++;
        } // 灏唅temList鏁扮粍閲嶇敾涓�閬�
    }

    void draw(Graphics2D g2d, Drawing i) {
        i.draw(g2d);// 灏嗙敾绗斾紶鍒颁釜鍚勭被鐨勫瓙绫讳腑
    }

    //鏂板缓涓�涓浘褰㈢殑鍩烘湰鍗曞厓瀵硅薄鐨勭▼搴忔
    void createNewitem() {
        if (chosenStatus == 13)// 瀛椾綋鐨勮緭鍏ュ厜鏍囩浉搴旂殑璁剧疆涓烘枃鏈緭鍏ユ牸寮�
            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        switch (chosenStatus) {// button瑙﹀彂鏀瑰彉currentChoice鐨勫�硷紝鐢辨寰楀埌鍚勪簨浠剁殑鍏ュ彛
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
            /*case 14:
                itemList[index] = new OilPaint();*/
        }
        if (chosenStatus >= 3 && chosenStatus <= 13) {
            itemList[index].type = chosenStatus;
            itemList[index].R = R;
            itemList[index].G = G;
            itemList[index].B = B;
            itemList[index].stroke = stroke;
        }

    }

    public void setIndex(int x) {//璁剧疆index鐨勬帴鍙�
        index = x;
    }

    public int getIndex() {// 璇诲彇index鐨勬帴鍙�
        return index;
    }

    public void setColor(Color color)//璁剧疆棰滆壊鐨勫��
    {
        this.color = color;
    }

    public void setStroke(float f)//璁剧疆鐢荤瑪绮楃粏鐨勬帴鍙�
    {
        stroke = f;
    }

    public void chooseColor()//閫夋嫨褰撳墠棰滆壊
    {
        color = JColorChooser.showDialog(drawpad, "璇烽�夋嫨棰滆壊", color);
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

    public void changeColor()// 鏀瑰彉褰撳墠鍥剧墖鐨勯鑹�
    {
        color = JColorChooser.showDialog(drawpad, "璇烽�夋嫨棰滆壊", color);
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

    public void setStroke()//鐢荤瑪绮楃粏鐨勮皟鏁�
    {
        String input;
        input = JOptionPane.showInputDialog("璇疯緭鍏ョ敾绗旂殑绮楃粏( >0 )");
        try {
            stroke = Float.parseFloat(input);

        } catch (Exception e) {
            stroke = 1.0f;

        }
        itemList[index].stroke = stroke;

    }

    public void changeStroke()// 鐢荤瑪绮楃粏鐨勬敼鍙橈紙涓昏閽堝绌哄績鍥惧舰銆佺洿绾裤�侀殢绗旂敾锛�
    {
        String input;
        input = JOptionPane.showInputDialog("璇疯緭鍏ョ敾绗旂殑绮楃粏( >0 )");
        try {
            stroke = Float.parseFloat(input);

        } catch (Exception e) {
            stroke = 1.0f;

        }
        itemList[chooseni].stroke = stroke;

    }

    public void setChosenStatus(int i)// 璁剧疆褰撳墠閫夋嫨锛坆utton瑙﹀彂鏃朵娇鐢級
    {
        chosenStatus = i;
    }

    public void changeText() {//淇敼宸叉湁鏂囧瓧
        String input;
        input = JOptionPane.showInputDialog("璇疯緭鍏ヤ綘瑕佷慨鏀逛负鐨勬枃瀛�");
        itemList[chooseni].s1 = input;//閲嶈閫変腑鏂囨湰妗嗙殑鍚勫弬鏁�
        itemList[chooseni].type = f1 + f2;
        itemList[chooseni].s2 = style;
        itemList[chooseni].stroke = stroke;
        itemList[chooseni].R = R;
        itemList[chooseni].G = G;
        itemList[chooseni].B = B;
    }

    public void setFont(int i, int font)//璁剧疆瀛椾綋
    {
        if (i == 1) {
            f1 = font;
        } else
            f2 = font;
    }

    public void fillColor(Drawing nowdrawing) {// 濉厖
        int choice = nowdrawing.gettypechoice();// 鐢ㄤ簬鍒ゆ柇濉厖鍥惧舰绫诲瀷
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

    public void deletePaint(Drawing nowdrawing) {// 鍒犻櫎
        int choice = nowdrawing.gettypechoice();
        if (choice >= 3 && choice <= 13) {
            itemList[chooseni] = new Line();
        }
    }

    // 榧犳爣浜嬩欢MouseA绫荤户鎵夸簡MouseAdapter锛岀敤鏉ュ畬鎴愰紶鏍囩殑鍝嶅簲浜嬩欢鐨勬搷浣�
    class MouseA extends MouseAdapter {
        public void mouseEntered(MouseEvent me) {
            // 榧犳爣杩涘叆
            drawpad.setStratBar("榧犳爣杩涘叆鍦細[" + me.getX() + " ," + me.getY() + "]");// 璁剧疆鐘舵�佹爮鎻愮ず
        }

        public void mouseExited(MouseEvent me) {
            // 榧犳爣閫�鍑�
            drawpad.setStratBar("榧犳爣閫�鍑哄湪锛歔" + me.getX() + " ," + me.getY() + "]");
        }

        public void mousePressed(MouseEvent me) {
            // 榧犳爣鎸変笅
            drawpad.setStratBar("榧犳爣鎸変笅鍦細[" + me.getX() + " ," + me.getY() + "]");
            if (chosenStatus >= 15 && chosenStatus <= 21)
            // 鍒犻櫎锛岀Щ鍔紝鏇存敼澶у皬锛屾洿鏀归鑹诧紝鏇存敼绾垮瀷锛屽～鍏呭叚绉嶆搷浣滈兘闇�瑕侀�夊畾鍥惧舰
            {
                for (chooseni = index - 1; chooseni >= 0; chooseni--) {
                    // 浠庡悗鍒板墠瀵绘壘褰撳墠榧犳爣鏄惁鍦ㄦ煇涓浘褰㈠唴閮�
                    if (itemList[chooseni].in(me.getX(), me.getY())) {
                        if (chosenStatus == 16)// 绉诲姩鍥惧舰闇�瑕佽褰昿ress鏃剁殑鍧愭爣
                        {
                            x0 = me.getX();
                            y0 = me.getY();
                        }
                        break;// 鍏跺畠鎿嶄綔鍙渶鎵惧埌currenti鍗冲彲
                    }
                }
                if (chooseni >= 0) {// 鏈夊浘褰㈣閫変腑
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// 鏇存敼榧犳爣鏍峰紡涓烘墜褰�
                    if (chosenStatus == 20) {// 瑙﹀彂濉厖
                        fillColor(itemList[chooseni]);
                        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// 榧犳爣鏍峰紡鍙樺洖鍗佸瓧鑺�
                        repaint();
                    } else if (chosenStatus == 15) {// 瑙﹀彂鍒犻櫎
                        deletePaint(itemList[chooseni]);
                        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                        repaint();
                    } else if (chosenStatus == 18) {// 鏀瑰彉宸叉湁鍥惧舰鐨勯鑹�
                        changeColor();
                        repaint();
                    } else if (chosenStatus == 19) {// 鏀瑰彉宸叉湁鍥惧舰鐨勭嚎鍨�
                        changeStroke();
                        repaint();
                    } else if (chosenStatus == 21) {//鏀瑰彉宸叉湁鏂囧瓧
                        changeText();
                        repaint();
                    }
                }
            } else {
                itemList[index].x1 = itemList[index].x2 = me.getX();
                itemList[index].y1 = itemList[index].y2 = me.getY();// x1,x2,y1,y2鍒濆鍖�
                // 濡傛灉褰撳墠閫夋嫨涓洪殢绗旂敾鍒欒繘琛屼笅闈㈢殑鎿嶄綔
                if (chosenStatus == 3) {
                    itemList[index].x1 = itemList[index].x2 = me.getX();
                    itemList[index].y1 = itemList[index].y2 = me.getY();
                    index++;
                    createNewitem();//鍒涘缓鏂扮殑鍥惧舰鐨勫熀鏈崟鍏冨璞�
                }
                //濡傛灉閫夋嫨鍥惧舰鐨勬枃瀛楄緭鍏ワ紝鍒欒繘琛屼笅闈㈢殑鎿嶄綔
                if (chosenStatus == 13) {
                    tx = me.getX();
                    ty = me.getY();
                    tarea.setBounds(tx, ty, 0, 0);
                    tarea.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
                }
            }
        }

        public void mouseReleased(MouseEvent me) {
            // 榧犳爣鏉惧紑
            drawpad.setStratBar("榧犳爣鏉惧紑鍦細[" + me.getX() + " ," + me.getY() + "]");
            if (chosenStatus == 16) {// 绉诲姩缁撴潫

                if (chooseni >= 0) {// 榧犳爣鎴愬姛閫夋嫨浜嗘煇涓浘褰�
                    itemList[chooseni].x1 = itemList[chooseni].x1 + me.getX() - x0;
                    itemList[chooseni].y1 = itemList[chooseni].y1 + me.getY() - y0;
                    itemList[chooseni].x2 = itemList[chooseni].x2 + me.getX() - x0;
                    itemList[chooseni].y2 = itemList[chooseni].y2 + me.getY() - y0;
                    repaint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }
            } else if (chosenStatus == 17) {// 鏀惧ぇ缂╁皬缁撴潫
                if (chooseni >= 0) {// 榧犳爣鎴愬姛閫夋嫨浜嗘煇涓浘褰�
                    itemList[chooseni].x2 = me.getX();
                    itemList[chooseni].y2 = me.getY();
                    repaint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }
            } else {
                if (chosenStatus == 3) {// 闅忕瑪鐢荤粯鍒剁粨鏉�
                    itemList[index].x1 = me.getX();
                    itemList[index].y1 = me.getY();
                } else if (chosenStatus == 13) {// 鏂囨湰妗嗙粯鍒剁粨鏉�
                    tarea.setBounds(Math.min(tx, me.getX()) + 130, Math.min(ty, me.getY()), Math.abs(tx - me.getX()), Math.abs(ty - me.getY()));//缁樺埗鏂囨湰妗�
                    String input;
                    input = JOptionPane.showInputDialog("璇疯緭鍏ヤ綘瑕佸啓鍏ョ殑鏂囧瓧");
                    tarea.setText(input);
                    itemList[index].s1 = input;
                    itemList[index].type = f1 + f2;//璁剧疆绮椾綋銆佹枩浣�
                    itemList[index].x2 = me.getX();
                    itemList[index].y2 = me.getY();
                    itemList[index].s2 = style;//璁剧疆瀛椾綋

                    index++;
                    chosenStatus = 13;
                    createNewitem();//鍒涘缓鏂扮殑鍥惧舰鐨勫熀鏈崟鍏冨璞�
                    repaint();
                    tarea.setText("");//閲嶈鏂囨湰妗嗭紝涓轰笅涓�娆′娇鐢ㄥ仛鍑嗗
                    tarea.setBounds(tx, ty, 0, 0);
                }
                if (chosenStatus >= 3 && chosenStatus <= 12) {
                    itemList[index].x2 = me.getX();
                    itemList[index].y2 = me.getY();
                    repaint();
                    index++;
                    createNewitem();//鍒涘缓鏂扮殑鍥惧舰鐨勫熀鏈崟鍏冨璞�
                }
            }
        }
    }

    // 榧犳爣浜嬩欢MouseB缁ф壙浜哅ouseMotionAdapter锛岀敤鏉ュ鐞嗛紶鏍囩殑婊氬姩涓庢嫋鍔�
    class MouseB extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me)//榧犳爣鐨勬嫋鍔�
        {
            drawpad.setStratBar("榧犳爣鎷栧姩鍦細[" + me.getX() + " ," + me.getY() + "]");
            if (chosenStatus == 3) {// 浠绘剰绾跨殑鐢绘硶
                itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX();
                itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
                index++;
                createNewitem();//鍒涘缓鏂扮殑鍥惧舰鐨勫熀鏈崟鍏冨璞�
                repaint();
            } else if (chosenStatus == 16) {
                if (chooseni >= 0) {// 绉诲姩鐨勮繃绋�
                    itemList[chooseni].x1 = itemList[chooseni].x1 + me.getX() - x0;
                    itemList[chooseni].y1 = itemList[chooseni].y1 + me.getY() - y0;
                    itemList[chooseni].x2 = itemList[chooseni].x2 + me.getX() - x0;
                    itemList[chooseni].y2 = itemList[chooseni].y2 + me.getY() - y0;
                    x0 = me.getX();
                    y0 = me.getY();
                    repaint();
                }
            } else if (chosenStatus == 17) {// 鏀惧ぇ缂╁皬鐨勮繃绋�
                if (chooseni >= 0) {
                    itemList[chooseni].x2 = me.getX();
                    itemList[chooseni].y2 = me.getY();
                    repaint();
                }
            } else if (chosenStatus >= 3 && chosenStatus <= 12) {// 缁樺埗鍥惧舰鐨勮繃绋�
                itemList[index].x2 = me.getX();
                itemList[index].y2 = me.getY();
                repaint();
            }
            //repaint();
        }

        public void mouseMoved(MouseEvent me)//榧犳爣鐨勭Щ鍔�
        {
            drawpad.setStratBar("榧犳爣绉诲姩鍦細[" + me.getX() + " ," + me.getY() + "]");
            for (chooseni = index - 1; chooseni >= 0; chooseni--) {
                // 浠庡悗鍒板墠瀵绘壘褰撳墠榧犳爣鏄惁鍦ㄦ煇涓浘褰㈠唴閮�
                if (itemList[chooseni].in(me.getX(), me.getY())) {
                    break;// 鍏跺畠鎿嶄綔鍙渶鎵惧埌currenti鍗冲彲
                }
            }
            if (chooseni >= 0) {// 鏈夊浘褰㈣閫変腑
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// 鏇存敼榧犳爣鏍峰紡涓虹澶�
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        }
    }

}
