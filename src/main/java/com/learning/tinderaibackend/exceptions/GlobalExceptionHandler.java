package com.learning.tinderaibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

public class GlobalExceptionHandler {

    @ExceptionHandler(ApiExceptions.NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(ApiExceptions.NotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiExceptions.BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(ApiExceptions.BadRequestException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request data",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiExceptions.ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ApiExceptions.ForbiddenException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                "Access is forbidden",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ApiExceptions.InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(ApiExceptions.InternalServerErrorException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiExceptions.RedirectException.class)
    public ResponseEntity<ErrorResponse> handleRedirectException(ApiExceptions.RedirectException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.MULTIPLE_CHOICES.value(), // or another 3xx status
                "Redirect required",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.MULTIPLE_CHOICES);
    }
//    @ExceptionHandler(BaseException.class)
//    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                ex.getMessage(),
//                ex.getStatus(),
//                "Application-specific error occurred",
//                request.getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatus()));
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getClass().getSimpleName(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
