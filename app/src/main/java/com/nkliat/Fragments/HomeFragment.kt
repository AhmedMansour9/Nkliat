package com.nkliat.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nkliat.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

     lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        openFurniture()


        return root
    }

    private fun openFurniture() {
        root.Card_Furntiure.setOnClickListener(){
            var productsByid=FurintureTransfer()
            val bundle = Bundle()
            productsByid.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.add(R.id.Rela_Home, productsByid)
                ?.addToBackStack(null)?.commit()

        }

    }

}