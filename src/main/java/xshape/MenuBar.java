package xshape;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import xshape.Command.ColorCommand;
import xshape.Command.CommandManager;
import xshape.Command.ScaleSizeMoveCommand;
import xshape.Model.Shape;
import xshape.Model.ShapeAbstact;
import xshape.Model.ShapeGroup;
import xshape.Save.JsontoShape;
import xshape.Save.SaveToJson;

public class MenuBar extends JMenuBar {
    Canvas canvas;
    CommandManager commandManager = CommandManager.getInstance();

    public MenuBar(Canvas canvas) {
        this.canvas = canvas;
        this.add(createMenuButton("OpenFile.png", open));
        this.add(createMenuButton("DocumentOK.png", save));
        this.add(createMenuButton("Undo.png", undo));
        this.add(createMenuButton("Redo.png", redo));
        this.add(createMenuButton("PropertyPublic.png", edit));
        this.add(createMenuButton("Composition.png", group));
        this.add(createMenuButton("Ungroup.png", ungroup));
        
        JButton colorBtn = createMenuButton("ColorPalette.png", color);
        colorBtn.setBackground(ShapeAbstact.DEFAULT_COLOR);
        this.add(colorBtn);
    }

    private JButton createMenuButton(String filename) {
        return createMenuButton(filename, null);
    }

    private JButton createMenuButton(String filename, ActionListener actionListener) {
        ImageIcon icon = null;

        try {
            icon = ResourceHelper.getIconResource(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton button = new JButton(icon);
        if (actionListener != null)
            button.addActionListener(actionListener);

        // Border emptyBorder = BorderFactory.createEmptyBorder(4, 4, 4 ,4);
        // .setBorder(emptyBorder);
        // .setBorderPainted(false);

        button.setFocusable(false);

        return button;
    }

    @Deprecated
    private JButton createColorButton(ActionListener actionListener) {

        JButton button = new JButton();
        if (actionListener != null)
            button.addActionListener(actionListener);
        ;
        button.setBackground(ShapeAbstact.DEFAULT_COLOR);
        button.setOpaque(true);
        Border emptyBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), 
                                                                BorderFactory.createEmptyBorder(10,10,10,12));
        button.setBorder(emptyBorder);

        button.setFocusable(false);

        return button;
    }

    ActionListener edit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Shape selectedShape = canvas.getSelection();
            ScaleSizeMoveCommand c = new ScaleSizeMoveCommand(canvas);
            // commandManager.executeCommand(new ScaleSizeMoveCommand(canvas));
            if (selectedShape != null) {
                EditPanel edit = new EditPanel(selectedShape, canvas);
                int choice = JOptionPane.showConfirmDialog(getParent(),
                        edit,
                        "Edit shape",
                        JOptionPane.OK_CANCEL_OPTION);

                if (choice == JOptionPane.OK_OPTION) {
                    commandManager.executeCommand(c);
                }

            }
        }
    };

    ActionListener group = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            canvas._shapes.group(canvas._selectedShapes);
            // canvas._selectedShapes.group();
        }
    };

    ActionListener ungroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            canvas._shapes.ungroup(canvas._selectedShapes);
            // canvas._selectedShapes.ungroup();
        }
    };

    ActionListener save = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = "";
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePath = selectedFile.getPath();
            }
            SaveToJson saveToJson = new SaveToJson();
            saveToJson.save(canvas.getShapeGroup(),filePath);
        }
    };

    ActionListener open = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Shape> shapes;
            JsontoShape j = new JsontoShape();
            try {
                String filePath = "";
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getPath();
                }
                shapes = j.getShape(filePath);
                if (shapes==null) return;
                canvas.removeAllShape();
                for(Shape s : shapes){
                    canvas.addShapeFromJson(s);
                }
            } catch (Exception ex) {
                System.out.println("Erreur restoration de canavs depuis un json");

            }
            
        }
    };

    ActionListener undo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Undo");
            commandManager.undo();
            // canvas.repaint();
        }
    };

    ActionListener redo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Redo");
            commandManager.redo();
        }
    };

    ActionListener color = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ShapeGroup selection = canvas.getSelection();
            if(selection == null) return;

            Color color = selection.color;
            color = JColorChooser.showDialog(null, "Choose a color", color);
            ((JButton)e.getSource()).setBackground(color);
            commandManager.executeCommand(new ColorCommand(canvas, color));;
            // canvas._selectedShapes.setColor(color);
            // canvas.repaint();
        }
    };


}
