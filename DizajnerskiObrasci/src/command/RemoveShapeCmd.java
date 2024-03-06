package command;

import java.util.ArrayList;
import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command{

	private DrawingModel model;
	ArrayList<Shape> shapes;	
	StringBuilder stringBuilder = new StringBuilder();
	private String textForLog="Remove shapes: ";

	public RemoveShapeCmd(DrawingModel model, ArrayList<Shape> shapes) {
		this.model = model;
		this.shapes = shapes;
		for (int i = 0;i<shapes.size(); i++) {

			stringBuilder.append(shapes.get(i).toString()+"; ");
		}
		textForLog=textForLog+stringBuilder;
		textForLog= textForLog.substring(0, textForLog.length() - 2);
	}

	@Override
	public void execute() {
		
		for (int i = 0;i<shapes.size(); i++) {

			model.remove(shapes.get(i));
		}
	}

	@Override
	public void unexecute() {
		
		for (int i = 0;i<shapes.size(); i++) {

			model.addShape(shapes.get(i));
		}
	}
	
	@Override
	public String getTextForLog() {
		return textForLog;
	}
}
