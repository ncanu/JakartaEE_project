package com.example.users.models;


import com.example.common.BaseModel;
import lombok.Data;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Profile by User", query = "SELECT profile FROM Profile profile where profile.userId= :userId")
})

@Data
@Entity
@Table(name = "profile")
public class Profile extends BaseModel {

    @Column
    private String name;

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "description")
    private String description;

    @Column(name = "profile_image")
    private String profileImage;

}
