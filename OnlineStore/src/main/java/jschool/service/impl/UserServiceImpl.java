package jschool.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import jschool.dao.AddressDAO;
import jschool.dao.RoleDAO;
import jschool.dao.UserDAO;
import jschool.dto.UserDTO;
import jschool.model.Address;
import jschool.model.Role;
import jschool.model.User;
import jschool.service.DTOConverterService;
import jschool.service.UserService;
import jschool.validator.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    private final BCryptPasswordEncoder passwordEncoder;
    private DTOConverterService dtoConverterService;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private AddressDAO addressDAO;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, DTOConverterService dtoConverterService, RoleDAO roleDAO, UserDAO userDAO, AddressDAO addressDAO) {
        this.passwordEncoder = passwordEncoder;
        this.dtoConverterService = dtoConverterService;
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
    }


    /**
     * Gets current user
     * @return
     */
    @Override
    @Transactional
    public User getCurUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User curU = userDAO.findByEmail(auth.getName());
        return curU;
    }

    /**
     * Get User entity by email
     * @param email user email
     * @return entity
     */
    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return this.userDAO.findByEmail(email);
    }


    /**
     * Gets dto of current user
     * @return
     */
    @Override
    @Transactional
    public UserDTO getCurUserDTO() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User curU = userDAO.findByEmail(auth.getName());
        return dtoConverterService.getUserDTO(curU);
    }

    /**
     * gets address from json, adds new to user or edits old.
     * @param json {"user_id" : , "address_id" : }
     * @return Message object info
     * */
    @Override
    @Transactional
    public Message addAddress(String json) {
        logger.info("add address to User");
        Message m = new Message();
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
            m.getErrors().add("wrong data");
            return m;
        }
        int userId = jsonNode.get("user_id").asInt();
        int addrID = jsonNode.get("address_id").asInt();
        String addr = jsonNode.get("address_str").asText();

        addAddressById(userId, addrID);

        User u = getUserById(userId);
        if (!Objects.isNull(u)) {
            Address a = new Address();
            if (addrID > 0) {
                a = this.addressDAO.getById(addrID);
            }
            if (Objects.isNull(a) || addrID <= 0) {
                a = new Address();
                a.setAddress(addr);
                this.addressDAO.add(a);
            }
            addAddress(u, a);
        }
        m.getConfirms().add("address was added successfully");
        return m;
    }

    /**
     * Sets active address to user
     * @param json {"user_id" : , "address_id" : }
     * @return
     */
    @Override
    @Transactional
    public Message setActiveAddress(String json) {
        Message m = new Message();
        logger.info("set active address");
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
            m.getErrors().add("inconsistent json data");
            return m;
        }
        setActiveAddressToUser(jsonNode.get("address_id").asInt(), jsonNode.get("user_id").asInt());
        m.getConfirms().add("active address set successfully");
        return m;
    }

    /**
     * Removes address
     * @param json {"user_id" : , "address_id" : }
     * @return
     */
    @Override
    @Transactional
    public Message removeAddress(String json) {
        Message m = new Message();
        logger.info("remove address");
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
            m.getErrors().add("inconsistent json data");
            return m;
        }
        removeAddressById(jsonNode.get("address_id").asInt(), jsonNode.get("user_id").asInt());
        m.getConfirms().add("address removed successfully");
        return m;
    }

    /**
     * Updates address info for user
     * @param json {"user_id" : , "address_id" : }
     * @return
     */
    @Override
    @Transactional
    public Message editAddress(String json) {
        Message m  = new Message();
        logger.info("edit address");
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
            m.getErrors().add("inconsistent json data");
            return m;
        }
        this.addressDAO.update(jsonNode.get("address_id").asInt(),jsonNode.get("address_str").asText());
        m.getConfirms().add("address edited successfully");
        return m;
    }

    /**
     * Gets password from json, checks old one and sets new.
     * */
    @Override
    @Transactional
    public Message changeUserPassword(String json) {
        Message m = new Message();
        logger.info("change password");
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
            m.getErrors().add("inconsistent json data");
            return m;
        }
        String oldPassword = "";
        String password = "";
        password = jsonNode.get("password").asText();
        oldPassword = jsonNode.get("oldPassword").asText();

        User user = getCurUser();

        if (!checkIfValidOldPassword(user, oldPassword)) {
            m.getErrors().add("Invalid Old password");
            return m;
        }
        changeUserPassword(user, password);
        m.getConfirms().add("Password has changed");
        return m;

    }


    /**
     * Get list of all User rntities from db
     * @return list
     */
    @Override
    @Transactional
    public List<User> listUsers() {
        return this.userDAO.list();
    }

    /**
     * Get converted to dto list of entities
     * @return list
     */
    @Override
    @Transactional
    public List<UserDTO> listUsersDTO(){
        return this.dtoConverterService.getUserDTOList(this.listUsers());
    }

    /**
     * Get User entity by id
     * @param id user id
     * @return entity
     */
    @Override
    @Transactional
    public User getUserById(int id) {
        return this.userDAO.findById(id);
    }


    /**
     * Check if User by id is admin
     * @param id id of User
     * @return if admin
     */
    @Override
    @Transactional
    public boolean isUserAdmin(int id) {
        return this.userDAO.isUserAdmin(id);
    }

    /**
     * Adds user entity to db
     * @param p User data to add
     */
    @Override
    @Transactional
    public void addUser(User p) {
        this.userDAO.add(p);
    }


    /**
     * Updates User password in db
     * @param user
     * @param password
     */
    @Override
    @Transactional
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDAO.updatePassword(user);
    }

    /**
     * checks user entered correct old password
     * @param user
     * @param oldPassword
     * @return if oldpassword correct
     * */
    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    /**
     * Adds User to db
     * @param dto dto with user data to add
     * @return Message info object
     */
    @Override
    @Transactional
    public Message addUser(UserDTO dto) {
        Message message = new Message();
        logger.info("add user");

        if (emailExists(dto.getEmail())) {
            message.getErrors().add("Email is already registered.");
            return message;
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirth(dto.getBirth());
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleDAO.getByRole("USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        this.userDAO.add(user);
        message.getConfirms().add("You've successfully registered.Check email");
        return message;
    }

    //checks if email is already registered
    private boolean emailExists(String email) {
        logger.info("check email existence");
        User user = userDAO.findByEmail(email);
        return !Objects.isNull(user);
    }

    /**
     * Updates User in db
     * @param p User data to update
     */
    @Override
    @Transactional
    public void updateUser(User p) {
        this.userDAO.update(p);
    }

    /**updated users personal info. for password, address and admin role ttere are another methods
     * @param p new user dto info
     * */
    @Override
    @Transactional
    public Message updateUser(UserDTO p){
        Message m = new Message();
        try {
            logger.info("in user update from");
            logger.info(p.getBirth());
            User u = this.getUserById(p.getId());
            u.setBirth(p.getBirth());
            u.setFullName(p.getFullName());
            u.setBirth(p.getBirth());
            u.setPhone(p.getPhone());
            logger.info(u.getBirth());
            updateUser(u);
            logger.info(u.getBirth());
            m.getConfirms().add("Successful user update");
        }catch (Exception e){
            logger.error(e.toString());
            m.getErrors().add("Error while user update.Try again");
        }
        return m;
    }

    /**
     * Removes User from db
     * @param id id of User to remove
     */
    @Override
    @Transactional
    public void removeUser(int id) {
        this.userDAO.remove(id);
    }

    /**
     * Sets new active address for user
     * @param idAddr id of address to set
     * @param idUser id of user
     */
    @Override
    @Transactional
    public void setActiveAddressToUser(int idAddr, int idUser) {
            this.userDAO.setActiveAddressToUser(idAddr, idUser);
    }

    /**
     * Adds address to User
     * @param idAddr id of address to add
     * @param idUser id of User
     */
    @Override
    @Transactional
    public void addAddressById(int idAddr, int idUser) {
            this.userDAO.addAddressById(idAddr, idUser);
    }

    /**
     * Removes address from User by id
     * @param idAddr id of address
     * @param idUser id of user
     */
    @Override
    @Transactional
    public void removeAddressById(int idAddr, int idUser) {
            this.userDAO.removeAddressById(idAddr, idUser);
    }


    /**
     * Adds address to User
     * @param p user
     * @param a address
     */
    @Override
    @Transactional
    public void addAddress(User p, Address a) {
        this.userDAO.addAddress(p, a);
    }


    /**
     * Changes admin status and returns message object with error or success array for displaying in front.
     * @param m Message json object
     * @param json {"id" :, "admin" : true/false}
     * */
    @Override
    @Transactional
    public Message changeAdminStatus(Message m, String json){
        try {
            JsonNode jsonNode = UtilService.parseJsonInput(json);
            if (Objects.isNull(jsonNode)) {
                m.getErrors().add("wrong data");
                return m;
            }
            int id = jsonNode.get("id").asInt();
            boolean admin = jsonNode.get("admin").asBoolean();

            User u = this.getUserById(id);
            u.setAdmin(admin);
            this.userDAO.updateAdminStatus(u);
            m.getConfirms().add("User " + u.getFullName() + " admin status changed");
        }catch (Exception e){
            logger.error(e.toString());
            m.getErrors().add("Failed to change status.Try again");
        }
        return m;
    };

}
