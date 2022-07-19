package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.apiResponse.UserData;
import uz.gullbozor.gullbozor.dto.*;
import uz.gullbozor.gullbozor.entity.UserEntity;
import uz.gullbozor.gullbozor.repository.UserRepo;
import uz.gullbozor.gullbozor.verifysms.PhoneVerificationService;
import uz.gullbozor.gullbozor.verifysms.VerificationResult;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PhoneVerificationService phoneVerificationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse checkUser(RegisterDto registerDto) {

        if (userRepo.existsByUserNameAndPassword( registerDto.getPhoneNumber(),registerDto.getPassword())) {
            return new ApiResponse("This Login and Password already exists",false);
        }
        return null;
    }

    public ApiResponse sendSmsToUser(VerifyDto verifyDto) {
        if (userRepo.existsByPhoneNumber( verifyDto.getPhoneNumber())) {
            return new ApiResponse("This phone number already exists",false);
        }

        VerificationResult result= phoneVerificationService.startVerification(verifyDto.getPhoneNumber());
        if (result.isValid()) {
            return new ApiResponse("Otp sent",true);
        }
        return new ApiResponse("Otp failed to sent..",false);
    }




    public ApiResponse editUser(RegisterDto registerDto, Long id) {
        if (!userRepo.existsById(id)) {
            return new ApiResponse("Bunday foydalanuvchi topilmadi",false);
        }


        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        UserEntity userEntity = optionalUserEntity.get();

        if (userRepo.existsByPhoneNumber(registerDto.getPhoneNumber()) && (!registerDto.getPhoneNumber().equals(userEntity.getPhoneNumber()))) {
            return new ApiResponse("Bu telefon raqam ro'yxatdan o'tgan",false);
        }

        userEntity.setPhoneNumber(registerDto.getPhoneNumber());
        userEntity.setUserName(registerDto.getUserName());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepo.save(userEntity);

    return new ApiResponse("Profil muvaffaqiyatli tahrirlandi",true);

    }

    public ApiResponse getUserById(Long id) {
        if (!userRepo.existsById(id)) {
            return new ApiResponse("Not found User account",false);
        }
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        return new ApiResponse(optionalUserEntity.get());
    }

    public List<UserEntity> getUserList() {
        return userRepo.findAll();
    }

    public ApiResponse deleteUserById(Long id) {
        if (!userRepo.existsById(id)) {
            return new ApiResponse("Not found user account ",false);
        }
        userRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }



}
