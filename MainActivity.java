package mb.ganesh.trackie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView confirm , recovery , death  , update , active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        confirm = findViewById(R.id.cConfirmId);
        recovery = findViewById(R.id.cRecoverId);
        death = findViewById(R.id.cDeathId);
        update = findViewById(R.id.INUpdateId);
        active = findViewById(R.id.activeIndiaId);

        fetchData();


    }

    private void fetchData() {
        String URL = "https://api.covid19india.org/data.json";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject obj = new JSONObject(response.toString());
                    JSONArray arr = obj.getJSONArray("statewise");

                    JSONObject innerObj = arr.getJSONObject(0);

                    Object confirmStr =innerObj.get("confirmed");
                    Object recoveryStr =innerObj.get("recovered");
                    Object deathStr =innerObj.get("deaths");
                    Object updateStr =innerObj.get("lastupdatedtime");
                    Object activeStr =innerObj.get("active");

                    confirm.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(confirmStr.toString())));
                    recovery.setText(recoveryStr.toString());
                    death.setText(deathStr.toString());
                    update.setText(updateStr.toString());
                    active.setText(activeStr.toString());

                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , e.getMessage() , Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(request);
    }
}