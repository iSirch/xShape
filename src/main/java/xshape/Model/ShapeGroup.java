package xshape.Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ShapeGroup extends ShapeAbstact
{
    ArrayList<Shape> components;

    public ShapeGroup(Point position, Dimension size) {
        super(position, size);
        this.components = new ArrayList<Shape>();
        this.selectionColor = Color.cyan;
        this.name = "Groups";
    }

    public ShapeGroup(int x, int y, int width, int height) {
        this(new Point(x, y), new Dimension(width, height));
    }

    public ShapeGroup()
    {
        this(0, 0, 0 ,0);
    }

    public ShapeGroup(Shape ... shapes)
    {
        this();
        for (Shape shape : shapes) {
            this.add(shape);
        }
    }

    public ArrayList<Shape> getShapeGroup(){
        return components;
    }
    
    private Point relativePos(Shape shape)
    {
        return new Point(Math.abs(this.position.x - shape.position().x), 
                        Math.abs(this.position.y - shape.position().y));
    }

    private Dimension relativeSize(Shape shape)
    {
        return new Dimension(Math.abs(this.size.width - shape.size().width), 
                        Math.abs(this.size.height - shape.size().height));
    }


    @Override
    public void setPos(int posX, int posY) {
        // TODO Auto-generated method stub
        for (Shape shape : components) 
        {
            Point rpos = relativePos(shape);
            shape.setPos(rpos.x + posX,
                         rpos.y + posY);
        }
        super.setPos(posX, posY);
    }

    @Override
    public void setCenterToPos(int posX, int posY) {
        // TODO Auto-generated method stub
        for (Shape shape : components) {
            Point rpos = relativePos(shape);
            Dimension rsize = relativeSize(shape);

            // shape.setCenterToPos((int)(rpos.x - ((double)rsize.width / 2.0) + posX),
            //                     (int)(rpos.y - ((double)rsize.height / 2.0) + posY));
            double x = (rpos.x - (rsize.width / 2.0) + posX) - (shape.size().width / 2.0);
            double y = (rpos.y - (rsize.height / 2.0) + posY) - (shape.size().height / 2.0);
            shape.setPos((int)x, (int)y);
        }
        super.setCenterToPos(posX, posY);
    }

    @Deprecated //ancien setSize sans mise à l'échelle qui ne bugait pas avec des int (bug de perte de precision avec un scalling sur des dimensions int)
    public void setSize(int width, int height) {
       
        double diffW =  width - this.size.width;
        double diffH =  height - this.size.height;

        for (Shape shape : components) {
            Dimension sz = shape.size();
            shape.setSize(Math.abs(sz.width + diffW), Math.abs(sz.height + diffH));
        }

         recalculateBounds();
    }

    @Override
    public void setSize(double width, double height) {
        double qW = 1, qH = 1;

        if(this.size.width > 0) qW = (double)(width) / this.size.width;
        if(this.size.height > 0) qH = (double)(height) / this.size.height;

        // if we used ints:
        // if((qW - 1.0/qW) != 0 && (qW - 1.0/qW) < 0.1) return;
        // if((qH - 1.0/qH) != 0 && (qW - 1.0/qW) < 0.1) return;

        System.out.println(Math.abs(qW - 1.0/qW) + " " + Math.abs(qH - 1.0/qH));
        
        //scale everything
        scale(qW, qH);

        //update positions
        for (Shape shape : components) {
            Point pos = shape.position();
            Point rpos = relativePos(shape);
            shape.setPos((int)(pos.x + rpos.x * (-1.0 + qW)) , 
                        (int)(pos.y + rpos.y * (-1.0 + qH)));
        }

        recalculateBounds();
    }

    @Override
    public void scale(double scaleW, double scaleH)
    {
        super.scale(scaleW, scaleH);

        for (Shape shape : components) {
            shape.scale(scaleW, scaleH);
        }

        recalculateBounds();
    }

    @Override
    public void scale(double scale) 
    {
        super.scale(scale);

        for (Shape shape : components) {
            shape.scale(scale);
        }

        recalculateBounds();
    }


    @Override
    public void resetCenter() {
        // TODO Auto-generated method stub
        super.resetCenter();
        if(components == null) return;
        for (Shape shape : components) {
            shape.setCenter(center);
        }
    }

    @Override
    public void setDegrees(int degrees) {
        for (Shape shape : components) {
            shape.setDegrees(degrees);
        }
        super.setDegrees(degrees);
    }

    @Override
    public Shape translate(Point vec) {
        // TODO Auto-generated method stub
        return super.translate(vec);
    }

    @Override
    public boolean contains(int x, int y) 
    {
        for (Shape shape : components) {
            if(shape.contains(x, y)) return true;
        }

        return false;
    }

    public boolean inBounds(int x, int y) 
    {
        return new Rectangle(position, size).contains(x, y);
    }

    public boolean isEmpty() { return components.isEmpty(); }

    private void recalculateBounds()
    {
        int minX, minY, maxX, maxY;
        minX = minY = Integer.MAX_VALUE;
        maxX = maxY = 0;

        for (Shape shape : components) {

            Point pos = shape.position();
            
            minX = Math.min(minX, pos.x);
            minY = Math.min(minY, pos.y);

            Dimension sz = shape.size();
            maxX = (int)Math.max(maxX, pos.x + sz.width); 
            maxY = (int)Math.max(maxY, pos.y + sz.height);
        }

        this.position = new Point(minX, minY);
        this.size = new Dimension(maxX - minX, maxY - minY);
        resetCenter();
    }

    public ShapeGroup getShapesAt(int x, int y)
    {
        ShapeGroup group = new ShapeGroup(new Point(position), new Dimension(size));

        for (Shape shape : components) {
            if(shape.contains(x, y)) group.add(shape);
            //else System.out.println(x + " " + y + " not in " + shape.position() + " " + shape.size());
        }

        return group.isEmpty() ? null : group;
    }

    public ShapeGroup getShapesIn(Rectangle rect)
    {
        ShapeGroup group = new ShapeGroup(new Point(position), new Dimension(size));

        for (Shape shape : components) {
            Point pos = shape.position();
            if(rect.contains(pos.x, pos.y)) group.add(shape);
        }

        return group.isEmpty() ? null : group;
    }

    public ShapeGroup getShapesAt(Point position) { return  getShapesAt(position.x, position.y); }

    // public ShapeGroup getShapesIn(Point position, Dimension size)
    // {
    //     ShapeGroup group = new ShapeGroup(new Point(position), new Dimension(size));
        
    //     for (Shape s : components) {
    //         if(s.contains(shape)) group.add(s);
    //     }

    //     return group;
    // }

    @Override
    public void draw(Graphics g) {
        for (Shape shape : components) {
            shape.draw(g);
        }
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {
        for (Shape shape : components) {
            shape.drawAt(g, x, y);
        }
    }

    @Override
    public void drawSelection(Graphics g)
    {
        drawSelection(g, true, selectionColor, DEFAULT_MARGIN);
    }

    @Override
    public void drawSelection(Graphics g, int margin) 
    {
        for (Shape shape : components) {
            shape.drawSelection(g);
        }

        g.drawRect(position.x, position.y, (int)size.width, (int)size.height);
    }

    @Override
    public void add(Shape component) 
    {
        if(isEmpty())
        {
            this.position = component.position();
            this.size = component.size();
            this.components.add(component);
        }
        else
        {
            this.components.add(component);
            recalculateBounds();
        }

        component.setCenter(this.center);

        //onPositionUpdate(component.position());
    }

    @Override
    public void add(Shape ... shapes) 
    {
        if(isEmpty())
        {
            this.position = shapes[0].position();
            this.size = shapes[0].size();
        }
        
        for (Shape shape : shapes) {
            this.components.add(shape);
            shape.setCenter(this.center);
        }
        
        recalculateBounds();
    }

    @Override
    public void remove(Shape component) {
        this.components.remove(component);
        if(components.isEmpty())
        {
            this.position = new Point(0, 0);
            this.size = new Dimension(0,0);
        }
        else
        {
            recalculateBounds();
        }

        component.resetCenter();
    }

    @Override
    public void remove(Shape ... shapes) {

        for (Shape shape : shapes) 
        {
            this.components.remove(shape);
            shape.resetCenter();   
        }

        if(components.isEmpty())
        {
            this.position = new Point(0, 0);
            this.size = new Dimension(0,0);
        }
        else
        {
            recalculateBounds();
        }
    }

    @Override
    public Shape clone()
    {
        System.err.println("group clone");

        ShapeGroup group = new ShapeGroup(new Point(position), new Dimension(size));
        for (Shape shape : components) {
            Shape newShape = shape.clone();
            group.add(newShape);
        }
        return group;
    }

    @Override
    public Shape[] getChildren() {
        Shape[] arr = new Shape[this.components.size()];
        return this.components.toArray(arr);
    }

    //group ShapeGroup 'group' inside this
    public void group(ShapeGroup group)
    {
        if(this.components.size() == 1) return;
        this.remove(group.getChildren());
        this.add(group);
    }
    
    public void group(Shape ... shapes)
    {
        ShapeGroup group = new ShapeGroup(position, size);

        group.add(shapes);
        this.remove(shapes);
        this.add(group);
    }

    //ungroup ShapeGroup 'group' inside this
    public void ungroup(ShapeGroup group)
    {
        Shape[] children = group.getChildren();

        if(children == null || children.length != 1) return;

        this.add(children[0].getChildren());
        this.remove(children[0]);
    }

    @Override
    public void setColor(Color color) {
        // TODO Auto-generated method stub
        for (Shape shape : components) {
            shape.setColor(color);
        }
        this.color = color;
    }

    public void setPosTest(int x, int y){
        for (Shape shape : components) {
            shape.setPosTest(x,y);
        }
        
    }
}
