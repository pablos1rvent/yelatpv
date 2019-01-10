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

public class BDRelacionTicketProducto extends SQLiteOpenHelper {



    private SQLiteDatabase sqLiteDatabase;

    String crear_tabla = "CREATE TABLE IF NOT EXISTS relacionticketproducto(idrelacionticketproducto INTEGER PRIMARY KEY NOT NULL, idticket INTEGER, idproducto INTEGER, unidades INTEGER, precioxproducto REAL, porcentajeiva REAL);";
    public BDRelacionTicketProducto(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaRelacion(RelacionTicketProducto rtp){
        EscribirDB();
        sqLiteDatabase.insert("relacionticketproducto", null, RelactionTicketProductotocontent(rtp));
        sqLiteDatabase.close();

    }
    public void BorraRelacionPorIDProd(Integer idproducto){
        EscribirDB();
        String sql_borrado = "DELETE FROM relacionticketproducto WHERE idproducto = " + idproducto;
        sqLiteDatabase.execSQL(sql_borrado);
        sqLiteDatabase.close();

    }

    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }

    public static ArrayList<RelacionTicketProducto> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<RelacionTicketProducto> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            RelacionTicketProducto rtp = new RelacionTicketProducto();
            rtp.setIdrelacionticketproducto(cur.getInt(cur.getColumnIndexOrThrow("idrelacionticketproducto")));
            rtp.setIdticket(cur.getInt(cur.getColumnIndexOrThrow("idticket")));
            rtp.setIdproducto(cur.getInt(cur.getColumnIndexOrThrow("idproducto")));
            rtp.setUnidades(cur.getInt(cur.getColumnIndexOrThrow("unidades")));
            rtp.setPrecioxproducto(cur.getFloat(cur.getColumnIndexOrThrow("precioxproducto")));
            rtp.setPorcentajeiva(cur.getFloat(cur.getColumnIndexOrThrow("porcentajeiva")));
            lista.add(rtp);
        }
        return lista;
    }
    /*
     Integer idrelacionticketproducto;
     Integer idticket;
     Integer idproducto;
     Integer unidades;
     Float precioxproducto;
     Float porcentajeiva;

      */
    public Cursor getRelacionesCursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idrelacionticketproducto, idticket, idproducto, unidades, precioxproducto, porcentajeiva FROM relacionticketproducto ORDER BY idrelacionticketproducto ASC";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    public Cursor getCursorRelacionPorID(Integer id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idrelacionticketproducto, idticket, idproducto, unidades, precioxproducto, porcentajeiva FROM relacionticketproducto where idrelacionticketproducto = " + id + " ORDER BY idrelacionticketproducto ASC";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorPorIDTicket(String id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idrelacionticketproducto, idticket, idproducto, unidades, precioxproducto, porcentajeiva FROM relacionticketproducto where idticket = " + id + " ORDER BY idrelacionticketproducto ASC";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    private ContentValues RelactionTicketProductotocontent(RelacionTicketProducto rtp){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idrelacionticketproducto", rtp.getIdrelacionticketproducto());
        contentValues.put("idticket", rtp.getIdticket());
        contentValues.put("idproducto", rtp.getIdproducto());
        contentValues.put("unidades", rtp.getUnidades());
        contentValues.put("precioxproducto", rtp.getPrecioxproducto());
        contentValues.put("porcentajeiva", rtp.getPorcentajeiva());
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
