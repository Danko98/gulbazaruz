package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class ProfileEditDto {

    @NotNull
    private String newPhoneNumber; //username

    @NotNull
    private String oldNumber;

    @NotNull
    private String password; //userning passwordi

}
