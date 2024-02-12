//save inputs done by the user.
import AirbnbInformation.SavedInputs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
/**
 *  This class is the Home page of the application. This is what the user will
 *  first see. This class will contain 2 choice boxes to choose the min 
 *  and max value for the filters. Also there is a button to be used however
 *  it is initially disabled until both choice boxes have a value.
 *
 */
public class WelcomePage {
    // The button to go to the map page.
    @FXML private Button goBtn;
    // The predefined list for the minimum prices.
    @FXML private ChoiceBox<String> minBox;
    // The predefined list for the maximum prices.
    @FXML private ChoiceBox<String> maxBox;

    // An observable list that stores the price values for min and max. If there are changes, it is automatically updated.
    private ObservableList<String> minList = FXCollections.observableArrayList("0", "25","50","75","100","125","150","175","200","300","400","500","600","700");
    private ObservableList<String> maxList = FXCollections.observableArrayList( "25","50","75","100","125","150","175","200","300","400","500","600","700","800","7000");

    /**
     * Sets the button disabled initially.
     * The user must have selected a min
     * price and max price to enable the button.
     */
    public void initialize(){
        // Disable the button 
        goBtn.setDisable(true);
        minBox.setItems(minList);
        maxBox.setItems(maxList);
    }

    /**
     * Sets the button enabled provided there is a value selected in the min box and
     * the max box.
     */
    public void isEmpty(){
        // check if values of the choice boxes are not null in order to continue
        boolean isDisabled = (minBox.getValue() != null) && (maxBox.getValue() != null);
        goBtn.setDisable(!isDisabled);

    }

    /**
     * Filters the airbnb listing depending on the chosen input provided the minimum value selected is less than or equal to the max value.
     * Changes the panel to the MapPage.
     */
    public void goMapPage() throws Exception{
        // retrieve the value of both choice boxes
        int minValue = Integer.parseInt(minBox.getValue());
        int maxValue = Integer.parseInt(maxBox.getValue());
        // compare the values to make sure the min is lower than the max
        if (minValue <= maxValue){
            // store the input in another class to be used for filtering the list
            SavedInputs.setMinPrice(minValue);
            SavedInputs.setMaxPrice(maxValue);
            // load the next panel
            Parent root = FXMLLoader.load(getClass().getResource("MapPage.fxml"));
            Stage window = (Stage) goBtn.getScene().getWindow();
            window.setTitle("Map Page");
            window.setScene(new Scene(root));
        }

        else {
            // show an error to indicate that the min value is not lower than the max
            priceAlertMsg();
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
        alert.setHeaderText("To start the application please select a minimum and maximum price to continue");
        alert.showAndWait();
    }

    /**
     * The alert message that is displayed when the min price
     * selected is lower than the max price.
     */
    public void priceAlertMsg()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect filters");
        alert.setContentText("Please adjust to have the min filter lower than the max");
        alert.showAndWait();

    }
}

