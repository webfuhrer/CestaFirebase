package com.example.luis.pruebasfirebase;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.net.URI;

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
    EditText et_email_nuevo, et_email_login, et_nombre_usuario;
    EditText et_password_nuevo, et_password_login;
    Button btn_grabar, btn_login, btn_subir_imagen;
    ProgressBar barra_creacion, barra_login;
    TextView tv_usuario;
    String ruta_imagen;
    Uri uri;

    //eSTE MÉTODO SERÁ LLAMADO CUANDO SE HAYA ELEGIDO UNA IMAGEN Y HAYA UN RESULTADO
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//Para recuperar los datos de llamar a la galeria

        if (requestCode==1 && resultCode== Activity.RESULT_OK)
        {
            if (data.getData()!=null)
            {   //Aquí sólo se recoge la URI. No se grabará hasta que no se haya grabado el contacto
                 uri=(Uri)data.getData();

            }
        }
    }

    private void subirArchivo() {
        //No funciona. Quixá porque lo hago sin estar logueado
        FirebaseStorage almacen=FirebaseStorage.getInstance();
        StorageReference referencia=almacen.getReference();
        UploadTask tarea=referencia.child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).putFile(uri);
        //UploadTask tarea=referencia.child("hijo_falso").putFile(uri);
        tarea.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Log.v("Subida", task.toString());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragmento_login, container, false);
        autenticador=FirebaseAuth.getInstance();
        et_email_nuevo=(EditText)vista.findViewById(R.id.et_email_nuevo);
        et_password_nuevo=(EditText)vista.findViewById(R.id.et_password_nuevo);
        et_email_login=(EditText)vista.findViewById(R.id.et_email_login);
        et_password_login=(EditText)vista.findViewById(R.id.et_password_login);
        et_nombre_usuario=(EditText)vista.findViewById(R.id.et_nombre_usuario);
        barra_creacion=(ProgressBar)vista.findViewById(R.id.pb_nuevo);
        btn_grabar=(Button)vista.findViewById(R.id.btn_nuevo);
        btn_login=(Button) vista.findViewById(R.id.btn_entrar);
        btn_subir_imagen=(Button)vista.findViewById(R.id.btn_subir_imagen);
        barra_login=(ProgressBar)vista.findViewById(R.id.pb_login);

        //Escoger imagen
        btn_subir_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, 1);

            }
        });
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

                    //
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
        final String nombre_usuario=et_nombre_usuario.getText().toString();
        ia=(InterfazAutenticacion)getActivity();
        autenticador.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                barra_creacion.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    UserProfileChangeRequest cambio_usuario=new UserProfileChangeRequest.Builder()
                            .setDisplayName(nombre_usuario).build();
                    final FirebaseUser usuario=FirebaseAuth.getInstance().getCurrentUser();
                    usuario.updateProfile(cambio_usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                ia.autenticacion("Usuario creado "+usuario.getDisplayName()+" con email "+usuario.getEmail(), true);
                                subirArchivo();//Ahora intento subir la imagen. La uri ya la tengo
                            }

                        }
                    });




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
