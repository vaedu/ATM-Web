package com.example.atm.mapper;

import com.example.atm.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    int insert(Account account);

    Account findByCardNumber(@Param("cardNumber") String cardNumber);

    int updateBalance(@Param("cardNumber") String cardNumber,
                      @Param("balance") double balance);
}
