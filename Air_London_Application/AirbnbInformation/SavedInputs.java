package AirbnbInformation;

import java.util.ArrayList;

/**
 *  This class is the SavedInputs class. This class holds onto inputs made by the user
 *  for example the from and to price is saved here to be used in other controllers.
 *  
 */
public class SavedInputs {
    //lower bound of the property price
    private static int minPrice = 0; 
    //upper bound of the property price
    private static int maxPrice = 0;
    // Neighbourhood the user selects 
    private static String neighbourhood;
    //array list of the properties the user selected to be a favourite
    private static ArrayList<AirbnbListing> favourite = new ArrayList<>();

    /**
     * This method returns the minimum price
     * 
     * @return Integer for the minimum price
     */
    public static int getMinPrice() {
        return minPrice;
    }

    /**
     * This method returns the maximum price
     * 
     * @return Integer for the maximum price
     */
    public static int getMaxPrice() {
        return maxPrice;
    }

    /**
     * This method sets the minimum price
     * 
     * @param Integer for the new minimum price
     */
    public static void setMinPrice(int NewminPrice) {

        minPrice = NewminPrice;
    }

    /**
     * This method sets the neighbourhood name
     * 
     * @param String for the new neighbourhood name
     */
    public static void setNeighbourhood(String newNeighbourhood) {

        neighbourhood = newNeighbourhood;
    }

    /**
     * This method sets the maximum price
     * 
     * @param Integer for the new maximum price
     */
    public static void setMaxPrice(int NewmaxPrice) {

        maxPrice = NewmaxPrice;
    }

    /**
     * This method returns the neighbourhood to be searched for
     * 
     * @return String for the neighbourhood name
     */
    public static String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Adds the new property into the ArrayList
     * 
     * @param The property to be added into the ArrayList
     */
    public static void addProperty(AirbnbListing property){

        favourite.add(property);

    }

    /**
     * Returns the ArrayList containing properties the user has favourited
     * 
     * @return ArrayList containing properties
     */
    public static ArrayList<AirbnbListing> getProperty(){
        return favourite;
    }

    /**
     * removes a property from the ArrayList
     * 
     * @param The property to be removed from the ArrayList
     */
    public static void removeProperty(AirbnbListing property) {
        favourite.remove(property);
    }
}
