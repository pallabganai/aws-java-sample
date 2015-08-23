package com.amazonaws.samples.ec2.instance;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.samples.ec2.keypair.S3CreateKeypair;
import com.amazonaws.samples.ec2.security.S3CreateSecurity;
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
		
		S3CreateSecurity s3CreateSecurity = new S3CreateSecurity();
		List<String> securityGroupIds = new ArrayList<>();
		securityGroupIds.add(s3CreateSecurity.createSecurity());
		
		instancesRequest.setSecurityGroupIds(securityGroupIds);
		
		S3CreateKeypair s3CreateKeypair = new S3CreateKeypair();

		try {
			instancesRequest.setKeyName(s3CreateKeypair.createKeypair());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		instancesRequest.setUserData(getUserDataScript());
		
		RunInstancesResult runInstancesResult = ec2.runInstances(instancesRequest);
		
		for (Instance instance : runInstancesResult.getReservation().getInstances()) {
			System.out.println("InstanceId-" +instance.getInstanceId() +" , staus-" +instance.getState().getName());
		}
	}
	
    private String getUserDataScript() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("#! /bin/bash");
        lines.add("yum update -y");
        lines.add("yum groupinstall -y \"Web Server\" \"MySQL Database\"");
        lines.add("curl http://www.google.com > google.html");
        String str = new String(Base64.encodeBase64(join(lines, "\n").getBytes()));
        
        System.out.println("UserDataScript - " +str);
        return str;
    }
    
    static String join(Collection<String> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(delimiter);
        }
        return builder.toString();
    }
}
