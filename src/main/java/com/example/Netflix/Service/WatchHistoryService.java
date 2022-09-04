package com.example.Netflix.Service;

import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.Model.Video.VideoDto;
import com.example.Netflix.Accesseror.VideoAccessor;
import com.example.Netflix.Accesseror.Model.WatchHistoryDto;
import com.example.Netflix.Accesseror.WatchHistoryAccessor;
import com.example.Netflix.Exceptions.InvalidDataException;
import com.example.Netflix.Validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WatchHistoryService {

    @Autowired
    Validator validator;

    @Autowired
    WatchHistoryAccessor watchHistoryAccessor;

    @Autowired
    VideoAccessor videoAccessor;

    public void updateWatchHistory(final String profileId, final String videoId, final int watchedLength){
        UserDto userDto =  (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validator.validateProfile(profileId,userDto.getUserID());

        VideoDto videoDto = videoAccessor.getVideoByVideoId(videoId);

        if(watchedLength<1  || watchedLength > videoDto.getTotalLength()){
            throw new InvalidDataException("Invalid WatchLength");
        }

        WatchHistoryDto watchHistoryDto = watchHistoryAccessor.getWatchHistory(profileId,videoId);
        if(watchHistoryDto==null){
            watchHistoryAccessor.insertWatchHistory(profileId,videoId,watchedLength,0.0);
        }else{
            watchHistoryAccessor.updateWatchHistory(profileId,videoId,watchedLength);
        }

    }

    public int getWatchHistory(final String profileId, final String videoId){
        UserDto userDto =  (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validator.validateProfile(profileId,userDto.getUserID());

        VideoDto videoDto = videoAccessor.getVideoByVideoId(videoId);

        WatchHistoryDto watchHistoryDto = watchHistoryAccessor.getWatchHistory(profileId,videoId);
        if(watchHistoryDto!=null)
        return  watchHistoryDto.getWatchedLength();
        else
            return 0;

    }

}
