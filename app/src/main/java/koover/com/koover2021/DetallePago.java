package koover.com.koover2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetallePago extends AppCompatActivity {

    TextView txtId, txtImporte, txtStatus;
    Button seguimiento;
    Button finalizar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pago);

        txtId = (TextView) findViewById(R.id.txtId);
        txtImporte = (TextView) findViewById(R.id.txtImporte);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        seguimiento = (Button) findViewById(R.id.btnSeguimiento);
        finalizar = (Button) findViewById(R.id.btnFinalizar);

        //Obtengo el intent
        Intent intent = getIntent();
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Boton segumiento
        seguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.detallePago, new KooveterTracking()).commit();
            }
        });

        // Boton finalizar
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalizarProceso = new Intent(v.getContext(), MainActivity.class);
                startActivity(finalizarProceso);
            }
        });

    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtImporte.setText("$"+paymentAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}