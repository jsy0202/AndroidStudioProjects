package hansung.ac.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val db = FirebaseFirestore.getInstance()
    private val itemsCollectionRef = db.collection("items")
    private lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewItems)
        recyclerView.layoutManager = LinearLayoutManager(this)

        switch = findViewById(R.id.switch1)

        val userEmail = intent.getStringExtra("user_email") ?: ""

        adapter = MyAdapter(this, mutableListOf(), userEmail) // userEmail을 adapter 초기화 시에 전달
        recyclerView.adapter = adapter

        switch.setOnCheckedChangeListener { _, isChecked ->
            fetchDataFromFirestore(isChecked)
        }

        val textView = findViewById<TextView>(R.id.textView1)
        textView.text = "$userEmail"

        val logoutButton = findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.add).setOnClickListener {
            val intent = Intent(this, FirestoreActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchDataFromFirestore(switch.isChecked)
    }

    private fun fetchDataFromFirestore(status: Boolean) {
        val query = if (status) {
            itemsCollectionRef.whereEqualTo("status", true)
        } else {
            itemsCollectionRef
        }

        query.get()
            .addOnSuccessListener { querySnapshot ->
                val itemList = mutableListOf<Item>()

                for (document in querySnapshot) {
                    val item = Item(document)
                    itemList.add(item)
                }

                adapter.updateList(itemList)
            }
            .addOnFailureListener { exception ->
                // Handle the failure to fetch data from Firestore
            }
    }
}
