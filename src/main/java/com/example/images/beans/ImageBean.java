package com.example.images.beans;

import com.example.common.exceptions.ws.WSException;
import com.example.images.models.Image;
import com.example.users.models.User;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Local
@Stateless
public class ImageBean {

    @PersistenceContext
    EntityManager db;

    public void persistImage(Image image)
    {
        db.persist(image);
    }

    public Image findImage(Long id)
    {
        return db.find(Image.class, id);
    }

    public List<Image> getImages(Long userId)
    {
        return db.createNamedQuery("Images by User", Image.class).setParameter("userId", userId).getResultList();
    }

    public List<Image> getAllImages()
    {
        return db.createNamedQuery("All images", Image.class).getResultList();
    }

    public String uploadFiles(InputStream inputStream, String filename, User user) throws IOException {
        FTPClient client = new FTPClient();


        try {
            client.connect("apgexmarketing.com",21);
            client.login("user123@apgexmarketing.com", "Panda12345");
            client.enterLocalPassiveMode();
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.storeFile(filename, inputStream);

            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
            throw new WSException("Error al subir archivo").as(Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Image image = new Image();
        image.setCreationDate(new Date());
        image.setName(filename);
        image.setUserId(user.getId());

        persistImage(image);

        return "Subido exitosamente";
    }

}
