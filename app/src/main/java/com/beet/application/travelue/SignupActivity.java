package com.beet.application.travelue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.beet.application.travelue.SignupActivity2.setUser;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputName, inputSurname;
    private Button btnSignIn, btnSignUp, btnResetPassword, mbtnBack;
    private TextView tvTerminos;
    private ProgressBar progressBar;

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("usuarios");

        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputName = (EditText) findViewById(R.id.name);
        inputSurname = (EditText) findViewById(R.id.surname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        tvTerminos = (TextView) findViewById(R.id.tvTerminos);
        tvTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setTitle(getString(R.string.terminostitulo))
                        .setMessage(getString(R.string.terminosycondiciones))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.rabano)
                        .show();
            }
        });
        mbtnBack = (Button) findViewById(R.id.btnBack);
/**
 btnResetPassword.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
}
});
 */
        /**
         btnSignIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        finish();
        }
        });
         */
        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(nombre)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.alerta_nombre), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.alerta_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.alerta_clave), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), getString(R.string.alerta_clavecorta), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, getString(R.string.alerta_usuariocreado), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                String nombre=inputName.getText().toString();

                                String apellido=inputSurname.getText().toString();
                                String email=inputEmail.getText().toString();
                                Usuario user=new Usuario(nombre, apellido, email);
                                setUser(user);
                                SignupActivity2.setPassword(password);


                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    /**
                                    DatabaseReference currentUser = mDatabase.child("usuario");
                                    currentUser.child("nombre").setValue(nombre);
                                     */
                                    final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nombre)

                                            .build();

                                    usuario.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (usuario.equals(null)){
                                                            Log.d("TAG", "no existe");
                                                        }

                                                    }
                                                }
                                            });


                                    auth.signInWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString())
                                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    // If sign in fails, display a message to the user. If sign in succeeds
                                                    // the auth state listener will be notified and logic to handle the
                                                    // signed in user can be handled in the listener.
                                                    progressBar.setVisibility(View.GONE);
                                                    if (!task.isSuccessful()) {
                                                        // there was an error
                                                    } else {
                                                        Intent intent = new Intent(SignupActivity.this, SignupActivity2.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                    /**
                                    Intent intent = new Intent(SignupActivity.this,SignupActivity2.class);
                                    startActivity(intent);
                                     */
                                }
                            }
                        });



            }
        });
    }



    private void insertarContacto(Usuario user)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios");
        String key = myRef.child("usuario").push().getKey();
        Map m=new HashMap<>();
        m.put(key,user);
        myRef.updateChildren(m);

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}
