package ru.polescanner.droidmvp.ui.reusablecomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.validation_etude.ui.reusable.utils.FocusedState

@Composable
fun OutlinedCardWithTitle(
    content: @Composable ColumnScope.(
        interactionSource: MutableInteractionSource,
        onClickAtContent: () -> Unit,
        focused: FocusedState,
        modifier: Modifier) -> Unit,
    title: String?,
    modifier: Modifier = Modifier,
    focusedContent: FocusedState = FocusedState(1) //ToDo this implementation prevents from Content layout change
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var focusOnCard by remember { mutableStateOf(false) }
    val focusOnContent by interactionSource.collectIsFocusedAsState()
    val focusedCard = focusOnCard || focusOnContent || focusedContent.hasFocus
    //ToDo Check and replace with the Theme color
    val color  = if (focusedCard) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

    fun onClickAtContentOrCard() {
        focusManager.clearFocus()
        focusRequester.requestFocus()
    }
    Box( modifier = modifier.padding(8.dp)) {
        Surface(
            onClick = ::onClickAtContentOrCard,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusOnCard = it.isFocused }
                .focusable()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(if (focusedCard) 4.dp else 2.dp),
            border = BorderStroke(if (focusedCard) 2.dp else 1.dp, color),
            interactionSource = interactionSource,
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Column(
                content = {
                    content(
                        interactionSource = interactionSource,
                        onClickAtContent = ::onClickAtContentOrCard,
                        focused = focusedContent,
                        modifier = Modifier
                    )
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp) //ToDo Check and replace with the Theme background color
                .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text(
                text = title.orEmpty(),
                style = typography.labelMedium,
                color = color,
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }
    }
}

@Preview(
    //showSystemUi = true,
    showBackground = true,
    backgroundColor = 0x989a82,
    widthDp = 320)
@Composable
fun CardPreview () {
    OutlinedCardWithTitle(
        content = {_, _, _, _ ->
            Column {
                Spacer(
                    modifier = Modifier.height(60.dp)
                )
                Text(
                    text = "Hello Baina",
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
                Text(
                    text = "bye bye baby",
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        },
        title = "Hello",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
    )
}

@Preview
@Preview(showBackground = true)
@Composable
fun NewCardPreview() {
    OutlinedCardWithTitle(
        content = {_, _, _, _ ->
            Column {
                Text(
                    text = "Hello content",
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "tu tu tu",
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        },
        title = "Hello Card"
    )
}