package xshape.Command;

import xshape.EditPanel;
import xshape.MenuBar;
import xshape.Model.Shape;
import xshape.Model.ShapeGroup;
import xshape.Canvas;

public abstract class Command {
    Canvas canvas;
    EditPanel panel;
    MenuBar menu;
    Shape selectedShape;
    ShapeGroup shape;
    ShapeGroup shapeChanged;
    Boolean first = true;

    public Command(Canvas canvas) {
        this.canvas = canvas;
        shape = (ShapeGroup) canvas.getShapes().clone();
    }

    public Command(Canvas canvas, EditPanel panel, MenuBar menu) {
        this.canvas = canvas;
        this.panel = panel;
        this.menu = menu;
    }

    
    void undo() {
        // selectedShape.setColor(oldColor);
        // System.out.println("new" + color+ ", old :" + oldColor);
        canvas.set_shapes(shape);
        canvas.repaint();
    }

    abstract void execute();

    public Canvas getCanvas() {
        return canvas;
    }

}
