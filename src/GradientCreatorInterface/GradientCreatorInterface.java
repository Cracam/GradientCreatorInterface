package GradientCreatorInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GradientCreatorInterface extends HBox {

        private static ImageView hideImageView;
        private ImageView showImageView;

        private static final boolean allowOpacity = false;

                private final BooleanProperty changed = new SimpleBooleanProperty(false);

        @FXML
        private Button ToogleButton;

        @FXML
        private HBox ToggleableHbox;

        @FXML
        private ComboBox<String> ListGradient;
        private Map<String, GradientCreator> gradientMap;

        @FXML
        private Slider SlideBarColorIntensity;

        @FXML
        private ColorPicker ColorPicker1;

        @FXML
        private ColorPicker ColorPicker2;

        @FXML
        private Label LabelParam1;

        @FXML
        private Slider SlideBarParam1;

        @FXML
        private Label LabelParam2;

        @FXML
        private Slider SlideBarParam2;

        @FXML
        private ImageView preview;

        @FXML
        private Button InvertColorsButton;
        private ImageView InvertColorsImage;

        @FXML
        private Button InvertIntensityButton;
        private ImageView InvertIntensityImage;

        
        

        public GradientCreatorInterface() {
                try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GradientPicker.fxml"));
                        System.out.println(fxmlLoader.getLocation());
                        if (fxmlLoader == null) {
                                throw new ResourcesFileErrorException();
                        }
                        fxmlLoader.setRoot(this);
                        fxmlLoader.setController(this);

                        fxmlLoader.load();

                        hideImageView = new ImageView(new Image(getClass().getResource("/hide.png").toExternalForm()));
                        hideImageView.setFitHeight(20.0);
                        hideImageView.setFitWidth(20.0);
                        ToogleButton.setGraphic(hideImageView);

                        InvertColorsImage = new ImageView(new Image(getClass().getResource("/InvertColor.png").toExternalForm()));
                        InvertColorsImage.setFitHeight(35.0);
                        InvertColorsImage.setFitWidth(35.0);
                        InvertColorsButton.setGraphic(InvertColorsImage);

                        InvertIntensityImage = new ImageView(new Image(getClass().getResource("/InvertIntensity.png").toExternalForm()));
                        InvertIntensityImage.setFitHeight(35.0);
                        InvertIntensityImage.setFitWidth(35.0);
                        InvertIntensityButton.setGraphic(InvertIntensityImage);

                        showImageView = new ImageView();

                        // Create a dictionary of the GradientCreator class
                        gradientMap = new HashMap<>();
                        Set<Class<? extends GradientCreator>> gradientClasses = Stream.of(GradientMonocolor.class, GradientCreatorLeftRight.class, GradientCreatorUpDown.class, GradientCreatorCenter.class,GradientCreatorDiagonalLeft.class,GradientCreatorDiagonalRight.class,GradientCreatorDimamond.class,GradientCreatorStar.class,GradientCreatorHypnotic.class/* add more gradient classes here */)
                                .collect(Collectors.toSet());
                        for (Class<? extends GradientCreator> gradientClass : gradientClasses) {
                                GradientCreator gradient = gradientClass.getDeclaredConstructor().newInstance();
                                gradientMap.put(gradient.getName(), gradient);
                        }

                        // Fill the ListGradient ComboBox
                        ListGradient.getItems().addAll(gradientMap.keySet());

                        SlideBarColorIntensity.setMin(0.001);
                        SlideBarColorIntensity.setMax(0.999);
                        SlideBarColorIntensity.setValue(0.5);
                        SlideBarColorIntensity.setBlockIncrement(0.001);

                        ListGradient.setValue(ListGradient.getItems().get(0));
                        UpdateCombobox();
                        
                        changed.set(false);//to reset the change after the Update Combobox 

                } catch (IOException | ResourcesFileErrorException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(GradientCreatorInterface.class.getName()).log(Level.SEVERE, null, ex);
                }

        }

        @FXML
        private void invertColors() {

                Color coltemp = getColor1();
                ColorPicker1.setValue(convertAwtColorToJavafxColor(getColor2()));
                ColorPicker2.setValue(convertAwtColorToJavafxColor(coltemp));
                UpdateGradient();
        }

        @FXML
        private void invertIntensity() {
                SlideBarColorIntensity.setValue(SlideBarColorIntensity.getMin() + SlideBarColorIntensity.getMax() - SlideBarColorIntensity.getValue());
                UpdateGradient();
        }

        @FXML
        private void ToggleHideShow() {

                if (ToggleableHbox.isVisible()) {
                        ToggleableHbox.setVisible(false);
                        ToggleableHbox.setPrefSize(0, 0);

                        // set preview image to our button
                        showImageView.setImage(preview.getImage());
                        showImageView.setFitWidth(60);
                        showImageView.setFitHeight(60);

                        ToogleButton.setGraphic(showImageView);
                } else {
                        ToggleableHbox.setVisible(true);
                        ToggleableHbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                        ToogleButton.setGraphic(hideImageView);
                }
        }

        private GradientCreator getGradient() {
                return gradientMap.get(getComboboxValue());
        }

        @FXML
        private void UpdateGradient() {

                Color color1 = getColor1();
                Color color2 = getColor2();
                double colorIntensity = getColorIntensity();
                double param1 = getParam1();
                double param2 = getParam2();

                Image previewImage = getGradient().generatePreview(color1, color2, colorIntensity, param1, param2);
                preview.setImage(previewImage);
                
                changed.set(true);
        }

        @FXML
        private void UpdateBar1() {
                // TODO: Implement the parameter 1 update logic
                GradientCreator selectedGradient = getGradient();

                if (selectedGradient.isSlideBar1Used()) {
                        LabelParam1.setText(selectedGradient.getSlidebar1_name() + " " + (int) SlideBarParam1.getValue() + "  (" + selectedGradient.getSlidebar1_unit() + ")");

                } else {
                        LabelParam1.setText("");
                }
//        
//        
//        
                this.UpdateGradient();
        }

        @FXML
        private void UpdateBar2() {
                GradientCreator selectedGradient = getGradient();

                if (selectedGradient.isSlideBar2Used()) {
                        LabelParam2.setText(selectedGradient.getSlidebar2_name() + " " + (int) SlideBarParam2.getValue() + "  (" + selectedGradient.getSlidebar2_unit() + ")");
                } else {
                        LabelParam2.setText("");
                }

                this.UpdateGradient();
        }

        @FXML
        private void updateColor1() {
                if (!allowOpacity) {
                        ColorPicker1.setValue(convertAwtColorToJavafxColor(setFullOpacity(getColor1())));
                }
                this.UpdateGradient();
        }

        @FXML
        private void updateColor2() {
                if (!allowOpacity) {
                        ColorPicker2.setValue(convertAwtColorToJavafxColor(setFullOpacity(getColor2())));
                }
                this.UpdateGradient();
        }

        public static Color setFullOpacity(Color color) {
                return new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
        }

        @FXML
        private void UpdateCombobox() {
                String selectedGradientName = getComboboxValue();
                GradientCreator selectedGradient = gradientMap.get(selectedGradientName);

                if (selectedGradient.isSlideBar1Used()) {
                        LabelParam1.setText(selectedGradient.getSlidebar1_name() + " (" + selectedGradient.getSlidebar1_unit() + ")");
                        SlideBarParam1.setMin(selectedGradient.getSlidebar1_min());
                        SlideBarParam1.setMax(selectedGradient.getSlidebar1_max());
                        SlideBarParam1.setBlockIncrement(selectedGradient.getSlidebar1increment());
                        SlideBarParam1.setDisable(false);
                } else {
                        LabelParam1.setText("");
                        SlideBarParam1.setDisable(true);
                }

                if (selectedGradient.isSlideBar2Used()) {
                        LabelParam2.setText(selectedGradient.getSlidebar2_name() + " (" + selectedGradient.getSlidebar2_unit() + ")");
                        SlideBarParam2.setMin(selectedGradient.getSlidebar2_min());
                        SlideBarParam2.setMax(selectedGradient.getSlidebar2_max());
                        SlideBarParam2.setBlockIncrement(selectedGradient.getSlidebar2increment());
                        SlideBarParam2.setDisable(false);
                } else {
                        LabelParam2.setText("");
                        SlideBarParam2.setDisable(true);
                }

                if (selectedGradient.isUse2color()) {
                        ColorPicker2.setDisable(false);
                        SlideBarColorIntensity.setDisable(false);
                        InvertIntensityButton.setDisable(false);
                } else {
                        ColorPicker2.setDisable(true);
                        SlideBarColorIntensity.setDisable(true);
                        InvertIntensityButton.setDisable(true);
                }

                UpdateBar1();
                UpdateBar2();
        }

        public double getColorIntensity() {
                return SlideBarColorIntensity.getValue();
        }

        public double getParam1() {
                return SlideBarParam1.getValue();
        }

        public double getParam2() {
                return SlideBarParam2.getValue();
        }

        private String getComboboxValue() {
                return ListGradient.getValue();
        }

        //----- Color management 
        public Color getColor1() {
                return convertJavafxColorToAwtColor(ColorPicker1.getValue());
        }

        public Color getColor2() {
                return convertJavafxColorToAwtColor(ColorPicker2.getValue());

        }

        public static java.awt.Color convertJavafxColorToAwtColor(javafx.scene.paint.Color javafxColor) {
                java.awt.Color awtColor = new java.awt.Color(
                        (float) javafxColor.getRed(),
                        (float) javafxColor.getGreen(),
                        (float) javafxColor.getBlue(),
                        (float) javafxColor.getOpacity()
                );
                return awtColor;
        }

        private javafx.scene.paint.Color convertAwtColorToJavafxColor(java.awt.Color awtColor) {
                return javafx.scene.paint.Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), awtColor.getAlpha() / 255.0);
        }
//-----------------------------------------------------------------------------------------------------------------------------    

        public BooleanProperty  isChanged() {
                return changed;
        }

        public void setChanged(boolean value) {
                        this.changed.set(value);
                       // System.out.println("Setting changed property to: " + value);
        }
        
        /**
         * This method return the recolored Image using a mask for opacity management and size of the ImageOut
         * @param opacityTable the mask
         * @return 
         */
        public BufferedImage getImageOut(int[][] opacityTable){
                Color color1 = getColor1();
                Color color2 = getColor2();
                double colorIntensity = getColorIntensity();
                double param1 = getParam1();
                double param2 = getParam2();

                return this.getGradient().generateColoredImage(opacityTable, color1,color2, colorIntensity, param1, param2);
        }

        
        /**
         * 
         * @return 
         */
        public String getSelectedGradientName(){
                return this.getGradient().getName();
        }
        
        
        /**
         * This function set the gradient combobox value into it's name
         * @param gradientName 
         */
        public void setSelectedGradientByName(String gradientName) {
                try {
                        if (!ListGradient.getItems().contains(gradientName)) {
                                throw new ThisGradientDoesntExist("the gradient you want to open doesn't exist in this version " + gradientName);
                        }

                        ListGradient.setValue(gradientName);
                         this.UpdateGradient();
                         
                } catch (ThisGradientDoesntExist ex) {
                        Logger.getLogger(GradientCreatorInterface.class.getName()).log(Level.SEVERE, null, ex);
                }

        }
        
                /**
         * This program will allow the user to load the data saved before;
         *
         * @param gradientName - name of the gradient if the name dont exist set
         * it to one that exste (left_right)
         * @param color1
         * @param color2
         * @param param1
         * @param colorIntensity
         * @param param2
         */
        public void setInterfaceState(String gradientName, Color color1, Color color2, double colorIntensity, double param1, double param2) {
                setSelectedGradientByName(gradientName);
                ColorPicker1.setValue(convertAwtColorToJavafxColor(color1));
                ColorPicker2.setValue(convertAwtColorToJavafxColor(color2));
                 SlideBarColorIntensity.setValue(colorIntensity);
                SlideBarParam1.setValue(param1);
                SlideBarParam2.setValue(param2);
        }


}
