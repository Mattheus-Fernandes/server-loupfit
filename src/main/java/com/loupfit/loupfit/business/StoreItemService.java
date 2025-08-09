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

    public StoreItemCreateDTO createItem(StoreItemCreateDTO storeItemReqDTO) {

        User user = getUserLogged();

        if (user.getRole() != 1) {
            throw new ConflictException("Você não tem permissão para cadastrar item.");
        }

        StoreItem newItem = storeItemConverter.storeItemEntity(storeItemReqDTO);
        newItem.setCreatedBy(user);

        return storeItemConverter.storeItemCreateDTO(storeItemRepository.save(newItem));
    }


    private User getUserLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepository.findByUsername(username).orElseThrow(
                () -> new ConflictException("Usuário logado não encontrado.")
        );
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
}
