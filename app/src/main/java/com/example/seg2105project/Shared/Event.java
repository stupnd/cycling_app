/**
 * The Event class represents an event and contains methods to set and get the event name, age, pace,
 * and level, as well as a toString() method to return a string representation of the event.
 */
package com.example.seg2105project.Shared;

import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.DB.UDBHelper;

public class Event {
    private String eventName, age, pace, level, clubName, participants, date, newName;
    UDBHelper Udb;

   // The code you provided is a constructor for the Event class. It takes four parameters: eventName,
   // age, pace, and level. When an object of the Event class is created, this constructor is called
   // and assigns the values of the parameters to the corresponding instance variables (eventName, age,
   // pace, and level) of the object.
    public Event (String eventName,String age,String pace,String level, String clubName){
        this.eventName = eventName;
        this.age = age;
        this.pace = pace;
        this.level = level;
        if(clubName == null){
            this.clubName = "admin";
        } else {
            this.clubName = clubName;
        }
    }

    public Event (String eventName,String age,String pace,String level, String clubName, String participants, String date){
        this.eventName = eventName;
        this.age = age;
        this.pace = pace;
        this.level = level;
        if(clubName == null){
            this.clubName = "admin";
        } else {
            this.clubName = clubName;
        }
        this.participants = participants;
        this.date = date;
    }

    public Event (String eventName,String age,String pace,String level, String clubName, String participants, String date, String newName){
        this.eventName = eventName;
        this.age = age;
        this.pace = pace;
        this.level = level;
        if(clubName == null){
            this.clubName = "admin";
        } else {
            this.clubName = clubName;
        }
        this.participants = participants;
        this.date = date;
        if(newName == null){
            this.newName = "Default Event";
        } else {
            this.newName = newName;
        }

    }

    /**
     * The function sets the name of an event.
     * 
     * @param eventName The parameter "eventName" is a String that represents the name of an event.
     */
    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    /**
     * The function sets the age of an object.
     * 
     * @param age The parameter "age" is a String that represents the age of an object.
     */
    public void setAge(String age){
        this.age = age;
    }
    /**
     * The function sets the value of the "pace" variable to the given input.
     * 
     * @param pace The "pace" parameter is a string that represents the pace of something.
     */
    public void setPace(String pace){
        this.pace = pace;
    }
    /**
     * The function sets the value of the "level" variable.
     * 
     * @param level The level parameter is a String that represents the level of something.
     */
    public void setLevel(String level){
        this.level = level;
    }
    /**
     * The function returns the name of an event.
     * 
     * @return The method is returning the value of the variable eventName.
     */
    public String getEventName(){
        return eventName;
    }
    /**
     * The function returns the age as a string.
     * 
     * @return The age as a string.
     */
    public String getAge(){
        return age;
    }
    /**
     * The function getPace() returns the pace.
     * 
     * @return The method is returning the value of the variable "pace".
     */
    public String getPace(){
        return pace;
    }
    /**
     * The function returns the value of the variable "level".
     * 
     * @return The method is returning the value of the variable "level".
     */
    public String getLevel(){
        return level;
    }

    public String getClubName() {return clubName;}

    public String getDate() {return date;}

    public int getParticipants() {
        int participantNumber = Integer.parseInt(participants);
        return participantNumber;
    }

    public String getNewName() {return newName;}

    /**
     * The toString() function returns a string representation of an event's name, age, pace, and
     * level.
     * 
     * @return The toString() method is returning a string representation of an event, including the
     * event name, age, pace, and level.
     */
    public String toString(){
        if(date == null){
            return getEventName() +
                    " Age = " + getAge() +
                    ", Pace = " + getPace() +
                    ", Level = " + getLevel() +
                    ", Club Name = " + this.clubName;
        } else {
            return getEventName() +
                    " Age = " + getAge() +
                    ", Pace = " + getPace() +
                    ", Level = " + getLevel() +
                    ", Participants = " + this.participants +
                    ", Date = " + this.date +
                    ", Club Name = " + this.clubName +
                    ", Event Name = " + this.newName;
        }

    }

}
