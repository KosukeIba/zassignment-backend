package com.zassignment.api

import org.junit.jupiter.api.Test
import com.zassignment.api.PhotoController
import com.zassignment.service.PhotoService
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.ArrayList

@WebMvcTest(PhotoController::class)
class PhotoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockBean
    private val photoServiceMock: PhotoService
) {

    @Test
    fun getAll() {
        val request : RequestBuilder = MockMvcRequestBuilders.get("/image/db").accept(MediaType.APPLICATION_JSON)
        val expected = "[{\"label\":\"test\",\"owberId\":\"testId\",\"uri\":\"testUri\",\"timestamp\":\"testTime\"}]"

        `when`(photoServiceMock.getPhoto()).thenReturn(
            arrayListOf(hashMapOf("label" to "test", "owberId" to "testId", "uri" to "testUri", "timestamp" to "testTime"))
        )

        val result : MvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        assertEquals(expected, result.response.contentAsString)

    }

    @Test
    fun putNewPost() {
    }
}