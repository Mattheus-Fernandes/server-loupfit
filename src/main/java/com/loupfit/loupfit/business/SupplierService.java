package com.loupfit.loupfit.business;

import com.loupfit.loupfit.business.converter.SupplierConverter;
import com.loupfit.loupfit.business.dto.supplier.SupplierCreateDTO;
import com.loupfit.loupfit.business.dto.supplier.SupplierDTO;
import com.loupfit.loupfit.infrastructure.entity.Supplier;
import com.loupfit.loupfit.infrastructure.entity.User;
import com.loupfit.loupfit.infrastructure.exceptions.ConflictException;
import com.loupfit.loupfit.infrastructure.exceptions.NotFoundException;
import com.loupfit.loupfit.infrastructure.repository.SupplierRepository;
import com.loupfit.loupfit.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final SupplierConverter supplierConverter;

    private User getUserLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepository.findByUsername(username).orElseThrow(
                () -> new ConflictException("Usuário logado não encontrado.")
        );
    }

    public SupplierCreateDTO registerSupplier(SupplierCreateDTO supplierCreateDTO) {
        try {

            User user = getUserLogged();

            verifyAction("post", user);
            existFantasyName(supplierCreateDTO);

            Supplier supplier = supplierConverter.supplierCreateEntity(supplierCreateDTO);
            supplier.setCreatedBy(user);

            return supplierConverter.supplierCreateDTO(supplierRepository.save(supplier));

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }

    }

    public void existFantasyName(SupplierCreateDTO supplier) {
        boolean exist = supplierRepository.existsByFantasyNameContainsIgnoreCase(supplier.getFantasyName());

        if (exist) {
            throw new ConflictException("Fornecedor " + supplier.getFantasyName() + " já cadastrado(a).");
        }
    }

    public void verifyAction(String action, User user) {

        switch (action) {
            case "post":

                if (user.getRole() != 1) {
                    throw new ConflictException("Você não tem permissão para cadastrar fornecedor.");
                }

                break;

            case "delete":

                if (user.getRole() != 1) {
                    throw new ConflictException("Você não tem permissão para excluir fornecedor.");
                }

                break;

            case "put":

                if (user.getRole() != 1) {
                    throw new ConflictException("Você não tem permissão para editar fornecedor");
                }

                break;
        }
    }

    public List<SupplierDTO> findAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return supplierConverter.supplierDTOList(suppliers);
    }

    public List<SupplierDTO> filterSupplier(String fantasyName, String type) {

        List<Supplier> suppliers = new ArrayList<Supplier>();

        if (fantasyName != null && !fantasyName.isEmpty()) {
            suppliers = supplierRepository.findByFantasyNameContainsIgnoreCase(fantasyName);
        } else if (type != null && !type.isEmpty()) {
            suppliers = supplierRepository.findByTypeContainsIgnoreCase(type);
        }

        if (suppliers.isEmpty()) {
            throw new NotFoundException("Nenhum fornecedor encontrado.");
        }

        return supplierConverter.supplierDTOList(suppliers);

    }

    public SupplierDTO deleteSupplier(Long id) {
        try {

            User user = getUserLogged();

            if (user.getRole() != 1) {
                throw new ConflictException("Você não tem permissão para excluir fornecedor.");
            }

            Supplier supplier = supplierRepository.findById(id).orElseThrow(
                    () -> new ConflictException("Não foi possível excluir esse fornecedor")
            );

            supplierRepository.deleteById(id);

            return supplierConverter.supplierDTO(supplier);

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        try {

            User user = getUserLogged();

            if (user.getRole() != 1) {
                throw new ConflictException("Você não tem permissão para editar o fornecedor.");
            }

            Supplier supplierEntity = supplierRepository.findById(id).orElseThrow(
                    () -> new ConflictException("Não foi possível excluir esse fornecedor")
            );

            Supplier supplierEdit = supplierConverter.updateSupplier(supplierDTO, supplierEntity);

            return supplierConverter.supplierDTO(supplierRepository.save(supplierEdit));


        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }
}
