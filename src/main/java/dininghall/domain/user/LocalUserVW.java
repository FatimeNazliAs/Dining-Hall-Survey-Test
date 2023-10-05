/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Tolga
 */
public class LocalUserVW implements Serializable {

    private long localUserId;
    private LocalUserRole localUserRole;
    private String firstName;
    private String lastName;
    private int genderId;
    private String gender;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Date registeredDate;
    private Date lastLoginDate;
    private Date lastUpdate;
    private String enabled;
    private String statusMessage;
    private List<RoleVW> roleList;
    private String repeatPassword;
    private String imageName;
    private String imagePath;
    private String registeredDateStr;
    private String lastLoginDateStr;
    private String lastUpdateStr;
    private String oldPassword;
    private String role;
    private String accessToken;
    private int localRoleId;
    private String newPassword;


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

    /**
     * @return the registeredDateStr
     */
    public String getRegisteredDateStr() {
        return registeredDateStr;
    }

    /**
     * @param registeredDateStr the registeredDateStr to set
     */
    public void setRegisteredDateStr(String registeredDateStr) {
        this.registeredDateStr = registeredDateStr;
    }

    /**
     * @return the lastLoginDateStr
     */
    public String getLastLoginDateStr() {
        return lastLoginDateStr;
    }

    /**
     * @param lastLoginDateStr the lastLoginDateStr to set
     */
    public void setLastLoginDateStr(String lastLoginDateStr) {
        this.lastLoginDateStr = lastLoginDateStr;
    }

    /**
     * @return the lastUpdateStr
     */
    public String getLastUpdateStr() {
        return lastUpdateStr;
    }

    /**
     * @param lastUpdateStr the lastUpdateStr to set
     */
    public void setLastUpdateStr(String lastUpdateStr) {
        this.lastUpdateStr = lastUpdateStr;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getLocalUserId() {
        return localUserId;
    }

    public void setLocalUserId(long localUserId) {
        this.localUserId = localUserId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setLocalRoleId(int localRoleId) {
        this.localRoleId = localRoleId;
    }

    public int getLocalRoleId() {
        return localRoleId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}