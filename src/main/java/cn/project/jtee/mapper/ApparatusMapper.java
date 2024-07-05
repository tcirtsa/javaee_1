package cn.project.jtee.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import cn.project.jtee.model.Apparatus;

@Mapper
public interface ApparatusMapper {
    @Select("select * from apparatus")
    public List<Apparatus> findAll();

    @Select("select * from apparatus where id = #{id}")
    public Apparatus findByID(@Param("id") String id);

    @Select("select * from apparatus where type = #{type}")
    public List<Apparatus> findByType(@Param("type") String type);

    @Select("select * from apparatus where who = #{who}")
    public List<Apparatus> findByWho(@Param("who") String who);

    @Select("select * from apparatus where status = #{status}")
    public List<Apparatus> findByStatus(@Param("status") Integer status);

    @Delete("delete from apparatus where id = #{id}")
    public void deleteByID(@Param("id") String id);

    @Update("update apparatus set who = #{who}, status = #{status}, time = #{time}, address = #{address} where id = #{id}")
    public void lendByID(Apparatus apparatus);

    @Update("update apparatus set type = #{type}, name = #{name}, description = #{description},id = #{Newid},address = #{address} where id = #{id}")
    public void updateByID(@Param("id") String id, @Param("type") String type, @Param("name") String name,
            @Param("description") String description, @Param("Newid") String Newid, @Param("address") String address);

    @Update("update apparatus set status = #{status},time = #{time},who = #{who},address = #{address} where id = #{id}")
    public void returnByID(Apparatus apparatus);

    @Insert("insert into apparatus (id,name,type,status,who,address,description,time) values(#{id},#{name},#{type},#{status},#{who},#{address},#{description},#{time})")
    public void addApparatus(Apparatus apparatus);

    @Select("select * from apparatus order by name")
    public List<Apparatus> sortNA();

    @Select("select * from apparatus order by name desc")
    public List<Apparatus> sortND();

    @Select("select * from apparatus order by time")
    public List<Apparatus> sortTA();

    @Select("select * from apparatus order by time desc")
    public List<Apparatus> sortTD();

    @Select("select * from apparatus order by address")
    public List<Apparatus> sortAA();

    @Select("select * from apparatus order by address desc")
    public List<Apparatus> sortAD();

    @Select("select * from apparatus order by status")
    public List<Apparatus> sortSA();

    @Select("select * from apparatus order by status desc")
    public List<Apparatus> sortSD();

    @Select("select * from apparatus order by who")
    public List<Apparatus> sortWA();

    @Select("select * from apparatus order by who desc")
    public List<Apparatus> sortWD();

    @Update("update apparatus set image = #{image} where id = #{id}")
    public void updateImage(@Param("id") String id, @Param("image") String image);
}
