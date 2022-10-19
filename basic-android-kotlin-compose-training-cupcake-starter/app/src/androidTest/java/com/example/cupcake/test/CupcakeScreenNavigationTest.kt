package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class CupcakeScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController:TestNavHostController

    @Before
    fun setupCupcakeNavHost(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            CupcakeApp(navController = navController)
        }
    }

    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.one_cupcake))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }


    @Test
    fun cupcakeNavHost_clickBackOnFlavor_navigatesToStartScreen(){
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelOnFlavor_navigatesToStartScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.cancel))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_navigatePickupScreen(){
        navigateToPickupScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    @Test
    fun cupcakeNavHost_clickBackOnPickup_navigatesToFlavorScreen(){
        navigateToPickupScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelOnPickup_navigatesToStartScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.cancel))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_navigatesToSummery(){
        navigateSummeryScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelOnSummery_navigatesToStartScreen(){
        navigateSummeryScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.cancel))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }


    private fun navigateToFlavorScreen(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.one_cupcake))
            .performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.chocolate))
            .performClick()
    }

    private fun navigateToPickupScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.next))
            .performClick()
    }

    private fun navigateSummeryScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.next))
            .performClick()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun performNavigateUp() {
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }
}