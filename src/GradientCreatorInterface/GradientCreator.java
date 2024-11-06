package GradientCreatorInterface;



import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import javafx.embed.swing.SwingFXUtils;

/**
 * This class is used to create gradient and his preview it will also comunicate
 * the name, max lenght, unit with the GradientCreator Picker interface
 *
 * @author LECOURT Camille
 */
public abstract class GradientCreator {

         private final String name;
         //private final Image previewImage; if custom cmbobox implementation

         private final boolean use2color;

         private final boolean slideBar1Used;
         private final String slidebar1_name;
         private final String slidebar1_unit;
         private final int slidebar1_min;
         private final int slidebar1_max;
         private final int slidebar1increment;

         private final boolean slideBar2Used;
         private final String slidebar2_name;
         private final String slidebar2_unit;
         private final int slidebar2_min;
         private final int slidebar2_max;
         private final int slidebar2increment;

         private static final float maxBlend = (float) 7.5;

         public GradientCreator(String name, boolean use2color, boolean slideBar1Used, String slidebar1_name, String slidebar1_unit, int slidebar1_min, int slidebar1_max, int slidebar1increment, boolean slideBar2Used, String slidebar2_name, String slidebar2_unit, int slidebar2_min, int slidebar2_max, int slidebar2increment) {
                  this.name = name;
                  this.use2color = use2color;
                  this.slideBar1Used = slideBar1Used;
                  this.slidebar1_name = slidebar1_name;
                  this.slidebar1_unit = slidebar1_unit;
                  this.slidebar1_min = slidebar1_min;
                  this.slidebar1_max = slidebar1_max;
                  this.slidebar1increment = slidebar1increment;

                  this.slideBar2Used = slideBar2Used;
                  this.slidebar2_name = slidebar2_name;
                  this.slidebar2_unit = slidebar2_unit;
                  this.slidebar2_min = slidebar2_min;
                  this.slidebar2_max = slidebar2_max;
                  this.slidebar2increment = slidebar2increment;
         }

         public GradientCreator(String name, boolean use2color, String slibar1_name, String slidebar1_unit, int slidebar1_min, int slidebar1_max, int slidebar1increment, String slidebar2_name, String slidebar2_unit, int slidebar2_min, int slidebar2_max, int slidebar2increment) {
                  this(name, use2color, true, slibar1_name, slidebar1_unit, slidebar1_min, slidebar1_max, slidebar1increment, true, slidebar2_name, slidebar2_unit, slidebar2_min, slidebar2_max, slidebar2increment);

         }

         public GradientCreator(String name, boolean use2color, String slibar1_name, String slidebar1_unit, int slidebar1_min, int slidebar1_max, int slidebar1increment) {
                  this(name, use2color, true, slibar1_name, slidebar1_unit, slidebar1_min, slidebar1_max, slidebar1increment, false, "", "", 0, 0, 0);
         }

         public GradientCreator(String name, boolean use2color) {
                  this(name, use2color, false, "", "", 0, 0, 0, false, "", "", 0, 0, 0);
         }

         public static float getMaxBlend() {
                  return maxBlend;
         }

         public int getSlidebar1_min() {
                  return slidebar1_min;
         }

         public int getSlidebar1_max() {
                  return slidebar1_max;
         }

         public int getSlidebar2_min() {
                  return slidebar2_min;
         }

         public int getSlidebar2_max() {
                  return slidebar2_max;
         }

         public String getName() {
                  return name;
         }

         public boolean isSlideBar1Used() {
                  return slideBar1Used;
         }

         public boolean isSlideBar2Used() {
                  return slideBar2Used;
         }



         public String getSlidebar1_unit() {
                  return slidebar1_unit;
         }



         public String getSlidebar2_unit() {
                  return slidebar2_unit;
         }

         public boolean isUse2color() {
                  return use2color;
         }

         public String getSlidebar1_name() {
                  return slidebar1_name;
         }

         public int getSlidebar1increment() {
                  return slidebar1increment;
         }

         public String getSlidebar2_name() {
                  return slidebar2_name;
         }

         public int getSlidebar2increment() {
                  return slidebar2increment;
         }

         
         public Image generatePreview(Color color1, Color color2, double colorIntensityParam, double param1, double param2) {
                  BufferedImage previewImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
                  Graphics2D g2d = previewImage.createGraphics();
                  g2d.fillRect(0, 0, 100, 100);
                  g2d.dispose();

                  BufferedImage coloredImage = generateColoredImage(previewImage, color1, color2, colorIntensityParam, param1, param2);

                  return SwingFXUtils.toFXImage(coloredImage, null);
         }

         /**
          * return the value of the blend coefficent and the color order if
          * necessary
          *
          * @return
          */
         double convertBlendCoeff(double colorIntensityParam) {
                  float invMaxBlend = 1 / maxBlend;

                  if (colorIntensityParam < 0.5) {
                           return ((1 - invMaxBlend) * 2 * colorIntensityParam + invMaxBlend);
                  } else {
                           return ((maxBlend - 1) * 2 * colorIntensityParam + 2 - maxBlend);
                  }

         }

         public static BufferedImage generateImage(float[][] table, Color color1, Color color2) {
                  int width = table.length;
                  int height = table[0].length;
                  BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                  for (int i = 0; i < width; i++) {
                           for (int j = 0; j < height; j++) {
                                    float value = table[i][j];
                                    int red = (int) (color1.getRed() * value + color2.getRed() * (1 - value));
                                    int green = (int) (color1.getGreen() * value + color2.getGreen() * (1 - value));
                                    int blue = (int) (color1.getBlue() * value + color2.getBlue() * (1 - value));
                                    int alpha = (int) (color1.getAlpha() * value + color2.getAlpha() * (1 - value));
                                    int rgba = (alpha << 24) | (red << 16) | (green << 8) | blue;
                                    image.setRGB(i, j, rgba);
                           }
                  }

                  return image;
         }

         public abstract BufferedImage generateColoredImage(BufferedImage image_in, Color color1, Color color2, double colorIntensityParam, double param1, double param2);

//         public abstract GradientCreator();
}
