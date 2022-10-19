package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import com.example.cupcake.ui.theme.CupcakeTheme
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        val flavours = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subTotal = "$100"

        composeTestRule.setContent {
            CupcakeTheme() {
                SelectOptionScreen(subtotal = subTotal, options = flavours)
            }
        }

        flavours.forEach{ flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(com.example.cupcake.R.string.subtotal_price, subTotal)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(com.example.cupcake.R.string.next))
            .assertIsNotEnabled()
    }

    @Test
    fun startScreen_verifyContent(){
        composeTestRule.setContent { 
            CupcakeTheme() {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {})
            }
        }

        DataSource.quantityOptions.forEach {
            composeTestRule.onNodeWithText(
                composeTestRule.activity.getString(it.first)
            ).assertIsDisplayed()
        }
    }

    @Test
    fun summeryScreen_verifyContent(){
        val sampleOrderUiState = OrderUiState(
            quantity = 6,
            flavor = "Vanilla",
            date = "Wed Jul 21",
            price = "$100",
            pickupOptions = listOf()
        )

        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = sampleOrderUiState,
                onCancelButtonClicked = { },
                onSendButtonClicked = {_,_ -> }
            )
        }

        composeTestRule.onNodeWithText(sampleOrderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleOrderUiState.date).assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(com.example.cupcake.R.string.subtotal_price, sampleOrderUiState.price)
        ).assertIsDisplayed()
    }
}