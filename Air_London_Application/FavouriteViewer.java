import AirbnbInformation.AirbnbListing;
import AirbnbInformation.SavedInputs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Class that contains the code for the panel that shows the properties the user has added into his/her favorite list. It can display
 * these properties, allow you to select them for a more in-depth look and you can remove them from your list. 
 */
public class FavouriteViewer {
    // The actual table where all the properties will be displayed.
    @FXML
    private TableView<AirbnbListing> table;

    // Displayed button that takes you to statistics page.
    @FXML
    private Button statisticsBtn;

    // ArrayList that contains the properties the user has added into the favourite list.
    private ArrayList<AirbnbListing> propertyArrayList;

    // A list that can be used to display the favourite list.
    private ObservableList<AirbnbListing> propertyList;

    /**
     *  Initializes the favourite viewer panel to be displaye the favourite list in a table. First sets up
     *  the table and then adds any favorite properties chosen by the user into the list to be displayed.
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

        // disable the action to sort the columns
        name.setSortable(false);
        hostName.setSortable(false);
        price.setSortable(false);
        noOfNights.setSortable(false);
        noOfReviews.setSortable(false);

        // disable the action to reorder the columns
        name.setReorderable(false);
        hostName.setReorderable(false);
        price.setReorderable(false);
        noOfNights.setReorderable(false);
        noOfReviews.setReorderable(false);

        table.getColumns().addAll(name, hostName, price, noOfNights, noOfReviews);

        propertyArrayList = SavedInputs.getProperty();
        propertyList = FXCollections.observableArrayList(propertyArrayList);

        table.setItems(propertyList);
    }

    /**
     *  This method is for when you click a property in the favourite viewer. It will display that properties details and it will display the
     *  property in google map.
     */
    @FXML
    public void nextPanel(MouseEvent event) throws Exception{
        AirbnbListing property = table.getSelectionModel().getSelectedItem();
        if (property != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PropertyViewer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            PropertyViewer controller = fxmlLoader.getController();
            controller.setProperty(property);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("This is not a property please select a property to view");
            alert.setContentText("This is content text.");
            alert.showAndWait();
        }

    }

    /**
     * Refreshes the list when you press the 'refresh' button , usually done if you remove a property from the list.
     */
    @FXML
    public void refresh(){
        propertyArrayList = SavedInputs.getProperty();
        propertyList = FXCollections.observableArrayList(propertyArrayList);
        table.setItems(propertyList);
        table.refresh();
    }

    /**
     * Changes viewed panel to the statistics panel by clicking the 'Go Back' button.
     */
    @FXML
    public void goStatisticPage() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StatisticsPage.fxml"));
        Stage window = (Stage) statisticsBtn.getScene().getWindow();
        window.setTitle("Statistics");
        window.setScene(new Scene(root));
    }
}
