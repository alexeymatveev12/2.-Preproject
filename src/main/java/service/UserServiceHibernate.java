package service;


import dao.UserDaoJDBCimpl;
import dao.UserDaoHibernateImpl;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserServiceHibernate {
    private static UserServiceHibernate userServiceHibernate;

    private SessionFactory sessionFactory;

    private UserServiceHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserServiceHibernate getInstance() {
        if (userServiceHibernate == null) {
            userServiceHibernate = new UserServiceHibernate(DBHelper.getSessionFactory());
        }
        return userServiceHibernate;
    }

    public UserServiceHibernate() {
    }

//    public User getUserByName(String name) {
//        return new UserDaoHibernateImpl(sessionFactory.openSession()).getUserByName(name);
//    }

    public User getUserById(Long id) {
        User user = null;
        try {
             user = new UserDaoHibernateImpl(sessionFactory.openSession()).getUserByIdDao(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } return user;
    }

    public List<User> getAllUsers() {
        return new UserDaoHibernateImpl(sessionFactory.openSession()).
                getAllUsersDao();
    }

    public boolean checkUserByName(String name) {

        try {
            return new UserDaoHibernateImpl(sessionFactory.openSession()).checkUserByNameDao(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
}
    public boolean checkUserByLogin(String login) {

        try {
            return new UserDaoHibernateImpl(sessionFactory.openSession()).checkUserByLoginDao(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

  public void addUser(User user) {
      // Проверка, существует ли клиент в сервисе. Добавить если нет
      if (user.getName() != null && user.getName().length() > 0
              && user.getLogin() != null && user.getLogin().length() > 0
              && user.getPassword() != null && user.getPassword().length() > 0) {
          if (checkUserByName(user.getName()) && (checkUserByLogin(user.getLogin()))) {
              new UserDaoHibernateImpl(sessionFactory.openSession()).addUserDao(user);
          }
      }
  }

//    public boolean deleteUserById2(Long id) throws SQLException { // add deleteClient to BankClientDAO
//        if (new UserDaoHibernateImpl(sessionFactory.openSession()).getUserByIdDao(id) != null) {// Проверка, существует ли клиент в сервисе. Удалить если есть
//            new UserDaoHibernateImpl(sessionFactory.openSession()).deleteUserByIdDao(id);
//            return true;
//        }
//        return false;
//    }

    public void deleteUserById(Long id) /*throws SQLException*/ { // add deleteClient to BankClientDAO
        //     if (getUserDAO().getUserById(id) != null) {// Проверка, существует ли клиент в сервисе. Удалить если есть
        try {
            new UserDaoHibernateImpl(sessionFactory.openSession()).deleteUserByIdDao(id);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void updateUser(User user) {
        // Проверка, существует ли клиент в сервисе. Добавить если нет
        if (user.getName() != null && user.getName().length() > 0
                && user.getLogin() != null && user.getLogin().length() > 0
                && user.getPassword() != null && user.getPassword().length() > 0) {
       //     if (checkUserByName(user.getName())) {
       //         if (checkUserByLogin(user.getLogin())) {
                    new UserDaoHibernateImpl(sessionFactory.openSession()).updateUserDao(user);
       //         }
       //     }
        }
    }

    ////////////////////////////////////////////////////////////////////




    /*
        public void cleanUp() throws DBException {
            UserHibernateDAO dao = new UserHibernateDAO(sessionFactory.openSession());
            try {
                dao.dropTable();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
        public void createTable() throws DBException{
            UserHibernateDAO dao = new UserHibernateDAO(sessionFactory.openSession());
            try {
                dao.createTable();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
    */
    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example88?").          //db name
                    append("user=root&").          //login Alex
                    append("password=19181938");       //password 19181938

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserDaoJDBCimpl getUserDAO() {
        return new UserDaoJDBCimpl(getMysqlConnection());
    }
}
