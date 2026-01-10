package com.FinTrack.repository;

import com.FinTrack.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    @Query(value = " " +
            "SELECT \n" +
            "\tu.user_name,\n" +
            "\tt.transaction_id ,\n" +
            "\tt.amount,\n" +
            "\tt.transaction_date,\n" +
            "\tt.description,\n" +
            "\tt.flow,\n" +
            "\ta.balance ,\n" +
            "\ta.account_name,\n" +
            "\tc.category_name ,\n" +
            "\tc.transaction_type\n" +
            "from\n" +
            "\t{h-schema}transactions t\n" +
            "join {h-schema}accounts a on\n" +
            "\tt.account_id = a.account_id\n" +
            "join {h-schema}category c on\n" +
            "\tt.category_id = c.category_id\n" +
            "join {h-schema}user_master u on\n" +
            "\tt.user_id = u.user_id\n" +
            "where \n" +
            "\tt.user_id = :userId ", nativeQuery = true)
    List<Object[]> findAllTransactionsByUserId(@Param("userId") Long userId);

    @Query(value = " " +
            "SELECT \n" +
            "\tu.user_name,\n" +
            "\tt.transaction_id ,\n" +
            "\tt.amount,\n" +
            "\tt.transaction_date,\n" +
            "\tt.description,\n" +
            "\tt.flow,\n" +
            "\ta.balance ,\n" +
            "\ta.account_name,\n" +
            "\tc.category_name ,\n" +
            "\tc.transaction_type\n" +
            "from\n" +
            "\t{h-schema}transactions t\n" +
            "join {h-schema}accounts a on\n" +
            "\tt.account_id = a.account_id\n" +
            "join {h-schema}category c on\n" +
            "\tt.category_id = c.category_id\n" +
            "join {h-schema}user_master u on\n" +
            "\tt.user_id = u.user_id\n" +
            "where \n" +
            "\tt.transaction_id = :transactionId ", nativeQuery = true)
    Optional<Object[]> findTransactionByTransactionId(@Param("transactionId") Long transactionId);
}
