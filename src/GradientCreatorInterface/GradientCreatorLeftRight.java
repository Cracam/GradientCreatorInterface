package GradientCreatorInterface;

import java.awt.Color;

public class GradientCreatorLeftRight extends GradientCreator {

        public GradientCreatorLeftRight() {
                super("Gauche-Droite", true);
        }

        @Override
        public float[][] generateBlendTable(int x_dim, int y_dim, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {

                colorIntensityParam = convertBlendCoeff(colorIntensityParam);

                float[][] blendTable = new float[x_dim][y_dim];

                // Loop through each pixel of the input image
                for (int x = 0; x < x_dim; x++) {
                        float alphaBlend = (float) Math.pow((float) x / (x_dim - 1), colorIntensityParam);
                        for (int y = 0; y < y_dim; y++) {
                                blendTable[x][y] = alphaBlend;
                        }
                }
                return blendTable;

        }
}
//blend x2 if >1 *15 else nothing
