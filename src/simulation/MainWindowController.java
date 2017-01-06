package simulation;
import geometry.Box;
import geometry.Vector2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import display.MainWindow;


public class MainWindowController implements IMainWindowController{

	private MainWindow gui;
	private ShapeContainer shapes;
	private LogicManager logic;
	
	private Box active_box = null;
	private Vector2D dragging_offset = new Vector2D();
	
	public MainWindowController(MainWindow in_gui, ShapeContainer in_shapes, LogicManager in_logic){
		gui = in_gui;
		shapes = in_shapes;
		logic = in_logic;		
	}
	
	@Override
    public void mouseReleased(MouseEvent e) {
		active_box = null;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
		restart();		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		active_box = null;
	}

	// Select boxes to drag
	@Override
	public void mousePressed(MouseEvent e) {
		
		for(Box box : shapes.getBoxes()){			
			// Check whether box was clicked
			Vector2D target_point = new Vector2D(e.getPoint());			
			if(box.includes(target_point)){				
				active_box = box;
				dragging_offset = box.getCenter();
				dragging_offset.subtract(target_point);
			}			
    	}   
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D target_point = new Vector2D(e.getPoint());	
		target_point.add(dragging_offset);
		if(active_box != null){			
			logic.moveBox(active_box,target_point);
			gui.updateView();
		}		    	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
		restart();
	}
	
	void restart(){

		ShapeContainer shapes = logic.initializeShapes();
		gui.resetShapes(shapes);
		gui.updateView();

	}
}
