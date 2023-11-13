package com.resuadam2.eleccionesescalera;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class VotacionActivity extends AppCompatActivity {
    final int NUM_VOTOS = 3;
    String[] codCandidatosVotados = new String[NUM_VOTOS];
    SQLiteDatabase bd;
    String NIF;
    Spinner listaCandidatos;
    Button bVotar; int numVotos=0; // v.1
    FrgBotonLimitado frgBotonLimitado; // v.2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion);
        NIF = getIntent().getStringExtra("NIF");
        if(NIF==null) throw new RuntimeException("Votación sin NIF");
        // TODO Validar que NIF puede votar (existe y !haVotado)
        // Controlado en autentificación para dar allí el error, pero aquí sería necesario
        listaCandidatos= (Spinner) findViewById(R.id.listaCandidatos);
        listaCandidatos.setPrompt(getString(R.string.elijaSuVoto));
        //TODO Mejorar el spinner/adaptador para que no aparezca el 1º candidato seleccionado
        //v.1
        bVotar=findViewById(R.id.bVotar);
        bVotar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { VotaManual(); }
        });
        //v.2
        frgBotonLimitado = (FrgBotonLimitado) getSupportFragmentManager().findFragmentById(R.id.frgBotonLimitado);
        frgBotonLimitado.setOnClickListener(
                new FrgBotonLimitado.OnClickListener() {
                    @Override public void onClick(int numClic) { Vota(numClic); }
                    @Override public void ultimoClic() { FinDeLaVotacion(); }
                },
                NUM_VOTOS,
                getString(R.string.vota));
        //---------
        bd = new AsistenteBD(this).getReadableDatabase();
        listaCandidatos.setAdapter(new AdaptadorCandidatos(this, getDatosCandidatos()));
    }
    private ArrayList<Candidato> getDatosCandidatos() {
        ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
        String sql = "SELECT codCandidato, candidatos.nombre AS nombre," +
                "partidos.nombre AS partido, color FROM candidatos " +
                "JOIN partidos ON candidatos.codPartido=partidos.codPartido " +
                "ORDER BY candidatos.nombre";
        Cursor filas= bd.rawQuery(sql, null);
        int colIndexCodCandidato=filas.getColumnIndex("codCandidato");
        int colIndexNombre=filas.getColumnIndex("nombre");
        int colIndexPartido=filas.getColumnIndex("partido");
        int colIndexColor=filas.getColumnIndex("color");
        while(filas.moveToNext()) {
            int codCandidato = filas.getInt(colIndexCodCandidato);
            String nombre = filas.getString(colIndexNombre);
            String partido = filas.getString(colIndexPartido);
            int color = filas.getInt(colIndexColor);
            listaCandidatos .add(new Candidato(codCandidato,nombre,partido,color));
        }
        return listaCandidatos ;
    }
    //v.1
    private void VotaManual() {
        Vota(++numVotos);
        if(numVotos==NUM_VOTOS) FinDeLaVotacion();
    }
    private void Vota(int numVoto) {
        Candidato candidato = (Candidato) listaCandidatos.getSelectedItem();
        long codCandidato=candidato.getCodCandidato();
        // también podría ser así el código porque sobreescribimos getItemId de AdaptadorCandidato
        // long codCandidato = listaCandidatos.getSelectedItemId();
        codCandidatosVotados[numVoto-1]=""+codCandidato;
        // Eliminamos el view del spinner de ese candidato para que no se vuelva a votar
        ((AdaptadorCandidatos) listaCandidatos.getAdapter()).remove(candidato);
        Toast.makeText(this,"Registrado voto " + numVoto+ " a " + candidato.toString(),
                Toast.LENGTH_SHORT).show();
    }
    private void FinDeLaVotacion() {
        bd.beginTransaction();
        // Actualizamos los votos a los candidatos
        String csvCodCandidatos=Utiles.implode(",",codCandidatosVotados);
        bd.execSQL(
                "UPDATE candidatos SET votos=votos+1 WHERE codCandidato IN("+csvCodCandidatos+")");
        // Actualizamos al usuario porque ya ha votado
        bd.execSQL("UPDATE usuarios SET haVotado=1 WHERE NIF='" + NIF + "'");
        bd.setTransactionSuccessful();
        bd.endTransaction();
        // Recuperamos los datos de cómo va la votación
        String sql = "SELECT nombre, votos FROM candidatos WHERE votos>0 ORDER BY votos DESC";
        String strResultado="";
        Cursor filas = bd.rawQuery(sql,null);
        while(filas.moveToNext()){
            strResultado += filas.getString(0) + " " + filas.getInt(1) + " votos \r\n" ;
        }
        filas.close();
        ((TextView) findViewById(R.id.tvResultado)).setText(strResultado);
    }
}
