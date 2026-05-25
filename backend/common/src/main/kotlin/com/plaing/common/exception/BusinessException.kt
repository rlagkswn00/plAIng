package com.plaing.common.exception

open class BusinessException(
    message: String,
    val code: String = "BUSINESS_ERROR",
) : RuntimeException(message)
