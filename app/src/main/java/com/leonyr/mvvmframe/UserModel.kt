package com.leonyr.mvvmframe

import com.squareup.moshi.Json

class UserModel {

    @field:Json(name = "token") var token: String? = null
    @field:Json(name = "nickName") var nickName: String? = null
    @field:Json(name = "mobile") var mobile: String? = null
    @field:Json(name = "avatar") var avatar: String? = null
    @field:Json(name = "gender") var gender: Int = 0
    @field:Json(name = "language") var language: String? = null
    @field:Json(name = "status")  var status: Int = 0

    @field:Json(name = "real")  var real: Int = 0
    @field:Json(name = "realName")  var realName: String? = null

    @field:Json(name = "openid")  private val openid: String? = null
    @field:Json(name = "city")  var city: String? = null
    @field:Json(name = "province") var province: String? = null
    @field:Json(name = "country") var country: String? = null

    @field:Json(name = "wxUnionid") var wxUnionid: String? = null
    @field:Json(name = "qqUnionid") var qqUnionid: String? = null
    @field:Json(name = "alipayUserId") var alipayUserId: String? = null

    @field:Json(name = "remark")  var remark: String? = null
}
