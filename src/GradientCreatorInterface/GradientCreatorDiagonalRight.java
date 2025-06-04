package GradientCreatorInterface;

import java.awt.Color;

public class GradientCreatorDiagonalRight extends GradientCreator {

        public GradientCreatorDiagonalRight() {
                super("Diagonal-Droite", true);
        }

        @Override
        public float[][] generateBlendTable(int x_dim, int y_dim, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {

                colorIntensityParam = convertBlendCoeff(colorIntensityParam);

                float[][] blendTable = new float[x_dim][y_dim];
                
                
                 
                // Loop through each pixel of the input image
                for (int y = 0; y < y_dim; y++) {
                        for (int x = 0; x < x_dim; x++) {
                                blendTable[x][y] = (float) Math.pow((float)(x_dim -x+y)/(x_dim+y_dim-2),colorIntensityParam);
                               
                                        }
                }
                return blendTable;

        }
}
//blend x2 if >1 *15 else nothing
