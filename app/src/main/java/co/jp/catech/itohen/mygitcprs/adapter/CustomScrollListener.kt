package co.jp.catech.itohen.mygitcprs.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

//TODO fixme
abstract class CustomScrollListener: RecyclerView.OnScrollListener() {

    private val hideThreshHold = 20
    private var scrolledDistance = 0
    private var controlsVisible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        Log.v("TAG - onScrolled", "dx: $dx && dy: $dy")

        if (scrolledDistance > hideThreshHold && controlsVisible) {
            onHide()
            controlsVisible = false
            scrolledDistance = 0
        } else if (scrolledDistance < -hideThreshHold && !controlsVisible) {
            onShow()
            controlsVisible = true
            scrolledDistance = 0
        }

        if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
            scrolledDistance += dy
        }
    }

    abstract fun onHide()
    abstract fun onShow()
}