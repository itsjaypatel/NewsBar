package com.example.newsbar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private var mAdapter: NewsListAdapter? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsListAdapter(this)
        fetchData();
        //Log.d("Recycler View",recyclerView.id.toString()
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {

        // Instantiate the RequestQueue.
        val queue =MySingleton.getInstance(this.applicationContext).requestQueue
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val newsList: ArrayList<News> = ArrayList()
                val newsJsonArray = response.getJSONArray("articles")
                for(index in 0 until newsJsonArray.length()){
                    val newsJson = newsJsonArray.getJSONObject(index)
                    if(newsJson.getString("author").equals("null")){
                        newsJson.put("author","Unknown")
                    }
                    val news = News(
                        newsJson.getString("title"),
                        newsJson.getString("urlToImage"),
                        newsJson.getString("author"),
                        newsJson.getString("url")
                    )
                    newsList.add(news)
                }

                mAdapter?.updateNews(newsList);
            },
            { error ->
                Log.d("News Fetching Error","Error while fetching news... $error")
            }
        )


        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        //Toast.makeText(this,"$item is clicked!!",Toast.LENGTH_SHORT).show()
    }
}