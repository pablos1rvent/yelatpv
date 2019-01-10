package com.yelatpv.ClasesOBJ;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by pablosirvent on 11/3/18.
 */

public class BDUsuarios extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    String crear_tabla = "CREATE TABLE IF NOT EXISTS usuarios(idusuario INTEGER PRIMARY KEY NOT NULL, mail TEXT, nombreusuario TEXT, password TEXT, pinacceso TEXT, esowner INTEGER);";
    public BDUsuarios(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaUsuario(Usuario e){
        EscribirDB();
        sqLiteDatabase.insert("usuarios", null, Usuariotocontent(e));
        sqLiteDatabase.close();

    }
    public void ActualizaUsuario(Usuario e){
        EscribirDB();
        sqLiteDatabase.update("usuarios", Usuariotocontent(e), "idusuario=" + e.getIdusuario().toString(), null);
        sqLiteDatabase.close();

    }

    public void BorraUsuario(Usuario u) {
        EscribirDB();
        sqLiteDatabase.delete("usuarios",  "idusuario=" + u.getIdusuario().toString(), null);
        sqLiteDatabase.close();
    }

    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }

    public static ArrayList<Usuario> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<Usuario> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            Usuario e = new Usuario();
            e.setIdusuario(cur.getInt(cur.getColumnIndexOrThrow("idusuario")));
            e.setMail(cur.getString(cur.getColumnIndexOrThrow("mail")));
            e.setNombreusuario(cur.getString(cur.getColumnIndexOrThrow("nombreusuario")));
            e.setPassword(cur.getString(cur.getColumnIndexOrThrow("password")));
            e.setPinacceso(cur.getString(cur.getColumnIndexOrThrow("pinacceso")));


            if(cur.getInt(cur.getColumnIndexOrThrow("esowner")) == 1){
                e.setEsOwner(true);
            }else{
                e.setEsOwner(false);
            }
            lista.add(e);
        }
        return lista;
    }

    public Cursor getUsuarioscursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idusuario, mail, nombreusuario, password, pinacceso, esowner FROM usuarios";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    public Cursor getUsuarioCursorPorID(Integer id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idusuario, mail, nombreusuario, password, pinacceso, esowner FROM usuarios where idusuario = " + id;
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    private ContentValues Usuariotocontent(Usuario e){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idusuario", e.getIdusuario());
        contentValues.put("mail", e.getMail());
        contentValues.put("nombreusuario", e.getNombreusuario());
        contentValues.put("password", e.getPassword());
        contentValues.put("pinacceso", e.getPinacceso());
        if(e.getEsowner() == true){
            contentValues.put("esowner", 1);
        }else {
            contentValues.put("esowner", 0);
        }

        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
