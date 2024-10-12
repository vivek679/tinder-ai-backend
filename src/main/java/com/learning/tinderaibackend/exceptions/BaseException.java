package com.learning.tinderaibackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    private final String message;
    private final int status;
}
