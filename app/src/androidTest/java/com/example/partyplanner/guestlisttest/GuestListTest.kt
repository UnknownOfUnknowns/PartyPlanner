package com.example.partyplanner.guestlisttest

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.partyplanner.R
import com.example.partyplanner.ui.pages.guestlist.GuestListPage
import com.example.partyplanner.ui.pages.guestlist.GuestListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GuestListTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    val resources = ApplicationProvider.getApplicationContext<Context>().resources

    val viewModel = GuestListViewModel(GuestRepoStub())


    @Before
    fun setUp(){
        composeTestRule.setContent {
            GuestListPage(viewModel = viewModel)
        }
    }

    @Test
    fun addButtonTest() {

        composeTestRule.onNodeWithContentDescription(resources.getString(R.string.defaultAddButtonDescription)).performClick()
        composeTestRule.onNodeWithText("Inviter gæst").assertIsDisplayed()
    }

    @Test
    fun deleteGuest() {
        composeTestRule.onAllNodesWithContentDescription(resources.getString(R.string.deleteIconDescription))
            .onFirst().performClick()
        composeTestRule.onNodeWithText("Du er ved at slette").assertIsDisplayed()

        composeTestRule.onNodeWithText(resources.getString(R.string.DeleteGuest)).performClick()
        composeTestRule.onAllNodesWithContentDescription(resources.getString(R.string.deleteIconDescription)).assertCountEquals(1)
    }
}