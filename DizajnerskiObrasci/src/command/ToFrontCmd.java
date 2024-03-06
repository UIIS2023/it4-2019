package command;

import java.util.Collections;
import geometry.Shape;
import mvc.DrawingModel;

public class ToFrontCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	private String textForLog;
	
	public ToFrontCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
		textForLog="To front: " + shape.toString();

	}

	@Override
	public void execute() {
		
		int index=model.getIndexOfShape(shape);
		Collections.swap(model.getAllShapes(), index+1, index);		
	}

	@Override
	public void unexecute() {
		
		int index=model.getIndexOfShape(shape);
		System.out.println(index + " " +model.getShape(index).toString() +" "+ model.getShape(index-1).toString());

		Collections.swap(model.getAllShapes(), index, index-1);
		System.out.println(index + " " +model.getShape(index).toString() +" "+ model.getShape(index-1).toString());

	}
	
	@Override
	public String getTextForLog() {
		return textForLog;
	}

	}


