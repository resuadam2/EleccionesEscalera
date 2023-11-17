package com.resuadam2.eleccionesescalera;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adaptador para la lista de candidatos
 */
public class AdaptadorCandidatos extends ArrayAdapter<Candidato> {
    private Context contexto;

    /**
     * Clase que contiene los elementos de la fila
     */
    public static class ViewHolder {
        TextView tvNombreCandidato,tvPartido;
        ImageView ivLogo;
    }

    /**
     * Constructor
     * @param contexto Contexto de la aplicación
     * @param listaCandidatos Lista de candidatos
     */
    public AdaptadorCandidatos(Context contexto, ArrayList<Candidato> listaCandidatos) {
        super(contexto,R.layout.fila_candidato,listaCandidatos);
        this.contexto=contexto;
    }

    /**
     * Devuelve la vista que se va a mostrar en la lista
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return Devuelve la vista que se va a mostrar en la lista
     */
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Candidato candidato=getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(contexto).inflate(R.layout.fila_candidato,null);
            viewHolder=new ViewHolder();
            viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);
            viewHolder.tvNombreCandidato = (TextView) convertView.findViewById(R.id.tvNombreCandidato);
            viewHolder.tvPartido =(TextView) convertView.findViewById(R.id.tvPartido);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder= (ViewHolder) convertView.getTag();
        // Buscamos el id del drawable(logo) a través de su nombre, que es el nombre del partido
        Resources recursos = contexto.getResources();
        String nombreArchivo=candidato.getNombre().toLowerCase().split(" ")[0];
        int resourceId=recursos.getIdentifier(nombreArchivo,"drawable",contexto.getPackageName());
        viewHolder.ivLogo.setImageResource(resourceId);
        viewHolder.tvNombreCandidato.setText(candidato.getNombre());
        viewHolder.tvPartido.setText(candidato.getPartido());
        viewHolder.tvPartido.setTextColor(candidato.getColor());
        return convertView;
    }

    /**
     * Devuelve la vista que se va a mostrar en el spinner
     * @param position index of the item whose view we want.
     * @param convertView the old view to reuse, if possible. Note: You should
     *        check that this view is non-null and of an appropriate type before
     *        using. If it is not possible to convert this view to display the
     *        correct data, this method can create a new view.
     * @param parent the parent that this view will eventually be attached to
     * @return Devuelve la vista que se va a mostrar en el spinner
     */
    @Override public View getDropDownView(int position,View convertView,ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    /**
     * Devuelve el id del candidato
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return Devuelve el id del candidato
     */
    @Override
    public long getItemId(int position) { return getItem(position).getCodCandidato(); }
}