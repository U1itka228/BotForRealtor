package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String city;
    private String type;
    private LocalDateTime timeToContact;
    private Long realtorID;
    private String location;
    private String formOfPayment;
    private String heatingLevel;
    private String shortDescription;

    public Customer() {
    }

    public Customer(String name, String phone, String city, String type,
                    LocalDateTime timeToContact, Long realtorID, String location, String formOfPayment, String heatingLevel, String shortDescription) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.type = type;
        this.timeToContact = timeToContact;
        this.realtorID = realtorID;
        this.location = location;
        this.formOfPayment = formOfPayment;
        this.heatingLevel = heatingLevel;
        this.shortDescription = shortDescription;
    }
    public Customer(ArchiveCustomer archiveCustomer) {
        this.name = archiveCustomer.getName();
        this.phone = archiveCustomer.getPhone();
        this.city = archiveCustomer.getCity();
        this.type = archiveCustomer.getType();
        this.timeToContact = archiveCustomer.getTimeToContact();
        this.realtorID = archiveCustomer.getRealtorID();
        this.location = archiveCustomer.getLocation();
        this.formOfPayment = archiveCustomer.getFormOfPayment();
        this.heatingLevel = archiveCustomer.getHeatingLevel();
        this.shortDescription = archiveCustomer.getShortDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimeToContact() {
        return timeToContact;
    }

    public void setTimeToContact(LocalDateTime timeToContact) {
        this.timeToContact = timeToContact;
    }

    public Long getRealtorID() { return realtorID; }

    public void setRealtorID(Long realtorID) { this.realtorID = realtorID; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(String formOfPayment) {
        this.formOfPayment = formOfPayment;
    }

    public String getHeatingLevel() {
        return heatingLevel;
    }

    public void setHeatingLevel(String heatingLevel) {
        this.heatingLevel = heatingLevel;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("📋 Информация о клиенте:\n");
        sb.append("•     Имя: ").append(name).append("\n");
        sb.append("•     Телефон: ").append(phone).append("\n");
        sb.append("•     Город: ").append(city).append("\n");
        if (shortDescription != null && !shortDescription.isEmpty()) {
            sb.append("•     Описание: ").append(shortDescription).append("\n");
        } else {
            sb.append("•     Тип недвижимости: ").append(type != null ? type : "не указано").append("\n");
            sb.append("•     Локация: ").append(location != null ? location : "не указано").append("\n");
            sb.append("•     Форма оплаты: ").append(formOfPayment != null ? formOfPayment : "не указано").append("\n");
            sb.append("•     Уровень клиента: ").append(heatingLevel != null ? heatingLevel : "не указано").append("\n");
            sb.append("Время контакта: ").append(timeToContact != null ?  "\n        \uD83D\uDCC5 " + timeToContact.getDayOfMonth() + "." + timeToContact.getMonth().getValue() + "." + timeToContact.getYear() +
                        "\n        \uD83D\uDD52 " + timeToContact.getHour() + ":" + timeToContact.getMinute()+"\n": " не указано\n\n");
        }

        return sb.toString();
    }


    //    @Override
//    public String toString() {
//        return
//                "\uD83D\uDCDD Основная информация:\n" +
//                        "•     Имя: " + name +
//                        "\n•     Телефон: " + (phone = "+7" + phone.substring(1)) +
//                        "\n•     Город: " + city +
//                        "\n•     Тип недвижимости: " + type +
//                        "\n\n⏰ Время следующего контакта: " +
//                        "\n        \uD83D\uDCC5 " + timeToContact.getDayOfMonth() + "." + timeToContact.getMonth().getValue() + "." + timeToContact.getYear() +
//                        "\n        \uD83D\uDD52 " + timeToContact.getHour() + ":" + timeToContact.getMinute();
//    }
}