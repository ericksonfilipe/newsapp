package com.apps.ericksonfilipe.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<Article>> {

    public NewsLoader(Context context) {
        super(context);
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        String response = NewsAPI.getNews();
        if (response != null) {
            return parseJson(response);
        }
        return null;
    }

    private List<Article> parseJson(String json) {
        List<Article> articlesList = new ArrayList<>();
        try {
            final JSONArray articles = new JSONObject(json).getJSONObject("response").getJSONArray("results");
            for (int i = 0; i < articles.length(); i++) {
                JSONObject articleJson = articles.getJSONObject(i);
                Article article = new Article();
                if (articleJson.has("webTitle")) {
                    article.setTitle(articleJson.getString("webTitle"));
                }
                if (articleJson.has("webUrl")) {
                    article.setUrl(articleJson.getString("webUrl"));
                }
                if (articleJson.has("sectionName")) {
                    article.setSection(articleJson.getString("sectionName"));
                }
                articlesList.add(article);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articlesList;
    }
}
