package com.example.images.models;

import com.example.common.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
     @NamedQuery(name = "Images by User", query = "SELECT image FROM Image image where image.userId= :userId"),
     @NamedQuery(name = "All images", query = "SELECT image FROM Image image")
})

@Data
@Entity
@Table(name = "image")
public class Image extends BaseModel {

    @Column
    private String name;

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column
    private String description;

}
