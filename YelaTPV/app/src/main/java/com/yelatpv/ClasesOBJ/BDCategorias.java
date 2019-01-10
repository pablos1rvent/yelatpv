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

public class BDCategorias extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;


    String crear_tabla = "CREATE TABLE IF NOT EXISTS categorias(idcategoria INTEGER PRIMARY KEY NOT NULL, idpadre INTEGER, idempresa INTEGER, nombrecategoria TEXT, activo INTEGER, color TEXT, imagen TEXT);";
    public BDCategorias(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaCategoria(Categoria c){
        EscribirDB();
        sqLiteDatabase.insert("categorias", null, Categoriatocontent(c));
        sqLiteDatabase.close();

    }
    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }

    public static ArrayList<Categoria> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<Categoria> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            Categoria c = new Categoria();
            c.setIdcategoria(cur.getInt(cur.getColumnIndexOrThrow("idcategoria")));
            c.setIdpadre(cur.getInt(cur.getColumnIndexOrThrow("idpadre")));
            c.setIdempresa(cur.getInt(cur.getColumnIndexOrThrow("idempresa")));
            c.setNombrecategoria(cur.getString(cur.getColumnIndexOrThrow("nombrecategoria")));
            c.setActivo(cur.getInt(cur.getColumnIndexOrThrow("activo")));
            c.setColor(cur.getString(cur.getColumnIndexOrThrow("color")));
            c.setImagen(cur.getString(cur.getColumnIndexOrThrow("imagen")));
            lista.add(c);
        }
        return lista;
    }

    public Cursor getCategoriasCursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idcategoria, idpadre, idempresa, nombrecategoria, activo, color, imagen FROM categorias";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    public Cursor getCategoriaporID(Integer id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idcategoria, idpadre, idempresa, nombrecategoria,activo, color, imagen FROM categorias where idcategoria = " + id;
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    private ContentValues Categoriatocontent(Categoria c){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idcategoria", c.getIdcategoria());
        contentValues.put("idpadre", c.getIdpadre());
        contentValues.put("idempresa", c.getIdempresa());
        contentValues.put("nombrecategoria", c.getNombrecategoria());
        contentValues.put("activo", c.getActivo());
        contentValues.put("color", c.getColor());
        contentValues.put("imagen", c.getImagen());
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
