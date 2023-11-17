package com.resuadam2.eleccionesescalera;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utiles {
    /**
     * Método que convierte un array de String en un String separado por un delimitador
     * @param delimitador Delimitador
     * @param lista Lista de String
     * @return Devuelve un String separado por un delimitador
     */
    static public String implode(String delimitador, String[] lista) {
        StringBuilder resultado=new StringBuilder("");
        Iterator<String> it= Arrays.asList(lista).iterator();
        while(it.hasNext())
        {
            resultado.append(it.next());
            if(it.hasNext())
                resultado.append(delimitador);
        }
        return resultado.toString();
    }

    /**
     * Método que comprueba si un NIF es correcto
     * @param nif NIF a comprobar
     * @return Devuelve true si el NIF es correcto y false en caso contrario
     */
    static public boolean NifOk(String nif) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        Pattern pattern =
                Pattern.compile("(\\d{8})([" + letras + "])",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            int numero = Integer.parseInt(matcher.group(1));
            String letra = matcher.group(2);
            char letraCorrecta = letras.charAt(numero % 23);
            if (letra.toUpperCase().charAt(0) == letraCorrecta) return true;
        }
        return false;
    }

    /**
     * Método que comprueba si una contraseña es correcta
     * @param password Contraseña a comprobar
     * @return Devuelve true si la contraseña es correcta y false en caso contrario
     */
    static public boolean PasswordOk(String password) {
        final int MIN_LENGTH_PASSWORD = 5;
        if (password.length()<MIN_LENGTH_PASSWORD) return false;
        //TODO: Verificar la existencia de símbolos peligrosos para la inyección SQL
        return true;
    }

    /**
     * Método que genera un hash a partir de un texto
     * @param texto Texto a partir del cual se genera el hash
     * @return Devuelve el hash generado
     */
    // Gracias a http://www.coderblog.de/producing-the-same-sha-512-hash-in-java-and-php/
    static public String generateHash(String texto) {
        MessageDigest md = null;
        byte[] hash = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            hash = md.digest(texto.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return Utiles.convertToHex(hash);
    }

    /**
     * Método que convierte un array de bytes en un String hexadecimal
     * @param raw Array de bytes
     * @return Devuelve un String hexadecimal
     */
    static private String convertToHex(byte[] raw) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < raw.length; i++) {
            sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}