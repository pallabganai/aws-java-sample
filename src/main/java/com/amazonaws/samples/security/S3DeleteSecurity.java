package com.amazonaws.samples.security;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.SecurityGroup;

public class S3DeleteSecurity {

    public static void main(String[] args) {
    	S3DeleteSecurity s3CreateSecurity = new S3DeleteSecurity();
    	s3CreateSecurity.deleteSecurity();
    }
    
	public void deleteSecurity() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);
		
		DescribeSecurityGroupsResult describeSecurityGroupsResult = ec2.describeSecurityGroups();
		DeleteSecurityGroupRequest deleteSecurityGroupRequest = null;
		
		for (SecurityGroup securityGroup : describeSecurityGroupsResult.getSecurityGroups()) {
			if(!"default".equalsIgnoreCase(securityGroup.getGroupName())) {
				System.out.println("Deleting securityGroup - " +securityGroup.getGroupId() +", group name - " +securityGroup.getGroupName());

				deleteSecurityGroupRequest = new DeleteSecurityGroupRequest();
				deleteSecurityGroupRequest.setGroupId(securityGroup.getGroupId());
				ec2.deleteSecurityGroup(deleteSecurityGroupRequest);
			} else {
				System.out.println("Not Deleting securityGroup - " +securityGroup.getGroupId() +", group name - " +securityGroup.getGroupName());
			}
		}
	}
}