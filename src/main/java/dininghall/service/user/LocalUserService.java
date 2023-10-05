/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dininghall.service.user;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.dao.user.LocalUserDAO;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.user.ForgetPasswordToken;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;


@ManagedBean(name = "localUserService")
@ApplicationScoped
public class LocalUserService {

    private final static LocalUserDAO localUserDAO;

    static {

        localUserDAO = new LocalUserDAOImpl();

    }

    public List<LocalUser> getLocalUsers(int size) {
        List<LocalUser> list = localUserDAO.getLocalUserList();

        return list;
    }

    public List<LocalUserVW> getLocalUserVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        return localUserDAO.getLocalUserVWList(first, pageSize, sortFilters, filters);
    }


    public boolean updateLocalUser(LocalUser localUser) {
        return localUserDAO.updateLocalUser(localUser);
    }

    public boolean updateLocalUser(LocalUserVW localUserVW) {
        return localUserDAO.updateLocalUser(localUserVW);
    }

    public boolean deleteLocalUser(LocalUser localUser) {
        return localUserDAO.deleteLocalUser(localUser.getLocalUserId());
    }

    public LocalUser getLocalUserByLocalUserId(long localUserId) {
        return localUserDAO.getLocalUserByLocalUserId(localUserId);
    }

    public boolean insertLocalUser(LocalUser localUser) {
        return localUserDAO.addLocalUser(localUser);
    }

    public boolean insertLocalUser(LocalUserVW localUserVW) {
        return localUserDAO.addLocalUser(localUserVW);
    }

    public int getLocalUserCount(Map<String, FilterMeta> filters) {
        return localUserDAO.getLocalUserCount(filters);
    }

    public boolean checkLocalUserEmail(String email) {
        return localUserDAO.checkLocalUserEmail(email);
    }

    public boolean updateKeasUser(LocalUser asstnaviUser) {
        return localUserDAO.updateKeasUser(asstnaviUser);
    }

    public boolean checkKeasUserPassword(LocalUser asstnaviUser) {
        return localUserDAO.checkKeasUserPassword(asstnaviUser);
    }


    public boolean updateLocalUserPassword(long localUserId, String password) {
        return localUserDAO.updateLocalUserPassword(localUserId, password);
    }

    public boolean updateKeasUserPassword(LocalUser asstnaviUser) {
        return localUserDAO.updateKeasUserPassword(asstnaviUser);
    }

    public LocalUser getLocalUserByAccessToken(String accessToken) {
        return localUserDAO.getLocalUserByAccessToken(accessToken);
    }

    public LocalUser getLocalUserByEmail(String email) {
        return localUserDAO.getLocalUserByEmail(email);
    }

    public boolean insertForgetPasswordToken(ForgetPasswordToken forgetPasswordToken) {
        return localUserDAO.insertForgetPasswordToken(forgetPasswordToken);
    }

    public ForgetPasswordToken getForgetPasswordToken(String token) {
        return localUserDAO.getForgetPasswordToken(token);
    }

    public boolean updateForgetPasswordToken(ForgetPasswordToken forgetPasswordToken) {
        return localUserDAO.updateForgetPasswordToken(forgetPasswordToken);
    }
}
