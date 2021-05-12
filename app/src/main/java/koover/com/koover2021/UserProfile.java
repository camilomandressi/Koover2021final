package koover.com.koover2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class UserProfile extends AppCompatActivity {

    Button editarPerfil;
    FloatingActionButton botonAtrasDashbord;

    TextView nickTemporal;
    TextView nombreTemporal;
    TextView telefonoTemporal;
    TextView mailTemporal;
    TextView direccionTemporal;

    //Integración con database Llamo a las referencias
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Instancio las variables a levantar
        nickTemporal = (TextView) findViewById(R.id.tvUserNickName);
        nombreTemporal = (TextView) findViewById(R.id.etUserFullName);
        mailTemporal = (TextView) findViewById(R.id.etUserMail);
        telefonoTemporal = (TextView) findViewById(R.id.etUserPhone);


        //Instancio la variable de data base (Hace referencia al nodo principal)
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        getUserInfo();


        Intent traeDatos = getIntent();
//        TRAER LOS DATOS DEL USER PROFILE EDIT ESTO FUNCIONA
        /* RECIBE DATOS NICKNAME */
        final String recibeUserNickName = traeDatos.getStringExtra(UserProfileEdit.ENVIARNICKNAME);
        final TextView muestraUserNickName = (TextView) findViewById(R.id.tvUserNickName);

        final String direccionMuestra = traeDatos.getStringExtra(UserProfileEdit.ENVIARDIRECCION);
        final TextView direccionMuestraTv = (TextView) findViewById(R.id.etUserAddress);

        if (recibeUserNickName == null){
            muestraUserNickName.setText("Pepe");
            direccionMuestraTv.setText("Necesita cambiar la dirección");
        }else{
            muestraUserNickName.setText(recibeUserNickName);
        }

        /* RECIBE DATOS NOMBRE */
        String recibeUserName = traeDatos.getStringExtra(UserProfileEdit.ENVIARNOMBRE);
        TextView muestraUserName = (TextView) findViewById(R.id.etUserFullName);
        muestraUserName.setText(recibeUserName);

        /* RECIBE DATOS TELEFONO */
        String recibeUserTelefono = traeDatos.getStringExtra(UserProfileEdit.ENVIARTELEFONO);
        TextView muestraUserTelefono = (TextView) findViewById(R.id.etUserPhone);
        muestraUserTelefono.setText(recibeUserTelefono);

        /* RECIBE DATOS MAIL */
        String recibeUserMail = traeDatos.getStringExtra(UserProfileEdit.ENVIARMAIL);
        TextView muestraUserMail = (TextView) findViewById(R.id.etUserMail);
        muestraUserMail.setText(recibeUserMail);

        /* RECIBE DATOS DIRECCION */
        String recibeUserDireccion= traeDatos.getStringExtra(UserProfileEdit.ENVIARDIRECCION);
        TextView muestraUserDireccion = (TextView) findViewById(R.id.etUserAddress);
        muestraUserDireccion.setText(recibeUserDireccion);



        //BOTON EDITAR
        editarPerfil = findViewById(R.id.btn_editar);
        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent editaP = new Intent(v.getContext(), UserProfileEdit.class);
        TextView muestraUserNickName = findViewById(R.id.tvUserNickName);
        String recibeUserNickName = editaP.getStringExtra(UserProfileEdit.ENVIARNICKNAME);
        muestraUserNickName.setText(recibeUserNickName);
        startActivity(editaP);
            }
        });


        //BOTON ATRAS DASHBOARD
        botonAtrasDashbord = findViewById(R.id.fbFlechaAtras);
        botonAtrasDashbord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(v.getContext(), MainActivity.class);

                startActivity(atras);
            }
        });

    }


    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String telefono = dataSnapshot.child("phone").getValue().toString();

                    nickTemporal.setText(name);
                    nombreTemporal.setText(name);
                    mailTemporal.setText(email);
                    telefonoTemporal.setText(telefono);
                }
                else{
                    Toast.makeText(UserProfile.this, "No funciona temporalmente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

