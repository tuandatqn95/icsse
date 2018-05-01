package com.example.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.example.model.DriveFile;

@Service
public class AmazonS3Service {

	private static String bucketName = "icsse";

	public static AWSCredentials getCredential() {
		return new BasicAWSCredentials("AKIAJWTOS7QFKVSYOXHA", "gVOL88gnbMPTLiH9VSAMlY++5I0g1ZWOFGsRhLF9");
	}

	public static AmazonS3 getAmazonService() {
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.withSignerOverride("S3SignerType");

		AmazonS3 s3 = new AmazonS3Client(getCredential(), clientConfig);
		// s3.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
		return s3;
	}

	public String upload(File file) throws IOException {
		PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file); 
		getAmazonService().putObject(request);
		return file.getName();
	}

	// public void downloadFile(String fileKey, HttpServletResponse response) throws
	// IOException {
	//
	////// File file = getDriveService().files().get(fileId).execute();
	//// S3Object object = getAmazonService().getObject(bucketName,fileKey);
	//// response.addHeader("Content-Disposition", "attachment; filename=" +
	// object.getKey());
	////
	//// response.getOutputStream().write(object.getObjectContent().);
	//// getDriveService().files().get(fileId).executeMediaAndDownloadTo(response.getOutputStream());
	//// response.getOutputStream().flush();
	// }

	public List<S3Object> getListDriveFile(Set<DriveFile> driveFiles) throws IOException {
		List<S3Object> listfile = new ArrayList<S3Object>();
		for (DriveFile file : driveFiles) {
			listfile.add(getAmazonService().getObject(bucketName, file.getFileId()));
		}
		return listfile;
	}

}
