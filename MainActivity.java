package com.example.luis.pruebasfirebase;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

public class MainActivity extends AppCompatActivity implements FragmentoLogin.InterfazAutenticacion{
    FragmentoLogin.InterfazAutenticacion ia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        FragmentoLogin fl=new FragmentoLogin();

        ft.replace(R.id.contenedor_fragment, fl);
        ft.commit();



    }

//El Fragment de Login llama a este método cuando ha habido un intento de autenticación o un nuevo login
    @Override
    public void autenticacion(String resultado, boolean correcto) {
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        //Si es resultado correcto, hay que mandarle al fragment de listado
        if (correcto)
        {
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            FragmentoListado fl=new FragmentoListado();

            ft.replace(R.id.contenedor_fragment, fl);
            ft.commit();
        }
    }
}
