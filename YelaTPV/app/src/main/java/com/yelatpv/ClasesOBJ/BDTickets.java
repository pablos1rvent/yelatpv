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

public class BDTickets extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    String crear_tabla = "CREATE TABLE IF NOT EXISTS tickets(idticket INTEGER PRIMARY KEY NOT NULL, fecha_ticket TEXT, preciototal REAL, codigo_descuento TEXT, tipopago INTEGER);";
    public BDTickets(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crear_tabla);
    }

    public void InsertaTicket(Tickets t){
        EscribirDB();
        sqLiteDatabase.insert("tickets", null, Tickettocontent(t));
        sqLiteDatabase.close();

    }
    public void ActualizaProducto(Tickets t){
        EscribirDB();
        sqLiteDatabase.update("tickets", Tickettocontent(t), "idticket=" + t.getIdticket().toString(), null);
        sqLiteDatabase.close();

    }
    private void EscribirDB(){
        sqLiteDatabase = this.getWritableDatabase();
    }
    private void LeerDB(){
        sqLiteDatabase =  this.getReadableDatabase();
    }

    public static ArrayList<Tickets> devuelveArrayListPorCursor(Cursor cur){
        ArrayList<Tickets> lista = new ArrayList<>();
        while (cur.moveToNext()) {
            Tickets t = new Tickets();
            t.setIdticket(cur.getString(cur.getColumnIndexOrThrow("idticket")));
            t.setFecha_ticket(cur.getString(cur.getColumnIndexOrThrow("fecha_ticket")));
            t.setPreciototal(cur.getFloat(cur.getColumnIndexOrThrow("preciototal")));
            t.setCodigo_descuento(cur.getString(cur.getColumnIndexOrThrow("codigo_descuento")));
            t.setTipoPago(cur.getInt(cur.getColumnIndexOrThrow("tipopago")));
            lista.add(t);
        }
        return lista;
    }

    public Cursor getTicketsCursor(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idticket,fecha_ticket, preciototal, codigo_descuento, tipopago FROM tickets";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }

    public Cursor getCursorTicketporID(String id){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idticket,fecha_ticket, preciototal, codigo_descuento, tipopago FROM tickets where idticket = " + id;
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    public Cursor getCursorUltimoInsertado(){
        LeerDB();
        Cursor cursor = null;
        String sentencia = "SELECT idticket,fecha_ticket, preciototal, codigo_descuento, tipopago FROM tickets ORDER BY idticket DESC LIMIT 1";
        cursor = sqLiteDatabase.rawQuery(sentencia,null);
        return cursor;
    }
    private ContentValues Tickettocontent(Tickets t){
        ContentValues contentValues = new ContentValues();
        contentValues.put("idticket", t.getIdticket());
        contentValues.put("fecha_ticket", t.getFecha_ticket());
        contentValues.put("preciototal", t.getPreciototal());
        contentValues.put("codigo_descuento", t.getCodigo_descuento());
        contentValues.put("tipopago", t.getTipoPago());
        return contentValues;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
