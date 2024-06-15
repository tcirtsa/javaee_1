package cn.project.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import cn.project.model.User;

@Mapper
public interface uMapper {
    @Select("select * from user")
    public List<User> findAll();

    @Select("select * from user where account = #{account}")
    public User findByAccount(@Param("account") String account);

    @Update("update user set password = #{password} where account = #{account}")
    public void updatePassword(@Param("account") String account, @Param("password") String password);

    @Delete("delete from user where account = #{account}")
    public void deleteAccount(@Param("account") String account);

    @Insert("insert into user(account,name,password) values(#{account},#{name},#{password})")
    public void insert_user(User user);

    @Select("select * from user order by account")
    public List<User> sortAA();

    @Select("select * from user order by account desc")
    public List<User> sortAD();

    @Select("select * from user order by name")
    public List<User> sortNA();

    @Select("select * from user order by name desc")
    public List<User> sortND();

    @Select("select * from user order by password")
    public List<User> sortPA();

    @Select("select * from user order by password desc")
    public List<User> sortPD();

    @Update("update user set address = #{address} where account = #{account}")
    public void updateAddress(@Param("account") String account, @Param("address") String address);

    @Update("update user set head = #{head} where account = #{account}")
    public void updateHead(@Param("account") String account, @Param("head") String head);
}
