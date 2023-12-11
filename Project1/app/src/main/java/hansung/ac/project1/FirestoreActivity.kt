package hansung.ac.project1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items")
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)

        findViewById<Button>(R.id.buttonAddUpdate)?.setOnClickListener {
            addItem()
        }
    }

    private fun addItem() {
        val itemName = findViewById<EditText>(R.id.editItemName).text.toString()
        val itemPrice = findViewById<EditText>(R.id.editPrice2).text.toString().toIntOrNull() ?: 0
        val descriptionText = findViewById<EditText>(R.id.editDescription).text.toString()

        // 현재 로그인한 사용자의 이메일 가져오기
        val currentUser = auth.currentUser
        val sellerEmail = currentUser?.email

        // 상품 정보 작성
        val item = hashMapOf(
            "name" to itemName,
            "price" to itemPrice,
            "description" to descriptionText,
            "seller" to sellerEmail,
            "status" to true
        )

        // Firestore에 상품 정보 추가
        itemsCollectionRef.add(item)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                // 성공적으로 등록되었을 때 처리
                Toast.makeText(this, "상품 등록 성공", Toast.LENGTH_SHORT).show()
                finish() // FirestoreActivity 종료
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                // 등록 실패 시 처리
                Toast.makeText(this, "상품 등록 실패", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        const val TAG = "FirestoreActivity"
    }
}
