package com.vitalmovs.services;

import com.vitalmovs.entities.Authority;

public interface AuthorityService {

    public Authority findById(Long id);
    public Authority findByName(String name);

    public Authority add(Authority authority);

}
