package dininghall.view.common;

import dininghall.service.common.PhotoService;
import org.primefaces.model.ResponsiveOption;
import dininghall.domain.common.Photo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "galleriaView")
@ViewScoped
public class GalleriaView implements Serializable {

    private List<Photo> photos;

    private List<ResponsiveOption> responsiveOptions1;

    private List<ResponsiveOption> responsiveOptions2;

    private List<ResponsiveOption> responsiveOptions3;

    private int activeIndex = 0;

    @ManagedProperty("#{photoService}")
    private PhotoService service;

    @PostConstruct
    public void init() {
        photos = service.getPhotos();

        responsiveOptions1 = new ArrayList<>();
        responsiveOptions1.add(new ResponsiveOption("1024px", 5));
        responsiveOptions1.add(new ResponsiveOption("768px", 3));


    }

    public void changeActiveIndex() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        this.activeIndex = Integer.valueOf(params.get("index"));
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public List<ResponsiveOption> getResponsiveOptions1() {
        return responsiveOptions1;
    }

    public List<ResponsiveOption> getResponsiveOptions2() {
        return responsiveOptions2;
    }

    public List<ResponsiveOption> getResponsiveOptions3() {
        return responsiveOptions3;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public void setService(PhotoService service) {
        this.service = service;
    }

}
