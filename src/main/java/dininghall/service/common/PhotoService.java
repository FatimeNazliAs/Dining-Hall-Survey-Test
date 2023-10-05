package dininghall.service.common;

import dininghall.domain.common.Photo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "photoService")
@ApplicationScoped
public class PhotoService {

    private List<Photo> photos;

    @PostConstruct
    public void init() {
        photos = new ArrayList<>();

        photos.add(new Photo("images/home/nuts1920x510.png", "images/home/nuts1920x510.png",
                "Description for Image 1", "Title 1"));

        photos.add(new Photo("images/home/spices1920x510.png", "images/home/spices1920x510.png",
                "Description for Image 2", "Title 2"));

    }

    public List<Photo> getPhotos() {
        return photos;
    }
}