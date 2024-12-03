package GradientCreatorInterface;




import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GradientMonocolor extends GradientCreator {

    public GradientMonocolor() {
        super("Mono-Color", false);
    }



        @Override
        public BufferedImage generateColoredImage(int[][] opacityTable ,Color color1, Color color2, double colorIntensityParam, double param1, double param2) {
                try {

                        int x_dim = opacityTable.length;
                        if (x_dim <= 0) {
                                throw new NotA2DTable();
                        }
                        int y_dim = opacityTable[0].length;
                        if (y_dim <= 0) {
                                throw new NotA2DTable();
                        }
                        
                BufferedImage coloredImage = new BufferedImage(x_dim ,y_dim, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = coloredImage.createGraphics();
                g2d.setColor(color1);
                g2d.fillRect(0, 0, x_dim, y_dim);
                g2d.dispose();
                return coloredImage;
                
                  } catch (NotA2DTable ex) {
                        Logger.getLogger(GradientCreatorLeftRight.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                }
        }

        @Override
        protected float[][] generateBlendTable(int x, int y, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {
                System.out.println("generateBlendTable -- Gradient Monocolor -- This methods is not mean to be executed");
                return null;
        }


}