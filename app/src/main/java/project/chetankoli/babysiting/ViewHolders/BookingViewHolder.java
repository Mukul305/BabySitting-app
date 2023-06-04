package project.chetankoli.babysiting.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import project.chetankoli.babysiting.databinding.RowBookingBinding;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    public RowBookingBinding binding;
    public BookingViewHolder(@NonNull RowBookingBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }
}
