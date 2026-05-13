package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserReadDto;
import org.example.entity.User;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto mapFrom(User user) {
        return new UserReadDto(user.getId(),
                user.getPersonalInfo(),
                user.getUsername(),
                user.getRole(),
                companyReadMapper.mapFrom(user.getCompany()));
    }
}
