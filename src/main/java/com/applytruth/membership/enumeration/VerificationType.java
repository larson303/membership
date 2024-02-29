package com.applytruth.membership.enumeration;

public enum VerificationType {
  ACCOUNT("ACCOUNT"),
  EMAIL("EMAIL"),
  PASSWORD("PASSWORD"), 
  PHONE("PHONE"), 
  TWO_FACTOR("TWO_FACTOR"), 
  RECOVERY("RECOVERY"), 
  INVITE("INVITE"); 

  private final String type;

  VerificationType(String type) {
    this.type = type;
  }

  public String getType() {
    return type.toLowerCase();
  }

}
