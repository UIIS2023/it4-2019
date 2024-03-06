package command;

import geometry.Shape;
import mvc.DrawingModel;

public class SelectShapeCmd implements Command{

	private Shape shape;
	private DrawingModel model;
	private String textForLog;

	
	public SelectShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;	
		textForLog="Select: " + shape.toString();
	}

	@Override
	public void execute() {

		int index=model.getIndexOfShape(shape);
		model.getShape(index).setSelected(true);
	}

	@Override
	public void unexecute() {	
		
		int index=model.getIndexOfShape(shape);
		model.getShape(index).setSelected(false);	
	}
	
	@Override
	public String getTextForLog() {
		return textForLog;
	}
}
