import AirbnbInformation.AirbnbListing;
import AirbnbInformation.SavedInputs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URI;

/**
 *   Class for panel that displays a specific property in a new window, with its specific details and a google map web view of it.
 */
public class PropertyViewer {
    // The button that exits you from the window.
    @FXML
    private Button exitBtn;
    // Label that displays property name.
    @FXML
    private Label propertyNameLbl;
    // Label that displays property host name.
    @FXML
    private Label propertyHostNameLbl;
    // Label that displays property neighbourhood.
    @FXML
    private Label neighbourhoodLbl;
    // Label that displays property latitude.
    @FXML
    private Label latitudeLbl;
    // Label that displays property longitude.
    @FXML
    private Label longitudeLbl;
    // Label that displays property room type.
    @FXML
    private Label roomTypeLbl;
    // Label that displays property price.
    @FXML
    private Label priceLbl;
    // Label that displays property minimum nights.
    @FXML
    private Label minimumNightLbl;
    // Label that displays property number of reviews.
    @FXML
    private Label reviewsLbl;
    // Button that will place property in favourite list.
    @FXML
    private Button addToFavouriteBtn;
    // Button that will removeFromFavouriteBtn property from favourite list.
    @FXML
    private Button removeFromFavouriteBtn;
    // button that allows the user to see the property on the browser
    @FXML
    private Button googleMapsBtn;
    // View that allows user to view property in addToFavouriteBtn map web browser.
    @FXML
    private WebView webView;
    // Engine that loads the addToFavouriteBtn map web browser in after inputting url of property location in addToFavouriteBtn map.
    @FXML
    private WebEngine engine;
    // The actual property.
    private AirbnbListing property;

    /**
     *  Initializes  window by creating web viewer.
     */
    public void initialize() {
        engine = webView.getEngine();
    }

    /**
     *  Sets specific property to display, displaying it's details in a specific order.
     */
    @FXML
    public void setProperty(AirbnbListing newProperty){
        property = newProperty;
        propertyNameLbl.setText(propertyNameLbl.getText() + System.lineSeparator() + property.getName());
        propertyHostNameLbl.setText(propertyHostNameLbl.getText() + " " + property.getHost_name());
        neighbourhoodLbl.setText(neighbourhoodLbl.getText() + " " + property.getNeighbourhood());
        latitudeLbl.setText(latitudeLbl.getText() + " " + property.getLatitude());
        longitudeLbl.setText(longitudeLbl.getText() + " " + property.getLongitude());
        roomTypeLbl.setText(roomTypeLbl.getText() + " " + property.getRoom_type());
        priceLbl.setText(priceLbl.getText() + " " + property.getPrice());
        minimumNightLbl.setText(minimumNightLbl.getText() + " " + property.getMinimumNights());
        reviewsLbl.setText(reviewsLbl.getText() + " " + property.getNumberOfReviews());

        addToFavouriteBtn.setDisable(property.getIsFavourite());
        removeFromFavouriteBtn.setDisable(!property.getIsFavourite());

        double latitude = property.getLatitude();

        double longitude = property.getLongitude();
        engine.load("https://www.google.com/maps/place/" + latitude + "," + longitude);
    }

    /**
     *  Adds property to user's favourite list after clicking 'add to favourite' button.
     */
    @FXML
    public void addToFavourites() throws Exception{
        SavedInputs.addProperty(property);
        property.setIsFavourite();
        removeFromFavouriteBtn.setDisable(!property.getIsFavourite());
        addToFavouriteBtn.setDisable(property.getIsFavourite());

    }

    /**
     *  This code was reused from coursework 1 
     *
     *  This method allows the user to view the property online if they do 
     *  not prefer to see the map on the window.
     */
    @FXML
    public void viewMap() throws Exception
    {
        double latitude = property.getLatitude();
        double longitude = property.getLongitude();

        URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri);
    }

    /**
     *  Remove property to user's favourite list after clicking 'Remove from favourite's' button.
     */
    @FXML
    public void removeFromFavourites() throws Exception{
        SavedInputs.removeProperty(property);
        property.setIsFavourite();
        removeFromFavouriteBtn.setDisable(!property.getIsFavourite());
        addToFavouriteBtn.setDisable(property.getIsFavourite());

    }

    /**
     *  When the exit button is clicked it closes the displayed panel from user's view.
     */
    @FXML
    public void exitApp() throws Exception{
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

}
