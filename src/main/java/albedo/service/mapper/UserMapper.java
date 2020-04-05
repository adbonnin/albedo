package albedo.service.mapper;

import albedo.domain.User;
import albedo.service.dto.UserDTO;

import javax.inject.Singleton;

@Singleton
public class UserMapper {

    public UserDTO toDTO(User user) {

        if (user == null) {
            return null;
        }

        final UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        return dto;
    }
}
