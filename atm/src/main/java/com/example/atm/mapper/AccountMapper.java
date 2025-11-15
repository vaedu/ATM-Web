package com.example.atm.mapper;

import com.example.atm.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    int insert(Account a);

    Account findByCardNumber(@Param("card") String card);

    int updateBalance(@Param("card") String card,
                      @Param("balance") double balance);
}
