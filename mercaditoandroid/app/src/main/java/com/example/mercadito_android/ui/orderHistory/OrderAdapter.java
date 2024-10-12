package com.example.mercadito_android.ui.orderHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadito_android.R;
import com.example.mercadito_android.ui.models.Order;

import java.util.List;

//Clase para manejar elementos gráficos de las órdenes
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orderList;
    private OnOrderClickListener listener;

    public void setListener(OnOrderClickListener listener){
        this.listener = listener;
    }

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textOrderDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textOrderDate = itemView.findViewById(R.id.textOrderDate);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onOrderClick(orderList.get(position));
                    }
                }
            });
        }

        void bind(Order order) {
            textOrderDate.setText("Fecha de compra: " + order.getTimestamp());
        }
    }
}