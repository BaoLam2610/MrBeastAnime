package com.lambao.base.data.remote

enum class NetworkErrorType(val code: Int? = null) {
    NOT_MODIFIED(304),         // 304: Không có thay đổi
    BAD_REQUEST(400),          // 400: Yêu cầu không hợp lệ
    UNAUTHORIZED(401),         // 401: Không có quyền truy cập
    FORBIDDEN(403),            // 403: Bị cấm truy cập
    NOT_FOUND(404),            // 404: Không tìm thấy tài nguyên
    METHOD_NOT_ALLOWED(405),    // 405: Phương thức không được phép
    TOO_MANY_REQUESTS(429),    // 429: Quá nhiều yêu cầu
    SERVER_ERROR(500),         // 500: Lỗi server nội bộ
    BAD_GATEWAY(502),          // 502: Gateway không hợp lệ
    SERVICE_UNAVAILABLE(503),  // 503: Dịch vụ không khả dụng
    NO_NETWORK,                // Không có kết nối mạng
    UNKNOWN;                   // Lỗi không xác định
}