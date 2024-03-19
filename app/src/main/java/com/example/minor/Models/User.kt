package com.example.minor.Models

class User {
    var department: String?=null
    var email: String?=null
    var password: String?=null
    var name: String?=null
    var re_password: String?=null
    constructor()

    constructor(email: String?, password: String?, name: String?, re_password: String?) {
        this.email = email
        this.password = password
        this.name = name
        this.re_password = re_password
    }
}