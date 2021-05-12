package koover.com.koover2021;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class DynamicRVModel implements Parcelable {

    private String name;
    private String details;
    private int image;
    private int imageFondo;
    int pos;
    private LatLng latlong;
    private String address;

    public DynamicRVModel(String name, String details, int image, int imageFondo, int pos, LatLng latlong) {
        this.name = name;
        this.details = details;
        this.image = image;
        this.imageFondo = imageFondo;
        this.pos = pos;
        this.latlong = latlong;
    }

    protected DynamicRVModel(Parcel in) {
        name = in.readString();
        details = in.readString();
        image = in.readInt();
        imageFondo = in.readInt();
        pos = in.readInt();
        latlong = in.readParcelable(LatLng.class.getClassLoader());
        address = in.readString();
    }

    public static final Creator<DynamicRVModel> CREATOR = new Creator<DynamicRVModel>() {
        @Override
        public DynamicRVModel createFromParcel(Parcel in) {
            return new DynamicRVModel(in);
        }

        @Override
        public DynamicRVModel[] newArray(int size) {
            return new DynamicRVModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getImage() {
        return image;
    }

    public int getImageFondo(){
        return  imageFondo;
    }

    public int getPos() {
        return pos;
    }

    public LatLng getLatLong() {
        return latlong;
    }

    public String getAddress(){ return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(details);
        dest.writeInt(image);
        dest.writeInt(imageFondo);
        dest.writeInt(pos);
        dest.writeParcelable(latlong, flags);
        dest.writeString(address);
    }
}
