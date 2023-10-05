package dininghall.view.survey;

import dininghall.view.survey.models.Country;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "selectManyView")

@ViewScoped

public class SelectManyView {

    private List<String> selectedOptions;
    private List<String> selectedOptions2;
    private List<Country> selectedCountries;
    private List<Country> selectedCountries2;
    private List<Country> countries;

    @ManagedProperty("#{countryService_1}")
    private CountryService service;

    @PostConstruct
    public void init() {
        countries = service.getCountries();
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public List<String> getSelectedOptions2() {
        return selectedOptions2;
    }

    public void setSelectedOptions2(List<String> selectedOptions2) {
        this.selectedOptions2 = selectedOptions2;
    }

    public List<Country> getSelectedCountries() {
        return selectedCountries;
    }

    public void setSelectedCountries(List<Country> selectedCountries) {
        this.selectedCountries = selectedCountries;
    }

    public List<Country> getSelectedCountries2() {
        return selectedCountries2;
    }

    public void setSelectedCountries2(List<Country> selectedCountries2) {
        this.selectedCountries2 = selectedCountries2;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void setService(CountryService service) {
        this.service = service;
    }
}