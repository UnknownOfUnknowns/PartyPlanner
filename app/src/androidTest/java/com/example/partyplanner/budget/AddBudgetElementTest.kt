package com.example.partyplanner.budget

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.partyplanner.R
import com.example.partyplanner.ui.elements.BudgetPage
import com.example.partyplanner.ui.pages.budget.BudgetViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddBudgetElementTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val viewModel = BudgetViewModel(BudgetServiceStub())
    val resources = ApplicationProvider.getApplicationContext<Context>().resources
    @Before
    fun setUp() {
        composeTestRule.setContent {
            BudgetPage(viewModel = viewModel)
        }
    }


    @Test
    fun textInputsUpdatesState() {
        composeTestRule.onNodeWithContentDescription(resources.getString(R.string.defaultAddButtonDescription)).performClick()
        composeTestRule.onNodeWithText(resources.getString(R.string.BudgetAddExpensePostText)).assertIsDisplayed()

        composeTestRule.onNodeWithText(resources.getString(R.string.BudgetChooseNameForExpensePost)).performTextInput("Hello")
        composeTestRule.onNodeWithText(resources.getString(R.string.BudgetChooseNameForExpensePost)).assert(
            hasText("Hello")
        )
    }
}