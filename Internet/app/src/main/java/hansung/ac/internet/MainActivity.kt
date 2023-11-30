package hansung.ac.internet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Owner(val login: String)
data class Repo(val name: String, val owner: Owner, val url: String)

interface RestApi {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}

class MyAdapter(var items: List<Repo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() { // 수정
    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvRepo = v.findViewById<TextView>(R.id.tvRepo)
        val tvOwner = v.findViewById<TextView>(R.id.tvOwner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
        val viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repo = items[position]
        holder.tvRepo.text = repo.name
        holder.tvOwner.text = repo.owner.login
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val api = retrofit.create(RestApi::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter(emptyList())
        recyclerView.adapter = adapter

        val editUsername = findViewById<EditText>(R.id.editUsername)
        val buttonQuery = findViewById<Button>(R.id.buttonQuery)

        buttonQuery.setOnClickListener {
            val username = editUsername.text.toString()
            if (username.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val repos = api.listRepos(username)
                    withContext(Dispatchers.Main) {
                        adapter.items = repos
                        adapter.notifyDataSetChanged()
                        recyclerView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT // RecyclerView의 높이 설정
                    }
                }
            }
        }
    }
}

