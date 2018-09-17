package mybatis;

import org.apache.ibatis.session.SqlSession;

public class MyTest {
    public static void main(String[] args) throws Exception {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        userMapper.createTable("t_user");
        User user = new User(1, "csp" , 20);
        userMapper.add(user);
        User[] users = userMapper.list();
        for (User user1 : users) {
            System.out.println(user1);
        }
        sqlSession.commit();
        sqlSession.close();
    }
}