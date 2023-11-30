package hansung.ac.myapplication

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyView : View {

    private var selectedShape: Shape = Shape.RECTANGLE // 기본값으로 사각형 선택

    private var lastTouchX: Float = 0F
    private var lastTouchY: Float = 0F

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.RED

        when (selectedShape) {
            Shape.RECTANGLE -> {
                canvas.drawRect(
                    lastTouchX,
                    lastTouchY,
                    lastTouchX + 100F, // 사각형 너비
                    lastTouchY + 100F, // 사각형 높이
                    paint
                )
            }
            Shape.CIRCLE -> {
                val radius = 50F // 원의 반지름
                canvas.drawCircle(
                    lastTouchX + radius, // 원 중심 X
                    lastTouchY + radius, // 원 중심 Y
                    radius,
                    paint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                invalidate() // 뷰를 다시 그리도록 요청
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setSelectedShape(shape: Shape) {
        selectedShape = shape
    }

    enum class Shape {
        RECTANGLE,
        CIRCLE
    }
}
