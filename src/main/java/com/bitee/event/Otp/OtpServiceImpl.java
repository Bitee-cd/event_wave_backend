package com.bitee.event.Otp;

import com.bitee.event.Email.EmailDetailsDto;
import com.bitee.event.Email.EmailService;
import com.bitee.event.User.AccountStatus;
import com.bitee.event.User.User;
import com.bitee.event.User.UserRepository;
import com.bitee.event.utils.ApiResponse;
import com.bitee.event.utils.EventUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public ResponseEntity<ApiResponse<String>> generateOtp(String email) {
        User user = userRepository.findByUserEmail(email);

        if (user == null || !user.getStatus().equals(AccountStatus.INACTIVE)) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid details", null), HttpStatus.BAD_REQUEST);
        }
        Otp otp = createAndSaveOtp(user);
        sendOtpEmail(user, otp);
        return new ResponseEntity<>(ApiResponse.success("200", "Otp sent successfully to email", null), HttpStatus.OK);


    }


    @Override
    public ResponseEntity<ApiResponse<String>> verifyOtp(OtpRequestDto otpRequestDto) {
        User user = userRepository.findByUserEmail(otpRequestDto.getEmail());
        Otp supposedOtp = otpRepository.findByToken(otpRequestDto.getOtp());

        if (user == null && supposedOtp == null || !supposedOtp.getUser().equals(user)) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid details", null), HttpStatus.BAD_REQUEST);
        }
        if (supposedOtp.getExpiresAt().before(new Date())) {
            return new ResponseEntity<>(ApiResponse.error("400", "Token expired", null), HttpStatus.BAD_REQUEST);
        }
        if (user.getStatus().equals(AccountStatus.ACTIVE)) {
            return new ResponseEntity<>(ApiResponse.error("400", "User already activated", null), HttpStatus.BAD_REQUEST);
        }

        user.setStatus(AccountStatus.ACTIVE);
        userRepository.save(user);

        otpRepository.delete(supposedOtp);
        return new ResponseEntity<>(ApiResponse.success("200", "Verified Successfully", null), HttpStatus.OK);


    }

    @Override
    public ResponseEntity<ApiResponse<String>> regenerateOtp(String email) {
        User user = userRepository.findByUserEmail(email);
        if (user== null){
            return new ResponseEntity<>(ApiResponse.success("200","Otp sent successfully to email",null),HttpStatus.OK);
        }

        if( !user.getStatus().equals(AccountStatus.INACTIVE)){
            return new ResponseEntity<>(ApiResponse.error("400", "User has been activated", null), HttpStatus.BAD_REQUEST);
        }
        invalidateExistingOtp(user);
        Otp otp = createAndSaveOtp(user);
        sendOtpEmail(user, otp);
        return new ResponseEntity<>(ApiResponse.success("200", "Otp sent successfully to email", null), HttpStatus.OK);



    }


    private void invalidateExistingOtp(User user){
        Otp existingOtp = otpRepository.findByUserEmail(user.getEmail());
        if (existingOtp != null) {
            otpRepository.delete(existingOtp);
        }
    }
    private Otp createAndSaveOtp(User user) {
        Otp otp = new Otp();
        otp.setCreatedAt(new Date());
        otp.setUser(user);
        otp.setExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000));  // OTP expires in 15 minutes
        otp.setToken(EventUtils.generateRandomToken());
        otpRepository.save(otp);
        return otp;
    }

    private void sendOtpEmail(User user, Otp otp) {
        EmailDetailsDto emailDetailsDto = new EmailDetailsDto();
        emailDetailsDto.setRecipient(user.getEmail());
        emailDetailsDto.setSubject("OTP Token for Event Wave Registration");
        emailDetailsDto.setMessageBody(EventUtils.EmailOtpBody(user.getFirstName(), otp.getToken()));
        System.out.println("i have gotten to send otp email line 72");
        emailService.sendEmailAlert(emailDetailsDto);

    }
}
