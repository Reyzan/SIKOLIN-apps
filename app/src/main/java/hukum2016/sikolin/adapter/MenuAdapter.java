package hukum2016.sikolin.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hukum2016.sikolin.R;
import hukum2016.sikolin.helper.MenuKantin;
import hukum2016.sikolin.holder.MenuViewHolder;

/**
 * Created by Ari on 5/8/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder>{
    private ArrayList<MenuKantin> menuKantinList;

    public MenuAdapter(ArrayList<MenuKantin> menuKantinList){
        this.menuKantinList = menuKantinList;
    }
    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list, parent, false);
        MenuViewHolder mvh = new MenuViewHolder(parent.getContext(),itemView);
        Log.d("masuk","menuviewholder");
        return mvh;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        Log.d("masuk","bind");
        holder.foto.setImageBitmap(menuKantinList.get(position).getImage());
        holder.nama.setText(menuKantinList.get(position).getNama_menu());
        holder.harga.setText(menuKantinList.get(position).getHarga_menu());
    }

    @Override
    public int getItemCount() {
        return menuKantinList == null ? 0 : menuKantinList.size();
    }
}
