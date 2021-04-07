package ylc.love.wxj.mywife.ui.game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup.LayoutParams
import android.widget.GridLayout
import com.orhanobut.hawk.Hawk
import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.utils.ResUtil
import ylc.love.wxj.mywife.utils.ScreenUtil
import kotlin.math.absoluteValue

/**
 *@author YLC-D
 *@create on 2021/3/1 11
 *说明:
 */
@SuppressLint("ClickableViewAccessibility")
class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : GridLayout(context, attrs, defStyle) {
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    var viewModel: SmallGameViewModel? = null

    companion object {
        const val LINES_KEY = "game_lines_key"
        const val BEST_SCORE = "game_best_score"
        const val MAX_SCORE = "game_max_score"
        const val space: Int = 10
        private var lines: Int = Hawk.get(LINES_KEY, 4)
        private var cardArray = Array(lines) { arrayOfNulls<Card>(lines) }
        private val emptyPoints = mutableListOf<Point>()
    }

    init {
        columnCount = lines
        setBackgroundColor(ResUtil.getColor(context, R.color.game_background))
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    offsetX = event.x - startX
                    offsetY = event.y - startY
                    judgingTheDirection(offsetX, offsetY)
                }
            }
            true
        }
        setPadding(space, space, space, space)
        addCards((ScreenUtil.getScreenWidth() - 2 * space) / lines)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        addRandomCard()
        addRandomCard()
    }

    private fun addCards(cardWidth: Int) {
        val lp = LayoutParams(cardWidth, cardWidth)
        var card: Card
        for (y in 0 until lines) {
            for (x in 0 until lines) {
                card = Card(context, space, space, space, space)
                card.num = 0
                addView(card, lp)
                cardArray[x][y] = card
            }
        }
    }

    /**
     * 判断用户的手指滑动方向
     */
    private fun judgingTheDirection(offsetX: Float, offsetY: Float) {
        val xD = offsetX.absoluteValue > offsetY.absoluteValue
        when {
            xD && offsetX < -5 -> swipeLeft()
            xD && offsetX > 5 -> swipeRight()
            !xD && offsetY < -5 -> swipeUp()
            !xD && offsetY > 5 -> swipeDown()
        }
    }

    private fun swipeLeft() {
        var merge = false

        for (y in 0 until lines) {
            var x = 0
            while (x < lines) {
                for (x1 in x + 1 until lines) {
                    if (cardArray[x1][y]!!.num > 0) {
                        if (cardArray[x][y]!!.num == 0) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x1][y]!!,cardArray[x][y]!!,Point(x1, y), Point(x, y))
                            cardArray[x][y]!!.num = cardArray[x1][y]!!.num
                            cardArray[x1][y]!!.num = 0
                            x--
                            merge = true
                        } else if (cardArray[x][y]!!.num == cardArray[x1][y]!!.num) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x1][y]!!,cardArray[x][y]!!,Point(x1, y), Point(x, y))
                            cardArray[x][y]!!.num = cardArray[x1][y]!!.num * 2
                            cardArray[x1][y]!!.num = 0
                            viewModel?.addScore(cardArray[x][y]!!.num)
                            merge = true
                        }
                        break
                    }
                }
                x++
            }
        }
        if (merge) {
            addRandomCard()
            checkComplete()
        }
    }

    private fun swipeRight() {
        var merge = false
        for (y in 0 until lines) {
            var x = lines - 1
            while (x >= 0) {
                for (x1 in x - 1 downTo 0) {
                    if (cardArray[x1][y]!!.num > 0) {
                        if (cardArray[x][y]!!.num == 0) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x1][y]!!,cardArray[x][y]!!,Point(x1, y), Point(x, y))
                            cardArray[x][y]!!.num = cardArray[x1][y]!!.num
                            cardArray[x1][y]!!.num = 0
                            x++
                            merge = true
                        } else if (cardArray[x][y]!!.num == cardArray[x1][y]!!.num) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x1][y]!!,cardArray[x][y]!!,Point(x1, y), Point(x, y))
                            cardArray[x][y]!!.num = cardArray[x1][y]!!.num * 2
                            cardArray[x1][y]!!.num = 0
                            viewModel?.addScore(cardArray[x][y]!!.num)
                            merge = true
                        }
                        break
                    }
                }
                x--
            }
        }
        if (merge) {
            addRandomCard()
            checkComplete()
        }
    }

    private fun swipeUp() {
        var merge = false

        for (x in 0 until lines) {
            var y = 0
            while (y < lines) {
                for (y1 in y + 1 until lines) {
                    if (cardArray[x][y1]!!.num > 0) {
                        if (cardArray[x][y]!!.num == 0) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x][y1]!!,cardArray[x][y]!!,Point(x, y1), Point(x, y))
                            cardArray[x][y]!!.num = cardArray[x][y1]!!.num
                            cardArray[x][y1]!!.num = 0
                            y--
                            merge = true
                        } else if (cardArray[x][y]!!.num == cardArray[x][y1]!!.num) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x][y1]!!,cardArray[x][y]!!,Point(x, y1), Point(x, y))
                            cardArray[x][y]!!.num =  cardArray[x][y1]!!.num * 2
                            cardArray[x][y1]!!.num = 0
                            viewModel?.addScore(cardArray[x][y]!!.num)
                            merge = true
                        }
                        break
                    }
                }
                y++
            }
        }
        if (merge) {
            addRandomCard()
            checkComplete()
        }
    }

    private fun swipeDown() {
        var merge = false

        for (x in 0 until lines) {
            var y = lines - 1
            while (y >= 0) {
                for (y1 in y - 1 downTo 0) {
                    if (cardArray[x][y1]!!.num > 0) {
                        if (cardArray[x][y]!!.num == 0) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x][y1]!!,cardArray[x][y]!!,Point(x, y1), Point(x, y))
                            cardArray[x][y]!!.num = cardArray[x][y1]!!.num
                            cardArray[x][y1]!!.num = 0
                            y++
                            merge = true
                        } else if (cardArray[x][y]!!.num == cardArray[x][y1]!!.num) {
                            viewModel?.swipe?.value = SwipePoint(cardArray[x][y1]!!,cardArray[x][y]!!,Point(x, y1), Point(x, y))
                            cardArray[x][y]!!.num =cardArray[x][y1]!!.num * 2
                            cardArray[x][y1]!!.num = 0
                            viewModel?.addScore(cardArray[x][y]!!.num)
                            merge = true
                        }
                        break
                    }
                }
                y--
            }
        }
        if (merge) {
            addRandomCard()
            checkComplete()
        }
    }


    private fun checkComplete() {
        var complete = true
        ALL@ for (y in 0 until lines) {
            for (x in 0 until lines) {
                if (cardArray[x][y]!!.num == 0 ||
                    x > 0 && cardArray[x][y]!!.equal(cardArray[x - 1][y]!!) ||
                    x < lines - 1 && cardArray[x][y]!!.equal(cardArray[x + 1][y]!!) ||
                    y > 0 && cardArray[x][y]!!.equal(cardArray[x][y - 1]!!) ||
                    y < lines - 1 && cardArray[x][y]!!.equal(cardArray[x][y + 1]!!)
                ) {
                    complete = false
                    break@ALL
                }
            }
        }
        if (complete) {
            AlertDialog.Builder(context).setTitle("你好").setMessage("游戏结束").setPositiveButton(
                "重新开始"
            ) { _, _ -> restartGame() }.show()
        }
    }

    /**
     * 布局新的边
     */
    fun newLayout(column:Int){
        removeAllViews()
        lines = column
        cardArray = Array(lines) { arrayOfNulls(lines) }
        columnCount = column
        addCards((ScreenUtil.getScreenWidth() - 2 * space) / lines)
        restartGame()
    }

    fun restartGame() {
        for (y in 0 until lines) {
            for (x in 0 until lines) {
                cardArray[x][y]!!.num = 0
            }
        }
        addRandomCard()
        addRandomCard()
        viewModel?.scoreValue = 0
        viewModel?.bestScoreValue = Hawk.get(BEST_SCORE, 0)
        viewModel?.addScore(0)
    }

    private fun addRandomCard() {
        emptyPoints.clear()
        for (y in 0 until lines) {
            for (x in 0 until lines) {
                if (cardArray[x][y]!!.num <= 0) {
                    emptyPoints.add(Point(x, y))
                }
            }
        }

        if (emptyPoints.size > 0) {
            val p: Point = emptyPoints.removeAt((Math.random() * emptyPoints.size).toInt())
            cardArray[p.x][p.y]!!.num = if (Math.random() > 0.1) 2 else 4
            GameAnimUtil.showScaleAnim(cardArray[p.x][p.y]!!)
        }
    }

}