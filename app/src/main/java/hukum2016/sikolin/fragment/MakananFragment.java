package hukum2016.sikolin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hukum2016.sikolin.R;
import hukum2016.sikolin.activity.BuyerActivity;
import hukum2016.sikolin.adapter.MenuAdapter;
import hukum2016.sikolin.helper.MenuKantin;

/**
 * Created by Ari on 4/26/2016.
 */
public class MakananFragment extends Fragment{
    private GridLayoutManager lLayout;
    private MenuAdapter mAdapter;
    private BuyerActivity buyerActivity;
    private ArrayList<MenuKantin> menuMakanan;
    public MakananFragment(){
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        menuMakanan = new ArrayList<>();
        buyerActivity = new BuyerActivity();
        getMakanan(buyerActivity.menuKantinList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        Log.d("masuk","createview");
        View rootView = inflater.inflate(R.layout.fragment_makanan, container, false);
        lLayout = new GridLayoutManager(getActivity(), 2);

        RecyclerView rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        mAdapter = new MenuAdapter(menuMakanan);
        mAdapter.notifyDataSetChanged();
        rView.setAdapter(mAdapter);
        return rootView;
    }
    public ArrayList<MenuKantin> getMakanan(ArrayList<MenuKantin> menuKantin) {
        for(int i = 0; i< menuKantin.size();i++){
            if(menuKantin.get(i).getJenis_menu() == 0){
                menuMakanan.add(menuKantin.get(i));
            }
        }
        return menuMakanan;
    }
}
