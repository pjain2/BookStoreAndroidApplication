package edu.stevens.cs522.entities;

/**
 * Created by Poonam on 2/7/2015.
 */


import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{

    // TODO Modify this to implement the Parcelable interface.

    // TODO redefine toString() to display book title and price (why?).

    public int id;

    public String title;

    public Author[] authors;

    public String isbn;

    public String price;

    public Book(int id, String title, Author[] author, String isbn, String price) {
        this.id = id;
        this.title = title;
        this.authors = author;
        this.isbn = isbn;
        this.price = price;
    }

    /*Method required by Parceable interface
     * */
    private Book(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.authors = new Author[5];
        in.readTypedArray(authors, Author.CREATOR);;
        this.isbn = in.readString();;
        this.price = in.readString();;
    }

    /*Method required by Parceable interface
     * */
    public int describeContents() {
        return 0;
    }

    /*Method required by Parceable interface
     * */
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeTypedArray(authors, 0);
        out.writeString(isbn);
        out.writeString(price);
    }

    /*Variable required by Parceable interface
     * */
    public static final Parcelable.Creator<Book> CREATOR
            = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString(){
        return this.title +" $" +  this.price;
    }

}