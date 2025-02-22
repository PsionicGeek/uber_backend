package org.psionicgeek.uber_backend.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RuntimeConflictException extends RuntimeException {

    public RuntimeConflictException(String message) {
        super(message);
    }
}
