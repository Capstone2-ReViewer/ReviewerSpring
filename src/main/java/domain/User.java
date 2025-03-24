package domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

public class User {

    @GeneratedValue()
    @Column(name="USER_PK")
    private int userPK;
    private String id;
    private String name;
    private String nickname;
    private int age;
    private boolean gender;
}
