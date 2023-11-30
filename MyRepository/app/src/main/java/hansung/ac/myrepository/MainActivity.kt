package hansung.ac.myrepository

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.*

class MyViewModel(context: Context) : ViewModel() {
    private val repository = MyRepository(context)
    val repos = repository.repos

    class Factory(val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MyViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

class MyAdapter(var items: List<Repo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
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
    private lateinit var myViewModel: MyViewModel
    private lateinit var editUsername: EditText
    private lateinit var startWorkerButton: Button
    private lateinit var stopWorkerButton: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUsername = findViewById(R.id.editUsername)
        startWorkerButton = findViewById(R.id.startWorker)
        stopWorkerButton = findViewById(R.id.stopWorker)
        recyclerView = findViewById(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(emptyList())

        myViewModel = ViewModelProvider(this, MyViewModel.Factory(this)).get(MyViewModel::class.java)

        startWorkerButton.setOnClickListener {
            val username = editUsername.text.toString()
            startWorker(username)
        }

        stopWorkerButton.setOnClickListener {
            stopWorker()
        }

        myViewModel.repos.observe(this) { reposD ->
            val repos = reposD.map {
                Repo(it.name, Owner(it.owner), "")
            }
            recyclerView.adapter = MyAdapter(repos)
        }
    }

    private fun startWorker(username: String) {
        val inputData = workDataOf("username" to username)
        val constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.UNMETERED)
            setRequiresBatteryNotLow(true)
        }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            MyWorker.name,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun stopWorker() {
        WorkManager.getInstance(this).cancelUniqueWork(MyWorker.name)
    }
}
