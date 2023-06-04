package project.chetankoli.babysiting.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kotlinx.coroutines.Job;
import project.chetankoli.babysiting.databinding.JobRequestLayoutBinding;

public class RequestViewHolder extends RecyclerView.ViewHolder {
    public JobRequestLayoutBinding binding;
    public RequestViewHolder(@NonNull JobRequestLayoutBinding itemView) {
        super(itemView.getRoot());
//        binding = JobRequestLayoutBinding.bind(itemView);
        this.binding = itemView;
    }
}
