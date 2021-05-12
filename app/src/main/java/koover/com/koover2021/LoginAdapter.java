package koover.com.koover2021;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;

public class LoginAdapter extends FragmentPagerAdapter {

    //Creo las variables privadas del apdaptador y llamo a la cantidad de tabs
    private Context context;
    int totalTabs;

    //Creo el constructor (Paso el tipo y la variable)
    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }


    //Genero un fragment para que me traiga todos los items.
    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SignupTabFragment signupTabFragment = new SignupTabFragment();
                return signupTabFragment;
            default:
                return null;
        }
    }

    //Para generar el override mas rápido voy a code override y selecciono la función getCount
    @Override
    public int getCount() {
        return totalTabs;
    }

}
