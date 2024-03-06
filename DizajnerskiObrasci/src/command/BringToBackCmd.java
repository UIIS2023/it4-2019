package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command{
	
	private Shape shape;
	private DrawingModel model;
	private int p;
	private String textForLog;
	
	public BringToBackCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
		textForLog="Bring to back: " + shape.toString();
	}

	@Override
	public void execute() {
		
		int index=model.getIndexOfShape(shape);
		p=index;
		model.getAllShapes().remove(index);
		model.getAllShapes().add(0, shape);
	}

	@Override
	public void unexecute() {
		
		model.getAllShapes().remove(0);
		model.getAllShapes().add(p, shape);
		
	}
	
	@Override
	public String getTextForLog() {
		return textForLog;
	}
}
