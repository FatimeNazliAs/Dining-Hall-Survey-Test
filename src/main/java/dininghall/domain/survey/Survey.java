package dininghall.domain.survey;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;
import java.util.Date;

@ASqlTable(TableName = "survey")
@Data
public class Survey implements Serializable, ISqlTable {
  @AColumn(name = "survey_id",primary = true)
    private long surveyId;
    @AColumn(name = "created_date")
    private Date createdDate;
    @AColumn(name = "survey_date")
    private Date surveyDate;
    @AColumn(name = "user_type")
    private long userType;
    @AColumn(name = "user_id")
    private long userId;
    @AColumn(name = "domp_id")
    private long dompId;

}
