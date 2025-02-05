package xshape.Save;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import xshape.Model.Dimension;
import xshape.Model.Polygon;
import xshape.Model.Shape;
import xshape.Model.ShapeGroup;

public class ShapeSave{
    public class Couleur{
        int r,g,b;
        public Couleur(Color c) {
            this.r = c.getRed();
            this.g = c.getGreen();
            this.b = c.getBlue();
        }
        public int getR() {
            return r;
        }
        public int getG() {
            return g;
        }
        public int getB() {
            return b;
        }

        public Color getColor(){
            return new Color(r,g,b);
        }
    }
    public Point position;
    public Dimension size;
    public int nbSides;
    public double sideLength;
    public String type;
    public ArrayList<ShapeSave> shapes = new ArrayList<>();
    public Couleur couleur;
    public ShapeSave(Shape s){
        this.position = s.position();
        this.size = s.size();
        this.type = s.name();
        this.couleur = new Couleur(s.getColor());
        if (s instanceof Polygon){
            nbSides = ((Polygon) s).nbSides();
            sideLength = ((Polygon) s).sideLength();
        }
        if (s instanceof ShapeGroup){
            ArrayList<Shape> shapesLocal = ((ShapeGroup)s).getShapeGroup();
            for(Shape p: shapesLocal){
                shapes.add(new ShapeSave(p));
            }
        }
    }

    public Color getColor(){
        return couleur.getColor();
    }


}