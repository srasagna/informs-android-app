package com.example.iorms.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MySnackbar() {
    var snackbarVisible by remember { mutableStateOf(false) }

    Column {
        Button(
            onClick = { snackbarVisible = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Submit")
        }

        if (snackbarVisible) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    Button(
                        onClick = { snackbarVisible = false }
                    ) {
                        Text("Dismiss")
                    }
                },
                //backgroundColor = Color.Green // Set background color for Snackbar
            ) {
                Text(
                    "Status Uploaded Successfully",
                    color = Color.White
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview2(){
    MySnackbar()
}
