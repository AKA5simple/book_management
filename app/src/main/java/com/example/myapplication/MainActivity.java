package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.ShopItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_IN_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<ShopItem> shopItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycleview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        shopItems = new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            shopItems.add(new ShopItem("item"+i,Math.random()*10,i%2==1?R.drawable.funny_2:R.drawable.funny_3));
        }
        mainRecycleViewAdapter= new MainRecycleViewAdapter(shopItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_ADD:
                shopItems.add(item.getOrder(),new ShopItem("added"+item.getOrder(),Math.random()*10,R.drawable.funny_1));
                mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
                break;
            case MENU_IN_UPDATE:
                shopItems.get(item.getOrder()).setTitle("updated");
                mainRecycleViewAdapter.notifyItemChanged(item.getOrder());
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                                .setMessage(R.string.sure_to_delete)

                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                shopItems.remove(item.getOrder());
                                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());

                                            }
                                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>{

        private ArrayList<ShopItem> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;

            public TextView getTextViewPrice() {
                return textViewPrice;
            }

            private final TextView textViewPrice;
            private final ImageView imageViewImage;

            public ViewHolder(View view) {
                super(view);
                imageViewImage=view.findViewById(R.id.imageview_item_image);
                textViewTitle=view.findViewById(R.id.textview_item_caption);
                textViewPrice=view.findViewById(R.id.textview_item_price);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle(){
                return textViewTitle;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"Add"+getAdapterPosition());
                contextMenu.add(0,MENU_IN_UPDATE,getAdapterPosition(),"Update"+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete"+getAdapterPosition());

            }
        }

        public MainRecycleViewAdapter(ArrayList<ShopItem> dataSet){
            localDataSet=dataSet;
        }
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,final int position) {
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            viewHolder.getTextViewPrice().setText(localDataSet.get(position).getPrice().toString());
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getResourceId());

        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}