package com.example.luis.pruebasfirebase;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by luis on 09/01/2018.
 */

public class FragmentoListado extends Fragment implements AccesoFirebase.RespuestaFirebase, DialogoInsertarProducto.OyenteInsercion{
    View vista;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.layout_listado, container, false);
        FloatingActionButton fab=vista.findViewById(R.id.fab_insertar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoInsertarProducto d=new DialogoInsertarProducto();
                d.setTargetFragment(FragmentoListado.this,0);
                d.show(getFragmentManager(), "fragmento_insertar");
            }
        });
        AccesoFirebase.obtenerProductos(this);//Lanzo el método, que cuando tenga la respuesta llamará a obtenerProductos(lista)

        return vista;
    }

    @Override
    public void obtenerProductos(ArrayList<Producto> lista) {
        ListView listado=vista.findViewById(R.id.lv_objetos);
        AdaptadorListView adaptador=new AdaptadorListView(lista, getActivity().getBaseContext());
        //AdaptadorListView adaptador=(AdaptadorListView)listado.getAdapter();
        adaptador.setLista_productos(null);
        adaptador.setLista_productos(lista);
        listado.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }

    //Método implementado de la interfaz creada en DialogoInsertarProducto
    @Override
    public void insertarProducto(Producto c) {
        AccesoFirebase.insertarProducto(c);
    }
}
