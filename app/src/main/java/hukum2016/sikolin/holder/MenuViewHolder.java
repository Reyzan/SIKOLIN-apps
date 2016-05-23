package hukum2016.sikolin.holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hukum2016.sikolin.R;

/**
 * Created by Ari on 5/8/2016.
 */
public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Context mContext;
    public TextView nama,harga;
    public ImageView foto;
    public CardView cardView;
    public MenuViewHolder(Context context,View itemView) {
        super(itemView);
        mContext = context;
        itemView.setOnClickListener(this);
        foto = (ImageView) itemView.findViewById(R.id.menu_photo);
        nama = (TextView) itemView.findViewById(R.id.menu_name);
        harga = (TextView) itemView.findViewById(R.id.menu_price);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "MenuKantin" + nama.toString(), Toast.LENGTH_SHORT).show();
    }

    public Context getmContext() {
        return mContext;
    }
}
