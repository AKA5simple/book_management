package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.BookItem;
import com.example.myapplication.data.DataSaver;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_IN_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<BookItem> bookItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int[] pictures={R.drawable.book1,R.drawable.book2,R.drawable.book3,R.drawable.book4,R.drawable.book5,
                            R.drawable.book6,R.drawable.book7,R.drawable.book8,R.drawable.book9,R.drawable.book10,
                            R.drawable.book11};



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
                        bookItems.add(position,new BookItem(title,author,year,translator,publisher,putdate,isbn,pictures[(int) (Math.random()*11)]));
                        new DataSaver().Save(this, bookItems);
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
                        bookItems.get(position).setTitle(title);
                        bookItems.get(position).setAuthor(author);
                        new DataSaver().Save(this, bookItems);
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }

                }
            } );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        drawerLayout=(DrawerLayout)findViewById(R.id.drawers_menu);
        Button button_drawer_layout=(Button)findViewById(R.id.drawers);
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        button_drawer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        Button button_about=findViewById(R.id.about);
        button_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        Button button_add=(Button)findViewById(R.id.add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, InputBookItemActivity.class);
                intent.putExtra("position",0);
                addDataLauncher.launch(intent);

            }
        });


        RecyclerView recyclerViewMain=findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        DataSaver dataSaver=new DataSaver();
        bookItems =dataSaver.Load(this);

        if(bookItems.size()==0) {
            bookItems.add(new BookItem("Android群英传","徐宣生","2015-11","某某人",
                    "电子工业出版社","2015-11","21313",R.drawable.book5));
        }
        mainRecycleViewAdapter= new MainRecycleViewAdapter(bookItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId())
//        {
//            case MENU_ID_ADD:
//                Intent intent=new Intent(this, InputBookItemActivity.class);
//                intent.putExtra("position",item.getOrder());
//                addDataLauncher.launch(intent);
//                break;
//            case MENU_IN_UPDATE:
//                Intent intentUpdate=new Intent(this, InputBookItemActivity.class);
//                intentUpdate.putExtra("position",item.getOrder());
//                //intentUpdate.putExtra("title",shopItems.get(item.getOrder()).getTitle());
//                updateDataLauncher.launch(intentUpdate);
//                break;
//            case MENU_ID_DELETE:
//                AlertDialog alertDialog=new AlertDialog.Builder(this)
//                        .setTitle(R.string.confirmation)
//                        .setMessage(R.string.sure_to_delete)
//
//                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                bookItems.remove(item.getOrder());
//                                new DataSaver().Save(MainActivity.this, bookItems);
//                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
//
//                            }
//                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                            }
//                        }).create();
//                alertDialog.show();
//                break;
//        }
        return super.onContextItemSelected(item);
    }




    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>{

        private ArrayList<BookItem> localDataSet;
        private AdapterView.OnItemClickListener itemClickListener;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;



            private final TextView textViewAuthor;
            private final ImageView imageViewImage;
            private final TextView textViewYear;

            public final LinearLayout getLinearLayout_book() {
                return linearLayout_book;
            }

            private LinearLayout linearLayout_book;

            private final TextView textViewTranslator;
            private final TextView textViewPublisher;
            private final TextView textViewPutdate;
            private final TextView textViewIsbn;



            public TextView getTextViewAuthor() {
                return textViewAuthor;
            }

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
                linearLayout_book=view.findViewById(R.id.linearLayout);

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
//                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"Add"+getAdapterPosition());
//                contextMenu.add(0,MENU_IN_UPDATE,getAdapterPosition(),"Update"+getAdapterPosition());
//                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete"+getAdapterPosition());

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


            viewHolder.getLinearLayout_book().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.confirmation)
                            .setMessage(R.string.sure_to_delete)

                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    bookItems.remove(viewHolder.getAdapterPosition());
                                    new DataSaver().Save(MainActivity.this, bookItems);
                                    mainRecycleViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            }).create();
                    alertDialog.show();
                    return false;
                }
            });

            viewHolder.getLinearLayout_book().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=viewHolder.getAdapterPosition();
                    Intent intent=new Intent(MainActivity.this,InputBookItemActivity.class);
                    intent.putExtra("position",viewHolder.getAdapterPosition());
                    intent.putExtra("title",bookItems.get(position).getTitle());
                    intent.putExtra("author",bookItems.get(position).getAuthor());
                    intent.putExtra("translator",bookItems.get(position).getTranslator());
                    intent.putExtra("publisher",bookItems.get(position).getPublisher());
                    intent.putExtra("putdate",bookItems.get(position).getPutdate());
                    intent.putExtra("isbn",bookItems.get(position).getIsbn());
                    updateDataLauncher.launch(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }


    }
}