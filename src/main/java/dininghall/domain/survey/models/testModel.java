package dininghall.domain.survey.models;

import lombok.Data;

@Data
public class testModel {
  private   String value="";
   private String key="";
   private String columnKey="";
   private int rowNum=-1;
   private String  colName="";
   private boolean  dbSearch=false;
   private boolean  dbAdd=false;
  private   boolean add=false;
}
