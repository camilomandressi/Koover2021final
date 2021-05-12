package koover.com.koover2021;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class IntroductoryActivity extends AppCompatActivity{

    ImageView logo,appName,splashImg;
    LottieAnimationView lottieAnimationView;

    private static final int NUM_PAGES =3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    Animation anim;
    private static int SPLASH_TIME_OUT =  5500;

    SharedPreferences mSharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.app_name);
        splashImg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim = AnimationUtils.loadAnimation(this, R.anim.o_b_anim);
        viewPager.startAnimation(anim);


        splashImg.animate().translationY(-3200).setDuration(1000).setStartDelay(6000);
        logo.animate().translationY(2800).setDuration(1000).setStartDelay(6000);
        appName.animate().translationY(2800).setDuration(1000).setStartDelay(6000);
        lottieAnimationView.animate().translationY(2800).setDuration(1000).setStartDelay(6000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharedPref = getSharedPreferences("SharedPref", MODE_PRIVATE);
                boolean isFirstTime = mSharedPref.getBoolean("firstTime", true);

                if(isFirstTime){
                    SharedPreferences.Editor editor = mSharedPref.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    //Si es verdadero vamos a boarding inicial
                }else{
                    Intent intent = new Intent(IntroductoryActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{


        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoardingFragment tab1 = new OnBoardingFragment();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}