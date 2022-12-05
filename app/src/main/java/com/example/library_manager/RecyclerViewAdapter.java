package com.example.library_manager;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BookViewHolder> {

   public ArrayList<Book> books;

    public RecyclerViewAdapter(ArrayList<Book> b) {
        this.books = b;
    }

    public void set_search_result(ArrayList<Book> b) {
        this.books = b;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, null, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book b = books.get(position);
        holder.BSN.setText(b.getBOOK_SN());
        holder.BN.setText(b.getBOOK_NAME());
        holder.AN.setText(b.getAUTHOR_NAME());
        holder.BC.setText("Copies:" + b.getBOOK_COPIES());
        holder.BIMG.setImageBitmap(BitmapFactory.decodeByteArray(b.getBOOK_IMAGE(), 0, b.getBOOK_IMAGE().length));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView BSN, BN, AN, BC;
        public ImageView BIMG;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            BSN = itemView.findViewById(R.id.book_sn_tv);
            BN = itemView.findViewById(R.id.book_name_tv);
            AN = itemView.findViewById(R.id.author_name_tv);
            BC = itemView.findViewById(R.id.book_copies_tv);
            BIMG = itemView.findViewById(R.id.imageView);
        }
    }
}
