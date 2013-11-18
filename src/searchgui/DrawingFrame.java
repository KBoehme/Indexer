package searchgui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class DrawingFrame extends JFrame {

	public DrawingFrame() throws IOException {
		this.setTitle("Drawing");
        String path = "image.png";
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        JLabel label = new JLabel(new ImageIcon(image));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        f.setLocation(100,100);
        f.setSize(1000,500);
        f.setVisible(true);
	}
}