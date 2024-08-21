package dev.davi.spring.security.jwt.exception.detail;

import java.time.LocalDateTime;
import java.util.Map;

public record ExceptionDetail(
		String title,
		LocalDateTime timestamp,
		int statusCode,
		String message,
		Map<String, Object> details
) {

}
