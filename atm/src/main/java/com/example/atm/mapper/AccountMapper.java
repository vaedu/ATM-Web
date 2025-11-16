package com.example.atm.mapper;

import com.example.atm.entity.Account;
import org.apache.ibatis.annotations.*;

/**
 * 注解方式 Mapper，方法名与 SQL 一一对应。
 * 注意 SQL 中列名使用你数据库的实际列（card,name,password,balance,`limit`,sex）
 */
@Mapper
public interface AccountMapper {

    @Select("SELECT card, name, password, balance, `limit`, sex FROM account WHERE card = #{card}")
    Account findByCard(@Param("card") String card);

    @Insert("INSERT INTO account(card, name, password, balance, `limit`, sex) " +
            "VALUES(#{card}, #{name}, #{password}, #{balance}, #{limit}, #{sex})")
    void insert(Account account);

    @Update("UPDATE account SET balance = #{balance} WHERE card = #{card}")
    void updateBalance(@Param("card") String card, @Param("balance") double balance);

    /**
     * 修改密码（通过 oldPassword 校验）
     * 返回受影响行数，方便判断是否修改成功
     */
    @Update("UPDATE account SET password = #{newPassword} WHERE card = #{card} AND password = #{oldPassword}")
    int updatePassword(@Param("card") String card,
                       @Param("oldPassword") String oldPassword,
                       @Param("newPassword") String newPassword);
}
