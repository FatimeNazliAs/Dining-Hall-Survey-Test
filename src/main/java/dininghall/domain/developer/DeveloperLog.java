package dininghall.domain.developer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import dininghall.generic.Manager.DbManager;

import java.io.Serializable;

@ASqlTable(TableName = "dev_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperLog implements Serializable, ISqlTable {
    @AColumn(name = "id", primary = true)
    private int id;
    @AColumn(name = "class_name")
    private String className = "";
    @AColumn(name = "error_code")
    private String errorCode = "";
    @AColumn(name = "error_description")
    private String errorDescription = "";
    @AColumn(name = "operation")
    private String operation = "";
    @AColumn(name = "error")
    private boolean error = false;

    public void AddLog(String className, String operation) {
        this.setClassName(className);
        this.setOperation(operation);
        new DbManager(this).Add();
    }

}
