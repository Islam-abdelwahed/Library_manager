package com.example.library_manager.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_manager.Adapters.RecyclerViewAdapter;
import com.example.library_manager.DatacClass.Book;
import com.example.library_manager.R;
import com.example.library_manager.services.BookServices;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class BookFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static RecyclerViewAdapter adapter;
    static ArrayList<Book> allBooks;

    public BookFragment() {
        // Required empty public constructor
    }

    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.booksfragment, container, false);
        TextView tv = v.findViewById(R.id.Books_num_tv);
        try {
            BookServices.GetBooks(result -> {
                    allBooks = Book.fromJson(result);
                    new Handler(Looper.getMainLooper()).post(() -> {
                        adapter = new RecyclerViewAdapter(allBooks);
                        buildRecyclerView(v,adapter);
                        tv.setText(getString(R.string.total) + allBooks.size());
                    });
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

    public static void Refresh(){
        try {
            BookServices.GetBooks(result -> {
                allBooks = Book.fromJson(result);
                adapter .set_search_result(allBooks);
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void buildRecyclerView(View v,RecyclerViewAdapter recyclerViewAdapter){

        RecyclerView rv = v.findViewById(R.id.Book_list);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(recyclerViewAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Book Deleted_book = allBooks.get(viewHolder.getLayoutPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle((R.string.deletebook));
                builder.setMessage(getString(R.string.delete_warning) + Deleted_book.getBOOK_NAME());
                builder.setPositiveButton((R.string.delete), (dialogInterface, i) -> {
                    adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                    try {
                        BookServices.DeleteBook(result -> Log.d("islam", "onSwiped: "+result),Deleted_book.getBOOK_ID());
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton((R.string.cancel), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }).show();
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
            MenuItem search = menu.findItem(R.id.searchicon);
            SearchView sv=(SearchView) search.getActionView();
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    try {
                        BookServices.Search(result -> {
                            Log.d("islam", "onQueryTextChange: "+result);
                            allBooks=Book.fromJson(result);
                            adapter.set_search_result(allBooks);
                        },newText);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
            super.onCreateOptionsMenu(menu, inflater);
        }
}
