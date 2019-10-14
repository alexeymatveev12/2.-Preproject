package service;



import dao.UserDaoJDBCimpl;
import exception.DBException;
import model.User;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    public UserService() {
    }

    public User getUserByName(String name) { // Почему нет исключения?????
        return getUserDAO().getUserByName(name);
    }

    public User getUserById(Long id) {
        return getUserDAO().getUserByIdDao(id);
    }

    public List<User> getAllUsers() {
        return getUserDAO().getAllUsersDao();
    }

    public boolean addUser(User user) {
        if (getUserByName(user.getName()) == null) {// Проверка, существует ли клиент в сервисе. Добавить если нет
            getUserDAO().addUserDao(user);
            return true;
        }
        return false;
    }

    public void addUser2(User user) {
        //      if (getUserByName(user.getName()) == null) {// Проверка, существует ли клиент в сервисе. Добавить если нет
        if (user.getName() != null && user.getLogin() != null && user.getPassword() != null) {
            getUserDAO().addUserDao(user);
        }

        //          return true;
        //       }
 //       return false;
    }

    public boolean deleteUserById2(Long id) { // add deleteClient to BankClientDAO
        if (getUserDAO().getUserByIdDao(id) != null) {// Проверка, существует ли клиент в сервисе. Удалить если есть
            getUserDAO().deleteUserByIdDao(id);
            return true;
        }
        return false;
    }

    public void deleteUserById(Long id) { // add deleteClient to BankClientDAO
   //     if (getUserDAO().getUserById(id) != null) {// Проверка, существует ли клиент в сервисе. Удалить если есть
            getUserDAO().deleteUserByIdDao(id);
  //          return true;
 //       }
 //       return false;
    }

    public void updateUser(User user) {
        if (user.getName() != null && user.getLogin() != null && user.getPassword() != null) {
            getUserDAO().updateUserDao(user);
        }
    }

    public void cleanUp() throws DBException {
        UserDaoJDBCimpl dao = getUserDAO();
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
    public void createTable() throws DBException{
        UserDaoJDBCimpl dao = getUserDAO();
        try {
            dao.createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

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
