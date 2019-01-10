package com.yelatpv.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.yelatpv.Acciones.NuevoProducto;
import com.yelatpv.Acciones.VerTicket;
import com.yelatpv.Adaptadores.CategoriasTPVAdapter;
import com.yelatpv.Adaptadores.ProductosTPVAdapter;
import com.yelatpv.ClasesOBJ.*;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;

import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TPV extends Fragment implements CategoriasTPVAdapter.ItemClickListener, ProductosTPVAdapter.ItemClickListener {

    private static final int RESULT_LOAD_IMAGE = 5551;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    private CategoriasTPVAdapter adapter1;
    private ProductosTPVAdapter adapter2;
    View view;
    ArrayList<Categoria> categorias_arr;
    ArrayList<Producto> productos_arr;
    boolean modo_sin_categorias = false;
    boolean viendo_categorias = true;
    TextView fecha_ticket;
    Map<Producto,Integer> lineas_producto_tpv = new HashMap<>();
    Button cobrar_btn;
    float precio_total = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tpv, container, false);
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        fecha_ticket = (TextView)view.findViewById(R.id.fecha_ticket);
        fecha_ticket.setText(timeStamp);
        ((Home) getActivity()).getSupportActionBar().setTitle("TPV");
        this.view = view;
        Button codigo_barras = view.findViewById(R.id.btn_codigo_barras);
        codigo_barras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        PackageManager pm = getContext().getPackageManager();
                        int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getContext().getPackageName());
                        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                            Intent intent2 = new Intent(getContext(),CaptureActivity.class);
                            intent2.setAction("com.google.zxing.client.android.SCAN");
                            intent2.putExtra("SAVE_HISTORY", false);
                            startActivityForResult(intent2, 0);
                        } else{
                            Toast.makeText(getContext(), "Camera Permission error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else{
                    Intent intent2 = new Intent(getContext(),CaptureActivity.class);
                    intent2.setAction("com.google.zxing.client.android.SCAN");
                    intent2.putExtra("SAVE_HISTORY", false);
                    startActivityForResult(intent2, 0);
                }

            }
        });
        cobrar_btn = view.findViewById(R.id.btn_cobrar);
        cobrar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

                View mView = getLayoutInflater().inflate(R.layout.formas_pago, null);

                mBuilder.setView(mView);

                final AlertDialog dialog = mBuilder.create();
                CardView tarjeta = mView.findViewById(R.id.tarjeta);
                CardView efectivo = mView.findViewById(R.id.efectivo);
                dialog.show();
                efectivo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(getContext());

                        final View mView2 = getLayoutInflater().inflate(R.layout.efectivo_cambio, null);
                        Button finalizar_ticket = mView2.findViewById(R.id.finalizar_ticket);

                        final TextView cambio_cantidad = mView2.findViewById(R.id.cambio_cantidad);
                        final TextView total_cantidad = mView2.findViewById(R.id.total_cantidad);

                        total_cantidad.setText(precio_total+"€");

                        final EditText cantidad_cambio = mView2.findViewById(R.id.cantidad_cambio);
                        cantidad_cambio.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                String cantidad_parseo = cantidad_cambio.getText().toString();
                                if(cantidad_parseo != null || cantidad_parseo != ""){
                                    try {
                                        Float cambio = Float.parseFloat(String.valueOf(cantidad_parseo));
                                        Float adeber = cambio - precio_total;
                                        if(cambio > 0){
                                            cambio_cantidad.setText(adeber.toString()+"€");
                                        }
                                    } catch (NumberFormatException e) {
                                        // Executed iff the string couldn't be parsed.
                                    }

                                }


                            }
                        });

                        final EditText codigofidelizacion = mView2.findViewById(R.id.fidelizacioncodigo);
                        codigofidelizacion.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                String codigo_fidelizacion = codigofidelizacion.getText().toString();
                                TextView cantidad_fidelizacion = mView2.findViewById(R.id.cantidad_fidelizacion);
                                TextView total_cantidad = mView2.findViewById(R.id.total_cantidad);
                                BDFidelizacion bdfidelizacion = new BDFidelizacion(getContext(), "fidelizacion", null, 1);
                                ArrayList<com.yelatpv.ClasesOBJ.Fidelizacion> fidelizacionarr = bdfidelizacion.devuelveArrayListPorCursor(bdfidelizacion.getCursorFidelizacionporCodigo(codigo_fidelizacion));


                                if(fidelizacionarr.size() > 0){
                                    Float cantidad_descuento = roundFloat(Float.parseFloat((fidelizacionarr.get(0).getCantidad_fidelizacion().toString())), 2);
                                    if(fidelizacionarr.get(0).getModo_fidelizacion() == 0){
                                        cantidad_fidelizacion.setText(cantidad_descuento+ "€");
                                        Float cantidad_flot = roundFloat(precio_total - Float.parseFloat(fidelizacionarr.get(0).getCantidad_fidelizacion().toString()), 2);
                                        total_cantidad.setText(cantidad_flot+"€");
                                    }else{
                                        cantidad_fidelizacion.setText(cantidad_descuento+ "%");
                                        Float cantidad_descuento_aux = roundFloat((precio_total*cantidad_descuento)/100, 2);
                                        total_cantidad.setText((precio_total - cantidad_descuento_aux) +"€");
                                    }

                                }else{
                                    cantidad_fidelizacion.setText("0€");
                                    total_cantidad.setText(precio_total+"€");
                                }
                            }
                        });

                        mBuilder2.setView(mView2);
                        final AlertDialog dialog2 = mBuilder2.create();
                        dialog2.show();
                        finalizar_ticket.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String resultado_ticket = finalizarTicket(0);
                                dialog2.dismiss();
                                reiniciarTPVyMostrarTicket(resultado_ticket);
                            }
                        });



                    }
                });
                tarjeta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(getContext());

                        final View mView2 = getLayoutInflater().inflate(R.layout.efectivo_cambio, null);
                        Button finalizar_ticket = mView2.findViewById(R.id.finalizar_ticket);

                        final TextView cambio_cantidad = mView2.findViewById(R.id.cambio_cantidad);
                        cambio_cantidad.setVisibility(View.GONE);

                        final TextView CAMBIOTEXT = mView2.findViewById(R.id.cambio);
                        CAMBIOTEXT.setVisibility(View.GONE);

                        final TextView total_cantidad = mView2.findViewById(R.id.total_cantidad);
                        total_cantidad.setText(precio_total+"€");

                        final EditText cantidad_cambio = mView2.findViewById(R.id.cantidad_cambio);
                        cantidad_cambio.setVisibility(View.GONE);
                        final EditText codigofidelizacion = mView2.findViewById(R.id.fidelizacioncodigo);
                        codigofidelizacion.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                String codigo_fidelizacion = codigofidelizacion.getText().toString();
                                TextView cantidad_fidelizacion = mView2.findViewById(R.id.cantidad_fidelizacion);
                                TextView total_cantidad = mView2.findViewById(R.id.total_cantidad);
                                BDFidelizacion bdfidelizacion = new BDFidelizacion(getContext(), "fidelizacion", null, 1);
                                ArrayList<com.yelatpv.ClasesOBJ.Fidelizacion> fidelizacionarr = bdfidelizacion.devuelveArrayListPorCursor(bdfidelizacion.getCursorFidelizacionporCodigo(codigo_fidelizacion));


                                if(fidelizacionarr.size() > 0){
                                    Float cantidad_descuento = Float.parseFloat((fidelizacionarr.get(0).getCantidad_fidelizacion().toString()));
                                    if(fidelizacionarr.get(0).getModo_fidelizacion() == 0){
                                        cantidad_fidelizacion.setText(cantidad_descuento+ "€");
                                        total_cantidad.setText((precio_total - Float.parseFloat(fidelizacionarr.get(0).getCantidad_fidelizacion().toString())+"€"));
                                    }else{
                                        cantidad_fidelizacion.setText(cantidad_descuento+ "%");
                                        Float cantidad_descuento_aux = (precio_total*cantidad_descuento)/100;
                                        total_cantidad.setText((precio_total - cantidad_descuento_aux) +"€");
                                    }

                                }else{
                                    cantidad_fidelizacion.setText("0€");
                                    total_cantidad.setText(precio_total+"€");
                                }
                            }
                        });

                        mBuilder2.setView(mView2);
                        final AlertDialog dialog2 = mBuilder2.create();
                        dialog2.show();
                        finalizar_ticket.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String resultado_ticket = finalizarTicket(1);
                                dialog2.dismiss();
                                reiniciarTPVyMostrarTicket(resultado_ticket);
                            }
                        });

                    }
                });
            }
        });
        ImageButton categorias_carga_btn = view.findViewById(R.id.home_categorias);
        categorias_carga_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!viendo_categorias){
                    cargarRecyclerCategorias();
                }

            }
        });
        BDCategorias bdcategorias = new BDCategorias(getActivity(), "categorias", null, 1);
        categorias_arr = bdcategorias.devuelveArrayListPorCursor(bdcategorias.getCategoriasCursor());
        if(categorias_arr.size() > 0){
            cargarRecyclerCategorias();
        }else{
            modo_sin_categorias = true;
            cargarRecyclerProductosSinCategoria();
        }
        return view;
    }

    private void reiniciarTPVyMostrarTicket(String resultado_ticket) {
        if(resultado_ticket != "0"){
            ((Home) getActivity()).cambiarFragmentDesdeOtroFragment(1);
            Intent myIntent = new Intent(getContext(), VerTicket.class);
            myIntent.putExtra("idticket", resultado_ticket);
            startActivity(myIntent);
        }
    }

    private String finalizarTicket(Integer formaPago) {
        if(lineas_producto_tpv.size() > 0){
            BDTickets bdtickets = new BDTickets(getActivity(), "tickets", null, 1);
            BDRelacionTicketProducto bdrelacionticketprod = new BDRelacionTicketProducto(getActivity(), "relacionticketproducto", null, 1);
            Tickets t = new Tickets();
            t.setFecha_ticket(new Date().toString());
            t.setPreciototal(precio_total);
            t.setCodigo_descuento(null);
            t.setTipoPago(formaPago);
            bdtickets.InsertaTicket(t);

            ArrayList<Tickets> tickets_ultimo = bdtickets.devuelveArrayListPorCursor(bdtickets.getCursorUltimoInsertado());
            String id_ultimo_ticket = tickets_ultimo.get(0).getIdticket();

            for (Map.Entry<Producto, Integer> entry : lineas_producto_tpv.entrySet())
            {
                Producto prod = entry.getKey();
                Integer cantidad_producto = entry.getValue();
                RelacionTicketProducto rtp = new RelacionTicketProducto();
                rtp.setIdticket(Integer.parseInt(id_ultimo_ticket));
                rtp.setIdproducto(prod.getIdproducto());
                rtp.setUnidades(cantidad_producto);
                rtp.setPrecioxproducto(roundFloat(prod.getPreciosinimpuestos(), 2));
                rtp.setPorcentajeiva(prod.getPorcentajeimpuestos());
                bdrelacionticketprod.InsertaRelacion(rtp);
            }
            return id_ultimo_ticket;
        }else{
            Toast.makeText(getContext(), "Inserta al menos un producto para finalizar el ticket", Toast.LENGTH_SHORT).show();
            return "0";
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == -1) {
                String contents = data.getStringExtra("SCAN_RESULT");
                buscarBarcodeProducto(contents);
            } else if (resultCode == 0) {

            }
        }
    }

    private void buscarBarcodeProducto(String contents) {
        BDProductos bdproductos = new BDProductos(getActivity(), "productos", null, 1);
        ArrayList<Producto> productos = bdproductos.devuelveArrayListPorCursor(bdproductos.getCursorProductoporbarcode(contents));
        if(productos == null){
            Toast.makeText(getContext(), "No se ha encontrado ningún producto", Toast.LENGTH_SHORT).show();
        }else{
            if(productos.size() > 1){
                for (int i = 0; i < productos.size(); i++) {
                    addLineaProducto(productos.get(i));
                }
            }else{
                addLineaProducto(productos.get(0));
            }
        }
    }

    public void cargarRecyclerCategorias(){
        TextView migas_productos = view.findViewById(R.id.migas_productos);
        migas_productos.setVisibility(View.GONE);
        if(categorias_arr.size() > 0){
            viendo_categorias = true;
            RecyclerView recyclerView = view.findViewById(R.id.categorias_horizontales);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            adapter1 = new CategoriasTPVAdapter(getActivity(), categorias_arr);
            adapter1.setClickListener(this);
            recyclerView.setAdapter(adapter1);
        }else{
            modo_sin_categorias = true;
            cargarRecyclerProductosSinCategoria();
        }

    }

    private void cargarRecyclerProductosSinCategoria() {
        TextView migas_productos = view.findViewById(R.id.migas_productos);
        migas_productos.setVisibility(View.VISIBLE);
        viendo_categorias = false;
        productos_arr = new ArrayList<>();
        BDProductos bdproductos = new BDProductos(getActivity(), "productos", null, 1);
        ArrayList<Producto> prods = bdproductos.devuelveArrayListPorCursor(bdproductos.getProductosCursor());
        for (int i = 0; i < prods.size(); i++) {
            productos_arr.add(bdproductos.devuelveArrayListPorCursor(bdproductos.getCursorProductoporID(prods.get(i).getIdproducto().toString())).get(0));
        }

        RecyclerView recyclerView = view.findViewById(R.id.categorias_horizontales);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter2 = new ProductosTPVAdapter(getActivity(), productos_arr);
        adapter2.setClickListener(this);
        recyclerView.setAdapter(adapter2);
    }

    public void cargarRecyclerProductos(Integer idcategoria){
        TextView migas_productos = view.findViewById(R.id.migas_productos);
        migas_productos.setVisibility(View.VISIBLE);
        viendo_categorias = false;
        productos_arr = new ArrayList<>();
        BDProductos bdproductos = new BDProductos(getActivity(), "productos", null, 1);
        BDRelacionCategoriaProducto bdrelacioncatprod = new BDRelacionCategoriaProducto(getActivity(), "relacioncategoriaproducto", null, 1);
        ArrayList<RelacionCategoriaProducto> relaciones = bdrelacioncatprod.devuelveArrayListPorCursor(bdrelacioncatprod.getCursorRelacionesPorIDCategoria(idcategoria + 1));
        for (int i = 0; i < relaciones.size(); i++) {
            productos_arr.add(bdproductos.devuelveArrayListPorCursor(bdproductos.getCursorProductoporID(relaciones.get(i).getIdproducto().toString())).get(0));
        }

        RecyclerView recyclerView = view.findViewById(R.id.categorias_horizontales);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter2 = new ProductosTPVAdapter(getActivity(), productos_arr);
        adapter2.setClickListener(this);
        recyclerView.setAdapter(adapter2);
    }

    private void borraLineaProducto(Integer idproducto){
        boolean existe = false;
        Producto productoaborrar = null;
        Integer cantidad = 0;
        TableRow row = (TableRow)view.findViewWithTag("prod_select_" + idproducto);
        TableLayout tl = (TableLayout) view.findViewById(R.id.lineas_pedido);
        tl.removeView(row);
        for (Map.Entry<Producto, Integer> entry : lineas_producto_tpv.entrySet())
        {
            if(idproducto == entry.getKey().getIdproducto() && !existe){
                existe = true;
                cantidad = entry.getValue();
                productoaborrar = entry.getKey();
            }
        }

        if(productoaborrar != null){
            lineas_producto_tpv.remove(productoaborrar);
            for (int i = 0; i < cantidad; i++) {
                precio_total = precio_total - productoaborrar.getPreciosinimpuestos();
                cobrar_btn.setText("COBRAR (" + precio_total + " €)");
            }
        }



    }
    public Float getPrecioConIVA(Float precio_sin_iva, Float porcentajeiva){
        return roundFloat(precio_sin_iva + (precio_sin_iva * porcentajeiva / 100), 2);
    }
    private void addLineaProducto(final Producto producto) {

        boolean existe = false;


        for (Map.Entry<Producto, Integer> entry : lineas_producto_tpv.entrySet())
        {
            if(producto.getIdproducto() == entry.getKey().getIdproducto() && !existe){
                existe = true;
                Integer valor_cantidad = entry.getValue() + 1;
                Float precio_producto = entry.getKey().getPreciosinimpuestos() * valor_cantidad;
                precio_producto = roundFloat(precio_producto, 2);
                entry.setValue(valor_cantidad);
                TextView txt_cantidad = (TextView) view.findViewWithTag("prod_" + entry.getKey().getIdproducto());
                TextView txt_totales = (TextView) view.findViewWithTag("total_" + entry.getKey().getIdproducto());
                txt_cantidad.setText(valor_cantidad.toString());
                txt_totales.setText(precio_producto.toString());
            }
        }


        if(!existe){
            if(Home.iva_general){
                producto.setPreciosinimpuestos(getPrecioConIVA(producto.getPreciosinimpuestos(), Home.iva_total));
            }else{
                producto.setPreciosinimpuestos(getPrecioConIVA(producto.getPreciosinimpuestos(), producto.getPorcentajeimpuestos()));
            }
            lineas_producto_tpv.put(producto, 1);
            TableLayout tl = (TableLayout) view.findViewById(R.id.lineas_pedido);
            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.setTag("prod_select_"+producto.getIdproducto());
            tr.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    borraLineaProducto(producto.getIdproducto());
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:

                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                    ab.setMessage("Seguro que quieres borrar esa línea de producto?").setPositiveButton("Sí", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    return false;
                }
            });
            TextView item_nombre = new TextView(getContext());
            item_nombre.setText(producto.getNombreproducto());
            item_nombre.setPadding(3,3,3,3);
            item_nombre.setTextSize(16);
            item_nombre.setWidth(200);
            tr.addView(item_nombre);

            TextView precio = new TextView(getContext());
            precio.setText(String.valueOf(roundFloat(producto.getPreciosinimpuestos(), 2)));
            precio.setPadding(3,3,3,3);
            precio.setTextSize(16);
            precio.setGravity(Gravity.RIGHT);

            tr.addView(precio);

            TextView cantidad = new TextView(getContext());
            cantidad.setText("1");
            cantidad.setTag("prod_"+producto.getIdproducto());
            cantidad.setPadding(3,3,3,3);
            cantidad.setTextSize(16);
            cantidad.setGravity(Gravity.RIGHT);

            tr.addView(cantidad);

            TextView totales = new TextView(getContext());
            totales.setText(String.valueOf(roundFloat(producto.getPreciosinimpuestos(), 2)));
            totales.setTag("total_"+producto.getIdproducto());
            totales.setPadding(3,3,3,3);
            totales.setTextSize(16);
            totales.setGravity(Gravity.RIGHT);

            tr.addView(totales);

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }


        precio_total = precio_total + roundFloat(producto.getPreciosinimpuestos(), 2);
        precio_total = roundFloat(precio_total, 2);
        cobrar_btn.setText("COBRAR (" + precio_total + " €)");

    }
    public static float roundFloat(float F, int roundTo){

        Float aux = F;
        String num = "#########.";

        for (int count = 0; count < roundTo; count++){
            num += "0";
        }

        DecimalFormat df = new DecimalFormat(num);

        df.setRoundingMode(RoundingMode.HALF_UP);

        String S = df.format(F);
        try{
            F = Float.parseFloat(S);
        }catch(NumberFormatException ex){
            return aux;
        }
        return F;
    }
    @Override
    public void onItemClick(View view, int position) {
        cargarRecyclerProductos(position);
    }

    @Override
    public void onItemClick_prods(View view, int position) {

        addLineaProducto(productos_arr.get(position));

        //cargarRecyclerCategorias();
    }


}