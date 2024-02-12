import AirbnbInformation.AirbnbListing;
import AirbnbInformation.SavedInputs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *  This class is the TableList class. This shows a list of all the 
 *  properties within a borough. This class retrieves a list that is
 *  filtered the airbnb data for a specific borough as well as 
 *  a range of values for the minimum and maximum price
 *
 *
 */
public class TableList {
    // Object of the filter table class to retrieve a list of a properties based on inputs
    private FilterTable filterLoad = new FilterTable();
    // ArrayList for the airbnb data
    private ArrayList<AirbnbListing> propertyArrayList;
    // Observable list to track changes to the list as well as be shown on the table
    private ObservableList<AirbnbListing> propertyList;
    // Observable list of filters to be used for the list of properties
    private ObservableList<String> filterList = FXCollections.observableArrayList("Name: A-Z","Name: Z-A",
            "Host Name: A-Z", "Host Name: Z-A", "Price: Low to High", "Price: High to Low", "No of Reviews: Low to High", "No of Reviews: High to Low","No Of Nights: High to Low",
            "No of Nights: Low to High");

    // The table to hold the data
    @FXML private TableView<AirbnbListing> table;
    // Button to go back to the previous page
    @FXML private Button backBtn;
    // choice box to hold the list of filters for the user to select from
    @FXML private ChoiceBox choice;

    /**
     * This method initialises the table view and makes sure that 
     * some features are disabled. For example reordering.
     * Also the columns will be made and data will be loaded into
     * the columns by using the ArrayList and the ObservableList.
     */
    public void initialize(){
        // Columns on the table
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("name"));
        
        TableColumn hostName = new TableColumn("Host Name");
        hostName.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("host_name"));
        
        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("price"));
        
        TableColumn noOfReviews = new TableColumn("No of Reviews");
        noOfReviews.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("numberOfReviews"));
        
        TableColumn noOfNights = new TableColumn("Minimum Nights");
        noOfNights.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("minimumNights"));
        
        // disable features of the table
        name.setSortable(false);
        hostName.setSortable(false);
        price.setSortable(false);
        noOfNights.setSortable(false);
        noOfReviews.setSortable(false);

        name.setReorderable(false);
        hostName.setReorderable(false);
        price.setReorderable(false);
        noOfNights.setReorderable(false);
        noOfReviews.setReorderable(false);


        // load the filters into the choice box
        choice.setItems(filterList);

        // set the columns on the table view
        table.getColumns().addAll(name, hostName, price, noOfReviews, noOfNights);

        // retrieve the filtered ArrayList for a specific borough
        propertyArrayList = filterLoad.getNeighbourhoodData(SavedInputs.getNeighbourhood() ,SavedInputs.getMinPrice(), SavedInputs.getMaxPrice());
        
        // store the filtered list into an observable array list to be used on the table
        propertyList = FXCollections.observableArrayList(propertyArrayList);
        
        table.setItems(propertyList);
    }

    /**
     * This method filters the data given already. For example the price from 
     * low to high
     * A temporary list is created that will be used to show the new filtered
     * data.
     * a SWITCH CASE statement is used to go through all the cases of the
     * choice box selections.
     */
    @FXML
    public void sortingAction(){
        // create a temporary list
        ObservableList<AirbnbListing> newList = FXCollections.observableArrayList(propertyList);
        // check if choice box is empty
        if(choice.getValue() != null){
            // switch case to determine which filter will be done.
            switch(choice.getValue().toString()){
                case "Name: A-Z": Collections.sort(newList, Comparator.comparing(AirbnbListing::getName));
                    break;
                case "Name: Z-A" :
                {
                    Collections.sort(newList, Comparator.comparing(AirbnbListing::getName));
                    // reverse the list so that it shows from Z-A
                    Collections.reverse(newList);
                }
                break;
                case "Price: Low to High": Collections.sort(newList, Comparator.comparing(AirbnbListing::getPrice));
                    break;
                case "Price: High to Low" :
                {
                    Collections.sort(newList, Comparator.comparing(AirbnbListing::getPrice));
                    // reverse the list so that it shows from High to Low
                    Collections.reverse(newList);
                }
                break;
                case "No of Nights: Low to High": Collections.sort(newList, Comparator.comparing(AirbnbListing::getMinimumNights));
                    break;
                case "No Of Nights: High to Low" :
                {
                    Collections.sort(newList, Comparator.comparing(AirbnbListing::getMinimumNights));
                    // reverse the list so that it shows from High to Low
                    Collections.reverse(newList);
                }
                break;
                case "Host Name: A-Z" : Collections.sort(newList, Comparator.comparing(AirbnbListing::getHost_name));
                    break;
                case "Host Name: Z-A" :
                {
                    Collections.sort(newList, Comparator.comparing(AirbnbListing::getHost_name));
                    // reverse the list so that it shows from Z-A
                    Collections.reverse(newList);
                }
                break;
                case "No of Reviews: Low to High" : Collections.sort(newList, Comparator.comparing(AirbnbListing::getNumberOfReviews));
                    break;
                case "No of Reviews: High to Low" :
                {
                    Collections.sort(newList,Comparator.comparing(AirbnbListing::getNumberOfReviews));
                    // reverse the list so that it shows from High to Low
                    Collections.reverse(newList);
                }
                break;
                // filter is invalid
                default: System.out.println("Invalid");
            }
        }
        table.setItems(newList);
    }


    /**
     * This closes the stage the user is currently on
     */
    @FXML
    public void pressBackButton(){
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();

    }

    /**
     * This method is called when a user selects a property from the table view
     * Once a property has been selected, a new window will appear to show the
     * user more information in detail. As well as a addToFavouriteBtn maps web view to
     * show the location of the property.
     *
     * @param MouseEvent to retrieve what was selected by the user.
     */
    @FXML
    public void nextPanel(MouseEvent event) throws Exception{
        // retrieve the airbnb property selected
        AirbnbListing property = table.getSelectionModel().getSelectedItem();
        // check if property is null
        if (property != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PropertyViewer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            // retrieve the controller of the next panel.
            PropertyViewer controller = fxmlLoader.getController();
            // call a method of the controller to load the property description
            controller.setProperty(property);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }
        else {
            // error message to indicate no property has been found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("This is not a property please select a property to view");
            alert.setContentText("This is content text.");
            alert.showAndWait();
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