package xshape.Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Cercle extends ShapeAbstact {
    
    public Cercle(Point position, Dimension size) {
        super(position, size);
        this.margin = 3;
        name = "Cercle";
        //TODO Auto-generated constructor stub
    }

    public Cercle(int x, int y, int width, int height) {
        this(new Point(x, y), new Dimension(width, height));
        name = "Cercle";
    }

    public Cercle(Point position, Dimension size, Color color) {
        super(position, size);
        this.margin = 3;
        name = "Cercle";
        this.color = color;
    }

    @Override
    public void drawAt(Graphics g, int x, int y) 
    {
        g.fillOval(x, y, (int)size.width, (int)size.height);
    }

    @Override
    public void drawSelection(Graphics g, int margin)
    {
        final int margin2 = 2 * margin;
        g.drawOval(position.x - margin, 
                    position.y - margin, 
                    (int)size.width + margin2, 
                    (int)size.height + margin2);
    }

    @Override
    public boolean contains(int x, int y) {
        //TODO inventer la roue
        return new Rectangle(position, size).contains(x, y);
    }
    
    @Override
    public Cercle clone(){
        return new Cercle(new Point(position), new Dimension(size), color);
    }

    public void setPosTest(int x, int y){
        super.setPos(x,y);
    }
}
