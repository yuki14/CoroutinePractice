package com.example.coroutinepractice.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.coroutinepractice.R
import com.example.coroutinepractice.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        MainFragmentBinding.bind(view!!).let {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        button.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.countDownPreparationTimer()

                async { viewModel.countUpTimer() }
                async { viewModel.countDownTimer() }
            }

//            viewModel.countup()
        }

        viewModel.countUpTimer.observe(viewLifecycleOwner, Observer {
            if (viewModel.countDownPreParationTimer.value == 0L) async { viewModel.countUpTimer() }
        })

        viewModel.countDownTimer.observe(viewLifecycleOwner, Observer {
            if (viewModel.countDownPreParationTimer.value == 0L) async { viewModel.countDownTimer() }
        })
    }

}