package com.bitee.event.User;

import com.bitee.event.Config.CustomUserDetailsService;
import com.bitee.event.Config.JwtUtil;
import com.bitee.event.Event.EventType;
import com.bitee.event.Otp.Otp;
import com.bitee.event.Otp.OtpRepository;
import com.bitee.event.Otp.OtpService;
import com.bitee.event.Otp.RegenerateOtpRequestDto;
import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    OtpService otpService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    OtpRepository otpRepository;

    /**
     * create a user
     * check if a user has an account
     * sends otp to user email for activation
     *
     * @param userRequestDto
     * @return api response
     */
    @Override
    public ResponseEntity<ApiResponse<User>> signup(UserRequestDto userRequestDto) {

        //  @validate phone number
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            ApiResponse<User> accountExists = ApiResponse.error("409", "User with provided email already exists",
                    null);
            return new ResponseEntity<>(accountExists, HttpStatus.CONFLICT);
        }
        User newUser = getNewUser(userRequestDto);
        userRepository.save(newUser);
        otpService.generateOtp(newUser.getEmail());

        ApiResponse<User> otpSent = ApiResponse.success("201", "OTp sent to email for verification", null);
        return new ResponseEntity<>(otpSent, HttpStatus.CREATED);
    }

    /**
     * logins a user
     * check if a user detail is correct
     *
     * @param loginRequestDto
     * @return token as api response
     */
    @Override
    public ResponseEntity<ApiResponse<Map<String, String>>> login(LoginRequestDto loginRequestDto) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        if (!auth.isAuthenticated()) {
            ApiResponse<Map<String, String>> invalidLogin = ApiResponse.success("400", "Invalid Credentials", null);
            return new ResponseEntity<>(invalidLogin, HttpStatus.BAD_REQUEST);
        }

        User userDetails = customUserDetailsService.getUserDetail();
        if (!userDetails.getStatus().equals(AccountStatus.ACTIVE)) {
            ApiResponse<Map<String, String>> invalidAccountStatus = ApiResponse.success("403", "Account not verified", null);
            return new ResponseEntity<>(invalidAccountStatus, HttpStatus.FORBIDDEN);
        }

        String token = jwtUtil.generateToken(userDetails.getEmail(), userDetails.getRole().name());
        Map<String, String> responseData = new HashMap<>();
        responseData.put("token", token);
        ApiResponse<Map<String, String>> userToken = ApiResponse.success("400", "Sucess", responseData);
        return new ResponseEntity<>(userToken, HttpStatus.OK);
    }

    /**
     * forgot password
     *
     * @param otpRequest email
     * @return string
     * sends otp to user email
     */
    @Override
    public ResponseEntity<ApiResponse<String>> forgotPassword(RegenerateOtpRequestDto otpRequest) {
        return otpService.regenerateOtp(otpRequest.getEmail());
    }

    /**
     * @param changePasswordRequest
     * @return string
     * <p>
     * validate otp validate user and change password;
     */
    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordRequestDto changePasswordRequest) {
        Otp otp = otpRepository.findByToken(changePasswordRequest.getOtp());
        User user = userRepository.findByUserEmail(changePasswordRequest.getEmail());

        if (otp == null || !otp.getUser().equals(user)) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid Details", null), HttpStatus.BAD_REQUEST);
        }
        if (otp.getExpiresAt().before(new Date())) {
            return new ResponseEntity<>(ApiResponse.error("400", "Token is not valid", null), HttpStatus.BAD_REQUEST);
        }
        if (user.getStatus().equals(AccountStatus.INACTIVE)) {
            return new ResponseEntity<>(ApiResponse.error("403", "User is not activated", null), HttpStatus.FORBIDDEN);
        }

        String encodedNewPassword = passwordEncoder.encode(changePasswordRequest.getPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        otpRepository.delete(otp);


        return new ResponseEntity<>(ApiResponse.success("200", "Success", null), HttpStatus.OK);
    }

    /**
     *
     * @return list of enum types
     */
    @Override
    public ResponseEntity<ApiResponse<Map<String, List<Map<String, String>>>>> options() {

        Map<String, List<Map<String, String>>> optionsData = new HashMap<>();
        List<Map<String, String>> eventOptions = Arrays.stream(EventType.values())
                .map(eventType -> {
                    Map<String, String> option = new HashMap<>();
                    option.put("label", eventType.name().charAt(0) + eventType.name().substring(1).toLowerCase());
                    option.put("value", eventType.name());
                    return option;
                }).collect(Collectors.toList());

        optionsData.put("eventType", eventOptions);
        return new ResponseEntity<>(ApiResponse.success("200", "success", optionsData), HttpStatus.OK);
    }


    private User getNewUser(UserRequestDto userRequestDto) {
        User newUser = new User();
        newUser.setEmail(userRequestDto.getEmail());
        newUser.setRole(UserRole.USER);
        newUser.setAddress(userRequestDto.getAddress());
        newUser.setStatus(AccountStatus.INACTIVE);
        newUser.setFirstName(userRequestDto.getFirstName());
        newUser.setLastName(userRequestDto.getLastName());
        newUser.setOtherNames(userRequestDto.getOtherNames());
        newUser.setPhoneNumber(userRequestDto.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return newUser;
    }
}
