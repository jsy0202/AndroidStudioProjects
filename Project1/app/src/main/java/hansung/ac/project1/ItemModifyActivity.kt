package hansung.ac.project1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ItemModifyActivity : AppCompatActivity() {

    private lateinit var switch2: Switch
    private lateinit var editPrice2: EditText
    private lateinit var buttonModify: Button

    private val db = FirebaseFirestore.getInstance()
    private val itemsCollectionRef = db.collection("items")

    private lateinit var itemId: String
    private lateinit var itemStatus: String
    private var itemPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        val itemNameTextView = findViewById<TextView>(R.id.itemNameTextView2)
        val itemDescription = findViewById<TextView>(R.id.itemDescription2)
        val itemSeller = findViewById<TextView>(R.id.itemSeller2)

        itemNameTextView.text = intent.getStringExtra("itemName")
        itemDescription.text = intent.getStringExtra("itemDescription")
        itemSeller.text = intent.getStringExtra("itemSeller")

        switch2 = findViewById(R.id.switch2)
        editPrice2 = findViewById(R.id.editPrice2)
        buttonModify = findViewById(R.id.buttonModify)

        itemId = intent.getStringExtra("itemId") ?: ""
        itemStatus = intent.getStringExtra("itemStatus") ?: ""
        itemPrice = intent.getIntExtra("itemPrice", 0)

        // 초기값 설정
        switch2.isChecked = itemStatus.toBoolean()
        editPrice2.setText(itemPrice.toString())

        // 수정 버튼 클릭 시
        buttonModify.setOnClickListener {
            val status = switch2.isChecked
            val priceText = editPrice2.text.toString()

            if (priceText.isNotEmpty()) {
                val price = priceText.toInt()

                // 데이터베이스 업데이트
                itemsCollectionRef.document(itemId)
                    .update(
                        mapOf(
                            "status" to status,
                            "price" to price
                        )
                    )
                    .addOnSuccessListener {
                        onBackPressed()
                    }
                    .addOnFailureListener { exception ->
                        // 업데이트 실패 처리
                    }
            }
        }
    }
}
