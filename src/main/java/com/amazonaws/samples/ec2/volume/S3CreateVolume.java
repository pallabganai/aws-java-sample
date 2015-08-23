package com.amazonaws.samples.ec2.volume;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateVolumeRequest;
import com.amazonaws.services.ec2.model.CreateVolumeResult;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;

public class S3CreateVolume {
	public static void main(String[] args) {
		S3CreateVolume createVolume = new S3CreateVolume();
		createVolume.createVolume();
	}
	
	public void createVolume() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);

		DescribeAvailabilityZonesResult describeAvailabilityZonesResult = ec2.describeAvailabilityZones();
		String availabilityZone = describeAvailabilityZonesResult.getAvailabilityZones().get(0).getZoneName();
		System.out.println("availabilityZone=" + availabilityZone);

		CreateVolumeRequest createVolumeRequest = new CreateVolumeRequest();
		createVolumeRequest.setSize(6);
		createVolumeRequest.setVolumeType("gp2");
		createVolumeRequest.setAvailabilityZone(availabilityZone);
		CreateVolumeResult createVolumeResult = ec2.createVolume(createVolumeRequest);

		System.out.println("createVolumeResult - " + createVolumeResult.getVolume().getVolumeId());
	}
}
