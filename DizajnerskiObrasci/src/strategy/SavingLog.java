package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mvc.DrawingController;

public class SavingLog implements Saving {

	private DrawingController controller;
	
	public SavingLog(DrawingController controller) {
		
		this.controller = controller;
	}
	
	@Override
	public void save() throws IOException {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File( "C:/Users/doljn/Desktop"));
		fileChooser.setDialogTitle("Save a log");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setFileFilter(filter);
		
		int result = fileChooser.showSaveDialog(null);
		if(result==JFileChooser.APPROVE_OPTION)  {
			
			File choosenFile = new File(fileChooser.getSelectedFile().getAbsolutePath()+".txt");
			
			if (choosenFile.exists()) { 
				
				JOptionPane.showMessageDialog(null, "File " +choosenFile.getName() +" already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				
				FileOutputStream fileOutputStream = new FileOutputStream(choosenFile);
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
				
				for (int i = 0; i < controller.getLogList().size(); i++) {
					
					bufferedWriter.write(controller.getLogList().getElementAt(i));
					bufferedWriter.newLine();
				}
		 
				bufferedWriter.close();
			}
		}
		else {
			return;
		}
	
	}

}


