package cn.project.jtee.service.impl;

import cn.project.jtee.model.User;
import cn.project.jtee.selfEnum.SelectLevel;
import cn.project.jtee.service.UserMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cn.project.jtee.mapper.UserMapper;

import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:globalApplication.properties")
public class UserMapperServiceImpl implements UserMapperService {

    private UserMapper userMapper;

    @Autowired
    public UserMapperServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllAccountsByLevel(SelectLevel level) {
        switch (level) {
            case UserLevel:
            case AdminLevel:
            case SuperAdminLevel:
                return null;
            case MaintenanceLevel:
                return userMapper.findByAuthority();
            case All:
            default:
                return userMapper.findAll();
        }
    }

    @Override
    public ResponseEntity<?> updateUser(User user,String type) {
        try {
            switch (type) {
                case "password":
                    userMapper.updatePassword(user.getAccount(), user.getPassword());
                    break;
                case "authority":
                    userMapper.updateAuthority(user.getAccount(), user.getAuthority());
                    break;
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("update fail");
        }
        return ResponseEntity.ok("update success");
    }

    @Override
    public ResponseEntity<?> deleteAccount(User user) {
        try {
            userMapper.deleteAccount(user.getAccount());
            return ResponseEntity.ok("delete success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("delete fail");
        }
    }

    @Override
    public ResponseEntity<?> insertUser(User user) {
        try {
            userMapper.insert_user(user);
            return ResponseEntity.ok("insert success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("insert fail");
        }
    }

    @Override
    public ResponseEntity<?> queryUser(User user,String type) {
        try {
            switch (type){
                case "user":
                    return ResponseEntity.ok(userMapper.findByAccount(user.getAccount()));
                case "authority":
                    return ResponseEntity.ok(userMapper.findByAuthorityAndAccount(user.getAccount()));
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body("search success");
        }
        return ResponseEntity.badRequest().body("search fail");
    }

    @Override
    public ResponseEntity<?> sort(User user) {
        List<User> users;
        try {
            if (Objects.equals(user.getAccount(), "account")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = userMapper.sortAA();
                } else {
                    users = userMapper.sortAD();
                }
            } else if (Objects.equals(user.getAccount(), "password")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = userMapper.sortPA();
                } else {
                    users = userMapper.sortPD();
                }
            } else if (Objects.equals(user.getAccount(), "name")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = userMapper.sortNA();
                } else {
                    users = userMapper.sortND();
                }
            } else {
                return ResponseEntity.badRequest().body("sort fail");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("sort fail");
        }
    }

    @Override
    public ResponseEntity<?> sort_users(User user) {
        List<User> users;
        try {
            if (Objects.equals(user.getAccount(), "account")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = userMapper.sortUAA();
                } else {
                    users = userMapper.sortUAD();
                }
            } else if (Objects.equals(user.getAccount(), "password")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = userMapper.sortUPA();
                } else {
                    users = userMapper.sortUPD();
                }
            } else if (Objects.equals(user.getAccount(), "name")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = userMapper.sortUNA();
                } else {
                    users = userMapper.sortUND();
                }
            } else {
                return ResponseEntity.badRequest().body("sort fail");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("sort fail");
        }
    }
    public UserMapper getUserMapper() {
        return userMapper;
    }
}
