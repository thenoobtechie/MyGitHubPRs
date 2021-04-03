package co.jp.catech.itohen.mygitcprs.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.jp.catech.itohen.mygitcprs.R
import co.jp.catech.itohen.mygitcprs.Utility
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import kotlinx.android.synthetic.main.layout_cpr_list_item.view.*
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CPRListAdapter(var cPRList: List<CPRModel>): RecyclerView.Adapter<CPRViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CPRViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_cpr_list_item,
            parent,
            false
        )
        return CPRViewHolder(view);
    }

    override fun onBindViewHolder(holder: CPRViewHolder, position: Int) {
        holder.bind(cPRList[position]);
    }

    override fun getItemCount(): Int = cPRList.size
    fun updateData(list: List<CPRModel>) {

        cPRList = list
        notifyDataSetChanged()
    }

}

class CPRViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val dateFormat = SimpleDateFormat(Utility.displayDateFormat, Locale.getDefault())

    fun bind(cPRItem: CPRModel) {
        itemView.tvTitle.text = cPRItem.title
        itemView.tvCreatedAt.text = cPRItem.createdDate?.let { itemView.context.getString(R.string.created_date_format, dateFormat.format(it)) }
        itemView.tvClosedAt.text = cPRItem.closedDate?.let { itemView.context.getString(R.string.closed_date_format, dateFormat.format(it)) }
        itemView.tvUserName.text = cPRItem.user?.name ?: "NA"
        Utility.loadImage(cPRItem.user?.avatarImg ?: "", itemView.imgUserImg)
    }
}