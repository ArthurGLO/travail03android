package ca.ulaval.ima.tp3.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private  String img ;
    private int year ;
    private int kilometers ;
    private int price ;
    private String brandName ;
    private String modelName;
    private String transmission;
    private Boolean sale;
    private String description;


    public Car(String img , int year , int kilometers ,int price , String brandName , String modelName){
        this.img = img ;
        this.year = year ;
        this.kilometers = kilometers ;
        this.price =  price ;
        this.brandName = brandName ;
        this.modelName = modelName;
    }

    public Car(String img , int year , int kilometers ,String transmission,Boolean sale,int price , String brandName , String modelName,String description){
        this.img = img ;
        this.description = description;
        this.sale = sale;
        this.transmission = transmission;
        this.year = year ;
        this.kilometers = kilometers ;
        this.price =  price ;
        this.brandName = brandName ;
        this.modelName = modelName;
    }

    public int getKilometers() {
        return kilometers;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Boolean getSale() {
        return sale;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getImg() {
        return img;
    }

    public String getModelName() {
        return modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setYear(int year) {
        this.year = year;
    }



    protected Car(Parcel in) {
        img = in.readString();
        year = in.readInt();
        kilometers = in.readInt();
        price = in.readInt();
        brandName = in.readString();
        modelName = in.readString();
        transmission = in.readString();
        byte saleVal = in.readByte();
        sale = saleVal == 0x02 ? null : saleVal != 0x00;
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeInt(year);
        dest.writeInt(kilometers);
        dest.writeInt(price);
        dest.writeString(brandName);
        dest.writeString(modelName);
        dest.writeString(transmission);
        if (sale == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (sale ? 0x01 : 0x00));
        }
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
}