package mybatis;

import org.apache.ibatis.annotations.Param;
public interface UserMapper {
    void createTable (@Param("tableName") String tableName);
    void add(User user);
    void del(int id);
    void update(User user);
    User getUser(int id);
    User[] list();
}