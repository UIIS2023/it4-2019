package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command{

	private Shape shape;
	private DrawingModel model;
	private String textForLog;
	
	public ToBackCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
		textForLog="To back: " + shape.toString();
	}
	@Override
	public void execute() {
		
		int index=model.getIndexOfShape(shape);
		Collections.swap(model.getAllShapes(), index, index-1);
		
	}

	@Override
	public void unexecute() {

		int index=model.getIndexOfShape(shape);
		Collections.swap(model.getAllShapes(), index+1, index);
	}

	@Override
	public String getTextForLog() {
		return textForLog;
	}
	
	}
	

