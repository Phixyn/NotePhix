package com.phixyn.notephix

import android.content.pm.ActivityInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaFocusedAssertions.assertFocused
import com.schibsted.spain.barista.assertion.BaristaFocusedAssertions.assertNotFocused
import com.schibsted.spain.barista.assertion.BaristaHintAssertions.assertHint
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskListTest {
    //region testData
    private val testDataLargeCollection = listOf(
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
    )

    private val testDataSampleTasks = listOf(
        "Order Grimes album",
        "Pre-order Cyberpunk 2077",
        "Buy cake for the office \uD83C\uDF70",
        "Buy a partyhat \uD83C\uDF89\uD83C\uDF88\uD83C\uDF89",
        "Get 99 firemaking \uD83D\uDD25",
        "Feed the cat \uD83D\uDE3A"
    )

    private val testDataSingleTask = "Some test data"
    private val testDataLongTask =
        "This is a really long string that will most likely create a TextView that spans more" +
        "than one line. This is used to test multi-line text."
    private val testDataUnicodeCharacters = "Buy a partyhat \uD83C\uDF89\uD83C\uDF88\uD83C\uDF89"
    private val testDataMultipleLines =
        "This is something that is\nguaranteed to end up in\nmultiple lines."
    private val testDataEmptyTask = ""
    private val testDataEmptyTaskWithSpaces = "           "
    //endregion

    /** Time, in milliseconds, to wait before performing an action after the screen
     * has been rotated.
     */
    private val rotationGracePeriod: Long = 2000

    @JvmField
    @Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    // @get:Rule
    // val baristaRule: BaristaRule<MainActivity> = BaristaRule.create(MainActivity::class.java)

    // TODO: Uncomment to test with a fresh app (no existing data)
    // Clear all app's SharedPreferences
    // @JvmField @Rule val clearPreferencesRule = ClearPreferencesRule()
    // Delete all tables from all the app's SQLite Databases
    // @JvmField @Rule val clearDatabaseRule = ClearDatabaseRule()
    // Delete all files in getFilesDir() and getCacheDir()
    // @JvmField @Rule val clearFilesRule = ClearFilesRule()

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun allElementsVisible() {
        assertDisplayed(R.id.items_list)
        assertDisplayed(R.id.item_edit_text)
        assertDisplayed(R.id.add_btn)
    }

    @Test
    fun enterText_forNewTask() {
        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataSingleTask)
        assertDisplayed(R.id.item_edit_text, testDataSingleTask)
    }

    @Test
    fun editTaskHint_isCorrect() = assertHint(R.id.item_edit_text, R.string.edittext_new_task_hint)

    @Test
    fun addButtonText_isCorrect() = assertDisplayed(R.id.add_btn, R.string.button_add_task)

    @Test
    fun addNewTask() {
        clickOn(R.id.item_edit_text)
        assertFocused(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataSingleTask)
        clickOn(R.id.add_btn)
        assertNotFocused(R.id.item_edit_text)
        assertDisplayed(R.id.view_task_card_text, testDataSingleTask)
    }

    @Test
    fun addNewTask_withLongString() {
        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataLongTask)
        clickOn(R.id.add_btn)
        assertDisplayed(R.id.view_task_card_text, testDataLongTask)
    }

    @Test
    fun addNewTask_withMultipleLines() {
        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataMultipleLines)
        clickOn(R.id.add_btn)
        assertDisplayed(R.id.view_task_card_text, testDataMultipleLines)
    }

    @Test
    fun addNewTask_withUnicodeCharacters() {
        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataUnicodeCharacters)
        clickOn(R.id.add_btn)
        assertDisplayed(R.id.view_task_card_text, testDataUnicodeCharacters)
    }

    @Test
    fun addManyNewTasks() {
        for (task in testDataLargeCollection) {
            clickOn(R.id.item_edit_text)
            writeTo(R.id.item_edit_text, task)
            clickOn(R.id.add_btn)
            assertDisplayed(R.id.view_task_card_text, task)
        }
    }

    @Test
    fun addSampleTasks() {
        // Used to set up app screenshots for README, Play Store, etc.
        for (task in testDataSampleTasks) {
            writeTo(R.id.item_edit_text, task)
            clickOn(R.id.add_btn)
        }
    }

    @Test
    fun removeTask() {
        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataSingleTask)
        clickOn(R.id.add_btn)
        assertDisplayed(R.id.view_task_card_text, testDataSingleTask)
        clickOn(testDataSingleTask)
        assertNotDisplayed(R.id.view_task_card_text, testDataSingleTask)
    }

    @Test
    fun cannotAddEmptyTask() {
        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataEmptyTask)
        clickOn(R.id.add_btn)
        assertNotDisplayed(R.id.view_task_card_text, testDataEmptyTask)
        assertFocused(R.id.item_edit_text)
        assertDisplayed(R.id.item_edit_text, testDataEmptyTask)
    }

    @Test
    fun cannotAddEmptyTask_withSpaceCharacters() {
        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataEmptyTaskWithSpaces)
        clickOn(R.id.add_btn)
        assertNotDisplayed(R.id.view_task_card_text, testDataEmptyTaskWithSpaces)
        assertFocused(R.id.item_edit_text)
        assertDisplayed(R.id.item_edit_text, testDataEmptyTaskWithSpaces)
    }

    @Test
    fun activityRotation_removesEditTextFocus() {
        val activity = activityRule.activity

        // Ensure orientation is portrait
        if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            // Wait for rotation to finish (Barista's sleep)
            sleep(rotationGracePeriod)
        }

        clickOn(R.id.item_edit_text)
        assertFocused(R.id.item_edit_text)
        sleep(rotationGracePeriod)
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        sleep(rotationGracePeriod)
        assertNotFocused(R.id.item_edit_text)

        // Ensure orientation is landscape
        if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            sleep(rotationGracePeriod)
        }

        clickOn(R.id.item_edit_text)
        assertFocused(R.id.item_edit_text)
        sleep(rotationGracePeriod)
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        sleep(rotationGracePeriod)
        assertNotFocused(R.id.item_edit_text)
    }

    @Test
    fun activityRotation_doesNotRemoveTextFromEditText() {
        val activity = activityRule.activity

        // Set orientation to portrait if needed
        if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            // Wait for rotation to finish (Barista's sleep)
            sleep(rotationGracePeriod)
        }

        clickOn(R.id.item_edit_text)
        writeTo(R.id.item_edit_text, testDataSingleTask)
        assertContains(R.id.item_edit_text, testDataSingleTask)
        sleep(rotationGracePeriod)
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        sleep(rotationGracePeriod)
        assertContains(R.id.item_edit_text, testDataSingleTask)

        // Set orientation to landscape if needed
        if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            sleep(rotationGracePeriod)
        }

        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        sleep(rotationGracePeriod)
        assertContains(R.id.item_edit_text, testDataSingleTask)
    }

    @Test
    fun allElementsVisible_inLandscapeOrientation() {
        val activity = activityRule.activity

        // Set orientation to landscape if needed
        if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            sleep(rotationGracePeriod)
        }

        assertDisplayed(R.id.items_list)
        assertDisplayed(R.id.item_edit_text)
        assertDisplayed(R.id.add_btn)
    }
}
