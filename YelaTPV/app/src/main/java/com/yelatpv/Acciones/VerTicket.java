package com.yelatpv.Acciones;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ByteBuffer;
import com.itextpdf.text.pdf.PdfWriter;
import com.yelatpv.ClasesOBJ.BDCategorias;
import com.yelatpv.ClasesOBJ.BDEmpresa;
import com.yelatpv.ClasesOBJ.BDProductos;
import com.yelatpv.ClasesOBJ.BDRelacionCategoriaProducto;
import com.yelatpv.ClasesOBJ.BDRelacionTicketProducto;
import com.yelatpv.ClasesOBJ.BDTickets;
import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.ClasesOBJ.Empresa;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.RelacionCategoriaProducto;
import com.yelatpv.ClasesOBJ.RelacionTicketProducto;
import com.yelatpv.ClasesOBJ.Tickets;
import com.yelatpv.Principal.MultiSelectionSpinner;
import com.yelatpv.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VerTicket extends AppCompatActivity{

    TextView txttitulo;
    String id_ticket;
    private void addLineaProducto(Producto producto, int udsprod) {

            TableLayout tl = (TableLayout) findViewById(R.id.lineas_pedido);
            TableRow tr = new TableRow(getApplicationContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView item_nombre = new TextView(getBaseContext());
            item_nombre.setText(producto.getNombreproducto());
            item_nombre.setPadding(3,3,3,3);
            item_nombre.setTextSize(16);
            item_nombre.setWidth(200);
            tr.addView(item_nombre);

            TextView precio = new TextView(getBaseContext());
            precio.setText(producto.getPreciosinimpuestos().toString());
            precio.setPadding(3,3,3,3);
            precio.setTextSize(16);
            precio.setGravity(Gravity.RIGHT);

            tr.addView(precio);

            TextView cantidad = new TextView(getBaseContext());
            cantidad.setText(String.valueOf(udsprod));
            cantidad.setTag("prod_"+producto.getIdproducto());
            cantidad.setPadding(3,3,3,3);
            cantidad.setTextSize(16);
            cantidad.setGravity(Gravity.RIGHT);

            tr.addView(cantidad);
            Float total_aux = udsprod * producto.getPreciosinimpuestos();
            TextView totales = new TextView(getBaseContext());
            totales.setText(total_aux.toString());
            totales.setTag("total_"+producto.getIdproducto());
            totales.setPadding(3,3,3,3);
            totales.setTextSize(16);
            totales.setGravity(Gravity.RIGHT);

            tr.addView(totales);

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verticket);
        BDEmpresa bdempresa = new BDEmpresa(this, "Empresa", null, 1);
        Empresa e = bdempresa.devuelveArrayListPorCursor(bdempresa.getEmpresaCursor()).get(0);

        txttitulo = (TextView)findViewById(R.id.txttitulo);

        Button descargar_pdf = findViewById(R.id.descargar_pdf);
        descargar_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView iv = (ScrollView) findViewById(R.id.ll1);
                Bitmap bitmap = Bitmap.createBitmap(
                        iv.getChildAt(0).getWidth(),
                        iv.getChildAt(0).getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                c.drawColor(Color.WHITE);
                iv.getChildAt(0).draw(c);

                // Do whatever you want with your bitmap
                try {
                    saveBitmap(bitmap, id_ticket);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (DocumentException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String idticket = (String) bd.get("idticket");
            id_ticket = "TPV"+idticket;
            txttitulo.setText("#TPV" + idticket);
            TextView fecha = findViewById(R.id.fecha_ticket);
            TextView forma_pago = findViewById(R.id.forma_pago);


            TextView razonsocial = findViewById(R.id.razonsocial);
            TextView direccion = findViewById(R.id.direccion);
            TextView provincia = findViewById(R.id.provincia);
            TextView codpostal = findViewById(R.id.codpostal);
            TextView telefono = findViewById(R.id.telefono);

            TextView total_pagado_ticket_final = findViewById(R.id.total_pagado_ticket_final);

            razonsocial.setText(e.getRazonsocial());
            direccion.setText(e.getDireccion());
            provincia.setText(e.getProvincia() + " - " + e.getCiudad());
            codpostal.setText(e.getCodigopostal());
            telefono.setText(e.getTelefono());


            BDTickets bdtickets = new BDTickets(getBaseContext(), "tickets", null, 1);

            ArrayList<Tickets> tickets_arr = bdtickets.devuelveArrayListPorCursor(bdtickets.getCursorTicketporID(idticket));
            Tickets t = tickets_arr.get(0);
            total_pagado_ticket_final.setText("Total pagado:" + t.getPreciototal() + "€");
            if(t.getTipoPago() == 0){
                forma_pago.setText("Efectivo");
            }else{
                forma_pago.setText("Tarjeta de crédito");
            }
            fecha.setText("Fecha: " + t.getFecha_ticket());

            BDProductos bdproductos = new BDProductos(getBaseContext(), "productos", null, 1);
            BDRelacionTicketProducto bdrelacionticketprod = new BDRelacionTicketProducto(getBaseContext(), "relacionticketproducto", null, 1);
            ArrayList<RelacionTicketProducto> relacionTicketProductos_arr = bdrelacionticketprod.devuelveArrayListPorCursor(bdrelacionticketprod.getCursorPorIDTicket(idticket));

            for (int i = 0; i < relacionTicketProductos_arr.size(); i++) {
                RelacionTicketProducto r = relacionTicketProductos_arr.get(i);
                int cantidad = r.getUnidades();
                Float precio = r.getPrecioxproducto();
                Producto prodainsertar = bdproductos.devuelveArrayListPorCursor(bdproductos.getCursorProductoporID(String.valueOf(r.getIdproducto()))).get(0);
                prodainsertar.setPreciosinimpuestos(precio);
                addLineaProducto(prodainsertar, cantidad);
            }


        }
    }
    private File destination = null;

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public void saveBitmap(Bitmap bitmap, String id_ticket) throws IOException, DocumentException {

        if(isStoragePermissionGranted()){
            destination = new File(Environment.getExternalStorageDirectory() + "/Download", id_ticket + ".pdf");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();

            Image image = Image.getInstance(byteArray);
            image.setAbsolutePosition(0, 0);

            Document document = new Document(image);
            PdfWriter.getInstance(document, new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/" + id_ticket +".pdf"));
            document.open();
            document.add(image);
            document.close();
            Toast.makeText(this, "PDF guardado en la carpeta Descargas", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.verticket_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.cerrar_ticket:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
