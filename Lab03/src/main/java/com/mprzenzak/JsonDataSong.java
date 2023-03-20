package com.mprzenzak;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class JsonDataSong {
    private String created;
    private int count;
    private int offset;
    private List<Release> releases;

    public List<Release> getReleases() {
        return releases;
    }
}

class Release {
    private String id;
    private int score;
    @SerializedName("status-id")
    private String statusId;
    private int count;
    private String title;
    private String status;
    @SerializedName("artist-credit")
    private List<ArtistCredit> artistCredit;
    private ReleaseGroup releaseGroup;
    private List<LabelInfo> labelInfo;
    private int trackCount;
    private List<Media> media;

    public String getTitle() {
        return title;
    }

    public List<ArtistCredit> getArtistCredit() {
        return artistCredit;
    }
}

class ArtistCredit {
    private String name;
    private Artist artist;

    public String getName() {
        return name;
    }
}

class Artist {
    private String id;
    private String name;
    @SerializedName("sort-name")
    private String sortName;
}

class ReleaseGroup {
    private String id;
    private String typeId;
    private String primaryTypeId;
    private String title;
    private String primaryType;
    private List<String> secondaryTypes;
    private List<String> secondaryTypeIds;
}

class LabelInfo {
    private String catalogNumber;
    private Label label;
}

class Label {
    private String id;
    private String name;
}

class Media {
    private String format;
    private int discCount;
    private int trackCount;
}
