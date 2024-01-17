package com.example.imageslabeling.ui.theme;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.example.imageslabeling.LabelsCallback;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import java.io.IOException;

public class Labeler {
    private final ImageLabeler labeler;

    public Labeler() {
        labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
    }

    public void getLabels(Context context, Uri image_uri, LabelsCallback labelsCallback) throws IOException {
        InputImage image = InputImage.fromFilePath(context, image_uri);
        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    for (ImageLabel label : labels) {
                        labelsCallback.onLabelsReady(label.getText());
                    }
                })
                .addOnFailureListener(e -> {});
    }
}
