package com.apps.ericksonfilipe.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.apps.ericksonfilipe.newsapp.R.id.section;


public class BookListAdapter extends ArrayAdapter<Article> {

    private Context context;

    public BookListAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        }
        ArticleViewHolder articleViewHolder = new ArticleViewHolder();
        articleViewHolder.itemLayout = (LinearLayout) convertView.findViewById(R.id.item_layout);
        articleViewHolder.title = (TextView) convertView.findViewById(R.id.title);
        articleViewHolder.section = (TextView) convertView.findViewById(section);

        final Article article = getItem(position);

        articleViewHolder.title.setText(article.getTitle());
        articleViewHolder.section.setText(context.getString(R.string.category_text) + article.getSection());
        articleViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));
                context.startActivity(intent);
            }
        });
        convertView.setTag(articleViewHolder);
        return convertView;
    }

    static class ArticleViewHolder {
        LinearLayout itemLayout;
        TextView title;
        TextView section;
    }
}
