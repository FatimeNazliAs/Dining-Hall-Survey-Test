/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.domain.common.ParamsVW;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.Params;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface ParamsDAO {
    List<Params> getParamsList();

    Params getParamsByParamsId(int paramsId);

    Params getParamsByParamName(String paramName);

    boolean addParams(Params params);

    boolean updateParams(Params params);

    boolean updateParams(ParamsVW paramsVW);

    boolean deleteParams(int paramsId);

    int getParamsCount(Map<String, Object> filters);

    List<ParamsVW> getParamsVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);
}
