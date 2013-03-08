package be.kdg.android.entities;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 14:29
 * Copyright @ Soulware.be
 */
public class Picture implements Serializable {
    private Integer id;
    private String url;
    private String description;

    public Picture() {
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
