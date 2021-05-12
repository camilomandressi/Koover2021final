package koover.com.koover2021;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import koover.com.koover2021.Config.Config;


public class Pagar extends AppCompatActivity {

    public static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    Button btnPagarAhora;
    EditText etImportePagar;
    String importe = "";
    FloatingActionButton btnAtras;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);

        //Comienza el paypal service
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        btnPagarAhora = (Button) findViewById(R.id.btnPagar);
        etImportePagar = (EditText) findViewById(R.id.etMonto);
        btnPagarAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }

        });

        btnAtras = (FloatingActionButton) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAtras = new Intent(v.getContext(), Contratar.class);
                startActivity(intentAtras);
                finish();
            }
        });
    }

    private void processPayment() {

        importe = etImportePagar.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(importe)), "USD", "Importe a pagar", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intentPagar = new Intent(this, PaymentActivity.class);
        intentPagar.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intentPagar.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intentPagar,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, DetallePago.class)
                                    .putExtra("PaymentDetails", paymentDetails)
                                    .putExtra("PaymentAmount", importe)


                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
                    Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Operación no valida", Toast.LENGTH_SHORT).show();
        }
    }
}