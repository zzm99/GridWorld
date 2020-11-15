import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.Toolkit;  
import java.awt.image.BufferedImage;  
import java.awt.image.ImageProducer;  
import java.awt.image.MemoryImageSource;  
import java.awt.image.*;
import java.io.File;  
import java.io.FileInputStream;  
import javax.imageio.ImageIO;  
import imagereader.IImageIO;

// my implement of the imageio
public class ImplementImageIO implements IImageIO {
    private Image img;

    // my implement of my Read
    public Image myRead(String filePath) {
        try {
            // init
            File file = new File(filePath);
            // use FileInputStream to read the file into byte
            FileInputStream inputstream = new FileInputStream(file);

            // read the bmp head
            byte bmphead[] = new byte[14];
            inputstream.read(bmphead, 0, 14);

            // read the bmp info
            byte bmpinfo[] = new byte[40];
            inputstream.read(bmpinfo, 0, 40);
            
            // get the bmp width (4 bytes) (pixel)
            int bmpWidth = (int)((bmpinfo[7] & 0xff) << 24 | (bmpinfo[6] & 0xff) << 16);
            bmpWidth = bmpWidth | (int)((bmpinfo[5] & 0xff) << 8 | (bmpinfo[4] & 0xff));

            // get the bmp height (4 bytes) (pixel)
            int bmpHeight = (int)((bmpinfo[11] & 0xff) << 24 | (bmpinfo[10] & 0xff) << 16);
            bmpHeight = bmpHeight | (int)((bmpinfo[9] & 0xff) << 8 | (bmpinfo[8] & 0xff));

            // get the bits per pixel (8 (gray scale) and 24 (color))
            int bitsPerPixel = (int)((bmpinfo[15] & 0xff) << 8 | (bmpinfo[14] & 0xff));

            // get the bmp image size (The number of bytes occupied by the bitmap)
            int bmpImageSize = (int)((bmpinfo[23] & 0xff) << 24 | (bmpinfo[22] & 0xff) << 16);
            bmpImageSize = bmpImageSize | (int)((bmpinfo[21] & 0xff) << 8 | (bmpinfo[20] & 0xff));
            
            if (bitsPerPixel == 24) {
                //count the empty bit Nums
                int emptyBytes = bmpImageSize / bmpHeight - 3 * bmpWidth;
                if (emptyBytes == 4) {
                    emptyBytes = 0;
                }

                // new a pixel array
                int pixel[] = new int [bmpWidth * bmpHeight];
                // new a bmp byte array
                byte bmpBytes[] = new byte[bmpImageSize];
                inputstream.read(bmpBytes, 0, bmpImageSize);
                
                // Pixels are saved from bottom to top, left to right.
                int k = 0;
                for (int i = bmpHeight-1; i >= 0; i--) {
                    for (int j = 0; j < bmpWidth; j++) {
                        // The data is read backwards
                        // 24 bit BMP image, a pixel is three bytes of R, G, B, each channel is exactly one byte.
                        pixel[i*bmpWidth+j] = (0xff << 24 | (bmpBytes[k+2] & 0xff) << 16 | (bmpBytes[k+1] & 0xff) << 8 | (bmpBytes[k] & 0xff));
                        k += 3; 
                    }
                    k += emptyBytes;
                }
                // create the img
                img = Toolkit.getDefaultToolkit().createImage((ImageProducer)new MemoryImageSource(bmpWidth, bmpHeight, pixel, 0, bmpWidth));
            }
            inputstream.close();
            return img;
        }catch (Exception e) {
            // handle the exception 
            return (Image)null;
        }
    }
    
    // my implement of my Write
    public Image myWrite(Image image, String filePath) {
        try {
            // new the file
            File file = new File(filePath + ".bmp");
            BufferedImage buffer = new BufferedImage(image.getWidth((ImageObserver)null), image.getHeight((ImageObserver)null), BufferedImage.TYPE_INT_RGB);
            
            // new the graphics
            Graphics2D gh = buffer.createGraphics();
            gh.drawImage(image, 0, 0, null);
            gh.dispose();

            // write the img
            ImageIO.write(buffer, "bmp", file);
            return image;
        } catch (Exception e) {
            // handle the exception 
            return (Image)null;
        }
    }

}