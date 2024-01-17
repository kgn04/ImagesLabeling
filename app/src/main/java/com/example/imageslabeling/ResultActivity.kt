package com.example.imageslabeling
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.imageslabeling.ui.theme.ImagesLabelingTheme

class ResultActivity : ComponentActivity(), AIProcessorCallback {
    private var labels by mutableStateOf<List<String>>(emptyList())
    private var text by mutableStateOf("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagesLabelingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    intent.getStringExtra("image_uri")?.let { ResultView(it, labels, text, this) }
                }
            }
        }
    }

    override fun onLabelsReady(label: String) {
        if(!labels.contains(label))
            labels = labels + label
    }

    override fun onTextReady(text: String) {
        if(this.text != text)
            this.text = text
    }
}


