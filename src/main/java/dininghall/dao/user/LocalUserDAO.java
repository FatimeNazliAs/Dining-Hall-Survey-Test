/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.user;


import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.domain.user.ForgetPasswordToken;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserVW;
import dininghall.domain.user.RoleVW;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface LocalUserDAO {

    List<LocalUser> getLocalUserList();

    List<LocalUserVW> getLocalUserVWList(int start, int end, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);

    LocalUser loginLocalUser(String email, String password);

    LocalUser getLocalUserByUsername(String username);

    LocalUser getLocalUserByEmail(String email);

    LocalUser getLocalUserByAccessToken(String code);

    LocalUser getLocalUserByLocalUserId(long localUserId);

    LocalUserVW getLocalUserVWById(long localUserId);

    boolean addLocalUser(LocalUser user);

    boolean addLocalUser(LocalUserVW localUserVW);

    boolean updateLocalUser(LocalUser user);

    boolean updateLocalUser(LocalUserVW localUserVW);

    boolean updateLocalUserWithPassword(LocalUserVW userVW);

    boolean updateLocalUserInfo(LocalUser user);

    boolean updateLocalUserVWInfo(LocalUserVW userVW);

    boolean updateLocalUserLoginDate(long localUserId);

    boolean updateLocalUserAccessToken(String email, String accessToken);

    boolean updateLocalUserPassword(long localUserId, String userPassword);

    boolean updateLocalUserProfileImage(long localUserId, String imageName, String imagePath);

    boolean deleteLocalUser(long localUserId);

    boolean checkLocalUsername(String username);

    boolean checkLocalUserEmail(String email);

    boolean checkOldPassword(long localUserId, String password);

    boolean checkLocalUserEnabled(long localUserId);

    List<RoleVW> getRolePermissionVWList(long localUserId);

    String getLocalUserPassword(long localUserId);

    /* COUNT FUNCTION*/
    int getLocalUserCount(Map<String, FilterMeta> filters);


    boolean updateKeasUser(LocalUser asstnaviUser);

    boolean checkKeasUserPassword(LocalUser asstnaviUser);

    boolean updateKeasUserPassword(LocalUser asstnaviUser);

    boolean insertForgetPasswordToken(ForgetPasswordToken forgetPasswordToken);

    ForgetPasswordToken getForgetPasswordToken(String token);

    boolean updateForgetPasswordToken(ForgetPasswordToken forgetPasswordToken);


}
