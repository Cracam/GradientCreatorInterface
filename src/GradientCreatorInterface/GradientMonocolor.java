package GradientCreatorInterface;




import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class GradientMonocolor extends GradientCreator {

    public GradientMonocolor() {
        super("Mono-Color", false);
    }



    @Override
    public BufferedImage generateColoredImage(BufferedImage image_in, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {
        BufferedImage coloredImage = new BufferedImage(image_in.getWidth(), image_in.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = coloredImage.createGraphics();
        g2d.setColor(color1);
        g2d.fillRect(0, 0, image_in.getWidth(), image_in.getHeight());
        g2d.dispose();
        return coloredImage;
    }


}