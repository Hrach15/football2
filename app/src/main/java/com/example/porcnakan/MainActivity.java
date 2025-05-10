package com.example.porcnakan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MainScreen;
import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerRedirect;
    private TextView forgotPasswordButton;
    private Button guestModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.email_login);
        passwordEditText = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.btn_login);
        registerRedirect = findViewById(R.id.register_redirect);
        forgotPasswordButton = findViewById(R.id.forgotPassword);
        guestModeButton = findViewById(R.id.guest_mode_button);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity
                        .this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });

        forgotPasswordButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your email first", Toast.LENGTH_SHORT).show();
            } else {
                sendPasswordResetEmail(email);
            }
        });

        registerRedirect.setOnClickListener(v -> {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        guestModeButton.setOnClickListener(v -> {
            Intent guestIntent = new Intent(MainActivity.this, MainScreen.class);
            guestIntent.putExtra("isGuest", true);
            startActivity(guestIntent);
            finish();
        });
    }

    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MainScreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Please verify your email address.", Toast.LENGTH_LONG).show();
                            firebaseAuth.signOut();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("LoginActivity", "Error: ", task.getException());
                    }
                });
    }

    private void sendPasswordResetEmail(String email) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}