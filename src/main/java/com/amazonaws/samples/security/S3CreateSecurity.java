package com.amazonaws.samples.security;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;

public class S3CreateSecurity {

    public static void main(String[] args) {
    	S3CreateSecurity s3CreateSecurity = new S3CreateSecurity();
    	s3CreateSecurity.createSecurity();
    }
    
	public void createSecurity() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);
		
		CreateSecurityGroupRequest createSecurityGroupRequest = new CreateSecurityGroupRequest();
		createSecurityGroupRequest.setGroupName("app-layer-security");
		createSecurityGroupRequest.setDescription("app-layer-security allow");
		
		CreateSecurityGroupResult createSecurityGroupResult = ec2.createSecurityGroup(createSecurityGroupRequest);
		
		System.out.println("Result - " +createSecurityGroupResult.getGroupId());
	}
}