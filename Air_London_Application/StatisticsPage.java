import AirbnbInformation.AirbnbListing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import AirbnbInformation.SavedInputs;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *  This class is the StatisticsPage class. This class will have 4 sections so multiple statistics
 *  an ArrayList of statistics will be made as well as an ArrayList of Boolean values to match with
 *  a statistic. 
 */
public class StatisticsPage {
    // total price of a borough
    private double totalPrice;
    // most expensive borough
    private double highPrice;
    // Name of the expensive borough
    private String highBorough;
    // Object of the filter table class to retrieve a list of a properties based on inputs
    private FilterTable filterLoad = new FilterTable();
    // ArrayList of all the statistics
    private ArrayList<String> statistic = new ArrayList<>();
    // ArrayList of Boolean values that match with a statistic
    private ArrayList<Boolean> isShown = new ArrayList<>();
    // ArrayList of names of the boroughs to be used to find the most expensive borough
    private ArrayList<String> names = new ArrayList<>(Arrays.asList("BARK", "BARN", "BEXL", "BREN", "BROM", "CAMD", "CITY", "CROY", "EALI", "GWCH", "HACK", "HAMM", "HRRW", "HRGY", "HAVE", "HILL", "HOUN", "ISLI", "KENS", "KING", "LAMB", "LEWS", "MERT", "NEWH", "REDB", "RICH", "STHW", "SUTT", "TOWH", "WALT", "WSTM", "WAND"));

    // Label of section 1
    @FXML private Label lbl1;
    // Label of section 2
    @FXML private Label lbl2;
    // Label of section 3
    @FXML private Label lbl3;
    // Label of section 4
    @FXML private Label lbl4;
    // Button to go back to the Map Page
    @FXML private Button backBtn;
    // Button to go back to the Favourites page
    @FXML private Button nextBtn;

    /**
     * Initialises the panel, adding statistics to the arraylist to be displayed.
     */
    public void initialize() {
        statistic.add("Welcome to statistics");
        statistic.add("The number of entire homes and apartments:" +"\n" + getHomeApt());
        statistic.add("The most expensive neighbourhood: "+"\n" + getExpensiveBorough());
        statistic.add("Average number of reviews per property: "+ "\n" + getAverageNumOfReviewsPerProperty());
        statistic.add("Number of Properties available: " +"\n" + propertyListAvailable());
        statistic.add("Most Expensive Property: " + "\n" + getExpensiveProperty());
        statistic.add("Number of properties available annually: "+"\n" + yearlyAvailableProperty());
        statistic.add("Cheapest Property: " + "\n" + getCheapestProperty());
        statistic.add("Property with most reviews: " +"\n" +getPopularReview());

        lbl1.setText(statistic.get(0));
        lbl2.setText(statistic.get(1));
        lbl3.setText(statistic.get(2));
        lbl4.setText(statistic.get(3));

        isShown.add(true);
        isShown.add(true);
        isShown.add(true);
        isShown.add(true);
        isShown.add(false);
        isShown.add(false);
        isShown.add(false);
        isShown.add(false);
        isShown.add(false);

    }

    /**
     * Searches through filtered list of the properties to calculate
     * the total price by multiplying the price per night by the min. 
     * no of nights. Then compares the total price of each borough to
     * return the highest priced borough in London.
     * @return The most expensive borough and it's totalled price to 
     * be displayed.
     */
    @FXML
    public String getExpensiveBorough() {
        // FOR loop to iterate through the boroughs
        for (String lbl : names){
            // filter the properties of a specific borough
            ArrayList<AirbnbListing> BoroughList = filterLoad.getNeighbourhoodData(getName(lbl),SavedInputs.getMinPrice(),SavedInputs.getMaxPrice());
            // iterate through the properties to find the total price
            for (AirbnbListing lists: BoroughList){
                totalPrice += lists.getPrice()*lists.getMinimumNights();
            }
            // check if the total price of a borough is more expensive than the most expensive borough
            if(totalPrice > highPrice ){
                // set the new borough name
                highBorough = getName(lbl);
                // new expensive borough price
                highPrice = totalPrice;
            }
            //reset total price 
            totalPrice = 0;
        }
        return (highPrice + " " + highBorough);
    }

    /**
     * Searches through the filtered list to see whether a property 
     * is a home or apartment.
     * @return The count of the home/apartment properties to be 
     * displayed.
     */
    @FXML
    public int getHomeApt(){
        // counter for the amount of entire homes/apartments
        int tempcount = 0;
        // retrieve the list of all the properties within the min and max price
        ArrayList<AirbnbListing> homesList = filterLoad.getDataMapList(SavedInputs.getMinPrice(),SavedInputs.getMaxPrice());
        // iterate through the properties to find which ones contain the String "Entire home/apt"
        for (AirbnbListing lists: homesList){
            if(lists.getRoom_type().equals("Entire home/apt")){
                // property found therefore the counter increments
                tempcount++;
            }
        }
        return (tempcount);
    }

    /**
     * Searches through the filtered list to total the number
     * of reviews then divides by the number of properties to
     * calculate the average reviews per property.
     * @return String of the average number of reviews to be displayed.
     */
    @FXML
    public String  getAverageNumOfReviewsPerProperty() {
        // total number of reviews
        int totalReviews = 0;
        // total number of properties
        int numOfProperties = 0;
        // filtered list of properties within the min and max price range
        ArrayList<AirbnbListing> reviewsList = filterLoad.getDataMapList(SavedInputs.getMinPrice(),SavedInputs.getMaxPrice());
        // iterate through the properties and calculate the total number of properties and add to the total number of reviews
        for(AirbnbListing Property: reviewsList) {
            numOfProperties++;
            totalReviews += Property.getNumberOfReviews();
        }
        // calculate the average number of reviews
        int avgReviews = totalReviews / numOfProperties;
        return ("" + avgReviews);
    }

    /**
     * Uses the Stream API's max method to compare the
     * properties and return the highest priced property.
     *
     * From week 10 of the content streams are used for processing collections
     *
     * @return The most expensive property to be displayed.
     *
     */
    public String getExpensiveProperty() {
        // retrieve a list of the properties within a specific min and max price range
        ArrayList<AirbnbListing> expensiveList = filterLoad.getDataMapList(SavedInputs.getMinPrice(),SavedInputs.getMaxPrice());
        String propertyName = expensiveList.stream().max(Comparator.comparing(AirbnbListing::getPrice)).get().getName();
        int propertyValue = expensiveList.stream().max(Comparator.comparing(AirbnbListing::getPrice)).get().getPrice();
        return propertyName + " £" + propertyValue;
    }

    /**
     * Uses the Stream API's min method to compare the 
     * properties and return the lowest priced property.
     *
     * From week 10 of the content streams are used for processing collections
     *
     * @return The lowest priced property to be displayed.
     */
    public String getCheapestProperty() {
        // retrieve a list of the properties within a specific min and max price range
        ArrayList<AirbnbListing> cheapList = filterLoad.getDataMapList(SavedInputs.getMinPrice(),SavedInputs.getMaxPrice());
        String propertyName = cheapList.stream().min(Comparator.comparing(AirbnbListing::getPrice)).get().getName();
        double propertyValue = cheapList.stream().min(Comparator.comparing(AirbnbListing::getPrice)).get().getPrice();
        return propertyName + " £" + propertyValue;
    }

    /**
     * Uses the Stream API's min method to compare the 
     * properties and return the most reviewed property.
     *
     * From week 10 of the content streams are used for processing collections
     *
     * @return The property name with the number of reviews it has
     */
    public String getPopularReview() {
        // retrieve a list of the properties within a specific min and max price range
        ArrayList<AirbnbListing> cheapList = filterLoad.getDataMapList(SavedInputs.getMinPrice(),SavedInputs.getMaxPrice());
        // retrieve the name of the property with the most reviews
        String popularView = cheapList.stream().max(Comparator.comparing(AirbnbListing::getNumberOfReviews)).get().getName();
        // retrieve the number of reviews of the property
        int maxReview = cheapList.stream().max(Comparator.comparing(AirbnbListing::getNumberOfReviews)).get().getNumberOfReviews();
        return popularView + " " + maxReview;
    }

    /**
     * Searches through the filtered list to retrieve
     * the number of properties available.
     * @return The number of properties available to 
     * be displayed.
     */
    public String propertyListAvailable() {
        // counter for the number of properties that are available
        int propertyCounter = 0;
        // retrieve the list of properties within the min and max price range
        ArrayList<AirbnbListing> yearlyList = filterLoad.getDataMapList(SavedInputs.getMinPrice(), SavedInputs.getMaxPrice());
        // iterate through the list of properties
        for(AirbnbListing lists: yearlyList){
            // check if property is available
            if(lists.getAvailability365() >0){
                // increment counter by one to indicate this property is available 
                propertyCounter++;
            }
        }
        return(" " + propertyCounter);
    }

    /**
     * Searches through the filtered list to see whether 
     * a property is available annually (365 days).
     * @return A string representation of the property count.
     */
    @FXML
    public String yearlyAvailableProperty(){
        // counter for the number of properties that are available anually 
        int propertyCounter = 0;
        // retrieve the list of properties within the min and max price range
        ArrayList<AirbnbListing> yearlyList = filterLoad.getDataMapList(SavedInputs.getMinPrice(), SavedInputs.getMaxPrice());
        // iterate through the list of properties
        for(AirbnbListing lists: yearlyList){
            // check if property is available for 365 days
            if(lists.getAvailability365() == 365){
                // increment counter by one to indicate this property is available anually
                propertyCounter++;
            }
        }
        return(" " + propertyCounter);
    }

    /**
     * Sets the scene to be the Map Page when the back button is pressed.
     */
    @FXML
    public void goMapPage() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MapPage.fxml"));
        Stage window = (Stage) backBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    /**
     * Sets the scene to be the favourite page when the next button is pressed.
     */
    @FXML
    public void goFavouritePage() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FavouriteViewer.fxml"));
        Stage window = (Stage) nextBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    /**
     * Detects which button is pressed in order to change
     * the label.
     */
    @FXML
    public void nextButtonAction(ActionEvent event) {
        // ActionEvent to detect which button is being pressed
        String source = event.getSource().toString();
        // retrieve the ID from the button clicked on 
        source = source.substring(source.indexOf('=') + 1, source.indexOf(','));
        // switch case statement to specify which label will be changed based on the button pressed
        switch (source) {
            case "btn2":  statistics(lbl1);
                break;
            case "btn4":  statistics(lbl2);
                break;
            case "btn6":  statistics(lbl3);
                break;
            case "btn8":  statistics(lbl4);
                break;
        }
    }

    /**
     * Determines if a statistic is being shown or not.
     * @param label The label that is currently being
     * displayed.
     */
    public void toggleShowing(Label label){
        // iterate through the statistic list 
        for (String lbl: statistic){
            // check if the label is being shown
            if (label.getText().equals(lbl) && isShown.get(statistic.indexOf(lbl))){
                // if shown then the statistic boolean value will change to false
                isShown.set(statistic.indexOf(label.getText()),false);
                break;
            }
        }
    }

    /**
     * Checks if the next position is the last position in the ArrayList as well as check if the next few positions are currently occupied by other labels. if so then change the current position of the ArrayList to the start of the ArrayList.
     *
     * @param Integer number to represent the position of the current statistic
     *
     * @return the new number to represent the index of the statistic
     */
    public int checkPosition(int num){
        // check if the next position is the end of the ArrayList and that the next few statistics are being shown.
        if(num + 1  >=  statistic.size()||num + 2  >=  statistic.size()&& isShown.get(num + 1)||num + 3  >=  statistic.size()&& isShown.get(num + 1) && isShown.get(num + 2)||num + 4  >=  statistic.size()&& isShown.get(num + 1) && isShown.get(num + 2)
                && isShown.get(num + 3)){
            // reset the current statistic index
            num = 1;
        }
        return num;
    }

    /**
     * Implements the behaviour for a statistic to be displayed
     * and to rollover when the next button is pressed.
     * @param label The label that is currently being displayed.
     */
    public void statistics(Label label){
        // call the method to indicate that this statistic is being changed
        toggleShowing(label);
        // retrieve the index of the current statistic
        int num = statistic.indexOf(label.getText());
        // check if the next statistic is the last one so that the statistic ArrayLst can loop over and start showing the first statistic
        num = checkPosition(num);

        //iterate from the current statistic to find the next available statistic
        for (int counter = num ; counter< statistic.size(); counter++){
            // check that statistic is showing and that the statistic isnt the same as the current statistic
            if (!isShown.get(counter) && !label.getText().equals(statistic.get(counter))){
                label.setText(statistic.get(counter));
                isShown.set(counter,true);
                break;
            }
        }

    }

    /**
     * Checks if the previous position is the first position in the ArrayList. if so then change the current position of the ArrayList to the end of the ArrayList.
     *
     * @param Integer number to represent the position of the current statistic
     *
     * @return the new number to represent the index of the statistic
     */
    public int checkBackPosition(int num){

        if(num <=  0|| (num - 1 <= 0)|| (num - 2 <= 0)|| (num - 3 <= 0)){
            num = statistic.size()-1;
        }
        return num;
    }

    /**
     * Implements the behaviour for a statistic to be displayed
     * and to rollover when the back button is pressed.
     * @param label The label that is currently being displayed.
     */
    public void statisticsBack(Label label){
        // call the method to indicate that this statistic is being changed
        toggleShowing(label);
        // retrieve the index of the current statistic
        int num = statistic.indexOf(label.getText());
        // check if the next statistic is the first one so that the statistic ArrayLst can loop over and start showing the last statistic
        num = checkBackPosition(num);
        //iterate from the current statistic to find the next available statistic
        for (int counter = num ; counter< statistic.size(); counter--){
            // check that statistic is showing and that the statistic isnt the same as the current statistic
            if (!isShown.get(counter) && !label.getText().equals(statistic.get(counter))){
                label.setText(statistic.get(counter));
                isShown.set(counter,true);
                break;
            }
        }

    }

    /**
     * Detects which button is pressed in order to change
     * the label.
     *
     */
    @FXML
    public void backButtonAction(ActionEvent event) {
        // ActionEvent to detect which button is being pressed
        String source = event.getSource().toString();
        // retrieve the ID from the button clicked on 
        source = source.substring(source.indexOf('=') + 1, source.indexOf(','));
        // switch case statement to specify which label will be changed based on the button pressed
        switch (source) {
            case "btn1" :  statisticsBack(lbl1);
                break;
            case "btn3" :  statisticsBack(lbl2);
                break;
            case "btn5" :  statisticsBack(lbl3);
                break;
            case "btn7" :  statisticsBack(lbl4);
                break;
        }
    }

    /**
     * This method takes in a label and returns the full name of a borough based on 
     * the text of the string 
     * for example : "HRRW" is equivalent to "Harrow"
     * Using the switch case is much more simple than multiple IF statements
     *
     * @param lbl the label which the user clicked on
     * @return String to return the full borough name for filtering the airbnb list.
     */
    private String getName(String lbl){
        switch (lbl) {
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
        alert.setHeaderText("Click any of the buttons to go through the statistics of the properties");
        alert.showAndWait();
    }

}
