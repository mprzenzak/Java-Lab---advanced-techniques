package com.mprzenzak;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonDataBand {
    String created;
    int count;
    int offset;
    List<Artist> artists;

    public static JsonDataBand fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JsonDataBand.class);
    }

    public String getCreated() {
        return created;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    class Artist {
        String id;
        String type;
        String typeId;
        int score;
        String name;
        String sortName;
        String country;
        Area area;
        @SerializedName("life-span")
        LifeSpan lifeSpan;

        public String getName() {
            return name;
        }
        public String getScore() {
            return String.valueOf(score);
        }
        public LifeSpan getLifeSpan() {
            return lifeSpan;
        }
    }

    class Area {
        String id;
        String type;
        String typeId;
        String name;
        String sortName;
        @SerializedName("life-span")
        LifeSpan lifeSpan;
    }

    class LifeSpan {
        String begin;
        String end;
        Boolean ended;

        public String getBegin() {
            return begin;
        }
    }
}
