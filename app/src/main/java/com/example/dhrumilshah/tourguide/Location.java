package com.example.dhrumilshah.tourguide;

 class Location {
    private int mImageResourceId;
    private int mNameStringId;
    private int mAddressStringId;
    private int mAudioResourceId;

     Location(int mImageResourceId, int mNameStringId, int mAddressStringId, int mAudioResourceId) {
        this.mImageResourceId = mImageResourceId;
        this.mNameStringId = mNameStringId;
        this.mAddressStringId = mAddressStringId;
        this.mAudioResourceId = mAudioResourceId;
    }

     int getImageResourceId() {
        return mImageResourceId;
    }

     int getNameStringId() {
        return mNameStringId;
    }

     int getAddressStringId() {
        return mAddressStringId;
    }
     int getAudioResourceId() {
         return mAudioResourceId;
     }
}
