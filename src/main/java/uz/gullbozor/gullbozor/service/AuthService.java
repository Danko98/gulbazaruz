package uz.gullbozor.gullbozor.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.apiResponse.UserData;
import uz.gullbozor.gullbozor.dto.Login;
import uz.gullbozor.gullbozor.dto.RegisterDto;
import uz.gullbozor.gullbozor.entity.UserEntity;
import uz.gullbozor.gullbozor.entity.enums.RoleName;
import uz.gullbozor.gullbozor.repository.RoleRepo;
import uz.gullbozor.gullbozor.repository.UserRepo;
import uz.gullbozor.gullbozor.security.JwtProvider;
import uz.gullbozor.gullbozor.verifysms.PhoneNumberDto;
import uz.gullbozor.gullbozor.verifysms.PhoneVerificationService;
import uz.gullbozor.gullbozor.verifysms.VerificationResult;

import java.util.Collections;


@Service
public class AuthService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    PhoneVerificationService phoneVerificationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public UserData login(Login login) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login.getPhoneNumber(),
                    login.getPassword()
            ));
            System.out.println(authenticate);
            UserEntity user = (UserEntity) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(login.getPhoneNumber(), user.getRoles());
            return new UserData(true,token,user.getId());
        } catch (BadCredentialsException badCredentialsException) {
            return new UserData("telefon raqam yoki parol xato",false);
        }
    }



    public ApiResponse checkSMSCode(String code, String phoneNumber) {
        VerificationResult result = phoneVerificationService.checkVerification(phoneNumber, code);
        if (result.isValid()) {
            return new ApiResponse("sms code is valid",true);
        }else {
            return new ApiResponse("sms code is not valid",false);
        }
    }

    public UserData registerUser(RegisterDto registerDto) {

      if (userRepo.existsByPhoneNumber(registerDto.getPhoneNumber())) {
          return new UserData("Bu telefon raqam ro'yxatdan o'tgan",false);
        }

              UserEntity user = new UserEntity();
              user.setPhoneNumber(registerDto.getPhoneNumber());
              user.setUserName(registerDto.getUserName());
              user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
              if (registerDto.getPhoneNumber().equals("+998977169686")){
                  user.setRoles(Collections.singleton(roleRepo.findByName(RoleName.ROLE_ADMIN)));
              }else {
                  user.setRoles(Collections.singleton(roleRepo.findByName(RoleName.ROLE_USER)));
              }
              user.setEnabled(true);

              userRepo.save(user);
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    registerDto.getPhoneNumber(),
                    registerDto.getPassword()
            ));
            System.out.println(authenticate);
            UserEntity user1 = (UserEntity) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(registerDto.getPhoneNumber(), user.getRoles());
            return new UserData(true,token,user1.getId());
        } catch (BadCredentialsException badCredentialsException) {
            return new UserData("Xatolik",false);
        }
    }

    public ApiResponse sendSmsCode(PhoneNumberDto phoneNumberDto) {
        VerificationResult result = phoneVerificationService.startVerification(phoneNumberDto.getPhoneNumber());
        if (result.isValid()) {
            return new ApiResponse("Otp sent",true);
        }else {
            return new ApiResponse("Otp was not sent",false);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String  phoneNumber) throws UsernameNotFoundException {

//        Optional<UserEntity> optionalUserEntity = userRepo.findByUserName(username);
//        if (optionalUserEntity.isPresent()) {
//            return optionalUserEntity.get();
//        }
//        else {
//            throw new UsernameNotFoundException(username + "Not found");
//        }

        return userRepo.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(phoneNumber + "Not found"));

    }


}
