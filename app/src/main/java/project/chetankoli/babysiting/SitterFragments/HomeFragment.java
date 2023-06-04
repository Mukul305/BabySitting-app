package project.chetankoli.babysiting.SitterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import project.chetankoli.babysiting.Adapters.BookingAdapter;
import project.chetankoli.babysiting.Adapters.SitterAdapter;
import project.chetankoli.babysiting.Adapters.UsersAdapter;
import project.chetankoli.babysiting.Models.Booking;
import project.chetankoli.babysiting.Models.Sitter;
import project.chetankoli.babysiting.Models.User;
import project.chetankoli.babysiting.R;
import project.chetankoli.babysiting.SitterHomeActivity;
import project.chetankoli.babysiting.configs.UserType;
import project.chetankoli.babysiting.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    FirebaseFirestore firestore;
    BookingAdapter adapter;
    ArrayList<Booking> bList;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        firestore = FirebaseFirestore.getInstance();
        binding.rvSitter.setHasFixedSize(true);
        bList = new ArrayList<>();
        adapter = new BookingAdapter(bList,getContext());
        binding.rvSitter.setAdapter(adapter);
        binding.progressBar.setVisibility(ProgressBar.VISIBLE);
        String type = new UserType().getType(getContext());
        Log.d("userType", type);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bList.clear();
                adapter.notifyDataSetChanged();
                if (snapshot.exists()){
                    binding.progressBar.setVisibility(View.GONE);
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Booking booking = ds.getValue(Booking.class);
                        bList.add(booking);
                    }
                }else{
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Not Any Booking available!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}