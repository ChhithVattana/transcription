package com.example.project.utils

import com.example.project.exception.ResourceNotFoundException
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import javax.servlet.http.HttpServletRequest

class FileUpload {
    companion object {
        fun storeImage(file: MultipartFile?, filepath: String?): String? {
            val path = Paths.get(filepath!!).toAbsolutePath().normalize()
            val directory = File(path.toString())
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val extension = file?.originalFilename.toString()
            val sub = extension.substring(extension.lastIndexOf("."))

            val fileName = UUID.randomUUID().toString().plus(sub)
            try {
                if (file != null) {
                    Files.copy(file.inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING)
                }
            } catch (e: IOException) {
                return null.toString()
            }
            return fileName
        }

        fun removeImage(fileName: String, fileProperty: String?): Boolean {
            val path = Paths.get(fileProperty).toAbsolutePath().normalize()
            val filePath = path.resolve(fileName).normalize()
            val file = File(filePath.toString())

            return if (file.exists()) file.delete()
            else false
        }

        fun loadImage(fileName: String, fileProperty: String, request: HttpServletRequest): ResponseEntity<Resource> {
            try {
                val path = Paths.get(fileProperty).toAbsolutePath().normalize()
                val filePath = path.resolve(fileName).normalize()
                val resource = UrlResource(filePath.toUri())

                resource.contentLength()

                val contentType = request.servletContext.getMimeType(resource.file.absolutePath)
                    ?: throw ResourceNotFoundException("Error")
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body<Resource>(resource)
            } catch (e: MalformedURLException) {
                throw ResourceNotFoundException("error")
            } catch (e: IOException) {
                throw ResourceNotFoundException("error")
            }
        }
    }
}