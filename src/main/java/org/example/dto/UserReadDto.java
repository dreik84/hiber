package org.example.dto;

import org.example.entity.PersonalInfo;
import org.example.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          Role role,
                          CompanyReadDto company) {
}
