package pl.filipwlodarczyk.worktrackerv2.image;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;

@RestController
@RequestMapping("/image")
public class ImageController {
    private static final String CONNECTION_STRING =
            "DefaultEndpointsProtocol=https;AccountName=worktrackerimages;AccountKey=Qop/PPIgpgg+bWDcEDWoXBbjc2YeRvbdyJGM4PDhfoXUnd0SYsJ8sclU3FitP5h7zIT2/5NbLhKS+AStJcsy1g==;EndpointSuffix=core.windows.net";

    @GetMapping
    public ResponseEntity<Void> getProfilePhoto(HttpServletResponse response, Principal principal) {
        String username = principal.getName();
        String blobName = username + ".jpeg"; // Assuming the image format is jpeg

        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(CONNECTION_STRING)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("profile-photos");

            if (!containerClient.exists()) {
                return ResponseEntity.status(404).body(null);
            }

            BlobClient blobClient;

             blobClient = containerClient.getBlobClient(blobName);

            if (!blobClient.exists()) {
                blobName = "default.jpg";
                blobClient = containerClient.getBlobClient(blobName);
            }

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + blobName + "\"");

            try (OutputStream outputStream = response.getOutputStream()) {
                blobClient.downloadStream(outputStream);
            }

            return ResponseEntity.ok().build();
        } catch (BlobStorageException e) {
            System.err.println("BlobStorageException: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> uploadProfilePhoto(@RequestParam("file") MultipartFile file, Principal principal) {
        String username = principal.getName();
        String blobName = username + ".jpeg"; // Assuming the image format is jpeg

        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(CONNECTION_STRING)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("profile-photos");

            if (!containerClient.exists()) {
                containerClient.create();
            }

            BlobClient blobClient = containerClient.getBlobClient(blobName);

            try (InputStream inputStream = file.getInputStream()) {
                blobClient.upload(inputStream, file.getSize(), true);
            }

            return ResponseEntity.ok().build();
        } catch (BlobStorageException e) {
            System.err.println("BlobStorageException: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
