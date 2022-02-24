package com.loco.assignment.transactionsum.domain.repository;

import com.loco.assignment.transactionsum.domain.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

	List<Transaction> findByType(String type);

	@Query(value = "select t from Transaction t inner join t.parent p where p.id = :parentId")
	List<Transaction> findTransactionByParentId(Long parentId);

	List<Transaction> findByIdIn(List<Long> transactionIds);
}
