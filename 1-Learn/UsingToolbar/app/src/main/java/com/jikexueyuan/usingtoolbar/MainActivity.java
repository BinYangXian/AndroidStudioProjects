package com.jikexueyuan.usingtoolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by fangc on 2016/2/25.
 */
//自定义toolbar，及其控制，且在不同的android版本中几乎没有变化
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//自定义toolbar，且在不同的android版本中几乎没有变化

        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击btnClick隐藏或显示toolbar
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    if (actionBar.isShowing()) {
                        actionBar.hide();
                    } else {
                        actionBar.show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_layout,menu);

//        MenuItem menuItem = menu.findItem(R.id.search);//查找到相关按钮id
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);//加载menuItem并赋值给searchView
//        // 对象可以用之操作搜索结果、添加搜索内容等；下述是与main_layout.xml中collapseActionView属性配合的改写（
// 增加了两回调用方法的执行！）：
        MenuItem menuItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {//当点击搜索按钮时，true 搜索栏展开/
            // false搜索栏不展开 并执行此法。

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {//当点击按钮退出搜索时，true 搜索栏关闭/
            // false 搜索栏不关闭 并执行此法。
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()){
    case R.id.favorite:
        showToast("已添加为喜欢");
        return true;
    case R.id.settings:
        showToast("设置");
    return true;
    default:return super.onOptionsItemSelected(item);
}

    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void intentOtherActivity(View view){//这里需要view这个参数，是因为我们是从main_layout.xml文件中调用此法(
    //既是从main_layout对应的view中调用此法 )。
        startActivity(new Intent(this,OtherActivity.class));
    }
}
