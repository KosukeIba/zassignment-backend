package com.zassignment.api

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.DocumentReference
import com.zassignment.service.PhotoService
import com.zippractice1.entity.Photo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class PhotoController (private val photoService: PhotoService) {

    @CrossOrigin(origins = ["http://localhost:5173", "https://zippracitce.web.app/"])
    @GetMapping("/image/db")
    fun getAll(): ArrayList<Map<String, *>> {
        return photoService.getPhoto()
    }

    @PostMapping("/photo")
    fun putNewPost(@RequestBody photo: Photo): ApiFuture<DocumentReference>? {
        println("================")
        return photoService.createNewPost(photo)
    }

}