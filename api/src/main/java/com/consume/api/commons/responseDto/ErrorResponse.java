package com.consume.api.commons.responseDto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        String message) { }
