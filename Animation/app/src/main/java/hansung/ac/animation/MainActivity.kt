import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import hansung.ac.animation.R

class MainActivity : AppCompatActivity() {

    private lateinit var studentRadioButton: RadioButton
    private lateinit var staffRadioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentRadioButton = findViewById(R.id.radioStudent)
        staffRadioButton = findViewById(R.id.radioStaff)

        // 라디오 버튼 클릭 리스너 설정
        studentRadioButton.setOnClickListener {
            showStudentFragment()
        }

        staffRadioButton.setOnClickListener {
            showStaffFragment()
        }

        // 초기에는 Student 프래그먼트를 표시
        if (savedInstanceState == null) {
            showStudentFragment()
        }
    }

    private fun showStudentFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, StudentFragment())
        transaction.commit()
    }

    private fun showStaffFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, StaffFragment())
        transaction.commit()
    }

    class StudentFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.student_layout, container, false)
        }
    }

    class StaffFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.staff_layout, container, false)
        }
    }
}
