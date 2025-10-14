package com.hubpedro.bookstoreapi.service;

import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<UserResponse> listPaginatedUser(Pageable pageable);

    UserResponse createUser(UserRequest userRequest);

    UserResponse findUserById(Long ind);

    void userDelete(Long id);

    UserResponse userUpdated(Long id, UserRequest userRequest);

    UserResponse userPatch(Long id, UserRequest userRequest);
}
