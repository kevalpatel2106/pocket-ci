package com.kevalpatel2106.feature.artifact.list.usecase

import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.entity.ArtifactType
import com.kevalpatel2106.entity.ArtifactType.ANDROID_APK
import com.kevalpatel2106.entity.ArtifactType.TEXT_FILE
import com.kevalpatel2106.entity.ArtifactType.UNKNOWN_TYPE
import com.kevalpatel2106.entity.ArtifactType.ZIP
import javax.inject.Inject

internal class ArtifactIconMapperImpl @Inject constructor() : ArtifactIconMapper {

    override fun invoke(type: ArtifactType) = when (type) {
        ZIP -> R.drawable.ic_zip_file to R.string.artifact_type_zip_content_description
        ANDROID_APK -> R.drawable.ic_android_apk to R.string.artifact_type_apk_content_description
        TEXT_FILE -> R.drawable.ic_text_file to R.string.artifact_type_text_content_description
        UNKNOWN_TYPE -> R.drawable.ic_file to R.string.artifact_type_unknown_content_description
    }
}
