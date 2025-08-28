package com.example.appveterinarias;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
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

import org.json.JSONObject;

public class registros extends AppCompatActivity {

    EditText edtNombre, edtTipo, edtRaza, edtColor, edtPeso, edtGenero;
    Button btnGuardar;
    private final String URL = "http://192.168.18.186:3000/mascotas"; //Constante

    RequestQueue requestQueue;

    private void loadUI(){

        edtNombre = findViewById(R.id.edtNombre);
        edtTipo = findViewById(R.id.edtTipo);
        edtRaza = findViewById(R.id.edtRaza);
        edtColor = findViewById(R.id.edtColor);
        edtPeso = findViewById(R.id.edtPeso);
        edtGenero = findViewById(R.id.edtGenero);
        btnGuardar = findViewById(R.id.btnGuardar);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registros);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataWS();
            }
        });


    }
    private void sendDataWS(){
        //1.Habilitar servicio
        requestQueue = Volley.newRequestQueue(this);

        //1.5 Para que este ENDPINT / POST funcione, debemos rpeparar un JSON con los datos
        JSONObject jsonObject = new JSONObject();

        try{
            //El JSON recibe los datos de las cajas (EditText)
            jsonObject.put("nombre", edtNombre.getText().toString());
            jsonObject.put("tipo", edtTipo.getText().toString());
            jsonObject.put("raza", edtRaza.getText().toString());
            jsonObject.put("color", edtColor.getText().toString());
            jsonObject.put("peso", edtPeso.getText().toString());
            jsonObject.put("genero", edtGenero.getText().toString());

        }catch (Exception error){
            Log.e("Error JSON envio", error.toString());
        }


        //2.Tipo de dato obtenido y parametros
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("ID Obtenido", jsonObject.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error envio", volleyError.toString());
                    }
                }
        );

        //3.Envio
        requestQueue.add(jsonObjectRequest);
    }


}