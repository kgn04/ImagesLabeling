package com.example.imageslabeling

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ResultView(imageUri: String, labels: List<String>, text: String, instance: AIProcessorCallback) {
    AIImageProcessor().processImage(LocalContext.current, imageUri.toUri(), instance)
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(30.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imageUri)
                .crossfade(enable = true).build(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(30.dp))
        DataTable(header = "Labels", rows = labels)
        Spacer(modifier = Modifier.height(20.dp))
        DataTable(header = "Text found in the image", rows = listOf(text))
        val clipboardManager = LocalClipboardManager.current
        Button(onClick = { clipboardManager.setText(AnnotatedString((text)))}) {
            Text(text = "Copy to clipboard")
        }
    }

}


@Composable
fun DataTable(header: String, rows: List<String>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(Modifier.background(Color.Gray)) {
            Text(
                text = header,
                Modifier
                    .border(1.dp, Color.Black)
                    .padding(8.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }
        rows.forEach {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = it,
                    Modifier
                        .border(1.dp, Color.Black)
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
