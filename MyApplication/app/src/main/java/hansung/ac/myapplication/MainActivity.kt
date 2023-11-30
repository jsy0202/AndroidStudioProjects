package hansung.ac.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myView = findViewById<MyView>(R.id.view)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedShape = when (radioButton.id) {
                R.id.radioRect -> MyView.Shape.RECTANGLE
                R.id.radioCircle -> MyView.Shape.CIRCLE
                else -> MyView.Shape.RECTANGLE // 기본값으로 사각형 선택
            }
            myView.setSelectedShape(selectedShape)
        }
    }
}
