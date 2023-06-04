package project.chetankoli.babysiting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import project.chetankoli.babysiting.databinding.ActivityUserDatailsBinding;

public class UserDatailsActivity extends AppCompatActivity {
    private ActivityUserDatailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDatailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}