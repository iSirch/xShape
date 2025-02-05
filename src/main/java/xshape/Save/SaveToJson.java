package xshape.Save;

import java.io.FileWriter;
import java.util.ArrayList;
import com.google.gson.Gson;
import xshape.ShapeToolBar;
import xshape.Model.Shape;

public class SaveToJson {
    final String filePathToolBar = "dataToolBar.json";
    final String filePathCanvas = "canvas.json";
    Gson gson = new Gson();
    ShapeToolBar shapeToolBar;
    String jsonString;

    public void save(ArrayList<Shape> shape) {
        ArrayList<Object> shapes = new ArrayList<>();
        for (Shape s : shape) {
            shapes.add(new ShapeSave(s));
        }
        // Convert object to JSON
        jsonString = gson.toJson(shapes);
        // Write JSON to file
        try (FileWriter writer = new FileWriter(filePathCanvas)) {
            writer.write(jsonString);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fail Writer Json ");
        }

        System.out.println("Object saved to file: " + filePathCanvas);
    }

    public void save(ArrayList<Shape> shape, String path) {
        ArrayList<Object> shapes = new ArrayList<>();
        for (Shape s : shape) {
            shapes.add(new ShapeSave(s));
        }
        // Convert object to JSON
        jsonString = gson.toJson(shapes);
        // Write JSON to file
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(jsonString);
            writer.flush();
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Fail Writer Json ");
            return;
        }

        System.out.println("Object saved to file: " + path);
    }

}
