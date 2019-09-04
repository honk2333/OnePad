
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//帮助菜单功能的事项类
public class Help extends JFrame {
	DrawPad dp;

	public Help(DrawPad drawpad) {
		dp = drawpad;
	}

	public void MainHelp() {
		JOptionPane.showMessageDialog(this, "OnePad帮助文档", "OnePad", JOptionPane.WARNING_MESSAGE);
	}

	public void AboutBook() {
		JOptionPane.showMessageDialog(dp, "OnePad" + "\n" + "版本: 1.0" + "\n" + "作者:    " + "\n" + "      王洪科 " + "\n"
				+ "      刘炯驿" + "\n" + "完成时间:  2019/7月", "OnePad", JOptionPane.WARNING_MESSAGE);
	}
}
