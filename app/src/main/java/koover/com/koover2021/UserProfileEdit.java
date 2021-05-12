package koover.com.koover2021;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserProfileEdit extends AppCompatActivity {

    EditText nickName;
    EditText userFullName;
    EditText userDireccion;
    EditText userMail;
    EditText userTelefono;
    Button guardarCambios;
    Button cancelaCambios;

    FloatingActionButton fabCamara;

    ImageView fotoPerfil;


    public static final String ENVIARNICKNAME= "Nick del Usuario";
    public static final String ENVIARNOMBRE= "Nombre Completo";
    public static final String ENVIARTELEFONO= "Telefunken";
    public static final String ENVIARDIRECCION= "Aqui vivo yo";
    public static final String ENVIARMAIL= "mi correo";


    private static final int REQUEST_PERMISSION_CAMERA = 1;
    private static final int REQUEST_IMAGE_CAPTURE= 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        nickName = findViewById(R.id.etUserNickName);
        userFullName = findViewById(R.id.etUserFullName);
        userDireccion = findViewById(R.id.etUserAddress);
        userMail = findViewById(R.id.etUserMail);
        userTelefono = findViewById(R.id.etUserPhone);
        guardarCambios = findViewById(R.id.btn_guardar);
        cancelaCambios = findViewById(R.id.btn_cancel);

        fabCamara = findViewById(R.id.fabCameraBtn);
        fotoPerfil = findViewById(R.id.ivUserProfilePicture);


        ///BOTON DE LA CÁMARA

        fabCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PARA EL MANEJO DE LOS PERMISOS, PRIMERO  CHEQUEO SI LA VERSIÓN QUE ESTOY CORRIENDO DE ANDROID (SDK.INT)
//                ES MAYOR O IGUAL A MASRSHMALLOW (M), SI ES ASÍ EJECUTO UN CHEQUEO DE PERMISOS
//                LOS PERMISOS DECLARADOS EN EL MANIFEST LOS COMPARO CON LA CONSULTA QUE LE HAGO AL PACKAGE MANAGER PARA SABER SI ESTAN OTORGADOS PERMISSION_GRANTED
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    //CHEQUEO SI LOS PERMISOS FUERON OTORGADOS
                    if (ActivityCompat.checkSelfPermission(UserProfileEdit.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        abrirCamara();

                    }else{ //SI LOS PERMISOS NO ESTÁN HABILITADOS, ENTONCES LOS SOLICITO AQUÍ
                        ActivityCompat.requestPermissions(UserProfileEdit.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }

                }else{  //SI LA VERSIÓN ES ANTERIOR A MARSHMALLOW, LOS PERMISOS YA SE ACEPTARÁN ANTES DE INSTALAR LA APPLICACIÓN
                    abrirCamara();
                }

            }
        });





//        if (nickName != null){
//            nickName.setText("datos del edit");
//        }


        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guardarCambios = new Intent(v.getContext(), UserProfile.class);

                /* DATOS CAMPO NICK */
                EditText datosNickName = findViewById(R.id.etUserNickName);
                String cDatosNickName = datosNickName.getText().toString();
                guardarCambios.putExtra(ENVIARNICKNAME, cDatosNickName);


                /* DATOS CAMPO NOMBRE */
                EditText datosNombre = findViewById(R.id.etUserFullName);
                String cDatosNombre = datosNombre.getText().toString();
                guardarCambios.putExtra(ENVIARNOMBRE, cDatosNombre);

                /* DATOS CAMPO TELEFONO */
                EditText datosTelefono = findViewById(R.id.etUserPhone);
                String cDatosTelefono = datosTelefono.getText().toString();
                guardarCambios.putExtra(ENVIARTELEFONO, cDatosTelefono);

                /* DATOS CAMPO MAIL */
                EditText datosMail = findViewById(R.id.etUserMail);
                String cDatosMail = datosMail.getText().toString();
                guardarCambios.putExtra(ENVIARMAIL, cDatosMail);

                /* DATOS CAMPO DIRECCION */
                EditText datosDireccion = findViewById(R.id.etUserAddress);
                String cDatosDireccion = datosDireccion.getText().toString();
                guardarCambios.putExtra(ENVIARDIRECCION, cDatosDireccion);

//                INICIA LA ACTIVITY
                startActivity(guardarCambios);

            }
        });



        cancelaCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelaCambios = new Intent(v.getContext(), UserProfile.class);


//                INICIA LA ACTIVITY

                startActivity(cancelaCambios);
            }
        });


    }

    /////////// FUNCIONES  ////////////////


    //FUNCION CHEQUEA RESULTADOS DE PERMISOS ///

    //en el momento que el usuario responde sobre los permisos (onRequesPermissionResult) analizamos si fueron otorgados o no
    //utilizando el valor mayor a 0 que le asignamos a REQUEST_PERMISSION_CAMERA y lo comparamos con lo que nos responde el packagemanager
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirCamara();
            }else{//Si los permisos no fueron otorgados, mandamos un mensaje y volvemos atrás
                Toast.makeText(this, "Necesita habilitar los permisos para usar la cámara", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //FUNCION QUE RECIBE LA FOTO requestCode RECIBE EL CÓDIGO DE RESPUESTA AL CUADRO DE DIÁLOGO DE LOS PERMISOS
    // resultCode RECIBE LA FOTO data INDICA EL TIPO DE DATOS
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK){
//                AQUÍ NOS ASEGURAMOS QUE SE TOMO LA FOTO Y TODO SALIÓ OK*/
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                //relleno el ImageView con la foto que levanté
                fotoPerfil.setImageBitmap(bitmap);
                Log.i("TAG", "Result =>" + bitmap);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //FUNCION DE ABRIR CAMARA
    public void abrirCamara(){
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camaraIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(camaraIntent, REQUEST_IMAGE_CAPTURE);
        }

    };


}
