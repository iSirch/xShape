package xshape;

import java.awt.*;
import javax.swing.*;

import xshape.Model.Polygon;
import xshape.Save.SaveToJson;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class App {

    public static void main(String[] args) {
        JFrame frame = new JFrame("XShape Swing/AWT Rendering");
        ShapeToolBar toolBar = new ShapeToolBar();
        WindowAdapter wa = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SaveToJson stj = new SaveToJson();
                stj.save(toolBar.getShapes(), "toolBar.json");
                System.exit(0);
            }
        };
        frame.addWindowListener(wa);

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        canvas.setPreferredSize(new Dimension(500, 500));
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(canvas, BorderLayout.CENTER);

        frame.add(toolBar, BorderLayout.WEST);

        MenuBar menu = new MenuBar(canvas);
        frame.add(menu, BorderLayout.NORTH);

        Editor editor = new Editor(canvas, toolBar, menu);// , null)

        setIcon(frame);
        frame.pack();
        frame.setVisible(true);
    }

    static void setIcon(Frame frame) 
    {  
        String osName = System.getProperty("os.name").toLowerCase();
        if(!osName.contains("win") && !osName.contains("mac")) return;// changer l'icon de cet façon ne fonction pas sur linux

        final Polygon hex = new Polygon(new Point(0, 0), 5, 32);
        try {
            BufferedImage bi = new BufferedImage((int) hex.size.width, (int) hex.size.height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            hex.draw(g2d);

            frame.setIconImage(bi);
            Taskbar.getTaskbar().setIconImage(bi);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
