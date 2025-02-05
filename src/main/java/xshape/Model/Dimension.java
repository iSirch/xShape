package xshape.Model;

public class Dimension 
{
    public double width;
    public double height;

    public Dimension(double width, double height)
    {
        this.width = width;
        this.height = height;
    }

    public Dimension(Dimension dimension)
    {
        this.width = dimension.width;
        this.height = dimension.height;
    }

    public void setSize(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
}
