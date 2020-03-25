package ca.ulaval.ima.tp3.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Salor implements Parcelable {
    private String salorFirstName;
    private String salorLastName;
    private String salorMail;

    public Salor(String salorFirstName,String salorLastName,String SalorMail){
        this.salorLastName = salorLastName;
        this.salorFirstName = salorFirstName;
        this.salorMail = SalorMail;
    }

    public String getSalorFirstName() {
        return salorFirstName;
    }

    public String getSalorLastName() {
        return salorLastName;
    }

    public void setSalorFirstName(String salorFirstName) {
        this.salorFirstName = salorFirstName;
    }

    public void setSalorLastName(String salorLastName) {
        this.salorLastName = salorLastName;
    }

    public String getSalorMail() {
        return salorMail;
    }

    public void setSalorMail(String salorMail) {
        this.salorMail = salorMail;
    }



    protected Salor(Parcel in) {
        salorFirstName = in.readString();
        salorLastName = in.readString();
        salorMail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(salorFirstName);
        dest.writeString(salorLastName);
        dest.writeString(salorMail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Salor> CREATOR = new Parcelable.Creator<Salor>() {
        @Override
        public Salor createFromParcel(Parcel in) {
            return new Salor(in);
        }

        @Override
        public Salor[] newArray(int size) {
            return new Salor[size];
        }
    };
}