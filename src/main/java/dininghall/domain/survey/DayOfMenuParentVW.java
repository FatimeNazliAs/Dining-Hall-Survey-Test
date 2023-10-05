package dininghall.domain.survey;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ASqlTable(TableName = "day_of_menu_parent_view")
public class DayOfMenuParentVW implements Serializable, ISqlTable
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
    @AColumn(name = "child_count")
    private int childrenCount;
}
