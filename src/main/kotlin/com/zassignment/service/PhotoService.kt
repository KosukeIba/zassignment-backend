package com.zassignment.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.*
import com.google.firebase.cloud.FirestoreClient
import com.zippractice1.entity.Photo
import org.springframework.stereotype.Service

@Service
public class PhotoService {
    public fun getPhoto(): ArrayList<Map<String, *>> {
        val db : Firestore = FirestoreClient.getFirestore();

        val query : ApiFuture<QuerySnapshot> = db.collection("photo").orderBy("timestamp").get();
        val querySnapshot : QuerySnapshot = query.get();
        val documents : List<QueryDocumentSnapshot> = querySnapshot.documents

        var ary : ArrayList<Map<String, *>> = arrayListOf();
        documents.forEach { document ->
            run {
                System.out.println((document.data))
                val jsonResult : String = jacksonObjectMapper().writeValueAsString(document.data);

                ary.add(document.data as Map<String, *>);
            }
        }

        return ary
    }

    public fun createNewPost(photo: Photo): ApiFuture<DocumentReference>? {
        val db : Firestore = FirestoreClient.getFirestore();
        val result : ApiFuture<DocumentReference>? = db.collection("photo").add(photo)

        return result
    }
}