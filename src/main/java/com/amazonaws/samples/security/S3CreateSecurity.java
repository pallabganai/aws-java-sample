package com.amazonaws.samples.security;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.IpPermission;

public class S3CreateSecurity {

    public static void main(String[] args) {
    	S3CreateSecurity s3CreateSecurity = new S3CreateSecurity();
    	s3CreateSecurity.createSecurity();
    }
    
	public String createSecurity() {
		AmazonEC2 ec2 = new AmazonEC2Client();
		Region defaultRegion = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(defaultRegion);
		
		CreateSecurityGroupRequest createSecurityGroupRequest = new CreateSecurityGroupRequest();
		createSecurityGroupRequest.setGroupName("app-layer-security");
		createSecurityGroupRequest.setDescription("app-layer-security allow");
		
		CreateSecurityGroupResult createSecurityGroupResult = ec2.createSecurityGroup(createSecurityGroupRequest);
		
		System.out.println("Result - " +createSecurityGroupResult.getGroupId());
		
		List<String> ipRanges = new ArrayList<>();
		ipRanges.add("14.202.170.7/32");
		
		IpPermission ipPermission = new IpPermission();
		ipPermission.setIpRanges(ipRanges);
		ipPermission.setIpProtocol("tcp");
		ipPermission.setFromPort(22);
		ipPermission.setToPort(22);
		
		List<IpPermission> ipPermissions = new ArrayList<>();
		ipPermissions.add(ipPermission);
		
		AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest();
		authorizeSecurityGroupIngressRequest.setGroupId(createSecurityGroupResult.getGroupId());
		authorizeSecurityGroupIngressRequest.setIpPermissions(ipPermissions);
		
		ec2.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
		
		return createSecurityGroupResult.getGroupId();
	}
}