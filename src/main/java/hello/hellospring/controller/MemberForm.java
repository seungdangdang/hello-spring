package hello.hellospring.controller;

public class MemberForm {

    // createMemberForm의 name="name"에서 "name"을 보고 아래 변수에 값을 넣어줌
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}