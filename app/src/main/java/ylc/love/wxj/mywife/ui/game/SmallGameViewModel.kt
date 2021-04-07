package ylc.love.wxj.mywife.ui.game

import androidx.lifecycle.MutableLiveData
import com.orhanobut.hawk.Hawk
import ylc.love.wxj.mywife.base.BaseViewModel

/**
 *@author YLC-D
 *@create on 2021/2/26 17
 *说明:
 */
class SmallGameViewModel : BaseViewModel() {
    var scoreValue: Int = 0
    var bestScoreValue: Int = 0
    var maxScoreValue:Int = 0

    val score = MutableLiveData<String>()
    val bestScore = MutableLiveData<String>()
    val maxScore = MutableLiveData<String>()
    val swipe = MutableLiveData<SwipePoint>()

    fun addScore(s: Int) {
        scoreValue += s
        score.postValue("分数：$scoreValue")
        val best = if (scoreValue > bestScoreValue) {
            Hawk.put(GameView.BEST_SCORE, scoreValue)
            scoreValue
        } else bestScoreValue
        bestScore.postValue("最佳分数：$best")
        if(maxScoreValue == 0){
            maxScoreValue = Hawk.get(GameView.MAX_SCORE,0)
        }
        val max = if(s > maxScoreValue){
            Hawk.put(GameView.MAX_SCORE,s)
            maxScoreValue = s
            s
        }else maxScoreValue
        maxScore.value = "最大卡片：$max"
    }

}