package koover.com.koover2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class Contratar extends AppCompatActivity implements View.OnClickListener{

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    Button confirmarPedido;
    EditText horario;
    Button selectorHorario;
    FloatingActionButton btnAtras;

    public static final String ENVIAR_DIRECCION_R ="Dirección de retiro";
    public static final String ENVIAR_DIRECCION_E ="Dirección de entrega";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar);

        horario = findViewById(R.id.descripcionPedido);
        confirmarPedido = findViewById(R.id.confirmarPedido);
        selectorHorario = findViewById(R.id.selectorHorario);
        selectorHorario.setOnClickListener((View.OnClickListener) this);

        confirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirmacion = new Intent(v.getContext(), ConfirmacionPedido.class);

                EditText dRetiro = (EditText) findViewById(R.id.direccionRetiro);
                EditText dEntrega = (EditText) findViewById(R.id.nombreLocal);
                String cPdireccionR = dRetiro.getText().toString();
                String ePdireccionE = dEntrega.getText().toString();

                confirmacion.putExtra(ENVIAR_DIRECCION_R, cPdireccionR);
                confirmacion.putExtra(ENVIAR_DIRECCION_E, ePdireccionE);
                startActivity(confirmacion);
                finish();
            }
        });


        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(Contratar.this, KooDetailActivity.class);
                finish();
                startActivity(atras);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectorHorario:
                selectorHorario();
                break;
        }
    }

    private void selectorHorario() {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                horario.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hour, minute, false);

        recogerHora.show();
    }



}