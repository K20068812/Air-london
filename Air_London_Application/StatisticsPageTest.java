
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import AirbnbInformation.SavedInputs;

/**
 * The test class StatisticsPageTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */

public class StatisticsPageTest
{

    /**
     *Default constructor for test class StatisticsPageTest
     */
    public StatisticsPageTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    /**
     * test if the correct average number of reviews returns the correct value
     */
    @Test
    public void testAvgNumberOfReviews()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(0);
        SavedInputs.setMaxPrice(100);
        assertEquals("" + 12 ,panelTest.getAverageNumOfReviewsPerProperty());
    }

    /**
     * test if the correct expensive property returns the correct value
     */
    @Test
    public void testgetExpensiveProperty()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(0);
        SavedInputs.setMaxPrice(100);
        assertEquals("Whole House Surbiton, handy London £100",panelTest.getExpensiveProperty());
    }

    /**
     * test if the correct cheapest property returns the correct value
     */
    @Test
    public void testgetCheapestProperty()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(0);
        SavedInputs.setMaxPrice(100);
        assertEquals("Air Bed on Living Room £8.0",panelTest.getCheapestProperty());
    }

    /**
     * test if the correct list size returns the correct value
     */
    @Test
    public void testpropertyListSize()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(50);
        SavedInputs.setMaxPrice(100);
        assertEquals(" " + 16300,panelTest.propertyListAvailable());
    }

    /**
     * test if the correct yearly available property returns the correct value
     */
    @Test
    public void testyearlyAvailableProperty()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(25);
        SavedInputs.setMaxPrice(100);
        assertEquals(" " + 3330 ,panelTest.yearlyAvailableProperty());
    }

    /**
     * test if the correct number of home/apartments returns the correct value
     */
    @Test
    public void testgetHomeApt()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(100);
        SavedInputs.setMaxPrice(100);
        assertEquals(1535 ,panelTest.getHomeApt());
    }

    /**
     * test if the correct expensive borough returns the correct value
     */
    @Test
    public void testgetExpensiveBorough()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(0);
        SavedInputs.setMaxPrice(999);
        assertEquals("2734552.0 Westminster",panelTest.getExpensiveBorough());
    }

    /**
     * test if the correct getPopularReview() returns the correct value
     */
    @Test
    public void testgetPopularReview()
    { StatisticsPage panelTest = new StatisticsPage();
        SavedInputs.setMinPrice(0);
        SavedInputs.setMaxPrice(25);
        assertEquals("Double room with TV+WiFi  in London 267",panelTest.getPopularReview());
    }

}
