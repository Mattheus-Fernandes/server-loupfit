package com.loupfit.loupfit.business;

import com.loupfit.loupfit.business.converter.StoreItemConverter;
import com.loupfit.loupfit.business.dto.storeitem.StoreItemCreateDTO;
import com.loupfit.loupfit.business.dto.storeitem.StoreItemDTO;
import com.loupfit.loupfit.infrastructure.entity.StoreItem;
import com.loupfit.loupfit.infrastructure.entity.User;
import com.loupfit.loupfit.infrastructure.exceptions.ConflictException;
import com.loupfit.loupfit.infrastructure.exceptions.NotFoundException;
import com.loupfit.loupfit.infrastructure.repository.StoreItemRepository;
import com.loupfit.loupfit.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreItemService {

    private final StoreItemConverter storeItemConverter;
    private final StoreItemRepository storeItemRepository;
    private final UserRepository userRepository;

    private User getUserLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepository.findByUsername(username).orElseThrow(
                () -> new ConflictException("Usuário logado não encontrado.")
        );
    }

    public StoreItemCreateDTO createItem(StoreItemCreateDTO storeItemReqDTO) {

        User user = getUserLogged();

        if (user.getRole() != 1) {
            throw new ConflictException("Você não tem permissão para cadastrar item.");
        }

        StoreItem newItem = storeItemConverter.storeItemEntity(storeItemReqDTO);
        newItem.setCreatedBy(user);

        return storeItemConverter.storeItemCreateDTO(storeItemRepository.save(newItem));
    }

    public List<StoreItemDTO> findAllStoreItens() {
        List<StoreItem> storeItems = storeItemRepository.findAll();

        return storeItemConverter.storeItemDTOList(storeItems);
    }

    public List<StoreItemDTO> filterCreatedBy(String createdBy, String supplier, String item) {
        try {
            List<StoreItem> storeItems = new ArrayList<StoreItem>();


            if (createdBy != null && !createdBy.isEmpty()) {
                storeItems = storeItemRepository.findByCreatedByUsername(createdBy);
            } else if (supplier != null && !supplier.isEmpty()) {
                storeItems = storeItemRepository.findBySupplierContainsIgnoreCase(supplier);
            } else if (item != null && !item.isEmpty()) {
                storeItems = storeItemRepository.findByItemContainsIgnoreCase(item);
            }

            if (storeItems.isEmpty()) {
                throw new NotFoundException("Nenhum cadastro encontrado.");
            }

            return storeItemConverter.storeItemDTOList(storeItems);

        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public StoreItemDTO deleteItem(Long id) {

        try {
            User user = getUserLogged();

            if (user.getRole() != 1) {
                throw new ConflictException("Você não tem permissão para excluir esse item.");
            }

            StoreItem item = storeItemRepository.findById(id).orElseThrow(
                    () -> new ConflictException("Item não encontrado")
            );

            storeItemRepository.deleteById(id);

            return storeItemConverter.storeItemDTO(item);


        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public StoreItemDTO updateItem(Long id, StoreItemDTO storeItemDTO) {

        User user = getUserLogged();

        if (user.getRole() != 1) {
            throw new ConflictException("Você não tem permissão para editar esse item.");
        }

        StoreItem itemEntity = storeItemRepository.findById(id).orElseThrow(
                () -> new ConflictException("Item não encontrado")
        );

        StoreItem editItem = storeItemConverter.updateItem(storeItemDTO, itemEntity);

        return storeItemConverter.storeItemDTO(storeItemRepository.save(editItem));

    }
}
