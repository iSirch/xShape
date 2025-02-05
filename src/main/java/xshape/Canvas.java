package xshape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import xshape.Command.CommandManager;
import xshape.Command.MoveCommand;
import xshape.DragDrop.ShapeTransferHandler;
import xshape.Model.Rectangle;
import xshape.Model.Shape;
import xshape.Model.ShapeFactory;
import xshape.Model.ShapeGroup;

public class Canvas extends JPanel {
    ContextMenu menu;
    private ShapeFactory _factory = null;
    ShapeGroup _shapes = null;
    ShapeGroup _selectedShapes = null;
    Rectangle selectionRect = null;
    MoveCommand moveCommand;
    boolean dragging = false;
    Point mousePressPt;
    boolean block = true;

    CommandManager commandManager;
    ArrayList<CanvasObserver> observers;

    public Canvas() {
        menu = new ContextMenu(this);
        this.addMouseListener(mousePressAdapter);
        this.addMouseMotionListener(mouseDragAdapter);

        this.setTransferHandler(new ShapeTransferHandler());
        _factory = new ShapeFactory();
        createScene();
        observers = new ArrayList<CanvasObserver>();
        commandManager = commandManager.getInstance();
    }

    public void subscribe(CanvasObserver target) {
        observers.add(target);
    }

    public void unsubscribe(CanvasObserver target) {
        observers.remove(target);
    }

    public void onDragShapeStart(ShapeGroup dragedShapes) {
        for (CanvasObserver canvasObserver : observers) {
            canvasObserver.onDragShapeStart(this, dragedShapes);
        }
    }

    public void onDragShapeEnd(ShapeGroup dragedShapes) { // Notify
        for (CanvasObserver canvasObserver : observers) {
            canvasObserver.onDragShapeEnd(this, dragedShapes);
        }
    }

    public void onDoPopMenu(ContextMenu menu) {
        for (CanvasObserver canvasObserver : observers) {
            canvasObserver.onDoPopMenu(this, menu);
        }
    }

    public ArrayList<Shape> getShapeGroup() {
        return _shapes.getShapeGroup();
    }

    MouseAdapter mousePressAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            // context menu
            if (e.isPopupTrigger()) {
                doPop(e);
                onDoPopMenu(menu);
                return;
            } else if (menu.isVisible())
                return;
            //

            mousePressPt = e.getPoint();// to define selection rect start pos

            if (_selectedShapes == null) // allow directly dragging shape from a press
                _selectedShapes = _shapes.getShapesAt(e.getX(), e.getY());

            // System.out.println("press: " + ((_selectedShapes == null) ? "un" : "") +
            // "selected at:" + e.getX() + " " + e.getY());

            ((Canvas) e.getSource()).repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            boolean wasDragging = dragging;
            dragging = false;

            // context menu
            if (e.isPopupTrigger()) {
                doPop(e);
                onDoPopMenu(menu);
                return;
            } else if (menu.isVisible())
                return;
            //

            if (!wasDragging) // mouse released and was dragging
            {
                // unselect/select single shape
                _selectedShapes = _shapes.getShapesAt(e.getX(), e.getY());
            } else if (selectionRect != null) // was dragging to select
            {
                _selectedShapes = _shapes.getShapesIn(selectionRect);
                selectionRect = null;
            } else // was dragging the selection
            {
                onDragShapeEnd(_selectedShapes); // notify post-drag positioning state
                moveCommand.executeDelayed();
                block = true;
            }

            // System.out.println("release: " + ((_selectedShapes == null) ? "un" : "") +
            // "selected");

            ((Canvas) e.getSource()).repaint();
        };
    };

    private void doPop(MouseEvent e) {
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    MouseAdapter mouseDragAdapter = new MouseAdapter() {

        @Override
        public void mouseDragged(MouseEvent e) {
            boolean wasDragging = dragging;
            dragging = true;
            Canvas canvas = (Canvas) e.getSource();

            if (!wasDragging) {
                if (_selectedShapes == null) { // On crée le rectangle de selection
                    selectionRect = _factory.createRectangle(Math.min(e.getX(), mousePressPt.x),
                            Math.min(e.getY(), mousePressPt.y),
                            Math.abs(e.getX() - mousePressPt.x),
                            Math.abs(e.getY() - mousePressPt.y));
                } else {
                    if (_selectedShapes.inBounds(e.getX(), e.getY())) {
                        onDragShapeStart(_selectedShapes); // notify pre-drag positioning state
                    } else {
                        // don't allow draging shapes from anywhere
                        dragging = false;
                        _selectedShapes = null;
                    }
                }
            } else // still draging 
            {
                if (e.isControlDown()) { // la touche controle est pressée on envoie la selection dans la toolbar
                    // prepare for transfer to toolbar
                    TransferHandler handle = canvas.getTransferHandler();
                    handle.exportAsDrag(canvas, e, TransferHandler.COPY);
                } else if (selectionRect != null)  
                {
                    // update selection rect
                    selectionRect.setPos(Math.min(e.getX(), mousePressPt.x),
                            Math.min(e.getY(), mousePressPt.y));
                    selectionRect.setSize(Math.abs(e.getX() - mousePressPt.x),
                            Math.abs(e.getY() - mousePressPt.y)); 
                } else if (_selectedShapes != null){ // click-drag positioning / drag the shapes
                    _selectedShapes.setCenterToPos(e.getX(), e.getY()); 
                    if(block){
                        block = false;
                        moveCommand = new MoveCommand(canvas);
                        commandManager.executeCommand(moveCommand);
                    }
                }
            }

            canvas.repaint();

            // System.out.println("drag to: " + e.getX() + " " + e.getY());
        }
    };

    private void createScene() {
        _shapes = new ShapeGroup();
        Shape shape = _factory.createRectangle(250, 250, 75, 25);

        _shapes.add(shape);
        _shapes.add(_factory.createPolygon(150, 150, 5, 20));
    }

    public void addShape(Shape shape) {
        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, this);
        shape.setPos(point.x, point.y);

        _shapes.add(shape);
        System.out.println("new shape dropped.");
        this.repaint();
    }

    public void removeShape(Shape shape) {
        _shapes.remove(shape);
        System.out.println("shape deletion.");
        this.repaint();
    }

    public void removeAllShape() {
        _shapes = new ShapeGroup();
        System.out.println("All shape deletion.");
        clearCanvas();
        this.repaint();
    }

    public void clearCanvas() {
        Graphics g = getGraphics(); // Get the graphics context
        g.setColor(getBackground()); // Set color to background color
        g.clearRect(0, 0, getWidth(), getHeight()); // Clear the entire canvas
    }

    public void addShapeFromJson(Shape shape) {
        _shapes.add(shape);
        System.out.println("new shape dropped.");
        this.repaint();
    }

    public ShapeGroup getSelection() {
        return _selectedShapes;
    }

    public ShapeGroup getShapes() {
        return _shapes;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // for (Shape s : _shapes)
        // s.draw(g);

        _shapes.draw(g);

        if (_selectedShapes != null)
            _selectedShapes.drawSelection(g, true, Color.green, 0);
        if (selectionRect != null)
            selectionRect.drawSelection(g, false, Color.magenta, 0);
    }

    public void nullSelect() {
        _selectedShapes = null;
    }

    public void simpleAdd(Shape s) {
        _shapes.add(s);
        // _selectedShapes = (ShapeGroup) s;
        repaint();
    }

    public void set_shapes(ShapeGroup _shapes) {
        this._shapes = _shapes;
    }

}
