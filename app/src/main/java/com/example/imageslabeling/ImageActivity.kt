package com.example.imageslabeling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.example.imageslabeling.ui.theme.ImagesLabelingTheme
import com.example.imageslabeling.ui.theme.Labeler

class ImageActivity : ComponentActivity(), LabelsCallback {
    private var labels by mutableStateOf<List<String>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagesLabelingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    intent.getStringExtra("image_uri")?.let { ResultView(it, labels, this) }
                }
            }
        }
    }

    override fun onLabelsReady(label: String) {
        if(!labels.contains(label))
            labels = labels + label
    }


}

@Composable
fun ResultView(image_uri: String, labels: List<String>, instance: LabelsCallback) {
    Labeler().getLabels(LocalContext.current, image_uri.toUri(), instance)
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(30.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(image_uri)
                .crossfade(enable = true).build(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(30.dp))
        DataTable(header = "Labels", rows = labels)
        Spacer(modifier = Modifier.height(20.dp))
        DataTable(header = "Text found in the image", rows = listOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit. In augue diam, iaculis et fringilla fringilla, fringilla eget neque. Nulla sollicitudin pulvinar luctus. Sed vitae fermentum velit. Suspendisse potenti. Etiam nulla mauris, imperdiet id magna vitae, vestibulum pulvinar justo. "))
        val clipboardManager = LocalClipboardManager.current
        Button(onClick = { clipboardManager.setText(AnnotatedString(("aaa")))}) {
            Text(text = "Copy to clipboard")
        }
    }

}


@Composable
fun DataTable(header: String, rows: List<String>) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
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
}

