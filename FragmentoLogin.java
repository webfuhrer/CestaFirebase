package com.example.luis.pruebasfirebase;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by luis on 10/01/2018.
 */

public class FragmentoLogin extends Fragment {
    InterfazAutenticacion ia;
    public interface InterfazAutenticacion
    {
         void autenticacion(String resultado, boolean correcto);
    }

    FirebaseAuth autenticador;
    FirebaseUser usuario;
    EditText et_email_nuevo, et_email_login;
    EditText et_password_nuevo, et_password_login;
    Button btn_grabar, btn_login;
    ProgressBar barra_creacion, barra_login;
    TextView tv_usuario;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragmento_login, container, false);
        autenticador=FirebaseAuth.getInstance();
        et_email_nuevo=(EditText)vista.findViewById(R.id.et_email_nuevo);
        et_password_nuevo=(EditText)vista.findViewById(R.id.et_password_nuevo);
        et_email_login=(EditText)vista.findViewById(R.id.et_email_login);
        et_password_login=(EditText)vista.findViewById(R.id.et_password_login);
        barra_creacion=(ProgressBar)vista.findViewById(R.id.pb_nuevo);
        btn_grabar=(Button)vista.findViewById(R.id.btn_nuevo);
        btn_login=(Button) vista.findViewById(R.id.btn_entrar);
        barra_login=(ProgressBar)vista.findViewById(R.id.pb_login);
        barra_login.setVisibility(View.GONE);
        barra_creacion.setVisibility(View.GONE);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barra_login.setVisibility(View.VISIBLE);
                login();
            }
        });
        btn_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barra_creacion.setVisibility(View.VISIBLE);
                grabar();
            }


        });
        return vista;
    }
    private void cargaVistas(View vista) {


    }
    private void login() {
        String email=et_email_login.getText().toString();
        String password=et_password_login.getText().toString();
        ia=(InterfazAutenticacion) (getActivity());
        autenticador.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                barra_login.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    usuario=autenticador.getCurrentUser();
                    ia.autenticacion("Resultado correcto "+usuario.getEmail(), true);

                }
                else
                {
                    String excepcion=task.getException().getMessage();
                    ia.autenticacion("Resultado incorrecto:"+excepcion, false);

                }
            }
        });
    }

    private void grabar() {
        String email=et_email_nuevo.getText().toString();
        String password=et_password_nuevo.getText().toString();
        ia=(InterfazAutenticacion)getActivity();
        autenticador.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                barra_creacion.setVisibility(View.GONE);
                if (task.isSuccessful())
                {

                    usuario=autenticador.getCurrentUser();
                    ia.autenticacion("Usuario creado "+usuario, true);


                }
                else
                {
                    String excepcion=task.getException().getMessage();
                    Toast.makeText(getActivity().getBaseContext(), "Error: "+excepcion, Toast.LENGTH_SHORT).show();
                }
            }
        })  ;
    }
}
