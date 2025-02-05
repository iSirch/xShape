package xshape;

import xshape.Model.ShapeGroup;

public interface CanvasObserver 
{
    public void onDragShapeStart(Canvas canvas, ShapeGroup dragedShapes);

    public void onDragShapeEnd(Canvas canvas, ShapeGroup dragedShapes);

    public void onDoPopMenu(Canvas canvas, ContextMenu menu);
}
