package com.kevalpatel2106.feature.log.log.usecase

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.resources.R
import timber.log.Timber
import java.io.IOException

internal class LogFileSaveHelper(private val fragment: Fragment) : DefaultLifecycleObserver {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var logsToSave: String? = null

    override fun onCreate(owner: LifecycleOwner) {
        resultLauncher = fragment.requireActivity().activityResultRegistry.register(
            ACTIVITY_RESULT_KEY,
            owner,
            StartActivityForResult(),
        ) { activityResult ->
            val uri = activityResult.data?.data ?: return@register
            writeLogFile(uri, logsToSave.orEmpty())
        }
    }

    fun createFile(fileName: String, logs: String) {
        logsToSave = logs
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = MIME_TYPE_TEXT
            putExtra(Intent.EXTRA_TITLE, fileName)
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOWNLOADS.toUri())
        }
        resultLauncher.launch(intent)
    }

    private fun writeLogFile(uri: Uri, log: String) {
        try {
            fragment.requireContext().contentResolver.openOutputStream(uri)?.apply {
                write(log.toByteArray())
                close()
            }
        } catch (e: IOException) {
            Timber.e(e)
            fragment.showSnack(fragment.getString(R.string.build_log_error_writing_logs))
        } finally {
            logsToSave = null
        }
    }

    companion object {
        private const val MIME_TYPE_TEXT = "text/plain"
        private const val ACTIVITY_RESULT_KEY = "activity_result_key"

        fun bindWithLifecycle(fragment: Fragment): LogFileSaveHelper {
            val observer = LogFileSaveHelper(fragment)
            fragment.lifecycle.addObserver(observer)
            return observer
        }
    }
}
