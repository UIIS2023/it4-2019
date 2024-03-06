package mvc;

import java.util.ArrayList;

import geometry.Shape;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	public void remove(Shape shape) {
		shapes.remove(shape);
	}
	
	public Shape getShape(int i) {
		return shapes.get(i);
	}
	
	public int getIndexOfShape(Shape shape) {
		return shapes.indexOf(shape);
	}
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}

	public ArrayList<Shape> getAllShapes() {
		return shapes;
	}
}
