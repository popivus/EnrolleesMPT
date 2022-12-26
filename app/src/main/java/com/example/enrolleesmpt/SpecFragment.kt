package com.example.enrolleesmpt

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpecFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var okHttpClient: OkHttpClient = OkHttpClient()
    private lateinit var recyclerView : RecyclerView
    private lateinit var progressBar : ProgressBar
    private var context1 : Context? = null
    private lateinit var view1 : View
    private val URL = "http://${getIp()}:5000/api/Specialties"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refresh(URL)
    }

    fun refresh(URL : String) {
        val request : Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API ERROR", e.message!!)
            }

            val specList: ArrayList<ItemOfList> = ArrayList()

            override fun onResponse(call: Call, response: Response) {
                val json = (JSONArray(response.body()!!.string()))
                activity?.runOnUiThread {
                    for (i in 0..json.length() - 1) {
                        var spec = json.optJSONObject(i)
                        var name = spec.getString("name")
                        var qualification = spec.getString("qualification")
                        var trainingPeriod = spec.getString("training_period")
                        var description = spec.getString("description")
                        var imageURL = spec.getString("image_URL")
                        specList.add(ItemOfList(name, qualification, trainingPeriod, description, imageURL))
                    }
                    recyclerView.adapter = ItemAdapter(context1, specList) {
                        val intent = Intent(context1, DetailActivity::class.java)
                        intent.putExtra("OBJECT_INTENT", it)
                        startActivity(intent)
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_spec, container, false)
        context1 = context
        recyclerView = view1.findViewById<RecyclerView>(R.id._RecyclerView1)
        recyclerView.setHasFixedSize(true)
        var list = ArrayList<ItemOfList>()
        recyclerView.adapter = ItemAdapter(context1, list) {}
        return view1
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpecFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SpecFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}