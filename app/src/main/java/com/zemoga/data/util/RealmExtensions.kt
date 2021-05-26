package com.zemoga.data.util

import io.realm.Realm
import io.realm.RealmObject


inline fun <reified T : RealmObject> Realm.updateWithRealm(crossinline function: (Realm, T) -> Unit) =
    Realm.getDefaultInstance().use {
        it.executeTransaction { realm ->
            val ro = realm.findFirstOrCreate<T>()
            function(realm, ro)
        }
    }

inline fun <reified T : RealmObject> Realm.update(crossinline function: T.() -> Unit) =
    updateWithRealm<T> { _, it -> function(it) }

inline fun <reified T : RealmObject> Realm.findFirst(): T? = Realm.getDefaultInstance().use {
    it.where(T::class.java).findFirst()
}

inline fun <reified T : RealmObject> T.save() = Realm.getDefaultInstance().use {
    it.executeTransaction { realm ->
        realm.copyToRealmOrUpdate(this)
    }
}

inline fun <reified T : RealmObject> Realm.findFirstOrCreate(): T =
    where(T::class.java).findFirst() ?: createObject(T::class.java)

inline fun <reified T : RealmObject> Realm.deleteAllPosts() = Realm.getDefaultInstance().use {
    it.executeTransaction { realm ->
        realm.deleteAll()
    }
}

inline fun <reified T : RealmObject> Realm.findAll(): List<T>? = Realm.getDefaultInstance().use {
    it.where(T::class.java).findAll()
}
