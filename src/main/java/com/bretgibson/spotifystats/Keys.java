package com.bretgibson.spotifystats;

// Spotify Keys
public enum Keys {
    CLIENT_SECRET("8435d9a4becb4bcaa123647a809b40f4"),
    CLIENT_ID("d6e84ae1d0734c5bbb3f39513bafcf5c");

    private final String key;

    Keys(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

}
