package xshape.DragDrop;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import xshape.ShapeToolBar;
import xshape.Model.Shape;

public class DeleteShapeTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(TransferSupport support) {
        return (support.getComponent() instanceof JButton)
         && (support.isDataFlavorSupported(ShapeTransferable.SHAPE_ICON_FLAVOR) || support.isDataFlavorSupported(ShapeTransferable.SHAPE_FLAVOR));
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

            if (component instanceof JButton) {
                ShapeToolBar toolBar = (ShapeToolBar)component.getParent();
                toolBar.remove((Shape)value);

                //TODO: del from whiteboard, singleton?
            }

            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getSourceActions(JComponent component) {
        return DnDConstants.ACTION_MOVE;
    }
}