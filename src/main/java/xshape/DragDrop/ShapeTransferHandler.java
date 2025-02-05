package xshape.DragDrop;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import xshape.ShapeIcon;
import xshape.ShapeToolBar;
import xshape.Command.CommandManager;
import xshape.Command.AddCommand;
import xshape.Canvas;
import xshape.Model.Shape;
import xshape.Model.ShapeGroup;

public class ShapeTransferHandler extends TransferHandler {

    CommandManager commandManager;

    @Override
    public boolean canImport(TransferSupport support) {
        commandManager = commandManager.getInstance();
        Component component = support.getComponent();
        return (component instanceof Canvas && support.isDataFlavorSupported(ShapeTransferable.SHAPE_ICON_FLAVOR))
        || ((component instanceof ShapeToolBar || component instanceof JButton) && support.isDataFlavorSupported(ShapeTransferable.SHAPE_FLAVOR));
    }

    @Override //appelée au drop
    public boolean importData(TransferSupport support) 
    {
        if (!canImport(support)) return false;
        
        try
        {
            DataFlavor flavor = support.getTransferable().getTransferDataFlavors()[0];

            Object value = support.getTransferable().getTransferData(flavor);
            if (!(value instanceof Shape)) return false;

            Component component = support.getComponent();

            //drop from JButton to Whiteboard
            if (component instanceof Canvas) {
                // ((Canvas)component).addShape(((Shape)value).clone());
                commandManager.executeCommand(new AddCommand((Canvas)component, ((Shape)value).clone()));
                return true;
            }

            //drop from Whiteboard to ShapeToolBar
            if(component instanceof ShapeToolBar)
            {
                ((ShapeToolBar)component).add(((Shape)value).clone());
                return true;
            }

            //drop from Whiteboard to JButton in ShapeToolBar
            if(component instanceof JButton)
            {
                ((ShapeToolBar)component.getParent()).add(((Shape)value).clone());
                return true;
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getSourceActions(JComponent component) {
        return DnDConstants.ACTION_COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent component) 
    {
        //drag from JButton
        if (component instanceof JButton) 
        {    
            JButton button = (JButton)component;

            try 
            {
                ShapeIcon icon = (ShapeIcon)button.getIcon();
                return new ShapeTransferable(icon);//SHAPE_ICON_FLAVOR
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        //drag from Whiteboard
        if(component instanceof Canvas)
        {
            Canvas canvas = (Canvas)component;
            ShapeGroup selectedShapes = canvas.getSelection();

            if (selectedShapes != null)
            {
                Shape[] children = selectedShapes.getChildren();
            
                if(children.length == 1) return new ShapeTransferable(children[0]); //alone shape selection

                return new ShapeTransferable(selectedShapes);
            }
        }

        return null;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        //appelé après à la fin du drop
    }
}