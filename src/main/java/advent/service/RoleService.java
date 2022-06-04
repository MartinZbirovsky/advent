package advent.service;

import advent.model.Role;
import advent.repository.RoleRepository;

public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role) { return roleRepository.save(role);}
}
