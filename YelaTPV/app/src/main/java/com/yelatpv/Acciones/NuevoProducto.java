package com.yelatpv.Acciones;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.yelatpv.ClasesOBJ.BDCategorias;
import com.yelatpv.ClasesOBJ.BDEmpresa;
import com.yelatpv.ClasesOBJ.BDProductos;
import com.yelatpv.ClasesOBJ.BDRelacionCategoriaProducto;
import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.ClasesOBJ.Empresa;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.RelacionCategoriaProducto;
import com.yelatpv.Principal.Home;
import com.yelatpv.Principal.MultiSelectionSpinner;
import com.yelatpv.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NuevoProducto extends AppCompatActivity{

    private static final int RESULT_LOAD_IMAGE = 5551;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    EditText referenciaunica;
    EditText barcode;
    EditText nombreproducto;
    EditText descripcionproducto;
    EditText preciosinimpuestos;
    EditText porcentajeimpuestos;
    EditText marca;
    EditText stock;
    TextView txttitulo;
    EditText porcentajeiva;
    Button escaner_barcode;
    MultiSelectionSpinner spinner;
    List <String> categorias_nombres;
    List <Integer> id_categorias;
    String id_producto;
    boolean modo_edicion = false;
    boolean modo_sin_categorias = false;
    Bitmap foto_actual;
    ImageView imagen_prod;
    Boolean ivaGeneral = false;
    Boolean imagen_setted = false;

    Float porcentajeIvaGeneral = 0f;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }


    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == -1) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            foto_actual = photo;
            imagen_setted = true;
            imagen_prod.setImageBitmap(photo);

        }
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");
                imagen_setted = true;
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                imagen_prod.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                foto_actual = bitmap;
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                imagen_setted = true;
                imgPath = getRealPathFromURI(selectedImage);
                destination = new File(imgPath.toString());
                imagen_prod.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 0) {
            if (resultCode == -1) {
                String contentss = data.getStringExtra("SCAN_RESULT");
               barcode.setText(contentss);
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_producto);

        BDEmpresa bdempresa = new BDEmpresa(this, "Empresa", null, 1);
        Cursor empresas_activas = bdempresa.getEmpresaCursor();
        ArrayList<Empresa> empresa = bdempresa.devuelveArrayListPorCursor(empresas_activas);
        Empresa e = empresa.get(0);
        spinner = (MultiSelectionSpinner)findViewById(R.id.categoriaseleccion);
        referenciaunica = (EditText) this.findViewById(R.id.referenciaunica);
        barcode = (EditText) this.findViewById(R.id.barcode);
        nombreproducto = (EditText) this.findViewById(R.id.nombreproducto);
        descripcionproducto = (EditText) this.findViewById(R.id.descripcionproducto);
        preciosinimpuestos = (EditText) this.findViewById(R.id.preciosinimpuestos);
        porcentajeimpuestos = (EditText) this.findViewById(R.id.porcentajeimpuestos);
        marca = (EditText) this.findViewById(R.id.marca);
        stock = (EditText) this.findViewById(R.id.stock);
        txttitulo = (TextView) this.findViewById(R.id.txttitulo);


        porcentajeiva = this.findViewById(R.id.porcentajeimpuestos);
        ivaGeneral = e.getIva_general();
        porcentajeIvaGeneral = e.getIva();
        if(ivaGeneral){
            porcentajeiva.setVisibility(View.GONE);
        }else{
            porcentajeiva.setVisibility(View.VISIBLE);
        }



        escaner_barcode = (Button)this.findViewById(R.id.barcodescanner);
        escaner_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        PackageManager pm = getPackageManager();
                        int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
                        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                            Intent intent2 = new Intent(NuevoProducto.this,CaptureActivity.class);
                            intent2.setAction("com.google.zxing.client.android.SCAN");
                            intent2.putExtra("SAVE_HISTORY", false);
                            startActivityForResult(intent2, 0);
                        } else{
                            Toast.makeText(getApplicationContext(), "Camera Permission error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else{
                    Intent intent2 = new Intent(NuevoProducto.this,CaptureActivity.class);
                    intent2.setAction("com.google.zxing.client.android.SCAN");
                    intent2.putExtra("SAVE_HISTORY", false);
                    startActivityForResult(intent2, 0);
                }


            }
        });
        imagen_prod = findViewById(R.id.imagen_prod);
        ImageButton hacerfoto = (ImageButton) findViewById(R.id.hacerfoto);
        hacerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        Button selecfoto = findViewById(R.id.selectimage);
        selecfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            id_producto = (String) bd.get("idproducto");
            modo_edicion = true;
            txttitulo.setText("Editar producto");
            BDProductos bdProductos = new BDProductos(this, "productos", null, 1);
            ArrayList<Producto> listaprods_unico = bdProductos.devuelveArrayListPorCursor(bdProductos.getCursorProductoporID(id_producto));
            Producto prod_edicion = listaprods_unico.get(0);
            referenciaunica.setText(prod_edicion.getReferenciaunica());
            barcode.setText(prod_edicion.getBarcode());
            nombreproducto.setText(prod_edicion.getNombreproducto());
            descripcionproducto.setText(prod_edicion.getDescripcionproducto());
            preciosinimpuestos.setText(prod_edicion.getPreciosinimpuestos().toString());
            porcentajeimpuestos.setText(prod_edicion.getPorcentajeimpuestos().toString());
            marca.setText(prod_edicion.getMarca());
            stock.setText(prod_edicion.getStock().toString());
            foto_actual = StringToBitMap(prod_edicion.getImagen());
            imagen_prod.setImageBitmap(StringToBitMap(prod_edicion.getImagen()));
        }




        BDRelacionCategoriaProducto bdrelacioncatprod = new BDRelacionCategoriaProducto(this, "relacioncategoriaproducto", null, 1);
        BDCategorias bdCategorias = new BDCategorias(this, "categorias", null, 1);

        ArrayList<Categoria> categoriaslista = bdCategorias.devuelveArrayListPorCursor(bdCategorias.getCategoriasCursor());

        categorias_nombres= new ArrayList<String>();
        id_categorias = new ArrayList<Integer>();

        for (int i = 0; i < categoriaslista.size(); i++) {
            id_categorias.add(categoriaslista.get(i).getIdcategoria());
            categorias_nombres.add(categoriaslista.get(i).getNombrecategoria());
        }
        if(categoriaslista.size() != 0){
            spinner.setItems(categorias_nombres);
        }else{
            modo_sin_categorias = true;
        }


        ArrayList<Integer> elegidos = new ArrayList<>();
        if(modo_edicion){
            for (int i = 0; i < categoriaslista.size(); i++) {
                ArrayList<RelacionCategoriaProducto> cur_aux = bdrelacioncatprod.devuelveArrayListPorCursor(bdrelacioncatprod.getCursorCategoriasPorProducto(categoriaslista.get(i).getIdcategoria(), id_producto));
                if(cur_aux.size() > 0){
                    elegidos.add(i);
                }
            }
            if(categoriaslista.size() > 0){
                spinner.setSelection(elegidos);
            }

        }







    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    private void selectImage() {

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_PERMISSION_CODE);
        } else {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        }



    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nuevo_producto_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BDProductos bdProductos = new BDProductos(this, "productos", null, 1);
        BDRelacionCategoriaProducto bdrelacioncatprod = new BDRelacionCategoriaProducto(this, "relacioncategoriaproducto", null, 1);
        switch(item.getItemId()){
            case R.id.add_boton_producto:

                if(!Home.permisos_administracion){
                    Toast.makeText(this, "No tienes permisos de Administración", Toast.LENGTH_SHORT).show();
                }else{
                    Producto p = new Producto();
                    Boolean valido = true;

                    String referenciaunica_str = referenciaunica.getText().toString();
                    String barcode_str = barcode.getText().toString();
                    String nombreproducto_str = nombreproducto.getText().toString();
                    String descripcionproducto_str = descripcionproducto.getText().toString();
                    String preciosinimpuestos_str = preciosinimpuestos.getText().toString();
                    String marca_str = marca.getText().toString();
                    String stock_str = stock.getText().toString();



                    p.setDescripcionproducto(descripcionproducto_str);
                    p.setMarca(marca_str);

                    if(barcode_str.equals("")){
                        valido = false;
                    }else{
                        p.setBarcode(barcode_str);
                    }


                    if (!porcentajeiva.getText().toString().equals("")) {
                        p.setPorcentajeimpuestos(Float.parseFloat(porcentajeiva.getText().toString()));
                    }
                    if(imagen_setted){
                        p.setImagen(getEncoded64ImageStringFromBitmap(foto_actual));
                    }else{
                        p.setImagen("nosetted");
                    }
                    if(referenciaunica_str.equals("")){
                        valido = false;
                    }else{
                        p.setReferenciaunica(referenciaunica_str);
                    }
                    if(nombreproducto_str.equals("")){
                        valido = false;
                    }else{
                        p.setNombreproducto(nombreproducto_str);
                    }
                    if(preciosinimpuestos_str.equals("")){
                        valido = false;
                    }else{
                        p.setPreciosinimpuestos(Float.parseFloat(preciosinimpuestos_str));
                    }

                    if(stock_str.equals("")){
                        valido = false;
                    }else{
                        p.setStock(Integer.parseInt(stock_str));
                    }
                    if(valido){
                        if(modo_edicion) {
                            p.setIdproducto(Integer.parseInt(id_producto));
                            bdProductos.ActualizaProducto(p);
                            bdrelacioncatprod.BorraRelacionPorIDProdyCat(id_producto);

                            if(!modo_sin_categorias){
                                List<Integer> elegidos = spinner.getSelectedIndicies();
                                for (int i = 0; i < elegidos.size(); i++) {
                                    RelacionCategoriaProducto rcp = new RelacionCategoriaProducto(null, id_categorias.get(elegidos.get(i)), Integer.parseInt(id_producto));
                                    bdrelacioncatprod.InsertaRelacion(rcp);
                                }
                            }
                        }else{
                            bdProductos.InsertaProducto(p);
                            ArrayList<Producto> listaprods_ultimo = BDProductos.devuelveArrayListPorCursor(bdProductos.getCursorUltimoInsertado());
                            Producto ultimo_insertado = listaprods_ultimo.get(0);
                            if(!modo_sin_categorias){
                                List<Integer> elegidos = spinner.getSelectedIndicies();
                                for (int i = 0; i < elegidos.size(); i++) {
                                    RelacionCategoriaProducto rcp = new RelacionCategoriaProducto(null, id_categorias.get(elegidos.get(i)), ultimo_insertado.getIdproducto());
                                    bdrelacioncatprod.InsertaRelacion(rcp);
                                }
                            }
                        }

                        finish();
                        super.onBackPressed();

                    }else{
                        Toast.makeText(this, "Inserta los campos obligatorios, por favor", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.cancel_boton_producto:


                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
