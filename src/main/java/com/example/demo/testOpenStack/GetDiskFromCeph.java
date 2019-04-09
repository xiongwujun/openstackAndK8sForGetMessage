package com.example.demo.testOpenStack;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ceph.pojo.JsonRootBean;
import com.ceph.pojo.Output;
import com.ceph.pojo.Pools;
import com.example.demo.token.OpenstackToken;
import com.example.utils.PropertyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class GetDiskFromCeph {
	//	@Value(value="${ceph_disk_url}")
	private String ceph_disk_url=PropertyUtil.getProperty("ceph_disk_url");
	//	@Value(value="${ceph_poolName}")
	private String poolName=PropertyUtil.getProperty("ceph_poolName");
	//	private HashMap<String,Object> openstackMessage = OpenstackToken.getOpenstackMessage();
	public void getDiskFromCeph() throws Exception{
		RestTemplate restTemplate = TestOpenStackAPI.getRestTemplate();
		URI uri = URI.create(ceph_disk_url);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		String body = response.getBody();
		ObjectMapper mapper=new ObjectMapper();
		JsonRootBean value = mapper.readValue(body.getBytes(), JsonRootBean.class);
		Output output = value.getOutput();
		List<Pools> pools = output.getPools();
		HashMap<String,Object> map = OpenstackToken.getOpenstackMessage();
		for (Pools pool : pools) {
			if(poolName.equals(pool.getName())) {
				//					openstackMessage.put("Max_avail", Long.toString(pool.getStats().getMax_avail()));
				//					openstackMessage.put("Bytes_used()", pool.getStats().getBytes_used());
				//					openstackMessage.put("Percent_used()", pool.getStats().getPercent_used());
				//					ObjectMapper aMapper=new ObjectMapper();
				long quota_bytes = pool.getStats().getQuota_bytes();
				if(quota_bytes==0) {
					map.put("openstack_total_disk",Math.round((((double)(pool.getStats().getMax_avail()))/1073741824)*Math.pow(10, 2))/Math.pow(10, 2));
				}else {
					map.put("openstack_total_disk",Math.round((((double)quota_bytes)/1073741824)*Math.pow(10, 2))/Math.pow(10, 2));
				}
				map.put("openstack_used_disk", pool.getStats().getBytes_used()/1073741824);
				//					System.out.println("volumes总磁盘大小:------------"+Long.toString(pool.getStats().getMax_avail()/1073741824)+"G	");
				//					System.out.println("volumes磁盘已经使用的大小:------------"+pool.getStats().getBytes_used()/1048576+"M");
				//					System.out.println("volumes磁盘使用的百分比:------------"+pool.getStats().getPercent_used()+"%");
			}
		}

	}
}
