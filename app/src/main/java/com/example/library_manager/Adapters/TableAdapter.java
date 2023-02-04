package com.example.library_manager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.library_manager.DatacClass.Book;
import com.example.library_manager.R;
import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    ArrayList<Book> books;

    public TableAdapter(ArrayList<Book> books) {
        this.books=books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.SN.setText(books.get(position).getBOOK_SN());
        holder.BN.setText(books.get(position).getBOOK_NAME());
        holder.BC.setText(""+books.get(position).getBOOK_COPIES());
        holder.BS.setText(books.get(position).getBOOK_SN());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView SN,BN,BC,BS;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SN=itemView.findViewById(R.id.sn_column);
            BN=itemView.findViewById(R.id.bn_column);
            BC=itemView.findViewById(R.id.bc_column);
            BS=itemView.findViewById(R.id.bs_column);
        }
    }
}
