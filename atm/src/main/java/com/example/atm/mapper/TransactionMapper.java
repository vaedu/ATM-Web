package com.example.atm.mapper;

import com.example.atm.entity.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO `transaction`(card, type, amount, remark, time) " +
            "VALUES(#{card}, #{type}, #{amount}, #{remark}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Transaction t);

    @Select("SELECT id, card, type, amount, remark, time " +
            "FROM `transaction` WHERE card = #{card} ORDER BY time DESC LIMIT 50")
    List<Transaction> findByCard(String card);
}
