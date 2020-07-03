package com.nkliat.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nkliat.Activites.Navigation
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
        Navigation.T_Title.text=resources.getString(R.string.search)
        openFurniture()
        openTrailer()
        openLightTrailer()
        openPickUp()
        openFlat()
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

    private fun openTrailer() {
        root.Card_Tailer.setOnClickListener(){
            var productsByid=Trailer()
            val bundle = Bundle()
            bundle.putString("type","trailer")
            productsByid.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.add(R.id.Rela_Home, productsByid)
                ?.addToBackStack(null)?.commit()

        }

    }
    private fun openLightTrailer() {
        root.Card_LightTailer.setOnClickListener(){
            var productsByid=Trailer()
            val bundle = Bundle()
            bundle.putString("type","light_trailer")
            productsByid.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.add(R.id.Rela_Home, productsByid)
                ?.addToBackStack(null)?.commit()

        }

    }
    private fun openPickUp() {
        root.Card_Pickup.setOnClickListener(){
            var productsByid=Trailer()
            val bundle = Bundle()
            bundle.putString("type","pickup")
            productsByid.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.add(R.id.Rela_Home, productsByid)
                ?.addToBackStack(null)?.commit()

        }

    }
    private fun openFlat() {
        root.Card_Flat.setOnClickListener(){
            var productsByid=Trailer()
            val bundle = Bundle()
            bundle.putString("type","flat_truck")
            productsByid.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.add(R.id.Rela_Home, productsByid)
                ?.addToBackStack(null)?.commit()

        }

    }
}