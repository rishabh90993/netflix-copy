package com.example.Netflix.Controller.Models;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class WatchHistoryRequest {
    public String profileId;
    public int watchTime;
}
