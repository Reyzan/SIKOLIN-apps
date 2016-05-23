package hukum2016.sikolin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Map;

import hukum2016.sikolin.R;
import hukum2016.sikolin.activity.BuyerActivity;
import hukum2016.sikolin.adapter.MenuAdapter;
import hukum2016.sikolin.app.AppConfig;
import hukum2016.sikolin.app.AppController;
import hukum2016.sikolin.helper.MenuKantin;
import hukum2016.sikolin.helper.SQLiteHandler;
import hukum2016.sikolin.helper.SessionManager;

/**
 * Created by Ari on 4/26/2016.
 */
public class MakananFragment extends Fragment{
    private GridLayoutManager lLayout;
    private MenuAdapter mAdapter;
    private static final String TAG = BuyerActivity.class.getSimpleName();
    private SessionManager session;
    private SQLiteHandler db;
    private int id_menu,jenis_menu,harga_menu;
    private String nama_menu,foto_menu,deskripsi,nama_penjual;
    private float rating;
    private MenuKantin menu;
    private ArrayList<MenuKantin> menuMakanan;
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    public MakananFragment(){
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        menuMakanan = new ArrayList<>();

        session = new SessionManager(getContext());
        db = new SQLiteHandler(getContext());
        getMenu();

        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_makanan, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        lLayout = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayout);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter = new MenuAdapter(menuMakanan);
                                    recyclerView.setAdapter(mAdapter);
                                }
                            }
                , 1000);
        return rootView;
    }
    public void getMenu() {
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
                            if(jenis_menu == 0){
                                menu = new MenuKantin(nama_menu,foto_menu,deskripsi,nama_penjual,harga_menu,jenis_menu,id_menu,rating);
                                menuMakanan.add(menu);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        // Error in getmenu. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "JSON Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Login Error: " + volleyError.getMessage());
                Toast.makeText(getContext(),
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
