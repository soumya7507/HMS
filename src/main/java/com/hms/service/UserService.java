package com.hms.service;

import com.hms.config.SecurityConfig;
import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public int x;
    private AppUserRepository appUserRepository;
    private JWTService jwtService;
    private OTPService otpService;
    private WhatsAppService whatsAppService;




    public UserService(AppUserRepository appUserRepository, SecurityConfig securityConfig, JWTService jwtService, OTPService otpService, WhatsAppService whatsAppService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
        this.otpService = otpService;
        this.whatsAppService = whatsAppService;
    }


    public String alreadyExist(AppUser user) {

        Optional<AppUser> byUsername = appUserRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            return "Already Username Exist";
        }
        Optional<AppUser> byEmail = appUserRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            return "Already Email Exist";
        }
        return null;
    }

    AppUser mapToEntity(UserDto userDto){
        AppUser appUser=new AppUser();
        appUser.setUsername(userDto.getUsername());
        appUser.setEmail(userDto.getEmail());
        appUser.setName(userDto.getName());
        appUser.setPassword(userDto.getPassword());
        return appUser;
    }

    UserDto mapToDto(AppUser appUser){
        UserDto dto=new UserDto();
        dto.setUsername(appUser.getUsername());
        dto.setEmail(appUser.getEmail());
        dto.setName(appUser.getName());

        return dto;
    }

    public UserDto createUser(AppUser appUser) {

        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(hashpw);
        appUser.setRole("ROLE_USER");
        AppUser save = appUserRepository.save(appUser);
        UserDto userDto1 = mapToDto(save);
        return userDto1;
    }
    public AppUser createUser1(AppUser user){
        AppUser save = appUserRepository.save(user);
        return save;
    }
    public List getAllUsers(){
        List<AppUser> all = appUserRepository.findAll();
        return all;
    }

    public String login(LoginDto loginDto) {
        Optional<AppUser> username = appUserRepository.findByUsername(loginDto.getUsername());
        if (username.isPresent()) {
            AppUser appUser = username.get();
            boolean checkpw = BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword());
            if (checkpw) {
                String s = otpService.generateOTP(appUser.getMobileNumber());
                //  otpService.generateWhatsappOTP(appUser.getMobileNumber());
                whatsAppService.sendWhatsAppMessage(appUser.getMobileNumber(), s);
                otpService.generateEmailOtp(appUser.getEmail());
                return "OTP sent successfully kindly verify it";
            }
        }
        return "Invalid Username Or Password";
    }


    public UserDto createPropertyOwner(AppUser appUser) {

        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(hashpw);
        appUser.setRole("ROLE_OWNER");
        AppUser save = appUserRepository.save(appUser);
        UserDto userDto1 = mapToDto(save);
        return userDto1;
    }


}
