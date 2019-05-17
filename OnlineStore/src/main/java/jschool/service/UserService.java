package jschool.service;

import jschool.dto.UserDTO;
import jschool.model.Address;
import jschool.model.User;
import jschool.validator.Message;

import java.util.List;

/**
 * User service interface
 */
public interface UserService {
     /**
      * Get list of all User rntities from db
      * @return list
      */
     List<User> listUsers();

     /**
      * Get converted to dto list of entities
      * @return list
      */
     List<UserDTO> listUsersDTO();

     /**
      * Get User entity by id
      * @param id user id
      * @return entity
      */
     User getUserById(int id);

     /**
      * Get User entity by email
      * @param email user email
      * @return entity
      */
     User getUserByEmail(String email);

     /**
      * Check if User by id is admin
      * @param id id of User
      * @return if admin
      */
     boolean isUserAdmin(int id);

     /**
      * Adds user entity to db
      * @param p User data to add
      */
     void addUser(User p);

     /**
      * Updates User password in db
      * @param user
      * @param password
      */
     void changeUserPassword(User user, String password);

     /**
      * Checks if User old password is ok
      * @param user
      * @param oldPassword
      * @return if oldpassword correct
      */
     boolean checkIfValidOldPassword(User user, String oldPassword);

     /**
      * Adds User to db
      * @param p dto with user data to add
      * @return Message info object
      */
     Message addUser(UserDTO p);

     /**
      * Updates User in db
      * @param p User data to update
      */
     void updateUser(User p);

     /**
      * Updates User in db
      * @param p User data to update
      */
     Message updateUser(UserDTO p);

     /**
      * Removes User from db
      * @param id id of User to remove
      */
     void removeUser(int id);

     /**
      * Sets new active address for user
      * @param idAddr id of address to set
      * @param idUser id of user
      */
     void setActiveAddressToUser(int idAddr, int idUser);

     /**
      * Adds address to User
      * @param idAddr id of address to add
      * @param idUser id of User
      */
     void addAddressById(int idAddr, int idUser );

     /**
      * Removes address from User by id
      * @param idAddr id of address
      * @param idUser id of user
      */
     void removeAddressById(int idAddr, int idUser);

     /**
      * Adds address to User
      * @param p user
      * @param a address
      */
     void addAddress(User p, Address a);

     /**
      * Gets current user
      * @return
      */
     User getCurUser();

     /**
      * Gets dto of current user
      * @return
      */
     UserDTO getCurUserDTO();

     /**
      * Adds address to current user
      * @param json
      * @return
      */
     Message addAddress(String json);

     /**
      * Sets active address to user
      * @param json
      * @return
      */
     Message setActiveAddress(String json);

     /**
      * Removes address
      * @param json
      * @return
      */
     Message removeAddress(String json);

     /**
      * Updates address info for user
      * @param json input data to update
      * @return message info object
      */
     Message editAddress(String json);

     /**
      * Changes password for User
      * @param json input data
      * @return Message object info
      */
     Message changeUserPassword(String json);


     /**
      * Change admin status  message of success
      * @param m message info object
      * @param json input with user data to update
      * @return message info object
      */
     Message changeAdminStatus(Message m, String json);
}
