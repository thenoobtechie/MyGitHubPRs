package co.jp.catech.itohen.mygitcprs

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import co.jp.catech.itohen.mygitcprs.adapter.CPRListAdapter
import co.jp.catech.itohen.mygitcprs.adapter.CustomScrollListener
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRDisplayModel
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var cprListAdapter: CPRListAdapter
    private lateinit var viewModel: CPRViewModel

    private val observer = Observer<CPRDisplayModel?> {

        if (it.showProgress) {
            progressCircular.visibility = VISIBLE
        } else {
            progressCircular.visibility = GONE
            if (it.success) {
                cprListAdapter.updateData(it.data ?: listOf())
            } else {
                cprListAdapter.updateData(listOf())
                if (it.message.isNotEmpty())
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
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


        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CPRViewModel() as T
            }
        }).get(CPRViewModel::class.java)
    }

    private fun initViews() {
        cprListAdapter = CPRListAdapter(ArrayList())
        rvCprList.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        rvCprList.adapter = cprListAdapter

        sortSpinner.setSelection(1)
        filterSpinner.setSelection(2)

        btnListRepos.setOnClickListener {
            fetchPrList()
        }
    }

    private fun startObserving() {

        if (viewModel.liveCPRDisplayModel.hasActiveObservers())
            stopObserving()

        viewModel.liveCPRDisplayModel.observe(this, observer)
    }

    private fun stopObserving() {

        viewModel.liveCPRDisplayModel.removeObserver(observer)
    }

    override fun onDestroy() {

        viewModel.liveCPRDisplayModel.removeObserver(observer)
        super.onDestroy()
    }

    private fun fetchPrList() {

        viewModel.fetchPrList(
            etUserSearchView.text.toString(),
            etRepoSearchView.text.toString(),
            filterSpinner.selectedItem.toString().toLowerCase(Locale.getDefault()),
            sortDirection = sortSpinner.selectedItem.toString().toLowerCase(Locale.getDefault()),
        )
    }
}