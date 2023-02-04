package com.example.library_manager.Activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.library_manager.Adapters.PagerAdapter;
import com.example.library_manager.DataBases.DataBase;
import com.example.library_manager.DatacClass.Book;
import com.example.library_manager.R;
import com.example.library_manager.fragments.AddBookFragment;
import com.example.library_manager.fragments.BookFragment;
import com.example.library_manager.fragments.SettingsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 2);
        checkPermission(Manifest.permission.CAMERA, 3);
        checkPermission(Manifest.permission.INTERNET, 4);

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabs = new ArrayList<>();
        ArrayList<Integer> tabs_icons = new ArrayList<>();
        //-------------------------------------------------//
        tabs.add("Home");
        tabs.add("Add Book");
        tabs_icons.add(R.drawable.ic_baseline_home_24);
        tabs_icons.add(R.drawable.ic_baseline_add_24);
        tabs_icons.add(R.drawable.ic_baseline_manage_accounts_24);
        //-------------------------------------------------//
        fragments.add(BookFragment.newInstance("ss", "ss"));
        fragments.add(AddBookFragment.newInstance("ADD"));
        fragments.add(SettingsFragment.newInstance("", ""));
        //-------------------------------------------------//

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager2 viewPager = findViewById(R.id.mainpage);
        setTitle(R.string.books);

        viewPager.setAdapter(new PagerAdapter(this, fragments));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            //tab.setText(tabs.get(position));
            tab.setIcon(tabs_icons.get(position));
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BookFragment.Refresh();
            }
        });

    }


    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
         //   Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

}