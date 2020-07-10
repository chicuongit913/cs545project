package cs545_project.online_market.controller.request;

import cs545_project.online_market.service.UserService;
import cs545_project.online_market.validation.filedMatch.FieldMatch;
import cs545_project.online_market.validation.uniqueKey.Unique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "password", second = "confirm_password", message = "The password fields must match")
public class UserRequest {

    @NotEmpty
    @Size(min = 3, max = 50, message = "{Size.validation}")
    private String firstName;

    @NotEmpty
    @Size(min = 4, max = 50, message = "{Size.validation}")
    private String lastName;

    @NotEmpty
    @Email
    @Unique(service = UserService.class, fieldName = "email", message = "Email is exist, please choose another username.")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 50, message = "{Size.validation}")
    @Unique(service = UserService.class, fieldName = "username", message = "User name is exist, please choose another username.")
    private String username;

    @Size(min = 6, max = 100, message = "{Size.validation}")
    @NotEmpty
    private String password;

    @NotEmpty
    private String confirm_password;
}
