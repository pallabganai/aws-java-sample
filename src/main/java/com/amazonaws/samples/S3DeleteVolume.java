package com.amazonaws.samples;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DeleteVolumeRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.amazonaws.services.ec2.model.Volume;

public class S3DeleteVolume {
	public static void main(String[] args) {
		S3DeleteVolume s3DeleteVolume = new S3DeleteVolume();
		s3DeleteVolume.deleteAllVolumes();
	}
	
	public void deleteAllVolumes() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);

		DescribeVolumesResult describeVolumesResult = ec2.describeVolumes();
		DeleteVolumeRequest deleteVolumeRequest = null;
		
		for (Volume volume : describeVolumesResult.getVolumes()) {
			System.out.println("going to delete - " + volume.getVolumeId());
			deleteVolumeRequest = new DeleteVolumeRequest(volume.getVolumeId());
			
			ec2.deleteVolume(deleteVolumeRequest);
		} 
	}
}
