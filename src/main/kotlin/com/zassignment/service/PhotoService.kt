package com.zassignment.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.api.core.ApiFuture
import com.google.cloud.Timestamp
import com.google.cloud.Timestamp.of
import com.google.cloud.firestore.*
import com.google.firebase.cloud.FirestoreClient
import com.zippractice1.entity.PhotoPost
import org.springframework.context.ApplicationContext
import org.springframework.core.io.WritableResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.collections.ArrayList

@Service
class PhotoService(private val ctx : ApplicationContext) {
    fun getPhoto(): ArrayList<Map<String, *>> {
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

    fun createNewPost(file: MultipartFile, description : String, ownerId : String): ApiFuture<DocumentReference>? {
        val bucket = "gs://zippractice1-photos/images"
        val id = UUID.randomUUID().toString()
        val uri = "$bucket/$id"  // create uri to store file data in cloudstorage
        val db : Firestore = FirestoreClient.getFirestore();

        // get resource location from application server by designating file path
        val gcs = ctx.getResource(uri) as WritableResource

        // store file in the location designated by the URL

        file.inputStream.use { input ->
            gcs.outputStream.use { output ->
                input.copyTo(output)
            }
        }

        val t = now()

        val photoPost = PhotoPost();
        photoPost.id = id;
        photoPost.uri = uri;
        photoPost.label = description;
        photoPost.ownerId = ownerId;
        photoPost.timestamp = t

        println(photoPost)

        val result : ApiFuture<DocumentReference>? = db.collection("photo").add(photoPost)

        return result
    }

    fun now(): com.google.cloud.Timestamp {
        val date = java.sql.Timestamp(System.currentTimeMillis())
        return of(date)
    }
}