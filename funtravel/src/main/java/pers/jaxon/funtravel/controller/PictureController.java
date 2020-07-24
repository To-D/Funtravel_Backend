package pers.jaxon.funtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.jaxon.funtravel.controller.request.RegisterRequest;
import pers.jaxon.funtravel.domain.Picture;
import pers.jaxon.funtravel.service.PictureService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/getHottestPictures")
    public ResponseEntity<List> getHot() {
        List<Picture> hottest = pictureService.getHottestPictures();
        return ResponseEntity.ok(hottest);
    }

    @PostMapping("/getNewestPictures")
    public ResponseEntity<List> getNew() {
//        List<Picture> newest = pictureService.getNewestPictures();
        List<String> newest = new LinkedList<>();
        return ResponseEntity.ok(newest);

    }

    @GetMapping("/loadimg")
    public void getImg2(HttpServletResponse response, String id ) {

        //这里省略掉通过id去读取图片的步骤。

        File file = new File("/resources/pictures/picture4.jpg");//imgPath为服务器图片地址
        if(file.exists() && file.isFile()){

            FileInputStream fis = null;

            OutputStream os = null;

            try {

                fis = new FileInputStream(file);

                os = response.getOutputStream();

                int count = 0;

                byte[] buffer = new byte[1024 * 8];

                while ((count = fis.read(buffer)) != -1) {

                    os.write(buffer, 0, count);

                    os.flush();

                }

            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                try {

                    fis.close();

                    os.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
