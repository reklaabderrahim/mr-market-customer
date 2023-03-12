package fr.mr_market.mr_customer.model

enum class CustomerAttributeNames(val value: String) {
    FIRST_NAME("firstName"), LAST_NAME("lastName"),
    GENDER("gender"), BIRTH_DATE("birthDate"),
    MAIL("mail"), LOGIN_DATE("loginDate"),
    ACTIVE("active")
}
