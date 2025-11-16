package com.example.atm.mapper;

import com.example.atm.entity.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    // 根据卡号查询账户
    @Select("SELECT card, name, password, sex AS gender, `limit` AS dailyLimit, balance " +
            "FROM account WHERE card = #{card}")
    Account findByCard(String card);

    // 插入账户
    @Insert("INSERT INTO account(card, name, password, sex, `limit`, balance) " +
            "VALUES(#{card}, #{name}, #{password}, #{gender}, #{dailyLimit}, #{balance})")
    void insert(Account account);

    // 更新余额
    @Update("UPDATE account SET balance = #{balance} WHERE card = #{card}")
    void updateBalance(@Param("card") String card, @Param("balance") double balance);

    // 修改密码
    @Update("UPDATE account SET password = #{newPassword} WHERE card = #{card} AND password = #{oldPassword}")
    int updatePassword(@Param("card") String card,
                       @Param("oldPassword") String oldPassword,
                       @Param("newPassword") String newPassword);

}
