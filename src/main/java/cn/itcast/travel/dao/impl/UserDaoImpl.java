package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.DruidUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    JdbcTemplate template = new JdbcTemplate(DruidUtils.getDataSource());

    /**
     * 查询用户名
     *
     * @param _user
     * @return
     */
    @Override
    public User findUsername(User _user) {
        User user = null;
        String username = _user.getUsername();
        String sql = "select * from tab_user where username = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            //如果没查到数据，说明不存在该用户名，可以注册
            return user;
        }
        return user;
    }

    /**
     * 存储用户信息
     *
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        Object[] pramaras = new Object[]{
                user.getUsername(),user.getPassword(),user.getName(),
                user.getBirthday(),user.getSex(),user.getTelephone(),
                user.getEmail(),user.getStatus(),user.getCode()
        };
        template.update(sql, pramaras);
        return true;
    }

    /**
     * 根据激活码查询用户对象
     * @return
     * @param code
     */
    @Override
    public User findUserByCode(String code) {
        User user =null;
        String sql = "select * from tab_user where code = ?";
        try{
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }catch (EmptyResultDataAccessException e){
            return user;
        }
        return user;
    }

    /**
     * 用户账号激活
     * @param user
     * @return
     */
    @Override
    public boolean userActive(User user) {
        String sql = "update tab_user set status = 'Y' where code = ?";
        int update = template.update(sql, user.getCode());
        if (update == 1) return true;
        return false;
    }

    /**
     * 验证是否存在改用户名和密码为实参的user数据
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        String sql = "select * from tab_user where username = ? and password = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (EmptyResultDataAccessException e){
            return user;
        }
        return user;
    }
}
