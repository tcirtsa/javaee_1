package cn.project.jtee.service;

import cn.project.jtee.model.User;
import cn.project.jtee.selfEnum.SelectLevel;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @Author yiren
 * @Date 2024/7/2
 * @Description
 */
public interface UserMapperService {
    /**
     * create by: yiren
     * description: 获得数据库的所有user对象,level 代表查询的对象的权限大小
     *              0代表所有权限，1代表管理员权限，2代表维修权限，3代表超级管理员权限
     * create time: 2024/7/2 上午11:57
     *
     * @params [SelectLevel level]
     * @return user[]
     */
     List<User> getAllAccountsByLevel(SelectLevel level);

     /**
      * create by: yiren
      * description: 通过type类型判断更新user的内容
      *              type 可以是 password,authority
      * create time: 2024/7/2 下午12:54
      * 
      * @params [User user,String type]
      * @return ResponseEntity
      */
    ResponseEntity<?> updateUser(User user,String type);

    /**
     * create by: yiren
     * description: 通过account删除user
     * create time: 2024/7/2 下午12:54
     *
     * @params [String account]
     * @return ResponseEntity
     */
    ResponseEntity<?> deleteAccount(User user);

    /**
     * create by: yiren
     * description: 通过account插入user
     * create time: 2024/7/2 下午12:54
     *
     * @params [User user]
     * @return ResponseEntity
     */
    ResponseEntity<?> insertUser(User user);

    /**
     * create by: yiren
     * description: 通过account查询user,type代表查询的对象的权限大小
     * create time: 2024/7/2 下午12:54
     *
     * @params [User user]
     * @return ResponseEntity
     */
    ResponseEntity<?> queryUser(User user,String type);

    /**
     * create by: yiren
     * description: 通过account排序user
     * create time: 2024/7/2 下午12:54
     *
     * @params [User user]
     * @return ResponseEntity
     */
    ResponseEntity<?> sort(User user);

    /**
     * create by: yiren
     * description: 通过account排序user
     * create time: 2024/7/2 下午12:54
     *
     * @params [User user]
     * @return ResponseEntity
     */
    ResponseEntity<?> sort_users(User user);
}
