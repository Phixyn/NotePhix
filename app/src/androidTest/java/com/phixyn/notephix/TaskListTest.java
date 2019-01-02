package com.phixyn.notephix;

import android.content.pm.ActivityInfo;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static com.schibsted.spain.barista.assertion.BaristaFocusedAssertions.assertFocused;
import static com.schibsted.spain.barista.assertion.BaristaFocusedAssertions.assertNotFocused;
import static com.schibsted.spain.barista.assertion.BaristaHintAssertions.assertHint;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TaskListTest {
    //region testData
    private List<String> mTestDataLargeCollection = Arrays.asList(
            "Some test data0",
            "Some test data1",
            "Some test data2",
            "Some test data3",
            "Some test data4",
            "Some test data5",
            "Some test data6",
            "Some test data7",
            "Some test data8",
            "Some test data9",
            "Some test data10"
    );

    private List<String> mTestDataSampleTasks = Arrays.asList(
            "Order Grimes album",
            "Pre-order Cyberpunk 2077",
            "Buy cake for the office \uD83C\uDF70",
            "Buy a partyhat \uD83C\uDF89\uD83C\uDF88\uD83C\uDF89",
            "Get 99 firemaking \uD83D\uDD25",
            "Feed the cat \uD83D\uDE3A"
    );

    private String mTestDataSingleTask = "Some test data";
    private String mTestDataLongTask = "This is a really long string that will most likely create a TextView that spans more than one line. This is used to test multi-line text.";
    private String mTestDataUnicodeCharacters = "Buy a partyhat \uD83C\uDF89\uD83C\uDF88\uD83C\uDF89"; // TODO
    private String mTestDataMultipleLines = "This is something that is\nguaranteed to end up in\nmultiple lines.";
    private String mTestDataEmptyTask = "";
    private String mTestDataEmptyTaskWithSpaces = "           ";
    //endregion

    private int mRotationGracePeriod = 2000; // in milliseconds

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    /*
    @Rule
    public BaristaRule<MainActivity> mBaristaRule = BaristaRule.create(
            MainActivity.class);
    */

    // TODO: Uncomment to test with a fresh app (no existing data)
    // Clear all app's SharedPreferences
    // @Rule public ClearPreferencesRule clearPreferencesRule = new ClearPreferencesRule();
    // Delete all tables from all the app's SQLite Databases
    // @Rule public ClearDatabaseRule clearDatabaseRule = new ClearDatabaseRule();
    // Delete all files in getFilesDir() and getCacheDir()
    // @Rule public ClearFilesRule clearFilesRule = new ClearFilesRule();

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void allElementsVisible() {
        assertDisplayed(R.id.items_list);
        assertDisplayed(R.id.item_edit_text);
        assertDisplayed(R.id.add_btn);
    }

    @Test
    public void enterText_forNewTask() {
        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataSingleTask);
        assertDisplayed(R.id.item_edit_text, mTestDataSingleTask);
    }

    @Test
    public void editTaskHint_isCorrect() {
        assertHint(R.id.item_edit_text, R.string.edittext_new_task_hint);
    }

    @Test
    public void addButtonText_isCorrect() {
        assertDisplayed(R.id.add_btn, R.string.button_add_task);
    }

    @Test
    public void addNewTask() {
        clickOn(R.id.item_edit_text);
        assertFocused(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataSingleTask);
        clickOn(R.id.add_btn);
        assertNotFocused(R.id.item_edit_text);
        assertDisplayed(R.id.view_task_card_text, mTestDataSingleTask);
    }

    @Test
    public void addNewTask_withLongString() {
        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataLongTask);
        clickOn(R.id.add_btn);
        assertDisplayed(R.id.view_task_card_text, mTestDataLongTask);
    }

    @Test
    public void addNewTask_withMultipleLines() {
        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataMultipleLines);
        clickOn(R.id.add_btn);
        assertDisplayed(R.id.view_task_card_text, mTestDataMultipleLines);
    }

    @Test
    public void addNewTask_withUnicodeCharacters() {
        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataUnicodeCharacters);
        clickOn(R.id.add_btn);
        assertDisplayed(R.id.view_task_card_text, mTestDataUnicodeCharacters);
    }

    @Test
    public void addManyNewTasks() {
        for (String task : mTestDataLargeCollection) {
            clickOn(R.id.item_edit_text);
            writeTo(R.id.item_edit_text, task);
            clickOn(R.id.add_btn);
            assertDisplayed(R.id.view_task_card_text, task);
        }
    }

    @Test
    public void addSampleTasks() {
        // Used to set up app screenshots for README, Play Store, etc.
        for (String task : mTestDataSampleTasks) {
            writeTo(R.id.item_edit_text, task);
            clickOn(R.id.add_btn);
        }
    }

    @Test
    public void removeTask() {
        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataSingleTask);
        clickOn(R.id.add_btn);
        assertDisplayed(R.id.view_task_card_text, mTestDataSingleTask);
        clickOn(mTestDataSingleTask);
        assertNotDisplayed(R.id.view_task_card_text, mTestDataSingleTask);
    }

    @Test
    public void cannotAddEmptyTask() {
        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataEmptyTask);
        clickOn(R.id.add_btn);
        assertNotDisplayed(R.id.view_task_card_text, mTestDataEmptyTask);
        assertFocused(R.id.item_edit_text);
        assertDisplayed(R.id.item_edit_text, mTestDataEmptyTask);
    }

    @Test
    public void cannotAddEmptyTask_withSpaceCharacters() {
        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataEmptyTaskWithSpaces);
        clickOn(R.id.add_btn);
        assertNotDisplayed(R.id.view_task_card_text, mTestDataEmptyTaskWithSpaces);
        assertFocused(R.id.item_edit_text);
        assertDisplayed(R.id.item_edit_text, mTestDataEmptyTaskWithSpaces);
    }

    @Test
    public void activityRotation_removesEditTextFocus() {
        MainActivity activity = mActivityRule.getActivity();

        // Ensure orientation is portrait
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // Wait for rotation to finish (Barista's sleep)
            sleep(mRotationGracePeriod);
        }

        clickOn(R.id.item_edit_text);
        assertFocused(R.id.item_edit_text);
        sleep(mRotationGracePeriod);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        sleep(mRotationGracePeriod);
        assertNotFocused(R.id.item_edit_text);

        // Ensure orientation is landscape
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            sleep(mRotationGracePeriod);
        }

        clickOn(R.id.item_edit_text);
        assertFocused(R.id.item_edit_text);
        sleep(mRotationGracePeriod);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sleep(mRotationGracePeriod);
        assertNotFocused(R.id.item_edit_text);
    }

    @Test
    public void activityRotation_doesNotRemoveTextFromEditText() {
        MainActivity activity = mActivityRule.getActivity();

        // Ensure orientation is portrait
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // Wait for rotation to finish (Barista's sleep)
            sleep(mRotationGracePeriod);
        }

        clickOn(R.id.item_edit_text);
        writeTo(R.id.item_edit_text, mTestDataSingleTask);
        assertContains(R.id.item_edit_text, mTestDataSingleTask);
        sleep(mRotationGracePeriod);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        sleep(mRotationGracePeriod);
        assertContains(R.id.item_edit_text, mTestDataSingleTask);

        // Ensure orientation is landscape
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            sleep(mRotationGracePeriod);
        }

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sleep(mRotationGracePeriod);
        assertContains(R.id.item_edit_text, mTestDataSingleTask);
    }
}
