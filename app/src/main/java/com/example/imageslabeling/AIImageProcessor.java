package com.example.imageslabeling;
import android.content.Context;
import android.net.Uri;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class AIImageProcessor {
    private final ImageLabeler labeler;
    private final TextRecognizer recognizer;


    public AIImageProcessor() {
        labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    private void findLabels(Context context, Uri image_uri, AIProcessorCallback AIProcessorCallback) throws IOException {
        InputImage image = InputImage.fromFilePath(context, image_uri);
        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    for (ImageLabel label : labels) {
                        AIProcessorCallback.onLabelsReady(label.getText());
                    }
                })
                .addOnFailureListener(e -> {});
    }

    private void findText(Context context, Uri image_uri, AIProcessorCallback AIProcessorCallback) throws IOException {
        InputImage image = InputImage.fromFilePath(context, image_uri);
        recognizer.process(image)
                .addOnSuccessListener(visionText -> AIProcessorCallback.onTextReady(visionText.getText()))
                .addOnFailureListener(
                        e -> {});
    }

    public void processImage(Context context, Uri image_uri, AIProcessorCallback aiProcessorCallback) throws IOException {
        findLabels(context, image_uri, aiProcessorCallback);
        findText(context, image_uri, aiProcessorCallback);
    }
}
