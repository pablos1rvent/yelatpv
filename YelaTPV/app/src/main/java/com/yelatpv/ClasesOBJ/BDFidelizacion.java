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

public class BDFidelizacion extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    String crear_tabla = "CREATE TABLE IF NOT EXISTS fidelizacion(idfidelizacion INTEGER PRIMARY KEY NOT NULL, codigo TEXT, modo_fidelizacion INTEGER, cantidad_fidelizacion REAL);";
    public BDFidelizacion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaFidelizacion(Fidelizacion f){
        EscribirDB();
        sqLiteDatabase.insert("fidelizacion", null, Fidelizaciontocontent(f));
        sqLiteDatabase.close();

    }
    public void ActualizaFidelizacion(Fidelizacion f){
        EscribirDB();
        sqLiteDatabase.update("fidelizacion", Fidelizaciontocontent(f), "idfidelizacion=" + f.getIdfidelizacion().toString(), null);
        sqLiteDatabase.close();

    }
    public void BorraFidelizacion(Fidelizacion f){
        EscribirDB();
        sqLiteDatabase.delete("fidelizacion", "idfidelizacion=" + f.getIdfidelizacion().toString(), null);
        sqLiteDatabase.close();
    }
    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }


    public static ArrayList<Fidelizacion> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<Fidelizacion> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            Fidelizacion f = new Fidelizacion();
            f.setIdfidelizacion(cur.getString(cur.getColumnIndexOrThrow("idfidelizacion")));
            f.setCodigo(cur.getString(cur.getColumnIndexOrThrow("codigo")));
            f.setModo_fidelizacion(cur.getInt(cur.getColumnIndexOrThrow("modo_fidelizacion")));
            f.setCantidad_fidelizacion(cur.getFloat(cur.getColumnIndexOrThrow("cantidad_fidelizacion")));
            lista.add(f);
        }
        return lista;
    }


    public Cursor getFidelizacionCursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idfidelizacion, codigo, modo_fidelizacion, cantidad_fidelizacion FROM fidelizacion";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    public Cursor getCursorFidelizacionporID(String id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idfidelizacion, codigo, modo_fidelizacion, cantidad_fidelizacion FROM fidelizacion where idfidelizacion = " + id;
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorUltimoInsertado(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idfidelizacion, codigo, modo_fidelizacion, cantidad_fidelizacion FROM fidelizacion ORDER BY idfidelizacion DESC LIMIT 1";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorFidelizacionporCodigo(String id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idfidelizacion, codigo, modo_fidelizacion, cantidad_fidelizacion FROM fidelizacion where codigo = '" + id + "'";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    private ContentValues Fidelizaciontocontent(Fidelizacion f){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idfidelizacion", f.getIdfidelizacion());
        contentValues.put("codigo", f.getCodigo());
        contentValues.put("modo_fidelizacion", f.getModo_fidelizacion());
        contentValues.put("cantidad_fidelizacion", f.getCantidad_fidelizacion());
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
