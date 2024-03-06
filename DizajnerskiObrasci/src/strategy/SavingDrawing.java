package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mvc.DrawingModel;

public class SavingDrawing implements Saving{


	private DrawingModel model;
	
	public SavingDrawing(DrawingModel model) {
		this.model=model;
	}
	
	@Override
	public void save() throws IOException {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File( "C:/Users/doljn/Desktop"));
		fileChooser.setDialogTitle("Save a draw");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".ser", "ser");
		fileChooser.setFileFilter(filter);
		
		int result = fileChooser.showSaveDialog(null);
		
		if(result==JFileChooser.APPROVE_OPTION)  {
			
			File choosenFile = new File(fileChooser.getSelectedFile().getAbsolutePath()+".ser");

			if (choosenFile.exists()) { 
				JOptionPane.showMessageDialog(null, "File " +choosenFile.getName() +" already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				FileOutputStream fileOutputStream;
				
				ObjectOutputStream objectOutputStream;
				
					fileOutputStream = new FileOutputStream(choosenFile);
					objectOutputStream = new ObjectOutputStream(fileOutputStream);

					objectOutputStream.writeObject(model.getAllShapes());
					objectOutputStream.close();
					fileOutputStream.close();

			}
		}
		else {
			return;
		}
		
		
	}

}
