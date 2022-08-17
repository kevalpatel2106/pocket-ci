package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.entity.ArtifactType
import java.util.Locale
import javax.inject.Inject

class ArtifactTypeMapperImpl @Inject constructor() : ArtifactTypeMapper {

    override fun invoke(fileName: String): ArtifactType {
        val sanitizedName = fileName.lowercase(Locale.getDefault())
        return when {
            sanitizedName.endsWith(TXT_FILE_EXTENSION) -> ArtifactType.TXT
            sanitizedName.endsWith(ZIP_FILE_EXTENSION) -> ArtifactType.ZIP
            sanitizedName.endsWith(APK_FILE_EXTENSION) -> ArtifactType.ANDROID_APK
            sanitizedName.endsWith(AAB_FILE_EXTENSION) -> ArtifactType.ANDROID_APK
            else -> ArtifactType.UNKNOWN
        }
    }

    companion object {
        private const val TXT_FILE_EXTENSION = ".txt"
        private const val ZIP_FILE_EXTENSION = ".zip"
        private const val APK_FILE_EXTENSION = ".apk"
        private const val AAB_FILE_EXTENSION = ".aab"
    }
}
