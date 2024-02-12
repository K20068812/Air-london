// A package used to save inputs done by the user. 
import AirbnbInformation.SavedInputs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.List;

/**
 *  This class is the Map page of the application. This page will contain the map of 
 *  all the boroughs in london and will have a visual indication to show the levels
 *  of properties. On the panel there is a key to understand the visual indication.
 */
public class MapPage {
    // button to go back to home page
    @FXML private Button backBtn;
    // button to go to the statistics page
    @FXML private Button nextBtn;
    // label to indicate what price range the user selected
    @FXML private Label price;
    // Object of the filter table class to retrieve a list of a properties based on inputs
    private FilterTable filterLoad = new FilterTable();
    // Labels for the user to click on to view the lists of properties in that area.
    @FXML Label barkingAndDagenham, barnet, bexley, brent, bromley, camden, city, croydon, ealing, enfield, greenwich, hackney, hammersmithAndFullham,
            haringey, harrow, havering, hillingdon, hounslow, islington, kensingtonAndChelsea, kingstonUponThames, lambeth, lewisham, merton, newham,
            redbridge, richmondUponThames, southwark, sutton, towerHamlets, walthamForest, wandsworth, westminster;
    // list of all the labels to be used for filtering the airbnb file.
    private List<Label> labels;

    /**
     * This method initialises the label which shows the price range 
     * Also this method calls another method for the visual indication of levels of 
     * properties.
     */
    public void initialize(){
        //add the labels into the array
        labels = Arrays.asList(barkingAndDagenham, barnet, bexley, brent,bromley, camden, city, croydon, ealing, enfield, greenwich, hackney, hammersmithAndFullham,
                haringey, harrow, havering, hillingdon, hounslow, islington, kensingtonAndChelsea, kingstonUponThames, lambeth, lewisham, merton, newham,
                redbridge, richmondUponThames, southwark, sutton, towerHamlets, walthamForest, wandsworth, westminster);
        // change the text so that the label shows the price range of the properties
        price.setText(("price range is from " + SavedInputs.getMinPrice() + " to " + SavedInputs.getMaxPrice()).toUpperCase());
        // visual indication method to show the level of properties.
        visualIndication();
    }

    /**
     * Method to return back to the Home Page 
     */
    public void pressBackButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
        Stage window = (Stage) backBtn.getScene().getWindow();
        window.setTitle("Home Page");
        window.setScene(new Scene(root));
    }

    /**
     * This method decides how the visuals will be done
     * RED indicates a borough with low number of properties
     * YELLOW indicates a borough with medium number of properties
     * GREEN indicates a borough with high number of properties.
     * I used upper and lower quartile to find numbers that can be used as limits
     */
    public void visualIndication(){
        // lower quartile of the size of the filtered list
        int minSize = (filterLoad.getDataMapList(SavedInputs.getMinPrice(),SavedInputs.getMaxPrice()).size()/32)/4;
        // upper quartile of the size of the filtered list
        int maxSize = ((filterLoad.getDataMapList(SavedInputs.getMinPrice(),SavedInputs.getMaxPrice()).size()/32)*3)/4;
        // FOR loop to iterate through all the borough labels
        for (Label lbl : labels){
            // if the number of properties in that borough is lower than the lower quartile then it is RED
            if (filterLoad.getNeighbourhoodData(getName(lbl),SavedInputs.getMinPrice(),SavedInputs.getMaxPrice()).size()<= minSize){
                lbl.setStyle("-fx-text-fill: #880202;");
            }
            // if the number of properties in that borough is higher than the lower quartile but lower than the upper quartile then it is YELLOW
            else if (filterLoad.getNeighbourhoodData(getName(lbl),SavedInputs.getMinPrice(),SavedInputs.getMaxPrice()).size()>= maxSize){
                lbl.setStyle("-fx-text-fill: #008000;");
            }
            // if the number of properties in that borough is higher than the upper quartile then it is GREEN
            else if ((filterLoad.getNeighbourhoodData(getName(lbl),SavedInputs.getMinPrice(),SavedInputs.getMaxPrice()).size()> minSize) &&(filterLoad.getNeighbourhoodData(getName(lbl),SavedInputs.getMinPrice(),SavedInputs.getMaxPrice()).size()< maxSize)){
                lbl.setStyle("-fx-text-fill: #c3a900;");
            }
        }
    }

    /**
     * This method will open a new window for the list of properties in a borough 
     * selected
     * Another method will be called to return a boroughs full name to be used for 
     * filtering the airbnb list.
     * once the list is found and is filtered then a new window will show a list of
     * all the boroughs
     *
     * @param MouseEvent to retrieve a label which the user selected
     */
    public void showProperties(MouseEvent event)  {
        try {
            // retrieve the label the user selected
            Label label = (Label) event.getSource();
            // retrieve the full name of the neighbourhood
            String name = getName(label);
            // save the neighbourhood name to be used to filter the list in the next window 
            SavedInputs.setNeighbourhood(name);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TablePage.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(getName(label));
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("can't load");
        }
    }

    /**
     * Press the button to go to the statistics panel which is the next panel.
     */
    public void statisticsPanel() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StatisticsPage.fxml"));
        Stage window = (Stage) nextBtn.getScene().getWindow();
        window.setTitle("Statistics Page");
        window.setScene(new Scene(root));
    }

    /**
     * This method takes in a label and returns the full name of a borough based on 
     * the text of the label 
     * for example : "HRRW" is equivalent to "Harrow"
     * Using the switch case is much more simple than multiple IF statements
     *
     * @param Label the label which the user clicked on
     * @return String to return the full borough name for filtering the airbnb list.
     */
    private String getName(Label lbl){
        switch (lbl.getText()) {
            case "BARK": return "Barking and Dagenham";
            case "BARN": return "Barnet";
            case "BEXL": return"Bexley";
            case "BREN": return "Brent";
            case "BROM": return "Bromley";
            case "CAMD": return "Camden";
            case "CITY": return "City of London";
            case "CROY" : return "Croydon";
            case "EALI" : return "Ealing";
            case "ENFI" : return "Enfield";
            case "GWCH" : return "Greenwich";
            case "HACK" : return "Hackney";
            case "HAMM" : return "Hammersmith and Fulham";
            case "HRGY" : return "Haringey";
            case "HRRW" : return "Harrow";
            case "HAVE" : return "Havering";
            case "HILL" : return "Hillingdon";
            case "HOUN" : return "Hounslow";
            case "ISLI" : return "Islington";
            case "KENS" : return "Kensington and Chelsea";
            case "KING" : return "Kingston upon Thames";
            case "LAMB" : return "Lambeth";
            case "LEWS" : return "Lewisham";
            case "MERT" : return "Merton";
            case "NEWH" : return "Newham";
            case "REDB" : return "Redbridge";
            case "RICH" : return "Richmond upon Thames";
            case "STHW" : return "Southwark";
            case "SUTT" : return "Sutton";
            case "TOWH" : return "Tower Hamlets";
            case "WALT" : return "Waltham Forest";
            case "WAND" : return "Wandsworth";
            case "WSTM" : return "Westminster";
            default : return "";
        }
    }

    /**
     * The alert message that is displayed when the
     * Help button on the menu bar is clicked.
     */
    @FXML
    public void alertMsg()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("To view a list of properties please select a borough by clicking on their name");
        alert.showAndWait();
    }

}

