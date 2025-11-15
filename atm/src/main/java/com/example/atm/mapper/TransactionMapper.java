package com.example.atm.mapper;

import com.example.atm.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransactionMapper {

    int insert(Transaction t);

    List<Transaction> findByCard(@Param("card") String card);
}
