package com.example.luis.pruebasfirebase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by luis on 11/01/2018.
 */

public class DialogoInsertarProducto extends DialogFragment {
    public interface OyenteInsercion {
        public void insertarProducto(Producto c);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View vista=inflater.inflate(R.layout.fragmento_insertar_producto, null);

        //Los botones de los Dialog tiene que gestionarlos el builder porque así al clicarlos hará el dismiss(cerrar Dialog)
        builder.setPositiveButton("Grabar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                String producto=((EditText)vista.findViewById(R.id.et_nombre)).getText().toString();
                String cantidad=((EditText)vista.findViewById(R.id.et_cantidad)).getText().toString();
                String observaciones=((EditText)vista.findViewById(R.id.et_observaciones)).getText().toString();
                String ruta=((EditText)vista.findViewById(R.id.et_ruta)).getText().toString();
                OyenteInsercion oyente=(OyenteInsercion) getTargetFragment();
                Producto p=new Producto(producto, cantidad, observaciones, ruta);
                oyente.insertarProducto(p);

            };
        });
        builder.setView(vista);
        return builder.create();
    }
}
