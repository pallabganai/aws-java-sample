package com.amazonaws.samples.ec2.keypair;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.KeyPairInfo;

public class S3PrepareKeypair {

	public static void main(String[] args) {
		S3PrepareKeypair s3PrepareKeypair = new S3PrepareKeypair();
		try {
			System.out.println(s3PrepareKeypair.prepareKeypair());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String prepareKeypair() throws FileNotFoundException, UnsupportedEncodingException {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);

		DescribeKeyPairsResult describeKeyPairsResult = ec2.describeKeyPairs();
		
		for (KeyPairInfo keyPairInfo : describeKeyPairsResult.getKeyPairs()) {
			System.out.println("key name - " +keyPairInfo.getKeyName());
			
			if("pallab-key".equalsIgnoreCase(keyPairInfo.getKeyName()))
				return keyPairInfo.getKeyName();
		}
		
		return null;
	}
}
