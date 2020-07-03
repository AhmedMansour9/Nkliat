package com.nkliat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chicchicken.Model.parameters
import com.nkliat.Adapter.FurntireParamters_Adapter
import com.nkliat.Model.Paramters_Response
import com.nkliat.R
import com.nkliat.ViewModel.Paramters_ViewModel
import com.tayser.utils.ChangeLanguage
import kotlinx.android.synthetic.main.fragment_form__furntiure_type.view.*
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Form_FurntiureType.newInstance] factory method to
 * create an instance of this fragment.
 */
class Form_FurntiureType : Fragment() , SwipeRefreshLayout.OnRefreshListener{
   lateinit var root:View
    companion object {
        var IdList: ArrayList<parameters> = ArrayList()
        fun ValidateList():Boolean{
            if(IdList.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_form__furntiure_type, container, false)
        SwipRefresh()
        return root
    }
    fun SwipRefresh(){
        root.SwipTypes.setOnRefreshListener(this)
        root.SwipTypes.isRefreshing=true
        root.SwipTypes.isEnabled = true
        root.SwipTypes.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipTypes.post(Runnable {
            getParamters()

        })
    }

    fun getParamters(){
        var Sizes: Paramters_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            Paramters_ViewModel::class.java)
        requireContext().applicationContext?.let {
            Sizes.getData("", ChangeLanguage.getLanguage(requireContext().applicationContext), it)
                .observe(viewLifecycleOwner, Observer<Paramters_Response> { loginmodel ->
                    root.SwipTypes.isRefreshing=false
                    if(loginmodel!=null) {
                    if(loginmodel.data!!.size>0) {
                        val listAdapter =
                            FurntireParamters_Adapter(requireContext().applicationContext,
                                loginmodel.data as List<Paramters_Response.Data>
                            )
                        root.recycler_Types.layoutManager = GridLayoutManager(
                            requireContext().applicationContext,2,
                            GridLayoutManager.VERTICAL,
                            false
                        )
                        root.recycler_Types.setAdapter(listAdapter)
                    }
                }


            })
        }

    }

    override fun onRefresh() {
        getParamters()

    }

}