package servlet;


import model.User;
import service.UserService;
import service.UserServiceHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

//    private UserServiceHibernate userServiceHibernate  = new UserServiceHibernate();
    UserServiceHibernate userServiceHibernate = UserServiceHibernate.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long reqID = Long.parseLong(req.getParameter("id"));

        User userToUpdate = userServiceHibernate.getUserById(reqID);
        req.setAttribute("userToUpdate", userToUpdate);
        req.getServletContext().getRequestDispatcher("/jsp/update.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // form data
        Long reqId = Long.parseLong(req.getParameter("id"));
        String nameUser = req.getParameter("name");
        String loginUser = req.getParameter("login");
        String passwordUser = req.getParameter("password");

        // new client
        // check the user!!!!!!
        User updatedUser = new User(reqId, nameUser, loginUser, passwordUser);
        //  if ( nameUser != null && loginUser!= null &&  passwordUser !=null)
        userServiceHibernate.updateUser(updatedUser);
        resp.sendRedirect("read");;

    }



    }

