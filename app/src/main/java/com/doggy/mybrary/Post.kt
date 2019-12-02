package com.doggy.mybrary

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Post(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var nowPage: Int = 0,
    open var nowPersent: Int = 0,
    open var sentence1: String = "",
    open var sentence2: String = "",
    open var sentence3: String = "",
    open var createdAt: Date = Date(System.currentTimeMillis())
) : RealmObject()