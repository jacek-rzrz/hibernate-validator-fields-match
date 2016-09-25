# hibernate-validator-fields-match
Annotations for validating fields that must match.

```java
@Data
@PasswordsMatch
@EmailsMatch
@FieldsMatch(fields = PhoneNumberFields.class)
public class User implements PasswordAndConfirmation, EmailAndConfirmation {

    @NotBlank
    private String password;

    private String confirmedPassword;

    @Email
    private String email;

    private String confirmedEmail;

    private String phoneNumber;

    private String confirmedPhoneNumber;
}
```

```java
public class PhoneNumberFields implements SelectFields<User> {

    @Override
    public Object field(User obj) {
        return obj.getPhoneNumber();
    }

    @Override
    public Object confirmedField(User obj) {
        return obj.getConfirmedPhoneNumber();
    }

    @Override
    public String getConfirmationFieldName() {
        return "confirmedPhoneNumber";
    }
}
```
