package xshape.Model;

public interface Composable 
{
    void add(Shape component);
    void add(Shape ... shapes);
	void remove(Shape component);
    void remove(Shape ... shapes);
    Shape[] getChildren();
}
