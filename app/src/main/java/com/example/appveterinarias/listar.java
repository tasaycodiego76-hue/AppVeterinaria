package com.example.appveterinarias;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class listar extends AppCompatActivity {

    ListView lstMascotas; //Contenedor donde sera renderizado los datos
    private final String URL = "http://192.168.18.186:3000/mascotas"; //Constante

    RequestQueue requestQueue;
    private void loadUI(){
        lstMascotas = findViewById(R.id.lstMascotas);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        getData();
    }

    private void getData(){
        //1. Habilitar el canal de comunicacion
        requestQueue = Volley.newRequestQueue(this);

        //2. Que tipo de datos espero obtener?
        //Opciones => objeto JSON, (Arreglo), Text, Binario (JPG)
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("Datos recibidos:", jsonArray.toString());
                        renderData(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error WS:", volleyError.toString());
                    }
                }

        );

        //3.Enviar la solicitud
        requestQueue.add(jsonArrayRequest);


    }

    private void renderData(JSONArray vehiculos){
        try{
            ArrayAdapter arrayAdapter;
            ArrayList<String> listaVehiculos = new ArrayList<>();

            for (int i = 0; i < vehiculos.length(); i++){
                JSONObject jsonObject = vehiculos.getJSONObject(i);
                listaVehiculos.add(jsonObject.getString("nombre")+ " " + jsonObject.getString("tipo")+ "("+ jsonObject.getString("raza")+ ")"+ jsonObject.getString("color")+ ")"+ jsonObject.getString("peso")+ ")"+ jsonObject.getString("genero")+ ")");


            }

            //El adaptador recibe la lista
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
            lstMascotas.setAdapter(arrayAdapter);
        }catch (Exception error){
            Log.e("Error JSON recibido:", error.toString());
        }


    }
}