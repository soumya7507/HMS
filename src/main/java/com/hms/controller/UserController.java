package com.hms.controller;


import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;

import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import com.hms.repository.BookingsRepository;
import com.hms.service.JWTService;
import com.hms.service.OTPService;
import com.hms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    private JWTService jwtService;
    private OTPService otpService;
    private final BookingsRepository bookingsRepository;
    private final AppUserRepository appUserRepository;

    public UserController(UserService userService, JWTService jwtService, OTPService otpService,
                          BookingsRepository bookingsRepository,
                          AppUserRepository appUserRepository) {
        this.userService = userService;

        this.jwtService = jwtService;
        this.otpService = otpService;
        this.bookingsRepository = bookingsRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/signup")
    //localhost:8080/api/v1/users/signup
    public ResponseEntity<?> signUp(
            @RequestBody AppUser user
    )
    {
        String s = userService.alreadyExist(user);
        if(s!=null) {
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        UserDto dto=userService.createUser(user);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginDto loginDto
    ){
        String status=userService.login(loginDto);
        return new ResponseEntity<>(status,HttpStatus.OK);

    }
    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createPropertyOwnerUser(
            @RequestBody AppUser user
    )
    {
        String s = userService.alreadyExist(user);
        if(s!=null) {
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        UserDto dto=userService.createPropertyOwner(user);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);

    }

//    @PostMapping("/generate-login-otp")
//    public String login(@RequestParam String mobileNumber){
//         otpService.generateOTP(mobileNumber);
//        return "OTP Generated";
//    }

    @PostMapping("/validate-login-otp")
    public ResponseEntity<?> validateOTP(@RequestParam String mobileNumber , @RequestParam String otp) {

        boolean isValid = otpService.validateOTP(mobileNumber, otp);
        if (isValid) {
            Optional<AppUser> byMobileNumber = appUserRepository.findByMobileNumber(mobileNumber);
            AppUser appUser = byMobileNumber.get();
            String token = jwtService.generateToken(appUser.getUsername());
            TokenDto tokenDto=new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setStatus("Created");
            return new ResponseEntity<>(tokenDto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or Expired OTP",HttpStatus.OK);
        }
    }
    @PostMapping("/validate-email-otp")
    public ResponseEntity<?> validateEmailOtp(@RequestParam String email,@RequestParam String otp){
        boolean b = otpService.validateEmailOtp(email, otp);
        if(b){
            AppUser appUser = appUserRepository.findByEmail(email).get();
            String s = jwtService.generateToken(appUser.getUsername());
            TokenDto tokenDto=new TokenDto();
            tokenDto.setStatus("Validate Successfully");
            tokenDto.setToken(s);
            return  new ResponseEntity<>(tokenDto,HttpStatus.OK);
        }return new ResponseEntity<>("Invalid or Expired OTP",HttpStatus.OK);

    }

}
