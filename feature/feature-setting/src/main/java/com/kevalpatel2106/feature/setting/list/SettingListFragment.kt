package com.kevalpatel2106.feature.setting.list

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.kevalpatel2106.core.extentions.collectStateInFragment
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.ui.errorHandling.showErrorSnack
import com.kevalpatel2106.feature.setting.R
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent
import com.kevalpatel2106.core.resources.R as coreR
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.ErrorChangingTheme
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenAppInvite
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenChangelog
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenContactUs
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenOpenSourceLicences
import com.kevalpatel2106.feature.setting.list.model.SettingListVMEvent.OpenPrivacyPolicy
import com.kevalpatel2106.feature.setting.list.model.SettingListViewState
import com.kevalpatel2106.feature.setting.list.usecase.PrepareAppInviteIntent
import com.kevalpatel2106.feature.setting.list.usecase.PrepareContactUsIntent
import com.kevalpatel2106.feature.setting.webView.WebViewContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingListFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    private val viewModel: SettingListViewModel by viewModels()

    @Inject
    internal lateinit var prepareAppInviteIntent: PrepareAppInviteIntent

    @Inject
    internal lateinit var prepareContactUsIntent: PrepareContactUsIntent

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_list, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<ListPreference>(getString(R.string.pref_key_theme))?.summaryProvider =
            ListPreference.SimpleSummaryProvider.getInstance()

        viewModel.viewState.collectStateInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, ::handleViewEvents)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {
        if (key == getString(R.string.pref_key_theme)) {
            viewModel.onNightModeChanged(pref?.getString(key, null))
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            getString(R.string.pref_key_contact) -> viewModel.onContactUsClicked()
            getString(R.string.pref_key_privacy_policy) -> viewModel.onShowPrivacyPolicyClicked()
            getString(R.string.pref_key_changelog) -> viewModel.onShowChangelogClicked()
            getString(R.string.pref_key_open_source_licences) -> viewModel.onShowOpenSourceLicencesClicked()
            getString(R.string.pref_key_share_friends) -> viewModel.onShareAppClicked()
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun handleViewState(it: SettingListViewState) {
        findPreference<Preference>(getString(R.string.pref_key_version))?.summary =
            it.versionPreferenceSummary
        if (AppCompatDelegate.getDefaultNightMode() != it.themeValue.value) {
            AppCompatDelegate.setDefaultNightMode(it.themeValue.value)
        }
    }

    private fun handleViewEvents(event: SettingListVMEvent) {
        when (event) {
            is OpenContactUs -> {
                startActivity(prepareContactUsIntent(event.versionName, event.versionCode))
            }
            is ErrorChangingTheme -> {
                showErrorSnack(event.error, coreR.string.settings_error_loading_them)
            }
            is OpenAppInvite -> startActivity(prepareAppInviteIntent())
            OpenOpenSourceLicences -> showOpenSourceLicences()
            OpenChangelog -> findNavController().navigate(
                SettingListFragmentDirections.actionSettingListToWebview(WebViewContent.CHANGELOG),
            )
            OpenPrivacyPolicy -> findNavController().navigate(
                SettingListFragmentDirections.actionSettingListToWebview(
                    WebViewContent.PRIVACY_POLICY,
                ),
            )
        }
    }

    private fun showOpenSourceLicences() {
        // Cannot open from nav graph because of 3rd party activity
        OssLicensesMenuActivity.setActivityTitle(
            requireContext().getString(coreR.string.title_activity_licences),
        )
        startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
    }
}
