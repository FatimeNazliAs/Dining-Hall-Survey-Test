package dininghall.domain.survey.SurveyExcel;

import dininghall.domain.survey.Food;
import dininghall.domain.survey.models.testModel;
import dininghall.generic.Manager.DbManager;
import dininghall.generic.Manager.PostgreSqlManager;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodManager {
    public void FoodAddJust(List<Map<String, testModel>> excelOutDataList) {
        Map<String, String> list = new HashMap<>();
        list.put("corba", "kalori1");
        list.put("ana", "kalori2");
        list.put("ara", "kalori3");
        list.put("tatli", "kalori4");
        list.put("salata", "kalori5");
        list.put("icecek", "kalori6");
        list.put("kahvalti", "kalori7");
        String upadetSyntax = "";
        List<Food> foodList = new ArrayList<>();
        List<Food> updateList = new ArrayList<>();
        for (Map<String, testModel> item : excelOutDataList) {
            Food food = new Food();
            food.setCalori(Integer.parseInt(item.get("calori").getValue()));
            food.setTitle(item.get("title").getValue());
            food.setFoodCategoryId(Integer.parseInt(item.get("category").getValue()));
            foodList.add(food);
            if (food.getTitle() != null && food.getTitle().length() > 0)
                upadetSyntax += " title='" + food.getTitle() + "' or ";

        }
        if (upadetSyntax.length() > 0) {
            upadetSyntax = upadetSyntax.substring(0, upadetSyntax.length() - 3);
            upadetSyntax = "select * from food where " + upadetSyntax;
            var obj = PostgreSqlManager.Read(new Food(), upadetSyntax);
            for (var item : obj) {
                for (var fitem : foodList) {
                    boolean update = false;
                    if (fitem.getTitle().equals(item.getTitle())) {
                        if (fitem.getFoodCategoryId() != item.getFoodCategoryId() || fitem.getCalori() != item.getCalori()) {
                            fitem.setFoodId(item.getFoodId());
                            updateList.add(fitem);
                        }
                    }
                }
            }
        }
        //update items

        foodList = CompareFoodDatabase(foodList);
        foodList = FoodListCategoryId(foodList, excelOutDataList);
        foodList = FoodsAdd(foodList);
        if (!updateList.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            String  updateSyntax="";
            for (var item:updateList) {
                updateSyntax+="update food set calori="+item.getCalori()+" ,food_category_id="+item.getFoodCategoryId()+" where food_id="+item.getFoodId()+" ; ";
            }
            PostgreSqlManager.Command(updateSyntax);
        }

    }

    public void FoodAdd(List<Map<String, testModel>> excelOutDataList) {
        Map<String, String> list = new HashMap<>();
        list.put("corba", "kalori1");
        list.put("ana", "kalori2");
        list.put("ara", "kalori3");
        list.put("tatli", "kalori4");
        list.put("salata", "kalori5");
        list.put("icecek", "kalori6");
        var foodList = FoodListEquals(excelOutDataList, list);
        //compare start unique
        foodList = FoodCompareListToList(foodList);
        foodList = CompareFoodDatabase(foodList);
        foodList = FoodListCategoryId(foodList, excelOutDataList);

        foodList = FoodsAdd(foodList);
        foodList = foodList;
    }

    public List<Food> FoodListEquals(List<Map<String, testModel>> excelOutDataList, Map<String, String> list) {

        List<Food> foodList = new ArrayList<Food>();
        //Listeye ekle
        for (var item : excelOutDataList) {
            for (var item_map : list.entrySet()) {
                Food food = new Food();
                var item_read = item.get(item_map.getKey());

                if (item_read == null)
                    continue;
                food.setTitle(item_read.getValue());


                var v_value = item.get(item_map.getValue());
                int value = 0;
                if (v_value != null)
                    food.setCalori(Integer.parseInt(v_value.getValue()));


                foodList.add(food);
            }
        }
        return foodList;
    }

    public List<Food> FoodCompareListToList(List<Food> foodList) {
        for (int i = 0; i < foodList.size(); i++) {
            for (int j = i + 1; j < foodList.size(); j++) {
                if (foodList.get(i).getTitle().equals(foodList.get(j).getTitle()))
                    foodList.remove(j--);
            }

        }
        return foodList;
    }

    public List<Food> FoodDatabaseList(List<Food> foodList) {
        //compare start db
        String syntax = "select * from food where ";

        boolean find = false;
        for (var food : foodList) {
            if (find)
                syntax += " or ";
            syntax += "title='" + food.getTitle() + "'";
            find = true;
        }
        if (find)
            return PostgreSqlManager.Read(new Food(), syntax);
        return new ArrayList<>();
    }

    public List<Food> CompareFoodDatabase(List<Food> foodList) {
//exceldeki tüm yemekeler
        var list_food_db = FoodDatabaseList(foodList);

//compare for
        for (var item : list_food_db) {
            for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).getTitle().equals(item.getTitle())) {
                    foodList.remove(i--);
                }
            }
        }
        return foodList;
    }

    public List<Food> FoodsAdd(List<Food> foods) {
        for (var food : foods) {
            int id = new DbManager(food).Add();
            food.setFoodId(id);


        }
        return foods;
    }

    public Map<String, Food> FoodListConvertToFoodMap(List<Food> foodList) {
        Map<String, Food> foodMap = new HashMap<>();
        for (var item : foodList) {
            foodMap.put(item.getTitle(), item);
        }
        return foodMap;
    }

    public Map<String, String> FoodListBreakfast() {

        Map<String, String> list = new HashMap<>();
        list.put("kahvalti1", "kahvalti");
        list.put("kahvalti2", "kahvalti");
        list.put("kahvalti3", "kahvalti");
        list.put("kahvalti4", "kahvalti");
        list.put("kahvalti5", "kahvalti");
        list.put("kahvalti6", "kahvalti");
        list.put("kahvalti7", "kahvalti");
        list.put("kahvalti8", "kahvalti");
        list.put("kahvalti9", "kahvalti");
        list.put("kahvalti10", "kahvalti");
        return list;

    }

    public List<Food> FoodList(List<Map<String, testModel>> excelOutDataList, int categoryId) {
        Map<String, String> list = new HashMap<>();
        if (categoryId > 1)
            list = FoodListLuncDinner();
        else
            list = FoodListBreakfast();

        var foodList = FoodListEquals(excelOutDataList, list);
        //compare start unique
        foodList = FoodCompareListToList(foodList);
        foodList = FoodDatabaseList(foodList);
        return foodList;
    }

    public Map<String, String> FoodListLuncDinner() {
        Map<String, String> list = new HashMap<>();
        list.put("corba", "kalori1");
        list.put("ana", "kalori2");
        list.put("ara", "kalori3");
        list.put("tatli", "kalori4");
        list.put("salata", "kalori5");
        list.put("icecek", "kalori6");
        return list;


    }

    private List<Food> FoodListCategoryId(List<Food> foodList, List<Map<String, testModel>> excelOutDataList) {
        for (var item_food : foodList) {
            for (var item : excelOutDataList) {

                boolean find = false;
                for (var item_property : item.entrySet()) {

                    if (item_property.getValue().equals(item_food.getTitle())) {
                        find = true;
                        System.out.println(item_property.getKey());

                        if (item_property.getKey().equals("ara")) {
                            item_food.setFoodCategoryId(3);
                        } else if (item_property.getKey().equals("corba")) {
                            item_food.setFoodCategoryId(1);
                        } else if (item_property.getKey().equals("tatli")) {
                            item_food.setFoodCategoryId(4);
                        } else if (item_property.getKey().equals("salata")) {
                            item_food.setFoodCategoryId(5);
                        } else if (item_property.getKey().equals("ana")) {
                            item_food.setFoodCategoryId(2);
                        } else if (item_property.getKey().equals("icecek")) {
                            item_food.setFoodCategoryId(6);
                        } else if (item_property.getKey().equals("kahvalti")) {
                            item_food.setFoodCategoryId(7);
                        }
                        break;
                    }
                    if (find)
                        break;
                }
            }
        }
        return foodList;
    }
}
