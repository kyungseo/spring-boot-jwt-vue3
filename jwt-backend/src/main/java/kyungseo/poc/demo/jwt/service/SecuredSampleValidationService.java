package kyungseo.poc.demo.jwt.service;

import org.springframework.stereotype.Service;

import kyungseo.poc.demo.jwt.payload.SecuredSampleDto;

@Service
public class SecuredSampleValidationService {

    // @Valid로 처리하지 못하는 Custom Validation 실행 예제
    public String validateUser(SecuredSampleDto user) {
        String message = "";
        if (user.getCountry() != null && user.getPhoneNumber() != null) {
            if (user.getCountry().equalsIgnoreCase("KR")
                && !user.getPhoneNumber().startsWith("82")) {
                message = user.getCountry() + "에 대한 전화번호가 잘못되었습니다.";
            }
        }
        return message;
    }

}
