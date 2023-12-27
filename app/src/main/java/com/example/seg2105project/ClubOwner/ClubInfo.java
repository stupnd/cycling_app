/**
 * The ClubInfo class represents information about a club, including its name, Instagram handle,
 * contact person, phone number, and ratings.
 */
package com.example.seg2105project.ClubOwner;

import java.util.HashMap;

public class ClubInfo {
    // These are private instance variables of the ClubInfo class.
    private String clubName;
    private String instagram;
    private String contact;
    private String phoneNumber;
    private HashMap<Float, String> ratings;

    // The `public ClubInfo(String clubName, String instagram, String contact, String phoneNumber)` is
    // a constructor method for the `ClubInfo` class. It is used to create a new instance of the
    // `ClubInfo` class and initialize its instance variables.
    public ClubInfo(String clubName, String instagram, String contact, String phoneNumber) {
        this.clubName = clubName;
        this.instagram = instagram;
        this.contact = contact;
        this.phoneNumber = phoneNumber;
        this.ratings = new HashMap<>();
    }

    /**
     * The function returns a HashMap containing ratings as keys and corresponding strings as values.
     * 
     * @return A HashMap with keys of type Float and values of type String is being returned.
     */
    public HashMap<Float, String> getRatings() {
        return ratings;
    }

    /**
     * The function adds a rating and feedback to a ratings map.
     * 
     * @param rating The rating parameter is a float value representing the rating given by a user.
     * @param feedback The feedback parameter is a string that represents the feedback or comments
     * associated with the rating.
     */
    public void addRating(float rating, String feedback) {
        ratings.put(rating, feedback);
    }

    /**
     * The toString() function returns a string representation of the ratings and feedback for an
     * object.
     * 
     * @return The method is returning a string representation of the ratings.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (!ratings.isEmpty()) {
            stringBuilder.append("Ratings:\n");
            for (Float rating : ratings.keySet()) {
                if (rating != 0 && ratings.get(rating) != null) {
                    stringBuilder.append("   - ").append("Rating: ").append(rating).append(", Feedback: ").append(ratings.get(rating)).append("\n");
                }
            }
        } else {
            stringBuilder.append("No ratings available.\n");
        }

        return stringBuilder.toString();
    }

    /**
     * The function returns the name of a club.
     * 
     * @return The method is returning the value of the variable "clubName".
     */
    public String getClubName() {
        return clubName;
    }

    /**
     * The function returns the value of the Instagram variable.
     * 
     * @return The method is returning the value of the variable "instagram".
     */
    public String getInstagram() {
        return instagram;
    }

    /**
     * The function returns the contact information.
     * 
     * @return The method is returning a String value.
     */
    public String getContact() {
        return contact;
    }

    /**
     * The function returns the phone number.
     * 
     * @return The method is returning a String value, specifically the value of the variable
     * "phoneNumber".
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * The function sets the club name for a Java class.
     * 
     * @param clubName The parameter "clubName" is a String that represents the name of a club.
     */
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    /**
     * The function sets the value of the Instagram variable.
     * 
     * @param instagram The parameter "instagram" is a string that represents the Instagram username or
     * handle.
     */
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    /**
     * The function sets the value of the "contact" variable.
     * 
     * @param contact The parameter "contact" is a string that represents the contact information for a
     * person or entity.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * The function sets the value of the phoneNumber variable.
     * 
     * @param phoneNumber The phoneNumber parameter is a String that represents a phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * The function sets the ratings of an object using a HashMap.
     * 
     * @param ratings The "ratings" parameter is a HashMap that maps Float values to String values.
     */
    public void setRatings(HashMap<Float, String> ratings) {
        this.ratings = ratings;
    }

    /**
     * The function returns a string representation of ratings, or a message indicating that no ratings
     * are available.
     * 
     * @return The method is returning a string representation of the ratings. If there are ratings
     * available, it will append each rating to the StringBuilder object and return the final string.
     * If there are no ratings available, it will return the string "No ratings available."
     */
    public String getRating() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!ratings.isEmpty()) {
            for (Float rating : ratings.keySet()) {
                if (rating != 0 && ratings.get(rating) != null) {
                    stringBuilder.append("Rating: ").append(rating);
                }
            }
        } else {
            stringBuilder.append("No ratings available.");
        }
        return stringBuilder.toString();
    }

    /**
     * The function returns a string containing feedback based on ratings.
     * 
     * @return The method is returning a string that contains feedback. If there are ratings available,
     * it will append each rating and its corresponding feedback to the string. If there are no ratings
     * available, it will return "No feedback available."
     */
    public String getFeedback() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!ratings.isEmpty()) {
            for (Float rating : ratings.keySet()) {
                if (rating != 0 && ratings.get(rating) != null) {
                    stringBuilder.append("Feedback: ").append(ratings.get(rating));
                }
            }
        } else {
            stringBuilder.append("No feedback available.");
        }
        return stringBuilder.toString();
    }




}
