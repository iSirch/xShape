package xshape;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class ResourceHelper {
    public static ImageIcon getIconResource(String filename) throws IOException
    {
        InputStream stream = App.class.getResourceAsStream('/' + filename);
        
        if(stream == null) throw new IOException();

        return new ImageIcon(ImageIO.read(stream));
    }
}
