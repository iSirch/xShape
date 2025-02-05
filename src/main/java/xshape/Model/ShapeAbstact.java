package xshape.Model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
import java.awt.Color;

public abstract class ShapeAbstact implements Shape {
    private final int DASH_LEN = 4;
    final Stroke DASHED = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { DASH_LEN },
            0);
    final int DEFAULT_MARGIN = 2;
    public static final Color DEFAULT_COLOR = Color.ORANGE;

    public Point position;
    public Dimension size;
    public Point center;
    public Color color = DEFAULT_COLOR;
    public Color selectionColor = Color.magenta;
    public int margin = DEFAULT_MARGIN;
    public int degrees = 0;
    public String name;

    public ShapeAbstact(Point position, Dimension size) {
        this.position = position;
        this.size = size;
        resetCenter();
    }

    public ShapeAbstact(int x, int y, int width, int height) {
        this(new Point(x, y), new Dimension(width, height));
    }

    public String name() {
        return name;
    }

    @Override
    public Point position() {
        return this.position;
    }

    @Override
    public Dimension size() {
        return this.size;
    }

    @Override
    public void setPos(int posX, int posY) {
        this.position.x = posX;
        this.position.y = posY;
        resetCenter();
    }

    @Override
    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public void resetCenter() {
        setCenter(new Point((int) (position.x + (this.size.width / 2.0)),
                (int) (position.y + (this.size.height / 2.0))));
    }

    @Override
    public void setCenterToPos(int posX, int posY) {
        this.position.x = (int) (posX - (this.size.width / 2.0));
        this.position.y = (int) (posY - (this.size.height / 2.0));
    }

    @Override
    public void setSize(double width, double height) {
        this.size.setSize(width, height);
        resetCenter();
    }

    @Override
    public void scale(double scale) {
        this.scale(scale, scale);
    }

    @Override
    public void scale(double scaleW, double scaleH) {
        this.size.width *= scaleW;
        this.size.height *= scaleH;
        resetCenter();
    }

    @Override
    public Shape translate(Point vec) {
        setPos(this.position.x + vec.x, this.position.y + vec.y);
        return this;
    }

    @Override
    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    @Override
    public void rotate(int degrees) {
        this.degrees = (this.degrees + degrees) % 360;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        g2d.setColor(this.color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform oldTransform = null;
        if (degrees != 0) {
            oldTransform = g2d.getTransform();
            g2d.rotate(Math.toRadians(degrees), center.x, center.y);
        }

        drawAt(g2d, this.position.x, this.position.y);

        if (degrees != 0)
            g2d.setTransform(oldTransform);
        g2d.setColor(oldColor);
    }

    @Override
    public void drawSelection(Graphics g) {
        drawSelection(g, true, selectionColor, DEFAULT_MARGIN);
    }

    public void drawSelection(Graphics g, boolean dashed, Color color, int margin) {
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        Stroke oldStroke = g2d.getStroke();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        if (dashed)
            g2d.setStroke(DASHED);

        drawSelection(g, margin);

        if (dashed)
            g2d.setStroke(oldStroke);
        g2d.setColor(oldColor);
    }

    @Override
    public Shape clone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Shape component) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void add(Shape... shapes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void remove(Shape component) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void remove(Shape... shapes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Shape[] getChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    public Color getColor() {
        return color;
    }
}
