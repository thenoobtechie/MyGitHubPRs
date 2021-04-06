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
import co.jp.catech.itohen.mygitcprs.data.CPRRepository
import co.jp.catech.itohen.mygitcprs.data.RequestModel
import co.jp.catech.itohen.mygitcprs.network.CPRApiService
import co.jp.catech.itohen.mygitcprs.network.CPRApiCallbackHandler
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRDisplayModel
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class CPRActivity : AppCompatActivity() {

    private lateinit var cprListAdapter: CPRListAdapter
    private lateinit var cprViewModel: CPRViewModel
    private val cprDisplayModelObserver = Observer<CPRDisplayModel?> { updateState(it) }

    private fun updateState(cprDisplayModel: CPRDisplayModel) {

        if (cprDisplayModel.showProgress) {
            progressCircular.visibility = VISIBLE
        } else {

            if (cprDisplayModel.data == null) {

                cprListAdapter.updateData(listOf())
                if (cprDisplayModel.message.isNotEmpty())
                    Toast.makeText(this, cprDisplayModel.message, Toast.LENGTH_SHORT).show()

            } else
                cprListAdapter.updateData(cprDisplayModel.data!!)

            progressCircular.visibility = GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initVars()
        initViews()
        startObserving()
        fetchPRList()
    }

    private fun initVars() {

        cprViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CPRViewModel(CPRRepository(CPRApiService.getRetrofitService())) as T
            }
        }).get(CPRViewModel::class.java)
    }

    private fun initViews() {
        cprListAdapter = CPRListAdapter(ArrayList())
        rvCprList.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        rvCprList.adapter = cprListAdapter

        sortSpinner.setSelection(1)
        filterSpinner.setSelection(2)

        val itemSelectedListener = getSpinnerItemSelectedListener()
        sortSpinner.onItemSelectedListener = itemSelectedListener
        filterSpinner.onItemSelectedListener = itemSelectedListener

        btnListRepos.setOnClickListener {
            fetchPRList()
        }
    }

    private fun getSpinnerItemSelectedListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            fetchPRList()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun startObserving() {

        stopObserving()
        cprViewModel.liveCPRDisplayModel.observe(this, cprDisplayModelObserver)
    }

    private fun stopObserving() {

        cprViewModel.liveCPRDisplayModel.removeObserver(cprDisplayModelObserver)
    }

    override fun onDestroy() {

        stopObserving()
        super.onDestroy()
    }

    private fun fetchPRList() {

        cprViewModel.fetchPRList(getRequestModel())
    }

    private fun getRequestModel(): RequestModel {
        return RequestModel(
            etUserSearchView.text.toString(),
            etRepoSearchView.text.toString(),
            filterSpinner.selectedItem.toString().toLowerCase(Locale.getDefault()),
            sortDirection = sortSpinner.selectedItem.toString().toLowerCase(Locale.getDefault()),
        )
    }
}