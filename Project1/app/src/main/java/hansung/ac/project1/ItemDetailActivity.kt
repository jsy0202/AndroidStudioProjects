package hansung.ac.project1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val itemName = intent.getStringExtra("itemName")
        val itemPrice = intent.getIntExtra("itemPrice", 0)
        val itemDescription = intent.getStringExtra("itemDescription")
        val itemSeller = intent.getStringExtra("itemSeller")
        val itemStatus = intent.getStringExtra("itemStatus")

        val itemNameTextView = findViewById<TextView>(R.id.itemNameTextView2)
        itemNameTextView.text = itemName
        val itemPriceTextView = findViewById<TextView>(R.id.itemPrice)
        itemPriceTextView.text = itemPrice.toString()
        val itemDescriptionTextView = findViewById<TextView>(R.id.itemDescription2)
        itemDescriptionTextView.text = itemDescription
        val itemSellerTextView = findViewById<TextView>(R.id.itemSeller2)
        itemSellerTextView.text = itemSeller
        val itemStatusTextView = findViewById<TextView>(R.id.itemStatus)
        itemStatusTextView.text = itemStatus
    }
}
