package xshape.DragDrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import xshape.ShapeIcon;
import xshape.Model.Shape;

public class ShapeTransferable implements Transferable {

    public static final DataFlavor SHAPE_FLAVOR = new DataFlavor(Shape.class, "xshape/Shape");
    public static final DataFlavor SHAPE_ICON_FLAVOR = new DataFlavor(ShapeIcon.class, "xshape/Shape from icon");
    private Shape shape;
    private DataFlavor flavor;

    public ShapeTransferable(Shape shape) {
        this.shape = shape;
        this.flavor = SHAPE_FLAVOR;
    }

    public ShapeTransferable(ShapeIcon shapeIcon) {
        this.shape = shapeIcon.getShape();
        this.flavor = SHAPE_ICON_FLAVOR;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.flavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(this.flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return shape;
    }
}