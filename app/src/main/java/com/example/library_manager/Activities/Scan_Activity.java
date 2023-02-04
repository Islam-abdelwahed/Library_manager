package com.example.library_manager.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import com.example.library_manager.Adapters.RecyclerViewAdapter;
import com.example.library_manager.DataBases.DataBase;
import com.example.library_manager.DatacClass.Book;
import com.example.library_manager.R;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;

public class Scan_Activity extends AppCompatActivity {
    DecoratedBarcodeView barcodeView ;
    RecyclerView rv;
    RecyclerViewAdapter adapter;
    DataBase dataBase;
    ArrayList<Book> books;
    AlertDialog.Builder builder;
    Button finish;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        dataBase=new DataBase(getApplicationContext());
        finish=findViewById(R.id.finish_btn);
        books=new ArrayList<>();
        barcodeView = findViewById(R.id.compoundBarcodeView);
        barcodeView.setStatusText(getString(R.string.scanmessage));
        barcodeView.initializeFromIntent(getIntent());
        barcodeView.decodeContinuous(callback);
        rv=findViewById(R.id.scan_rv);
        adapter=new RecyclerViewAdapter(books);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        finish.setOnClickListener(l->{
            Intent i=new Intent(getBaseContext(), Inventory_Result.class);

            startActivity(i);
            finish();
        });

    }

    private final BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                Book b=dataBase.SearchBySn(result.getText());
                if(b.IsEmpty()){
                    builder=new AlertDialog.Builder(Scan_Activity.this);
                    builder.setMessage("This Book is not exists in DataBase").setNeutralButton("Ok", (dialog, which) -> {
                        //
                    }).show();
                }else {
                books.add(dataBase.SearchBySn(result.getText()));
                adapter.set_search_result(books);
            }}

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    public void onBackPressed() {
        builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel").setNeutralButton("yes", (dialog, which) -> {
            finish();
        }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
