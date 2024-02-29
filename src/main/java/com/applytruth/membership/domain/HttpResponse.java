package com.applytruth.membership.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(NON_DEFAULT)
public class HttpResponse {

  protected String timeStamp;
  protected int httpStatusCode;
  protected HttpStatus httpStatus;
  protected String reason;
  protected String message;
  protected String developerMessage;
  protected Map<?,?> data;
  
}
