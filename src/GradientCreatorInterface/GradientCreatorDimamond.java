package GradientCreatorInterface;

import java.awt.Color;

public class GradientCreatorDimamond extends GradientCreator {

        public GradientCreatorDimamond() {
                super("Diamand", true);
        }

        @Override
        public float[][] generateBlendTable(int x_dim, int y_dim, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {

                colorIntensityParam = convertBlendCoeff(colorIntensityParam);

                float[][] blendTable = new float[x_dim][y_dim];

                int center_x = (int) x_dim / 2;
                int center_y = (int) y_dim / 2;

                int max_distance = center_x + center_y;
                float distance_to_center;

                // Loop through each pixel of the input image
                for (int y = 0; y < y_dim; y++) {
                        for (int x = 0; x < x_dim; x++) {
                                distance_to_center = Math.abs(x - center_x) + Math.abs(y - center_y);

                                blendTable[x][y] = (float) Math.pow(distance_to_center / max_distance, colorIntensityParam);

                        }
                }
                return blendTable;

        }
}
//blend x2 if >1 *15 else nothing
