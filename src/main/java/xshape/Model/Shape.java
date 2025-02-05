package xshape.Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Shape extends Composable, Prototypable
{
	Point position();
	Dimension size();
	void setPos(int posX, int posY);
	void setCenterToPos(int posX, int posY);
	Shape translate(Point vec);
	void setSize(double width, double height);
	void scale(double scale);
	void scale(double scaleW, double scaleH);
	void setCenter(Point center);
	void resetCenter();
	void rotate(int degrees);
	void setDegrees(int degrees);

	boolean contains(int x, int y);
	void setColor(Color color);
	Color getColor();

	void draw(Graphics g);
	void drawAt(Graphics g, int x, int y);
	void drawSelection(Graphics g);
	void drawSelection(Graphics g, int margin);
	void drawSelection(Graphics g, boolean dashed, Color color, int margin);

	String name();
    void setPosTest(int x, int y);
}
