package project.chetankoli.babysiting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.chetankoli.babysiting.Adapters.SitterAdapter;
import project.chetankoli.babysiting.FirebaseMessaging.Tokens;
import project.chetankoli.babysiting.Models.Sitter;
import project.chetankoli.babysiting.SitterFragments.ChatFragment;
import project.chetankoli.babysiting.SitterFragments.HomeFragment;
import project.chetankoli.babysiting.SitterFragments.ProfileFragment;
import project.chetankoli.babysiting.SitterFragments.RequestFragment;
import project.chetankoli.babysiting.configs.LoadingBar;
import project.chetankoli.babysiting.configs.MenuForSitter;
import project.chetankoli.babysiting.configs.MenuForUser;
import project.chetankoli.babysiting.databinding.ActivitySitterHomeBinding;
import project.chetankoli.babysiting.databinding.MenuSitterBinding;

public class SitterHomeActivity extends AppCompatActivity {
    ActivitySitterHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySitterHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().add(R.id.container_fragment,new HomeFragment()).commit();

        generateDeviceToken();

        binding.bottomNav.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new HomeFragment()).commit();
            }
        });

        binding.bottomNav.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new RequestFragment()).commit();
            }
        });

        binding.bottomNav.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new ProfileFragment()).commit();
            }
        });

        LoadingBar loadingBar = new LoadingBar(this);

        binding.bottomNav.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.showDialog("Logged Out");
                DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                tokenRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Map<String,Object> tokenMap = new HashMap<>();
                            tokenMap.put("token","12345");
                            tokenRef.updateChildren(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    loadingBar.dissmissDialog();
                                    FirebaseAuth.getInstance().signOut();
                                    Intent i = new Intent(SitterHomeActivity.this,OnBoardingActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }else {
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(SitterHomeActivity.this,OnBoardingActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                FirebaseAuth.getInstance().signOut();
//                Intent i = new Intent(SitterHomeActivity.this,OnBoardingActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//                finish();
            }
        });

        binding.bottomNav.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuForSitter menuForSitter = new MenuForSitter();
                menuForSitter.show(getSupportFragmentManager(),"SitterMenu");
            }
        });

    }

    private void generateDeviceToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("token", s);
                DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference("Tokens")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Tokens tokens = new Tokens();
                tokens.setToken(s);
                tokenRef.setValue(tokens);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SitterHomeActivity.this, "token"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}