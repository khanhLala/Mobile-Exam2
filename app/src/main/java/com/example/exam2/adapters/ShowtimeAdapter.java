package com.example.exam2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam2.R;
import com.example.exam2.dal.AppDB;
import com.example.exam2.entities.Showtime;
import com.example.exam2.entities.Theater;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {
    private List<Showtime> showtimes = new ArrayList<>();
    private AppDB db;
    private OnBookClickListener listener;
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    public interface OnBookClickListener {
        void onBookClick(Showtime showtime);
    }

    public ShowtimeAdapter(AppDB db, OnBookClickListener listener) {
        this.db = db;
        this.listener = listener;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimes.get(position);
        Theater theater = db.theaterDAO().getById(showtime.theaterId);
        holder.tvTheater.setText(theater != null ? theater.name : "Rạp");
        holder.tvTime.setText(showtime.date + " " + showtime.time);
        holder.tvPrice.setText(formatter.format(showtime.price) + " VNĐ");
        holder.btnBook.setOnClickListener(v -> listener.onBookClick(showtime));
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTheater, tvTime, tvPrice;
        Button btnBook;
        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTheater = itemView.findViewById(R.id.tvShowtimeTheater);
            tvTime = itemView.findViewById(R.id.tvShowtimeTime);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
