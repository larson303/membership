package com.applytruth.membership.service;

import com.applytruth.membership.enumeration.VerificationType;

public interface EmailService {

  public void sendVerificationUrl(String firstName, String email, String verificationUrl, VerificationType account);
  

} 