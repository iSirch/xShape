package xshape.Command;

import xshape.Canvas;
import xshape.Model.Shape;
import xshape.Model.ShapeGroup;

public class AddCommand extends Command {
    Shape shape;
    ShapeGroup shapeGroup;
    Shape newShape;

    public AddCommand(Canvas canvas, Shape s) {
        super(canvas);
        shape = s;
    }

    @Override
    void execute() {
        if (first) {
            first = false;
            canvas.addShape(shape);
            shapeChanged = (ShapeGroup) canvas.getShapes().clone();
            canvas.repaint();
        }else{
            canvas.set_shapes(shapeChanged);
            canvas.repaint();
        }
    }

}
