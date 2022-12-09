package com.example.library_manager.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library_manager.Book;
import com.example.library_manager.Converter;
import com.example.library_manager.DataBases.DataBase;
import com.example.library_manager.R;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Insert_Screen extends AppCompatActivity {
    DataBase db = new DataBase(this);
    EditText SN, BN, AN, BC;
    Button add;
    ImageButton SCAN_SN, SCAN_N;
    ImageView B_IMG;
    Bitmap b_img;
    ActivityResultLauncher<ScanOptions> RESULT = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            Toast.makeText(this, (R.string.scantoast_s), Toast.LENGTH_SHORT).show();
            SN.setText(result.getContents());
        } else
            Toast.makeText(this, (R.string.scantoast_f), Toast.LENGTH_SHORT).show();
    });

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_insert_screen);
        add = findViewById(R.id.add_bt);
        SCAN_SN = findViewById(R.id.scan_sn_bt);
        SCAN_N = findViewById(R.id.scan_n_bt);
        SN = findViewById(R.id.sn_et);
        BN = findViewById(R.id.book_name_et);
        BC = findViewById(R.id.book_copies_et);
        AN = findViewById(R.id.author_name_et);
        B_IMG = findViewById(R.id.book_img);

        B_IMG.setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, 2);
        });

        add.setOnClickListener(view -> {
            Book NB = new Book();
            NB.setBOOK_IMAGE(Converter.bitmapToByte(b_img));
            NB.setBOOK_SN(SN.getText().toString());
            NB.setBOOK_NAME(BN.getText().toString());
            NB.setAUTHOR_NAME(AN.getText().toString());
            NB.setBOOK_COPIES(Integer.parseInt(BC.getText().toString()));
            db.InsertBook(NB);
            finish();
        });

        SCAN_N.setOnClickListener(view -> {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 1);
                }
        );

        SCAN_SN.setOnClickListener(view -> scancode());
    }

    private void scancode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setPrompt(getString(R.string.scanmessage));
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        RESULT.launch(options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap b = (Bitmap) data.getExtras().get("data");
            InputImage image = InputImage.fromBitmap(b, 0);
            recognizeText(image);
        }else if(requestCode == 2 && resultCode == RESULT_OK){
             b_img = (Bitmap) data.getExtras().get("data");
             B_IMG.setImageBitmap(b_img);
        }
    }

    private void recognizeText(InputImage image) {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(visionText -> {
                            for (Text.TextBlock block : visionText.getTextBlocks()) {
                                BN.setText(block.getText());
                                for (Text.Line line : block.getLines()) {

                                    for (Text.Element element : line.getElements()) {

                                        for (Text.Symbol symbol : element.getSymbols()) {

                                        }
                                    }
                                }
                                }
                        }).addOnFailureListener(e -> Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show());
    }
}
