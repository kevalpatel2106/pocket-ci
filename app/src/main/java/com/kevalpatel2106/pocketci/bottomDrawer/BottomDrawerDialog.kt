package com.kevalpatel2106.pocketci.bottomDrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.pocketci.R
import com.kevalpatel2106.pocketci.databinding.DialogBottomDrawerBinding

internal class BottomDrawerDialog : BottomSheetDialogFragment(), View.OnClickListener {
    private val binding by viewBinding(DialogBottomDrawerBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            clickHandler = this@BottomDrawerDialog
        }
    }

    override fun onClick(viewClicked: View?) {
        when (viewClicked?.id) {
            R.id.bottom_drawer_option_settings -> {
                findNavController().navigateToInAppDeeplink(DeepLinkDestinations.SettingList)
            }
            R.id.bottom_drawer_option_accounts -> {
                findNavController().navigateToInAppDeeplink(DeepLinkDestinations.AccountList)
            }
        }
        dismiss()
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            BottomDrawerDialog().show(fragmentManager, BottomDrawerDialog::class.simpleName)
        }
    }
}
