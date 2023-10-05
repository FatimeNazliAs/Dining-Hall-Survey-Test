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
package dininghall.service.common;


import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.dao.common.ParamsDAO;
import dininghall.dao.common.ParamsDAOImpl;
import dininghall.domain.common.Params;
import dininghall.domain.common.ParamsVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "paramsService")
@ApplicationScoped
public class ParamsService {
    private final static ParamsDAO paramsDAO;

    static {
        paramsDAO = new ParamsDAOImpl();
    }

    public List<Params> getParamsList(int size) {
        List<Params> list = paramsDAO.getParamsList();

        return list;
    }


    public boolean updateParams(Params params) {
        return paramsDAO.updateParams(params);
    }

    public boolean updateParams(ParamsVW paramsVW) {
        return paramsDAO.updateParams(paramsVW);
    }

    public boolean deleteParams(Params params) {
        return paramsDAO.deleteParams(params.getParamId());
    }

    public Params getParamsByParamsId(int paramsId) {
        return paramsDAO.getParamsByParamsId(paramsId);
    }

    public Params getParamsByParamName(String paramName) {
        return paramsDAO.getParamsByParamName(paramName);
    }

    public boolean insertParams(Params params) {
        return paramsDAO.addParams(params);
    }

    public int getParamsCount(Map<String, Object> filters) {
        return paramsDAO.getParamsCount(filters);
    }

    public List<ParamsVW> getParamsVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        return paramsDAO.getParamsVWList(first, pageSize, sortFilters, filters);
    }

}
