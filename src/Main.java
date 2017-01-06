
import simulation.LogicManager;
import simulation.MainWindowController;
import simulation.ShapeContainer;

import display.MainFrame;
import display.MainWindow;


public class Main {

	 public static void main(String[] args) {
		 
	        LogicManager logic = new LogicManager();
	        
		 	ShapeContainer shapes = logic.initializeShapes();
		 	
	        MainFrame frame = new MainFrame();
	        MainWindow menu = new MainWindow(shapes);

	        MainWindowController controller = new MainWindowController(menu, shapes, logic);
	        menu.setupController(controller);
	        
	        frame.setCurrentGUI(menu);

	        frame.setVisible(true);
    }
}
