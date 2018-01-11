package com.example.luis.pruebasfirebase;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by luis on 09/01/2018.
 */

public class AdaptadorListView extends BaseAdapter implements ListAdapter {
    ArrayList<Producto> lista_productos;
    Context contexto;

    public AdaptadorListView(ArrayList<Producto> lista_productos, Context contexto) {
        this.lista_productos = lista_productos;
        this.contexto = contexto;
    }

    public ArrayList<Producto> getLista_productos() {
        return lista_productos;
    }

    public void setLista_productos(ArrayList<Producto> lista_productos) {
        this.lista_productos = lista_productos;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return lista_productos.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(contexto);
        View vista=inflater.inflate(R.layout.vista_producto, parent, false);
        TextView nombre=vista.findViewById(R.id.tv_nombre);
        TextView cantidad=vista.findViewById(R.id.tv_cantidad);
        TextView observaciones=vista.findViewById(R.id.tv_observaciones);
        nombre.setText(lista_productos.get(position).getNombre());
        cantidad.setText(lista_productos.get(position).getCantidad());
        observaciones.setText(lista_productos.get(position).getObservaciones());
        return vista;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return lista_productos.size();
    }

    @Override
    public boolean isEmpty() {
        return lista_productos.isEmpty();
    }
}
