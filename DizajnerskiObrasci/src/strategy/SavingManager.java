package strategy;

import java.io.IOException;

public class SavingManager implements Saving{

	private Saving saving;
	
	public SavingManager(Saving saving) {
		this.saving= saving;
	}
	
	@Override
	public void save() throws IOException {
		saving.save();
		
	}

}
