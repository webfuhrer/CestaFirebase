package com.example.luis.pruebasfirebase;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by luis on 09/01/2018.
 */

public class AccesoFirebase {
    private static DatabaseReference referencia_db;
    private static RespuestaFirebase respuesta;
    private static FragmentoListado fragmentoListado;
    public interface RespuestaFirebase
    {
        public void obtenerProductos(ArrayList<Producto> lista);
        public void cargarImagen(StorageReference referencia);
    }
    //Le paso el Fragment que le llama como argumento para luego poder invocar a la variable fragmentoListado que implementa el obtenerProductos(lista)
    //para rellenar el ListView
    public static void obtenerProductos(FragmentoListado f) {
        fragmentoListado=f;
        FirebaseUser usuario=FirebaseAuth.getInstance().getCurrentUser();
        //Las key de Database son el nombre de usuario
        String nombre_usuario=usuario.getDisplayName();

        referencia_db= FirebaseDatabase.getInstance().getReference("misproductos");

       //Oyente que salta cada vez que cambian los valores dentro del hijo "nombre_usuario".
                referencia_db.child(nombre_usuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Producto> lista_productos=new ArrayList<>();//La inicializo cada vez que cambian los datos para que no siga aumentando
                for (DataSnapshot dato : dataSnapshot.getChildren())
                {
                    Producto p=dato.getValue(Producto.class);
                    lista_productos.add(p);
                }
                respuesta =(RespuestaFirebase)fragmentoListado;
                respuesta.obtenerProductos(lista_productos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public static void  insertarProducto(Producto p)
    {
        FirebaseUser usuario=FirebaseAuth.getInstance().getCurrentUser();
        referencia_db= FirebaseDatabase.getInstance().getReference("misproductos");
        String key_usr = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();//referencia_db.child("productos").push().getKey();
        String key_producto=referencia_db.push().getKey();

        referencia_db.child(key_usr).child(key_producto).setValue(p);
        //referencia_db.setValue(p);
    }
    public static void obtenerImagen() {
        String usuario = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference referencia = storage.getReference();
        respuesta =(RespuestaFirebase)fragmentoListado;
        respuesta.cargarImagen(referencia.child(usuario));
    }


}
