package koover.com.koover2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static koover.com.koover2021.R.layout.singup_tab_fragment;

public class SignupTabFragment extends Fragment {

    EditText emailEditText;
    EditText nameEditText;
    EditText phoneEditText;
    EditText passwordEditText;
    EditText confirmEditText;
    Button regBtn;


    //Creo las variables de los datos que registro en el form
    String name = "";
    String email = "";
    String phone = "";
    String password = "";
    String confirm = "";

    //Valido firebase
    FirebaseAuth mAuth;
    //Genero mi objeto Database reference para almacenar los datos en la DB RealTime
    DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(singup_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(); // Hacemos referencia al nodo principal de nuestra base de datos.



        emailEditText = root.findViewById(R.id.email);
        nameEditText = root.findViewById(R.id.name);
        phoneEditText = root.findViewById(R.id.telefono);
        passwordEditText = root.findViewById(R.id.password);
        confirmEditText = root.findViewById(R.id.password_confirm);
        regBtn = root.findViewById(R.id.register);


        emailEditText.setTranslationX(800);
        nameEditText.setTranslationX(800);
        phoneEditText.setTranslationX(800);
        passwordEditText.setTranslationX(800);
        confirmEditText.setTranslationX(800);
        regBtn.setTranslationX(800);


        emailEditText.setAlpha(0);
        nameEditText.setAlpha(0);
        phoneEditText.setAlpha(0);
        passwordEditText.setAlpha(0);
        confirmEditText.setAlpha(0);
        regBtn.setAlpha(0);


        emailEditText.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        nameEditText.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        phoneEditText.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        passwordEditText.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        confirmEditText.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        regBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();


        //Creo el botón de registro que envía los datos del usuario a mi base de datos
        //cuando presiono el boton registrar.
        regBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                email = emailEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                password = passwordEditText.getText().toString();
                confirm = confirmEditText.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !password.isEmpty() && !confirm.isEmpty()){

                    if(password.length() >= 6){

                        if(password.equals(confirm)){

                            registerarUsuario();

                        }
                        else{
                            Toast.makeText(getContext(), "La contraseña no concuerda", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getContext(), "La contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    Toast.makeText(getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }//acá termino la funcion del click.

        });//Aca termina el boton del registro.

        return root;
    }


    private void registerarUsuario(){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Data base trabaja con mapa de valores
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("phone", phone);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getContext(), "No se ha completado el registro vuelva a intentarlo mas tarde", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else{
                    Toast.makeText(getContext(), "No se pudo completar el registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Creo un nuevo metodo para verificar si el usuario ya se ha registrado anteriormente y tomo sus datos.
    @Override
    public void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        }

    }
}
