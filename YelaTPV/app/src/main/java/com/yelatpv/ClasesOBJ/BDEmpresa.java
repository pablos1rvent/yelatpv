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

public class BDEmpresa extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    String crear_tabla = "CREATE TABLE IF NOT EXISTS empresa(nifcif VARCHAR PRIMARY KEY NOT NULL, pais TEXT, razonsocial TEXT, direccion TEXT, codigopostal TEXT, provincia TEXT,ciudad TEXT, telefono TEXT, ivageneral REAL, iva REAL);";
    public BDEmpresa(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaEmpresa(Empresa e){
        EscribirDB();
        sqLiteDatabase.insert("empresa", null, EmpresatoContent(e));
        sqLiteDatabase.close();

    }
    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }

    public static ArrayList<Empresa> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<Empresa> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            Empresa e = new Empresa();
            e.setNifcif(cur.getString(cur.getColumnIndexOrThrow("nifcif")));
            e.setPais(cur.getString(cur.getColumnIndexOrThrow("pais")));
            e.setRazonsocial(cur.getString(cur.getColumnIndexOrThrow("razonsocial")));
            e.setDireccion(cur.getString(cur.getColumnIndexOrThrow("direccion")));
            e.setNifcif(cur.getString(cur.getColumnIndexOrThrow("direccion")));
            e.setCodigopostal(cur.getString(cur.getColumnIndexOrThrow("codigopostal")));
            e.setProvincia(cur.getString(cur.getColumnIndexOrThrow("provincia")));
            e.setCiudad(cur.getString(cur.getColumnIndexOrThrow("ciudad")));
            e.setTelefono(cur.getString(cur.getColumnIndexOrThrow("telefono")));
            if(cur.getInt(cur.getColumnIndexOrThrow("ivageneral")) == 1){
                e.setIva_general(true);
            }else{
                e.setIva_general(false);
            }

            e.setIva(cur.getFloat(cur.getColumnIndexOrThrow("iva")));
            lista.add(e);
        }
        return lista;
    }

    public Cursor getEmpresaCursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT nifcif, pais, razonsocial, direccion, codigopostal, provincia, ciudad, telefono, ivageneral, iva FROM empresa";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);

        return cursor;
    }

    private ContentValues EmpresatoContent(Empresa e){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nifcif", e.getNifcif());
        contentValues.put("pais", e.getPais());
        contentValues.put("razonsocial", e.getRazonsocial());
        contentValues.put("direccion", e.getDireccion());
        contentValues.put("codigopostal", e.getCodigopostal());
        contentValues.put("provincia", e.getProvincia());
        contentValues.put("ciudad", e.getCiudad());
        contentValues.put("telefono", e.getTelefono());
        if(e.getIva_general() == true){
            contentValues.put("ivageneral", 1);
        }else {
            contentValues.put("ivageneral", 0);
        }

        contentValues.put("iva", e.getIva());
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
