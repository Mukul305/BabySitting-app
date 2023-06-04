package project.chetankoli.babysiting.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import project.chetankoli.babysiting.Models.User;
import project.chetankoli.babysiting.databinding.UsersBinding;

public class UsersHolder extends RecyclerView.ViewHolder {
    public UsersBinding binding;
    public UsersHolder(@NonNull UsersBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
