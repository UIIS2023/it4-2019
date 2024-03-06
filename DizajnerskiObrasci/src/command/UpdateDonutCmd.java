package command;

import geometry.Donut;

public class UpdateDonutCmd implements Command {

	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();
	private String textForLog;
	
	public UpdateDonutCmd(Donut oldState, Donut newState) {
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
		oldState.setInnerRadius(newState.getInnerRadius());
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
		oldState.setInnerRadius(original.getInnerRadius());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());		
	}

	@Override
	public String getTextForLog() {
		return textForLog;
	}
}
