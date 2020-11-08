package com.example.hatewait.model

import java.util.*


data class StoreListResponseData(var message: String? , var array:List<StoreListInfo>?)
data class StoreListInfo(var store_id:String, var store_name:String, var stamp_count: Int, var maximum_stamp: Int, var coupon_count: Int)

data class CouponListResponseData(var message: String?, var coupons:List<CouponListInfo>?)
data class CouponListInfo(var issue_date: Date, var expiration_date:Date, var used_date:Date, var coupon_count: Int)


