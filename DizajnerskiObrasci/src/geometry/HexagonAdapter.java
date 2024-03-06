package geometry;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape{

	private Hexagon hexagon;
	private Point startPoint;
	private int radius;
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	public HexagonAdapter(Point startPoint, int radius, Color color, Color innerColor) {
		this.hexagon = new Hexagon(startPoint.getX(), startPoint.getY(), radius);
		this.setColor(color);
		this.startPoint=startPoint;
		this.setInnerColor(innerColor);
		this.radius= radius;
	}

	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter pomocni = (HexagonAdapter) obj;
			if (this.getX()==pomocni.getX()&&this.getY()==pomocni.getY() &&this.getRadius()==pomocni.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void moveBy(int byX, int byY) {
		hexagon.setX(hexagon.getX() + byX);
		hexagon.setY(hexagon.getY() + byY);
	}
	
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter)
			return hexagon.getR() - ((HexagonAdapter) o).getRadius();
		return 0;
	}
	@Override
	public boolean contains(Point p) {
		
		return hexagon.doesContain(p.getX(), p.getY());
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
		super.setSelected(selected);
	}

	@Override
	public boolean isSelected() {
		return hexagon.isSelected();
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
		
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
		hexagon.setSelected(isSelected());

	}
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
	}
	public void setInnerColor(Color color) {
		hexagon.setAreaColor(color);
	}
	
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}
	
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	
	public int getRadius() {
		return hexagon.getR();
	}
	
	public void setRadius(int r) {
		hexagon.setR(r);
	}
	
	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	
	public int getX() {
		return hexagon.getX();
	}
	
	public void setX(int x) {
		hexagon.setX(x);
	}
	
	public int getY() {
		return hexagon.getY();
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	public void setY(int y) {
		hexagon.setY(y);
	}
	
	public HexagonAdapter clone(){
		
		HexagonAdapter hexagon = new HexagonAdapter(new Point(this.getX(),this.getY()), this.getRadius(),this.getColor(),this.getInnerColor());	
		
		return hexagon;
		
	}

	public String toString() {
	
	return "Hexagon: centerPoint(" + this.getX()+"," + this.getY() +")"  +", radius= " + getRadius()+",edge color = " + this.getColor()+",edge color = " + this.getInnerColor();
	
}
}
