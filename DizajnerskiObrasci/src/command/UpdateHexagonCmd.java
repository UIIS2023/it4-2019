package command;

import geometry.HexagonAdapter;

public class UpdateHexagonCmd  implements Command{

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original;
	private String textForLog;
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
		textForLog ="Update: " + oldState.toString() + " ->" + newState.toString();

	}

	@Override
	public void execute() {
		
		original=oldState.clone();
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setRadius(newState.getRadius());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());

		
	}

	@Override
	public void unexecute() {
		
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setRadius(original.getRadius());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());		
	}
	@Override
	public String getTextForLog() {
		return textForLog;
	}
}
