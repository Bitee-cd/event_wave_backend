package com.bitee.event.UserTests;

import com.bitee.event.Config.CustomUserDetailsService;
import com.bitee.event.Config.JwtUtil;
import com.bitee.event.Otp.*;
import com.bitee.event.User.*;
import com.bitee.event.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    OtpService otpService;

    @Mock
    CustomUserDetailsService customUserDetailsService;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    OtpRepository otpRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private static final String TEST_FIRST_NAME = "Test_First_Name";
    private static final String TEST_LAST_NAME = "Test_Last_Name";
    private static final String TEST_EMAIL="test@example.com";
    private static final String TEST_PASSWORD="TestPassword?";
    private static final String TEST_PHONE_NUMBER = "07000000000";
    private static final String TEST_OTP="000000";

    private UserRequestDto userRequestDto;
    private LoginRequestDto loginRequestDto;
    private ChangePasswordRequestDto changePasswordRequestDto;


    @BeforeEach
    void setup(){
        userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(TEST_FIRST_NAME);
        userRequestDto.setLastName(TEST_LAST_NAME);
        userRequestDto.setEmail(TEST_EMAIL);
        userRequestDto.setPassword(TEST_PASSWORD);
        userRequestDto.setPhoneNumber(TEST_PHONE_NUMBER);

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(TEST_EMAIL);
        loginRequestDto.setPassword(TEST_PASSWORD);

        changePasswordRequestDto = new ChangePasswordRequestDto();
        changePasswordRequestDto.setEmail(TEST_EMAIL);
        changePasswordRequestDto.setPassword(TEST_PASSWORD);
        changePasswordRequestDto.setOtp(TEST_OTP);
    }


    @Test
    void signup_UserDoesNotExist_SavesUserAndGeneratesOtp() {
        when(userRepository.existsByEmail(userRequestDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));


        ResponseEntity<ApiResponse<User>> response = userService.signup(userRequestDto);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("201", response.getBody().getResponseCode());
        assertEquals("OTp sent to email for verification", response.getBody().getResponseMessage());

        verify(userRepository, times(1)).save(any(User.class));
        verify(otpService, times(1)).generateOtp(userRequestDto.getEmail());
    }
    @Test
    void signup_UserAlreadyExists_ReturnsConflictResponse() {

        when(userRepository.existsByEmail(userRequestDto.getEmail())).thenReturn(true);

        ResponseEntity<ApiResponse<User>> response = userService.signup(userRequestDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("409", response.getBody().getResponseCode());
        assertEquals("User with provided email already exists", response.getBody().getResponseMessage());

        verify(userRepository, never()).save(any(User.class));
        verify(otpService, never()).generateOtp(anyString());
    }

    @Test
    void login_UserDoesNotExist_ReturnsBadRequest(){
        when(userRepository.findByUserEmail(loginRequestDto.getEmail())).thenReturn(null);

        ResponseEntity<ApiResponse<Map<String, String>>> response = userService.login(loginRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("400",response.getBody().getResponseCode());
        assertEquals("Invalid Credentials",response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(loginRequestDto.getEmail());

    }

    @Test
    void login_InvalidCredentials_ReturnsBadRequest(){
        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);
        when (userRepository.findByUserEmail(loginRequestDto.getEmail())).thenReturn(mockUser);

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);

        ResponseEntity<ApiResponse<Map<String, String>>> response = userService.login(loginRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("400",response.getBody().getResponseCode());
        assertEquals("Invalid Credentials",response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(loginRequestDto.getEmail());
        verify(authenticationManager, times(1)).authenticate(any());


    }
    @Test
    void login_UserNotVerified_ReturnsConflict(){
        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setPassword(TEST_PASSWORD);
        mockUser.setStatus(AccountStatus.INACTIVE);
        when (userRepository.findByUserEmail(loginRequestDto.getEmail())).thenReturn(mockUser);

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);

        when(customUserDetailsService.getUserDetail()).thenReturn(mockUser);

        ResponseEntity<ApiResponse<Map<String, String>>> response = userService.login(loginRequestDto);

        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
        assertEquals("403",response.getBody().getResponseCode());
        assertEquals("Account not verified",response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(loginRequestDto.getEmail());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(customUserDetailsService,times(1)).getUserDetail();
    }
    @Test
    void Login_Success_ReturnsGeneratedOtp(){
        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setPassword(TEST_PASSWORD);
        mockUser.setStatus(AccountStatus.ACTIVE);
        mockUser.setRole(UserRole.USER);
        when (userRepository.findByUserEmail(loginRequestDto.getEmail())).thenReturn(mockUser);

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);

        when(customUserDetailsService.getUserDetail()).thenReturn(mockUser);

        String token = "test token";
        when(jwtUtil.generateToken(loginRequestDto.getEmail(),mockUser.getRole().name())).thenReturn(token);

        ResponseEntity<ApiResponse<Map<String, String>>> response = userService.login(loginRequestDto);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("400",response.getBody().getResponseCode());
        assertEquals("Success",response.getBody().getResponseMessage());

        verify(userRepository, times(1)).findByUserEmail(loginRequestDto.getEmail());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(customUserDetailsService,times(1)).getUserDetail();
        verify(jwtUtil,times(1)).generateToken(loginRequestDto.getEmail(), mockUser.getRole().name());
    }

    @Test
    void changePassword_InvalidCredentials_ReturnsInvalidDetails(){
        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);

        when(otpRepository.findByToken(changePasswordRequestDto.getOtp())).thenReturn(null);
        when(userRepository.findByUserEmail(changePasswordRequestDto.getEmail())).thenReturn(mockUser);

        ResponseEntity<ApiResponse<String>> response = userService.changePassword(changePasswordRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("400",response.getBody().getResponseCode());
        assertEquals("Invalid Details",response.getBody().getResponseMessage());

        verify(otpRepository,times(1)).findByToken(changePasswordRequestDto.getOtp());
        verify(userRepository,times(1)).findByUserEmail(changePasswordRequestDto.getEmail());

    }

    @Test
    void changePassword_ExpiredToken_ReturnsInvalidToken(){
        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);

        Otp mockToken = new Otp();
        mockToken.setToken(TEST_OTP);
        mockToken.setUser(mockUser);
        mockToken.setExpiresAt(new Date(System.currentTimeMillis() - 15 * 60 * 1000));

        when(otpRepository.findByToken(changePasswordRequestDto.getOtp())).thenReturn(mockToken);
        when(userRepository.findByUserEmail(changePasswordRequestDto.getEmail())).thenReturn(mockUser);


        ResponseEntity<ApiResponse<String>> response = userService.changePassword(changePasswordRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("400",response.getBody().getResponseCode());
        assertEquals("Token is not valid",response.getBody().getResponseMessage());

        verify(otpRepository,times(1)).findByToken(changePasswordRequestDto.getOtp());
        verify(userRepository,times(1)).findByUserEmail(changePasswordRequestDto.getEmail());

    }
    @Test
    void changePassword_Success_ReturnsSuccess(){
        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setStatus(AccountStatus.ACTIVE);

        Otp mockToken = new Otp();
        mockToken.setToken(TEST_OTP);
        mockToken.setUser(mockUser);
        mockToken.setExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000));

        when(otpRepository.findByToken(changePasswordRequestDto.getOtp())).thenReturn(mockToken);
        when(userRepository.findByUserEmail(changePasswordRequestDto.getEmail())).thenReturn(mockUser);
        when(passwordEncoder.encode(changePasswordRequestDto.getPassword())).thenReturn(TEST_PASSWORD);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(otpRepository).delete(any(Otp.class));

        ResponseEntity<ApiResponse<String>> response = userService.changePassword(changePasswordRequestDto);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("200",response.getBody().getResponseCode());
        assertEquals("Success",response.getBody().getResponseMessage());

        verify(otpRepository,times(1)).findByToken(changePasswordRequestDto.getOtp());
        verify(userRepository,times(1)).findByUserEmail(changePasswordRequestDto.getEmail());
        verify(userRepository, times(1)).save(argThat(user ->
                user.getPassword().equals(TEST_PASSWORD)
        ));
        verify(otpRepository, times(1)).delete(mockToken);

    }
    @Test
    void forgotPassword_InvalidEmail_ReturnsSuccess(){

        RegenerateOtpRequestDto regenerateOtpRequestDto = new RegenerateOtpRequestDto();
        when(userRepository.findByUserEmail(regenerateOtpRequestDto.getEmail())).thenReturn(null);

        ResponseEntity<ApiResponse<String>> response = userService.forgotPassword(regenerateOtpRequestDto);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("200",response.getBody().getResponseCode());
        assertEquals("Otp sent successfully to email",response.getBody().getResponseMessage());

        verify(userRepository,times(1)).findByUserEmail(regenerateOtpRequestDto.getEmail());
    }
    @Test
    void forgotPassword_InactiveUser_ReturnsBadRequest(){
        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setStatus(AccountStatus.ACTIVE);

        RegenerateOtpRequestDto regenerateOtpRequestDto = new RegenerateOtpRequestDto();
        regenerateOtpRequestDto.setEmail(TEST_EMAIL);
        when(userRepository.findByUserEmail(regenerateOtpRequestDto.getEmail())).thenReturn(mockUser);

        ResponseEntity<ApiResponse<String>> response = userService.forgotPassword(regenerateOtpRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("400",response.getBody().getResponseCode());
        assertEquals("User has been activated",response.getBody().getResponseMessage());

        verify(userRepository,times(1)).findByUserEmail(regenerateOtpRequestDto.getEmail());
    }

    @Test
    void forgotPassword_Success_ReturnsSuccessEmail(){

        User mockUser = new User();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setStatus(AccountStatus.INACTIVE);

        Otp mockOtp = new Otp();
        mockOtp.setUser(mockUser);

        RegenerateOtpRequestDto regenerateOtpRequestDto = new RegenerateOtpRequestDto();
        regenerateOtpRequestDto.setEmail(TEST_EMAIL);
        when(userRepository.findByUserEmail(regenerateOtpRequestDto.getEmail())).thenReturn(mockUser);
        when(otpService.createAndSaveOtp(mockUser)).thenReturn(mockOtp);
        doNothing().when(otpService).sendOtpEmail(mockUser, mockOtp);
        doNothing().when(otpService).invalidateExistingOtp(mockUser);


        ResponseEntity<ApiResponse<String>> response = userService.forgotPassword(regenerateOtpRequestDto);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("200",response.getBody().getResponseCode());
        assertEquals("Otp sent successfully to email",response.getBody().getResponseMessage());


        verify(userRepository,times(1)).findByUserEmail(regenerateOtpRequestDto.getEmail());
        verify(otpService,times(1)).invalidateExistingOtp(mockUser);
        verify(otpService,times(1)).createAndSaveOtp(mockUser);
        verify(otpService,times(1)).sendOtpEmail(mockUser,mockOtp);
    }

}
