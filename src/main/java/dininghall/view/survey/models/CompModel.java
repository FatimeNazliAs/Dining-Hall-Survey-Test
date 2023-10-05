package dininghall.view.survey.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
    @AllArgsConstructor
  public  class CompModel {
        String columnkey = "";
        boolean use = false;
        int categoryId = 0;
        int foodCategoryId = 0;
        boolean extra=false;

    }