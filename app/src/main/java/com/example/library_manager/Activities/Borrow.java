package com.example.library_manager.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library_manager.DatacClass.Borrowing;
import com.example.library_manager.DataBases.BorrowingDB;
import com.example.library_manager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Borrow extends AppCompatActivity {

    Button bt;
    TextInputEditText SN, BN, BID;
    TextView da, exp;
    ImageButton scan_sn, scan_id;
    String barcode;
    FloatingActionButton add;
    ActivityResultLauncher<ScanOptions> RESULT = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            Toast.makeText(this, (R.string.scantoast_s), Toast.LENGTH_SHORT).show();
            barcode = result.getContents();
        } else
            Toast.makeText(this, (R.string.scantoast_f), Toast.LENGTH_SHORT).show();
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        da = findViewById(R.id.editTextDate);
        exp = findViewById(R.id.expire_date);
        scan_id = findViewById(R.id.scan_bid);
        scan_sn = findViewById(R.id.scan_sn);
        SN = findViewById(R.id.sn_et);
        BN = findViewById(R.id.bn_et);
        BID = findViewById(R.id.bid_et);
        //-----------------------------------------------//
        BorrowingDB db=new BorrowingDB(this);

        add.setOnClickListener(v->{
            Borrowing b = new Borrowing();
            b.setBOOK_SN(SN.getText().toString());
            b.setBORROWER_ID(String.valueOf(Integer.parseInt(BID.getText().toString())));
            b.setBORROWER_NAME(BN.getText().toString());
            db.InsertBorrowing(b);
        });

        scan_sn.setOnClickListener(v -> {
            scancode();
            SN.setText(barcode);
        });

        scan_id.setOnClickListener(v -> {
            scancode();
            BID.setText(barcode);
        });
        ///
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        da.setText(getString(R.string.today) + sdf.format(currentDate));
        c.add(Calendar.DATE, 20);
        exp.setText(getString(R.string.expire) + sdf.format(c.getTime()));
    }

    private void scancode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setPrompt(getString(R.string.scanmessage));
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        RESULT.launch(options);
    }

}