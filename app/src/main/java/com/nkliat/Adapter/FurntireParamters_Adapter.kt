package com.nkliat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chicchicken.Model.parameters
import com.chicchicken.Model.Checkboxs
import com.nkliat.Fragments.Form_FurntiureType
import com.nkliat.Model.Paramters_Response
import com.nkliat.R
import com.tayser.utils.CustomToast
import kotlinx.android.synthetic.main.row_paramters.view.*

class FurntireParamters_Adapter (context: Context, val userList: List<Paramters_Response.Data>)
    : RecyclerView.Adapter<FurntireParamters_Adapter.ViewHolder>() {

    var row_index:Int = -1
    var context: Context =context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FurntireParamters_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_paramters, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: FurntireParamters_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_Title.text=userList.get(position).title



        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if(!holder.itemView.E_Value.text.isNullOrEmpty()){
                    if( holder.itemView.E_Value.text.toString().toInt()==0){
                        holder.itemView.checkBox.isChecked=false
                        return@setOnCheckedChangeListener
                        CustomToast.toastIconError(context.resources.getString(R.string.validateinput)+" "+
                                userList.get(position).title,
                            context
                        )
                    }

                    holder.itemView.checkBox.isChecked=true
                    val puthesData = Checkboxs()
                    puthesData.id=userList.get(position).id!!
                    if (Form_FurntiureType.IdList.isEmpty()) {
                        val checkbox_id = parameters()
                        checkbox_id.setId(userList.get(position).id)
                        checkbox_id.setTitle(holder.itemView.E_Value.text.toString().toInt())
                        Form_FurntiureType.IdList.add(checkbox_id)
                    } else {
                        val poistion = userList.get(position).id
                        for (i in Form_FurntiureType.IdList.indices) {
                            if (Form_FurntiureType.IdList.get(i).getId() === poistion) {
                                Form_FurntiureType.IdList.removeAt(i)
                                break
                            }
                        }

                        val checkbox_id = parameters()
                        checkbox_id.setTitle(holder.itemView.E_Value.text.toString().toInt())
                        checkbox_id.setId(userList.get(position).id)
                        Form_FurntiureType.IdList.add(checkbox_id)
                    }
                }else {
                    CustomToast.toastIconError(context.resources.getString(R.string.validateinput)+" "+
                            userList.get(position).title,context)
                    holder.itemView.checkBox.isChecked=false
                    return@setOnCheckedChangeListener
                }

            } else {
                val poistio = userList.get(position).id
                for (i in Form_FurntiureType.IdList.indices) {
                    if (Form_FurntiureType.IdList.get(i).getId() === poistio) {
                        Form_FurntiureType.IdList.removeAt(i)
                        break
                    }
                }
            }

        }


    }
//    fun onClickCheck(onClickProductColorView: ChechBox_View) {
//        this.checkbox = onClickProductColorView
//    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        private val context: Context = itemView.context
        var checkBox = itemView.findViewById(R.id.checkBox) as CheckBox

    }

}