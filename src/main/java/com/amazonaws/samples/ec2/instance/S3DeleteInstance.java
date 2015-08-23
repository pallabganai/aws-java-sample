package com.amazonaws.samples.ec2.instance;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.samples.ec2.keypair.S3DeleteKeypair;
import com.amazonaws.samples.ec2.security.S3DeleteSecurity;
import com.amazonaws.samples.ec2.volume.S3DeleteVolume;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateChange;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

public class S3DeleteInstance {
	public static void main(String[] args) throws InterruptedException {
		S3DeleteInstance s3DeleteVolume = new S3DeleteInstance();
		s3DeleteVolume.deleteAllInstances();
	}
	
	public void deleteAllInstances() throws InterruptedException {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);

		DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
		TerminateInstancesRequest terminateInstancesRequest = null;
		TerminateInstancesResult terminateInstancesResult = null;
		
		List<String> instanceIdList = null;
		
		for (Reservation reservation : describeInstancesResult.getReservations()) {

			instanceIdList = new ArrayList<>();
			
			for (Instance instance : reservation.getInstances()) {
				System.out.println("going to delete InstanceId - " + instance.getInstanceId() + " State=" +instance.getState());
				
				instanceIdList.add(instance.getInstanceId());
			}

			while(instanceIdList.size() > 0) {
				terminateInstancesRequest = new TerminateInstancesRequest();
				terminateInstancesRequest.setInstanceIds(instanceIdList);
				
				terminateInstancesResult = ec2.terminateInstances(terminateInstancesRequest);
				
				List<InstanceStateChange> instanceStateChanges = terminateInstancesResult.getTerminatingInstances();
				
				for (InstanceStateChange instanceStateChange : instanceStateChanges) {
					InstanceState instanceState = instanceStateChange.getCurrentState();
	
					System.out.println("instanceState = " +instanceState.getName());
					
					if(!"terminated".equalsIgnoreCase(instanceState.getName())) {
						System.out.println("Waiting to terminate, instanceState = " +instanceStateChange.getInstanceId() + ", " +instanceState.getName());
	
						Thread.sleep(15000);
						
						continue;
					}

					System.out.println("remove instace id from list - " +instanceIdList.remove(instanceStateChange.getInstanceId()));

					System.out.println("instanceState = " +instanceStateChange.getInstanceId() + ", " +instanceState.getName());
				}
			}
		} 

		S3DeleteVolume s3DeleteVolume = new S3DeleteVolume();
		s3DeleteVolume.deleteAllVolumes();

    	S3DeleteSecurity s3CreateSecurity = new S3DeleteSecurity();
    	s3CreateSecurity.deleteSecurity();
		
		S3DeleteKeypair s3DeleteKeypair = new S3DeleteKeypair();
		s3DeleteKeypair.deleteKeypair();
	}
}