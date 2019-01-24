package com.example.huangjb.statuslayout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lib.OnStatusChildClickListener;
import com.example.lib.StatusLayoutManager;

public class MainActivity extends AppCompatActivity {

    private StatusLayoutManager statusLayoutManager;
    private LayoutInflater inflater;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });
        recyclerView = findViewById(R.id.rv_content);

        setupStatusLayoutManager();

        statusLayoutManager.showLoadingLayout();
    }

    private void setupStatusLayoutManager() {
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefresh)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        Toast.makeText(MainActivity.this, "重新加载（空数据）", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErrorChildClick(View view) {

                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }
                })
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_status_loading:
                // 加载中
                statusLayoutManager.showLoadingLayout();
                break;
            case R.id.menu_status_empty:
                // 空数据
                statusLayoutManager.showEmptyLayout();
                break;
            case R.id.menu_status_error:
                // 加载失败
                statusLayoutManager.showErrorLayout();
                break;
            case R.id.menu_status_success:
                // 加载成功，显示原布局
                statusLayoutManager.showSuccessLayout();
                break;
            case R.id.menu_status_customer:
                // 自定义状态布局
                statusLayoutManager.showCustomLayout(R.layout.layout_custome, R.id.tv_customer, R.id.tv_customer1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
