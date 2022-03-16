package repositories.dtos;

import lombok.Getter;
import lombok.Setter;
import models.User;

@Getter
@Setter
public class RegularUserDTO {
    private User user;
    private long id;
}
