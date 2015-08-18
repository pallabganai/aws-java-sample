package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

public class S3DeleteInstance {
	public static void main(String[] args) {
		S3DeleteInstance s3DeleteVolume = new S3DeleteInstance();
		s3DeleteVolume.deleteAllInstances();
	}
	
	public void deleteAllInstances() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);

		DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
		TerminateInstancesRequest terminateInstancesRequest = null;
		
		List<String> instanceIdList = null;
		
		for (Reservation reservation : describeInstancesResult.getReservations()) {

			instanceIdList = new ArrayList<>();
			
			for (Instance instance : reservation.getInstances()) {
				System.out.println("going to delete InstanceId - " + instance.getInstanceId() + " State=" +instance.getState());
				
				instanceIdList.add(instance.getInstanceId());
			}

			terminateInstancesRequest = new TerminateInstancesRequest();
			terminateInstancesRequest.setInstanceIds(instanceIdList);
			
			ec2.terminateInstances(terminateInstancesRequest);
		} 
	}
}