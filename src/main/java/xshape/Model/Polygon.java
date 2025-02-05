package xshape.Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Polygon extends ShapeAbstact
{
    private int nbSides = 7;
    private double sideLength = 4;
    int[] xOffset;
    int[] yOffset;
    int[] xPoints;
    int[] yPoints;

    public Polygon(Point position, int nbSides, double sideLength) {
        super(position, new Dimension((int)sideLength, (int)sideLength));
        this.nbSides = nbSides;
        this.sideLength = sideLength;

        calculatePoints(nbSides, sideLength);
        Rectangle bounds = calculateBounds();
        this.size = bounds.size();
        this.margin = 3;
        this.name = "Polygon";
    }

    public Polygon(Point position, int nbSides, double sideLength, Color color) {
        super(position, new Dimension((int)sideLength, (int)sideLength));
        this.nbSides = nbSides;
        this.sideLength = sideLength;

        calculatePoints(nbSides, sideLength);
        Rectangle bounds = calculateBounds();
        this.size = bounds.size();
        this.margin = 3;
        this.name = "Polygon";
        this.color = color;
    }

    private void calculatePoints(int nbSides, double sideLength)
    {
        xOffset = new int[nbSides];
        yOffset = new int[nbSides];
        xPoints = new int[nbSides];
        yPoints = new int[nbSides];

        double theta = 2 * Math.PI / nbSides; // Angle between vertices

        double firstAngle = Math.PI / 2 - theta / 2;

        for (int i = 0; i < nbSides; i++) 
        {
            double angle = firstAngle + i * theta;
            xOffset[i] = (int)(sideLength + (sideLength * Math.cos(angle)));
            yOffset[i] = (int)(sideLength + (sideLength * Math.sin(angle)));
            xPoints[i] = position.x + xOffset[i];
            yPoints[i] = position.y + yOffset[i];
        }
    }

    Rectangle calculateBounds() {
        //cf java.awt.Polygon
        int boundsMaxX = 0;
        int boundsMaxY = 0;

        for (int i = 0; i < nbSides; i++) {
            boundsMaxX = Math.max(boundsMaxX, xPoints[i]);
            boundsMaxY = Math.max(boundsMaxY, yPoints[i]);
        }
        return new Rectangle(this.position.x, this.position.y,
                               boundsMaxX - this.position.x,
                               boundsMaxY - this.position.y);
    }


    @Override
    public void setSize(double width, double height) 
    {
        //find the dim with most change
        double diffW = Math.abs(this.size.width - width);
        double diffH = Math.abs(this.size.height - height);
        double dim = diffW > diffH ? width : height;

        this.sideLength = dim / (2.0 * Math.tan(Math.PI / this.nbSides));
        calculatePoints(nbSides, sideLength);
        Rectangle bounds = calculateBounds();
        //semble faire bug ?:
        //this.size.width=bounds.size.width;
        //this.size.height=bounds.size.height;
        resetCenter();
    }

    @Override
    public void scale(double scaleW, double scaleH) 
    {
        setSize(this.size.width * scaleW, this.size.height * scaleH);
    }

    @Override
    public boolean contains(int x, int y) {
        // TODO inventer la roue carre
        return calculateBounds().contains(x, y);
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {

        for (int i = 0; i < nbSides; i++) {
            xPoints[i] = x + xOffset[i];
            yPoints[i] = y + yOffset[i];
        }

        g.fillPolygon(xPoints, yPoints, nbSides);
    }

    @Override
    public void drawSelection(Graphics g, int margin) 
    {
        int[] xSelectPoints = new int[nbSides];
        int[] ySelectPoints = new int[nbSides];

        double marginMult = 1 + margin * 0.1;

        for (int i = 0; i < nbSides; i++) {
            xSelectPoints[i] = (int)(position.x + sideLength + marginMult * (xOffset[i] - sideLength));
            ySelectPoints[i] = (int)(position.y + sideLength + marginMult * (yOffset[i] - sideLength));
        }

        g.drawPolygon(xSelectPoints, ySelectPoints, nbSides);
    }

    @Override
    public Shape clone() {
        // TODO Auto-generated method stub
        return new Polygon(new Point(position), nbSides, sideLength, color);
    }

    public int nbSides() {
        return nbSides;
    }

    public double sideLength() {
        return sideLength;
    }
    public void setPosTest(int x, int y){
        super.setPos(x,y);
    }
}
