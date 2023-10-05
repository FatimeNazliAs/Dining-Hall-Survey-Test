package dininghall.asstpackages.multilanguage.language.Models;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@Data
@ASqlTable(TableName = "language_item")
public class LanguageItem implements ISqlTable, Serializable {
    @AColumn(name = "id", primary = true)
    int id;
    @AColumn(name = "item_id")
    int itemId;
    @AColumn(name = "language_id")
    int languageId;
    @AColumn(name = "item_property_name")
    String propertyName;
    @AColumn(name = "item_class_name")
    String className;

    @AColumn(name = "item_property_value")
    String propertyValue;

}
