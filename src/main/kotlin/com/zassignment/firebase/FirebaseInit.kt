package com.zassignment.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import org.springframework.stereotype.Service
import java.io.FileInputStream
import javax.annotation.PostConstruct

@Service
class FirebaseInit {
    @PostConstruct
    public fun FirebaseInitialization() {

        if (!System.getenv("gcp_service").isNullOrBlank()) {
            val serviceAccount = FileInputStream(System.getenv("gcp_service"))

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

            FirebaseApp.initializeApp(options)
        }

//            val db : Firestore = FirestoreClient.getFirestore();
//            return db

    }
}