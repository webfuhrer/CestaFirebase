package com.example.luis.pruebasfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
  FirebaseAuth autenticador;
  FirebaseUser usuario;
  EditText et_email;
  EditText et_password;
  Button btn_grabar;
  ProgressBar barra_creacion;
  TextView tv_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        cargaVistas();
        autenticador=FirebaseAuth.getInstance();


    }

    private void cargaVistas() {

        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        barra_creacion=(ProgressBar)findViewById(R.id.pb_creacion);
        btn_grabar=(Button)findViewById(R.id.btn_grabar);
        tv_usuario=(TextView) findViewById(R.id.tv_usuario);
        barra_creacion.setVisibility(View.GONE);
        btn_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barra_creacion.setVisibility(View.VISIBLE);
                grabar();
            }


        });
    }

    private void grabar() {
        String email=et_email.getText().toString();
        String password=et_password.getText().toString();
        autenticador.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                barra_creacion.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Logueado, enhorabuena", Toast.LENGTH_SHORT).show();
                    usuario=autenticador.getCurrentUser();
                    tv_usuario.setText(usuario.getEmail());
                }
                else
                {
                    String excepcion=task.getException().getMessage();
                    Toast.makeText(MainActivity.this, "Error: "+excepcion, Toast.LENGTH_SHORT).show();
                }
            }
        })  ;
    }
}
