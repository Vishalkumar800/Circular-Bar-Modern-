package com.rach.circluarbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rach.circluarbar.ui.theme.CircluarBarTheme

@Composable
fun HomeDesign(modifier: Modifier = Modifier) {

    var initialValue by remember {
        mutableStateOf(0f)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularBar(
            modifier = modifier.size(300.dp),
            initialValue = initialValue.toInt()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = initialValue,
            onValueChange = {
                initialValue = it
            },
            valueRange = 0f..100f,

            )

    }

}

@AppPreview
@Composable
private fun Preview(){
    CircluarBarTheme {
        HomeDesign()
    }
}