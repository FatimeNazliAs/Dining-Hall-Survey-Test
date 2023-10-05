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
package dininghall.service.info;

import dininghall.dao.info.StateDAO;
import dininghall.dao.info.StateDAOImpl;
import dininghall.domain.info.State;
import dininghall.domain.info.StateVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "stateService")
@ApplicationScoped
public class StateService {

    private final static StateDAO stateDAO;

    static {
        stateDAO = new StateDAOImpl();
    }

    public List<State> getStateList(int size) {
        List<State> list = stateDAO.getStateList();

        return list;
    }


    public boolean updateState(State state) {
        return stateDAO.updateState(state);
    }

    public boolean deleteState(State state) {
        return stateDAO.deleteState(state.getStateId());
    }

    public State getStateByStateId(int stateId) {
        return stateDAO.getStateByStateId(stateId);
    }

    public boolean insertState(State state) {
        return stateDAO.addState(state);
    }

    public List<State> getStateListByCityId(int cityId) {
        return stateDAO.getStateListByCityId(cityId);
    }

    public List<StateVW> getStateVWList(int i) {
        return stateDAO.getStateVWList();
    }

    public boolean updateState(StateVW stateVW) {
        return stateDAO.updateState(stateVW);
    }

    public StateVW getStateVWByStateId(int stateId) {
        return stateDAO.getStateVWByStateId(stateId);
    }
}
