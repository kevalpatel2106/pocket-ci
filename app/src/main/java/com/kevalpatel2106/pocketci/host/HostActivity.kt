package com.kevalpatel2106.pocketci.host

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kevalpatel2106.core.extentions.collectInActivity
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.pocketci.R
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerDialog
import com.kevalpatel2106.pocketci.databinding.ActivityHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityHostBinding::inflate)
    private val viewModel: HostViewModel by viewModels()
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment,
        ) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this@HostActivity
        setUpToolbar()
        monitorNavigation()
        viewModel.vmEventsFlow.collectInActivity(this, ::handleViewEvent)
        viewModel.viewState.collectInActivity(this, ::handleViewState)
    }

    private fun monitorNavigation() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val previousDstId = controller.previousBackStackEntry?.destination?.id
            val previousDst = previousDstId?.run { resources.getResourceEntryName(previousDstId) }
            val nextDst = resources.getResourceEntryName(destination.id)
            viewModel.onNavDestinationChanged(previousDstId, destination.id)
            viewModel.trackNavEvent(previousDst, nextDst, arguments)
        }
    }

    private fun handleViewEvent(event: HostVMEvents) {
        when (event) {
            HostVMEvents.ShowBottomDialog -> BottomDrawerDialog.show(supportFragmentManager)
            HostVMEvents.NavigateUp -> navController.navigateUp()
        }
    }

    private fun handleViewState(viewState: HostViewState) {
        binding.hostToolbar.apply {
            isVisible = viewState.navigationIconVisible
            if (viewState.navigationIcon == null) {
                navigationIcon = null
            } else {
                setNavigationIcon(viewState.navigationIcon)
            }
        }
    }

    private fun setUpToolbar() = with(binding.hostToolbar) {
        setSupportActionBar(this)
        setupWithNavController(navController)
        setNavigationOnClickListener {
            viewModel.onNavigateUpClicked(navController.currentDestination?.id)
        }
    }
}
