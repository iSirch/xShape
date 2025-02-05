package xshape.Model;

import java.awt.Point;

public class ShapeFactory 
{
    public ShapeFactory() {
    }
    public Rectangle createRectangle(int posX, int posY, int width, int height) {
        return new Rectangle(posX, posY, width, height);
    }

    public Cercle createCircle(int posX, int posY, int width, int height) {
        return new Cercle(posX, posY, width, height);
    }

    public Polygon createPolygon(int posX, int posY, int nbSides, int sideLength)
    {
        return new Polygon(new Point(posX, posY), nbSides, sideLength);
    }
}
