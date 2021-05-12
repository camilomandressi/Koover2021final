package koover.com.koover2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static koover.com.koover2021.R.layout.login_tab_fragment;

public class LoginTabFragment extends Fragment {

    EditText email;
    EditText pass;
    Button login;
    TextView forgetPass;

    //Inicializo las variables de validación
    String usuario = "";
    String password = "";

    FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(login_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        login = root.findViewById(R.id.login);
        forgetPass = root.findViewById(R.id.forget_pass);


        email.setTranslationX(800);
        pass.setTranslationX(800);
        login.setTranslationX(800);
        forgetPass.setTranslationX(800);


        email.setAlpha(0);
        pass.setAlpha(0);
        login.setAlpha(0);
        forgetPass.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        //Creo el boton para iniciar la actividad del dashboard es por eso que mando todo el codigo al main */
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                usuario = email.getText().toString();
                password = pass.getText().toString();

                if(!usuario.isEmpty() && !password.isEmpty()) {
                    loginUser();
                }else{
                    Toast.makeText(getContext(), "Campos requeridos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    //Creo una nueva funcion que me permite llamar y comparar los datos con los de firebase
    private void loginUser(){
        mAuth.signInWithEmailAndPassword(usuario, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getContext(), "No se pudo iniciar sesión. Compruebe sus datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
