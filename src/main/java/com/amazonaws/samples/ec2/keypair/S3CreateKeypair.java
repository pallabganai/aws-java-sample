package com.amazonaws.samples.ec2.keypair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.KeyPair;

public class S3CreateKeypair {

	public static void main(String[] args) {
		S3CreateKeypair s3CreateKeypair = new S3CreateKeypair();
		try {
			s3CreateKeypair.createKeypair();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String createKeypair() throws FileNotFoundException, UnsupportedEncodingException {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);

		CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();
		createKeyPairRequest.setKeyName("pallab-key");
		
		CreateKeyPairResult createKeyPairResult = ec2.createKeyPair(createKeyPairRequest);
		
		KeyPair keyPair = createKeyPairResult.getKeyPair();
		
		PrintWriter writer = new PrintWriter("C:\\Users\\pkumar\\Dropbox\\Personal\\AWS\\pallab-key.pem", "UTF-8");
		writer.println(keyPair.getKeyMaterial());
		writer.close();
		
		System.out.println("New KeyName - " +keyPair.getKeyName());
		
		return keyPair.getKeyName();
	}
}
