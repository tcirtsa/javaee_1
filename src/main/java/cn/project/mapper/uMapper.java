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

    @Select("select * from user where authority = 0 || authority = 2")
    public List<User> findByAuthority();

    @Select("select * from user where authority = 0 || authority = 2 and account = #{account}")
    public User findByAuthorityAndAccount(@Param("account") String account);

    @Update("update user set password = #{password} where account = #{account}")
    public void updatePassword(@Param("account") String account, @Param("password") String password);

    @Delete("delete from user where account = #{account}")
    public void deleteAccount(@Param("account") String account);

    @Insert("insert into user(account,name,password,phone,address,authority,head) values(#{account},#{name},#{password},#{phone},#{address},#{authority},#{head})")
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

    @Select("select * from user order by account where authority = 0 || authority = 2")
    public List<User> sortUAA();

    @Select("select * from user order by account where authority = 0 || authority = 2 desc")
    public List<User> sortUAD();

    @Select("select * from user order by name where authority = 0 || authority = 2")
    public List<User> sortUNA();

    @Select("select * from user order by name where authority = 0 || authority = 2 desc")
    public List<User> sortUND();

    @Select("select * from user order by password where authority = 0 || authority = 2")
    public List<User> sortUPA();

    @Select("select * from user order by password where authority = 0 || authority = 2 desc")
    public List<User> sortUPD();

    @Update("update user set address = #{address} where account = #{account}")
    public void updateAddress(@Param("account") String account, @Param("address") String address);

    @Update("update user set head = #{head} where account = #{account}")
    public void updateHead(@Param("account") String account, @Param("head") String head);
}
