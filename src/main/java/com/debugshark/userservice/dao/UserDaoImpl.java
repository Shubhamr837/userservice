package com.debugshark.userservice.dao;

import com.debugshark.userservice.models.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static com.debugshark.userservice.dao.UserDaoConstants.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean save(User user) {

        String query = "insert into Users ( firstname, lastname , emailid , password ) values (?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmailId());
            ps.setString(4, user.getPassword());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("User saved with emailId" + user.getEmailId());
            } else {
                System.out.println("User save failed with emailid=" + user.getEmailId());
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public User getByEmailId(String EmailId) {
        String query = "select id,firstname,lastname,password from Users where emailid = ?";
        User user = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, EmailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmailId(EmailId);
                user.setPassword(rs.getString("password"));
                System.out.println("User Found::" + user);
            } else {
                System.out.println("No User found with emailId=" + EmailId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public boolean update(User user) {

        String query = "update Users set firstname=?, lastname=? where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setInt(3, user.getUserId());
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("User updated with emailid=" + user.getEmailId());
            } else System.out.println("No User found with emailid=" + user.getEmailId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        String query = "delete from Users where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("user deleted with id=" + id);
            } else {
                System.out.println("No user found with id=" + id);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public List<User> getAll() {
        String query = "select id, firstname, lastname , emailid from Users";
        List<User> userArrayList = new ArrayList<User>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmailId(rs.getString("emailid"));
                userArrayList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userArrayList;
    }

    @Override
    public boolean updatePassword(int id, String password) {
        String query = "update Users set password=? where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, password);
            ps.setInt(2, id);
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("User updated with id=" + id);
                result = true;
            } else {
                System.out.println("No User found with id=" + id);
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean addSolved(int id, int questionId) {
        String query = "insert into Solved (" + COLUMN_ID + "," + COLUMN_QUESTION_ID + " ) values (?,?);";
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, questionId);
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("Record updated with id=" + id);
                result = true;
            } else {
                System.out.println("failed to insert with user id=" + id + "and question id" + questionId);
                result = false;
            }
        }catch (SQLIntegrityConstraintViolationException e)
        {
            System.out.println("record may already exists");
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public JSONObject getSolved(int id,int page) {
        String query = "select " + COLUMN_QUESTION_ID + " from Solved where " + COLUMN_ID + "= ? limit ? offset ?;";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONObject jsonObject = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2,10);
            ps.setInt(3,page*10);
            rs = ps.executeQuery();
            if (rs.next()) {
                List<Integer> list = new ArrayList<Integer>();
                do {
                 list.add(rs.getInt(COLUMN_QUESTION_ID));
                } while (rs.next());
                jsonObject = new JSONObject();
                jsonObject.put(Integer.toString(id),list);
            } else {
                System.out.println("No Solved found with UserId=" + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}

