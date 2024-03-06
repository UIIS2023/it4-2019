package command;

import geometry.Circle;

public class UpdateCircleCmd implements Command{

	private Circle oldState;
	private Circle newState;
	private Circle original= new Circle();	
	private String textForLog;


	public UpdateCircleCmd(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
		textForLog ="Update: " + oldState.toString() + " ->" + newState.toString();
	}

	@Override
	public void execute() {

		original=oldState.clone();
		oldState.setCenter(newState.getCenter());
		try {
			oldState.setRadius(newState.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
		
	}

	@Override
	public void unexecute() {

		oldState.setCenter(original.getCenter());
		try {
			oldState.setRadius(original.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
		
	}
	
	@Override
	public String getTextForLog() {
		return textForLog;
	}
}
