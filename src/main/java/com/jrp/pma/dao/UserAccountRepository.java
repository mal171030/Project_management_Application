package com.jrp.pma.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jrp.pma.entities.UserAccount;

public interface UserAccountRepository
		extends CrudRepository<UserAccount, Long>, PagingAndSortingRepository<UserAccount, Long> {

}
