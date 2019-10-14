package servlet;




import model.User;
import org.hibernate.SessionFactory;
import service.UserService;
import service.UserServiceHibernate;
import util.DBHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {

 //   private UserServiceHibernate userServiceHibernate  = new UserServiceHibernate().get;
//    SessionFactory sessionFactory = DBHelper.getSessionFactory();
    UserServiceHibernate userServiceHibernate = UserServiceHibernate.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext()
                .getRequestDispatcher("/jsp/create.jsp")
                .forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // form data
        String nameUser = req.getParameter("name");
        String loginUser = req.getParameter("login");
        String passwordUser = req.getParameter("password");

        // new client
        User newUser = new User(nameUser, loginUser, passwordUser);

        // check the user!!!!!!
      //  if (
                userServiceHibernate.addUser(newUser);
    //    {
            resp.sendRedirect("read");;
     //   } else {
   //         resp.getWriter().println("Client not add");
   //     }

 //       resp.setStatus(HttpServletResponse.SC_OK);

    }


}
