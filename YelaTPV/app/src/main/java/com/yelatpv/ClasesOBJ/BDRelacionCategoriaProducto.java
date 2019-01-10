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

public class BDRelacionCategoriaProducto extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    String crear_tabla = "CREATE TABLE IF NOT EXISTS relacioncategoriaproducto(idrelacion INTEGER PRIMARY KEY NOT NULL, idcategoria INTEGER, idproducto INTEGER);";
    public BDRelacionCategoriaProducto(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaRelacion(RelacionCategoriaProducto rcp){
        EscribirDB();
        sqLiteDatabase.insert("relacioncategoriaproducto", null, RelacionCategoriaProductotocontent(rcp));
        sqLiteDatabase.close();

    }
    public void BorraRelacionPorIDProdyCat(String idproducto){
        EscribirDB();
        String sql_borrado = "DELETE FROM relacioncategoriaproducto WHERE idproducto = " + idproducto;
        sqLiteDatabase.execSQL(sql_borrado);
        sqLiteDatabase.close();

    }
    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }

    public static ArrayList<RelacionCategoriaProducto> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<RelacionCategoriaProducto> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            RelacionCategoriaProducto rcp = new RelacionCategoriaProducto();
            rcp.setIdcategoria(cur.getInt(cur.getColumnIndexOrThrow("idcategoria")));
            rcp.setIdproducto(cur.getInt(cur.getColumnIndexOrThrow("idproducto")));
            lista.add(rcp);
        }
        return lista;
    }

    public Cursor getRelacionesCursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idrelacion, idcategoria, idproducto FROM relacioncategoriaproducto ORDER BY idrelacion ASC";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    public Cursor getCursorRelacionPorID(Integer id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idrelacion, idcategoria, idproducto FROM relacioncategoriaproducto where idrelacion = " + id + " ORDER BY idrelacion ASC";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorCategoriasPorProducto(Integer idcategoria, String idproducto){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idrelacion, idcategoria, idproducto FROM relacioncategoriaproducto where idproducto = " + idproducto + " and idcategoria = " + idcategoria+ " ORDER BY idrelacion ASC";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorRelacionesPorIDCategoria(Integer idcategoria){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idrelacion, idcategoria, idproducto FROM relacioncategoriaproducto where idcategoria = " + idcategoria+ " ORDER BY idrelacion ASC";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    private ContentValues RelacionCategoriaProductotocontent(RelacionCategoriaProducto rcp){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idrelacion", rcp.getIdrelacion());
        contentValues.put("idcategoria", rcp.getIdcategoria());
        contentValues.put("idproducto", rcp.getIdproducto());
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
