package com.mteam.multichoicequiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mteam.multichoicequiz.DBHelper.DBHelper;
import com.mteam.multichoicequiz.adapter.AdapterCategory;
import com.mteam.multichoicequiz.common.Common;
import com.mteam.multichoicequiz.common.SpaceDecoration;
import com.mteam.multichoicequiz.model.Category;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterCategory.OnClick {
    private AdapterCategory adapterCategory;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
  private List<Category>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.tool_bar);
        toolbar.setTitle("Quiz");
        setSupportActionBar(toolbar);

        list=DBHelper.getInstance(this).getListCategory();
        recyclerView=findViewById(R.id.rc_category);
        adapterCategory=new AdapterCategory(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        int space=4;
        recyclerView.addItemDecoration(new SpaceDecoration(space));



        recyclerView.setAdapter(adapterCategory);


    }

    @Override
    public int count() {
        return list.size();
    }

    @Override
    public void onClick(int position) {
        list.get(position).getImage();
        Toast.makeText(this,""+list.get(position).getId(),Toast.LENGTH_LONG).show();
        Common.categorySelected=new Category();

        Common.categorySelected=list.get(position);
        Intent intent=new Intent(this,QuestionActivity.class);
        startActivity(intent);

    }

    @Override
    public Category getData(int position) {
        return list.get(position);
    }
}
