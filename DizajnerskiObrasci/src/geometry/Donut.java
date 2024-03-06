package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius); 
		this.innerRadius = innerRadius;
	}
	 
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	public void draw(Graphics g) {
		Graphics2D gr = (Graphics2D)g.create();
		Shape outer=new Ellipse2D.Double(
	            getCenter().getX() - this.getRadius(), 
	            getCenter().getY() - this.getRadius(),
	            this.getRadius()*2, 
	            this.getRadius()*2);
		
		Shape inner=new Ellipse2D.Double(
				getCenter().getX() - this.innerRadius, 
				getCenter().getY() - this.innerRadius,
	            this.innerRadius*2, 
	            this.innerRadius*2);
		
		Area donut = new Area(outer);
		Area areaToCut = new Area(inner);
        donut.subtract(areaToCut); 
        gr.setColor(getInnerColor());
        gr.fill(donut);
        gr.setColor(getColor());
        gr.draw(donut);
        
       
        if (isSelected()) {
        	g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() + getInnerRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - getInnerRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX()- 3, getCenter().getY() + getInnerRadius() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() - getInnerRadius() - 3, 6, 6);
    		g.setColor(Color.BLUE);
        	g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() + getRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - getRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX()- 3, getCenter().getY() + getRadius() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() - getRadius() - 3, 6, 6);
    		g.setColor(Color.BLUE);
        }
      

	}

	

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (this.getCenter().equals(pomocni.getCenter()) &&
					this.getRadius() == pomocni.getRadius() &&
					this.innerRadius == pomocni.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public String toString() {
		return "Donut: centerPoint(" +  this.getCenter().getX()+"," + this.getCenter().getY() +")"  + ", radius=" + this.getRadius() + ",edgeColor= "  + this.getColor() + ",innerColor=" + this.getInnerColor()+",inner radius=" + this.innerRadius;
	}
	
	@Override
	public Donut clone() {
		
		Donut donut = new Donut();
		
		donut.setCenter(this.getCenter());
		try {
			donut.setRadius(this.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		donut.setInnerRadius(this.getInnerRadius());
		donut.setColor(this.getColor());
		donut.setInnerColor(this.getInnerColor());
		
		return donut;
		
		
	}
	
	}