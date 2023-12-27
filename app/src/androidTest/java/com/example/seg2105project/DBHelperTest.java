/**
 * The DBHelperTest class is a JUnit test class that tests the functionality of the CDBHelper,
 * EDBHelper, and UDBHelper classes in the com.example.seg2105project package.
 */
package com.example.seg2105project;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.seg2105project.ClubOwner.ClubInfo;
import com.example.seg2105project.DB.CDBHelper;
import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.DB.UDBHelper;
import com.example.seg2105project.Shared.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class DBHelperTest {

    private CDBHelper cdbHelper;
    private EDBHelper edbHelper;
    private UDBHelper udbHelper;

    /**
     * The setUp() function initializes three database helper objects using the application context.
     */
    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        cdbHelper = new CDBHelper(context);
        edbHelper = new EDBHelper(context);
        udbHelper = new UDBHelper(context);
    }

    /**
     * The testCDB function tests the addClubInfo method of the cdbHelper class by asserting that it
     * returns true when given specific parameters.
     */
    @Test
    public void testCDB() {
        assertTrue(cdbHelper.addClubInfo("Clubname", "Instagram", "Contact", "123456789", (float) 1.5, "Awesome"));
    }
    /**
     * The testEDB function tests the addEvent method of the edbHelper class by asserting that it
     * returns true when given specific parameters.
     */
    @Test
    public void testEDB() {
        assertTrue(edbHelper.addEvent("eventname", "name","age", "level", "pace", "clubname", 5, "date"));
    }
    /**
     * The testUDB function tests the addUser method of the udbHelper class by asserting that it
     * returns true when given specific parameters.
     */
    @Test
    public void testUDB() {
        assertTrue(udbHelper.addUser("clubname", "username", "password", "type"));
    }


    /**
     * This function tests the validation of a user's password in the database.
     */
    @Test
    public void testUDBData() {
        // Assuming you have added an event with these details in the database in a
        // previous test
        udbHelper.addUser("clubname", "Testusername", "Testpassword", "type");

        assertTrue(udbHelper.validateUserPassword("Testusername", "Testpassword"));
    }

    /**
     * This function tests if an event with specific details exists in the database.
     */
    @Test
    public void testEDBData() {
        // Assuming you have added an event with these details in the database in a
        // previous test
        edbHelper.addEvent("Testeventname", "Testname","Testage", "Testlevel", "Testpace", "Testclubname", 5, "Testdate");

        assertTrue(edbHelper.eventFound("Testeventname", "Testage", "Testpace", "Testlevel"));
    }
    /**
     * This function tests if a club named "TestClub" is found in the database.
     */
    @Test
    public void testCDBData() {
        // Assuming you have added an event with these details in the database in a
        // previous test
        cdbHelper.addClubInfo("TestClub", "testInstagram", "testContact", "123456789", (float) 1.5, "Awesome");

        assertTrue(cdbHelper.clubFound("TestClub"));
    }
    /**
     * This is a unit test for the `getSearchEvents` method in a Java class, which tests if the method
     * returns the expected search results from a database.
     */
    @Test
    public void testGetSearchEvents() {
        // Assuming you have added test data to the database in a previous test
        // This data should match the expected results in the test
        edbHelper.addEvent("TestEvent1", "TestDescription1", "TestAge1", "TestLevel1", "TestPace1", "TestClub1", 10, "TestDate1");
        edbHelper.addEvent("TestEvent2", "TestDescription2", "TestAge2", "TestLevel2", "TestPace2", "TestClub2", 15, "TestDate2");

        // Perform the search and get the result from the method
        ArrayList<Event> searchResults = edbHelper.getSearchEvents("TestClub1");

        // Verify the results
        assertThat(searchResults).isNotNull();
        assertThat(((ArrayList<Event>) searchResults).size()).isEqualTo(1);

        // Assuming that the event details match the expected values
        Event resultEvent = searchResults.get(0);
        assertThat(resultEvent.getEventName()).isEqualTo("TestEvent1");
        assertThat(resultEvent.getAge()).isEqualTo("TestAge1");
        assertThat(resultEvent.getLevel()).isEqualTo("TestLevel1");
        assertThat(resultEvent.getPace()).isEqualTo("TestPace1");
        assertThat(resultEvent.getClubName()).isEqualTo("TestClub1");
        assertThat(resultEvent.getParticipants()).isEqualTo(10);
        assertThat(resultEvent.getDate()).isEqualTo("TestDate1");
    }

    /**
     * This is a unit test in Java that tests the functionality of the getMemberList method in a user
     * database helper class.
     */
    @Test
    public void testGetMemberList() {
        // Assuming you have added test data to the database in a previous test
        // This data should match the expected results in the test
        udbHelper.addUser("TestClub", "TestUsername1", "TestPassword1", "M");
        udbHelper.addUser("TestClub", "TestUsername2", "TestPassword2", "M");

        // Perform the getMemberList and get the result from the method
        ArrayList<String> memberList = udbHelper.getMemberList();

        // Verify the results
        assertThat(memberList).isNotNull();
        assertThat(memberList.size()).isEqualTo(2);

        // Assuming that the usernames match the expected values
        assertThat(memberList).contains("TestUsername1");
        assertThat(memberList).contains("TestUsername2");
    }
    /**
     * This function tests the getAllFeedback method in a Java class by adding test data to the
     * database and verifying the results.
     */
    @Test
    public void testGetAllFeedback() {
        // Assuming you have added test data to the database in a previous test
        cdbHelper.addClubInfo("TestClub1", "testInstagram1", "testContact1", "123456789", (float) 1.5, "Awesome1");
        cdbHelper.addClubInfo("TestClub2", "testInstagram2", "testContact2", "987654321", (float) 2.5, "Fantastic2");

        // Perform the getAllFeedback method and get the result
        ArrayList<ClubInfo> feedbackList = cdbHelper.getAllFeedback();

        // Verify the results
        assertThat(feedbackList).isNotNull();
        assertThat(feedbackList.size()).isEqualTo(2);

        // Assuming that the feedback details match the expected values for TestClub1
        ClubInfo feedback1 = feedbackList.get(0);
        assertThat(feedback1.getClubName()).isEqualTo("TestClub1");
        assertThat(feedback1.getInstagram()).isEqualTo("testInstagram1");
        assertThat(feedback1.getContact()).isEqualTo("testContact1");
        assertThat(feedback1.getPhoneNumber()).isEqualTo("123456789");
        assertThat(feedback1.getRating()).isEqualTo("Rating: 1.5");
        assertThat(feedback1.getFeedback()).isEqualTo("Feedback: Awesome1");

        // Assuming that the feedback details match the expected values for TestClub2
        ClubInfo feedback2 = feedbackList.get(1);
        assertThat(feedback2.getClubName()).isEqualTo("TestClub2");
        assertThat(feedback2.getInstagram()).isEqualTo("testInstagram2");
        assertThat(feedback2.getContact()).isEqualTo("testContact2");
        assertThat(feedback2.getPhoneNumber()).isEqualTo("987654321");
        assertThat(feedback2.getRating()).isEqualTo("Rating: 2.5");
        assertThat(feedback2.getFeedback()).isEqualTo("Feedback: Fantastic2");
    }

    /**
     * The testGetAllEvents function tests the getAllEvents method by adding events to the database and
     * then retrieving and asserting the properties of the events.
     */
    @Test
    public void testGetAllEvents() {
        
        edbHelper.addEvent("TestEvent3", "TestDescription3", "TestAge3", "TestLevel3", "TestPace3", "TestClub3", 30, "TestDate3");
        edbHelper.addEvent("TestEvent4", "TestDescription4", "TestAge4", "TestLevel4", "TestPace4", "TestClub4", 16, "TestDate4");
        // Call the getAllEvents method to retrieve events from the database
        ArrayList<Event> eventsList = edbHelper.getAllEvents();

        // Perform assertions on the retrieved eventsList
        assertThat(eventsList).isNotNull();

        // You can add more specific assertions based on your expected data
        // For example, if you know the structure of the Event class, you can
        // check specific properties of the events in the list.

        // Example:
        for (Event event : eventsList) {
            assertThat(event.getEventName()).isNotEmpty();
            assertThat(event.getAge()).isNotNull();
            assertThat(event.getLevel()).isNotEmpty();
            assertThat(event.getPace()).isNotEmpty();
            assertThat(event.getClubName()).isNotEmpty();
            assertThat(event.getParticipants()).isAtLeast(0);
            assertThat(event.getDate()).isNotEmpty();
        }
    }
}
