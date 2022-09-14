package com.example.Netflix.Controller;

import com.example.Netflix.Exceptions.InvalidDataException;
import com.example.Netflix.Security.Roles;
import com.example.Netflix.Service.VideoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("video/{videoId}/link")
    @Secured({Roles.Customer})
    public ResponseEntity<String> getVideoLink(@PathVariable("videoId") String videoId){
        try {
            String url = videoService.getVideoUrl(videoId);
            return ResponseEntity.status(HttpStatus.OK).body(url);
        }catch (InvalidDataException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }

    @GetMapping("video/{videoId}/thumbnail/link")
    @Secured({Roles.Customer})
    public ResponseEntity<String> getThumbnailLink(@PathVariable("videoId") String videoId){
        try {
            String url = videoService.getThumbnailUrl(videoId);
            return ResponseEntity.status(HttpStatus.OK).body(url);
        }catch (InvalidDataException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }

}
