package dininghall.domain.survey;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;
import java.util.Date;
@Data
@ASqlTable(TableName = "day_of_menu_parent")
public class DayOfMenuParent implements Serializable, ISqlTable
{
    @AColumn(name = "domp_id",primary = true)
    private long dompId;
    @AColumn(name = "menu_date")
    private Date menuDate;
    @AColumn(name = "category_id")
    private int categoryId;
    @AColumn(name = "created_date")
    private Date createdDate;
    @AColumn(name = "updated_date")
    private Date updatedDate;
    @AColumn(name = "local_user_id")
    private long localUserId;

    //no db prop
    private int childCount;
}
