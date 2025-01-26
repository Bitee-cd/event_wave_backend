package com.bitee.event.OtpTests;

import com.bitee.event.Email.EmailService;
import com.bitee.event.Otp.*;
import com.bitee.event.User.*;
import com.bitee.event.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Date;

import static com.bitee.event.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OtpServiceTest {

    @Mock
    private OtpRepository otpRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OtpServiceImpl otpService;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setup(){
        userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(TEST_FIRST_NAME);
        userRequestDto.setLastName(TEST_LAST_NAME);
        userRequestDto.setEmail(TEST_EMAIL);
        userRequestDto.setPassword(TEST_PASSWORD);
        userRequestDto.setPhoneNumber(TEST_PHONE_NUMBER);

    }

    @Test
    void generateOtp_InvalidDetails(){

        when(userRepository.findByUserEmail(TEST_EMAIL)).thenReturn(null);
        ResponseEntity<ApiResponse<String>> response = otpService.generateOtp(TEST_EMAIL);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("Invalid details", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(any());
        verify(userRepository, never()).save(any(User.class));
        verify(otpRepository, never()).save(any());
    }
    @Test
    void generateOtp_Success(){
        User mockUser = new User();
        mockUser.setStatus(AccountStatus.INACTIVE);

        Otp mockOtp = new Otp();
        mockOtp.setToken(TEST_OTP);
        mockOtp.setUser(mockUser);

        when(userRepository.findByUserEmail(TEST_EMAIL)).thenReturn(mockUser);
        when(otpRepository.save(any())).thenReturn(mockOtp);
        ResponseEntity<ApiResponse<String>> response = otpService.generateOtp(TEST_EMAIL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Otp sent successfully to email", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(any());
        verify(otpRepository, times(1)).save(any());
    }

    @Test
    void verifyOtp_invalidDetails(){
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setOtp(TEST_OTP);
        otpRequestDto.setEmail(TEST_EMAIL);

        User mockUser = new User();
        mockUser.setStatus(AccountStatus.INACTIVE);

        Otp mockOtp = new Otp();
        mockOtp.setToken(TEST_OTP);
        mockOtp.setUser(mockUser);

        when(userRepository.findByUserEmail(TEST_EMAIL)).thenReturn(mockUser);
        when(otpRepository.findByToken(TEST_OTP)).thenReturn(null);
        ResponseEntity<ApiResponse<String>> response = otpService.verifyOtp(otpRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("Invalid details", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(any());
        verify(otpRepository, times(1)).findByToken(any());
    }

    @Test
    void verifyOtp_ExpiredToken(){
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setOtp(TEST_OTP);
        otpRequestDto.setEmail(TEST_EMAIL);

        User mockUser = new User();
        mockUser.setStatus(AccountStatus.INACTIVE);

        Otp mockOtp = new Otp();
        mockOtp.setToken(TEST_OTP);
        mockOtp.setUser(mockUser);
        mockOtp.setExpiresAt(new Date(System.currentTimeMillis()-10*60*1000));

        when(userRepository.findByUserEmail(TEST_EMAIL)).thenReturn(mockUser);
        when(otpRepository.findByToken(TEST_OTP)).thenReturn(mockOtp);

        ResponseEntity<ApiResponse<String>> response = otpService.verifyOtp(otpRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("Token expired", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(any());
        verify(otpRepository, times(1)).findByToken(any());
    }
    @Test
    void verifyOtp_UserAlreadyActivated(){
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setOtp(TEST_OTP);
        otpRequestDto.setEmail(TEST_EMAIL);

        User mockUser = new User();
        mockUser.setStatus(AccountStatus.ACTIVE);

        Otp mockOtp = new Otp();
        mockOtp.setToken(TEST_OTP);
        mockOtp.setUser(mockUser);
        mockOtp.setExpiresAt(new Date(System.currentTimeMillis()+10*60*1000));

        when(userRepository.findByUserEmail(TEST_EMAIL)).thenReturn(mockUser);
        when(otpRepository.findByToken(TEST_OTP)).thenReturn(mockOtp);

        ResponseEntity<ApiResponse<String>> response = otpService.verifyOtp(otpRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("User already activated", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(any());
        verify(otpRepository, times(1)).findByToken(any());
    }

    @Test
    void verifyOtp_Success(){
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setOtp(TEST_OTP);
        otpRequestDto.setEmail(TEST_EMAIL);

        User mockUser = new User();
        mockUser.setStatus(AccountStatus.INACTIVE);

        Otp mockOtp = new Otp();
        mockOtp.setToken(TEST_OTP);
        mockOtp.setUser(mockUser);
        mockOtp.setExpiresAt(new Date(System.currentTimeMillis()+10*60*1000));

        when(userRepository.findByUserEmail(TEST_EMAIL)).thenReturn(mockUser);
        when(otpRepository.findByToken(TEST_OTP)).thenReturn(mockOtp);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(otpRepository).delete(any(Otp.class));


        ResponseEntity<ApiResponse<String>> response = otpService.verifyOtp(otpRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Verified Successfully", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(any());
        verify(otpRepository, times(1)).findByToken(any());
        verify(otpRepository,times(1)).delete(any(Otp.class));
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    void regenerate_Success(){
        User mockUser = new User();
        mockUser.setStatus(AccountStatus.INACTIVE);

        Otp mockOtp = new Otp();
        mockOtp.setToken(TEST_OTP);
        mockOtp.setUser(mockUser);

        when(userRepository.findByUserEmail(TEST_EMAIL)).thenReturn(mockUser);
        when(otpRepository.save(any())).thenReturn(mockOtp);
        lenient().doNothing().when(otpRepository).delete(any(Otp.class));

        ResponseEntity<ApiResponse<String>> response = otpService.regenerateOtp(TEST_EMAIL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Otp sent successfully to email", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(any());
        verify(otpRepository, times(1)).save(any(Otp.class));
    }


}
