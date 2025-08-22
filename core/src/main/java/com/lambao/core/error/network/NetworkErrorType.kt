package com.lambao.core.error.network

/**
 * Enum representing different types of network errors that can occur during API calls.
 *
 * This enum provides a comprehensive list of HTTP status codes and network-related errors
 * that can be used to categorize and handle different error scenarios.
 */
enum class NetworkErrorType(val code: Int? = null) {
    // 2xx Success (rarely used for errors, but included for completeness)
    NOT_MODIFIED(304),         // 304: Không có thay đổi (Not Modified)

    // 4xx Client Errors
    BAD_REQUEST(400),          // 400: Yêu cầu không hợp lệ (Bad Request)
    UNAUTHORIZED(401),         // 401: Không có quyền truy cập (Unauthorized)
    FORBIDDEN(403),            // 403: Bị cấm truy cập (Forbidden)
    NOT_FOUND(404),            // 404: Không tìm thấy tài nguyên (Not Found)
    METHOD_NOT_ALLOWED(405),   // 405: Phương thức không được phép (Method Not Allowed)
    UNPROCESSABLE_ENTITY(422), // 422: Dữ liệu không thể xử lý (Unprocessable Entity)
    TOO_MANY_REQUESTS(429),    // 429: Quá nhiều yêu cầu (Too Many Requests)

    // 5xx Server Errors
    SERVER_ERROR(500),         // 500: Lỗi server nội bộ (Internal Server Error)
    NOT_IMPLEMENTED(501),      // 501: Chức năng chưa được triển khai (Not Implemented)
    BAD_GATEWAY(502),          // 502: Gateway không hợp lệ (Bad Gateway)
    SERVICE_UNAVAILABLE(503),  // 503: Dịch vụ không khả dụng (Service Unavailable)
    GATEWAY_TIMEOUT(504),      // 504: Gateway hết thời gian chờ (Gateway Timeout)

    // Network-specific errors
    NO_NETWORK,                // Không có kết nối mạng
    TIMEOUT,                   // Hết thời gian chờ kết nối
    UNKNOWN;                   // Lỗi không xác định

    companion object {
        /**
         * Get NetworkErrorType from HTTP status code.
         *
         * @param code HTTP status code
         * @return Corresponding NetworkErrorType or UNKNOWN if not found
         */
        fun fromCode(code: Int): NetworkErrorType {
            return entries.find { it.code == code } ?: UNKNOWN
        }
    }
}


