package hukum2016.sikolin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hukum2016.sikolin.adapter.MenuAdapter;
import hukum2016.sikolin.app.AppConfig;
import hukum2016.sikolin.app.AppController;
import hukum2016.sikolin.fragment.MakananFragment;
import hukum2016.sikolin.fragment.MinumanFragment;
import hukum2016.sikolin.fragment.SnackFragment;
import hukum2016.sikolin.R;
import hukum2016.sikolin.helper.MenuKantin;
import hukum2016.sikolin.helper.SQLiteHandler;
import hukum2016.sikolin.helper.SessionManager;

public class BuyerActivity extends AppCompatActivity {
    private static final String TAG = BuyerActivity.class.getSimpleName();
    private SessionManager session;
    private SQLiteHandler db;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int id_menu,jenis_menu,harga_menu;
    private String nama_menu,foto_menu,deskripsi,nama_penjual;
    private float rating;
    private MenuKantin menu;
    public ArrayList<MenuKantin> menuKantinList;
    private int[] tabIcons = {
            R.drawable.ic_tab_food,
            R.drawable.ic_tab_drink,
            R.drawable.ic_tab_snack
    };
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        setContentView(R.layout.activity_buyer);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        menu = new MenuKantin();
        menuKantinList = new ArrayList<>();
        Log.d("ready","getmenu");
        getMenu();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MakananFragment(), "MAKANAN");
        adapter.addFragment(new MinumanFragment(), "MINUMAN");
        adapter.addFragment(new SnackFragment(), "SNACK");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     **/
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(BuyerActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }
    public ArrayList<MenuKantin> getMenu() {
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(BuyerActivity.class.getSimpleName(), "Buyer Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArr = jObj.getJSONArray("menu");
                    boolean status = jObj.getBoolean("status");
                    Log.d("JSONResult:", jsonArr.toString());
                    Log.d("JSONarrLength:", jsonArr.length() + "");
                    if(status){
                            for (int ii = 0; ii < jsonArr.length(); ii++) {
                                JSONObject json = jsonArr.getJSONObject(ii);
                                id_menu = json.getInt("id_menu");
                                nama_menu = json.getString("nama_menu");
                                jenis_menu = json.getInt("jenis_menu");
                                harga_menu = json.getInt("harga_menu");
                                foto_menu = json.getString("foto_menu");
                                deskripsi = json.getString("deskripsi");
                                rating = (float) json.getDouble("rating");
                                nama_penjual = json.getString("nama_penjual");
                                menu = new MenuKantin(nama_menu,foto_menu,deskripsi,nama_penjual,harga_menu,jenis_menu,id_menu,rating);
                                menuKantinList.add(menu);
                            }

                    } else {
                        // Error in getmenu. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "JSON Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Login Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),
                        volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                Map<String,String> userDetails = db.getUserDetails();
                Log.d("auth", session.getKeyAuth());
                Log.d("user", userDetails.get("username"));
                map.put("username",userDetails.get("username"));
                map.put("authkey",session.getKeyAuth());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
        return menuKantinList;
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
