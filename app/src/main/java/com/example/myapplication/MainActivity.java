package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_IN_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<String> mainStringSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycleview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        mainStringSet = new ArrayList<String>();
        for(int i=0;i<10;i++)
        {
            mainStringSet.add("item"+i);
        }
        MainRecycleViewAdapter mainRecycleViewAdapter=new MainRecycleViewAdapter(mainStringSet);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_ADD:
                Toast.makeText(this,"item add"+item.getOrder()+"clicked!",Toast.LENGTH_LONG)
                        .show();
                break;
            case MENU_IN_UPDATE:
                Toast.makeText(this,"item update"+item.getOrder()+"clicked!",Toast.LENGTH_LONG)
                        .show();
                break;
            case MENU_ID_DELETE:
                Toast.makeText(this,"item delete"+item.getOrder()+"clicked!",Toast.LENGTH_LONG)
                        .show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>{

        private ArrayList<String> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textView;
            private final ImageView imgaeViewImage;

            public ViewHolder(View view) {
                super(view);
                imgaeViewImage=view.findViewById(R.id.imageview_item_image);
                textView=view.findViewById(R.id.textView);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextView(){
                return textView;
            }

            public ImageView getImgaeViewImage() {
                return imgaeViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"Add"+getAdapterPosition());
                contextMenu.add(0,MENU_IN_UPDATE,getAdapterPosition(),"Update"+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete"+getAdapterPosition());

            }
        }

        public MainRecycleViewAdapter(ArrayList<String> dataSet){
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
            viewHolder.getTextView().setText(localDataSet.get(position));
            viewHolder.getImgaeViewImage().setImageResource(position%2==1?R.drawable.funny_2:R.drawable.funny_4);

        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}