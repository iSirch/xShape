package xshape;

import java.awt.Point;

import xshape.Command.CommandManager;
import xshape.Command.MoveCommand;
import xshape.Model.Shape;
import xshape.Model.ShapeGroup;

public class Editor implements CanvasObserver
{
    // Mediator for the GUI components

    Canvas canvas;
    ShapeToolBar toolBar;
    MenuBar menu;
    EditPanel editPanel;
    CommandManager commandManager;
    Point dragStart;

    public Editor(Canvas canvas, ShapeToolBar toolBar, MenuBar menu){//, EditPanel editPanel) {
        this.canvas = canvas;
        this.toolBar = toolBar;
        this.menu = menu;
        commandManager = commandManager.getInstance();
        //this.editPanel = editPanel;
        canvas.subscribe(this);
    }

    @Override
    public void onDragShapeStart(Canvas canvas, ShapeGroup dragedShapes) {
        // TODO Auto-generated method stub
        System.out.printf("shape(s) drag start (nb:%d) %s\n", dragedShapes.getChildren().length, dragedShapes.position().toString());
        dragStart = new Point(canvas.getSelection().position());
    }

    @Override
    public void onDragShapeEnd(Canvas canvas, ShapeGroup dragedShapes) { // update()
        // TODO Auto-generated method stub
        System.out.printf("shape(s) drag end (nb:%d) %s\n", dragedShapes.getChildren().length, dragedShapes.position().toString());
        // commandManager.executeCommand(new MoveCommand(canvas, dragStart,  new Point(dragedShapes.position())));
    }

    @Override
    public void onDoPopMenu(Canvas canvas, ContextMenu menu) {
        // TODO Auto-generated method stub
        System.out.println("pop");
    }




}
