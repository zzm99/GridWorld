import static org.junit.Assert.*;
import org.junit.Test;
import java.io.IOException;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;

// Junit Test To ImageProcessing
public class ImageProcessTest {
    private ImplementImageIO imageio = new ImplementImageIO(); // my ImplementImageIO
    private String pic1 = "../target/1.bmp"; // path of the 1.bmp
    private String goalPath = "../goal/"; // path of the goal directory
 
    // test red channel
    @Test
    public void testRedChannel() throws IOException {
        // get the image
        Image image = imageio.myRead(pic1);
        
        // get the goal red channel image
        FileInputStream goalfile = new FileInputStream(goalPath + "1_red_goal.bmp");
        BufferedImage goalimage = ImageIO.read(goalfile);

        // get the red channel
        ImplementImageProcessor processor = new ImplementImageProcessor();
        Image red = processor.showChanelR(image);

        int w = red.getWidth(null);
        int h = red.getHeight(null);
        // transform Image to BufferedImage
        BufferedImage myimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
        myimage.getGraphics().drawImage(red, 0, 0, w, h, null);
        
        // compare the width and height of myimage to goalimage
        assertEquals(myimage.getWidth(null), goalimage.getWidth(null));
        assertEquals(myimage.getHeight(null), goalimage.getHeight(null));

        // compare the rgb of myimage to goalimage
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                assertEquals(goalimage.getRGB(i, j), myimage.getRGB(i, j));
            }
        }
    }
    
    // test green channel
    @Test
    public void testGreenChannel() throws IOException {
        // get the image
        Image image = imageio.myRead(pic1);
        
        // get the goal green channel image
        FileInputStream goalfile = new FileInputStream(goalPath + "1_green_goal.bmp");
        BufferedImage goalimage = ImageIO.read(goalfile);

        // get the green channel
        ImplementImageProcessor processor = new ImplementImageProcessor();
        Image green = processor.showChanelG(image);
        
        int w = green.getWidth(null);
        int h = green.getHeight(null);
        // transform Image to BufferedImage
        BufferedImage myimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
        myimage.getGraphics().drawImage(green, 0, 0, w, h, null);
        
        // compare the width and height of myimage to goalimage
        assertEquals(myimage.getWidth(null), goalimage.getWidth(null));
        assertEquals(myimage.getHeight(null), goalimage.getHeight(null));

        // compare the rgb of myimage to goalimage
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                assertEquals(goalimage.getRGB(i, j), myimage.getRGB(i, j));
            }
        }
    }
    
    // test blue channel
    @Test
    public void testBlueChannel() throws IOException {
        // get the image
        Image image = imageio.myRead(pic1);

        // get the goal blue channel image
        FileInputStream goalfile = new FileInputStream(goalPath + "1_blue_goal.bmp");
        BufferedImage goalimage = ImageIO.read(goalfile);

        // get the blue channel
        ImplementImageProcessor processor = new ImplementImageProcessor();
        Image blue = processor.showChanelB(image);

        int w = blue.getWidth(null);
        int h = blue.getHeight(null);
        // transform Image to BufferedImage
        BufferedImage myimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
        myimage.getGraphics().drawImage(blue, 0, 0, w, h, null);

        // compare the width and height of myimage to goalimage
        assertEquals(blue.getWidth(null), goalimage.getWidth(null));
        assertEquals(blue.getHeight(null), goalimage.getHeight(null));

        // compare the rgb of myimage to goalimage
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                assertEquals(goalimage.getRGB(i, j), myimage.getRGB(i, j));
            }
        }
    }

    /**
     * Test gray channel convert
     */
    @Test
    public void testGrayChannel() throws IOException {
        // get the image
        Image image = imageio.myRead(pic1);

        // use standard library to get the goal gray channel image
        FileInputStream goalfile = new FileInputStream(goalPath + "1_gray_goal.bmp");
        BufferedImage goalimage = ImageIO.read(goalfile);
        
        // get the gray channel
        ImplementImageProcessor processor = new ImplementImageProcessor();
        Image gray = processor.showGray(image);

        int w = gray.getWidth(null);
        int h = gray.getHeight(null);
        // transform Image to BufferedImage
        BufferedImage myimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
        myimage.getGraphics().drawImage(gray, 0, 0, w, h, null);

        // compare the width and height of myimage to goalimage
        assertEquals(gray.getWidth(null), goalimage.getWidth(null));
        assertEquals(gray.getHeight(null), goalimage.getHeight(null));

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                assertEquals(goalimage.getRGB(i, j), myimage.getRGB(i, j));
            }
        }
    }
}
