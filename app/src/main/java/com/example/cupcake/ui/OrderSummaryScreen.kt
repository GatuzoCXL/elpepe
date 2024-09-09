package com.example.cupcake.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cupcake.R
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.components.FormattedPriceLabel
import com.example.cupcake.ui.theme.CupcakeTheme

@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val numberOfCupcakes = context.resources.getQuantityString(
        R.plurals.cupcakes,
        orderUiState.quantity,
        orderUiState.quantity
    )
    val orderSummary = context.getString(
        R.string.order_details,
        numberOfCupcakes,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.price
    )
    val newOrder = context.getString(R.string.new_cupcake_order)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Text(stringResource(R.string.order_summary), style = MaterialTheme.typography.headlineSmall)
            Text(stringResource(R.string.quantity, numberOfCupcakes), style = MaterialTheme.typography.bodyLarge)
            Text(stringResource(R.string.flavor, orderUiState.flavor), style = MaterialTheme.typography.bodyLarge)
            Text(stringResource(R.string.pickup_date, orderUiState.date), style = MaterialTheme.typography.bodyLarge)
            FormattedPriceLabel(subtotal = orderUiState.price, modifier = Modifier.align(Alignment.End))
        }
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, orderSummary)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, newOrder)
                        context.startActivity(shareIntent)
                    }
                ) {
                    Text(stringResource(R.string.send))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderSummaryPreview() {
    CupcakeTheme {
        OrderSummaryScreen(
            orderUiState = OrderUiState(0, "Vanilla", "Tomorrow", "$300.00"),
            onCancelButtonClicked = {},
            modifier = Modifier.fillMaxHeight()
        )
    }
}