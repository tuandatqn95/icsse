package com.example.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.example.model.DriveFile;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

@Service
public class DriveService {
	/** Application name. */
	private static final String APPLICATION_NAME = "Drive API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/drive-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	// private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	// public static Credential authorize() throws IOException {
	// // Load client secrets.
	// InputStream in =
	// DriveService.class.getResourceAsStream("/client_secret.json");
	// GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
	// new InputStreamReader(in));
	//
	// // Build flow and trigger user authorization request.
	// GoogleAuthorizationCodeFlow flow = new
	// GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	// clientSecrets,
	// SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
	// Credential credential = new AuthorizationCodeInstalledApp(flow, new
	// LocalServerReceiver()).authorize("user");
	// System.out.println("Credentials saved to " +
	// DATA_STORE_DIR.getAbsolutePath());
	// return credential;
	// }
	public static Credential authorize() throws IOException {
		GoogleCredential credential = GoogleCredential
				.fromStream(DriveService.class.getResourceAsStream("/My Project-d80184c3ef0f.json"))
				.createScoped(Collections.singleton(DriveScopes.DRIVE));

		return credential;
	}

	/**
	 * Build and return an authorized Drive client service.
	 * 
	 * @return an authorized Drive client service
	 * @throws IOException
	 */
	public static Drive getDriveService() throws IOException {
		Credential credential = authorize();
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
	}

	public File uploadFile(Path filePath, boolean useDirectUpload) throws IOException {

		java.io.File uploadFile = new java.io.File(filePath.toUri());
		File fileMetadata = new File();
		fileMetadata.setName(uploadFile.getName());

		FileContent mediaContent = new FileContent(
				MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(uploadFile), uploadFile);

		Drive.Files.Create create = getDriveService().files().create(fileMetadata, mediaContent);
		MediaHttpUploader uploader = create.getMediaHttpUploader();
		uploader.setDirectUploadEnabled(useDirectUpload);
		return create.execute();
	}

	public void downloadFile(String fileId, HttpServletResponse response) throws IOException {

		File file = getDriveService().files().get(fileId).execute();
		response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
		getDriveService().files().get(fileId).executeMediaAndDownloadTo(response.getOutputStream());
		response.getOutputStream().flush();
	}

	public List<File> getListDriveFile(Set<DriveFile> driveFiles) throws IOException {
		List<File> listfile = new ArrayList<File>();
		for (DriveFile file : driveFiles) {
			listfile.add(getDriveService().files().get(file.getFileId()).execute());
		}
		return listfile;
	}

	// public static void main(String[] args) throws IOException {
	// // Build a new authorized API client service.
	// Drive service = getDriveService();
	//
	// // Print the names and IDs for up to 10 files.
	// FileList result =
	// service.files().list().setPageSize(10).setFields("nextPageToken, files(id,
	// name)").execute();
	// List<File> files = result.getFiles();
	// if (files == null || files.size() == 0) {
	// System.out.println("No files found.");
	// } else {
	// System.out.println("Files:");
	// for (File file : files) {
	// System.out.printf("%s (%s)\n", file.getName(), file.getId());
	// }
	// }
	// }

}
