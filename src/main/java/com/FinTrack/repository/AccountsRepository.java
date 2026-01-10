package com.FinTrack.repository;

import com.FinTrack.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    @Query(value = "SELECT * FROM {h-schema}accounts ;", nativeQuery = true)
    Optional<Accounts> findAccountByAccountName(Double amount);
}
