package com.amazonaws.samples.keypair;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;

public class S3DeleteKeypair {
	public static void main(String[] args) {
		S3DeleteKeypair s3DeleteKeypair = new S3DeleteKeypair();
		s3DeleteKeypair.deleteKeypair();
	}
	
	public void deleteKeypair() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);
	}
}