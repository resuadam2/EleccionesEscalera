package com.resuadam2.eleccionesescalera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AutentificacionActivity extends AppCompatActivity {
    EditText etNif, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentificacion);
        etNif= (EditText) findViewById(R.id.etNif);
        etPassword= (EditText) findViewById(R.id.etPassword);
        Button bEntrar = (Button) findViewById(R.id.bEntrar);
        bEntrar.setOnClickListener(v -> EntrarClic());
    }
    public void EntrarClic() {
        String nif = etNif.getText().toString().trim().toUpperCase();
        String password = etPassword.getText().toString();
        String errorAutentificacion=puedeEntrar(nif, password);
        if(errorAutentificacion.equals("")) {
            // Borramos los datos para que al volver no aparezcan
            etNif.setText("");
            etPassword.setText("");
            AbrirVotacion(nif);
        }
        else
            Toast.makeText(this,errorAutentificacion,Toast.LENGTH_LONG).show();
    }
    private void AbrirVotacion(String nif) {
        Intent intent = new Intent(this, VotacionActivity.class);
        intent.putExtra("NIF",nif);
        startActivity(intent);
    }
    @SuppressLint("Range")
    private String puedeEntrar(String nif, String password) {
        // Devuelve el mensaje de error, o '' si puede entrar
        // Los mensajes de error pueden referirse a:
        // formato incorrecto de los datos
        // usuario y/o contraseña incorrectos
        // o a que ya ha votado anteriormente
        if (!Utiles.NifOk(nif) || !Utiles.PasswordOk(password))
            return getString(R.string.formatoIncorrecto);
        // Comprobar contra la BD
        SQLiteDatabase bd = new AsistenteBD(this).getReadableDatabase();
        String hashPassword = Utiles.generateHash(password);
        Cursor fila = bd.query("usuarios", new String[] {"haVotado"},
                "nif=? AND password=?", new String[] {nif,hashPassword},"","","");
        if(fila.moveToFirst()) {
            if (fila.getInt(fila.getColumnIndex("haVotado"))!=0)
                return getString(R.string.usuarioYaHaVotado);
            else
                return "";
        }
        return getString(R.string.usuarioPasswordIncorrecto);
    }
}