package com.locus_narrative.auth_service.domain.exceptions;

public class WeakPasswordException extends Exception {
  public WeakPasswordException(String message) {
    super(message);
  }
}
