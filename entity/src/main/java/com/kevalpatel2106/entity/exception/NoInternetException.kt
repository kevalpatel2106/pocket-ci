package com.kevalpatel2106.entity.exception

import java.io.IOException

data class NoInternetException(val url: String) : IOException(url)
