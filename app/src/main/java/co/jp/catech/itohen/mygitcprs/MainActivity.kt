package co.jp.catech.itohen.mygitcprs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import co.jp.catech.itohen.mygitcprs.adapter.CPRListAdapter
import co.jp.catech.itohen.mygitcprs.data.Repository
import co.jp.catech.itohen.mygitcprs.network.ApiService
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRDisplayModel
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var cprListAdapter: CPRListAdapter
    private lateinit var cprViewModel: CPRViewModel

    private val cprDisplayModelObserver = Observer<CPRDisplayModel?> {

        if (it.showProgress) {
            progressCircular.visibility = VISIBLE
        } else {
            progressCircular.visibility = GONE
            if (it.data == null) {

                cprListAdapter.updateData(listOf())
                if (it.message.isNotEmpty())
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            } else
                cprListAdapter.updateData(it.data!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initVars()
        initViews()
        startObserving()
        fetchPrList()
    }

    private fun initVars() {


        cprViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CPRViewModel(Repository(ApiService.getRetrofitService())) as T
            }
        }).get(CPRViewModel::class.java)
    }

    private fun initViews() {
        cprListAdapter = CPRListAdapter(ArrayList())
        rvCprList.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        rvCprList.adapter = cprListAdapter

        sortSpinner.setSelection(1)
        filterSpinner.setSelection(2)

        val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fetchPrList()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        sortSpinner.onItemSelectedListener = itemSelectedListener
        filterSpinner.onItemSelectedListener = itemSelectedListener

        btnListRepos.setOnClickListener {
            fetchPrList()
        }
    }

    private fun startObserving() {

        if (cprViewModel.liveCPRDisplayModel.hasActiveObservers())
            stopObserving()

        cprViewModel.liveCPRDisplayModel.observe(this, cprDisplayModelObserver)
    }

    private fun stopObserving() {

        cprViewModel.liveCPRDisplayModel.removeObserver(cprDisplayModelObserver)
    }

    override fun onDestroy() {

        cprViewModel.liveCPRDisplayModel.removeObserver(cprDisplayModelObserver)
        super.onDestroy()
    }

    private fun fetchPrList() {

        cprViewModel.fetchPrList(
            etUserSearchView.text.toString(),
            etRepoSearchView.text.toString(),
            filterSpinner.selectedItem.toString().toLowerCase(Locale.getDefault()),
            sortDirection = sortSpinner.selectedItem.toString().toLowerCase(Locale.getDefault()),
        )
    }
}