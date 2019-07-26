
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//帮助菜单功能的事项类
public class Help extends JFrame {
    private DrawPad drawpad = null;

    Help(DrawPad dp) {
        drawpad = dp;
    }

    public void MainHelp() {
        JOptionPane.showMessageDialog(this, "Tuhuaban帮助文档", "Tuhuaban", JOptionPane.WARNING_MESSAGE);
    }

    public void AboutBook() {
        JOptionPane.showMessageDialog(drawpad, "Tuhuaban" + "\n"
                + "作者:    " + "\n"
                + "     王洪科 " + "\n"
        		+ "   刘炯驿" + "\n"

                + "完成时间:  2019/7/28", "Tuhuaban", JOptionPane.WARNING_MESSAGE);
    }
}
