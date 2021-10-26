package com.renzo.web.utils.certification.sms;

public class SmsMessageTemplate {
    public String builderCertificationContent(String certificationNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("[renzo-user-service] 인증번호는 ");
        builder.append(certificationNumber);
        builder.append("입니다. ");
        return builder.toString();
    }
}
