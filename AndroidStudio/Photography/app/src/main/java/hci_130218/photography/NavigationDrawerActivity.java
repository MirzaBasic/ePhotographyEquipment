package hci_130218.photography;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import hci_130218.photography.Activitys.KorpaActivity;
import hci_130218.photography.Activitys.NarudzbeActivity;
import hci_130218.photography.Fragments.AkcijeFragment;
import hci_130218.photography.Fragments.ProfileFragment;
import hci_130218.photography.Fragments.ProizvodiViewPagerFragment;
import hci_130218.photography.Helper.Sesija;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);


       mDrawer= (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ProizvodiViewPagerFragment fragment=new ProizvodiViewPagerFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content,fragment).commit();
       View mHeaderNav= navigationView.getHeaderView(0);
        TextView username= (TextView) mHeaderNav.findViewById(R.id.tv_username);
        TextView email= (TextView) mHeaderNav.findViewById(R.id.tv_email);
        username.setText(Sesija.GetUser().Ime+" "+Sesija.GetUser().Prezime);
        email.setText(Sesija.GetUser().Email);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


         if (id == R.id.nav_proizvodi) {


             ProizvodiViewPagerFragment fragment=new ProizvodiViewPagerFragment();
             FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
             ft.replace(R.id.content,fragment).commit();





         }

        if (id == R.id.nav_akcija) {


            AkcijeFragment fragment=new AkcijeFragment();
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content,fragment).commit();




        }

        if (id == R.id.nav_korpa) {

           Intent intent=new Intent(NavigationDrawerActivity.this, KorpaActivity.class);
            startActivity(intent);




        }
        if (id == R.id.nav_narudzbe) {

            Intent intent=new Intent(NavigationDrawerActivity.this, NarudzbeActivity.class);
            startActivity(intent);




        }
        if (id == R.id.nav_logout) {

            Sesija.SaveUser(null);
            Intent intent=new Intent(NavigationDrawerActivity.this, MainActivity.class);
            startActivity(intent);
finish();



        }
        if (id == R.id.nav_profile) {

            ProfileFragment fragment=new ProfileFragment();
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content,fragment).commit();



        }



        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void OpenDrawer(){

        mDrawer.openDrawer(GravityCompat.START);
    }
}
