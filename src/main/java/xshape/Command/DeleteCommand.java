package xshape.Command;

import xshape.Canvas;
import xshape.Model.Shape;
import xshape.Model.ShapeGroup;

public class DeleteCommand extends Command {

    Shape getChildrenShape[];
    Shape canvasShape;
    Originator originator;
    Memento m1;

    public DeleteCommand(Canvas canvas) {
        super(canvas);
        canvasShape = canvas.getShapes();
        selectedShape = canvas.getSelection();
        getChildrenShape = selectedShape.getChildren();
        // originator = new Originator();
        // originator.setState(selectShape);
        // m1 = originator.createMemento();
    }

    // @Override
    // void undo() {
    //     canvas.simpleAdd(selectedShape);
    //     canvas.repaint();
    // }

    // @Override
    // void execute() {
    //     if(first){
    //         first = false;
    //         canvasShape.remove(getChildrenShape);
    //         canvas.nullSelect();
    //         canvas.repaint();
    //         return;
    //     }
    //     canvas.removeShape(selectedShape);
    //     canvas.repaint();
        
    //     // canvas._shapes.remove(canvas._selectedShapes.getChildren());
    //     // canvas._selectedShapes = null;
    //     // canvas.repaint();
    // }

    @Override
    void execute() {
        if (first) {
            first = false;
            canvasShape.remove(getChildrenShape);
            canvas.nullSelect();
            shapeChanged = (ShapeGroup) canvas.getShapes().clone();
            canvas.repaint();
        }else{
            canvas.set_shapes(shapeChanged);
            canvas.nullSelect();
            canvas.repaint();
        }
    }
    
}
