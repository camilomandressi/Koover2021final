package koover.com.koover2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmacionPedido extends AppCompatActivity {

    Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_pedido);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String direccionRe = intent.getStringExtra(Contratar.ENVIAR_DIRECCION_R);
        String direccionEn = intent.getStringExtra(Contratar.ENVIAR_DIRECCION_E);

        // Capture the layout's TextView and set the string as its text
        TextView direccionRetiro = (TextView) findViewById(R.id.nombreLocalConfirmacion);
        TextView direccionEntrega = (TextView) findViewById(R.id.pedidoLocalConfirmacion);

        direccionRetiro.setText(direccionRe);
        direccionEntrega.setText(direccionEn);


        Button confirmar = (Button) findViewById(R.id.btnFin);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagar = new Intent(v.getContext(), Pagar.class);
                startActivity(pagar);
                finish();
            }
        });

    }
}