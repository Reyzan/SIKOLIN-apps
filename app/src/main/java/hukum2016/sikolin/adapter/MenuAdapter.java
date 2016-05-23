package hukum2016.sikolin.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hukum2016.sikolin.R;
import hukum2016.sikolin.activity.AddToCart;
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
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, int position) {
        final MenuKantin listMenu = menuKantinList.get(position);
        holder.foto.setImageBitmap(listMenu.getImage());
        holder.nama.setText(listMenu.getNama_menu());
        holder.harga.setText(listMenu.getHarga_menu()+"");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.getmContext(), AddToCart.class);
                intent.putExtra("nama", listMenu.getNama_menu());
                intent.putExtra("harga", listMenu.getHarga_menu());
                intent.putExtra("id_menu",listMenu.getId_menu());
                holder.getmContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuKantinList == null ? 0 : menuKantinList.size();
    }
}
