package com.example.Netflix.Controller;

import com.example.Netflix.Accesseror.Model.WatchHistoryDto;
import com.example.Netflix.Controller.Models.WatchHistoryRequest;
import com.example.Netflix.Exceptions.InvalidDataException;
import com.example.Netflix.Exceptions.InvalidProfileException;
import com.example.Netflix.Security.Roles;
import com.example.Netflix.Service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class WatchHistoryController {

    @Autowired
    WatchHistoryService watchHistoryService;

    @PostMapping("/video/{videoId}/watchTime")
    @Secured({Roles.Customer})
    ResponseEntity<String> updateWatchHistory(@PathVariable("videoId") String videoId, @RequestBody WatchHistoryRequest request){

        try{
            watchHistoryService.updateWatchHistory(request.profileId,videoId,request.watchTime);
            return new ResponseEntity<String>("Watch History Updated Successfully!!", HttpStatus.OK);
        }catch (InvalidDataException | InvalidProfileException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (RuntimeException e ){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/video/{videoId}/watchTime")
    @Secured({Roles.Customer})
    ResponseEntity getWatchHistory(@PathVariable("videoId") String videoId,@RequestParam("profileId") String profileId){

        try{
            int watchHistory  = watchHistoryService.getWatchHistory(profileId,videoId);
            return new ResponseEntity(watchHistory, HttpStatus.OK);
        }catch (InvalidDataException | InvalidProfileException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (RuntimeException e ){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
