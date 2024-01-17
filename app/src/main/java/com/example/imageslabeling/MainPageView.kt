package com.example.imageslabeling

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imageslabeling.ui.theme.ImagesLabelingTheme

@Composable
fun MainPage() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Welcome!",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Just choose an image\nfrom your gallery and\nwe will do the rest!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        val mContext = LocalContext.current
        val photoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) {
            if (it != null) {
                val intent = Intent(mContext, ImageActivity::class.java)
                intent.putExtra("image_uri", it.toString())
                mContext.startActivity(intent)
            }
        }
        Button(
            onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )},
            modifier = Modifier
                .height(100.dp)
                .width(300.dp)

        ) {
            Text(
                text = "Choose image",
                fontSize = 30.sp
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "You will get:",
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "•   Best fitting labels for the image\n•    Text found in the image",
            fontSize = 20.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
fun MainPageView() {
    ImagesLabelingTheme {
        MainPage()
    }
}