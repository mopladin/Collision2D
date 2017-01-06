package display;
import java.awt.Dimension;

import javax.swing.JFrame;


public class MainFrame  extends JFrame{

	static final long serialVersionUID = -8457065345977698944L;
	private static final Dimension MIN_SIZE = new Dimension(500, 500);
	private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);

	
	public MainFrame() {
        super("2D collision"); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(MIN_SIZE);
        this.setSize(DEFAULT_SIZE);
        this.setLocationRelativeTo(null);
    }
	 
	 public void setCurrentGUI(MainWindow gui) {
        this.getContentPane().removeAll();
        this.getContentPane().add(gui.getComponent());
        this.revalidate();
    } 
	
}
