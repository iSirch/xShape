package xshape.Command;

import java.awt.Color;

import xshape.Canvas;
import xshape.Model.ShapeGroup;

public class ColorCommand extends Command {
    Color color;
    Color oldColor;

    public ColorCommand(Canvas canvas, Color color) {
        super(canvas);
        // oldColor = canvas.getSelection().getColor();
        selectedShape = canvas.getSelection();
        this.color = color;
    }

    @Override
    void execute() {
        if (first) {
            first = false;
            selectedShape.setColor(color);
            shapeChanged = (ShapeGroup) canvas.getShapes().clone();
            canvas.repaint();
        }else{
            canvas.set_shapes(shapeChanged);
            canvas.repaint();
        }
    }

}
