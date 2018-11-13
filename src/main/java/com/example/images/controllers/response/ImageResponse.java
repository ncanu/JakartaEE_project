package com.example.images.controllers.response;

import com.example.images.models.Image;
import com.example.users.models.Profile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageResponse {

    @JsonProperty
    private Image image;

    @JsonProperty
    private Profile profile;

}
