package xshape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import xshape.Command.CommandManager;
import xshape.Command.DeleteCommand;
import xshape.Command.ScaleSizeMoveCommand;
import xshape.Model.Shape;

public class ContextMenu extends JPopupMenu
{
    Canvas canvas;
    CommandManager commandManager;

    public ContextMenu(Canvas canvas) {
        this.canvas = canvas;
        this.add(createMenuItem("Delete", "Delete.png", delete));
        this.add(createMenuItem("Edit...", "PropertyPublic.png", edit));
        this.add(createMenuItem("Group", "Composition.png", group));
        this.add(createMenuItem("Ungroup", "Ungroup.png", ungroup));
        commandManager = commandManager.getInstance();
    }

    private JMenuItem createMenuItem(String text, String filename, ActionListener actionListener)
    {
        ImageIcon icon = null;

        try 
        {
            icon = ResourceHelper.getIconResource(filename);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        JMenuItem item = new JMenuItem(text, icon);
        if(actionListener != null) item.addActionListener(actionListener);

        return item;
    }

    ActionListener edit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScaleSizeMoveCommand c = new ScaleSizeMoveCommand(canvas);
                Shape selectedShape = canvas.getSelection();
                if(selectedShape != null)
                {
                    EditPanel edit = new EditPanel(selectedShape, canvas);
                    int choice = JOptionPane.showConfirmDialog(getParent(),
                                    edit,
                                    "Edit shape",
                                    JOptionPane.OK_CANCEL_OPTION);
    
                    if(choice == JOptionPane.OK_OPTION)
                    {
                        commandManager.executeCommand(c);
                    }
    
                }
            }
        };

    ActionListener delete = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // canvas._shapes.remove(canvas._selectedShapes.getChildren());
            // canvas._selectedShapes = null;
            // canvas.repaint();
            commandManager.executeCommand(new DeleteCommand(canvas));
        };
    };

    ActionListener group = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            canvas._shapes.group(canvas._selectedShapes);
            //canvas._selectedShapes.group();
            canvas.repaint();
        }
    };

    ActionListener ungroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            canvas._shapes.ungroup(canvas._selectedShapes);
            //canvas._selectedShapes.ungroup();
            canvas.repaint();
        }  
    };
}
