package GradientCreatorInterface;

import java.awt.Color;

public class GradientCreatorStripes extends GradientCreator {

        public GradientCreatorStripes() {
                super("Rayures", true, "Nombre de rayures", "", 2, 30, 1, "Angle", "°", 0, 360, 1);
        }

        @Override
        public float[][] generateBlendTable(int x_dim, int y_dim, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {

                colorIntensityParam = convertBlendCoeff(colorIntensityParam);

                float[][] blendTable = new float[x_dim][y_dim];

                //     double branch_angle = Math.PI / num_branches;
                // Loop through each pixel of the input image
                int num_stripes = (int) param1;
                int angle = (int) param2;

                double stripe_width = Math.pow((Math.pow((double) x_dim, 2) + Math.pow((double) y_dim, 2)), 0.5) / num_stripes; // calculate the width of each stripe based on the number of stripes   
                double rad = angle * Math.PI / 180.0;// convert angle to radians

                double distance_from_center;
                double dx;
                double dy;
                double distance;
                double stripe_center;
                
                
                for (int y = 0; y < y_dim; y++) {
                        for (int x = 0; x < x_dim; x++) {

                                // Calculate distance
                                dx = x - x_dim / 2;
                                dy = y - y_dim / 2;
                                distance =Math.abs( (dx * Math.cos(rad) + dy * Math.sin(rad)) / stripe_width);

                                // Calculate stripe_center and distance_from_center
                                stripe_center = distance % 1;

                                if (stripe_center > 1 - stripe_center) {
                                        distance_from_center = 1 - stripe_center;
                                } else {
                                        distance_from_center = stripe_center;
                                }

                                // Calculate alpha_blend
                                blendTable[x][y] = (float) Math.pow(Math.pow(1 - distance_from_center, 2), colorIntensityParam * Math.pow(num_stripes,0.45));

                                //System.out.println(cycle_index);
                        }
                }
                return blendTable;
        }

}
//blend x2 if >1 *15 else nothing
