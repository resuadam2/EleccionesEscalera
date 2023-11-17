package com.resuadam2.eleccionesescalera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

/**
 * Fragmento que contiene un botón que se puede pulsar un número limitado de veces

 */
public class FrgBotonLimitado extends Fragment {
    Button boton; // Botón que se puede pulsar un número limitado de veces
    int numClics,numClicsTotales; // Número de clics que se han hecho y número máximo de clics
    String textoBoton; // Texto del botón
    // region Interface OnClickListener
    OnClickListener listener;

    /**
     * Interface que debe implementar la actividad que contiene el fragmento
     */
    public interface OnClickListener {
        public void onClick(int numClic);
        public void ultimoClic();
    }
    // endregion

    /**
     * Constructor
     */
    public FrgBotonLimitado() {}

    /**
     * Método que asigna el listener del botón y el número de clics que se pueden hacer
     * @param listener Listener del botón
     * @param numClics Número de clics que se pueden hacer
     * @param textoBoton Texto del botón
     */
    public void setOnClickListener(OnClickListener listener,int numClics,String textoBoton){
        this.listener=listener;
        this.numClics=0;
        this.numClicsTotales=numClics;
        this.textoBoton=textoBoton;
        etiquetaBoton();
    }

    /**
     * Método que se ejecuta cuando se crea la vista del fragmento
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Devuelve la vista del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.frg_boton, container, false);
        boton = (Button) layout.findViewById(R.id.botonFrg);
        boton.setOnClickListener(v -> botonFrgClic());
        return layout;
    }

    /**
     * Método que se ejecuta cuando se pulsa el botón
     */
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

    /**
     * Método que actualiza la etiqueta del botón
     */
    private void etiquetaBoton() {
        boton.setText(textoBoton + " " + numClics + "/" + numClicsTotales);
    }
}