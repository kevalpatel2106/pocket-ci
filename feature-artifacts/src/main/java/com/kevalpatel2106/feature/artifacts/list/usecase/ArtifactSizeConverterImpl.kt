package com.kevalpatel2106.feature.artifacts.list.usecase

import java.util.Locale
import javax.inject.Inject

internal class ArtifactSizeConverterImpl @Inject constructor() : ArtifactSizeConverter {

    override fun invoke(sizeInBytes: Long?) = when {
        sizeInBytes == null -> null
        sizeInBytes < KB_MULTIPLIER -> "$sizeInBytes B"
        sizeInBytes < MB_MULTIPLIER -> "${format(sizeInBytes, KB_MULTIPLIER)} KB"
        sizeInBytes < GB_MULTIPLIER -> "${format(sizeInBytes, MB_MULTIPLIER)} MB"
        else -> "${format(sizeInBytes, GB_MULTIPLIER)} GB"
    }

    private fun format(sizeInBytes: Long, multiplier: Long) =
        String.format(Locale.getDefault(), "%.2f", (sizeInBytes.toFloat() / multiplier))

    companion object {
        private const val KB_MULTIPLIER = 1024L
        private const val MB_MULTIPLIER = KB_MULTIPLIER * KB_MULTIPLIER
        private const val GB_MULTIPLIER = KB_MULTIPLIER * KB_MULTIPLIER * KB_MULTIPLIER
    }
}
