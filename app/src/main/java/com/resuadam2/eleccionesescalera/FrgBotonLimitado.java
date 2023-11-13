package com.resuadam2.eleccionesescalera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class FrgBotonLimitado extends Fragment {
    Button boton;
    int numClics,numClicsTotales;
    String textoBoton;
    // region Interface OnClickListener
    OnClickListener listener;
    public interface OnClickListener {
        public void onClick(int numClic);
        public void ultimoClic();
    }
    // endregion
    public FrgBotonLimitado() {}
    public void setOnClickListener(OnClickListener listener,int numClics,String textoBoton){
        this.listener=listener;
        this.numClics=0;
        this.numClicsTotales=numClics;
        this.textoBoton=textoBoton;
        etiquetaBoton();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.frg_boton, container, false);
        boton = (Button) layout.findViewById(R.id.botonFrg);
        boton.setOnClickListener(v -> botonFrgClic());
        return layout;
    }
    public void botonFrgClic() {
        if(numClics<numClicsTotales) {
            listener.onClick(++numClics);
            etiquetaBoton();
            if((numClics==numClicsTotales)) {
                boton.setEnabled(false);
                listener.ultimoClic();
            }
        }
    }
    private void etiquetaBoton() {
        boton.setText(textoBoton + " " + numClics + "/" + numClicsTotales);
    }
}