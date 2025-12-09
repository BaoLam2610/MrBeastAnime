package com.lambao.base.domain.exception

data class ParamsEmptyException(
    override val message: String? = "Param is must not be empty"
): Throwable(message)
