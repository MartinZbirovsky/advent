package advent.service;

import advent.model.Role;
import advent.repository.RoleRepository;

public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role) { return roleRepository.save(role);}
}
