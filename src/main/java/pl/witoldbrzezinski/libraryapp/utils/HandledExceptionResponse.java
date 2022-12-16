package pl.witoldbrzezinski.libraryapp.utils;

import java.time.LocalDateTime;

public record HandledExceptionResponse(LocalDateTime time, String exceptionMessage) {}
