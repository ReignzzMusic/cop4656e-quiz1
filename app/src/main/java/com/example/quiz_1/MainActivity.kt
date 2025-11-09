package com.example.quiz_1

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import com.example.quiz_1.databinding.ActivityMainBinding
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val client = AsyncHttpClient();
    val params = RequestParams();

    private lateinit var binding: ActivityMainBinding;

    lateinit var recyclerView: RecyclerView;
    var adapter: RecyclerAdapter = RecyclerAdapter();

    var list: ArrayList<UserData> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = binding.recyclerView;
        recyclerView.addItemDecoration(ItemOffsetDecoration())
        setAdapter();

        binding.button.setOnClickListener { v ->
            list.clear()
            adapter.notifyDataSetChanged()
            query("https://jsonplaceholder.typicode.com/users")
        }

    }

    fun setAdapter() {

        var layoutManager = LinearLayoutManager(applicationContext);
        recyclerView.layoutManager = layoutManager;
        recyclerView.setItemAnimator(DefaultItemAnimator());
        recyclerView.adapter = adapter;
        adapter.usersList = list;
        println("Set adapter properly")
    }

    fun query(url: String) {
        binding.progressBar.visibility = View.VISIBLE;
        client[url, params, object :
            TextHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, response: String) {
                var results: JSONArray = JSONArray(response)
                println(results);

                for(i in 0 until results.length()) {

                    var user = results.getJSONObject(i);
                    var name = user.get("name").toString();
                    var email = user.get("email").toString();
                    var phoneNumber = user.get("phone").toString();

                    println(name);
                    println(email);
                    println(phoneNumber);

                    var newUserData = UserData();
                    newUserData.name = name;
                    newUserData.email = email;
                    newUserData.phone = phoneNumber;

                    list.add(newUserData);
                    adapter.notifyDataSetChanged();

                }
                binding.progressBar.visibility = View.GONE;
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                println("Failed.")
                Toast.makeText(applicationContext, "Failed to fetch data.", Toast.LENGTH_SHORT).show()
            }
        }]
    }

}