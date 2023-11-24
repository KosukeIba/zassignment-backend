package com.zassignment.api

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.DocumentReference
import com.zassignment.service.PhotoService
import com.zippractice1.entity.PhotoFile
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class PhotoController (private val photoService: PhotoService) {

    @CrossOrigin(origins = ["http://localhost:5173", "https://data-honor-403613.web.app"])
    @GetMapping("/image/db")
    fun getAll(): ArrayList<Map<String, *>> {
        return photoService.getPhoto()
    }

    @CrossOrigin(origins = ["http://localhost:5173", "https://data-honor-403613.web.app"])
    @PostMapping("/post")
    fun putNewPost(
                   @RequestPart("file") file : MultipartFile,
                   @RequestPart("description") description : String,
                   @RequestPart("ownerId") ownerId : String
    ): String {
        return photoService.createNewPost(file, description, ownerId)
    }

}