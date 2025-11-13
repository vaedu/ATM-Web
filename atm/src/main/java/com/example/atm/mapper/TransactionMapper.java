package com.example.atm.mapper;

import com.example.atm.entity.TransactionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransactionMapper {

    int insert(TransactionRecord record);

    List<TransactionRecord> findByCard(@Param("card") String card);
}
