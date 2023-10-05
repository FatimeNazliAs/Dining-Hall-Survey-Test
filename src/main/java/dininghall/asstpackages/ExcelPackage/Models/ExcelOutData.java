package dininghall.asstpackages.ExcelPackage.Models;

import dininghall.domain.survey.models.testModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ExcelOutData implements Serializable
{
    List<ExcelReadOut> errors;
    List<ExcelReadOut> update;
    List<ExcelReadOut> add;
    List<  Map<String, testModel>> dataList;
    // TODO: 3/1/2023 alt ve üst bura classa çevirilecek ve her eklenen verinin sütün , satır ve sheet bilgisi alınacak --yada model bilgisi
    Map<String,Integer> columnMap;

    public List<ExcelReadOut> getErrors() {
        return errors;
    }

    public void setErrors(List<ExcelReadOut> errors) {
        this.errors = errors;
    }


    public List<ExcelReadOut> getUpdate() {
        return update;
    }

    public void setUpdate(List<ExcelReadOut> update) {
        this.update = update;
    }



    public List<ExcelReadOut> getAdd() {
        return add;
    }

    public void setAdd(List<ExcelReadOut> add) {
        this.add = add;
    }
}
