package com.zassignment.api

import org.junit.jupiter.api.Test
import com.zassignment.api.PhotoController
import com.zassignment.service.PhotoService
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.multipart.MultipartFile
import java.util.ArrayList

@WebMvcTest(PhotoController::class)
class PhotoControllerTest @Autowired constructor(
    private var mockMvc: MockMvc,
    @MockBean
    private var photoServiceMock: PhotoService
) {

    @Test
    fun getAllPhotoTest() {
        val request : RequestBuilder = MockMvcRequestBuilders.get("/image/db").accept(MediaType.APPLICATION_JSON)
        val expected = "[{\"label\":\"test\",\"owberId\":\"testId\",\"uri\":\"testUri\",\"timestamp\":\"testTime\"}]"

        `when`(photoServiceMock.getPhoto()).thenReturn(
            arrayListOf(hashMapOf("label" to "test", "owberId" to "testId", "uri" to "testUri", "timestamp" to "testTime"))
        )

        val result : MvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        assertEquals(expected, result.response.contentAsString)

    }

    @Test
    fun putNewPostTest() {

        // val fileMock = mock(MultipartFile::class.java);
        val fileMock = MockMultipartFile("file", "content".toByteArray())
        val descMock = MockMultipartFile("description", "", "text/plain", "description".toByteArray());
        val owneridMock = MockMultipartFile("ownerId", "", "text/plain", "ownerId".toByteArray());
        val descriptionMock = "mockDescription"
        val ownerIdMock = "mockDescription"
        val expected = "documentId";

        // val request : RequestBuilder = MockMvcRequestBuilders.post("/post").contentType(MediaType.MULTIPART_FORM_DATA).content();
        val request : RequestBuilder = MockMvcRequestBuilders
            .multipart("/post")
            .file(fileMock)
//            .file(descMock)
//            .file(owneridMock)
            .param("description", "descriptionMock")
            .param("ownerId", "ownerIdMock")
        // why this parameter causes 400 bad request error?

        `when`(photoServiceMock.createNewPost(fileMock, descriptionMock, ownerIdMock)).thenReturn("documentId");

        mockMvc.perform(request).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string("documentId"))

    }
}