package advent.service;

import advent.model.Role;
import advent.repository.RoleRepository;
import advent.service.serviceinterface.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService<Role> {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role addNew(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return roleRepository.findAll(paging);
    }

    @Override
    public Role get(Long roleId) {
        return  roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role " + roleId + "not found"));
    }

    @Override
    @Transactional
    public Role edit(Long roleId, Role role) {
        return roleRepository.findById(roleId)
                .map(ad -> {
                    ad.setName(role.getName());
                    return roleRepository.save(ad);
                })
                .orElseGet(() -> {
                    role.setId(roleId);
                    return roleRepository.save(role);
                });
    }

    @Override
    @Transactional
    public Role delete(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role " + roleId + "not found"));
        roleRepository.deleteById(role.getId());
        return role;
    }
}
