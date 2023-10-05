/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.State;
import dininghall.domain.info.StateVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface StateDAO {
    public List<State> getStateList();

    public List<StateVW> getStateVWList();

    public State getStateByStateId(int stateId);

    public StateVW getStateVWByStateId(int stateId);

    public State getStateByStateCode(String stateCode);

    public StateVW getStateVWByStateCode(String stateCode);

    public State getStateByEnglishName(String englishName);

    public StateVW getStateVWByEnglishName(String englishName);

    public State getStateByNativeName(String nativeName);

    public StateVW getStateVWByNativeName(String nativeName);

    public boolean addState(State state);

    public boolean updateState(State state);

    public boolean updateState(StateVW stateVW);

    public boolean deleteState(int stateId);

    public List<State> getStateListByCityId(int cityId);
}
