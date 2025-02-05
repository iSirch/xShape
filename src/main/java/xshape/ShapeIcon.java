package xshape;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import xshape.Model.Dimension;
import xshape.Model.Shape;

public class ShapeIcon implements Icon {

    private final Dimension MAX_DIM = new Dimension(32, 32);
    private Shape shape;
    private Shape original;

    public ShapeIcon(Shape shape) {
        this.original = shape;
        this.shape = shape.clone();
        if(shape.size().width > MAX_DIM.width || shape.size().height > MAX_DIM.height)
        {
            this.shape.setSize(MAX_DIM.width, MAX_DIM.height);//getIconWidth(), getIconHeight());
        }
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) 
    {   
        shape.setPos(x, y);
        shape.draw(g);
    }

    @Override
    public int getIconWidth() {
        return (int)shape.size().width;
    }

    @Override
    public int getIconHeight() {
        return (int)shape.size().height;
    }

    public Shape getShape()
    {
        //Shape res = shape.clone();
        //if(oldSize != null) res.setSize(oldSize.width, oldSize.height);
        return original;
    }
}
