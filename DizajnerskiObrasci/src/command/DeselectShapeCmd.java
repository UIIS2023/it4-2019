package command;

import geometry.Shape;
import mvc.DrawingModel;

public class DeselectShapeCmd implements Command {


	private Shape shape;
	private DrawingModel model;
	private String textForLog;

	public DeselectShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;	
		textForLog="Deselect: " + shape.toString();
	}

	@Override
	public void execute() {

		int index=model.getIndexOfShape(shape);
		model.getShape(index).setSelected(false);
	}

	@Override
	public void unexecute() {	
		
		int index=model.getIndexOfShape(shape);
		model.getShape(index).setSelected(true);
	}
	@Override
	public String getTextForLog() {
		
		return textForLog;
	}
}
