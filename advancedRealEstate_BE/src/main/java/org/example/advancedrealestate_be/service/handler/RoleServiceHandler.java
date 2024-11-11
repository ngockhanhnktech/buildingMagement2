package org.example.advancedrealestate_be.service.handler;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrealestate_be.dto.request.RolePermissionRequest;
import org.example.advancedrealestate_be.dto.request.RoleRequest;
import org.example.advancedrealestate_be.dto.response.RolePermissionResponse;
import org.example.advancedrealestate_be.dto.response.RoleResponse;
import org.example.advancedrealestate_be.entity.Permission;
import org.example.advancedrealestate_be.entity.Role;
import org.example.advancedrealestate_be.mapper.RoleMapper;
import org.example.advancedrealestate_be.repository.PermissionRepository;
import org.example.advancedrealestate_be.repository.RoleRepository;
import org.example.advancedrealestate_be.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceHandler implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleMapper roleMapper;


    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public RolePermissionResponse updateRolePermission(String roleName, RolePermissionRequest request) {
        Optional<Role> roleOptional = roleRepository.findById(roleName);
        if(roleOptional.isPresent()){
            Role role = roleOptional.get();
            Role oldPermissions = roleOptional.get();
            role.setPermissions(request.getPermissions());
            Role roleUpdate = roleRepository.save(role);
            return new RolePermissionResponse(
                roleUpdate.getName(),
                roleUpdate.getDescription(),
                roleUpdate.getPermissions(),
                oldPermissions.getPermissions()
            );
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
