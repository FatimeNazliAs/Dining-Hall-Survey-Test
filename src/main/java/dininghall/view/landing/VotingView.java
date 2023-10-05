package dininghall.view.landing;

import lombok.Data;

import javax.faces.application.FacesMessage;
        import javax.faces.bean.ManagedBean;
        import javax.faces.bean.ViewScoped;
        import javax.faces.context.FacesContext;
        import java.util.ArrayList;
        import java.util.List;

@ManagedBean
@ViewScoped
public class VotingView {
    private List<Meal> meals;

    public VotingView() {
        meals = new ArrayList<>();
        meals.add(new Meal("Meal 1", 0, "", "https://globalimagecreation.com/wp-content/uploads/2018/01/food_photographer-globalimagecreation.comJMM_7357-min-uai-720x480.jpg" ));
        meals.add(new Meal("Meal 2", 0, "", "https://globalimagecreation.com/wp-content/uploads/2018/01/food_photographer-globalimagecreation.comJMM_7357-min-uai-720x480.jpg"));
        meals.add(new Meal("Meal 3", 0, "", "https://globalimagecreation.com/wp-content/uploads/2018/01/food_photographer-globalimagecreation.comJMM_7357-min-uai-720x480.jpg"));
        meals.add(new Meal("Meal 4", 0, "", "https://globalimagecreation.com/wp-content/uploads/2018/01/food_photographer-globalimagecreation.comJMM_7357-min-uai-720x480.jpg"));
        meals.add(new Meal("Meal 5", 0, "", "https://globalimagecreation.com/wp-content/uploads/2018/01/food_photographer-globalimagecreation.comJMM_7357-min-uai-720x480.jpg"));
    }

    public void submit() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your survey has been submitted successfully!"));
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}


