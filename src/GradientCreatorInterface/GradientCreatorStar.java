package GradientCreatorInterface;

import java.awt.Color;

public class GradientCreatorStar extends GradientCreator {

        public GradientCreatorStar() {
                super("Etoile", true,"Nombre de branches","",2,30,1,"Angle","°",0,360,1);
        }

        @Override
        public float[][] generateBlendTable(int x_dim, int y_dim, Color color1, Color color2, double colorIntensityParam, double param1, double param2) {

                colorIntensityParam = convertBlendCoeff(colorIntensityParam);

                float[][] blendTable = new float[x_dim][y_dim];

                int num_branches = (int) param1;
                //num_branches = (int) (num_branches / 2);
                int param_angle = (int) param2;

                int  center_x = (int) ( x_dim / 2);
                int center_y =(int) ( y_dim / 2);

           //     double branch_angle = Math.PI / num_branches;

                
                // Loop through each pixel of the input image
                for (int y = 0; y < y_dim; y++) {
                        for (int x = 0; x < x_dim; x++) {

                                // Calculate distance and angle
                                int dx = x - center_x;
                                int dy = y - center_y;
                                
                                double distance = (Math.pow(dx, 2) + Math.pow(dy, 2));
                                double angle = Math.atan2(dy, dx)  + param_angle * Math.PI/ 180;

                              // Calculate branch_distance
                               double  branch_angle = Math.PI* (1 - 2 / num_branches);
                                double branch_distance = distance * Math.sin(num_branches * angle + branch_angle *2 * Math.PI );
                                
                                
                                // Calculate alpha_blend
                                 blendTable[x][y] = (float) (Math.pow( (1- (branch_distance / distance))/2, colorIntensityParam));
                              
                                        }
                }
                return blendTable;

        }
        
        
        public static double pythonModulo(double dividend, double divisor) {
                
        int remainder = (int) (dividend) %  (int) (divisor);
        if (remainder < 0) {
            remainder += Math.abs(divisor);
        }
        return remainder;
    }
}
//blend x2 if >1 *15 else nothing
