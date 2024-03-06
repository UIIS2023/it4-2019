package observer;

import java.util.Observable;
import java.util.Observer;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class UpdateButtons implements Observer {
	
	private DrawingFrame frame;
	private DrawingModel model;

	 public UpdateButtons(DrawingFrame frame,DrawingModel model) {
			this.frame=frame;
			this.model=model;
		}
	 
	 private int getNumberOfSelectedShapes() { 
			int n=0;
			
			for (int i = 0; i <model.getAllShapes().size(); i++) {
				if(model.getShape(i).isSelected()) {
					n++;
				}
			}
			return n;
		}

	@Override
	public void update(Observable o, Object arg) {
		
		if(getNumberOfSelectedShapes()==0) 
		{ 
			frame.getBtnActionEdit().setEnabled(false);
			frame.getBtnActionDelete().setEnabled(false);	
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);
		}
		else if(getNumberOfSelectedShapes()==1)
		{			
			frame.getBtnActionEdit().setEnabled(true);
			frame.getBtnActionDelete().setEnabled(true);
			frame.getBtnToFront().setEnabled(true);
			frame.getBtnToBack().setEnabled(true);
			frame.getBtnBringToFront().setEnabled(true);
			frame.getBtnBringToBack().setEnabled(true);
		}
		else if(getNumberOfSelectedShapes()>1)
		{ 
			
			frame.getBtnActionEdit().setEnabled(false);
			frame.getBtnActionDelete().setEnabled(true);
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);	
			
		}
		

	}

	}


