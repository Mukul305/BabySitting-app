package project.chetankoli.babysiting.UserFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import project.chetankoli.babysiting.R;
import project.chetankoli.babysiting.databinding.FragmentUserChatBinding;

public class UserChatFragment extends Fragment {
    private FragmentUserChatBinding binding;
    public UserChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserChatBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}