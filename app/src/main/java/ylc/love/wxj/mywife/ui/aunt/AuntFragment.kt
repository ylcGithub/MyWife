package ylc.love.wxj.mywife.ui.aunt

import ylc.love.wxj.mywife.R
import ylc.love.wxj.mywife.base.BaseFragment
import ylc.love.wxj.mywife.base.BaseOneLayoutAdapter
import ylc.love.wxj.mywife.base.BaseViewHolder
import ylc.love.wxj.mywife.databinding.AuntListItemBinding
import ylc.love.wxj.mywife.databinding.FragmentAuntBinding
import ylc.love.wxj.mywife.model.AuntBean

/**
 *@author YLC-D
 *@create on 2021/1/28 15
 *说明:
 */
class AuntFragment:BaseFragment<AuntViewModel,FragmentAuntBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_aunt

    override fun getViewModel(): AuntViewModel = getViewModelProvider(this).get(AuntViewModel::class.java)

    override fun initData() {
        mBinding.click = ClickProxy()
        mViewModel.getAllAuntBeans()
    }

    val listAdapter = object:BaseOneLayoutAdapter<AuntBean,AuntListItemBinding>(R.layout.aunt_list_item){
        override fun itemIsSame(oldItem: AuntBean, newItem: AuntBean): Boolean  = oldItem.id == newItem.id

        override fun onBindItem(bind: AuntListItemBinding, item: AuntBean, holder: BaseViewHolder) {
           if(item.id == -1L){

           }else{
               bind.tvTitle.text = ""
               bind.tvDes.text = ""
               bind.tvCon.text = ""
           }
        }

    }

    inner class ClickProxy{
        fun addAunt(){

        }
    }

}