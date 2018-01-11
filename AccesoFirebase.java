package com.example.luis.pruebasfirebase;

import android.app.Fragment;
import android.app.FragmentManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by luis on 09/01/2018.
 */

public class AccesoFirebase {
    private static DatabaseReference referencia_db;
    private static RespuestaFirebase respuesta;
    public interface RespuestaFirebase
    {
        public void obtenerProductos(ArrayList<Producto> lista);
    }
    //Le paso el Fragment que le llama como argumento para luego poder invocar a la variable fragmentoListado que implementa el obtenerProductos(lista)
    //para rellenar el ListView
    public static void obtenerProductos(FragmentoListado f) {
        final FragmentoListado fragmentoListado=f;

        referencia_db= FirebaseDatabase.getInstance().getReference();
        referencia_db.child("misproductos").addValueEventListener(new ValueEventListener() {
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
        referencia_db= FirebaseDatabase.getInstance().getReference("misproductos");
        String key = referencia_db.child("productos").push().getKey();
        referencia_db.child(key).setValue(p);
        //referencia_db.setValue(p);
    }
}
