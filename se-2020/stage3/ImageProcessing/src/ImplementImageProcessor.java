import imagereader.IImageProcessor;  
import java.awt.Image;  
import java.awt.Toolkit;  
import java.awt.image.FilteredImageSource;  
import java.awt.image.RGBImageFilter;  

// my implement of the ImageProcessor
public class ImplementImageProcessor implements IImageProcessor { 
    // red channel
	public Image showChanelR(Image sourceImage) {
		ColorFilter redFilter = new ColorFilter(1);  
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), redFilter));  
        
	}

    // green channel
	public Image showChanelG(Image sourceImage) {
		ColorFilter greenFilter = new ColorFilter(2);  
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), greenFilter));  
       
	}

    // blue channel
	public Image showChanelB(Image sourceImage) {
		ColorFilter blueFilter = new ColorFilter(3);   
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), blueFilter));  
   
	}

    // gray channel
	public Image showGray(Image sourceImage) {
		ColorFilter grayFilter = new ColorFilter(4);  
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), grayFilter));      
	}
}

class ColorFilter extends RGBImageFilter{  
    private int color;  
    
    public ColorFilter(int c){  
        color = c;  
        canFilterIndexColorModel = true;  
    }  
    
    public int filterRGB(int x, int y, int rgb){   
        // color: 1 for red, 2 for green, 3 for blue, else for gray 
        if(color==1){  
            return (rgb & 0xffff0000);  
        }else if(color==2){  
            return (rgb & 0xff00ff00);  
        }else if(color==3){  
            return (rgb & 0xff0000ff);  
        }else{  
            // I = 0.299 * r + 0.587 * G + 0.114 * B
            // where R, G and B are the color values of red, green and blue channels respectively.
            // Then change the color value of the three color channels to this value. 
            // In this way, the original color image becomes a gray image.
            int g = (int)(((rgb & 0x00ff0000) >> 16)*0.299 + ((rgb & 0x0000ff00) >> 8)*0.587  
                    + ((rgb & 0x000000ff))*0.114);  
            return (rgb & 0xff000000) + (g << 16) + (g << 8) + g;  
        }  
    }  
} 