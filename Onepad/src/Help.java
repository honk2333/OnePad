
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//�����˵����ܵ�������
public class Help extends JFrame {
    private DrawPad drawpad = null;

    Help(DrawPad dp) {
        drawpad = dp;
    }

    public void MainHelp() {
        JOptionPane.showMessageDialog(this, "Draw_pad�����ĵ�", "Draw_pad", JOptionPane.WARNING_MESSAGE);
    }

    public void AboutBook() {
        JOptionPane.showMessageDialog(drawpad, "Draw_pad" + "\n" + "�汾: 1.0" + "\n"
                + "����:    " + "\n"
                + "      ����� " + "\n"
        		+ "      ������" + "\n"
                + "���ʱ��:  2019/7��", "Draw_pad", JOptionPane.WARNING_MESSAGE);
    }
}
