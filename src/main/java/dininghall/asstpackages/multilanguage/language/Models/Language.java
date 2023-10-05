package dininghall.asstpackages.multilanguage.language.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@ASqlTable(TableName = "language_table")
@Data
@NoArgsConstructor
public class Language implements ISqlTable, Serializable {
    @AColumn(name = "id", primary = true, defaultby = true)
    int id;
    @AColumn(name = "name")
    String name;
    @AColumn(name = "language_culture")
    String languageCulture;
    @AColumn(name = "unique_seo_code")
    String uniqueSeoCode;
    @AColumn(name = "flag_image_file_name")
    String flagimagefilename;
    @AColumn(name = "published")
    boolean published;
    @AColumn(name = "display_order")
    int displayorder;


}
