package xshape.Save;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import xshape.Model.Cercle;
import xshape.Model.Polygon;
import xshape.Model.Rectangle;
import xshape.Model.Shape;
import xshape.Model.ShapeGroup;

public class JsontoShape {
    String filePath = "canvas.json";
    String jsonString;
    ArrayList<Object> shapeSaves = new ArrayList<>();
    ArrayList<Shape> shapes = new ArrayList<>();

    public String readFile(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return new String(bytes);
    }

    public ArrayList<Shape> getShape(String filePathL){
        ArrayList<Shape> shapes = new ArrayList<>();
        Gson gson = new Gson();
        try {
            jsonString = readFile(filePathL);
        }
        catch (Exception e) {
            System.out.println("Erreur lecture du fichier "+ filePathL);
            return null;
        }
        
        TypeToken<ArrayList<ShapeSave>> typeToken = new TypeToken<ArrayList<ShapeSave>>() {};
        shapeSaves = gson.fromJson(jsonString, typeToken.getType());
        for (Object oj : shapeSaves) {
            ShapeSave o = (ShapeSave) oj;
            shapes.add(convert(o));
        }
        return shapes;
    }

    private Shape convert(ShapeSave o){
        if (o.type.equals("Rectangle")) {
            return (new Rectangle(o.position, o.size, o.getColor()));
        } else if (o.type.equals("Cercle")) {
            return (new Cercle(o.position, o.size, o.getColor()));
        } else if (o.type.equals("Groups")) {
            ShapeGroup g = new ShapeGroup(o.position.x,o.position.y,(int)o.size.width,(int)o.size.height);
            for(ShapeSave ss : o.shapes){
                g.add(convert(ss));
            }
            return g;
        } else {
            return (new Polygon(o.position, o.nbSides, o.sideLength, o.getColor()));
        }
    }

}
