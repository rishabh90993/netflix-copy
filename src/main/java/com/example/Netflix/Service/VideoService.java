package com.example.Netflix.Service;

import com.example.Netflix.Accesseror.AwsS3Accessor;
import com.example.Netflix.Accesseror.Model.Video.VideoDto;
import com.example.Netflix.Accesseror.VideoAccessor;
import com.example.Netflix.Exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoService {

    @Autowired
    private VideoAccessor videoAccessor;

    @Autowired
    private AwsS3Accessor awsS3Accessor;

    public String getVideoUrl(final String videoId){
        VideoDto videoDto = videoAccessor.getVideoByVideoId(videoId);

        if(videoDto == null) throw new InvalidDataException("Video Not Found!");

        String videoPath = videoDto.getVideoPath();

        return awsS3Accessor.getPreSignedUrl(videoPath,videoDto.getTotalLength()*60);

    }

    public String getThumbnailUrl(final String videoId){
        VideoDto videoDto = videoAccessor.getVideoByVideoId(videoId);

        if(videoDto == null) throw new InvalidDataException("Video Not Found!");

        String thumbnailPath = videoDto.getThumbnailPath();

        return awsS3Accessor.getPreSignedUrl(thumbnailPath,videoDto.getTotalLength()*60);

    }

}
