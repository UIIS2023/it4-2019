package command;

import geometry.Shape;
import mvc.DrawingModel;

public class AddShapeCmd implements Command{

	private Shape shape;
	private DrawingModel model;	
	private String textForLog;
	
	public AddShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
		textForLog="Add: " + shape.toString();
	}

	@Override
	public void execute() {
		
		model.addShape(shape);
		
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	@Override
	public String getTextForLog() {
		return textForLog;
	}
}
