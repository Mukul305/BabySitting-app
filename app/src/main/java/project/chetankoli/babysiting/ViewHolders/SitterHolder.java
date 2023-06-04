package project.chetankoli.babysiting.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import project.chetankoli.babysiting.databinding.SitterBinding;

public class SitterHolder extends RecyclerView.ViewHolder {
    public SitterBinding binding;
    public SitterHolder(@NonNull SitterBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
