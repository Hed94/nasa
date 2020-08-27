package com.hed.nasa.response;

import java.util.ArrayList;
import java.util.List;

/*
MarsRoverApiResponse je objekt skladující odpověď z API
Momentálně vytváří pouze list fotek, protože další atributy z odpovědí nevyužívám
 */

public class MarsRoverApiResponse {
    List<MarsPhoto> photos = new ArrayList<>();

    @Override
    public String toString() {
        return "MarsRoverApiResponse{" +
                "photos=" + photos +
                '}';
    }

    public List<MarsPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MarsPhoto> photos) {
        this.photos = photos;
    }
}
