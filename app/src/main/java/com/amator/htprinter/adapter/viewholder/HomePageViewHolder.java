package com.amator.htprinter.adapter.viewholder;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amator.htprinter.R;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.refreshRecyclerView.adapter.BaseViewHolder;
import com.amator.htprinter.ui.activity.BannerContentActivity;
import com.amator.htprinter.ui.activity.HomePageContentActivity;
import com.amator.htprinter.uitl.RxLogTool;


/**
 * Created by AmatorLee on 2018/4/11.
 */

public class HomePageViewHolder extends BaseViewHolder<HomePage> {

    private TextView txt_artical_author;
    private TextView txt_artical_name;
    private TextView txt_artical_tag;
    private TextView txt_artical_time;


    public HomePageViewHolder(ViewGroup parent) {
        super(parent, R.layout.layout_box_item);
    }


    @Override
    public void onInitializeView() {
        txt_artical_name = findViewById(R.id.txt_artical_name);
        txt_artical_author = findViewById(R.id.txt_artical_author_value);
        txt_artical_tag = findViewById(R.id.txt_artical_tag_value);
        txt_artical_time = findViewById(R.id.txt_artical_time);
    }

    @Override
    public void setData(HomePage data) {
        txt_artical_name.setText(data.getTitle());
        txt_artical_time.setText(data.getNiceDate());
        txt_artical_tag.setText(data.getChapterName());
        txt_artical_author.setText(data.getAuthor());
    }

    @Override
    public void onItemViewClick(HomePage data) {
        super.onItemViewClick(data);
        RxLogTool.d("link: " + data.getLink());
        startContentActivity(data);
    }

    public void startContentActivity(HomePage homePage) {
        Intent intent = new Intent(itemView.getContext(), HomePageContentActivity.class);
        intent.putExtra("homePage", homePage);
        itemView.getContext().startActivity(intent);
    }
}
