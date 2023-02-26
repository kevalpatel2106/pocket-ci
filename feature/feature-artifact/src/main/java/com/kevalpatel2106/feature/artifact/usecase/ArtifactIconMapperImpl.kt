package com.kevalpatel2106.feature.artifact.usecase

import androidx.annotation.DrawableRes
import com.kevalpatel2106.entity.ArtifactType
import com.kevalpatel2106.feature.artifact.R
import javax.inject.Inject

internal class ArtifactIconMapperImpl @Inject constructor() : ArtifactIconMapper {

    @DrawableRes
    override fun invoke(type: ArtifactType) = when (type) {
        ArtifactType.ZIP -> R.drawable.ic_zip_file
        ArtifactType.ANDROID_APK -> R.drawable.ic_android_apk
        ArtifactType.TXT -> R.drawable.ic_text_file
        ArtifactType.UNKNOWN -> R.drawable.ic_file
    }
}
