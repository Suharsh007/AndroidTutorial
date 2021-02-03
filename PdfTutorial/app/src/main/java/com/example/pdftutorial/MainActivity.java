package com.example.pdftutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
private Button save;
private EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save = findViewById(R.id.save);
        text = findViewById(R.id.text);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
                {
                   if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] parmission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(parmission,1000);

                    }
                    else
                    {
                        savePdf();
                    }

                    }
                else
                {
                    savePdf();
                }
            }
        });
    }

    private void savePdf() {
        Document doc = new Document();
        String mfile = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String mfilepath = Environment.getExternalStorageDirectory()+"/"+mfile+".pdf";
        Font smallbold = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
        try {
            PdfWriter.getInstance(doc,new FileOutputStream(mfilepath));
            doc.open();
            String mtext = text.getText().toString();
            doc.addAuthor("Suharsh");
            doc.add(new Paragraph(mtext,smallbold));
            doc.close();
            Toast.makeText(this, mfile+" .pdf"+" is saved to "+mfilepath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error Message :"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      savePdf();
                }
                else
                {
                    Toast.makeText(this, "Permission Denied....", Toast.LENGTH_SHORT).show();
                }
        }

    }
}