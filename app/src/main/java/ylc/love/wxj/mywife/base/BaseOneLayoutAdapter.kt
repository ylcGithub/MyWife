package ylc.love.wxj.mywife.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 *@author YLC-D
 *@create on 2020/12/1 09
 *说明: 数据源E 要实现 hashCode 和 equals 方法 以便数据的比较 （kotlin 中的data class 已经默认实现了这两个方法不用特意实现）
 */
abstract class BaseOneLayoutAdapter<E, VB : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    RecyclerView.Adapter<BaseViewHolder>() {
    //数据源列表
    private val dataList: ArrayList<E> = ArrayList()

    override fun getItemCount(): Int = dataList.size

    open fun updateList(list: List<E>?, isRefresh: Boolean = false) {
        val oldList = ArrayList(dataList)
        if (isRefresh) dataList.clear()
        list?.let { dataList.addAll(it)}
        val myDiffCallBack = MyDiffCallBack(oldList, dataList)
        val calculateDiff = DiffUtil.calculateDiff(myDiffCallBack, true)
        calculateDiff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):BaseViewHolder {
        val vb = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false)
        return BaseViewHolder(vb.root)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<VB>(holder.itemView)
        binding?.let {
            this.onBindItem(it, dataList[position], holder)
            it.executePendingBindings()
        }
    }

    inner class MyDiffCallBack(private val oldList: List<E>, private val newList: List<E>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            itemIsSame(oldList[oldItemPosition], newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.hashCode() == newItem.hashCode() && oldItem?.equals(newItem) ?: false
        }
    }

    /**
     * 列表更改前后 判断item是否是同一个
     *
     * @param oldItem 旧列表的item
     * @param newItem 新列表的item
     * @return 是否相同
     */
    protected abstract fun itemIsSame(oldItem: E, newItem: E): Boolean

    /**
     * 数据绑定动态交给需要使用的地方去实现
     *
     * @param bind itemLayout 的 数据绑定回调
     * @param item    数据item
     * @param holder  每条数据对应的holder 主要用来后去该条数据的下标 holder.getAdapterPosition();
     */
    protected abstract fun onBindItem(bind: VB, item: E, holder: BaseViewHolder)

    fun getItem(position: Int): E? {
        return if (position < 0 || position > itemCount) {
            null
        } else {
            dataList[position]
        }
    }

    fun deleteItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun deleteItem(item:E){
        dataList.remove(item)
        notifyItemRemoved(dataList.indexOf(item))
        notifyDataSetChanged()
    }
}