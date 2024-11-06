package GradientCreatorInterface;


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class GradientCreatorUpDown extends GradientCreator {

    public GradientCreatorUpDown() {
        super("Haut-bas", true) ;
    }



    @Override 
   public BufferedImage generateColoredImage(BufferedImage image_in, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {

             colorIntensityParam=convertBlendCoeff(colorIntensityParam);
        
         
         int x_dim=image_in.getWidth();
         int y_dim=image_in.getHeight();
         
         float [][] blendTable= new float[x_dim][y_dim];

     
             // Loop through each pixel of the input image
             for (int y = 0; y < y_dim; y++) {
                      float alphaBlend = (float) Math.pow((float) y / (y_dim - 1), colorIntensityParam);
                      for (int x = 0; x < x_dim; x++) {
                               blendTable[x][y] = alphaBlend;
                      }
             }
             



             
         return generateImage(blendTable,color2,color1);
      
    }
}
//blend x2 if >1 *15 else nothing