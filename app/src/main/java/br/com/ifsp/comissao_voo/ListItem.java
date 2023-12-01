package br.com.ifsp.comissao_voo;

public class ListItem {
    private int id;
    private String name;
    private String weight;
    private int age;
    private String gender;
    private String dayOfWeek;
    private String description;

    public ListItem(){}

    public ListItem(int id, String name, String weight, int age, String gender, String dayOfWeek, String description) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.dayOfWeek = dayOfWeek;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
