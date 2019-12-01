package com.example.bookstore.view.Main.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;
import com.example.bookstore.view.Adapter_Holder.Holder;
import com.example.bookstore.view.Main.fragments.MyBooks.BooksFragment;
import com.example.bookstore.view.Main.fragments.Home.HomeFragment;
import com.example.bookstore.view.Main.fragments.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_fraq)
    FrameLayout mainFraq;
    @BindView(R.id.nav_btn)
    BottomNavigationView navBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navBtn.setOnNavigationItemSelectedListener(this);
        openFrag(new HomeFragment());

    }

    private void openFrag(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fraq, fragment);
        transaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                openFrag(new HomeFragment());
                return true;
            case R.id.books:
                openFrag(new BooksFragment());
                return true;
            case R.id.profile:
                openFrag(new ProfileFragment());
                return true;
        }
        return false;
    }

    public void openBook(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Holder.url));
        Toast.makeText(this, "open", Toast.LENGTH_SHORT).show();
        startActivity(browserIntent);
    }
}
