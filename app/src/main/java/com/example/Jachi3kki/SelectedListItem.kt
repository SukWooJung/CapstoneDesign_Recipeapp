package com.example.Jachi3kki

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedListItem(val data: String) : Parcelable

@Parcelize
class SelectedItems : ArrayList<SelectedListItem>(), Parcelable