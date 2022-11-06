package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.DataSaver;
import com.example.myapplication.data.ShopItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopItemFragment extends Fragment {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_IN_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<ShopItem> shopItems;
    private ShopItemFragment.MainRecycleViewAdapter mainRecycleViewAdapter;



    private ActivityResultLauncher<Intent> addDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==InputShopItemActivity.RESULT_CODE_SUCCESS){
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        double price=bundle.getDouble("price");
                        int position=bundle.getInt("position");
                        shopItems.add(position,new ShopItem(title,price,R.drawable.funny_1));
                        new DataSaver().Save(this.getContext(),shopItems);
                        mainRecycleViewAdapter.notifyItemInserted(position);
                    }

                }
            } );
    private ActivityResultLauncher<Intent> updateDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==InputShopItemActivity.RESULT_CODE_SUCCESS){
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        double price=bundle.getDouble("price");
                        int position=bundle.getInt("position");
                        shopItems.get(position).setTitle(title);
                        shopItems.get(position).setPrice(price);
                        new DataSaver().Save(this.getContext(),shopItems);
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }

                }
            } );



    public ShopItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShopItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopItemFragment newInstance() {
        ShopItemFragment fragment = new ShopItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_shop_item,container,false);
        RecyclerView recyclerViewMain=rootView.findViewById(R.id.recycleview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        DataSaver dataSaver=new DataSaver();
        shopItems=dataSaver.Load(this.getContext());

        if(shopItems.size()==0) {
            shopItems.add(new ShopItem("item", Math.random() * 10, R.drawable.funny_2));
        }
        mainRecycleViewAdapter= new MainRecycleViewAdapter(shopItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
        return rootView;
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_ADD:
                Intent intent=new Intent(this.getContext(), InputShopItemActivity.class);
                intent.putExtra("position",item.getOrder());
                addDataLauncher.launch(intent);
                break;
            case MENU_IN_UPDATE:
                Intent intentUpdate=new Intent(this.getContext(), InputShopItemActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",shopItems.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("price",shopItems.get(item.getOrder()).getPrice());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog=new AlertDialog.Builder(this.getContext())
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)

                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shopItems.remove(item.getOrder());
                                new DataSaver().Save(ShopItemFragment.this.getContext(),shopItems);
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
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
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