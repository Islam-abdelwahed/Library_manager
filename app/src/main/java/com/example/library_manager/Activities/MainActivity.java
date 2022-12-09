package com.example.library_manager.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_manager.Adapters.RecyclerViewAdapter;
import com.example.library_manager.Book;
import com.example.library_manager.DataBases.DataBase;
import com.example.library_manager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    DataBase db = new DataBase(this);
    ArrayList<Book> books;
    RecyclerViewAdapter adapter;
    TextView tv;
    SearchView search;
    ActivityResultLauncher<ScanOptions> RESULT = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            Toast.makeText(this, (R.string.scantoast_s), Toast.LENGTH_SHORT).show();
            search.setQuery(result.getContents(), true);
        } else
            Toast.makeText(this, (R.string.scantoast_f), Toast.LENGTH_SHORT).show();
    });

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new RecyclerViewAdapter(db.GetAllBooks());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv = findViewById(R.id.Book_list);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        tv.setText(getString(R.string.total) + db.NUMBER_OF_BOOKS());


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                books = db.GetAllBooks();
                Book Deleted_book = books.get(viewHolder.getAdapterPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle((R.string.deletebook));
                builder.setMessage(getString(R.string.delete_warning) + Deleted_book.getBOOK_NAME());
                builder.setPositiveButton((R.string.delete), (dialogInterface, i) -> {
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    db.DeleteBook(Deleted_book);
                    dialogInterface.dismiss();
                    onResume();
                });
                builder.setNegativeButton((R.string.cancel), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onResume();
                }).show();
                //adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton ADD = findViewById(R.id.ADD_FAB);
        tv = findViewById(R.id.Books_num_tv);
        ImageButton m = findViewById(R.id.mbt), s = findViewById(R.id.scanbt);
        TextView MainText = findViewById(R.id.textView);
        search = findViewById(R.id.SearchButton);

        s.setOnClickListener(view -> scancode());
        ADD.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Insert_Screen.class);
            startActivity(i);
        });

        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Book> books = db.search(newText);
                 adapter.set_search_result(books);

                tv.setText(getString(R.string.total) + books.size());
                if (newText == "") {
                    MainText.setVisibility(View.VISIBLE);
                    s.setVisibility(View.GONE);
                    m.setVisibility(View.VISIBLE);
                } else {
                    MainText.setVisibility(View.GONE);
                    s.setVisibility(View.VISIBLE);
                    m.setVisibility(View.GONE);
                }
                return true;
            }
        });
        registerForContextMenu(m);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.menu_close):
                finish();
                break;
            case (R.id.menu_borrow):
                Intent i=new Intent(getApplicationContext(), Borrow.class);
                startActivity(i);
                break;
        }
        return true;
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