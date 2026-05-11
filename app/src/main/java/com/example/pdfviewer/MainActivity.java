package com.example.pdfviewer;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.pdf.viewer.fragment.PdfViewerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            PdfViewerFragment pdfViewerFragment = new PdfViewerFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pdf_viewer_container, pdfViewerFragment)
                    .commit();

            // Example: Load PDF from a URI
            // Replace 'your_pdf_uri' with the actual URI of your PDF file
            // pdfViewerFragment.setDocumentUri(your_pdf_uri);
        }
    }
}