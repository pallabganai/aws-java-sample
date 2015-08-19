package com.amazonaws.samples.instance;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.BlockDeviceMapping;
import com.amazonaws.services.ec2.model.EbsBlockDevice;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.VolumeType;

public class S3CreateInstance {

	public static void main(String[] args) {
		S3CreateInstance s3CreateInstance = new S3CreateInstance();
		s3CreateInstance.createInstance();
	}

	public void createInstance() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);
		
		List<BlockDeviceMapping> blockDeviceMappings = new ArrayList<>();
		
		EbsBlockDevice ebs = new EbsBlockDevice();
		ebs.setVolumeType(VolumeType.Gp2);
		ebs.setVolumeSize(8);
		
		BlockDeviceMapping blockDeviceMapping = new BlockDeviceMapping();
		blockDeviceMapping.setDeviceName("/dev/xvda");
		blockDeviceMapping.setEbs(ebs);
		
		blockDeviceMappings.add(blockDeviceMapping);
		
		RunInstancesRequest instancesRequest = new RunInstancesRequest();
		
		instancesRequest.setInstanceType(InstanceType.T2Micro);
		instancesRequest.setImageId("ami-e7527ed7");
		instancesRequest.setMaxCount(1);
		instancesRequest.setMinCount(1);
		instancesRequest.setBlockDeviceMappings(blockDeviceMappings);
		
		RunInstancesResult runInstancesResult = ec2.runInstances(instancesRequest);
		
		for (Instance instance : runInstancesResult.getReservation().getInstances()) {
			System.out.println("InstanceId-" +instance.getInstanceId() +" , staus-" +instance.getState().getName());
		}
	}
}
