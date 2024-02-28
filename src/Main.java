// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import javax.swing.*;
import java.awt.*;
public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame();
        frame.setSize(screenSize);
        Phase1 intro = new Phase1((int)screenSize.getWidth(), (int)screenSize.getHeight());
        frame.add(intro);
        frame.setVisible(true);
    }
}