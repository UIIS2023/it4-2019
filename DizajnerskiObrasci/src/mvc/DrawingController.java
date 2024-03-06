package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.DeselectAllShapesCmd;
import command.DeselectShapeCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import dialogs.DlgAddCircle;
import dialogs.DlgAddDonut;
import dialogs.DlgAddHexagon;
import dialogs.DlgAddRectangle;
import dialogs.DlgEditCircle;
import dialogs.DlgEditDonut;
import dialogs.DlgEditHexagon;
import dialogs.DlgEditLine;
import dialogs.DlgEditPoint;
import dialogs.DlgEditRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.UpdateButtons;
import strategy.Saving;
import strategy.SavingDrawing;
import strategy.SavingLog;
import strategy.SavingManager;



public class DrawingController extends Observable{
	DrawingModel model;
	DrawingFrame frame;
	
	private boolean lineWaitingForSecondPoint = false;
	private Point lineFirstPoint;
	private ArrayList <Command> undoCommandsList = new ArrayList<Command>();
	private ArrayList<Command> redoCommandsList = new ArrayList<Command>();
	private DefaultListModel<String> logList;
	private Scanner scanner;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model=model;
		this.frame=frame;
		logList = frame.getDlm();	
         }


	public int getSelected() {
		for (int i = model.getAllShapes().size()-1; i >= 0; i--) {
			if (model.getShape(i).isSelected()) {
				return i;
			}
		}
		return -1;
	}

	public void setShape(int index, Shape shape) {
		model.getAllShapes().set(index, shape);
	}
	
	public boolean isEmpty() {
		return model.getAllShapes().isEmpty();
	}
	
	public void select(MouseEvent e) {
		
		for (int i = model.getAllShapes().size()-1; i >= 0; i--) {
			if (model.getShape(i).contains(e.getX(), e.getY())) {
				if(model.getShape(i).isSelected()){
					DeselectShapeCmd cmd = new DeselectShapeCmd(model.getShape(i),model);
					cmd.execute();
					logList.addElement(cmd.getTextForLog());
					undoCommandsList.add(undoCommandsList.size(),cmd);
					frame.getBtnUndo().setEnabled(true);
					if(frame.getBtnRedo().isEnabled()){
						frame.getBtnRedo().setEnabled(false);
						int index = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
					}	
				}else {
					SelectShapeCmd cmd = new SelectShapeCmd(model.getShape(i),model);
					cmd.execute();
					logList.addElement(cmd.getTextForLog());
					undoCommandsList.add(undoCommandsList.size(),cmd);
					frame.getBtnUndo().setEnabled(true);
					if(frame.getBtnRedo().isEnabled()){
						frame.getBtnRedo().setEnabled(false);
						int index = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
					}
				}
				setChanged();
				notifyObservers();
				frame.repaint();
				return;
			}
			
		}
		for (int i = model.getAllShapes().size()-1; i >= 0; i--) {
			if (!model.getShape(i).contains(e.getX(), e.getY())&&model.getShape(i).isSelected()) {
				DeselectAllShapesCmd cmd = new DeselectAllShapesCmd(model);
				cmd.execute();
				logList.addElement(cmd.getTextForLog());
				undoCommandsList.add(undoCommandsList.size(),cmd);
				frame.getBtnUndo().setEnabled(true);
				if(frame.getBtnRedo().isEnabled()){
					frame.getBtnRedo().setEnabled(false);
					int index = redoCommandsList.indexOf(cmd);
					redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
				}
				setChanged();
				notifyObservers();
				frame.repaint();
			}		
		}	
	}

	public void deselect() {
		int n=0;
		
		for (int i = 0; i <model.getAllShapes().size(); i++) {
			if(model.getShape(i).isSelected()) {
				n++;
			}
		}
		if(n==0)
		{
			return;
		}
		else {
		DeselectAllShapesCmd cmd = new DeselectAllShapesCmd(model);
		cmd.execute();
		logList.addElement(cmd.getTextForLog());
		undoCommandsList.add(undoCommandsList.size(),cmd);
		frame.getBtnUndo().setEnabled(true);
		if(frame.getBtnRedo().isEnabled()){
			frame.getBtnRedo().setEnabled(false);
			int index = redoCommandsList.indexOf(cmd);
			redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
		}
		frame.repaint();
		setChanged();
		notifyObservers();
		}
	}
		
	 public void remove() {
			ArrayList<Shape> shapes = new ArrayList<>();
			if(frame.getBtnActionDelete().isEnabled()) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to remove selected shape?", "Warning!", JOptionPane.YES_NO_OPTION) == 0) {
					
					for (int i = 0;i<model.getAllShapes().size(); i++) {
						if (model.getShape(i).isSelected()) {
							shapes.add(model.getShape(i));
						}
					}
					RemoveShapeCmd cmd = new RemoveShapeCmd(model,shapes);
					cmd.execute();
					logList.addElement(cmd.getTextForLog());
					setChanged();
					notifyObservers();
					undoCommandsList.add(undoCommandsList.size(),cmd);
					frame.getBtnUndo().setEnabled(true);
					if(frame.getBtnRedo().isEnabled()){
						frame.getBtnRedo().setEnabled(false);
						int index = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
					}
				}
			}	 
		 }
	 
	 public void toFront() {
		 int index = getSelected();
		 
		 if(index==model.getAllShapes().size()-1) {
			 JOptionPane.showMessageDialog(null, "Shape is already in front", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
		 }
				 
		 ToFrontCmd cmd = new ToFrontCmd(model.getShape(index),model);
		 cmd.execute();
		 logList.addElement(cmd.getTextForLog());
		 undoCommandsList.add(undoCommandsList.size(),cmd);
		 if(frame.getBtnRedo().isEnabled()){
				frame.getBtnRedo().setEnabled(false);
				int i = redoCommandsList.indexOf(cmd);
				redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
			}
			
		 
	 }
	 public void toBack() {
		 int index = getSelected();
		 if(index==0) {
			 JOptionPane.showMessageDialog(null, "Shape is already in back", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
		 }
		 ToBackCmd cmd = new ToBackCmd(model.getShape(index), model);
		 cmd.execute();
		 logList.addElement(cmd.getTextForLog());
		 undoCommandsList.add(undoCommandsList.size(),cmd);
		 if(frame.getBtnRedo().isEnabled()){
				frame.getBtnRedo().setEnabled(false);
				int i = redoCommandsList.indexOf(cmd);
				redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
			}
	 }
	 
	 public void bringToFront() {
		 int index = getSelected();

		 if(index==model.getAllShapes().size()-1) {
			 JOptionPane.showMessageDialog(null, "Shape is already in front", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
		 }
		 BringToFrontCmd cmd = new BringToFrontCmd(model.getShape(index), model);
		 cmd.execute();
		 logList.addElement(cmd.getTextForLog());
		 undoCommandsList.add(undoCommandsList.size(),cmd);
		 if(frame.getBtnRedo().isEnabled()){
				frame.getBtnRedo().setEnabled(false);
				int i = redoCommandsList.indexOf(cmd);
				redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
			}

	 }
	 
	 public void bringToBack() {
		 int index = getSelected();
		 
		 if(index==0) {
			 JOptionPane.showMessageDialog(null, "Shape is already in back", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
		 }
		 BringToBackCmd cmd = new BringToBackCmd(model.getShape(index), model);
		 cmd.execute();
		 logList.addElement(cmd.getTextForLog());
		 undoCommandsList.add(undoCommandsList.size(),cmd);
		 if(frame.getBtnRedo().isEnabled()){
				frame.getBtnRedo().setEnabled(false);
				int i = redoCommandsList.indexOf(cmd);
				redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
			}
		
	 }
	 
	 public void undo() {
		 Command cmd = undoCommandsList.get(undoCommandsList.size()-1);
		 undoCommandsList.remove(undoCommandsList.size()-1);
		 if(undoCommandsList.size()==0){
				frame.getBtnUndo().setEnabled(false);
		 }
		 cmd.unexecute();
		 setChanged();
		 notifyObservers();
		 logList.addElement("Undo: " + cmd.getTextForLog());
		 redoCommandsList.add(redoCommandsList.size(),cmd);
		 if(redoCommandsList.size()>0){
			 frame.getBtnRedo().setEnabled(true);
		 }
	 }
	 
	 public void redo() {
		 Command cmd = redoCommandsList.get(redoCommandsList.size()-1);
		 redoCommandsList.remove(redoCommandsList.size()-1);
		 if(redoCommandsList.size()==0){
			 frame.getBtnRedo().setEnabled(false);
		 }
		 cmd.execute();
		 setChanged();
		 notifyObservers();
		 logList.addElement("Redo: " + cmd.getTextForLog());
		 undoCommandsList.add(undoCommandsList.size(),cmd);
		 if(undoCommandsList.size()>0){
			 frame.getBtnUndo().setEnabled(true);
		 }
	 }
	 
	 public void saveDraw() throws IOException {
		 
		 Saving savingDrawing = new SavingDrawing(model);
		 Saving savingManager = new SavingManager(savingDrawing);
		 savingManager.save();
	 }
	 
	 public void saveLog() throws IOException {
		 
		 Saving savingLog = new SavingLog(this);
		 Saving savingManager = new SavingManager(savingLog);
		 savingManager.save();
	 }
	 
	 @SuppressWarnings("unchecked")
	public void openDraw() throws IOException, ClassNotFoundException {
		 
		 JFileChooser fileChooser = new JFileChooser();
		 fileChooser.setCurrentDirectory(new java.io.File( "C:/Users/doljn/Desktop"));
		 fileChooser.setDialogTitle("Open a draw");
		 FileNameExtensionFilter filter = new FileNameExtensionFilter(".ser", "ser");
		 fileChooser.setFileFilter(filter);
		 
		 int result = fileChooser.showOpenDialog(null);
			
		if(result==JFileChooser.APPROVE_OPTION) { 
			
			File choosenFile = (fileChooser.getSelectedFile());
			
			 if(choosenFile.getName().toLowerCase().endsWith(".ser")){
				 
			FileInputStream fileInputStream = new FileInputStream(choosenFile);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			model.getAllShapes().clear();
			undoCommandsList.clear();
			redoCommandsList.clear();
			frame.dlmClear();
			frame.getBtnUndo().setEnabled(false);
		 	frame.getBtnRedo().setEnabled(false);
			
			ArrayList<Shape> shapesToDraw = (ArrayList<Shape>) objectInputStream.readObject();
			
			for (Shape shape : shapesToDraw) {
				model.addShape(shape);
				
			}
			frame.repaint();
			
			objectInputStream.close();
			fileInputStream.close();
			
			} else {
				JOptionPane.showMessageDialog(null, "The file must be a file of type: .ser", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		} else {
			return;
		} 
	 }
	 
	 public void openLog() throws IOException {
		 
		 JFileChooser fileChooser = new JFileChooser();
		 fileChooser.setCurrentDirectory(new java.io.File( "C:/Users/doljn/Desktop"));
		 fileChooser.setDialogTitle("Open a draw");
		 FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
		 fileChooser.setFileFilter(filter);
		 
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				 File choosenFile = (fileChooser.getSelectedFile());
				 if(choosenFile.getName().toLowerCase().endsWith(".txt")||choosenFile.getName().toLowerCase().endsWith(".text") ){
				 	scanner = new Scanner(choosenFile);
				 	model.getAllShapes().clear();
				 	undoCommandsList.clear();
					redoCommandsList.clear();
					frame.dlmClear();
				 	frame.getBtnLoadNextCommand().setEnabled(true);
				 	frame.getBtnUndo().setEnabled(false);
				 	frame.getBtnRedo().setEnabled(false);
				 	frame.getTglbtnPoint().setEnabled(false);
				 	frame.getTglbtnCircle().setEnabled(false);
				 	frame.getTglbtnRectangle().setEnabled(false);
				 	frame.getTglbtnLine().setEnabled(false);
				 	frame.getTglbtnDonut().setEnabled(false);
				 	frame.getTglbtnHexagon().setEnabled(false);
				 	frame.getTglBtnSelect().setEnabled(false);
				 	frame.getBtnDrawing().setEnabled(false);
				 	frame.getBtnEdgeColor().setEnabled(false);
				 	
				 }
				 else {
					 JOptionPane.showMessageDialog(null, "The file must be a file of type: txt", "Error", JOptionPane.ERROR_MESSAGE);
				 }
			}
			else {
				return;
			}
			
	 }
	 
	 public void loadNextCommand() {
		 while(scanner.hasNextLine()) {
			 
			 String strLine = scanner.nextLine();
			 String[] splitted = strLine.split(":");
			 String[] splittedForRemove = strLine.split("shapes:");
			 
			 if(strLine.equals("Deselect all shapes")) {
				 DeselectAllShapesCmd cmd = new DeselectAllShapesCmd(model);
				 cmdExecuterForLog(cmd,strLine);
				 frame.repaint();
				 return;
			 }
			 if(splitted[0].equals("Add")||splitted[0].equals("Select")||splitted[0].equals("Deselect")||splitted[0].equals("Update") ||splitted[0].equals("Bring to back")||splitted[0].equals("Bring to front")||splitted[0].equals("To back")||splitted[0].equals("To front")) {
				 if(splitted[1].equals(" Point")) {
					
					 String [] dataForPoint = splitted[2].split(",");
					  String pointX = dataForPoint[0].replaceAll("[^0-9]", "");
					  int x = Integer.parseInt(pointX);
					  String pointY = dataForPoint[1].replaceAll("[^0-9]", "");
					  int y = Integer.parseInt(pointY);
					  String Rcolor = dataForPoint[2]. replaceAll("[^0-9]", "");
					  String Gcolor = dataForPoint[3]. replaceAll("[^0-9]", "");
					  String Bcolor = dataForPoint[4]. replaceAll("[^0-9]", "");
					  int R = Integer.parseInt(Rcolor);
					  int G = Integer.parseInt(Gcolor);
					  int B = Integer.parseInt(Bcolor);
					  Color pointColor = new Color(R,G,B);
					  Point point = new Point(x,y);
					  point.setColor(pointColor);
					  
					  if(splitted[0].equals("Add")) {
						  frame.getBtnEdgeColor().setBackground(pointColor);
						  AddShapeCmd cmd = new AddShapeCmd(point,model);
						  cmdExecuterForLog(cmd,strLine);
						  return;
						  }
					  else if(splitted[0].equals("Select")) {

						  SelectShapeCmd cmd = new SelectShapeCmd(point,model);
						  cmdExecuterForLog(cmd,strLine);
						  return;
					  }
					  else if(splitted[0].equals("Deselect")) {

						  DeselectShapeCmd cmd = new DeselectShapeCmd(point,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  else if(splitted[0].equals("Update")) {
						  String [] dataForNewPoint = splitted[3].split(",");
						  String newPointX = dataForNewPoint[0].replaceAll("[^0-9]", "");
						  int newX = Integer.parseInt(newPointX);
						  String newPointY = dataForNewPoint[1].replaceAll("[^0-9]", "");
						  int newY = Integer.parseInt(newPointY);
						  String newRcolor = dataForNewPoint[2]. replaceAll("[^0-9]", "");
						  String newGcolor = dataForNewPoint[3]. replaceAll("[^0-9]", "");
						  String newBcolor = dataForNewPoint[4]. replaceAll("[^0-9]", "");
						  int newR = Integer.parseInt(newRcolor);
						  int newG = Integer.parseInt(newGcolor);
						  int newB = Integer.parseInt(newBcolor);
						  Color newPointColor = new Color(newR,newG,newB);
						  Point point2 = new Point(newX,newY);
						  point2.setColor(newPointColor);
						  point2.setSelected(true);
						  int index = model.getIndexOfShape(point);
						  Point pointToUpdate = (Point) model.getShape(index);
						  UpdatePointCmd cmd = new UpdatePointCmd(pointToUpdate, point2);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  else if(splitted[0].equals("Bring to back")) {
						  point.setSelected(true);
						  BringToBackCmd cmd = new BringToBackCmd(point,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  else if(splitted[0].equals("Bring to front")) {
						  point.setSelected(true);
						  BringToFrontCmd cmd = new BringToFrontCmd(point,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  else if(splitted[0].equals("To front")) {
						  point.setSelected(true);
						  ToFrontCmd cmd = new ToFrontCmd(point,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  else if(splitted[0].equals("To back")) {
						  point.setSelected(true);
						  ToBackCmd cmd = new ToBackCmd(point,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  
					  
				 	}
				 }
			 
			 if(splitted[0].equals("Add")||splitted[0].equals("Select")||splitted[0].equals("Deselect")||splitted[0].equals("Update") ||splitted[0].equals("Bring to back")||splitted[0].equals("Bring to front")||splitted[0].equals("To back")||splitted[0].equals("To front")) {
				  if(splitted[1].equals(" Line")) {
					  
					  String [] dataForLine = splitted[2].split("-->");
					  String [] dataForFirstPoint = dataForLine[0].split(",");
					  String firstPointX = dataForFirstPoint[0].replaceAll("[^0-9]", "");
					  int firstX = Integer.parseInt(firstPointX);
					  String firstPointY = dataForFirstPoint[1].replaceAll("[^0-9]", "");
					  int firstY = Integer.parseInt(firstPointY);
					  
					  String [] dataForSecondPointAndColor = dataForLine[1].split(",");
					  String secondPointX =dataForSecondPointAndColor[0]. replaceAll("[^0-9]", "");
					  int secondX = Integer.parseInt(secondPointX);
					  String secondPointY =dataForSecondPointAndColor[1]. replaceAll("[^0-9]", "");
					  int secondY = Integer.parseInt(secondPointY);
					  String Rcolor = dataForSecondPointAndColor[2]. replaceAll("[^0-9]", "");
					  String Gcolor = dataForSecondPointAndColor[3]. replaceAll("[^0-9]", "");
					  String Bcolor = dataForSecondPointAndColor[4]. replaceAll("[^0-9]", "");
					  int R = Integer.parseInt(Rcolor);
					  int G = Integer.parseInt(Gcolor);
					  int B = Integer.parseInt(Bcolor);
					  Color lineColor = new Color(R, G, B);
					  
					  Line line = new Line(new Point(firstX,firstY),new Point(secondX,secondY),false, lineColor);
					  if(splitted[0].equals("Add")) {
						  frame.getBtnEdgeColor().setBackground(lineColor);
						  AddShapeCmd cmd = new AddShapeCmd(line,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
						  }
					  else if(splitted[0].equals("Select")) {

						  SelectShapeCmd cmd = new SelectShapeCmd(line,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  
					  else if(splitted[0].equals("Deselect")) {

						  DeselectShapeCmd cmd = new DeselectShapeCmd(line,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					
					  else if (splitted[0].equals("Update")) {
						  String [] dataForNewLine = splitted[3].split("-->");
						  System.out.println(dataForNewLine[0]);
						  String [] newDataForFirstPoint = dataForNewLine[0].split(",");
						  String newFirstPointX = newDataForFirstPoint[0].replaceAll("[^0-9]", "");
						  int newFirstX = Integer.parseInt(newFirstPointX);
						  String newFirstPointY = newDataForFirstPoint[1].replaceAll("[^0-9]", "");
						  int newFirstY = Integer.parseInt(newFirstPointY);
						  String [] newDataForSecondPointAndColor = dataForNewLine[1].split(",");
						  String newSecondPointX =newDataForSecondPointAndColor[0]. replaceAll("[^0-9]", "");
						  int newSecondX = Integer.parseInt(newSecondPointX);
						  String newSecondPointY =newDataForSecondPointAndColor[1]. replaceAll("[^0-9]", "");
						  int newSecondY = Integer.parseInt(newSecondPointY);
						  String newRcolor = newDataForSecondPointAndColor[2]. replaceAll("[^0-9]", "");
						  String newGcolor = newDataForSecondPointAndColor[3]. replaceAll("[^0-9]", "");
						  String newBcolor = newDataForSecondPointAndColor[4]. replaceAll("[^0-9]", "");
						  int newR = Integer.parseInt(newRcolor);
						  int newG = Integer.parseInt(newGcolor);
						  int newB = Integer.parseInt(newBcolor);
						  Color newLineColor = new Color(newR,newG,newB);
						  Line line2 = new Line(new Point(newFirstX,newFirstY),new Point(newSecondX,newSecondY),true, newLineColor);
						  int index= model.getIndexOfShape(line);
						  Line lineToUpdate = (Line) model.getShape(index);
						  UpdateLineCmd cmd = new UpdateLineCmd(lineToUpdate, line2);
						  cmdExecuterForLog(cmd, strLine);
							return;
					  }
					  else if(splitted[0].equals("Bring to back")) {
						  line.setSelected(true);
						  BringToBackCmd cmd = new BringToBackCmd(line,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Bring to front")) {
						  line.setSelected(true);
						  BringToFrontCmd cmd = new BringToFrontCmd(line,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
					  else if(splitted[0].equals("To front")) {
						  int index= model.getIndexOfShape(line);
						  System.out.println(index);
						  Line lineToUpdate = (Line) model.getShape(index); 
						  ToFrontCmd cmd = new ToFrontCmd(lineToUpdate,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To back")) {
						  line.setSelected(true);
						  ToBackCmd cmd = new ToBackCmd(line,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
				  }
			 }
			 if(splitted[0].equals("Add")||splitted[0].equals("Select")||splitted[0].equals("Deselect")||splitted[0].equals("Update") ||splitted[0].equals("Bring to back")||splitted[0].equals("Bring to front")||splitted[0].equals("To back")||splitted[0].equals("To front")){
				  if(splitted[1].equals(" Circle")) { 
					  
					  String [] dataForCircle = splitted[2].split(",");
					  String centerX = dataForCircle[0].replaceAll("[^0-9]", "");
					  int x = Integer.parseInt(centerX);
					  String centerY =dataForCircle[1].replaceAll("[^0-9]", "");
					  int y = Integer.parseInt(centerY);
					  String radius =dataForCircle[2].replaceAll("[^0-9]", "");
					  int r = Integer.parseInt(radius);
					  String RforEdgeColor = dataForCircle[3]. replaceAll("[^0-9]", "");
					  String GforEdgeColor = dataForCircle[4]. replaceAll("[^0-9]", "");
					  String BforEdgecolor = dataForCircle[5]. replaceAll("[^0-9]", "");
					  int R = Integer.parseInt(RforEdgeColor);
					  int G = Integer.parseInt(GforEdgeColor);
					  int B = Integer.parseInt(BforEdgecolor);
					  Color edgeColor = new Color(R, G, B);
					  String RforInnerColor = dataForCircle[6]. replaceAll("[^0-9]", "");
					  String GforinnerColor = dataForCircle[7]. replaceAll("[^0-9]", "");
					  String BforInnercolor = dataForCircle[8]. replaceAll("[^0-9]", "");
					  int R2 = Integer.parseInt(RforInnerColor);
					  int G2 = Integer.parseInt(GforinnerColor);
					  int B2 = Integer.parseInt(BforInnercolor);
					  Color innerColor = new Color(R2, G2, B2);
					  Circle circle = new Circle(new Point(x,y), r, false, edgeColor, innerColor);
				  
					  if(splitted[0].equals("Add")) {
						  AddShapeCmd cmd = new AddShapeCmd(circle,model);
						  cmdExecuterForLog(cmd,strLine);
						  frame.getBtnEdgeColor().setBackground(edgeColor);
						  frame.getbtnColor().setBackground(innerColor);
						  return;
						  }
					  else if(splitted[0].equals("Select")) {

						  SelectShapeCmd cmd = new SelectShapeCmd(circle,model);
						  cmdExecuterForLog(cmd,strLine);
						  return;
					  }
					  else if(splitted[0].equals("Deselect")) {

						  DeselectShapeCmd cmd = new DeselectShapeCmd(circle,model);
							cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Update")) {
						  String [] dataForNewCircle = splitted[3].split(",");
						  String newCenterX = dataForNewCircle[0].replaceAll("[^0-9]", "");
						  int newX = Integer.parseInt(newCenterX);
						  String newCenterY =dataForNewCircle[1].replaceAll("[^0-9]", "");
						  int newY= Integer.parseInt(newCenterY);
						  String newRadius =dataForNewCircle[2].replaceAll("[^0-9]", "");
						  int newRad = Integer.parseInt(newRadius);
						  String newRforEdgeColor = dataForNewCircle[3]. replaceAll("[^0-9]", "");
						  String newGforEdgeColor = dataForNewCircle[4]. replaceAll("[^0-9]", "");
						  String newBforEdgecolor = dataForNewCircle[5]. replaceAll("[^0-9]", "");
						  int newR = Integer.parseInt(newRforEdgeColor);
						  int newG = Integer.parseInt(newGforEdgeColor);
						  int newB = Integer.parseInt(newBforEdgecolor);
						  Color newEdgeColor = new Color(newR,newG, newB);
						  String newRforInnerColor = dataForNewCircle[6]. replaceAll("[^0-9]", "");
						  String newGforinnerColor = dataForNewCircle[7]. replaceAll("[^0-9]", "");
						  String newBforInnercolor = dataForNewCircle[8]. replaceAll("[^0-9]", "");
						  int newR2 = Integer.parseInt(newRforInnerColor);
						  int newG2 = Integer.parseInt(newGforinnerColor);
						  int newB2 = Integer.parseInt(newBforInnercolor);
						  Color newInnerColor = new Color(newR2, newG2, newB2);
						  Circle circle2 = new Circle(new Point(newX,newY),newRad, true, newEdgeColor, newInnerColor);
						  int index= model.getIndexOfShape(circle);
						  Circle circleToUpdate = (Circle) model.getShape(index);
						  UpdateCircleCmd cmd = new UpdateCircleCmd(circleToUpdate, circle2);
						  cmdExecuterForLog(cmd, strLine);
							return;
					  
					  }
					  else if(splitted[0].equals("Bring to back")) {
						  BringToBackCmd cmd = new BringToBackCmd(circle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Bring to front")) {
						  BringToFrontCmd cmd = new BringToFrontCmd(circle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To front")) {
						  ToFrontCmd cmd = new ToFrontCmd(circle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To back")) {
						  ToBackCmd cmd = new ToBackCmd(circle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
				  	}
				  }
			 
			 if(splitted[0].equals("Add")||splitted[0].equals("Select")||splitted[0].equals("Deselect")||splitted[0].equals("Update") ||splitted[0].equals("Bring to back")||splitted[0].equals("Bring to front")||splitted[0].equals("To back")||splitted[0].equals("To front")){
				  if(splitted[1].equals(" Rectangle")) {  
					  
					  String [] dataForRectangle = splitted[2].split(",");
					  String upperLeftPointX = dataForRectangle[0].replaceAll("[^0-9]", "");
					  int x = Integer.parseInt(upperLeftPointX);
					  String upperLeftPointY =dataForRectangle[1].replaceAll("[^0-9]", "");
					  int y = Integer.parseInt(upperLeftPointY);
					  String rectangleWidth =dataForRectangle[2].replaceAll("[^0-9]", "");
					  int width = Integer.parseInt(rectangleWidth);
					  String rectangleHeight =dataForRectangle[3].replaceAll("[^0-9]", "");
					  int height = Integer.parseInt(rectangleHeight);
					  String RforEdgeColor = dataForRectangle[4]. replaceAll("[^0-9]", "");
					  String GforEdgeColor = dataForRectangle[5]. replaceAll("[^0-9]", "");
					  String BforEdgecolor = dataForRectangle[6]. replaceAll("[^0-9]", "");
					  int R = Integer.parseInt(RforEdgeColor);
					  int G = Integer.parseInt(GforEdgeColor);
					  int B = Integer.parseInt(BforEdgecolor);
					  Color edgeColor = new Color(R, G, B);
					  frame.getBtnEdgeColor().setBackground(edgeColor);
					  String RforInnerColor = dataForRectangle[7]. replaceAll("[^0-9]", "");
					  String GforinnerColor = dataForRectangle[8]. replaceAll("[^0-9]", "");
					  String BforInnercolor = dataForRectangle[9]. replaceAll("[^0-9]", "");
					  int R2 = Integer.parseInt(RforInnerColor);
					  int G2 = Integer.parseInt(GforinnerColor);
					  int B2 = Integer.parseInt(BforInnercolor);
					  Color innerColor = new Color(R2, G2, B2);
					  frame.getbtnColor().setBackground(innerColor);
					  Rectangle rectangle = new Rectangle(new Point(x,y) ,width, height, false, edgeColor, innerColor);
					 
					  if(splitted[0].equals("Add")) {
						  AddShapeCmd cmd = new AddShapeCmd(rectangle,model);
						  cmdExecuterForLog(cmd,strLine);
						  frame.getBtnEdgeColor().setBackground(edgeColor);
						  frame.getbtnColor().setBackground(innerColor);
						  return;
						  }
					  else if(splitted[0].equals("Select")) {
						 
						 SelectShapeCmd cmd = new SelectShapeCmd(rectangle,model);
						 cmdExecuterForLog(cmd,strLine);
						 return;
					  }
					  else if(splitted[0].equals("Deselect")) {

						  DeselectShapeCmd cmd = new DeselectShapeCmd(rectangle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Update")) {
						  String [] dataForNewRectangle = splitted[3].split(",");
						  String newUpperLeftPointX = dataForNewRectangle[0].replaceAll("[^0-9]", "");
						  int newX = Integer.parseInt(newUpperLeftPointX);
						  String newUpperLeftPointY =dataForNewRectangle[1].replaceAll("[^0-9]", "");
						  int newY = Integer.parseInt(newUpperLeftPointY);
						  String newRectangleWidth =dataForNewRectangle[2].replaceAll("[^0-9]", "");
						  int newWidth = Integer.parseInt(newRectangleWidth);
						  String newRectangleHeight =dataForNewRectangle[3].replaceAll("[^0-9]", "");
						  int newHeight = Integer.parseInt(newRectangleHeight);
						  String newRforEdgeColor = dataForNewRectangle[4]. replaceAll("[^0-9]", "");
						  String newGforEdgeColor= dataForNewRectangle[5]. replaceAll("[^0-9]", "");
						  String newBforEdgeColor = dataForNewRectangle[6]. replaceAll("[^0-9]", "");
						  int newR = Integer.parseInt(newRforEdgeColor);
						  int newG = Integer.parseInt(newGforEdgeColor);
						  int newB = Integer.parseInt(newBforEdgeColor);
						  Color newEdgeColor = new Color(newR, newG, newB);
						  String newRforInnerColor = dataForNewRectangle[7]. replaceAll("[^0-9]", "");
						  String newGforinnerColor = dataForNewRectangle[8]. replaceAll("[^0-9]", "");
						  String newBforInnercolor = dataForNewRectangle[9]. replaceAll("[^0-9]", "");
						  int newR2 = Integer.parseInt(newRforInnerColor);
						  int newG2 = Integer.parseInt(newGforinnerColor);
						  int newB2 = Integer.parseInt(newBforInnercolor);
						  Color newInnerColor = new Color(newR2, newG2, newB2);
						  Rectangle rectangle2 = new Rectangle(new Point(newX,newY) ,newWidth, newHeight, true, newEdgeColor, newInnerColor);
						  int index = model.getIndexOfShape(rectangle);
						  Rectangle rectangleToUpdate = (Rectangle) model.getShape(index);
						  UpdateRectangleCmd cmd = new UpdateRectangleCmd(rectangleToUpdate, rectangle2);
						  cmdExecuterForLog(cmd, strLine);
							return;
					  
					  }
					  else if(splitted[0].equals("Bring to back")) {
						  rectangle.setSelected(true);
						  BringToBackCmd cmd = new BringToBackCmd(rectangle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Bring to front")) {
						  rectangle.setSelected(true);
						  BringToFrontCmd cmd = new BringToFrontCmd(rectangle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To front")) {
						  rectangle.setSelected(true);
						  ToFrontCmd cmd = new ToFrontCmd(rectangle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To back")) {
						  rectangle.setSelected(true);
						  ToBackCmd cmd = new ToBackCmd(rectangle,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
				  }
				  
				  
			 }
			 
			 if(splitted[0].equals("Add")||splitted[0].equals("Select")||splitted[0].equals("Deselect")||splitted[0].equals("Update") ||splitted[0].equals("Bring to back")||splitted[0].equals("Bring to front")||splitted[0].equals("To back")||splitted[0].equals("To front")){
				  if(splitted[1].equals(" Donut")) { 
					  
					  String [] dataForDonut = splitted[2].split(",");
					  String centerX = dataForDonut[0].replaceAll("[^0-9]", "");
					  int x = Integer.parseInt(centerX);
					  String centerY =dataForDonut[1].replaceAll("[^0-9]", "");
					  int y = Integer.parseInt(centerY);
					  String radius =dataForDonut[2].replaceAll("[^0-9]", "");
					  int r = Integer.parseInt(radius);
					  String RforEdgeColor = dataForDonut[3]. replaceAll("[^0-9]", "");
					  String GforEdgeColor = dataForDonut[4]. replaceAll("[^0-9]", "");
					  String BforEdgecolor = dataForDonut[5]. replaceAll("[^0-9]", "");
					  int R = Integer.parseInt(RforEdgeColor);
					  int G = Integer.parseInt(GforEdgeColor);
					  int B = Integer.parseInt(BforEdgecolor);
					  Color edgeColor = new Color(R, G, B);
					  String RforInnerColor = dataForDonut[6]. replaceAll("[^0-9]", "");
					  String GforinnerColor = dataForDonut[7]. replaceAll("[^0-9]", "");
					  String BforInnercolor = dataForDonut[8]. replaceAll("[^0-9]", "");
					  int R2 = Integer.parseInt(RforInnerColor);
					  int G2 = Integer.parseInt(GforinnerColor);
					  int B2 = Integer.parseInt(BforInnercolor);
					  Color innerColor = new Color(R2, G2, B2);
					  String innerRadius =dataForDonut[9].replaceAll("[^0-9]", "");
					  int innerR = Integer.parseInt(innerRadius);
					  Donut donut  = new Donut(new Point(x,y),r, innerR, false, edgeColor, innerColor);
					  
					  if(splitted[0].equals("Add")) {
						  AddShapeCmd cmd = new AddShapeCmd(donut,model);
						  cmdExecuterForLog(cmd,strLine);
						  frame.getBtnEdgeColor().setBackground(edgeColor);
						  frame.getbtnColor().setBackground(innerColor);
						  return;
						  }
					  else if(splitted[0].equals("Select")) {

						  SelectShapeCmd cmd = new SelectShapeCmd(donut,model);
						  cmdExecuterForLog(cmd,strLine);
						  return;
					  }
					  else if(splitted[0].equals("Deselect")) {

						  DeselectShapeCmd cmd = new DeselectShapeCmd(donut,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Update")) { 
						  
						  String [] dataForNewDonut = splitted[3].split(",");
						  String newCenterX = dataForNewDonut[0].replaceAll("[^0-9]", "");
						  int newX = Integer.parseInt(newCenterX);
						  String newCenterY =dataForNewDonut[1].replaceAll("[^0-9]", "");
						  int newY = Integer.parseInt(newCenterY);
						  String newRadius =dataForNewDonut[2].replaceAll("[^0-9]", "");
						  int newRad = Integer.parseInt(newRadius);
						  String newRforEdgeColor = dataForNewDonut[3]. replaceAll("[^0-9]", "");
						  String newGforEdgeColor = dataForNewDonut[4]. replaceAll("[^0-9]", "");
						  String newBforEdgecolor = dataForNewDonut[5]. replaceAll("[^0-9]", "");
						  int newR = Integer.parseInt(newRforEdgeColor);
						  int newG = Integer.parseInt(newGforEdgeColor);
						  int newB = Integer.parseInt(newBforEdgecolor);
						  Color newEdgeColor = new Color(newR, newG, newB);
						  String newRforInnerColor = dataForNewDonut[6]. replaceAll("[^0-9]", "");
						  String newGforinnerColor = dataForNewDonut[7]. replaceAll("[^0-9]", "");
						  String newBforInnercolor = dataForNewDonut[8]. replaceAll("[^0-9]", "");
						  int newR2 = Integer.parseInt(newRforInnerColor);
						  int newG2 = Integer.parseInt(newGforinnerColor);
						  int newB2 = Integer.parseInt(newBforInnercolor);
						  Color newInnerColor = new Color(newR2, newG2, newB2);
						  String newInnerRadius =dataForDonut[9].replaceAll("[^0-9]", "");
						  int newInnerR = Integer.parseInt(newInnerRadius);
						  Donut donut2  = new Donut(new Point(newX,newY),newRad, newInnerR, true, newEdgeColor, newInnerColor);
						  int index = model.getIndexOfShape(donut);
						  Donut donutToUpdate = (Donut) model.getShape(index);
						  UpdateDonutCmd cmd = new UpdateDonutCmd(donutToUpdate, donut2);
						  cmdExecuterForLog(cmd, strLine);
							return;
						  
					  }
					  else if(splitted[0].equals("Bring to back")) {
						  donut.setSelected(true);
						  BringToBackCmd cmd = new BringToBackCmd(donut,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Bring to front")) {
						  donut.setSelected(true);
						  BringToFrontCmd cmd = new BringToFrontCmd(donut,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To front")) {
						  donut.setSelected(true);
						  ToFrontCmd cmd = new ToFrontCmd(donut,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To back")) {
						  donut.setSelected(true);
						  ToBackCmd cmd = new ToBackCmd(donut,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
				  }
				 }
			 
			 if(splitted[0].equals("Add")||splitted[0].equals("Select")||splitted[0].equals("Deselect")||splitted[0].equals("Update") ||splitted[0].equals("Bring to back")||splitted[0].equals("Bring to front")||splitted[0].equals("To back")||splitted[0].equals("To front")){
				  if(splitted[1].equals(" Hexagon")) { 
					  
					  String [] dataForCircle = splitted[2].split(",");
					  String centerX = dataForCircle[0].replaceAll("[^0-9]", "");
					  int x = Integer.parseInt(centerX);
					  String centerY =dataForCircle[1].replaceAll("[^0-9]", "");
					  int y = Integer.parseInt(centerY);
					  String radius =dataForCircle[2].replaceAll("[^0-9]", "");
					  int r = Integer.parseInt(radius);
					  String RforEdgeColor = dataForCircle[3]. replaceAll("[^0-9]", "");
					  String GforEdgeColor = dataForCircle[4]. replaceAll("[^0-9]", "");
					  String BforEdgecolor = dataForCircle[5]. replaceAll("[^0-9]", "");
					  int R = Integer.parseInt(RforEdgeColor);
					  int G = Integer.parseInt(GforEdgeColor);
					  int B = Integer.parseInt(BforEdgecolor);
					  Color edgeColor = new Color(R, G, B);
					  String RforInnerColor = dataForCircle[6]. replaceAll("[^0-9]", "");
					  String GforinnerColor = dataForCircle[7]. replaceAll("[^0-9]", "");
					  String BforInnercolor = dataForCircle[8]. replaceAll("[^0-9]", "");
					  int R2 = Integer.parseInt(RforInnerColor);
					  int G2 = Integer.parseInt(GforinnerColor);
					  int B2 = Integer.parseInt(BforInnercolor);
					  Color innerColor = new Color(R2, G2, B2);
					  HexagonAdapter hexagon = new HexagonAdapter(new Point(x,y), r,edgeColor,innerColor);
				  
					  if(splitted[0].equals("Add")) {
						  AddShapeCmd cmd = new AddShapeCmd(hexagon,model);
						  cmdExecuterForLog(cmd,strLine);
						  frame.getBtnEdgeColor().setBackground(edgeColor);
						  frame.getbtnColor().setBackground(innerColor);
						  return;
						  }
					  else if(splitted[0].equals("Select")) {

						  SelectShapeCmd cmd = new SelectShapeCmd(hexagon,model);
						  cmdExecuterForLog(cmd,strLine);
						  return;
					  }
					  else if(splitted[0].equals("Deselect")) {
						  int index= model.getIndexOfShape(hexagon);
						  System.out.println(hexagon.toString() +" " + index);
						  
						  DeselectShapeCmd cmd = new DeselectShapeCmd(hexagon,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
				  
					  else if(splitted[0].equals("Update")) {
						  String [] dataForNewCircle = splitted[3].split(",");
						  String newCenterX = dataForNewCircle[0].replaceAll("[^0-9]", "");
						  int newX = Integer.parseInt(newCenterX);
						  String newCenterY =dataForNewCircle[1].replaceAll("[^0-9]", "");
						  int newY= Integer.parseInt(newCenterY);
						  String newRadius =dataForNewCircle[2].replaceAll("[^0-9]", "");
						  int newRad = Integer.parseInt(newRadius);
						  String newRforEdgeColor = dataForNewCircle[3]. replaceAll("[^0-9]", "");
						  String newGforEdgeColor = dataForNewCircle[4]. replaceAll("[^0-9]", "");
						  String newBforEdgecolor = dataForNewCircle[5]. replaceAll("[^0-9]", "");
						  int newR = Integer.parseInt(newRforEdgeColor);
						  int newG = Integer.parseInt(newGforEdgeColor);
						  int newB = Integer.parseInt(newBforEdgecolor);
						  Color newEdgeColor = new Color(newR,newG, newB);
						  String newRforInnerColor = dataForNewCircle[6]. replaceAll("[^0-9]", "");
						  String newGforinnerColor = dataForNewCircle[7]. replaceAll("[^0-9]", "");
						  String newBforInnercolor = dataForNewCircle[8]. replaceAll("[^0-9]", "");
						  int newR2 = Integer.parseInt(newRforInnerColor);
						  int newG2 = Integer.parseInt(newGforinnerColor);
						  int newB2 = Integer.parseInt(newBforInnercolor);
						  Color newInnerColor = new Color(newR2, newG2, newB2);
						  HexagonAdapter hexagon2 = new HexagonAdapter(new Point(newX,newY),newRad, newEdgeColor, newInnerColor);
						  hexagon2.setSelected(true);
						  int index= model.getIndexOfShape(hexagon);

						  HexagonAdapter hexagonToUpdate = (HexagonAdapter) model.getShape(index);
						  UpdateHexagonCmd cmd = new UpdateHexagonCmd(hexagonToUpdate, hexagon2);
						  cmdExecuterForLog(cmd, strLine);
							return;
					  
					  }
					  else if(splitted[0].equals("Bring to back")) {
						  hexagon.setSelected(true);
						  BringToBackCmd cmd = new BringToBackCmd(hexagon,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("Bring to front")) {
						  hexagon.setSelected(true);
						  BringToFrontCmd cmd = new BringToFrontCmd(hexagon,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To front")) {
						  hexagon.setSelected(true);
						  ToFrontCmd cmd = new ToFrontCmd(hexagon,model);
						  cmdExecuterForLog(cmd, strLine);
							  return;
					  }
					  else if(splitted[0].equals("To back")) {
						  hexagon.setSelected(true);
						  ToBackCmd cmd = new ToBackCmd(hexagon,model);
						  cmdExecuterForLog(cmd, strLine);
						  return;
					  }
				  
				  }
			   }
			 if(splittedForRemove[0].equals("Remove ")||splittedForRemove[0].equals("Undo: Remove ")){

				  ArrayList<Shape> shapes = new ArrayList<Shape>();
				  String [] shapesToRemove = splittedForRemove[1].split(";");
				  
				  for(int i=0;i<shapesToRemove.length;i++) {
					  
					  String [] dataForShape = shapesToRemove[i].split(",");
					  String [] shapeName = dataForShape[0].split(":");
					  
					  if(shapeName[0].equals(" Point")) {
						  String pointX = dataForShape[0].replaceAll("[^0-9]", "");
						  int x = Integer.parseInt(pointX);
						  String pointY = dataForShape[1].replaceAll("[^0-9]", "");
						  int y = Integer.parseInt(pointY);
						  String Rcolor = dataForShape[2]. replaceAll("[^0-9]", "");
						  String Gcolor = dataForShape[3]. replaceAll("[^0-9]", "");
						  String Bcolor = dataForShape[4]. replaceAll("[^0-9]", "");
						  int R = Integer.parseInt(Rcolor);
						  int G = Integer.parseInt(Gcolor);
						  int B = Integer.parseInt(Bcolor);
						  Color pointColor = new Color(R,G,B);
						  Point point = new Point(x,y);
						  point.setColor(pointColor);
						  point.setSelected(true);
						  shapes.add(point);
					  }
					  if(shapeName[0].equals(" Line")) {
						  String firstPointX = dataForShape[0].replaceAll("[^0-9]", "");
						  int firstX = Integer.parseInt(firstPointX);
						  String [] dataForSecondPoint = dataForShape[1].split("-->");
						  String firstPointY = dataForSecondPoint[0].replaceAll("[^0-9]", "");
						  int firstY = Integer.parseInt(firstPointY);
						  String secondPointX =dataForSecondPoint[1]. replaceAll("[^0-9]", "");
						  int secondX = Integer.parseInt(secondPointX);
						  String secondPointY =dataForShape[2]. replaceAll("[^0-9]", "");
						  int secondY = Integer.parseInt(secondPointY);
						  String Rcolor = dataForShape[3]. replaceAll("[^0-9]", "");
						  String Gcolor = dataForShape[4]. replaceAll("[^0-9]", "");
						  String Bcolor = dataForShape[5]. replaceAll("[^0-9]", "");
						  int R = Integer.parseInt(Rcolor);
						  int G = Integer.parseInt(Gcolor);
						  int B = Integer.parseInt(Bcolor);
						  Color lineColor = new Color(R, G, B);
						  Line line = new Line(new Point(firstX,firstY),new Point(secondX,secondY),true, lineColor);
						  shapes.add(line);
						  
					  }
					  else if(shapeName[0].equals(" Circle")) {
						  
						  String centerX = dataForShape[0].replaceAll("[^0-9]", "");
						  int x = Integer.parseInt(centerX);
						  String centerY =dataForShape[1].replaceAll("[^0-9]", "");
						  int y = Integer.parseInt(centerY);
						  String radius =dataForShape[2].replaceAll("[^0-9]", "");
						  int r = Integer.parseInt(radius);
						  String RforEdgeColor = dataForShape[3]. replaceAll("[^0-9]", "");
						  String GforEdgeColor = dataForShape[4]. replaceAll("[^0-9]", "");
						  String BforEdgecolor = dataForShape[5]. replaceAll("[^0-9]", "");
						  int R = Integer.parseInt(RforEdgeColor);
						  int G = Integer.parseInt(GforEdgeColor);
						  int B = Integer.parseInt(BforEdgecolor);
						  Color edgeColor = new Color(R, G, B);
						  String RforInnerColor = dataForShape[6]. replaceAll("[^0-9]", "");
						  String GforinnerColor = dataForShape[7]. replaceAll("[^0-9]", "");
						  String BforInnercolor = dataForShape[8]. replaceAll("[^0-9]", "");
						  int R2 = Integer.parseInt(RforInnerColor);
						  int G2 = Integer.parseInt(GforinnerColor);
						  int B2 = Integer.parseInt(BforInnercolor);
						  Color innerColor = new Color(R2, G2, B2);
						  Circle circle = new Circle(new Point(x,y), r, true, edgeColor, innerColor);
						  shapes.add(circle);
					  }
					  else if(shapeName[0].equals(" Rectangle")){
						  
						  String upperLeftPointX = dataForShape[0].replaceAll("[^0-9]", "");
						  int x = Integer.parseInt(upperLeftPointX);
						  String upperLeftPointY =dataForShape[1].replaceAll("[^0-9]", "");
						  int y = Integer.parseInt(upperLeftPointY);
						  String rectangleWidth =dataForShape[2].replaceAll("[^0-9]", "");
						  int width = Integer.parseInt(rectangleWidth);
						  String rectangleHeight =dataForShape[3].replaceAll("[^0-9]", "");
						  int height = Integer.parseInt(rectangleHeight);
						  String RforEdgeColor = dataForShape[4]. replaceAll("[^0-9]", "");
						  String GforEdgeColor = dataForShape[5]. replaceAll("[^0-9]", "");
						  String BforEdgecolor = dataForShape[6]. replaceAll("[^0-9]", "");
						  int R = Integer.parseInt(RforEdgeColor);
						  int G = Integer.parseInt(GforEdgeColor);
						  int B = Integer.parseInt(BforEdgecolor);
						  Color edgeColor = new Color(R, G, B);
						  String RforInnerColor = dataForShape[7]. replaceAll("[^0-9]", "");
						  String GforinnerColor = dataForShape[8]. replaceAll("[^0-9]", "");
						  String BforInnercolor = dataForShape[9]. replaceAll("[^0-9]", "");
						  int R2 = Integer.parseInt(RforInnerColor);
						  int G2 = Integer.parseInt(GforinnerColor);
						  int B2 = Integer.parseInt(BforInnercolor);
						  Color innerColor = new Color(R2, G2, B2);
						  Rectangle rectangle = new Rectangle(new Point(x,y) ,width, height, true, edgeColor, innerColor);
						  shapes.add(rectangle);
					  }
					  else if(shapeName[0].equals(" Donut")){ 
						
						  String centerX = dataForShape[0].replaceAll("[^0-9]", "");
						  int x = Integer.parseInt(centerX);
						  String centerY =dataForShape[1].replaceAll("[^0-9]", "");
						  int y = Integer.parseInt(centerY);
						  String radius =dataForShape[2].replaceAll("[^0-9]", "");
						  int r = Integer.parseInt(radius);
						  String RforEdgeColor = dataForShape[3]. replaceAll("[^0-9]", "");
						  String GforEdgeColor = dataForShape[4]. replaceAll("[^0-9]", "");
						  String BforEdgecolor = dataForShape[5]. replaceAll("[^0-9]", "");
						  int R = Integer.parseInt(RforEdgeColor);
						  int G = Integer.parseInt(GforEdgeColor);
						  int B = Integer.parseInt(BforEdgecolor);
						  Color edgeColor = new Color(R, G, B);
						  String RforInnerColor = dataForShape[6]. replaceAll("[^0-9]", "");
						  String GforinnerColor = dataForShape[7]. replaceAll("[^0-9]", "");
						  String BforInnercolor = dataForShape[8]. replaceAll("[^0-9]", "");
						  int R2 = Integer.parseInt(RforInnerColor);
						  int G2 = Integer.parseInt(GforinnerColor);
						  int B2 = Integer.parseInt(BforInnercolor);
						  Color innerColor = new Color(R2, G2, B2);
						  String innerRadius =dataForShape[9].replaceAll("[^0-9]", "");
						  int innerR = Integer.parseInt(innerRadius);
						  Donut donut  = new Donut(new Point(x,y),r, innerR, true, edgeColor, innerColor);
						  shapes.add(donut);
					  }
					  else if(shapeName[0].equals(" Hexagon")){ 
						  String centerX = dataForShape[0].replaceAll("[^0-9]", "");
						  int x = Integer.parseInt(centerX);
						  String centerY =dataForShape[1].replaceAll("[^0-9]", "");
						  int y = Integer.parseInt(centerY);
						  String radius =dataForShape[2].replaceAll("[^0-9]", "");
						  int r = Integer.parseInt(radius);
						  String RforEdgeColor = dataForShape[3]. replaceAll("[^0-9]", "");
						  String GforEdgeColor = dataForShape[4]. replaceAll("[^0-9]", "");
						  String BforEdgecolor = dataForShape[5]. replaceAll("[^0-9]", "");
						  int R = Integer.parseInt(RforEdgeColor);
						  int G = Integer.parseInt(GforEdgeColor);
						  int B = Integer.parseInt(BforEdgecolor);
						  Color edgeColor = new Color(R, G, B);
						  String RforInnerColor = dataForShape[6]. replaceAll("[^0-9]", "");
						  String GforinnerColor = dataForShape[7]. replaceAll("[^0-9]", "");
						  String BforInnercolor = dataForShape[8]. replaceAll("[^0-9]", "");
						  int R2 = Integer.parseInt(RforInnerColor);
						  int G2 = Integer.parseInt(GforinnerColor);
						  int B2 = Integer.parseInt(BforInnercolor);
						  Color innerColor = new Color(R2, G2, B2);
						  HexagonAdapter hexagon = new HexagonAdapter(new Point(x,y), r,edgeColor,innerColor);
						  hexagon.setSelected(true);
						  shapes.add(hexagon);
					  } 
				  }
				  if(splittedForRemove[0].equals("Remove ")) {
					  
					  RemoveShapeCmd cmd = new RemoveShapeCmd(model,shapes);
					  cmd.execute();
					  undoCommandsList.add(undoCommandsList.size(),cmd);
					  logList.addElement(strLine);
					  frame.repaint();
					  return;
				  	}
				
			  }
			 
			 if(splitted[0].equals("Undo")||splittedForRemove[0].equals("Undo: Remove ")) {
					
				 	String check = strLine.replaceAll("Undo: ", "");
				 	System.out.println(check);
		 			for(Command cmd:undoCommandsList) {
		 				if(cmd.getTextForLog().equals(check)) {
		 					
		 					cmd.unexecute();
				  			undoCommandsList.remove(cmd);
							logList.addElement(strLine);
							redoCommandsList.add(cmd);
							
							frame.repaint();
							return;
		 				}
		 			}
							
			  }
				
			 if(splitted[0].equals("Redo") ||splittedForRemove[0].equals("Redo: Remove ")) {
					
				 			String check = strLine.replaceAll("Redo: ", "");
				 			for(Command cmd:redoCommandsList) {
				 				if(cmd.getTextForLog().equals(check)) {
				 					
				 					redoCommandsList.remove(cmd);
									cmd.execute();
									logList.addElement(strLine);
									undoCommandsList.add(cmd);
									 
									frame.repaint();
									return;
				 				}
				 			}
	 
					  }
			} 
		 JOptionPane.showMessageDialog(null, "There is no more commands in log", "Error!", JOptionPane.ERROR_MESSAGE);
		 	frame.getBtnLoadNextCommand().setEnabled(false);
		 	undoCommandsList.clear();
		 	redoCommandsList.clear();
			frame.getTglbtnPoint().setEnabled(true);
		 	frame.getTglbtnCircle().setEnabled(true);
		 	frame.getTglbtnRectangle().setEnabled(true);
		 	frame.getTglbtnLine().setEnabled(true);
		 	frame.getTglbtnDonut().setEnabled(true);
		 	frame.getTglbtnHexagon().setEnabled(true);
		 	frame.getTglBtnSelect().setEnabled(true);
		 	frame.getBtnDrawing().setEnabled(true);
		 	frame.getBtnEdgeColor().setEnabled(true);
		 	return;
			 
	 }
			 	
	 public void editShape() {

		    int index = getSelected();
			if (index == -1) return;
			
			
			Shape shape = model.getShape(index);
			if (shape instanceof Point) {
				DlgEditPoint dlgPoint = new DlgEditPoint();
				dlgPoint.getTxtX().setText(""+((Point) shape).getX());
				dlgPoint.getTxtY().setText(""+((Point) shape).getY());
				dlgPoint.getBtnColor().setBackground(shape.getColor());
				dlgPoint.setVisible(true);
				
				if(dlgPoint.isBtnEditClicked() == true) {
					Point point = (Point) model.getShape(index);
					Point point2 = new Point(dlgPoint.getNewX(),dlgPoint.getNewY(),false,dlgPoint.getColor());
					UpdatePointCmd cmd = new UpdatePointCmd(point, point2);
					cmd.execute();
					logList.addElement(cmd.getTextForLog());
					frame.getBtnUndo().setEnabled(true);
					undoCommandsList.add(undoCommandsList.size(),cmd);
					if(frame.getBtnRedo().isEnabled()){
						
						frame.getBtnRedo().setEnabled(false);
						int i = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
					}
					frame.repaint();
					model.getShape(index).setSelected(true);

				}
			}
			else if (shape instanceof Line) {
				DlgEditLine dlgLine = new DlgEditLine();
				dlgLine.getTxtFirstX().setText(""+((Line) shape).getStartPoint().getX());
				dlgLine.getTxtFirstY().setText(""+((Line) shape).getStartPoint().getY());
				dlgLine.getTxtSecondX().setText(""+((Line) shape).getEndPoint().getX());
				dlgLine.getTxtSecondY().setText(""+((Line) shape).getEndPoint().getY());
				dlgLine.getBtnColor().setBackground(shape.getColor());
				dlgLine.setVisible(true);
				
				if(dlgLine.isBtnEditClicked() == true) {
					Line line = (Line) model.getShape(index);
					Line line2 = new Line(new Point(dlgLine.getNewFirstX(), dlgLine.getNewFirstY()), new Point(dlgLine.getNewSecondX(), dlgLine.getNewSecondY()),false, dlgLine.getColor());
	
					UpdateLineCmd cmd = new UpdateLineCmd(line, line2);
					cmd.execute();
					logList.addElement(cmd.getTextForLog());
					frame.getBtnUndo().setEnabled(true);
					undoCommandsList.add(undoCommandsList.size(),cmd);
					if(frame.getBtnRedo().isEnabled()){
						
						frame.getBtnRedo().setEnabled(false);
						int i = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
					}
					frame.repaint();
					model.getShape(index).setSelected(true);

				}
			} else if (shape instanceof Rectangle) {
				DlgEditRectangle dlgRectangle = new DlgEditRectangle();
				dlgRectangle.getTxtX().setText("" + ((Rectangle) shape).getUpperLeftPoint().getX());
				dlgRectangle.getTxtY().setText("" + ((Rectangle) shape).getUpperLeftPoint().getY());
				dlgRectangle.getTxtHeight().setText(""+((Rectangle) shape).getHeight());
				dlgRectangle.getTxtWidth().setText(""+((Rectangle) shape).getWidth());
				dlgRectangle.getBtnColor().setBackground(((Rectangle) shape).getInnerColor());
				dlgRectangle.getBtnEdgeColor().setBackground(shape.getColor());
				dlgRectangle.setVisible(true);
				
				if(dlgRectangle.isBtnEditClicked()==true) {
				
					Rectangle rect = (Rectangle) model.getShape(index);
					Rectangle rect2 = new Rectangle(new Point(dlgRectangle.getNewX(),dlgRectangle.getNewY()),dlgRectangle.getNewWIdth() ,dlgRectangle.getNewHeight(),false, dlgRectangle.getEdgeColor(), dlgRectangle.getInnerColor());
				
					UpdateRectangleCmd cmd = new UpdateRectangleCmd(rect, rect2);
					cmd.execute();
					logList.addElement(cmd.getTextForLog());
					frame.getBtnUndo().setEnabled(true);
					undoCommandsList.add(undoCommandsList.size(),cmd);
					if(frame.getBtnRedo().isEnabled()){
						
						frame.getBtnRedo().setEnabled(false);
						int i = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
					}
					frame.repaint();
					model.getShape(index).setSelected(true);

				}
			
			}else if (shape instanceof Donut) {
					DlgEditDonut dlgDonut = new DlgEditDonut();
					dlgDonut.getTxtRadius().setText("" + ((Donut) shape).getRadius());
					dlgDonut.getTxtInnerRadius().setText("" + ((Donut) shape).getInnerRadius());
					dlgDonut.getTxtX().setText(((Donut) shape).getCenter().getX() + "");
					dlgDonut.getTxtY().setText(((Donut) shape).getCenter().getY() + "");
					dlgDonut.getBtnInnerColor().setBackground(((Donut) shape).getInnerColor());
					dlgDonut.getBtnEdgeColor().setBackground(shape.getColor());
					dlgDonut.setVisible(true);
					
					
					if(dlgDonut.isBtnEditClicked()==true){
						Donut donut = (Donut) model.getShape(index);
						Donut donut2 = new Donut(new Point(dlgDonut.getNewX(),dlgDonut.getNewY()), dlgDonut.getNewRadius(),dlgDonut.getNewInnerRadius(),false,dlgDonut.getEdgeColor(), dlgDonut.getInnerColor());
						UpdateDonutCmd cmd = new UpdateDonutCmd(donut, donut2);
						cmd.execute();
						logList.addElement(cmd.getTextForLog());
						frame.getBtnUndo().setEnabled(true);
						undoCommandsList.add(undoCommandsList.size(),cmd);
						if(frame.getBtnRedo().isEnabled()){
							
							frame.getBtnRedo().setEnabled(false);
							int i = redoCommandsList.indexOf(cmd);
							redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
						}
						frame.repaint();
						model.getShape(index).setSelected(true);

					}
					
						


					
			} else if (shape instanceof Circle) {
				DlgEditCircle dlgCircle = new DlgEditCircle();
				
				dlgCircle.getTxtRadius().setText("" + ((Circle) shape).getRadius());
				dlgCircle.getTxtX().setText(((Circle) shape).getCenter().getX() + "");
				dlgCircle.getTxtY().setText(((Circle) shape).getCenter().getY() + "");
				dlgCircle.getBtnColor().setBackground(((Circle) shape).getInnerColor());
				dlgCircle.getBtnEdgeColor().setBackground(shape.getColor());
				dlgCircle.setVisible(true);
				
				if(dlgCircle.isBtnEditClicked()==true){
					Circle circle = (Circle) model.getShape(index);
					Circle circle2 = new Circle(new Point(dlgCircle.getNewX(),dlgCircle.getNewY()), dlgCircle.getNewRadius(),false,dlgCircle.getEdgeColor(), dlgCircle.getInnerColor());
					UpdateCircleCmd cmd = new UpdateCircleCmd(circle, circle2);
					cmd.execute();
					logList.addElement(cmd.getTextForLog());
					frame.getBtnUndo().setEnabled(true);
					undoCommandsList.add(undoCommandsList.size(),cmd);
					if(frame.getBtnRedo().isEnabled()){
						
						frame.getBtnRedo().setEnabled(false);
						int i = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
					}
					frame.repaint();
					model.getShape(index).setSelected(true);

				}
			} 
			else if (shape instanceof HexagonAdapter) {
				DlgEditHexagon dlgHexagon= new DlgEditHexagon();
				dlgHexagon.getTxtRadius().setText("" + ((HexagonAdapter) shape).getRadius());
				dlgHexagon.getTxtX().setText(((HexagonAdapter) shape).getX() + "");
				dlgHexagon.getTxtY().setText(((HexagonAdapter) shape).getY() + "");
				dlgHexagon.getBtnColor().setBackground(((HexagonAdapter) shape).getInnerColor());
				dlgHexagon.getBtnEdgeColor().setBackground(shape.getColor());
				dlgHexagon.setVisible(true);
				if(dlgHexagon.isBtnEditClicked()==true) {
					
				HexagonAdapter hexagon = (HexagonAdapter) model.getShape(index);
			    HexagonAdapter hexagon2 = new HexagonAdapter(new Point(dlgHexagon.getNewX(),dlgHexagon.getNewY()), dlgHexagon.getNewRadius(),dlgHexagon.getEdgeColor(), dlgHexagon.getInnerColor());
				UpdateHexagonCmd cmd =new UpdateHexagonCmd(hexagon, hexagon2);
				cmd.execute();
				logList.addElement(cmd.getTextForLog());
				frame.getBtnUndo().setEnabled(true);
				undoCommandsList.add(undoCommandsList.size(),cmd);
				if(frame.getBtnRedo().isEnabled()){
					
					frame.getBtnRedo().setEnabled(false);
					int i = redoCommandsList.indexOf(cmd);
					redoCommandsList.subList(i+1, redoCommandsList.size()).clear();
				}
				frame.repaint();
				model.getShape(index).setSelected(true);
				}

				
			} 
	 }
	 
	 public void addShape(MouseEvent e) {
			 
		 this.addObserver(new UpdateButtons(frame,model));
		 
		 Point mouseClick = new Point(e.getX(), e.getY());
			if (!frame.getTglbtnLine().isSelected()) lineWaitingForSecondPoint = false;
		
			if (frame.getTglbtnPoint().isSelected()) {
				Point p = new Point(e.getX(), e.getY());
				p.setColor(frame.getBtnEdgeColor().getBackground());
				AddShapeCmd cmd = new AddShapeCmd(p, model);
				cmd.execute();
				logList.addElement(cmd.getTextForLog());
				undoCommandsList.add(undoCommandsList.size(),cmd);
				frame.getBtnUndo().setEnabled(true);
				if(frame.getBtnRedo().isEnabled()){
					frame.getBtnRedo().setEnabled(false);
					int index = redoCommandsList.indexOf(cmd);
					redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
				}
				frame.repaint();
			} else if (frame.getTglbtnLine().isSelected()) {
				if (lineWaitingForSecondPoint) {
					Line line = new Line(lineFirstPoint,mouseClick,false, frame.getBtnEdgeColor().getBackground());
					AddShapeCmd cmd = new AddShapeCmd(line,model);
				    cmd.execute();
				    logList.addElement(cmd.getTextForLog());
				    undoCommandsList.add(undoCommandsList.size(),cmd);
					frame.getBtnUndo().setEnabled(true);
					if(frame.getBtnRedo().isEnabled()){
						frame.getBtnRedo().setEnabled(false);
						int index = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
					}
					frame.repaint();
					lineWaitingForSecondPoint = false;
					return;
				
				}
				
				lineFirstPoint = mouseClick;
				lineWaitingForSecondPoint = true;
			}else if (frame.getTglbtnRectangle().isSelected()) {
				DlgAddRectangle dlgRectangle = new DlgAddRectangle();
				dlgRectangle.setVisible(true);
				Rectangle rect =new Rectangle(mouseClick, dlgRectangle.getNewHeight(),dlgRectangle.getNewWIdth() ,false, frame.getBtnEdgeColor().getBackground(),frame.getbtnColor().getBackground());
				
				if(dlgRectangle.getNewWIdth()>0 & dlgRectangle.getNewHeight()>0)
					{
						AddShapeCmd cmd = new AddShapeCmd(rect,model);
				    	cmd.execute();
				    	logList.addElement(cmd.getTextForLog());
				    	undoCommandsList.add(undoCommandsList.size(),cmd);
						frame.getBtnUndo().setEnabled(true);
						if(frame.getBtnRedo().isEnabled()){
							
							frame.getBtnRedo().setEnabled(false);
							int index = redoCommandsList.indexOf(cmd);
							redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
						}
						frame.repaint();
					}

				
			} else if (frame.getTglbtnCircle().isSelected()) {
				DlgAddCircle dlgCircle = new DlgAddCircle();
				dlgCircle.setVisible(true);
				Circle circle = new Circle(mouseClick, dlgCircle.getNewRadius(),false,frame.getBtnEdgeColor().getBackground(),frame.getbtnColor().getBackground());
				
				if(dlgCircle.getNewRadius() >0){
					AddShapeCmd cmd = new AddShapeCmd(circle,model);
				    cmd.execute();
				    logList.addElement(cmd.getTextForLog());
				    undoCommandsList.add(undoCommandsList.size(),cmd);
					frame.getBtnUndo().setEnabled(true);
					if(frame.getBtnRedo().isEnabled()){
						
						frame.getBtnRedo().setEnabled(false);
						int index = redoCommandsList.indexOf(cmd);
						redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
					}
					frame.repaint();
				}

			
			} else if (frame.getTglbtnDonut().isSelected()) {
				DlgAddDonut dlgDonut = new DlgAddDonut();
				dlgDonut.setVisible(true);
				Donut donut = new Donut(mouseClick, dlgDonut.getNewRadius(),dlgDonut.getNewInnerRadius(),false,frame.getBtnEdgeColor().getBackground(),frame.getbtnColor().getBackground());
				
				if(dlgDonut.getNewRadius() >0&dlgDonut.getNewInnerRadius() >0)
					{	
						AddShapeCmd cmd = new AddShapeCmd(donut,model);
				    	cmd.execute();
				    	logList.addElement(cmd.getTextForLog());
				    	undoCommandsList.add(undoCommandsList.size(),cmd);
						frame.getBtnUndo().setEnabled(true);
						if(frame.getBtnRedo().isEnabled()){
							
							frame.getBtnRedo().setEnabled(false);
							int index = redoCommandsList.indexOf(cmd);
							redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
						}
				    	frame.repaint();
					}

			}
			else if (frame.getTglbtnHexagon().isSelected()) {
				DlgAddHexagon dlgHexagon = new DlgAddHexagon();
				dlgHexagon.setVisible(true);
				HexagonAdapter hexagon = new HexagonAdapter(mouseClick, dlgHexagon.getNewRadius(),frame.getBtnEdgeColor().getBackground(), frame.getbtnColor().getBackground());
				
				if(dlgHexagon.getNewRadius() > 0)
					{
						AddShapeCmd cmd = new AddShapeCmd(hexagon,model);
				    	cmd.execute();
				    	logList.addElement(cmd.getTextForLog());
				    	undoCommandsList.add(undoCommandsList.size(),cmd);
						frame.getBtnUndo().setEnabled(true);
						if(frame.getBtnRedo().isEnabled()){
							
							frame.getBtnRedo().setEnabled(false);
							int index = redoCommandsList.indexOf(cmd);
							redoCommandsList.subList(index+1, redoCommandsList.size()).clear();
						}
				    	frame.repaint();
					}

			}
			frame.getBtnSaveDraw().setEnabled(true);
			 frame.getBtnSaveLog().setEnabled(true);
			 

		}
	 
	 public Color pickColor(Color colorOldState) {
			Color colorNewState = JColorChooser.showDialog(null, "Pick color", colorOldState);
			if (colorNewState != null) {
				return colorNewState;
			} else {
				return colorOldState;
			}
		}
	 
	 public DefaultListModel<String> getLogList() {
			return logList;
		}
	 
	 public void cmdExecuterForLog(Command cmd,String strLine) {
		cmd.execute();
		undoCommandsList.add(undoCommandsList.size(),cmd);
		logList.addElement(strLine);
		frame.repaint();
	 }

	 }