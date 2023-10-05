/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.user;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Tolga
 */
@ASqlTable(TableName = "local_user")
public class LocalUser implements Serializable, ISqlTable {


    @AColumn(name = "local_user_id", primary = true)
    private long localUserId;
    @AColumn(name = "localUserRole")
    private LocalUserRole localUserRole;
    @AColumn(name = "first_name")
    private String firstName;
    @AColumn(name = "last_name")
    private String lastName;
    @AColumn(name = "gender_id")
    private int genderId;
    @AColumn(name = "username")
    private String username;
    @AColumn(name = "password")
    private String password;
    @AColumn(name = "email")
    private String email;
    @AColumn(name = "phone_Number")
    private String phoneNumber;
    @AColumn(name = "registered_Date")
    private Date registeredDate;
    @AColumn(name = "last_Login_Date")
    private Date lastLoginDate;
    @AColumn(name = "last_Update")
    private Date lastUpdate;
    @AColumn(name = "enabled")
    private String enabled;
    @AColumn(name = "status_Message")
    private String statusMessage;

    private List<RoleVW> roleList;
    @AColumn(name = "repeat_Password")
    private String repeatPassword;
    @AColumn(name = "old_Password")
    private String oldPassword;
    @AColumn(name = "image_Name")
    private String imageName;
    @AColumn(name = "image_Path")
    private String imagePath;
    @AColumn(name = "access_Token")
    private String accessToken;


    /**
     * @return the localUserId
     */
    public long getLocalUserId() {
        return localUserId;
    }

    /**
     * @param localUserId the localUserId to set
     */
    public void setLocalUserId(long localUserId) {
        this.localUserId = localUserId;
    }

    /**
     * @return the localUserRole
     */
    public LocalUserRole getLocalUserRole() {
        return localUserRole;
    }

    /**
     * @param localUserRole the localUserRole to set
     */
    public void setLocalUserRole(LocalUserRole localUserRole) {
        this.localUserRole = localUserRole;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the genderId
     */
    public int getGenderId() {
        return genderId;
    }

    /**
     * @param genderId the genderId to set
     */
    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the registeredDate
     */
    public Date getRegisteredDate() {
        return registeredDate;
    }


    /**
     * @param registeredDate the registeredDate to set
     */
    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    /**
     * @return the lastLoginDate
     */
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * @param lastLoginDate the lastLoginDate to set
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * @return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @param statusMessage the statusMessage to set
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the enabled
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the roleList
     */
    public List<RoleVW> getRoleList() {
        return roleList;
    }

    /**
     * @param roleList the roleList to set
     */
    public void setRoleList(List<RoleVW> roleList) {
        this.roleList = roleList;
    }

    /**
     * @return the repeatPassword
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * @param repeatPassword the repeatPassword to set
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}