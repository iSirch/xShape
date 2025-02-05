package xshape.Command;

import xshape.Model.ShapeGroup;
import java.awt.Point;
import xshape.Canvas;

public class MoveCommand extends Command {
    private Point from;
    private Point to;
    int x, y;

    public MoveCommand(Canvas canvas, Point from, Point to) {
        super(canvas);
        this.selectedShape = canvas.getSelection();
        this.from = from;
        this.to = to;
        System.out.printf("init\nto=(%d, %d) from=(%d, %d)\n", to.x, to.y, from.x, from.y);
    }

    public MoveCommand(Canvas canvas) {
        super(canvas);
        // this.selectedShape = canvas.getSelection();
        // this.x = x;
        // this.y = y;
    }

    // @Override
    // void undo() {
    //     System.out.printf("redo\nto=(%d, %d) from=(%d, %d)\n", to.x, to.y, from.x, from.y);
    //     selectedShape.setPos(from.x, from.y);
    //     canvas.repaint();
    // }

    // @Override
    // void execute() {
    //     System.out.printf("execute/redo\nto=(%d, %d) from=(%d, %d)\n", to.x, to.y, from.x, from.y);
    //     selectedShape.setPos(to.x, to.y);
    //     canvas.repaint();
    // }

    @Override
    void execute() {
        if (!first) {
            canvas.set_shapes(shapeChanged);
            canvas.nullSelect();
            canvas.repaint();}

    }

    public void executeDelayed() {
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
        }
    }
}