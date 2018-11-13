package com.example.images.controllers;

import com.example.common.exceptions.ws.WSException;
import com.example.common.validators.auth.user.AuthenticatedUser;
import com.example.common.validators.auth.user.Secured;
import com.example.images.beans.ImageBean;
import com.example.images.controllers.response.ImageResponse;
import com.example.images.models.Image;
import com.example.users.beans.UserBean;
import com.example.users.models.Profile;
import com.example.users.models.User;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("/images")
@Secured
public class ImageController {

    @EJB
    ImageBean imageBean;

    @EJB
    UserBean userBean;

    @Inject
    @AuthenticatedUser
    User user;

    @POST
    @Path("/attachments")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadTicketDoc(@FormDataParam("file") InputStream file,
                                  @FormDataParam("file") FormDataContentDisposition contentDispositionHeader)
    {
        String response ="";
        try {
            String extension = "";

            String fileName = contentDispositionHeader.getFileName();

            int i = fileName.lastIndexOf('.');
            if (i >= 0) {
                extension = fileName.substring(i+1);
            }
            String name = UUID.randomUUID().toString().substring(0, 20) + "." +extension;
            response = imageBean.uploadFiles(file, name, user);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return response;
    }

    @GET
    @Path("/images")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImageResponse> getImages()
    {
        List<Image> images = imageBean.getAllImages();
        List<ImageResponse> response = new ArrayList<>();

        for(Image x: images)
        {
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setImage(x);
            Profile profile = userBean.profileByUser(x.getUserId());

            if(profile.getProfileImage()==null)
            {
                profile.setProfileImage("default.png");
            }

            imageResponse.setProfile(profile);
            response.add(imageResponse);
        }

        return response;
    }

    @GET
    @Path("/images/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Image> getImages(@PathParam("userId") Long userId)
    {
        return imageBean.getImages(userId);
    }





}
