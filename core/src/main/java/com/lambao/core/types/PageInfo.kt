package com.lambao.core.types

/**
 * Core-level pagination contract exposed to all layers.
 */
interface PageInfo {
    val hasNextPage: Boolean?
    val currentPage: Int?
    val totalPages: Int?
    val totalItems: Int?
    val perPage: Int?
}


