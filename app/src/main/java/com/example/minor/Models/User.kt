package com.example.minor.Models

class User {
    var isFaculty: Boolean = false
    var isStudent: Boolean = false
    var department: String?=null
    var email: String?=null
    var password: String?=null
    var name: String?=null
    var re_password: String?=null
    constructor()

    constructor(email: String?, password: String?, name: String?, re_password: String?, department: String?, isStudent: Boolean?, isFaculty: Boolean?) {
        this.email = email
        this.password = password
        this.name = name
        this.re_password = re_password
        this.isFaculty= false
        this.isStudent= false
        this.department= department
    }
}