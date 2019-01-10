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

public class BDProductos extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    String crear_tabla = "CREATE TABLE IF NOT EXISTS productos(idproducto INTEGER PRIMARY KEY NOT NULL, nombreproducto TEXT, descripcionproducto TEXT, preciosinimpuestos REAL, porcentajeimpuestos REAL, marca TEXT, referenciaunica TEXT, barcode TEXT, stock INTEGER, imagen TEXT);";
    public BDProductos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaProducto(Producto p){
        EscribirDB();
        sqLiteDatabase.insert("productos", null, Productotocontent(p));
        sqLiteDatabase.close();

    }
    public void ActualizaProducto(Producto p){
        EscribirDB();
        sqLiteDatabase.update("productos", Productotocontent(p), "idproducto=" + p.getIdproducto().toString(), null);
        sqLiteDatabase.close();
    }

    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }

    public static ArrayList<Producto> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<Producto> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            Producto p = new Producto();
            p.setIdproducto(cur.getInt(cur.getColumnIndexOrThrow("idproducto")));
            p.setNombreproducto(cur.getString(cur.getColumnIndexOrThrow("nombreproducto")));
            p.setDescripcionproducto(cur.getString(cur.getColumnIndexOrThrow("descripcionproducto")));
            p.setPreciosinimpuestos(cur.getFloat(cur.getColumnIndexOrThrow("preciosinimpuestos")));
            p.setPorcentajeimpuestos(cur.getFloat(cur.getColumnIndexOrThrow("porcentajeimpuestos")));
            p.setMarca(cur.getString(cur.getColumnIndexOrThrow("marca")));
            p.setReferenciaunica(cur.getString(cur.getColumnIndexOrThrow("referenciaunica")));
            p.setBarcode(cur.getString(cur.getColumnIndexOrThrow("barcode")));
            p.setStock(cur.getInt(cur.getColumnIndexOrThrow("stock")));
            p.setImagen(cur.getString(cur.getColumnIndexOrThrow("imagen")));
            lista.add(p);
        }
        return lista;
    }

    public Cursor getProductosCursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idproducto, nombreproducto, descripcionproducto, preciosinimpuestos, porcentajeimpuestos, marca, referenciaunica, barcode, stock, imagen FROM productos";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getProductosCursorPorCategoria(Integer idcategoria) {
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idproducto, nombreproducto, descripcionproducto, preciosinimpuestos, porcentajeimpuestos, marca, referenciaunica, barcode, stock, imagen FROM productos WHERE id";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    public Cursor getCursorProductoporID(String id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idproducto, nombreproducto, descripcionproducto, preciosinimpuestos, porcentajeimpuestos, marca, referenciaunica, barcode, stock, imagen FROM productos where idproducto = " + id;
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorUltimoInsertado(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idproducto, nombreproducto, descripcionproducto, preciosinimpuestos, porcentajeimpuestos, marca, referenciaunica, barcode, stock, imagen FROM productos ORDER BY idproducto DESC LIMIT 1";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorProductoporbarcode(String barcode){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idproducto, nombreproducto, descripcionproducto, preciosinimpuestos, porcentajeimpuestos, marca, referenciaunica, barcode, stock, imagen FROM productos where barcode = " + barcode;
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    private ContentValues Productotocontent(Producto p){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idproducto", p.getIdproducto());
        contentValues.put("nombreproducto", p.getNombreproducto());
        contentValues.put("descripcionproducto", p.getDescripcionproducto());
        contentValues.put("preciosinimpuestos", p.getPreciosinimpuestos());
        contentValues.put("porcentajeimpuestos", p.getPorcentajeimpuestos());
        contentValues.put("marca", p.getMarca());
        contentValues.put("referenciaunica", p.getReferenciaunica());
        contentValues.put("barcode", p.getBarcode());
        contentValues.put("stock", p.getStock());
        contentValues.put("imagen", p.getImagen());
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
