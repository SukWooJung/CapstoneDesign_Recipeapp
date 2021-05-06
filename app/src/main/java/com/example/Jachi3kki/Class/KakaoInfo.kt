package com.example.Jachi3kki.Class

import android.os.Parcelable
import com.kakao.usermgmt.response.model.Profile
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KakaoInfo(val id: String, val email: String, val profileImg: String): Parcelable
