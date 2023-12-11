package hansung.ac.project1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Item(
    val id: String,
    val name: String,
    val price: Int,
    val description: String,
    val seller: String, // Add seller property
    val status: String // Add status property
) {
    constructor(doc: QueryDocumentSnapshot) :
            this(
                doc.id,
                doc["name"].toString(),
                doc["price"].toString().toIntOrNull() ?: 0,
                doc["description"].toString(),
                doc["seller"].toString(), // Initialize seller from QueryDocumentSnapshot
                doc["status"].toString() // Initialize status from QueryDocumentSnapshot
            )

    constructor(key: String, map: Map<*, *>) :
            this(
                key,
                map["name"].toString(),
                map["price"].toString().toIntOrNull() ?: 0,
                map["description"].toString(),
                map["seller"].toString(), // Initialize seller from Map
                map["status"].toString() // Initialize status from Map
            )
}


class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

class MyAdapter(
    private val context: Context,
    private var items: List<Item>,
    val userEmail: String )
    : RecyclerView.Adapter<MyViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(itemId: String)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun updateList(newList: List<Item>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.view.findViewById<TextView>(R.id.textID).text = item.name
        holder.view.findViewById<TextView>(R.id.textPrice).text = item.price.toString()
        holder.view.findViewById<TextView>(R.id.content).text = item.seller
        holder.view.findViewById<TextView>(R.id.sender).text = item.status

        holder.view.findViewById<TextView>(R.id.textID).setOnClickListener {
            itemClickListener?.onItemClick(item.id)
        }

        holder.view.setOnClickListener {
            if (item.seller == userEmail) {
                val intent = Intent(context, ItemModifyActivity::class.java)
                intent.putExtra("itemId", item.id)
                intent.putExtra("itemName", item.name)
                intent.putExtra("itemPrice", item.price)
                intent.putExtra("itemSeller", item.seller)
                intent.putExtra("itemDescription", item.description)
                intent.putExtra("itemStatus", item.status)
                context.startActivity(intent)
            } else {
                val intent = Intent(context, ItemDetailActivity::class.java)
                intent.putExtra("itemId", item.id)
                intent.putExtra("itemName", item.name)
                intent.putExtra("itemPrice", item.price)
                intent.putExtra("itemSeller", item.seller)
                intent.putExtra("itemDescription", item.description)
                intent.putExtra("itemStatus", item.status)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = items.size
}
