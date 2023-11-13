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

public class AdaptadorCandidatos extends ArrayAdapter<Candidato> {
    private Context contexto;
    public static class ViewHolder {
        TextView tvNombreCandidato,tvPartido;
        ImageView ivLogo;
    }
    public AdaptadorCandidatos(Context contexto, ArrayList<Candidato> listaCandidatos) {
        super(contexto,R.layout.fila_candidato,listaCandidatos);
        this.contexto=contexto;
    }
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
    @Override public View getDropDownView(int position,View convertView,ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public long getItemId(int position) { return getItem(position).getCodCandidato(); }
}