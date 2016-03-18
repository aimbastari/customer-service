package com.icube.investframe;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icube.investframe.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
