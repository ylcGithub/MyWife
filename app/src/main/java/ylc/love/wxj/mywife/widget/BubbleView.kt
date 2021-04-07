package ylc.love.wxj.mywife.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import ylc.love.wxj.mywife.R
import kotlin.math.sqrt

/**
 * 带有三角形的气泡背景控件
 * 控件可设置圆角
 */
class BubbleView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    private var mPaint: Paint = Paint()
    private val mColor: Int
    private var mDirection: Int = 0
    private var mTriangleWidth: Float = 0f
    private var mTriangleHeight: Float = 0f
    private var mRatio: Float = 1f
    private val radiusArray = FloatArray(8)
    private val mTrianglePath: Path = Path()
    private val mBackgroundPath: Path = Path()
    private val mBackgroundRectF: RectF = RectF()

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)

    private fun init() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mDirection = TOP
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mDirection) {
            TOP -> {
                mBackgroundRectF.set(0f, mTriangleHeight, width.toFloat(), height.toFloat())
                val startX = (width - mTriangleWidth) * mRatio / (1 + mRatio)
                mTrianglePath.moveTo(startX, mTriangleHeight + 1)
                mTrianglePath.lineTo(startX + mTriangleWidth / 2, 0f)
                mTrianglePath.lineTo(startX + mTriangleWidth, mTriangleHeight + 1)
                mTrianglePath.close()
            }
            BOTTOM -> {
                mBackgroundRectF.set(0f, 0f, width.toFloat(), height.toFloat() - mTriangleHeight)
                val startX = (width - mTriangleWidth) * mRatio / (1 + mRatio)
                mTrianglePath.moveTo(startX, height - mTriangleHeight - 1)
                mTrianglePath.lineTo(startX + mTriangleWidth / 2, height.toFloat())
                mTrianglePath.lineTo(startX + mTriangleWidth, height - mTriangleHeight - 1)
                mTrianglePath.close()
            }
            RIGHT -> {
                mBackgroundRectF.set(0f, 0f, width.toFloat() - mTriangleHeight, height.toFloat())
                val startY = (height - mTriangleWidth) * mRatio / (1 + mRatio)
                mTrianglePath.moveTo(width - mTriangleHeight - 1, startY)
                mTrianglePath.lineTo(width.toFloat(), startY + mTriangleWidth / 2)
                mTrianglePath.lineTo(width - mTriangleHeight - 1, startY + mTriangleWidth)
                mTrianglePath.close()
            }
            LEFT -> {
                mBackgroundRectF.set(mTriangleHeight, 0f, width.toFloat(), height.toFloat())
                val startY = (height - mTriangleWidth) * mRatio / (1 + mRatio)
                mTrianglePath.moveTo(mTriangleHeight - 1, startY)
                mTrianglePath.lineTo(0f, startY + mTriangleWidth / 2)
                mTrianglePath.lineTo(mTriangleHeight - 1, startY + mTriangleWidth)
                mTrianglePath.close()
            }
        }
        mBackgroundPath.addRoundRect(mBackgroundRectF, radiusArray, Path.Direction.CW)
        //绘制圆角背景
        canvas.drawPath(mBackgroundPath, mPaint)
        //绘制三角形 气泡
        canvas.drawPath(mTrianglePath, mPaint)
    }

    companion object {
        private const val TOP = 0
        private const val BOTTOM = 1
        private const val RIGHT = 2
        private const val LEFT = 3
        private const val DEFAULT_COLOR = R.color.white
    }

    init {
        init()
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BubbleView, 0, 0)
        mColor = typedArray.getColor(
            R.styleable.BubbleView_bv_color,
            ContextCompat.getColor(getContext(), DEFAULT_COLOR)
        )
        mDirection = typedArray.getInt(R.styleable.BubbleView_bv_direction, mDirection)
        mTriangleWidth = typedArray.getDimension(R.styleable.BubbleView_bv_tr_width, 32f)
        mTriangleHeight = sqrt(mTriangleWidth * mTriangleWidth * 3 / 4)
        mRatio = typedArray.getFloat(R.styleable.BubbleView_bv_ratio, 1f)
        val radius = typedArray.getDimension(R.styleable.BubbleView_bv_radius, 0f)
        var leftTopRadius = typedArray.getDimension(R.styleable.BubbleView_bv_leftTopRadius, 0f)
        var leftBottomRadius =
            typedArray.getDimension(R.styleable.BubbleView_bv_leftBottomRadius, 0f)
        var rightTopRadius = typedArray.getDimension(R.styleable.BubbleView_bv_rightTopRadius, 0f)
        var rightBottomRadius =
            typedArray.getDimension(R.styleable.BubbleView_bv_rightBottomRadius, 0f)
        typedArray.recycle()
        mPaint.color = mColor
        if (radius > 0) {
            leftTopRadius = radius
            leftBottomRadius = radius
            rightBottomRadius = radius
            rightTopRadius = radius
        }
        radiusArray[0] = leftTopRadius
        radiusArray[1] = leftTopRadius
        radiusArray[2] = rightTopRadius
        radiusArray[3] = rightTopRadius
        radiusArray[4] = rightBottomRadius
        radiusArray[5] = rightBottomRadius
        radiusArray[6] = leftBottomRadius
        radiusArray[7] = leftBottomRadius
    }


}