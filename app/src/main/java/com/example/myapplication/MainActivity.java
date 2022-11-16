package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.DataSaver;
import com.example.myapplication.data.BookItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_IN_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<BookItem> shopItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;



    private ActivityResultLauncher<Intent> addDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()== InputBookItemActivity.RESULT_CODE_SUCCESS){
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        String author=bundle.getString("author");
                        String year=bundle.getString("year");
                        String translator=bundle.getString("translator");
                        String publisher=bundle.getString("publisher");
                        String putdate=bundle.getString("putdate");
                        String isbn=bundle.getString("isbn");
                        int position=bundle.getInt("position");
                        shopItems.add(position,new BookItem(title,author,year,translator,publisher,putdate,isbn,R.drawable.book2));
                        new DataSaver().Save(this,shopItems);
                        mainRecycleViewAdapter.notifyItemInserted(position);
                    }

                }
            } );
    private ActivityResultLauncher<Intent> updateDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()== InputBookItemActivity.RESULT_CODE_SUCCESS){
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        String author=bundle.getString("author");
                        String year=bundle.getString("year");
                        String translator=bundle.getString("translator");
                        String publisher=bundle.getString("publisher");
                        String putdate=bundle.getString("putdate");
                        String isbn=bundle.getString("isbn");
                        int position=bundle.getInt("position");
                        shopItems.get(position).setTitle(title);
                        shopItems.get(position).setAuthor(author);
                        new DataSaver().Save(this,shopItems);
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }

                }
            } );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button_add=(Button)findViewById(R.id.add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, InputBookItemActivity.class);
                intent.putExtra("position",0);
                addDataLauncher.launch(intent);
            }
        });


        RecyclerView recyclerViewMain=findViewById(R.id.recycleview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        DataSaver dataSaver=new DataSaver();
        shopItems=dataSaver.Load(this);

        if(shopItems.size()==0) {
            shopItems.add(new BookItem("Android群英传","徐宣生 著,电子工业出版社","2015-11","某某人",
                    "电子工业出版社","2015-11","21313",R.drawable.book5));
        }
        mainRecycleViewAdapter= new MainRecycleViewAdapter(shopItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_ADD:
                Intent intent=new Intent(this, InputBookItemActivity.class);
                intent.putExtra("position",item.getOrder());
                addDataLauncher.launch(intent);
                break;
            case MENU_IN_UPDATE:
                Intent intentUpdate=new Intent(this, InputBookItemActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                //intentUpdate.putExtra("title",shopItems.get(item.getOrder()).getTitle());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)

                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shopItems.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this,shopItems);
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

        private ArrayList<BookItem> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;

            public TextView getTextViewAuthor() {
                return textViewAuthor;
            }

            private final TextView textViewAuthor;
            private final ImageView imageViewImage;
            private final TextView textViewYear;


            private final TextView textViewTranslator;
            private final TextView textViewPublisher;
            private final TextView textViewPutdate;
            private final TextView textViewIsbn;

            public TextView getTextViewTranslator() {
                return textViewTranslator;
            }


            public TextView getTextViewPublisher() {
                return textViewPublisher;
            }


            public TextView getTextViewPutdate() {
                return textViewPutdate;
            }


            public TextView getTextViewIsbn() {
                return textViewIsbn;
            }


            public TextView getTextViewYear() {
                return textViewYear;
            }

            public ViewHolder(View view) {
                super(view);
                imageViewImage=view.findViewById(R.id.imageview_item_image);
                textViewTitle=view.findViewById(R.id.textview_item_caption);
                textViewAuthor=view.findViewById(R.id.textview_item_price);
                textViewYear=view.findViewById(R.id.textview_item_year);
                textViewTranslator=view.findViewById(R.id.edittext_shop_item_title_translator);
                textViewPublisher=view.findViewById(R.id.edittext_shop_item_title_publisher);
                textViewPutdate=view.findViewById(R.id.edittext_shop_item_title_put_date);
                textViewIsbn=view.findViewById(R.id.edittext_shop_item_title_isbn);

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

        public MainRecycleViewAdapter(ArrayList<BookItem> dataSet){
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
            viewHolder.getTextViewAuthor().setText(localDataSet.get(position).getAuthor());
            viewHolder.getTextViewYear().setText(localDataSet.get(position).getYear());
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getResourceId());
            //viewHolder.getTextViewTranslator().setText(localDataSet.get(position).getTranslator());
            //viewHolder.getTextViewPublisher().setText(localDataSet.get(position).getPublisher());
            //viewHolder.getTextViewPutdate().setText(localDataSet.get(position).getPutdate());
            //viewHolder.getTextViewIsbn().setText(localDataSet.get(position).getIsbn());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}