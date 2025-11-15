package com.example.atm.mapper;

import com.example.atm.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    Account findByCard(@Param("card") String card);

    int updateBalance(@Param("card") String card, @Param("balance") double balance);

    int updatePassword(@Param("card") String card, @Param("password") String password);

    int insert(Account account);
}
