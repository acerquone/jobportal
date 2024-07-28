package com.luv2code.jobportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users_type")
public class UsersTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userTypeId;

    private String userTypeName;

    @OneToMany(targetEntity = Users.class,mappedBy = "userTypeId",cascade = CascadeType.ALL)
    private List<Users> usersList;

    public UsersTypes() {
    }

    public UsersTypes(int userTypeId, String userTypeName, List<Users> usersList) {
        this.userTypeId = userTypeId;
        this.userTypeName = userTypeName;
        this.usersList = usersList;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @Override
    public String toString() {
        return "UsersTypes{" +
                "userTypeId=" + userTypeId +
                ", userTypeName='" + userTypeName + '\'' +
                '}';
    }
}
