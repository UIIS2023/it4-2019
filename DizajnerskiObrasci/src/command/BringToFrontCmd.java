package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command{


	private Shape shape;
	private DrawingModel model;
	private int p;
	private String textForLog;
	
	public BringToFrontCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
		textForLog="Bring to front: " + shape.toString();
	}

	@Override
	public void execute() {
		
		int index=model.getIndexOfShape(shape);
		p=index;
		model.getAllShapes().remove(index);
		model.getAllShapes().add(model.getAllShapes().size(), shape);
	}

	@Override
	public void unexecute() {
		
		model.getAllShapes().remove(model.getAllShapes().size()-1);
		model.getAllShapes().add(p, shape);
	}
	
	public String getTextForLog() {
		return textForLog;
	}
	
}
