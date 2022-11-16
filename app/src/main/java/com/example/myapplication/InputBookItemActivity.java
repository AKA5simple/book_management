package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputBookItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);

        position=this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("title");
        String author=this.getIntent().getStringExtra("author");
        String translator=this.getIntent().getStringExtra("translator");
        String publisher=this.getIntent().getStringExtra("publisher");
        String putdate=this.getIntent().getStringExtra("putdate");
        String isbn=this.getIntent().getStringExtra("isbn");

        EditText editTextTitle=findViewById(R.id.edittext_shop_item_title);
        EditText editTextAuthor=findViewById(R.id.edittext_shop_item_title_price);
        EditText editTextTranslator=findViewById(R.id.edittext_shop_item_title_translator);
        EditText editTextPublisher=findViewById(R.id.edittext_shop_item_title_publisher);
        EditText editTextPutdate=findViewById(R.id.edittext_shop_item_title_put_date);
        EditText editTextIBSN=findViewById(R.id.edittext_shop_item_title_isbn);



        if(null!=title)
        {
            editTextTitle.setText(title);
            editTextAuthor.setText(author);
            editTextTranslator.setText(translator);
            editTextPublisher.setText(publisher);
            editTextPutdate.setText(putdate);
            editTextIBSN.setText(isbn);
        }



        Button buttonOk=findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                bundle.putString("author",editTextAuthor.getText().toString());
                bundle.putString("year",editTextPutdate.getText().toString());
                bundle.putString("translator",editTextTranslator.getText().toString());
                bundle.putString("publisher",editTextPublisher.getText().toString());
                bundle.putString("ibsn",editTextIBSN.getText().toString());

                bundle.putInt("position",position);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                InputBookItemActivity.this.finish();
            }
        });
    }
}