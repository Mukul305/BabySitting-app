package project.chetankoli.babysiting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import project.chetankoli.babysiting.configs.Auth;
import project.chetankoli.babysiting.configs.LoadingBar;
import project.chetankoli.babysiting.configs.UserType;
import project.chetankoli.babysiting.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;

    private FirebaseFirestore firestore;
    private DocumentReference userRef;
    private DocumentReference sitterRef;
    private LoadingBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmail.getText().toString().trim();
                String pass = binding.edtPassword.getText().toString().trim();

                if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Some Fields are empty?", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseLogin(LoginActivity.this,email,pass,firebaseAuth);
                }
            }
        });

        binding.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }
    private void firebaseLogin(Context context, String email, String password, FirebaseAuth mAuth){
        loadingBar = new LoadingBar(context);
        loadingBar.showDialog("Login");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
//                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
//                        startActivity(i);
//                        finish();
                        checkUser();
                        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                    }else{
                        loadingBar.dissmissDialog();
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUser(){
        loadingBar.dissmissDialog();
        userRef = firestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    new UserType().setType(getApplicationContext(),"user");
                    Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    checkSitter();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkSitter(){
        loadingBar.dissmissDialog();
        sitterRef = firestore.collection("BabySitter").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        sitterRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    new UserType().setType(getApplicationContext(),"sitter");
                    Intent i = new Intent(LoginActivity.this,SitterHomeActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Log.d("user", "User Not Exits");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}