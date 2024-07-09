package cn.project.jtee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import cn.project.jtee.model.Pay;

@Mapper
public interface PayMapper {
    @Select("select * from pay")
    public List<Pay> findAll();

    @Select("select * from pay where who = #{who}")
    public List<Pay> findByWho(@Param("who") String who);

    @Update("update pay set image = #{image},pay = #{pay},description = #{description} where id = #{id}")
    public void updateImage(@Param("id") Integer id, @Param("image") String image, @Param("pay") String pay, @Param("description") String description);

    @Insert("insert into pay(who,time,description) values(#{who},#{time},#{description})")
    public boolean insertPay(Pay pay);

    @Delete("delete from pay where id = #{id}")
    public boolean deleteByID(@Param("id") Integer id);
    
}
