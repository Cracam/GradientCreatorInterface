package GradientCreatorInterface;

import java.awt.Color;

public class GradientCreatorHypnotic extends GradientCreator {

        public GradientCreatorHypnotic() {
                super("Hypnose", true, "Nombre de cycles", "", 2, 30, 1);
        }

        @Override
        public float[][] generateBlendTable(int x_dim, int y_dim, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {

                colorIntensityParam = convertBlendCoeff(colorIntensityParam);

                float[][] blendTable = new float[x_dim][y_dim];

                int num_cycles = (int) param1;

                int center_x = (int) (x_dim / 2);
                int center_y = (int) (y_dim / 2);

                //     double branch_angle = Math.PI / num_branches;
                // Loop through each pixel of the input image
            

                for (int y = 0; y < y_dim; y++) {
                        for (int x = 0; x < x_dim; x++) {

                                // Calculate distance_to_center
                                double distance_to_center = Math.sqrt(Math.pow((x - center_x), 2) + Math.pow((y - center_y), 2));
                                double max_distance = Math.sqrt(Math.pow(center_x, 2) + Math.pow(center_y, 2));
                                
                                
                                // Calculate cycle_index
                                int cycle_index =(int) ( distance_to_center/max_distance * num_cycles);


                                if (cycle_index == 0) {
                                        blendTable[x][y] = 0;
                                } else {
                                        blendTable[x][y] = (float) Math.pow(((float )cycle_index )/((float) num_cycles-1), colorIntensityParam);
                                }
                                //System.out.println(cycle_index);
                        }
                }
                return blendTable;

        }

        public static double pythonModulo(double dividend, double divisor) {

                int remainder = (int) (dividend) % (int) (divisor);
                if (remainder < 0) {
                        remainder += Math.abs(divisor);
                }
                return remainder;
        }
}
//blend x2 if >1 *15 else nothing
