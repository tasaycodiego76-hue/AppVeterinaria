package com.example.appveterinarias;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Buscar extends AppCompatActivity {

    EditText edtIdBuscado, edtNombre, edtTipo, edtRaza, edtColor, edtPeso, edtGenero;
    Button btnBuscarMascota, btnActualizarMascota, btnEliminarMascota;

    private final String URL = "http://192.168.18.186:3000/mascotas/";

    RequestQueue requestQueue; // Cola de pedido

    private void loadUI(){
        edtIdBuscado = findViewById(R.id.edtIdBuscado);
        edtNombre = findViewById(R.id.edtNombreedit);
        edtTipo = findViewById(R.id.edtTipoedit);
        edtRaza = findViewById(R.id.edtRazaedit);
        edtColor = findViewById(R.id.edtColoredit);
        edtPeso = findViewById(R.id.edtPesoedit);
        edtGenero = findViewById(R.id.edtGeneroedit);

        btnBuscarMascota = findViewById(R.id.btnBuscarMascota);
        btnActualizarMascota = findViewById(R.id.btnActualizarMascota);
        btnEliminarMascota = findViewById(R.id.btnEliminarMascota);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        btnBuscarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchById();
            }
        });

        btnActualizarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUpdate();
            }
        });

        btnEliminarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });


    }//OnCreate

    private void searchById(){
        String idmascota = edtIdBuscado.getText().toString().trim();

        if (idmascota.isEmpty()){
            edtIdBuscado.setError("Escriba el ID");
            edtIdBuscado.requestFocus();
        } else {
            requestQueue = Volley.newRequestQueue(this);
            String endPoint = URL + idmascota;

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    endPoint,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("Respuesta WS:", jsonObject.toString());
                            try {
                                edtNombre.setText(jsonObject.getString("nombre"));
                                edtTipo.setText(jsonObject.getString("tipo"));
                                edtRaza.setText(jsonObject.getString("raza"));
                                edtColor.setText(jsonObject.getString("color"));
                                edtPeso.setText(jsonObject.getString("peso"));
                                edtGenero.setText(jsonObject.getString("genero"));
                            } catch (JSONException e) {
                                Log.e("Error JSON: ", e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            formClear();
                            edtIdBuscado.requestFocus();
                            Toast.makeText(getApplicationContext(),
                                    "No existe la mascota",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );

            requestQueue.add(jsonArrayRequest);
        }
    }

    private void updatePet(){
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("nombre", edtNombre.getText().toString().trim());
            jsonObject.put("tipo", edtTipo.getText().toString().trim());
            jsonObject.put("raza", edtRaza.getText().toString().trim());
            jsonObject.put("color", edtColor.getText().toString().trim());
            jsonObject.put("peso", edtPeso.getText().toString().trim());
            jsonObject.put("genero", edtGenero.getText().toString().trim());
        } catch (JSONException e) {
            Log.e("Error JSON:", e.toString());
        }

        String endPoint = URL + edtIdBuscado.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                endPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),
                                "Actualizado correctamente", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(),
                                "No se pudo actualizar", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }


    private void deletePet(){
        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + edtIdBuscado.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        formClear();
                        Toast.makeText(getApplicationContext(), "Eliminacion Exitosa", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "No se pudo Eliminar", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void confirmDelete(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Eliminacion Mascota");
        dialog.setMessage("¿Procedemos con la Eliminacion?");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Cancelar", null);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePet();
            }
        });
        dialog.show();
    }

    private void formClear(){
        edtNombre.setText(null);
        edtTipo.setText(null);
        edtRaza.setText(null);
        edtColor.setText(null);
        edtPeso.setText(null);
        edtGenero.setText(null);

    }
    private void confirmUpdate(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("");
        dialog.setMessage("¿Procedemos con la actualización?");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Cancelar", null);

        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updatePet();
            }
        });

        dialog.show();
    }

}