package com.elaniin.technical_test.databases

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DaoTeam{
    lateinit var reference: DatabaseReference

    fun getDao(){
        val db = FirebaseDatabase.getInstance()
        reference = db.getReference("User")
    }

    fun add(user: User): Task<Void> {
        val uniqueKey: String = reference.child("User").push().key.toString()
        val uniqueKeyRef: Task<Void> = reference.child(uniqueKey).push().setValue(user)
        return uniqueKeyRef //reference.push().child(uniqueKeyRef).setValue(user)
    }
}