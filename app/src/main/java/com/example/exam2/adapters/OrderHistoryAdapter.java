package com.example.exam2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam2.R;
import com.example.exam2.entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {
    private List<Ticket> tickets = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Ticket ticket);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use item_movie but hide buttons for simple history view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        // Sửa lỗi: Truy cập trực tiếp vào public fields thay vì dùng getter
        holder.tvTitle.setText("Vé #" + ticket.id);
        holder.tvDate.setText("Ngày đặt: " + ticket.bookingDate);
        holder.btnShowtimes.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(ticket);
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        View btnShowtimes;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvDate = itemView.findViewById(R.id.tvMovieGenre);
            btnShowtimes = itemView.findViewById(R.id.btnViewShowtimes);
        }
    }
}
