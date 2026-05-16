package org.example.dto;

import org.example.entity.PersonalInfo;
import org.example.entity.Role;

public record UserCreateDto(PersonalInfo personalInfo,
                            String username,
                            Role role,
                            Integer companyId) {
}
