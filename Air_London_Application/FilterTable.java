// the airbnb listing loader
import AirbnbInformation.AirbnbDataLoader;
// each individual record of the properties in the air bnb listing.
import AirbnbInformation.AirbnbListing;

import java.util.ArrayList;

/**
 *  This class is the FilterTable class which filters the airbnb list based on factors like
 *  minimum price
 *  maximum price
 *  neighbourhood/borough
 *
 */
public class FilterTable {

    // ArrayList of the the whole airbnb list data.
    private static ArrayList<AirbnbListing> listings;

    /**
     * Constructor for the FilterTable 
     * load the airbnb data and store it as an ArrayList 
     */
    public FilterTable(){
        listings = new AirbnbDataLoader().load();
    }

    /**
     * This method retrieves a list filtered to fit the minimum and maximum price range
     * @param integer value for the users minimum price
     * @param integer value for the users maximum price
     * @return ArrayList of the new properties that fit within the price range
     */
    public ArrayList<AirbnbListing> getDataMapList(int minPrice, int maxPrice){
        // new array list for the filtered list
        ArrayList<AirbnbListing> normalList = new ArrayList();
        // for loop to iterate through all the properties that fit within the price range
        for(AirbnbListing listing: listings) {
            // if statement to compare prices
            if (listing.getPrice() >= minPrice && (listing.getPrice() <= maxPrice)){
                // add this property into the new list
                normalList.add(listing);
            }
        }
        // return the new filtered list
        return normalList;
    }

    /**
     * This method retrieves a list filtered to fit the minimum and maximum price range
     * and the specific borough location
     * @param String value neighbourhood to indicate the borough to be filtered for.
     * @param integer value for the users minimum price
     * @param integer value for the users maximum price
     * @return ArrayList of the new properties that fit within the price range and is in a
     * specific borough.
     */
    public ArrayList<AirbnbListing> getNeighbourhoodData(String neighbourhood, int minPrice, int maxPrice){
        // new array list for the filtered list
        ArrayList<AirbnbListing> neighbourhoodList = new ArrayList();
        // for loop to iterate through all the properties that fit within the price range
        // also filters location for the borough specified
        for(AirbnbListing listing: listings) {
            // if statement to compare prices and find properties within the bourough
            if (listing.getNeighbourhood().equals(neighbourhood) && listing.getPrice() >= minPrice && (listing.getPrice() <= maxPrice)){
                // add this property into the new list
                neighbourhoodList.add(listing);
            }
        }
        // return the new filtered list
        return neighbourhoodList;
    }
}
