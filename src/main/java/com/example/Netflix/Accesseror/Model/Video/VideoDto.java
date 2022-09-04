package com.example.Netflix.Accesseror.Model.Video;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Builder
@Getter
public class VideoDto {
    private String videoId;
    private String name;
    private String seriesId;
    private String showId;
    private double rating;
    private Date releaseDate;
    private int totalLength;

}
