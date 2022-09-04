package com.example.Netflix.Accesseror.Model;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchHistoryDto {
    private String profileId;
    private String videoId;
    private double rating;
    private int watchedLength;
    private Date lastWatchedAt;
    private Date firstWatchedAt;
}
