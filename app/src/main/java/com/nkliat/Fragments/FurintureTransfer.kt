package com.nkliat.Fragments

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.nkliat.Activites.Navigation
import com.nkliat.Adapter.FurntireParamters_Adapter
import com.nkliat.Loading
import com.nkliat.Model.CreateOrder_Response
import com.nkliat.Model.FurntiuretransferRequest_Model
import com.nkliat.Model.Paramters_Response
import com.nkliat.R
import com.nkliat.ViewModel.Paramters_ViewModel
import com.nkliat.ViewModel.RequestFurntiure_ViewModel
import com.tayser.utils.ChangeLanguage
import com.tayser.utils.CustomToast
import kotlinx.android.synthetic.main.fragment_form__furntiure_type.view.*
import kotlinx.android.synthetic.main.fragment_furinture_transfer.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [FurintureTransfer.newInstance] factory method to
 * create an instance of this fragment.
 */
class FurintureTransfer : Fragment() {
    private var idx_state = 0
       var array_state= arrayOf<String>()
    private lateinit var DataSaver: SharedPreferences
    private  var Tags:String = ""


    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_furinture_transfer, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext().applicationContext)
        init()
        Tag="from"
        displayFragment(resources.getString(R.string.from))


        return root
    }
    fun CreatOrder(){
        var Furntiure= FurntiuretransferRequest_Model(
         "furniture",
         Form_Date.Datee,
         Form_FurntiureType.IdList,
         Form_From.TypeHouse,
         Form_From.No_Rooms,
         Form_From.No_Floor,
         Form_From.elevator,
         Form_From.Notes,
         Form_From.From_Lat,
         Form_From.From_Lng,
         Form_To.From_Lat,
            "create",
            Form_To.From_Lng,
//          Form_To.TypeHouse,
//          Form_To.No_Rooms,
//          Form_To.No_Floor,
//          "1",
            null
            )

        Loading.Show(requireContext())
        var Sizes: RequestFurntiure_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            RequestFurntiure_ViewModel::class.java)
        requireContext().applicationContext?.let {
            Sizes.getData(Furntiure, DataSaver.getString("token", null)!!, it)
                .observe(viewLifecycleOwner, Observer<CreateOrder_Response> { loginmodel ->
                Loading.Disable()
                    if(loginmodel.message.equals("Added successfully")){


                    root.image_from.clearColorFilter()
                    root.image_to.clearColorFilter()
                    root.image_furntiretype.clearColorFilter()
                    root.image_confirm.clearColorFilter()
                    root.lyt_finish.visibility=View.VISIBLE
                    root.lyt_Send.visibility=View.GONE
                    root.lyt_next.visibility =View.GONE
                    root.lyt_previous.visibility =View.GONE
                    val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction =
                        fragmentManager.beginTransaction()
                    var fragment: Fragment? = null
                    fragment = SuccessOrder()
                    Tag="success"
                    fragmentTransaction.replace(R.id.frame_content, fragment,Tag)
                    fragmentTransaction.commit()
                    }
                })
        }

    }
    private fun init() {
        Navigation.T_Title.text=resources.getString(R.string.furntiure)
        array_state=  arrayOf(resources.getString(R.string.from),resources.getString(R.string.to),
            resources.getString(R.string.type_furn),resources.getString(R.string.furn_date))
        root.image_to.setColorFilter(
            ContextCompat.getColor(requireContext(),R.color.grey_10),
            PorterDuff.Mode.SRC_ATOP
        )
        root.image_furntiretype.setColorFilter(
            ContextCompat.getColor(requireContext(),R.color.grey_10),
            PorterDuff.Mode.SRC_ATOP
        )
        root.image_confirm.setColorFilter(
            ContextCompat.getColor(requireContext(),R.color.grey_10),
            PorterDuff.Mode.SRC_ATOP
        )

        root.lyt_next.setOnClickListener {

            root.lyt_Send.visibility=View.GONE
            root.lyt_next.visibility =View.VISIBLE
            ValidateForm_From()
            ValidateForm_To()
            ValidateForm_TypeFurntiure()

        }
       root.lyt_previous.setOnClickListener(){
           if (idx_state < 1) return@setOnClickListener
           idx_state--
           displayFragment(array_state[idx_state])
           root.lyt_Send.visibility=View.GONE
           root.lyt_next.visibility =View.VISIBLE

       }

        root.lyt_Send.setOnClickListener {
           ValidateForm_Date()


        }

    }


    private fun displayFragment(state: String) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        var fragment: Fragment? = null
        refreshStepTitle()
        if (state.equals(

                resources.getString(R.string.from),
                ignoreCase = true
            )
        ) {
            root.Title.text= resources.getString(R.string.from)
            fragment = Form_From()
            Tag="from"
            root.tv_from.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_90))
            root.image_from.clearColorFilter()
            root.line_first.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_to.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
            root.line_first.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_to.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
            root.line_second.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_furntiretype.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
            root.line_three.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_confirm.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )

        } else if (state.equals(
                resources.getString(R.string.to),
                ignoreCase = true
            )
        ) {
            root.Title.text= resources.getString(R.string.to)
            fragment = Form_To()
            Tag="to"
            root.line_first.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
            root.image_from.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.blue),
                PorterDuff.Mode.SRC_ATOP
            )
            root.image_to.clearColorFilter()
            root.tv_to.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_90))
            root.line_second.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_furntiretype.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
            root.line_three.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_confirm.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
        }
        else if (state.equals(
                resources.getString(R.string.type_furn),
                ignoreCase = true
            )
        ) {
            root.Title.text= resources.getString(R.string.type_furn)
            fragment = Form_FurntiureType()
            Tag="type"
            root.line_second.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
            root.image_to.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.blue),
                PorterDuff.Mode.SRC_ATOP
            )
            root.image_furntiretype.clearColorFilter()
            root.tv_furntiretype.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_90))
          
            root.line_three.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_confirm.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
        }
        else if (state.equals(
                resources.getString(R.string.furn_date),
                ignoreCase = true
            )
        ) {
            root.Title.text= resources.getString(R.string.furn_date)
            fragment = Form_Date()
            Tag="date"
            root.line_three.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
            root.image_furntiretype.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.blue),
                PorterDuff.Mode.SRC_ATOP
            )
            root.image_confirm.clearColorFilter()
            root.tv_confirm.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_90))
        }
        if (fragment == null) return
        fragmentTransaction.replace(R.id.frame_content, fragment,Tag)
        fragmentTransaction.commit()
    }
    private fun refreshStepTitle() {
        root.tv_from.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_20))
        root.tv_to.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_20))
        root.tv_furntiretype.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_20))
        root.tv_confirm.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_20))
    }


    fun ValidateForm_From(){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val Fragment  =
            fragmentManager.findFragmentByTag("from")
        if (Fragment != null && Fragment.isVisible()) {
            if(!Form_From.ValidateTypeHouse() or !Form_From.ValidateNo_Floor() or !Form_From.ValidateNo_Rooms()
                or !Form_From.ValidateLat() or !Form_From.ValidateLng()){
                CustomToast.toastIconError(resources.getString(R.string.validate_feild),
                    context as Activity
                )
            }else {
                MoveToNextSteep()
            }
        }
    }
    fun ValidateForm_To(){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val Fragment  =
            fragmentManager.findFragmentByTag("to")
        if (Fragment != null && Fragment.isVisible()) {
            if(!Form_To.ValidateTypeHouse() or !Form_To.ValidateNo_Floor() or !Form_To.ValidateNo_Rooms()
                or !Form_To.ValidateLat() or !Form_To.ValidateLng()){

                CustomToast.toastIconError(resources.getString(R.string.validate_feild),
                    context as Activity
                )
            }else {
                MoveToNextSteep()
            }
        }
    }
    fun ValidateForm_TypeFurntiure(){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val Fragment  =
            fragmentManager.findFragmentByTag("type")
        if (Fragment != null && Fragment.isVisible()) {
            if(!Form_FurntiureType.ValidateList() ){

                CustomToast.toastIconError(resources.getString(R.string.validate_oneitem),
                    context as Activity
                )
            }else {
                MoveToNextSteep()
            }
        }
    }

    fun ValidateForm_Date(){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val Fragment  =
            fragmentManager.findFragmentByTag("date")
        if (Fragment != null && Fragment.isVisible()) {
            if(!Form_Date.ValidateNo_Date() ){

                CustomToast.toastIconError(resources.getString(R.string.validate_date),
                    context as Activity
                )
            }else {
               CreatOrder()
            }
        }
    }
    fun MoveToNextSteep(){
        if (idx_state == array_state.size - 1) {
            root.lyt_Send.visibility=View.VISIBLE
            root.lyt_next.visibility =View.GONE
            return
        }
        idx_state++
        displayFragment(array_state[idx_state])
        if (idx_state == array_state.size - 1) {
            root.lyt_Send.visibility=View.VISIBLE
            root.lyt_next.visibility =View.GONE
            return
        }
    }

}