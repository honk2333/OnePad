
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//�����˵����ܵ�������
public class Help extends JFrame {
	DrawPad dp;

	public Help(DrawPad drawpad) {
		dp = drawpad;
	}

	public void MainHelp() {
		JOptionPane.showMessageDialog(this, "OnePad�����ĵ�", "OnePad", JOptionPane.WARNING_MESSAGE);
	}

	public void AboutBook() {
		JOptionPane.showMessageDialog(dp, "OnePad" + "\n" + "�汾: 1.0" + "\n" + "����:    " + "\n" + "      ����� " + "\n"
				+ "      ������" + "\n" + "���ʱ��:  2019/7��", "OnePad", JOptionPane.WARNING_MESSAGE);
	}
}
