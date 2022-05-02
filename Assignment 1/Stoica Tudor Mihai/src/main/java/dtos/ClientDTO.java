package dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ClientDTO {
    private long id;
    private String name;
    private String identityCardNumber;
    private String personalNumericCode;
    private String address;
}
