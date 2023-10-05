package dininghall.asstpackages.multilanguage.language.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor

@ASqlTable(TableName = "language_strings")
public class LanguageString implements ISqlTable, Serializable {
    @AColumn(name = "id", primary = true)
    int id;
    @AColumn(name = "language_id")
    int languageId;
    @AColumn(name = "resource_name")
    String resourceName;
    @AColumn(name = "resource_value")
    String resourceValue;


}
