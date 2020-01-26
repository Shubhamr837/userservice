package co.hackerscode.userservice.controllers;

import co.hackerscode.userservice.dao.UserDaoImpl;
import co.hackerscode.userservice.models.User;
import co.hackerscode.userservice.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

@RestController
public class UserController {
@Autowired
   private UserDaoImpl userDao;

   @RequestMapping(value = "/adduser",method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
    public ResponseEntity addUser(@RequestBody String jsonString){

      JSONObject jsonObject = new JSONObject(jsonString);
      User user = new User();

      user.setEmailId(jsonObject.getString(CommonConstants.EMAIL_ID));
      user.setFirstname(jsonObject.getString(CommonConstants.FIRST_NAME));
      user.setLastname(jsonObject.getString(CommonConstants.LAST_NAME));
      user.setPassword(jsonObject.getString(CommonConstants.PASSWORD));
      if(userDao.save(user))
      {
         JSONObject jsonObjectResponse = new JSONObject();
         jsonObjectResponse.put("status",HttpStatus.OK);
         jsonObjectResponse.put("added-user",jsonObject);

         return ResponseEntity.ok().body(jsonObjectResponse.toString());

      }
      else {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }

   }
    @RequestMapping(value = "/updateuser",method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
    public ResponseEntity updateUser(@RequestBody String jsonString){

        JSONObject jsonObject = new JSONObject(jsonString);
        User user = new User();
        if((!jsonObject.has(CommonConstants.USER_ID))||jsonObject.getString(CommonConstants.FIRST_NAME)==null||jsonObject.getString(CommonConstants.LAST_NAME)==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user.setFirstname(jsonObject.getString(CommonConstants.FIRST_NAME));
        user.setLastname(jsonObject.getString(CommonConstants.LAST_NAME));
        user.setUserId(jsonObject.getInt(CommonConstants.USER_ID));
        if(userDao.update(user))
        {
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("status",HttpStatus.OK);
            jsonObjectResponse.put("updated-user",jsonObject);

            return ResponseEntity.ok().body(jsonObjectResponse.toString());

        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @RequestMapping(value = "/deleteuser",method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
    public ResponseEntity deleteUser(@RequestBody String jsonString){

        JSONObject jsonObject = new JSONObject(jsonString);
        if(jsonObject.getString(CommonConstants.USER_ID)==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(userDao.deleteById(jsonObject.getInt(CommonConstants.USER_ID)))
        {
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("status",HttpStatus.OK);

            jsonObjectResponse.put("delete-user-with-id",jsonObject.getInt(CommonConstants.USER_ID));

            return ResponseEntity.ok().body(jsonObjectResponse.toString());

        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @RequestMapping(value = "/getuser",method = RequestMethod.GET , consumes = "application/json", produces = "application/json")
    public ResponseEntity getUser(@RequestBody String jsonString){

        JSONObject jsonObject = new JSONObject(jsonString);
        User user = new User();
        if(jsonObject.getString(CommonConstants.EMAIL_ID)==null)
        {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        user = userDao.getByEmailId(jsonObject.getString(CommonConstants.EMAIL_ID));

        if(user!=null)
        {
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("status",HttpStatus.FOUND);
            jsonObjectResponse.put(CommonConstants.EMAIL_ID,user.getEmailId());
            jsonObjectResponse.put(CommonConstants.FIRST_NAME,user.getFirstname());
            jsonObjectResponse.put(CommonConstants.LAST_NAME,user.getLastname());
            jsonObjectResponse.put(CommonConstants.USER_ID,user.getUserId());
            jsonObjectResponse.put(CommonConstants.PASSWORD,user.getPassword());

            return ResponseEntity.ok().body(jsonObjectResponse.toString());

        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }
    @RequestMapping(value = "/updatepassword",method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
    public ResponseEntity updatePassword(@RequestBody String jsonString){

        JSONObject jsonObject = new JSONObject(jsonString);
        if((!jsonObject.has(CommonConstants.USER_ID))||(!jsonObject.has(CommonConstants.PASSWORD)))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        if(userDao.updatePassword(jsonObject.getInt(CommonConstants.USER_ID),jsonObject.getString(CommonConstants.PASSWORD)))
        {
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("status",HttpStatus.OK);

            return ResponseEntity.ok().body(jsonObjectResponse.toString());

        }
        else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

    }


}
