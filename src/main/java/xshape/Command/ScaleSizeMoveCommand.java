package xshape.Command;

import xshape.Canvas;
import xshape.Model.ShapeGroup;

public class ScaleSizeMoveCommand extends Command {

    public ScaleSizeMoveCommand(Canvas canvas) {
        super(canvas);
    }

    @Override
    void execute() {
        if (first) {
            first = false;
            //selectedShape.setPos(x, y);
            canvas.nullSelect();
            shapeChanged = (ShapeGroup) canvas.getShapes().clone();
            // canvas.repaint();
        }else{
            canvas.set_shapes(shapeChanged);
            canvas.nullSelect();
            canvas.repaint();
        } }
}
