package com.example.atm.mapper;

import com.example.atm.entity.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transaction(card, type, amount, remark, time) VALUES(#{card}, #{type}, #{amount}, #{remark}, #{time})")
    void insert(Transaction t);

    @Select("SELECT * FROM transaction WHERE card = #{card} ORDER BY time DESC LIMIT 10")
    List<Transaction> findByCard(String card);
}
