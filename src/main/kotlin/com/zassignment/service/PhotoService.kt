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

        // DAO start

        val query : ApiFuture<QuerySnapshot> = db.collection("photo").orderBy("timestamp").get();
        val querySnapshot : QuerySnapshot = query.get();
        val documents : List<QueryDocumentSnapshot> = querySnapshot.documents

        var ary : ArrayList<Map<String, *>> = arrayListOf();
        documents.forEach { document ->
            run {
                ary.add(document.data as Map<String, *>);
            }
        }

        // DAO end

        return ary
    }

    // take posted photo info from frontend;
    // save the photo to cloud store;
    // save post info to firestore;
    fun createNewPost(file: MultipartFile, description : String, ownerId : String): String {
        val bucket = "gs://zippractice1-photos/images"
        val id = UUID.randomUUID().toString()
        val uri = "$bucket/$id"  // create uri to store file data in cloudstorage
        val db : Firestore = FirestoreClient.getFirestore();

        // DAO for cloud storage start

        // get resource location from application server by designating file path
        val gcs = ctx.getResource(uri) as WritableResource

        // store file in the location designated by the URL
        file.inputStream.use { input ->
            gcs.outputStream.use { output ->
                input.copyTo(output)
            }
        }

        // DAO for cloud storage end

        val t = now()

        val photoPost = PhotoPost();
        photoPost.id = id;
        photoPost.uri = "/images/$id";
        photoPost.label = description;
        photoPost.ownerId = ownerId;
        photoPost.timestamp = t

        println(photoPost)

        // DAO for firestore start

        val result : String = db.collection("photo").add(photoPost).get().id;

        // DAO for firestore end

        return result // return document Id for a post
    }

    fun now(): com.google.cloud.Timestamp {
        val date = java.sql.Timestamp(System.currentTimeMillis())
        return of(date)
    }
}