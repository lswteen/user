package com.renzo.web.service;

import com.renzo.core.exception.ApiException;
import com.renzo.core.type.ServiceErrorType;
import com.renzo.web.dao.SmsCertificationDao;
import com.renzo.web.request.UserRequest;
import com.renzo.web.utils.certification.sms.SmsMessageTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

import static com.renzo.web.utils.certification.sms.RandomNumberGeneration.makeRandomNumber;
import static com.renzo.web.utils.certification.sms.SmsConstants.APP_VERSION;
import static com.renzo.web.utils.certification.sms.SmsConstants.SMS_TYPE;

@Service
public class SmsCertificationService {

    @Resource
    SmsCertificationDao smsCertificationDao;

    // 인증 메세지 내용 생성
    public String makeSmsContent(String certificationNumber) {
        SmsMessageTemplate content = new SmsMessageTemplate();
        return content.builderCertificationContent(certificationNumber);
    }

    public HashMap<String, String> makeParams(String to, String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", "01038381414");
        params.put("type", SMS_TYPE);
        params.put("app_version", APP_VERSION);
        params.put("to", to);
        params.put("text", text);
        return params;
    }

    // sms로 인증번호 발송하고, 발송 정보를 세션에 저장
    public void sendSms(String phone) {
        //FIXME : 여기에 SMS문자발송이 들어가야하지만 요청과제예기해주신대로 스킵하겠습니다.!!
        String randomNumber = makeRandomNumber();
        String content = makeSmsContent(randomNumber);
        HashMap<String, String> params = makeParams(phone, content);
        smsCertificationDao.createSmsCertification(phone, randomNumber);
    }

    // 입력한 인증번호가 발송되었던(세션에 저장된) 인증번호가 동일한지 확인
    public void verifySms(UserRequest.SmsCertificationRequest requestDto) {
        if (isVerify(requestDto)) {
            throw new ApiException(ServiceErrorType.MISMATCH);
        }
        smsCertificationDao.removeSmsCertification(requestDto.getPhone());
    }

    public boolean isVerify(UserRequest.SmsCertificationRequest requestDto) {
        return !(smsCertificationDao.hasKey(requestDto.getPhone()) &&
                smsCertificationDao.getSmsCertification(requestDto.getPhone())
                        .equals(requestDto.getCertificationNumber()));
    }
}
