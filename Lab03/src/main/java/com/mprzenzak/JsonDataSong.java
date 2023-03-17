package com.mprzenzak;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class JsonDataSong {
    private String created;
    private int count;
    private int offset;
    private List<Release> releases;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ArtistCredit> getArtistCredit() {
        return artistCredit;
    }

    public void setArtistCredit(List<ArtistCredit> artistCredit) {
        this.artistCredit = artistCredit;
    }

    public ReleaseGroup getReleaseGroup() {
        return releaseGroup;
    }

    public void setReleaseGroup(ReleaseGroup releaseGroup) {
        this.releaseGroup = releaseGroup;
    }

    public List<LabelInfo> getLabelInfo() {
        return labelInfo;
    }

    public void setLabelInfo(List<LabelInfo> labelInfo) {
        this.labelInfo = labelInfo;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}

class ArtistCredit {
    private String name;
    private Artist artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}

class Artist {
    private String id;
    private String name;
    @SerializedName("sort-name")
    private String sortName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}

class ReleaseGroup {
    private String id;
    private String typeId;
    private String primaryTypeId;
    private String title;
    private String primaryType;
    private List<String> secondaryTypes;
    private List<String> secondaryTypeIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getPrimaryTypeId() {
        return primaryTypeId;
    }

    public void setPrimaryTypeId(String primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public List<String> getSecondaryTypes() {
        return secondaryTypes;
    }

    public void setSecondaryTypes(List<String> secondaryTypes) {
        this.secondaryTypes = secondaryTypes;
    }

    public List<String> getSecondaryTypeIds() {
        return secondaryTypeIds;
    }

    public void setSecondaryTypeIds(List<String> secondaryTypeIds) {
        this.secondaryTypeIds = secondaryTypeIds;
    }
}

class LabelInfo {
    private String catalogNumber;
    private Label label;

    // getters and setters
}

class Label {
    private String id;
    private String name;

    // getters and setters
}

class Media {
    private String format;
    private int discCount;
    private int trackCount;

    // getters and setters
}
