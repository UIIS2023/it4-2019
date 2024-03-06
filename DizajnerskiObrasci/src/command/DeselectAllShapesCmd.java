package command;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingModel;

public class DeselectAllShapesCmd implements Command{

	private DrawingModel model;
	private String textForLog;
	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	
	public DeselectAllShapesCmd(DrawingModel model) {
		this.model = model;		
		textForLog="Deselect all shapes";
	}

	@Override
	public void execute() {
		for(Shape shape: model.getAllShapes()) {
			if(shape.isSelected()) {
				shape.setSelected(false);
				selectedShapes.add(shape);
			}
		}
		
	}

	@Override
	public void unexecute() {
		selectedShapes.forEach(shape -> shape.setSelected(true));		
	}
	
	@Override
	public String getTextForLog() {
		// TODO Auto-generated method stub
		return textForLog;
	}
}
