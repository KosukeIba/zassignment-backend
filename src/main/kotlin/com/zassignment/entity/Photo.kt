package com.zippractice1.entity

// import com.google.cloud.spring.data.datastore.core.mapping.Entity
import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.web.multipart.MultipartFile

// this is for interaction with datastore not for firestore
//@Entity
//data class Photo (
//    @Id
//    var id: String? = null,
//    var uri: String? = null,
//    var label: String? = null,
//    var ownerId: String? = null,
//    var timestamp: com.google.cloud.Timestamp?
//)

@Getter
@Setter
public class PhotoPost {

    var id : String = "";
    var uri : String = "";
    var label : String = "";
    var ownerId : String = "";
    var timestamp : com.google.cloud.Timestamp? = null

}

@Getter
@Setter
// MultiParFile cannot be treated in Entity. need to find a way to convert byte and store into storage.
public class PhotoFile {

    var file : MultipartFile? = null;
    var description : String = "";
    var ownerId : String = "";

}
